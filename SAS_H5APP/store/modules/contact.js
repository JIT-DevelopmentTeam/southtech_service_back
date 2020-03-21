import {getContactList} from '@/api/serviceNumber.js'

const state = {
	contactList: []
}

const getters = {
	getContactList() {
		return state.contactList
	}
}

const mutations = {
	setContactList(state, payload) {
		state.contactList = payload
	}
}

const actions = {
	GetDataList({commit,state},payload){
		getContactList().then(response => {
			commit("setContactList",response.data.body.projectList)
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