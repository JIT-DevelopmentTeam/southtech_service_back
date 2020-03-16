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
            <a-form-item label="状态">
              <j-dict-select-tag placeholder="请选择状态" v-model="queryParam.status" dictCode="work_order_status"/>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item label="类型">
                <j-dict-select-tag placeholder="请选择类型" v-model="queryParam.type" dictCode="work_order_type"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="紧急程度">
                <j-dict-select-tag placeholder="请选择紧急程度" v-model="queryParam.emergencyLevel" dictCode="work_order_emergency_level"/>
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
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
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
      <a-button type="primary" v-has="'workOrder:returnVisits'" icon="message">回访意见</a-button>
      <a-button type="primary" v-has="'workOrder:finish'" icon="check">完成</a-button>
      <a-button type="primary" v-has="'workOrder:close'" icon="close">关闭</a-button>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange, type:'checkbox'}"
        :customRow="clickThenSelect"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="图片不存在" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="uploadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

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
        </span>

      </a-table>
    </div>

    <a-tabs defaultActiveKey="1">
      <a-tab-pane tab="工单明细" key="1" >
        <work-order-detail-list :mainId="selectedMainId" />
      </a-tab-pane>
      <a-tab-pane tab="工单进度" key="2" >
        <work-order-progress-page :mainId="selectedMainId" />
      </a-tab-pane>
    </a-tabs>

    <workOrder-modal ref="modalForm" @ok="modalFormOk"></workOrder-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import WorkOrderModal from './modules/WorkOrderModal'
  import { getAction } from '@/api/manage'
  import WorkOrderDetailList from './WorkOrderDetailList'
  import WorkOrderProgressPage from './WorkOrderProgressPage'
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: "WorkOrderList",
    mixins:[JeecgListMixin],
    components: {
      JDictSelectTag,
      WorkOrderDetailList,
      WorkOrderProgressPage,
      WorkOrderModal
    },
    data () {
      return {
        description: '工单信息管理页面',
        // 表头
        columns: [
          {
            title:'编号',
            align:"center",
            dataIndex: 'number'
          },
          {
            title:'状态',
            align:"center",
            dataIndex: 'status',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['status'], text+"")
              }
            }
          },
          {
            title:'类型',
            align:"center",
            dataIndex: 'type',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['type'], text+"")
              }
            }
          },
          {
            title:'接入方式',
            align:"center",
            dataIndex: 'accessMethod',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['accessMethod'], text+"")
              }
            }
          },
          {
            title:'客户',
            align:"center",
            dataIndex: 'clientId',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['clientId'], text+"")
              }
            }
          },
          {
            title:'联系人',
            align:"center",
            dataIndex: 'contactId',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['contactId'], text+"")
              }
            }
          },
          {
            title:'联系方式',
            align:"center",
            dataIndex: 'contactId',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['mobilePhone'], text+"")
              }
            }
          },
          {
            title:'紧急程度',
            align:"center",
            dataIndex: 'emergencyLevel',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['emergencyLevel'], text+"")
              }
            }
          },
          {
            title:'客服',
            align:"center",
            dataIndex: 'customerServiceName',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['customerServiceName'], text+"")
              }
            }
          },
          {
            title:'申报时间',
            align:"center",
            dataIndex: 'declarationTime'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/workorder/workOrder/list",
          delete: "/workorder/workOrder/delete",
          deleteBatch: "/workorder/workOrder/deleteBatch",
          exportXlsUrl: "/workorder/workOrder/exportXls",
          importExcelUrl: "workorder/workOrder/importExcel",
        },
        dictOptions:{
         status:[],
         type:[],
         accessMethod:[],
         clientId:[],
         contactId:[],
         emergencyLevel:[],
         customerServiceName:[],
         mobilePhone:[]
        },
        /* 分页参数 */
        ipagination:{
          current: 1,
          pageSize: 5,
          pageSizeOptions: ['5', '10', '50'],
          showTotal: (total, range) => {
            return range[0] + "-" + range[1] + " 共" + total + "条"
          },
          showQuickJumper: true,
          showSizeChanger: true,
          total: 0
        },
        selectedMainId:''

      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig(){
        initDictOptions('work_order_status').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'status', res.result)
          }
        })
        initDictOptions('work_order_type').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'type', res.result)
          }
        })
        initDictOptions('work_order_access_method').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'accessMethod', res.result)
          }
        })
        initDictOptions('tb_client,name,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'clientId', res.result)
          }
        })
        initDictOptions('tb_contact,name,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'contactId', res.result)
          }
        })
        initDictOptions('tb_contact,mobile_phone,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'mobilePhone', res.result)
          }
        })
        initDictOptions('work_order_emergency_level').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'emergencyLevel', res.result)
          }
        })
        initDictOptions('sys_user,realname,username').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'customerServiceName', res.result)
          }
        })
      },
      clickThenSelect(record) {
        return {
          on: {
            click: () => {
              this.onSelectChange(record.id.split(","), [record]);
            }
          }
        }
      },
      onClearSelected() {
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.selectedMainId=''
      },
      onSelectChange(selectedRowKeys, selectionRows) {
        this.selectedMainId=selectedRowKeys[0]
        this.selectedRowKeys = selectedRowKeys;
        this.selectionRows = selectionRows;
      },
      loadData(arg) {
        if(!this.url.list){
          this.$message.error("请设置url.list属性!")
          return
        }
        //加载数据 若传入参数1则加载第一页的内容
        if (arg === 1) {
          this.ipagination.current = 1;
        }
        this.onClearSelected()
        var params = this.getQueryParams();//查询条件
        this.loading = true;
        getAction(this.url.list, params).then((res) => {
          if (res.success) {
            this.dataSource = res.result.records;
            this.ipagination.total = res.result.total;
          }
          if(res.code===510){
            this.$message.warning(res.message)
          }
          this.loading = false;
        })
      }

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>