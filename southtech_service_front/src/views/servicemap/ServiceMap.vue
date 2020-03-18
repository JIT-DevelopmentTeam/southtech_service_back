<template>
  <div id="components-layout-demo-basic">
    <a-layout>
      <a-layout-sider>
        <sac-card title="服务工单" :list="ticketList" @checkboxChange="ticketSel"></sac-card>
        <sac-card title="服务工程师" :list="enginerList" @checkboxChange="engSel"></sac-card>
      </a-layout-sider>
      <a-layout>
        <div :style="{width: '100%', height: '100%'}">
          <el-amap vid="amap" :zoom="11" :plugin="plugin" :center="center">
            <el-amap-marker v-for="(titem, index) in ticketMarkers" :key="index" :position="titem.position"
                            :vid="index" :title="titem.name" :clickable="true" icon="~@/assets/customerLocal.png"></el-amap-marker>
            <el-amap-marker v-for="(eitem, index) in enginerMarkers" :key="index" :position="eitem.position"
                            :vid="index" :title="eitem.name" :clickable="true" icon="~@/assets/enginerLocal.png"></el-amap-marker>
          </el-amap>
        </div>
      </a-layout>
    </a-layout>
  </div>
</template>

<script>
  export default {
    name: "ServiceMap",
    components: {
      sacCard: () => import('./component/card')
    },
    data() {
      let self = this;
      return {
        center: [0, 0],
        plugin: [{
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
                  self.$nextTick();
                }
              });
            }
          }
        },
        {
          pName: 'Scale'
        }],
        ticketList: [
          {
            name: '佛山创意产业园',
            position: [113.097795,23.013805],
            address: '广东省佛山市禅城区张槎街道19 Megacera佛山创意产业园(季华四路)',
            checked: false
          },
          {
            name: '佛山新DNA',
            position: [113.107846,22.993916],
            address: '广东省佛山市禅城区石湾镇街道天御尚城',
            checked: false
          },
          {
            name: '乐从镇天佑城',
            position: [113.107514,22.947229],
            address: '广东省佛山市乐从镇',
            checked: false
          },
          {
            name: 'D',
            position: [],
            address: '',
            checked: false
          },
          {
            name: 'E',
            position: [],
            address: '',
            checked: false
          }
        ],
        enginerList: [
          {
            name: 'F',
            position: [],
            address: '',
            checked: false
          },
          {
            name: 'G',
            position: [],
            address: '',
            checked: false
          },
          {
            name: 'H',
            position: [],
            address: '',
            checked: false
          },
          {
            name: 'I',
            position: [],
            address: '',
            checked: false
          },
          {
            name: 'J',
            position: [],
            address: '',
            checked: false
          }
        ],
        ticketMarkers: [],
        enginerMarkers: []
      }
    },
    methods: {
      ticketSel(obj) {
        console.log('checked = ticket --> ', obj)
        if (obj.checked) {
          this.ticketMarkers.push(obj);
        } else {
          let index = this.ticketMarkers.indexOf(obj);
          if (index > -1) {
            this.ticketMarkers.splice(index, 1);
          }
        }
      },
      engSel(obj) {
        console.log('checked = eng --> ', obj)
      }
    },
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