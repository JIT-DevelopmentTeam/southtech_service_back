import {getDicList} from '@/api/Ticket.js'
import {getServiceDicList} from '@/api/serviceNumber.js'

const state = {
	/* 钉钉 */
	typeList: [],/* 工单类型 */
	statusList: [],/* 工单状态 */
	faultLocaList: [],/* 故障部位 */
	
	/* 服务号 */
	regionList: [],/* 片区部位 */
	servicetypeList: [],/* 工单类型 */
	servicefaultLocaList: []/* 故障部位 */
}

const getters = {
	/* 钉钉 */
	getTypeList() {
		return state.typeList
	},
	getStatusList() {
		return state.statusList
	},
	getFaultLocaList() {
		return state.faultLocaList
	},
	
	/* 服务号 */
	getRegionList() {
		return state.regionList
	},
	getServiceTypeList() {
		return state.servicetypeList
	},
	getServiceFaultLocaList() {
		return state.servicefaultLocaList
	}
}

const mutations = {
	/* 钉钉 */
	setTypeList(state, payload) {
		sessionStorage.setItem('typeList', JSON.stringify(payload))
		state.typeList = payload
	},
	setStatusList(state, payload) {
		sessionStorage.setItem('statusList', JSON.stringify(payload))
		state.statusList = payload
	},
	setFaultLocaList(state, payload) {
		sessionStorage.setItem('faultLocaList', JSON.stringify(payload))
		state.faultLocaList = payload
	},
	
	/* 服务号 */
	setRegionList(state, payload){
		sessionStorage.setItem('regionList', JSON.stringify(payload))
		state.regionList = payload
	},
	setServiceTypeList(state, payload) {
		sessionStorage.setItem('serviceTypeList', JSON.stringify(payload))
		state.servicetypeList = payload
	},
	setServiceFaultLocaList(state, payload) {
		sessionStorage.setItem('serviceFaultLocaList', JSON.stringify(payload))
		state.servicefaultLocaList = payload
	}
}

const actions = {
	/* 钉钉 */
	GetTypeList({commit,state}, payload){
		getDicList(payload).then(response => {
			commit("setTypeList",response.data.result)
		}).catch(error => {
			console.log(error);
		})
	},
	GetStatusList({commit,state}, payload){
		getDicList(payload).then(response => {
			commit("setStatusList",response.data.result)
		}).catch(error => {
			console.log(error);
		})
	},
	GetFaultLocaList({commit, state}, payload) {
		getDicList(payload).then(response => {
			commit("setFaultLocaList",response.data.result)
		}).catch(error => {
			console.log(error);
		})
	},
	
	/* 服务号 */
	GetRegionList({commit, state}, payload) {
		getServiceDicList(payload).then(response => {
			commit("setRegionList",response.data.result)
		}).catch(error => {
			console.log(error);
		})
	},
	GetServiceTypeList({commit, state}, payload) {
		getServiceDicList(payload).then(response => {
			commit("setServiceTypeList", response.data.result)
		}).catch(error => {
			console.log(error);
		})
	},
	GetServiceFaultLocaList({commit, state}, payload) {
		getServiceDicList(payload).then(response => {
			commit("setServiceFaultLocaList", response.data.result)
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