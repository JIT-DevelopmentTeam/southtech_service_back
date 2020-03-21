import Vue from 'vue'
import App from './App'
import store from './store/index.js'
import moment from 'moment'

/* 全局配置 */
Vue.config.productionTip = false

/* 钉钉配置 */
/* -------------------START-------------------- */
// 捷特
// Vue.prototype.$corpId = 'ding2ced2e5420af006835c2f4657eb6378f'	// 企业ID
// Vue.prototype.$agentId = '344468113'
// Vue.prototype.$url = 'http://120.77.40.245:8691/'

// 索奥斯
Vue.prototype.$corpId = 'dinge122d3ee13ff396f35c2f4657eb6378f'	// 企业ID
Vue.prototype.$agentId = '346116753'
Vue.prototype.$url = 'http://www.southtech.cn/H5_WEB/'	
/* -------------------END-------------------- */

/* 微信配置 */
/* -------------------START-------------------- */
Vue.prototype.$appId = 'wx35360545b97a547b'  // 微信appId
// Vue.prototype.$oauthUrl = 'http://192.168.0.106:8691/#/pages/wechat_service_num/login/index'  // 本地
// Vue.prototype.$oauthUrl = 'http://120.77.40.245:8691/#/pages/wechat_service_num/login/index'  // 捷特
Vue.prototype.$oauthUrl = 'http://www.southtech.cn/H5_WEB/#/pages/wechat_service_num/login/index'      // 索奥斯
/* -------------------END-------------------- */

moment.locale('cn')
Vue.prototype.$moment = moment
// Vue.prototype.$IP = 'http://120.77.40.245:8080/sac'//捷特
Vue.prototype.$IP = 'http://40.73.75.101:8080/sac'// 索奥斯
// Vue.prototype.$IP = 'http://192.168.0.106:8096' // 

App.mpType = 'app'

const app = new Vue({
    ...App, store
})
app.$mount()
