<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->
    
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
        :rowSelection="{fixed:true,selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        
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

      </a-table>
    </div>
    <file-list ref="fileList"></file-list>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: "ServiceVisitsList",
    mixins:[JeecgListMixin],
    components: {
      FileList
    },
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
            this.clearList();
          }else{
            this.queryParam['workOrderId'] = val
            this.initDictConfig();
            this.loadData(1);
          }
        }
      }
    },
    data () {
      return {
        disableMixinCreated:true,
        description: '进度汇报管理页面',
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
        {
          title: '回访人',
          align: 'center',
          dataIndex: 'visitPeople',
          customRender: text => {
            if (!text) {
              return ''
            } else {
              return filterMultiDictText(this.dictOptions['visitPeople'], text + '')
            }
          }
        },
        {
          title: '回访时间',
          align: 'center',
          dataIndex: 'visitTime'
        },
        {
          title: '客户',
          align: 'center',
          dataIndex: 'customer',
          customRender: text => {
            if (!text) {
              return ''
            } else {
              return filterMultiDictText(this.dictOptions['customer'], text + '')
            }
          }
        },
        {
          title: '受访人',
          align: 'center',
          dataIndex: 'respondents'
        },
        {
          title: '完成状态',
          align: 'center',
          dataIndex: 'isCompleted',
          customRender: text => {
            if (!text) {
              return ''
            } else {
              return filterMultiDictText(this.dictOptions['isCompleted'], text + '')
            }
          }
        },
        {
          title: '回访评分',
          align: 'center',
          dataIndex: 'score'
        },
        {
          title: '回访方式',
          align: 'center',
          dataIndex: 'visitWay',
          customRender: text => {
            if (!text) {
              return ''
            } else {
              return filterMultiDictText(this.dictOptions['visitWay'], text + '')
            }
          }
        },
        {
          title: '联系电话',
          align: 'center',
          dataIndex: 'contact'
        },
        {
          title: '回访意见',
          align: 'center',
          dataIndex: 'opinion',
          scopedSlots: { customRender: 'htmlSlot' }
        },
        ],
        url: {
          list: '/servicevisits/serviceVisits/list',
          delete: '/servicevisits/serviceVisits/delete',
          deleteBatch: '/servicevisits/serviceVisits/deleteBatch',
          exportXlsUrl: '/servicevisits/serviceVisits/exportXls',
          importExcelUrl: 'servicevisits/serviceVisits/importExcel'
        },
        dictOptions:{
          visitWay: [],
          customer: [],
          isCompleted:[]
        },
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig(){
        initDictOptions('service_visits_way').then(res => {
          if (res.success) {
            this.$set(this.dictOptions, 'visitWay', res.result)
          }
        })
        initDictOptions('tb_client,name,id').then(res => {
          if (res.success) {
            this.$set(this.dictOptions, 'customer', res.result)
          }
        })
        initDictOptions('sys_user,realname,username').then(res => {
          if (res.success) {
            this.$set(this.dictOptions, 'visitPeople', res.result)
          }
        })
        initDictOptions('service_visits_is_completed').then(res => {
          if (res.success) {
            this.$set(this.dictOptions, 'isCompleted', res.result)
          }
        })
      },
      clearList(){
        this.dataSource=[]
        this.selectedRowKeys=[]
        this.ipagination.current = 1
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>