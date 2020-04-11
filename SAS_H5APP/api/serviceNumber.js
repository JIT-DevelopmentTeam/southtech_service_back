import { http } from '@/utils/request.js'

/*  网页授权认证  */
export function authorize(code) {
	return http({
		url: '/f/weChat/service/oauthLogin',
		params: {
			code: code
		}
	})
}

/*  授权认证加入  */
export function authorizeJoin(params) {
	return http({
		url: '/f/weChat/service/login/authorize',
		method: 'post',
		data: params
	})
}

/* 获取工单列表 */
export function getServiceWorkOrderList(params) {
	return http({
		url: '/mobile/workOrder/WXworkOrderList',
		method: 'get',
		params: params
	})
}

/* 获取设备列表 */
export function getContactList() {
	return http({
		url: '/f/weChat/contact/project/list'
	})
}

/* 故障申报保存 */
export function saveMalfunction(params) {
	return http({
		url: '/f/weChat/malfunction/save',
		method: 'post',
		headers: {
			"Content-Type": "text/html;charset=UTF-8"
		},
		data: params
	})
}
/*  提交工单回访评价  */
export function ticketEvaluate(params) {
	return http({
		url: '/f/weChat/ticket/evaluate',
		method: 'post',
		data: params
	})
}

/* 提交投诉建议 */
export function saveSuggest(params) {
	return http({
		url: '/f/weChat/suggest/save',
		method: 'post',
		 headers: {
			"Content-Type": "text/html;charset=UTF-8"
		},
		data: params
	})
}

/* 获取对应数据字段列表 */
export function getServiceDicList(dictCode) {
	return http({
		url: '/mobile/dic/getDictItemByCode?dictCode='+dictCode,
		method: 'get'
	})
}

/* 获取阶段列表 */
export function getServiceStageList(params) {
	return http({
		url: '/mobile/stage/queryStageByWorkOrderId',
		params: params
	})
}

export function getServiceCommentList(params) {
	return http({
		url: '/mobile/serviceCommentery/getByWorkOrderId',
		params: params
	})
}