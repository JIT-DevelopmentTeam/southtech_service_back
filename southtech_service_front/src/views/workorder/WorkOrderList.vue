<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="客户">
              <a-input placeholder="请输入客户" v-model="queryParam.clientName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="需要派工">
              <a-radio-group v-model="queryParam.needDispatch" :options="options.needDispatchOptions"/>
            </a-form-item>
          </a-col>
          
          <template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item label="状态">
                <j-dict-select-tag
                  placeholder="请选择状态"
                  v-model="queryParam.status"
                  dictCode="work_order_status"
                />
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="类型">
                <j-dict-select-tag
                  placeholder="请选择类型"
                  v-model="queryParam.type"
                  dictCode="work_order_type"
                />
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="紧急程度">
                <j-dict-select-tag
                  placeholder="请选择紧急程度"
                  v-model="queryParam.emergencyLevel"
                  dictCode="work_order_emergency_level"
                />
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="客服">
                <a-input placeholder="请输入客服" v-model="queryParam.customerServiceRealName"></a-input>
              </a-form-item>
            </a-col>
          </template>
          <a-col :md="6" :sm="8">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button
                type="primary"
                @click="searchReset"
                icon="reload"
                style="margin-left: 8px"
              >重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'" />
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button @click="exportReport" v-has="'workOrder:exportReport'" type="primary" icon="download">导出报表</a-button>
      <a-button
        type="primary"
        v-has="'workOrder:returnVisits'"
        v-if="selectedRowKeys.length === 1"
        icon="message"
        @click="handleServiceVisits"
      >回访</a-button>

      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" v-has="'workOrder:finish'" @click="setStatus(3)">
            <a-icon type="check" />强制完成
          </a-menu-item>
          <a-menu-item key="2" v-has="'workOrder:close'" @click="setStatus(4)">
            <a-icon type="close" />强制关闭
          </a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px">
          批量操作
          <a-icon type="down" />
        </a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="small"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{fixed:true,selectedRowKeys: selectedRowKeys, onChange: onSelectChange, type:'checkbox'}"
        :customRow="clickThenSelect"
        @change="handleTableChange"
        :scroll="tableScroll"
      >
        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此图片</span>
          <img
            v-else
            :src="getImgView(text)"
            height="25px"
            alt="图片不存在"
            style="max-width:80px;font-size: 12px;font-style: italic;"
          />
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="uploadFile(text)"
          >下载</a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <div v-if="record.needDispatch === '1' && (record.status === '1' || record.status === '3')" style="display: inline;">
            <a-divider type="vertical" />
            <a-dropdown>
              <a v-has="'workOrder:dispatch'" @click="dispatch(record)" class="ant-dropdown-link">
                派工
              </a>
            </a-dropdown>
          </div>

          <div v-has="'workOrder:del'" style="display: inline;">
            <a-divider type="vertical" />
            <a-dropdown>
              <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
              <a-menu slot="overlay">
                <a-menu-item>
                  <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                    <a>删除</a>
                  </a-popconfirm>
                </a-menu-item>
              </a-menu>
            </a-dropdown>
          </div>
        </span>
      </a-table>
    </div>

    <a-tabs defaultActiveKey="1">
      <a-tab-pane tab="工单进度" key="1">
        <work-order-progress-page :mainId="selectedMainId" />
      </a-tab-pane>
      <a-tab-pane tab="汇报记录" key="2" forceRender>
        <progress-report-list :mainId="selectedMainId" />
      </a-tab-pane>
      <a-tab-pane tab="回访记录" key="3">
        <service-visits-list-page :mainId="selectedMainId"/>
      </a-tab-pane>
      <a-tab-pane tab="服务评价" key="4" forceRender>
        <service-commentery-list :mainId="selectedMainId" />
      </a-tab-pane>
    </a-tabs>

    <workOrder-modal ref="modalForm" @ok="modalFormOk"></workOrder-modal>
    <DisPatchModal ref="dispatchModalForm" @ok="modalFormOk"></DisPatchModal>
    <ServiceVisits ref="ServiceVisits" :workOrder="workOrder" @ok="modalFormOk"/>
  </a-card>
</template>

<script>
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import WorkOrderModal from './modules/WorkOrderModal'
import { getAction, postAction } from '@/api/manage'
import DisPatchModal from './modules/DispatchModal'
import WorkOrderProgressPage from './WorkOrderProgressPage'
import ServiceCommenteryList from './ServiceCommenteryList'
import ProgressReportList from './ProgressReportList'
import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
import ServiceVisits from '@/views/servicevists/modules/ServiceVisitsModal'
import { initDictOptions, filterMultiDictText } from '@/components/dict/JDictSelectUtil'
import ServiceVisitsListPage from './ServiceVisitsListPage'

import {formatDate} from '@/utils/util.js'



export default {
  name: 'WorkOrderList',
  mixins: [JeecgListMixin],
  components: {
    JDictSelectTag,
    DisPatchModal,
    WorkOrderProgressPage,
    ServiceCommenteryList,
    ProgressReportList,
    WorkOrderModal,
    ServiceVisits,
    ServiceVisitsListPage,
  },
  data() {
    return {
      description: '工单信息管理页面',
      tableScroll: { x: 16 * 147 + 50 },
      // 表头
      columns: [
        {
          title: '#',
          dataIndex: '',
          key: 'rowIndex',
          width: 60,
          align: 'center',
          customRender: function(t, r, index) {
            return parseInt(index) + 1
          }
        },
        // {
        //   title: '编号',
        //   align: 'center',
        //   dataIndex: 'number',
        //   type: 'income',
        //   note: 'transfer'
        // },
        {
          title: '客户',
          align: 'center',
          dataIndex: 'clientId',
          customRender: text => {
            if (!text) {
              return ''
            } else {
              return filterMultiDictText(this.dictOptions['clientId'], text + '')
            }
          }
        },
        {
          title:'设备档案',
          align:"center",
          dataIndex:"deviceNumber",
          customRender:(text)=>{
            if(!text){
              return ''
            }else{
              return filterMultiDictText(this.dictOptions['deviceNumber'], text+"")
            }
          }
        },
        {
          title:'保修期至',
          align:"center",
          width:100,
          dataIndex:"qgp",
        },
        {
          title: '问题描述',
          align: 'center',
          ellipsis: true,
          width: 200,
          dataIndex: 'description'
        },
        {
          title: '联系人',
          align: 'center',
          width: 70,
          dataIndex: 'contactId',
          customRender: text => {
            if (!text) {
              return ''
            } else {
              return filterMultiDictText(this.dictOptions['contactId'], text + '')
            }
          }
        },
        {
          title: '联系电话',
          align: 'center',
          dataIndex: 'mobilePhone',
        },
        {
          title: '紧急程度',
          align: 'center',
          width:90,
          dataIndex: 'emergencyLevel',
          customRender: text => {
            if (!text) {
              return ''
            } else {
              return filterMultiDictText(this.dictOptions['emergencyLevel'], text + '')
            }
          }
        },
        {
          title: '申报时间',
          align: 'center',
          dataIndex: 'declarationTime',
        },
        {
          title: '状态',
          align: 'center',
          width: 70,
          dataIndex: 'status',
          customRender: text => {
            if (!text) {
              return ''
            } else {
              return filterMultiDictText(this.dictOptions['status'], text + '')
            }
          }
        },
        {
          title: '需要派工',
          align: 'center',
          dataIndex: 'needDispatch',
          customRender: text => {
            if (!text) {
              return ''
            } else {
              return filterMultiDictText(this.dictOptions['needDispatch'], text + '')
            }
          }
        },
        {
          title: '类型',
          align: 'center',
          dataIndex: 'type',
          width: 80,
          customRender: text => {
            if (!text) {
              return ''
            } else {
              return filterMultiDictText(this.dictOptions['type'], text + '')
            }
          }
        },
        {
          title: '申报人',
          align: 'center',
          width: 70,
          dataIndex: 'createrName'
        },
        {
          title: '接入方式',
          align: 'center',
          dataIndex: 'accessMethod',
          width: 100,
          customRender: text => {
            if (!text) {
              return ''
            } else {
              return filterMultiDictText(this.dictOptions['accessMethod'], text + '')
            }
          }
        },
        {
          title: '客服',
          align: 'center',
          width: 70,
          dataIndex: 'customerServiceName',
          customRender: text => {
            if (!text) {
              return ''
            } else {
              return filterMultiDictText(this.dictOptions['customerServiceName'], text + '')
            }
          }
        },
          {
            title:'服务工程师',
            align:"center",
            width: 70,
            dataIndex: 'serviceEngineerName',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['serviceEngineerName'], text+"")
              }
            }
          },
          {
            title:'故障部位',
            align:"center",
            dataIndex: 'faultLocation',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['faultLocation'], text+"")
              }
            }
          },
          {
            title:'派工时间',
            align:"center",
            dataIndex: 'assignedTime'
          },
          {
            title:'同行人员',
            align:"center",
            dataIndex: 'peers',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['peers'], text+"")
              }
            }
          },
          {
            title:'预约服务时间',
            align:"center",
            width: 100,
            dataIndex: 'appointment',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return formatDate(text,'yyyy-MM-dd');
              }
            }
          },
          {
            title:'计划完成时间',
            align:"center",
            width: 100,
            dataIndex: 'plannedCompletionTime',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return formatDate(text,'yyyy-MM-dd');
              }
            }
          },
          {
            title:'实际完成时间',
            align:"center",
            width: 100,
            dataIndex: 'actualCompletionTime',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return formatDate(text,'yyyy-MM-dd');
              }
            }
          },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          fixed: 'right',
          width: 200,
          scopedSlots: { customRender: 'action' }
        }
      ],
      url: {
        list: '/workorder/workOrder/listPage',
        delete: '/workorder/workOrder/delete',
        deleteBatch: '/workorder/workOrder/deleteBatch',
        exportXlsUrl: '/workorder/workOrder/exportXls',
        importExcelUrl: 'workorder/workOrder/importExcel',
        setStatusBatch: '/workorder/workOrder/setStatusBatch',
        exportReport:'workorder/workOrder/exportReport'
      },
      dictOptions: {
        status: [],
        type: [],
        accessMethod: [],
        clientId: [],
        contactId: [],
        emergencyLevel: [],
        customerServiceName: [],
        deviceNumber:[],
        serviceEngineerName:[],
        faultLocation:[],
        peers:[],
        needDispatch:[]
      },
      options:{
        needDispatchOptions:[]
      },
      /* 分页参数 */
      ipagination: {
        current: 1,
        pageSize: 10,
        pageSizeOptions: ['5', '10', '50'],
        showTotal: (total, range) => {
          return range[0] + '-' + range[1] + ' 共' + total + '条'
        },
        showQuickJumper: true,
        showSizeChanger: true,
        total: 0
      },
      selectedMainId: '',
      selectedMainStatus: null,
      workOrder: {}
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  methods: {
    initDictConfig() {
      initDictOptions('work_order_status').then(res => {
        if (res.success) {
          this.$set(this.dictOptions, 'status', res.result)
        }
      })
      initDictOptions('work_order_type').then(res => {
        if (res.success) {
          this.$set(this.dictOptions, 'type', res.result)
        }
      })
      initDictOptions('work_order_access_method').then(res => {
        if (res.success) {
          this.$set(this.dictOptions, 'accessMethod', res.result)
        }
      })
      initDictOptions('tb_client,name,id').then(res => {
        if (res.success) {
          this.$set(this.dictOptions, 'clientId', res.result)
        }
      })
      initDictOptions('tb_contact,name,id').then(res => {
        if (res.success) {
          this.$set(this.dictOptions, 'contactId', res.result)
        }
      })
      initDictOptions('work_order_emergency_level').then(res => {
        if (res.success) {
          this.$set(this.dictOptions, 'emergencyLevel', res.result)
        }
      })
      initDictOptions('sys_user,realname,username').then(res => {
        if (res.success) {
          this.$set(this.dictOptions, 'customerServiceName', res.result)
        }
      })
      initDictOptions('tb_device_number,name,id').then((res) => {
        if (res.success) {
          this.$set(this.dictOptions, 'deviceNumber', res.result)
        }
      })
      initDictOptions('sys_user,realname,username').then((res) => {
        if (res.success) {
          this.$set(this.dictOptions, 'serviceEngineerName', res.result)
        }
      })
      initDictOptions('work_order_detail_fault_location').then((res) => {
        if (res.success) {
          this.$set(this.dictOptions, 'faultLocation', res.result)
        }
      })
      initDictOptions('sys_user,realname,username').then((res) => {
        if (res.success) {
          this.$set(this.dictOptions, 'peers', res.result)
        }
      });
      initDictOptions('DIC_YES_OR_NOT').then((res) => {
        if (res.success) {
          this.$set(this.dictOptions, 'needDispatch', res.result)
          for (let index = 0; index < res.result.length; index++) {
             this.options.needDispatchOptions.push({label:res.result[index].text,value:res.result[index].value}); 
          }
        }
      })
    },
    clickThenSelect(record) {
      return {
        on: {
          click: () => {
            this.onSelectChange(record.id.split(','), [record])
          }
        }
      }
    },
    onClearSelected() {
      this.selectedRowKeys = []
      this.selectionRows = []
      this.selectedMainId = ''
    },
    onSelectChange(selectedRowKeys, selectionRows) {
      this.selectedMainId = selectedRowKeys[0]
      if (selectionRows[0]) {
        this.selectedMainStatus = selectionRows[0].status
      }
      this.selectedRowKeys = selectedRowKeys
      this.selectionRows = selectionRows

      if (this.selectionRows.length === 1) {
        let row = this.selectionRows[0]
        this.workOrder = row
        this.$refs.ServiceVisitsList.loadData();
      }
    },
    loadData(arg) {
      if (!this.url.list) {
        this.$message.error('请设置url.list属性!')
        return
      }
      //加载数据 若传入参数1则加载第一页的内容
      if (arg === 1) {
        this.ipagination.current = 1
      }
      this.onClearSelected()
      var params = this.getQueryParams() //查询条件
      this.loading = true
      getAction(this.url.list, params).then(res => {
        if (res.success) {
          this.dataSource = res.result.records
          this.ipagination.total = res.result.total
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
        this.loading = false
      })
    },
    setStatus(status) {
      if (this.selectedRowKeys.length <= 0) {
        this.$message.warning('请选择一条记录！')
        return
      } else {
        var ids = ''
        for (var a = 0; a < this.selectedRowKeys.length; a++) {
          ids += this.selectedRowKeys[a] + ','
        }
      }
      let message = null;
      switch (status) {
        case 3:
          message = '您确定要完成选中工单吗?'
          break
        case 4:
          message = '您确定要关闭选中工单吗?'
          break
      }
      this.$confirm({
        title: message,
        onOk: () => {
          let url = this.url.setStatusBatch + '?ids=' + this.selectedRowKeys.toString() + '&status=' + status
          postAction(url, null).then(res => {
            if (res.success) {
              this.$message.success(res.message)
              this.loadData()
            } else {
              this.$message.error(res.message)
            }
          })
        }
      })
    },
    handleServiceVisits() {
      this.$refs.ServiceVisits.add()
    },
    dispatch(record){
      record.ids = record.workOrderDetailId;
      this.$refs.dispatchModalForm.edit(record);
      this.$refs.dispatchModalForm.title = "派工";
      this.$refs.dispatchModalForm.disableSubmit = false;
    },
    exportReport() {
      let url = `${window._CONFIG['domianURL']}/${this.url.exportReport}`;
      window.location.href = url;
    },
  }
}
</script>
<style>
/* @import '~@assets/less/common.less'; */

.resize-table-th {
  position: relative;
}
.table-draggable-handle {
  /* width: 10px !important; */
  height: 100% !important;
  left: auto !important;
  right: -5px;
  cursor: col-resize;
  touch-action: none;
  border: none;
}
</style>