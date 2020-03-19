<template>
  <a-card :bordered="false" :class="'cust-erp-sub-tab'">
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <!-- <a-button v-if="mainId" @click="handleAdd" type="primary" icon="plus">新增</a-button> -->
      <a-button type="primary" v-if="selectedRowKeys.length > 0" v-has="'workOrder:dispatch'" @click="dispatch" icon="select">派工</a-button>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
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
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
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
          <!-- <a @click="handleEdit(record)">编辑</a> -->
          <!-- <a-divider type="vertical" /> -->
          <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
            <a>删除</a>
          </a-popconfirm>
        </span>

      </a-table>
    </div>

    <workOrderDetail-modal ref="modalForm" @ok="modalFormOk" :mainId="mainId"></workOrderDetail-modal>
    <DisPatchModal ref="dispatchModalForm" @ok="modalFormOk" :mainId="mainId"></DisPatchModal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import WorkOrderDetailModal from './modules/WorkOrderDetailModal'
  import DisPatchModal from './modules/DispatchModal'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'



  export default {
    name: "WorkOrderDetailList",
    mixins:[JeecgListMixin],
    components: { WorkOrderDetailModal,DisPatchModal },
    props:{
      mainId:{
        type:String,
        default:'',
        required:false
      }
    },
    watch:{
      mainId:{
        immediate: true,
        handler(val) {
          if(!this.mainId){
            this.clearList()
          }else{
            this.queryParam['workOrderId'] = val
            this.loadData(1);
            this.initDictConfig();
          }
        }
      }
    },
    data () {
      return {
        description: '工单信息管理页面',
        disableMixinCreated:true,
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'设备编号',
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
            title:'服务工程师',
            align:"center",
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
            dataIndex: 'dispatchTime'
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
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/workorder/workOrder/listWorkOrderDetailByMainId",
          delete: "/workorder/workOrder/deleteWorkOrderDetail",
          deleteBatch: "/workorder/workOrder/deleteBatchWorkOrderDetail"
        },
        dictOptions:{
         status:[],
         type:[],
         clientId:[],
         contactId:[],
         emergencyLevel:[],
         deviceNumber:[],
         serviceEngineerName:[],
         faultLocation:[],
         peers:[]
        },

      }
    },
    methods: {
      clearList(){
        this.dataSource=[]
        this.selectedRowKeys=[]
        this.ipagination.current = 1
      },
      initDictConfig(){
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
        })
      },
      dispatch(){
        if (this.selectedRowKeys.length <= 0) {
          this.$message.warning('请选择一条记录！');
          return;
        } else {
          var ids = "";
          for (var a = 0; a < this.selectedRowKeys.length; a++) {
            ids += this.selectedRowKeys[a] + ",";
          }
        }
        this.$refs.dispatchModalForm.edit(ids);
        this.$refs.dispatchModalForm.title = "派工";
        this.$refs.dispatchModalForm.disableSubmit = false;
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
