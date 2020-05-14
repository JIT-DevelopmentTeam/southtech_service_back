import { getWorkOrderList, getAllDetail, getAllReport } from '@/api/Ticket.js'
import { getServiceWorkOrderList} from '@/api/serviceNumber.js'


const state = {
	ticketList: [],  // 钉钉工单列表
	ticketDetailList: [], // 工单明细列表
	reportList: [],	// 汇报记录列表
	serviceTicketList:[]   /* 服务号工单列表 */
}

const getters = {
	getTicketList() {
		return state.ticketList
	},
	getTicketDetailList() {
		return state.ticketDetailList
	},
	getReportList() {
		return state.reportList
	},
	getServiceTicketList(){
		return state.serviceTicketList
	}
}

const mutations = {
	setTicketList(state, payload) {
		sessionStorage.setItem('ticketList', JSON.stringify(payload))
		state.ticketList = payload
	},
	setTicketDetailList(state, payload) {
		sessionStorage.setItem('ticketDetailList', JSON.stringify(payload))
		state.ticketDetailList = payload
	},
	setReportList(state, payload) {
		sessionStorage.setItem('reportList', JSON.stringify(payload))
		state.reportList = payload
	},
	setServiceTicketList(state, payload){
		sessionStorage.setItem('serviceTicketList', JSON.stringify(payload))
		state.serviceTicketList = payload
	}
}

const actions = {
	GetDataList({commit,state}, payload){
		return new Promise((resolve, reject) => {
			getWorkOrderList(payload).then(response => {
				// commit("setTicketList",response.data.result.records)
				if (response.data.success) {
					let res = {
						list: response.data.result.records,
						total: response.data.result.total
					}
					resolve(res)
				} else {
					uni.showToast({
						title: response.data.message,
						icon: 'none',
						mask: true,
						duration: 2000
					})
				}
			}).catch(error => {
				reject(error)
			})
		})
		
	},
	GetWorkOrderDetail({commit,state}, payload) {
		return new Promise((resolve, reject) => {
			getAllDetail(payload).then(res => {
				commit("setTicketDetailList",res.data.result)
				resolve(res.data.result)
			}).catch(err => {
				console.log(error);
				reject()
			})
		})
	},
	GetReportList({commit, state}, payload) {
		return new Promise((resolve, reject) => {
			getAllReport(payload).then(res => {
				commit('setReportList', res.data.result)
				resolve(res.data.result)
			}).catch(err => {
				console.log(err);
				reject()
			})
		})
	},
	// 服务工单信息
	GetServiceDataList({commit,state}, payload){
		return new Promise((resolve, reject) => {
			getServiceWorkOrderList(payload).then(response => {
				// commit("setServiceTicketList",response.data.body.ticketList)
				let res = {
					list: response.data.result.records,
					total: response.data.result.total
				}
				resolve(res)
			}).catch(error => {
				reject(error)
			})
		})
	}
}

export default{
	namespaced: true,
	state,
	mutations,
	getters,
	actions
}