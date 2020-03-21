import { http } from '@/utils/request.js'

/* 获取对应数据字段列表 */
export function getDicList(treeName) {
	return http({
		url: '/f/mobile/dic/list',
		params: {
			treeName: treeName
		}
	})
}

/* 获取阶段列表 */
export function getStageList(ticketType, ticketId) {
	return http({
		url: '/f/mobile/stage/getStagelist',
		params: {
			ticketType: ticketType,
			ticketId: ticketId
		}
	})
}

/* 获取工单列表 */
export function getWorkOrderList(userId) {
	return http({
		url: '/f/mobile/ticket/list',
		params: {
			userId: userId
		}
	})
}

/* 阶段维护保存 */
export function ticketRepairSave(params, header) {
	return http({
		url: '/f/mobile/stageProcess/save',
		method: 'post',
		headers: header,
		data: params
	})
}

export function delUploadFile(id) {
	return http({
		url: '/f/mobile/upload/deleteUpFile?id=' + id,
		method: 'get'
	})
}