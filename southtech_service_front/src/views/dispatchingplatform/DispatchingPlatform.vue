<template>
  <div id="components-layout-demo-basic">
    <a-layout>
      <a-layout-header>
        <a-row>
          <a-col :span="12" style="text-align: left;">
            <a-select placeholder="请选择工单类型" style="width: 200px" @change="handleChange" v-if="value == 1"
                      :allowClear="true">
              <a-select-option v-for="(item, index) in dictList" :key="index" :value="item.value">{{ item.text }}
              </a-select-option>
            </a-select>
          </a-col>
          <a-col :span="12" style="text-align: right;">
            <a-radio-group @change="onChange" v-model="value">
              <a-radio :value="1">地图</a-radio>
              <a-radio :value="2">甘特图</a-radio>
            </a-radio-group>
          </a-col>
        </a-row>
      </a-layout-header>
      <a-divider style="margin: 0 0 !important;"/>
      <a-layout>
        <a-layout-sider v-show="value == 1">
          <order-list-card v-model="selectdWorkOrder" ref="workOrderList" :orderType="orderType"
                           :enginerList="enginerMarkers"></order-list-card>
        </a-layout-sider>
        <a-divider type="vertical" style="margin: 0 0 !important;"/>
        <a-layout>
          <div :style="{width: '100%', height: '100%'}" v-show="value == 1">
            <el-amap vid="dispathingmap" :zoom="zoom" :plugin="plugin" :center="center" :amap-manager="amapManager">
              <el-amap-marker v-if="eitem.longitude != null && eitem.latitude != null"
                              v-for="(eitem, index) in enginerMarkers" :key="index"
                              :position="[eitem.longitude,eitem.latitude]"
                              :vid="index" :title="'工程师：' + eitem.realname" :clickable="true" :icon="enginerIcon"
                              :offset="[-16, -30]"></el-amap-marker>

              <el-amap-marker
                v-if="JSON.stringify(this.selectdWorkOrder) != '{}' && selectdWorkOrder.longitude != null && selectdWorkOrder.latitude != null"
                :position="[selectdWorkOrder.longitude,selectdWorkOrder.latitude]"
                :vid="selectdWorkOrder.id" :title="selectdWorkOrder.name" :clickable="true"
                :icon="customerIcon"
                :offset="[-16, -30]"
                animation="AMAP_ANIMATION_BOUNCE"></el-amap-marker>
            </el-amap>
          </div>
          <div :style="{width: '100%', height: '100%'}" v-show="value == 2">
            <Gantt ref="myGantt" class="left-container" :tasks="tasks"></Gantt>
          </div>
        </a-layout>
      </a-layout>
    </a-layout>
  </div>
</template>

<script>
  import {AMapManager} from "vue-amap"
  import {getAction} from '@/api/manage'

  let amapManager = new AMapManager();
  export default {
    name: "DispatchingPlatform",
    data() {
      let self = this;
      return {
        customerIcon: require('../../assets/customerIcon.png'),
        enginerIcon: require('../../assets/enginerIcon.png'),
        value: 1,
        zoom: 5,
        center: [109.12014, 32.441281],
        amapManager,
        selectdWorkOrder: {},
        plugin: [
          {
            pName: 'Scale'
          }
        ],
        enginerMarkers: [],
        tasks: {
          data: [],
        },
        dictList: [],
        orderType: '',
        url: {
          listTaskByEngineer: "/gantttask/gantttask/listTaskByEngineer",
          dictList: '/sys/dict/getDictItems/',
          enginerList: '/sys/user/enginerList'
        }
      }
    },
    components: {
      orderListCard: () => import('./component/orderListCard')
    },
    methods: {
      handleChange(value) {
        console.log(`selected ${value}`);
        this.orderType = value ? value : '';
        this.$refs.workOrderList.ticketList = [];
        this.$refs.workOrderList.loadTicket(this.orderType, 1);
      },
      onChange(e) {
        console.log('radio checked', e.target.value);
        if (e.target.value == 2) {
          getAction(this.url.listTaskByEngineer, null).then((res) => {
            if (res.success) {
              this.tasks.data = res.result;
              gantt.init(this.$refs.myGantt.$refs.gantt);
              gantt.parse(this.tasks);
            } else {
              this.$message.error(res.message);
            }
          })
        }
      },
      loadDict(type) {
        getAction(this.url.dictList + type)
          .then(res => {
            if (res.success) {
              this.dictList = res.result
            }
            if (res.code === 510) {
              this.$message.warning(res.message)
            }
          })
      },
      loadEnginer() {
        getAction(this.url.enginerList)
          .then(res => {
            if (res.success) {
              this.enginerMarkers = res.result.records
            }
            if (res.code === 510) {
              this.$message.warning(res.message)
            }
          })
      }
    },
    created() {
      this.loadDict('work_order_type');
      this.loadEnginer();
    }
  }
</script>

<style scoped>
  #components-layout-demo-basic {
    text-align: center;
  }

  #components-layout-demo-basic .ant-layout-header,
  #components-layout-demo-basic .ant-layout-footer {
    background: #fff;
    color: #000;
  }

  #components-layout-demo-basic .ant-layout-footer {
    line-height: 1.5;
  }

  #components-layout-demo-basic .ant-layout-sider {
    background: #fff;
    color: #000;
    /*line-height: 120px;*/
    line-height: 50px;
    flex: none !important;
    max-width: 300px !important;
    min-width: 300px !important;
    width: 300px !important;
  }

  #components-layout-demo-basic .ant-layout-content {
    background: #fff;
    color: #000;
    min-height: 120px;
    line-height: 120px;
  }

  #components-layout-demo-basic > .ant-layout {
    margin-bottom: 48px;
  }

  #components-layout-demo-basic > .ant-layout:last-child {
    margin: 0;
  }

  #components-layout-demo-basic .ant-layout-sider .ant-card .ant-card-body {
    height: 270px;
  }

  .container {
    height: 100%;
    width: 100%;
  }

  .left-container {
    overflow: hidden;
    position: relative;
    height: 500px;
  }
</style>