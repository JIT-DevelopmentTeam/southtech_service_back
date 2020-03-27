<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24"></a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <!-- <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button> -->
      <a-button type="primary" icon="download" @click="handleExportXls('回访记录')">导出</a-button>
      <!-- <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>-->
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
    <!-- <serviceVisits-modal ref="modalForm" @ok="modalFormOk"></serviceVisits-modal> -->
  </a-card>
</template>

<script>
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
// import ServiceVisitsModal from './modules/ServiceVisitsModal'
import { initDictOptions, filterMultiDictText } from '@/components/dict/JDictSelectUtil'

export default {
  name: 'ServiceVisitsList',
  mixins: [JeecgListMixin],
  components: {
    // ServiceVisitsModal
  },
  props: {
    workOrderNum: {
      type: String
    }
  },
  watch: {
    workOrderNum: {
      immediate: true,
      handler(val) {
        if (!this.workOrderNum) {
          this.clearList()
        } else {
          this.queryParam['workOrderId'] = val
          this.loadData(1)
          this.initDictConfig()
        }
      }
    }
  },
  data() {
    return {
      description: '回访记录管理页面',
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
          title: '是否完成',
          align: 'center',
          dataIndex: 'isCompleted'
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
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' }
        }
      ],
      url: {
        list: '/servicevisits/serviceVisits/list',
        delete: '/servicevisits/serviceVisits/delete',
        deleteBatch: '/servicevisits/serviceVisits/deleteBatch',
        exportXlsUrl: '/servicevisits/serviceVisits/exportXls',
        importExcelUrl: 'servicevisits/serviceVisits/importExcel'
      },
      dictOptions: {
        visitWay: [],
        customer: []
      }
    }
  },

  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  methods: {
    initDictConfig() {
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
    },
    onSelectChange(selectedRowKeys, selectionRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectionRows = selectionRows
      if (this.selectionRows.lenght === 1) {
        this.$emit('change', this.selectionRows[0])
      }
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>