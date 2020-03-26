import { http } from '@/utils/request.js'

/* 获取对应数据字段列表 */
export function getDicList(dictCode) {
	return http({
		url: '/mobile/dic/getDictItemByCode?dictCode='+dictCode,
		method: 'get'
	})
}

/* 根据角色编号获取用户 */
export function listUserByRoleCode(roleCode) {
	return http({
		url: '/mobile/user/listUserByRoleCode?roleCode='+roleCode,
		method: 'get'
	})
}

/* 获取阶段列表 */
export function getStageList(params) {
	return http({
		url: '/mobile/stage/queryStageByWorkOrderId',
		params: params
	})
}

/* 获取工单列表 */
export function getWorkOrderList(params) {
	return http({
		url: '/mobile/workOrder/workOrderList',
		params: params
	})
}

/* 阶段维护保存 */
export function ticketRepairSave(params/* , header */) {
	return http({
		url: '/mobile/stage/progressReport',
		method: 'post',
		// headers: header,
		data: params
	})
}

export function delUploadFile(id) {
	return http({
		url: '/f/mobile/upload/deleteUpFile?id=' + id,
		method: 'get'
	})
}