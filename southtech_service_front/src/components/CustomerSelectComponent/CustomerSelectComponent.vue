<template>
  <div>
    <a-button type @click="visible=true" icon="user" :disabled="disable" @change="handleSearch">{{butonText}}</a-button>
    <a-modal width="60vw" :visible="visible" title="选择客户" @cancel="handleCancel" @ok="handleOK">
      <div class="searchContainer">
        <a-input placeholder="输入关键词搜索" v-model="keyword" @change="handleSearch"></a-input>
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
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange, type:rowSelectionType}"
        :customRow="clickThenSelect"
        @change="handleTableChange"
      ></a-table>
    </a-modal>
  </div>
</template>

<script>
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { initDictOptions, filterMultiDictText } from '@/components/dict/JDictSelectUtil'
export default {
  name: 'CustomerSelectComponent',
  mixins: [JeecgListMixin],
  data() {
    return {
      selectedRows: [],
      dictOptions: {
        type: [],
        sourceId: [],
        industry: [],
        property: []
      },
      /* 分页参数 */
      ipagination: {
        current: 1,
        pageSize: 5,
        pageSizeOptions: ['5', '10', '50'],
        showTotal: (total, range) => {
          return range[0] + '-' + range[1] + ' 共' + total + '条'
        },
        showQuickJumper: true,
        showSizeChanger: true,
        total: 0
      },
      selectedMainId: '',
      keyword: '',
      visible: false,
      url: {
        list: '/client/client/list'
      },
      columns: [
        {
          title: '编码',
          align: 'center',
          dataIndex: 'number'
        },
        {
          title: '名称',
          align: 'center',
          dataIndex: 'name'
        },
        {
          title: '类型',
          align: 'center',
          dataIndex: 'type',
          customRender: text => {
            if (!text) {
              return ''
            } else {
              return filterMultiDictText(this.dictOptions['type'], text + '')
            }
          }
        },
        {
          title: '来源',
          align: 'center',
          dataIndex: 'sourceId',
          customRender: text => {
            if (!text) {
              return ''
            } else {
              return filterMultiDictText(this.dictOptions['sourceId'], text + '')
            }
          }
        },
        {
          title: '所属用户',
          align: 'center',
          dataIndex: 'userId'
        },
        {
          title: '最近联系时间',
          align: 'center',
          dataIndex: 'lastContactTime'
        },
        {
          title: '行业',
          align: 'center',
          dataIndex: 'industry',
          customRender: text => {
            if (!text) {
              return ''
            } else {
              return filterMultiDictText(this.dictOptions['industry'], text + '')
            }
          }
        },
        {
          title: '性质',
          align: 'center',
          dataIndex: 'property',
          customRender: text => {
            if (!text) {
              return ''
            } else {
              return filterMultiDictText(this.dictOptions['property'], text + '')
            }
          }
        }
      ]
    }
  },
  props: {
    value: {
      type: Object | Array
    },
    multiSelect: {
      type: Boolean,
      default: () => {
        return false
      }
    },
    disable:{
        type:Boolean
    }
  },
  computed: {
    rowSelectionType() {
      return this.multiSelect ? 'checkbox' : 'radio'
    },
    butonText() {
      let title = '选择客户'
      const result = []
      if (this.selectedRows && this.selectedRows.length > 0) {
        this.selectedRows.forEach(row => {
          result.push(row.name)
        })
        title = result.join('，')
      }

      return title
    }
  },
  methods: {
    show() {
      this.selectedRows = []
      this.loadData()
      this.visible = true
    },
    handleSearch() {
      this.queryParam.name = this.keyword
      this.loadData()
    },
    handleOK() {
      let result = this.multiSelect ? this.selectedRows : this.selectedRows[0]
      result = result || {}
      this.$emit('input', result)
      this.triggerChange(result)
      this.visible = false
    },
    handleCancel(){
        this.visible=false
        this.selectedRows=[]
    },
    triggerChange(value){
        this.$emit('change',Object.assign({},this.$data,value))
    },
    onSelectChange(selectedRowKeys, selectionRows) {
      this.selectedMainId = selectedRowKeys[0]
      this.selectedRowKeys = selectedRowKeys

      let addrows = selectionRows.filter(r => !this.selectedRows.includes(r))
        
      this.selectedRows = this.multiSelect&&this.selectedRows.length>0? this.selectedRows.concat(addrows):selectionRows;
    },
    // onClearSelected() {
    //   this.selectedRowKeys = []
    //   this.selectedRows = []
    //   this.selectedMainId = ''
    // },
    clickThenSelect(record) {
      return {
        on: {
          click: () => {
            this.onSelectChange(record.id.split(','), [record])
          }
        }
      }
    },
    initDictConfig() {
      initDictOptions('client_type').then(res => {
        if (res.success) {
          this.$set(this.dictOptions, 'type', res.result)
        }
      })
      initDictOptions('client_source').then(res => {
        if (res.success) {
          this.$set(this.dictOptions, 'sourceId', res.result)
        }
      })
      initDictOptions('client_industry').then(res => {
        if (res.success) {
          this.$set(this.dictOptions, 'industry', res.result)
        }
      })
      initDictOptions('client_property').then(res => {
        if (res.success) {
          this.$set(this.dictOptions, 'property', res.result)
        }
      })
    }
  }
}
</script>

<style scoped>
.searchContainer {
  margin-bottom: 10px;
}
</style>