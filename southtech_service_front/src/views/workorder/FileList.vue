<template>
 <a-modal
      title="附件"
      :width="width"
      :visible="visible"
      @ok="ok"
      @cancel="cancel"
    >
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
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('文件表')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
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
        :visible="visible"
        :rowSelection="{fixed:true,selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        
        @change="handleTableChange">

         <template slot="htmlSlot" slot-scope="text">
            <span v-if="(text.indexOf('jpg') >= 0 || text.indexOf('png') >= 0) && !text" style="font-size: 12px;font-style: italic;">无此图片</span>
            <div v-else-if="text.indexOf('jpg') >= 0 || text.indexOf('png') >= 0" style="width:300px;display: inline-block;">
              <div
                style="width: 100%;height: 100%;padding: 8px;border: 1px solid #d9d9d9;border-radius: 4px;">
                <img style="width: 100%;height: 100%;" :src="getImgView(text)" alt="图片不存在"/>
              </div>
            </div>
            <!-- <img  height="25px" alt="图片不存在" style="max-width:80px;font-size: 12px;font-style: italic;"/> -->
            <span v-if="(text.indexOf('jpg') <= 0 || text.indexOf('png') <= 0) && !text" style="font-size: 12px;font-style: italic;">无此文件</span>
              <a-button
                v-else-if="text.indexOf('jpg') < 0 && text.indexOf('png') < 0"
                :ghost="true"
                type="primary"
                icon="download"
                size="small"
                @click="uploadFile(text)">
                下载
              </a-button>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此图片</span>
          <img v-else :src="getImgView(text)" height="100px" alt="图片不存在" style="max-width:80px;font-size: 12px;font-style: italic;"/>
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

        <!-- <span slot="action" slot-scope="text, record">
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
        </span> -->

      </a-table>
    </div>
  </a-card>
    </a-modal>
  
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: "FileList",
    mixins:[JeecgListMixin],
    components: {
    },
    data () {
      return {
        width:1200,
        visible:false,
        ModalText:'附件',
        description: '文件表管理页面',
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
            title:'类型',
            align:"center",
            dataIndex: 'type',
            customRender:function(text) {
              let typeText;
              switch (text) {
                case 'Photo':
                  typeText = '图片';
                  break;
                case 'File':
                  typeText = '文件';
                  break;
              }
              return typeText;
            }
          },
          {
            title:'附件',
            align:"center",
            dataIndex: 'url',
            scopedSlots: {customRender: 'htmlSlot'}
          },
          // {
          //   title: '操作',
          //   dataIndex: 'action',
          //   align:"center",
          //   scopedSlots: { customRender: 'action' }
          // }
        ],
        url: {
          list: "/file/file/list",
          delete: "/file/file/delete",
          deleteBatch: "/file/file/deleteBatch",
          exportXlsUrl: "/file/file/exportXls",
          importExcelUrl: "file/file/importExcel",
        },
        dictOptions:{
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
      },
      showModal(record) {
        this.queryParam['progressReportId'] = record.id;
        this.initDictConfig();
        this.loadData(1);
        this.visible = true;

      },
      ok(e) {
        this.visible = false;
      },
      cancel(e) {
        this.visible = false;
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>