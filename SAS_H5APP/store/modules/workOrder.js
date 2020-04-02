import { getWorkOrderList, getAllDetail } from '@/api/Ticket.js'
import { getServiceWorkOrderList} from '@/api/serviceNumber.js'


const state = {
	ticketList: [],  // 钉钉工单列表
	ticketDetailList: [], // 工单明细列表
	serviceTicketList:[]   /* 服务号工单列表 */
}

const getters = {
	getTicketList() {
		return state.ticketList
	},
	getTicketDetailList() {
		return state.ticketDetailList
	},
	getServiceTicketList(){
		return state.serviceTicketList
	}
}

const mutations = {
	setTicketList(state, payload) {
		state.ticketList = payload
	},
	setTicketDetailList(state, payload) {
		state.ticketDetailList = payload
	},
	setServiceTicketList(state, payload){
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
	// 服务工单信息
	GetServiceDataList({commit,state}, payload){
		getServiceWorkOrderList(payload).then(response => {
			commit("setServiceTicketList",response.data.body.ticketList)
		}).catch(error => {
			console.log(error);
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