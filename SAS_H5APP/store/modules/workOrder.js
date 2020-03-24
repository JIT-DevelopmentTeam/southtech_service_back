import { getWorkOrderList } from '@/api/Ticket.js'
import { getServiceWorkOrderList} from '@/api/serviceNumber.js'


const state = {
	ticketList: [],  // 钉钉工单列表
	serviceTicketList:[]   /* 服务号工单列表 */
}

const getters = {
	getTicketList() {
		return state.ticketList
	},
	getServiceTicketList(){
		return state.serviceTicketList
	}
}

const mutations = {
	setTicketList(state, payload) {
		state.ticketList = payload
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
				let res = {
					list: response.data.result.records,
					total: response.data.result.total
				}
				resolve(res)
			}).catch(error => {
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