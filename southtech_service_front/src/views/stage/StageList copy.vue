<template>
  <a-card :bordered="false">
    <div class="gridContainer">
      <!-- 工单类型树 -->
      <div>
        <a-tree :treeData="workOrderTypeTree" @select="handleTreeSelect"></a-tree>
      </div>
      <div>
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
          <a-form layout="inline" @keyup.enter.native="searchQuery">
            <a-row :gutter="24"></a-row>
          </a-form>
        </div>
        <!-- 查询区域-END -->

        <!-- 操作按钮区域 -->
        <div class="table-operator">
          <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
          <a-button type="primary" icon="download" @click="handleExportXls('阶段信息')">导出</a-button>
          <a-upload
            name="file"
            :showUploadList="false"
            :multiple="false"
            :headers="tokenHeader"
            :action="importExcelUrl"
            @change="handleImportExcel"
          >
            <a-button type="primary" icon="import">导入</a-button>
          </a-upload>
          <a-dropdown v-if="selectedRowKeys.length > 0">
            <a-menu slot="overlay">
              <a-menu-item key="1" @click="batchDel">
                <a-icon type="delete" />删除
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
            size="middle"
            bordered
            rowKey="id"
            :columns="columns"
            :dataSource="dataSource"
            :pagination="ipagination"
            :loading="loading"
            :rowSelection="{fixed:true,selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
            :scroll="tableScroll"
            @change="handleTableChange"
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

              <a-divider type="vertical" />
              <a-dropdown>
                <a class="ant-dropdown-link">
                  更多
                  <a-icon type="down" />
                </a>
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

        <stage-modal ref="modalForm" @ok="modalFormOk"></stage-modal>
      </div>
    </div>
  </a-card>
</template>

<script>
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import StageModal from './modules/StageModal'
import { initDictOptions, filterMultiDictText } from '@/components/dict/JDictSelectUtil'

export default {
  name: 'StageList',
  mixins: [JeecgListMixin],
  components: {
    StageModal
  },
  data() {
    return {
      description: '阶段信息管理页面',
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
          title: '工单类型',
          align: 'center',
          dataIndex: 'workOrderType',
          customRender: text => {
            if (!text) {
              return ''
            } else {
              return filterMultiDictText(this.dictOptions['workOrderType'], text + '')
            }
          }
        },
        {
          title: '阶段编号',
          align: 'center',
          dataIndex: 'stageNumber'
        },
        {
          title: '阶段名称',
          align: 'center',
          dataIndex: 'stageName'
        },
        {
          title: '累计百分比',
          align: 'center',
          dataIndex: 'cumulativePercentage'
        },
        {
          title: '排序',
          align: 'center',
          dataIndex: 'orderIndex'
        },
        {
          title: '操作规程',
          align: 'center',
          dataIndex: 'operatingProcedures',
          customRender: text => {
            if (!text) {
              return ''
            } else {
              return filterMultiDictText(this.dictOptions['operatingProcedures'], text + '')
            }
          }
        },
        {
          title: '需要签到',
          align: 'center',
          dataIndex: 'checkIn'
        },
        {
          title: '需要签出',
          align: 'center',
          dataIndex: 'checkOut'
        },
        {
          title: '需要拍照',
          align: 'center',
          dataIndex: 'takePicture'
        },
        {
          title: '需要费用模板',
          align: 'center',
          dataIndex: 'costTemplate'
        },
        {
          title: '需要原件归档',
          align: 'center',
          dataIndex: 'archive'
        },
        {
          title: '资料状态',
          align: 'center',
          dataIndex: 'stateInformation'
        },
        {
          title: '触发消息',
          align: 'center',
          dataIndex: 'triggerMessage'
        },
        {
          title: '触发应收',
          align: 'center',
          dataIndex: 'triggerReceivable'
        },
        {
          title: '提交附件',
          align: 'center',
          dataIndex: 'attachment'
        },
        {
          title: '工作说明',
          align: 'center',
          dataIndex: 'jobDescription',
          scopedSlots: { customRender: 'htmlSlot' }
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          fixed: 'right',
          width: 147,
          scopedSlots: { customRender: 'action' }
        }
      ],
      url: {
        list: '/stage/stage/list',
        delete: '/stage/stage/delete',
        deleteBatch: '/stage/stage/deleteBatch',
        exportXlsUrl: '/stage/stage/exportXls',
        importExcelUrl: 'stage/stage/importExcel'
      },
      dictOptions: {
        workOrderType: [],
        operatingProcedures: []
      },
      tableScroll: { x: 16 * 147 + 50 }
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    },
    workOrderTypeTree: function() {
      let result = []
      this.dictOptions.workOrderType.forEach(t => {
        result.push({
          key: t.value,
          title: t.title
        })
      })
      return result
    }
  },
  methods: {
    initDictConfig() {
      initDictOptions('work_order_type').then(res => {
        if (res.success) {
          this.$set(this.dictOptions, 'workOrderType', res.result)
        }
      })
      initDictOptions('tb_operating_procedures,name,id').then(res => {
        if (res.success) {
          this.$set(this.dictOptions, 'operatingProcedures', res.result)
        }
      })
    },
    handleTreeSelect(selectedKeys,e){
      
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';

.gridContainer {
  display: grid;
  grid-template-columns: 10% 80%;
}
</style>