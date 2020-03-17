<template>
  <div id="components-layout-demo-basic">
    <a-layout>
      <a-layout-sider>
        <sac-card ref="ticket" title="服务工单" :list="ticketList" @checkboxChange="ticketSel"></sac-card>
        <sac-card ref="enginer" title="服务工程师" :list="enginerList" @checkboxChange="engSel"></sac-card>
      </a-layout-sider>
      <a-layout>
        <div :style="{width: '100%', height: '100%'}">
          <el-amap vid="amap" :zoom="zoom" :plugin="plugin" :center="center" :amap-manager="amapManager">
            <el-amap-marker v-for="(titem, index) in ticketMarkers" :key="index"
                            :position="[titem.longitude,titem.latitude]"
                            :vid="index" :title="titem.name" :clickable="true" :icon="customerIcon"
                            :offset="[-16, -30]" :events="titem.events"></el-amap-marker>

            <el-amap-marker v-for="(eitem, index) in enginerMarkers" :key="index"
                            :position="[eitem.longitude,eitem.latitude]"
                            :vid="index" :title="eitem.name" :clickable="true" :icon="enginerIcon"
                            :offset="[-16, -30]"></el-amap-marker>

            <el-amap-info-window v-if="window" :position="window.position" :visible="window.visible"
                                 :content="window.content"></el-amap-info-window>
          </el-amap>
        </div>
      </a-layout>
    </a-layout>
  </div>
</template>

<script>
  import {checkboxMixin} from "./checkboxMixin";
  import {AMapManager} from "vue-amap"

  let amapManager = new AMapManager();
  export default {
    name: "ServiceMap",
    mixins: [checkboxMixin],
    components: {
      sacCard: () => import('./component/card')
    },
    data() {
      let self = this;
      return {
        customerIcon: require('../../assets/customerIcon.png'),
        enginerIcon: require('../../assets/enginerIcon.png'),
        center: [0, 0],
        oldCenter: [0, 0],// 记录最初始的经纬度
        amapManager,
        zoom: 12,
        tPageNo: 1,
        ePageNo: 1,
        pageSize: 10,
        plugin: [
          {
            convert: true,           //自动偏移坐标，偏移后的坐标为高德坐标，默认：true
            buttonPosition: 'RB',    //定位按钮停靠位置，默认：'LB'，左下角
            panToLocation: true,     //定位成功后将定位到的位置作为地图中心点，默认：true
            zoomToAccuracy: true,//定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：f
            extensions: 'all',
            pName: 'Geolocation',
            events: {
              init(o) {
                // o 是高德地图定位插件实例
                o.getCurrentPosition((status, result) => {
                  if (result && result.position) {
                    let lng = result.position.lng;
                    let lat = result.position.lat;
                    self.center = [lng, lat];
                    self.oldCenter = self.center;
                    self.$nextTick();
                  }
                });
              }
            }
          },
          {
            pName: 'Scale'
          }
        ],
        ticketList: [],
        enginerList: [],
        ticketMarkers: [],
        enginerMarkers: [],
        windows: [],
        window: ''
      }
    },
    methods: {
      ticketSel(obj) {
        this.checkboxSel(obj, this.ticketMarkers);
        let map = amapManager.getMap();
        map.setFitView();
      },
      engSel(obj) {
        this.checkboxSel(obj, this.enginerMarkers);
        let map = amapManager.getMap();
        map.setFitView();
      },
      tscrollFn(e) {
        this.handleScroll(e, 'ticket');
      },
      escrollFn() {
        this.handleScroll(e, 'enginer');
      },
    },
    created() {
      this.loadTicketData(this.tPageNo)
      this.loadEnginerData(this.ePageNo)
    },
    mounted() {
      window.addEventListener('scroll', this.tscrollFn, true)
      window.addEventListener('scroll', this.escrollFn, true)
    },
    destroyed() {
      window.removeEventListener('scroll', this.tscrollFn); // 销毁监听
      window.removeEventListener('scroll', this.escrollFn); // 销毁监听
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
    height: 300px;
    width: 300px;
    border: 1px solid #f0f2f5;
  }

  #components-layout-demo-basic .ant-layout-footer {
    line-height: 1.5;
  }

  #components-layout-demo-basic .ant-layout-sider {
    background: #3ba0e9;
    color: #fff;
    line-height: 120px;
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
</style>