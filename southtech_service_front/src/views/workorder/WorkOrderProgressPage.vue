<template>
  <a-card :bordered="false" :class="'cust-erp-sub-tab'">

    <!-- table区域-begin -->
    <div>
      <a-steps :current="finishIndex"  size="small">
        <a-step v-for="progress in progressList" :title="progress.name" :key="progress.id">
          <span slot="description">{{progress.finishTime}}</span>
        </a-step>
      </a-steps>
    </div>

  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import { getAction } from '@/api/manage'

  export default {
    name: "WorkOrderProgressPage",
    mixins:[JeecgListMixin],
    components: {},
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
            this.loadProgress(val);
          }
        }
      }
    },
    data () {
      return {
        description: '工单信息管理页面',
        disableMixinCreated:true,
        finishIndex:0,
        progressList:[],
        url: {
          list: "/workorder/workOrder/queryWorkOrderProgressByMainId"
        },
        dictOptions:{
        },

      }
    },
    methods: {
      clearList(){
        this.dataSource=[]
        this.selectedRowKeys=[]
        this.ipagination.current = 1
      },
      loadProgress(id) {
        getAction(this.url.list,{id:id}).then((res) => {
          if (res.success) {
            this.progressList = res.result;
            if (res.result) {
              for (let i=res.result.length - 1;i >= 0; i--) {
                if (res.result[i].finishTime) {
                  this.finishIndex = i + 1;
                  break;
                }
              }
            }
          }
        });
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
