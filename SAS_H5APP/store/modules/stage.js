import {getStageList} from '@/api/Ticket.js'
import {getServiceStageList} from '@/api/serviceNumber.js'

const state = {
	/* 钉钉 */
	stageList: [],
	sign_out: [],
	
	/* 服务号 */
	serviceStageList: [],
	serviceSign_out: []
}

const getters = {
	/* 钉钉 */
	getStageList() {
		return state.stageList
	},
	getSign_Out() {
		return state.sign_out
	},
	
	/* 服务号 */
	getServiceStageList() {
		return state.serviceStageList
	},
	getServiceSign_out() {
		return state.serviceSign_out
	}
}

const mutations = {
	/* 钉钉 */
	setStageList(state, payload) {
		state.stageList = payload
	},
	setSign_Out(state, payload) {
		state.sign_out = payload
	},
	
	/* 服务号 */
	setServiceStageList(state, payload) {
		state.serviceStageList = payload
	},
	setServiceSign_out(state, payload) {
		state.serviceSign_out = payload
	}
}

const actions = {
	/* 钉钉 */
	GetDataList({commit,state},payload){
		getStageList(payload.ticketType, payload.ticketId).then(response => {
			commit("setStageList",response.data.body.pageList)
			commit("setSign_Out", response.data.body.stageLogList)
		}).catch(error => {
			console.log(error);
		})
	},
	
	/* 服务号 */
	GetServiceDataList({commit, state}, payload) {
		getServiceStageList(payload.ticketType, payload.ticketId).then(response => {
			commit("setServiceStageList",response.data.body.pageList)
			commit("setServiceSign_out", response.data.body.stageLogList)
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