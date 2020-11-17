package org.jeecg.modules.qywx.api.authorize;

import net.sf.json.JSONObject;
import org.jeewx.api.core.common.WxstoreUtils;

public class AuthorizeAPI {

    private static String auth_getuserinfo = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE";

    public static String auth_getuserinfo(String access_token, String code) {
        String url = auth_getuserinfo.replace("ACCESS_TOKEN", access_token).replace("CODE", code);
        JSONObject result = WxstoreUtils.httpRequest(url, "GET", null);
        return result.getString("UserId");
    }
}
