import { http } from '@/utils/request.js'


//获取jsapi_ticket（鉴权）
export function GetJsapiTicket(params) {
    return http({
        url:"/f/dingtalk/index/getJsapiTicket",
        method:"get",
        params:params
    })
}

//获取token（鉴权）
export function GetAccessToken() {
    return http({
        url:"/f/dingtalk/index/getAccessToken",
        method:"get"
    })
}


