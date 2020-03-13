<template>
  <a-card :bordered="false">
    <a-row>
      <!-- 左侧树 -->
      <a-col :span="5">
        <a-tree
          showLine
          :treeData="treeData"
          :expandedKeys="[expandedKeys[0]]"
          :selectedKeys="selectedKeys"
          :style="{'height':'500px','border-right':'2px solid #c1c1c1','overflow-y':'auto'}"
          @expand="onExpand"
          @select="this.onSelect"
        >
        </a-tree>
      </a-col>

      <!--右侧列表-->
      <a-col :span="19" >
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
          <a-form layout="inline" @keyup.enter.native="searchQuery">
            <a-row :gutter="24">

            </a-row>
          </a-form>
        </div>
        <!-- 查询区域-END -->

        <!-- 操作按钮区域 -->
        <div class="table-operator">
          <a-button @click="sync" type="primary" icon="cloud-download">同步</a-button>
          <!--<a-button type="primary" icon="download" @click="handleExportXls('物料')">导出</a-button>
          <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
            <a-button type="primary" icon="import">导入</a-button>
          </a-upload>-->
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
            :rowSelection="{fixed:true,selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
            :scroll="tableScroll"
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

        <item-modal ref="modalForm" @ok="modalFormOk"></item-modal>
      </a-col>
    </a-row>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import ItemModal from './modules/ItemModal'

  export default {
    name: "ItemList",
    mixins:[JeecgListMixin],
    components: {
      ItemModal
    },
    data () {
      return {
        description: '物料管理页面',
        //数据集
        treeData: [{
          title: '第一页',
          key: '0-0',
          children: [{
            title: '1页',
            key: '0-0-0',
            imgUrl:'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2735633715,2749454924&fm=27&gp=0.jpg'
          }, {
            title: '2页',
            key: '0-0-1',
            imgUrl:'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3967239004,1951414302&fm=27&gp=0.jpg'
          }]
        },{
          title: '第二页',
          key: '0-1',
          children: [{
            title: '1页',
            key: '0-1-0',
            imgUrl:'https://ss0.bdstatic.com/6Ox1bjeh1BF3odCf/it/u=3660968530,985748925&fm=191&app=48&size=h300&n=0&g=4n&f=JPEG?sec=1853310920&t=5e64af964be378c6c2a3b0acc65dfe24'
          }, {
            title: '2页',
            key: '0-1-1',
            imgUrl:'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=889120611,3801177793&fm=27&gp=0.jpg'
          }]
        },{
          title: '第三页',
          key: '0-2',
          children: [{
            title: '1页',
            key: '0-2-0',
            imgUrl:'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2445468140,2491956848&fm=27&gp=0.jpg'
          }]
        }],
        expandedKeys:[],
        selectedKeys:[],
        imgList:[],
        sort:0,
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
            title:'物料名称',
            align:"center",
            dataIndex: 'name'
          },
          {
            title:'物料编码',
            align:"center",
            dataIndex: 'number'
          },
          {
            title:'规格型号',
            align:"center",
            dataIndex: 'model'
          },
          {
            title:'计量单位',
            align:"center",
            dataIndex: 'uname'
          },
          {
            title:'所在仓位',
            align:"center",
            dataIndex: 'sname'
          },
          {
            title:'保修期',
            align:"center",
            dataIndex: 'ikfperiod'
          },
          {
            title:'modifyTime',
            align:"center",
            dataIndex: 'modifytime'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/item/item/list",
          delete: "/item/item/delete",
          deleteBatch: "/item/item/deleteBatch",
          exportXlsUrl: "/item/item/exportXls",
          importExcelUrl: "item/item/importExcel",
        },
        dictOptions:{
        },
        tableScroll:{x :7*147+50}
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig(){
      },
      onExpand (expandedKeys) {
        this.expandedKeys = [];
        if(expandedKeys !== null && expandedKeys !== ''){
          this.expandedKeys[0] = expandedKeys[1];
        }
      },
      onSelect (selectedKeys, info) {
        for(var i=0;i<this.imgList.length;i++){
          if(this.imgList[i].key === selectedKeys[0]){
            this.sort = this.imgList[i].sort;
            this.setValue(this.imgList[i]);
            break;
          }
        }
      },
      sync () {

      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>