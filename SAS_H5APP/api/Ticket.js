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
		method: 'get',
		params: params
	})
}

/* 获取工单附件 */
export function getWorkOrderAnnex(params) {
	return http({
		url: '/mobile/workOrder/getWorkOrderAnnex',
		method: 'get',
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

export function delUploadFile(ids) {
	return http({
		url: '/mobile/upload/deleteULPicture?ids=' + ids,
		method: 'get'
	})
}

export function getAllDetail(params) {
	return http({
		url: '/mobile/workOrder/getAllDetail',
		params: params,
		method: 'get'
	})
}

export function getAllReport(params) {
	return http({
		url: '/mobile/workOrder/getAllReport',
		params: params,
		method: 'get'
	})
}

/* 获取用户列表 */
export function listClient(params) {
	return http({
		url: '/mobile/client/list',
		params: params,
		method: 'get'
	})
}

/* 根据id获取客户 */
export function getClientById(params) {
	return http({
		url: '/mobile/client/getClientById?id='+params,
		method: 'get'
	})
}

/* 根据客户查询未派工单数量 */
export function queryUnDispatchTicket(clientId) {
	return http({
		url: '/mobile/workOrder/queryUnDispatchTicket?clientId=' + clientId,
		method: 'get'
	})
}

/* 根据id获取汇报历史记录 */
export function getReportById(params) {
	return http({
		url: '/mobile/stage/getByReportId',
		params: params,
		method: 'get'
	});
}

/* 保存工单 */
export function addWorkOrder(params) {
	return http({
		url:'/mobile/workOrder/add',
		data: params,
		method:'post'
	});
}

/* 根据钉钉id获取实体 */
export function getUserByEnterpriseId(params) {
	return http({
		url:'/mobile/user/getByEnterpriseId?enterpriseId='+params,
		method:'get'
	});
}

/* 根据openid获取微信用户信息 */
export function getClientByOpenId(params) {
	return http({
		url:'/mobile/wxUser/getClientByOpenId?openId='+params,
		method:'get'
	});
}

/* 根据客户id获取设备信息 */
export function listDeviceNumberByMainId(params) {
	return http({
		url:'/mobile/client/listDeviceNumberByMainId',
		method:'get',
		params:params
	})
}

/* 获取待评价工单 */
export function listWorkOrderByComment(params) {
	return http({
		url:'/mobile/workOrder/worderOrderListByComment?clientId='+params,
		method:'get',
		params:params
	});
}

/* 批量评价 */
export function batchSaveServiceCommentery(params) {
	return http({
		url:'/mobile/serviceCommentery/batchSave',
		method:'post',
		data:params
	});
}

/* 数据统计 */
export function dataStatistics(params) {
	return http({
		url:'/mobile/dataStatistics/dataStatistics',
		method:'get',
		params:params
	});
}

/* 排行榜 */
export function ranking(params) {
	return http({
		url:'/mobile/dataStatistics/ranking',
		method:'get',
		params:params
	});
}