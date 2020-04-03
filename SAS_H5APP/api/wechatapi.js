let origin =  location.href;
let urlNow =encodeURIComponent(origin);///netbar/api/wechat/v1/get_open_id
let scope = 'snsapi_base';    //snsapi_userinfo   //静默授权 用户无感知
let appid = "wx35360545b97a547b";
let url = `https://open.weixin.qq.com/connect/oauth2/authorize?appid=${appid}&redirect_uri=${urlNow}&response_type=code&scope=${scope}#wechat_redirect`;

export function GetUrlKey(name) {//获取url 参数
  return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.href) || [, ""])[1].replace(/\+/g, '%20')) || null;
}
