<template>
  <a-popover trigger="click" v-model="visible" placement="rightTop">
    <template slot="content">
      <div style="width: 110px; height: 200px; overflow: auto;">
        <a-radio-group @change="onChange" v-model="radioValue">
          <a-radio :style="radioStyle" :value="item.userName" v-for="(item, index) in enginerList" :key="index">{{
            item.realname }}
          </a-radio>
        </a-radio-group>
      </div>
      <a-divider/>
      <a-button type="primary" @click="dispatch">派工</a-button>
    </template>
    <div class="card">
      <div class="content" @scroll="hscroll($event)">
        <a-spin :spinning="spinning">
          <div id="card" @click="cardClick(item)" v-for="(item, index) in showList" :key="index">
            <a-card v-bind:class="{actived:item.detailId===activeItem.detailId}">
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
                  <span>工单编号：{{ item.number }}</span>
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
                  <span class="time">{{ calTime(item.declarationTime) }}</span>
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
    <dis-patch-modal ref="dispatchModalForm" @ok="modalFormOk"></dis-patch-modal>
  </a-popover>
  <!--<div class="card">
    &lt;!&ndash;    <div :style="{ borderBottom: '1px solid #E9E9E9', height: '50px' }">&ndash;&gt;
    &lt;!&ndash;      <a-row>&ndash;&gt;
    &lt;!&ndash;        <a-col :span="8" style="margin-left: 10px;">&ndash;&gt;
    &lt;!&ndash;          <a-button type="danger">关闭工单</a-button>&ndash;&gt;
    &lt;!&ndash;        </a-col>&ndash;&gt;
    &lt;!&ndash;      </a-row>&ndash;&gt;
    &lt;!&ndash;    </div>&ndash;&gt;
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
  </div>-->
</template>

<script>
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import {getAction} from '@/api/manage'
  import {calculationTime} from '@/utils/moment'

  export default {
    name: "orderListCard",
    components: {
      DisPatchModal: () => import('@/views/workorder/modules/JitDispatchModal')
    },
    data() {
      return {
        radioValue: 1,
        radioStyle: {
          display: 'block',
          height: '30px',
          lineHeight: '30px',
        },
        visible: false,
        // ---------------------------------
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
      },
      enginerList: {
        type: Array
      },
    },
    methods: {
      modalFormOk() {
        this.activeItem = {}
        this.$emit('input', this.activeItem);
        this.ticketList = [];
        this.pageNo = 1;
        this.loadTicket('', this.pageNo);
      },
      dispatch() {
        this.visible = false;
        var ids = this.activeItem.detailId;
        var record = {
          ids: ids,
          serviceEngineerName: this.radioValue
        }
        this.$refs.dispatchModalForm.edit(record);
        this.$refs.dispatchModalForm.title = "派工";
        this.$refs.dispatchModalForm.disableSubmit = false;
      },
      onChange(e) {
        this.radioValue = e.target.value;
        console.log('radio checked', e.target.value);
      },
      // ------------------------------------------
      cardClick(item) {
        let enginerList = this.enginerList;
        let p1 = [item.longitude, item.latitude];
        enginerList.sort(function (a, b) {
          return AMap.GeometryUtil.distance(p1, [a.longitude, a.latitude]) - AMap.GeometryUtil.distance(p1, [b.longitude, b.latitude])
        })
        if (enginerList.length > 0) {
          this.radioValue = enginerList[0].userName;
        }

        this.visible = false;
        setTimeout(function () {
          this.visible = true;
        }, 500)

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
        this.spinning = true;
        this.pageNo = pageNo;
        getAction(this.ticListUrl, {type: type, pageNo: this.pageNo})
          .then(res => {
            if (res.success) {
              this.total = res.result.total;
              let result = res.result.records;
              result.forEach(item => {
                this.ticketList.push(item);
              })
            }
            if (res.code === 510) {
              this.$message.warning(res.message)
            }
            this.spinning = false;
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
      calTime(dataTime) {
        return dataTime => {
          return calculationTime(dataTime);
        }
      }
    },
    created() {
      this.loadTicket('', this.pageNo);
      console.log(this)
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
    height: 500px;
    overflow: auto;
  }

  .ant-divider-horizontal {
    margin: 10px 0 !important;
  }
</style>