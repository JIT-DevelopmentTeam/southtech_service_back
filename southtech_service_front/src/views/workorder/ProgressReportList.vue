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
    
    <!-- 操作按钮区域 -->
    <!-- <div class="table-operator">
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div> -->

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

        <span slot="action" slot-scope="text, record">
          <a @click="annexList(record)">附件</a>

          <!-- <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown> -->
        </span>

      </a-table>
    </div>
    <file-list ref="fileList"></file-list>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import FileList from './FileList'

  export default {
    name: "ProgressReportList",
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
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'所属进度',
            align:"center",
            dataIndex: 'progressId',
            customRender:(text)=>{
              console.log(text);
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['progressName'], text+"")
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
            title:'描述',
            align:"center",
            dataIndex: 'description'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/progressreport/progressReport/list",
          delete: "/progressreport/progressReport/delete",
          deleteBatch: "/progressreport/progressReport/deleteBatch",
          exportXlsUrl: "/progressreport/progressReport/exportXls",
          importExcelUrl: "progressreport/progressReport/importExcel",
        },
        dictOptions:{
         progressName:[],
         faultLocation:[]
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
        initDictOptions('ic_work_order_progress,name,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'progressName', res.result)
          }
        });
        initDictOptions('work_order_detail_fault_location').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'faultLocation', res.result)
          }
        })
      },
      annexList(record) {
        this.$refs.fileList.showModal()
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