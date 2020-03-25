import { http } from '@/utils/request.js'


//获取jsapi_ticket（鉴权）
export function GetJsapiTicket(params) {
    return http({
        url:"/mobile/index/getJsapiTicket",
        method:"get",
        params:params
    })
}

//获取UserId（鉴权）
export function GetUserInfo(params) {
    return http({
        url:"/mobile/index/getUserInfo",
        method:"get",
		params:params
    })
}


