import Vue from 'vue'
import Vuex from 'vuex'
import workOrder from './modules/workOrder.js'
import dic from './modules/dic.js'
import stage from './modules/stage.js'
import contact from './modules/contact.js'
import clientUser from './modules/clientUser.js'
import * as dd from 'dingtalk-jsapi'
import {GetAccessToken} from '@/api/ddjsapi.js'

Vue.use(Vuex)

const debug = process.env.NODE_ENV !== 'production'

const state = {
	code: '',
	token: '',
	userId: '',
	weChatUser: {},
}

const getters = {
	getCode() {
		return state.code
	},
	getToken() {
		return state.token
	},
	getUserId() {
		return state.userId
	},
	getWeChatUser(){
		return stage.openId
	}
}

const mutations = {
	SET_CODE(state, payload) {
		sessionStorage.setItem('code', payload)
		state.code = payload
	},
	SET_TOKEN(state, payload) {
		sessionStorage.setItem('token', payload)
		state.token = payload
	},
	SET_USERID(state, payload) {
		sessionStorage.setItem('userId', payload)
		state.userId = payload
	},
	SET_WECHAT_USER(state, payload){
		state.openId = payload
	}
}

const actions = {
	GET_CODE({commit,state},payload) {
		return new Promise((resolve, reject) => {
			dd.ready(() => {
				dd.runtime.permission.requestAuthCode({
					corpId: payload,
					onSuccess: (info) => {
						commit("SET_CODE", info.code)
						resolve(info.code)
					},
					onFail: (err) => {
						uni.showModal({
							title: 'err',
							content: JSON.stringify(err)
						});
						reject()
					}
				})
			});
		})
	},
	GET_TOKEN({commit, state}, payload) {
		return new Promise((resolve, reject) => {
			GetAccessToken().then(res => {
				commit("SET_TOKEN", res.data.body.accessToken)
				commit("SET_USERID", res.data.body.userId)
				var obj = {
					token: res.accessToken,
					userId: res.userId
				}
				resolve(obj)
			}).catch(err => {
				uni.showModal({
					title: 'err',
					content: JSON.stringify(err)
				});
				reject()
			})
		})
	},
	GET_WECHAT_USER({commit, state}, payload) {
		commit("SET_WECHAT_USER", payload)
	},
}

const modules = {
	workOrder,
	dic,
	stage,
	contact,
	clientUser
}

export default new Vuex.Store({
	namespaced: true,
	state,
	getters,
	mutations,
	actions,
	modules,
	strict: debug
})