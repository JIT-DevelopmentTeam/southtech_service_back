import {getClientUserList} from '@/api/client_user.js'

const state = {
	clientUserList: []
}

const getters = {
	getClientUserList() {
		return state.clientUserList
	}
}

const mutations = {
	setClientUserList(state, payload) {
		sessionStorage.setItem('clientUserList', JSON.stringify(payload))
		state.clientUserList = payload
	}
}

const actions = {
	GetDataList({commit,state},payload){
		return new Promise((resolve, reject) => {
			getClientUserList().then(response => {
				commit("setClientUserList",response.data.body.clientUserList)
				resolve(response.data.body.clientUserList)
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