<template>
  <div class="card">
    <div :style="{ borderBottom: '1px solid #E9E9E9', height: '50px' }">
      <a-row>
        <a-col :span="8" style="margin-left: 10px;">
          <a-button type="danger">关闭工单</a-button>
        </a-col>
      </a-row>
    </div>
    <div class="content" @scroll="hscroll($event)">
      <a-spin :spinning="spinning">
        <div id="card" @click="cardClick(item)" v-for="(item, index) in showList" :key="index">
          <a-card v-bind:class="{actived:item.id===activeItem.id}">
            <a-row>
              <a-col :span="12">
                <span class="title">{{ workTypeText.getText( item.type ) }}</span>
              </a-col>
              <a-col :span="12" class="status">
                <span>{{ workEmLevelText.getText( item.emergencyLevel ) }}</span>
              </a-col>
            </a-row>
            <a-row>
              <a-col :span="24">
                <a-icon type="crown" theme="filled"/>
                <span>{{ item.clientName }}</span>
              </a-col>
            </a-row>
            <a-row>
              <a-col :span="18">
                <span>报障时间：{{ item.declarationTime }}</span>
              </a-col>
              <a-col :span="6">
                <span class="time">过去4小时</span>
              </a-col>
            </a-row>
            <a-row>
              <a-col :span="24">
                <a-icon type="environment" theme="filled"/>
                <span>{{ item.province + item.city + item.area + item.community + item.address }}</span>
              </a-col>
            </a-row>
          </a-card>
        </div>
      </a-spin>
    </div>
  </div>
</template>

<script>
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import { getAction } from '@/api/manage'

  export default {
    name: "listCard",
    data() {
      return {
        workTypeDic: [],
        workEmLevelDic: [],
        activeItem: {},
        workTypeText: {
          getText: (type) => {
            return filterMultiDictText(this.workTypeDic, type)
          }
        },
        workEmLevelText: {
          getText: (type) => {
            return filterMultiDictText(this.workEmLevelDic, type)
          }
        },
        pageNo: 1,
        ticListUrl: '/workorder/workOrder/queryWorkOrderList',
        ticketList: [],
        spinning: false,
        total: 0
      }
    },
    mounted() {
      this.initDict()
    },
    props: {
      value: {
        type: Object
      },
      orderType: {
        type: String
      }
    },
    methods: {
      cardClick(item) {
        this.activeItem = item;
        this.$emit('input', item)
      },
      initDict() {
        initDictOptions('work_order_type')
          .then(res => {
            this.workTypeDic = res.result;
          })
        initDictOptions('work_order_emergency_level')
          .then(res => {
            this.workEmLevelDic = res.result;
          })
      },
      hscroll(e) {
        //变量scrollTop是滚动条滚动时，距离顶部的距离
        var scrollTop = parseInt(e.target.scrollTop);
        //变量windowHeight是可视区的高度
        var windowHeight = e.target.clientHeight;
        //变量scrollHeight是滚动条的总高度
        var scrollHeight = e.target.scrollHeight;
        var loading = false;
        //滚动条到底部的条件
        if (scrollTop + windowHeight === scrollHeight && scrollTop !== 0) {
          if (this.total != this.showList.length) {
            if (loading) return;
            loading = true;
            this.spinning = true;
            //写后台加载数据的函数
            console.log("距顶部" + scrollTop + "可视区高度" + windowHeight + "滚动条总高度" + scrollHeight);
            setTimeout(() => {
              this.pageNo += 1;
              this.loadTicket(this.orderType, this.pageNo);
              loading = false;
              this.spinning = false;
            }, 2000)
          }

        }
      },
      loadTicket(type, pageNo) {
        this.pageNo = pageNo;
        getAction(this.ticListUrl, {type: type, pageNo: this.pageNo})
          .then(res => {
            if (res.success) {
              console.log(res)
              this.total = res.result.total;
              let result = res.result.records;
              result.forEach(item => {
                this.ticketList.push(item);
              })
            }
            if(res.code===510){
              this.$message.warning(res.message)
            }
          })
      }
    },
    computed: {
      showList() {
        if (!this.orderType) {
          return this.ticketList
        }
        return this.ticketList.filter(e => e.type === this.orderType);
      },
    },
    created() {
      this.loadTicket('', this.pageNo);
    }
  }
</script>

<style scoped>
  .title {
    font-weight: bold;
    font-size: larger;
  }

  .status {
    text-align: right;
  }

  .time {
    color: #f5222d;
    font-size: xx-small;
  }

  #card .ant-card .ant-card-body {
    padding: 15px !important;
  }

  .actived {
    background: #e8e8e8;
  }

  .card {
    border: 1px solid rgb(233, 233, 233);
    width: 300px;
    height: 500px;
    text-align: left;
  }

  .content {
    height: 450px;
    overflow: auto;
  }
</style>