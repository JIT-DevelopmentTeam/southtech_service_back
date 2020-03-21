import { http } from '@/utils/request.js'
import qs from 'qs'

export function getClientUserList() {
	return http({
		url: '/f/mobile/clientUser/list'
	})
}

export function updateStatus(params) {
	return http({
		url: '/f/mobile/clientUser/updateStatus',
		params: params,
		method: 'post',
		paramsSerializer: function(params) {
			return qs.stringify(params, {arrayFormat: 'repeat'})
		}
	})
}