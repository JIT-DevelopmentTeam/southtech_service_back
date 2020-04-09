import Vue from 'vue'
import Vuex from 'vuex'
import workOrder from './modules/workOrder.js'
import dic from './modules/dic.js'
import stage from './modules/stage.js'
import contact from './modules/contact.js'
import clientUser from './modules/clientUser.js'
import * as dd from 'dingtalk-jsapi'
import {GetUserInfo} from '@/api/ddjsapi.js'
import {GetUrlKey,getWxUserInfo} from '@/api/wechatapi.js'

Vue.use(Vuex)

const debug = process.env.NODE_ENV !== 'production'

const state = {
	code: null,
	userId: null,
	openId: null
}

const getters = {
	getCode() {
		return state.code
	},
	getUserId() {
		return state.userId
	},
	getWeChatOpenId(){
		return state.openId
	}
}

const mutations = {
	SET_CODE(state, payload) {
		sessionStorage.setItem('code', payload)
		state.code = payload
	},
	SET_USERID(state, payload) {
		sessionStorage.setItem('userId', payload)
		state.userId = payload
	},
	SET_WECHAT_OPENID(state, payload){
		sessionStorage.setItem('openId', payload);
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
	GET_USER_INFO({commit, state}, payload) {
		return new Promise((resolve, reject) => {
			GetUserInfo().then(res => {
				commit("SET_USERID", res.data.result.userId)
				resolve()
			}).catch(err => {
				reject()
			})
		})
	},
	GET_WECHAT_OPENID({commit, state}, payload) {
		return new Promise((resolve, reject) => {
			getWxUserInfo(payload).then(res => {
				if (res.data.success) {
					commit("SET_WECHAT_OPENID", res.data.result.openId);
					resolve(res.data.result);
				} else {
					// 本地无用户数据
					resolve(null);
				}
			});
		});
		
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
	strict: process.env.NODE_ENV !== 'production'
})