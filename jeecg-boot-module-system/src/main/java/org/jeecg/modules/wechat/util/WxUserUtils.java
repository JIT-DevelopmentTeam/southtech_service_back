package org.jeecg.modules.wechat.util;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jeecg.modules.wechat.entity.WxUserEntity;
import org.jeewx.api.core.common.WxstoreUtils;
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.req.WeiXinReqService;
import org.jeewx.api.core.req.model.user.UserInfoListGet;
import org.jeewx.api.wxuser.user.model.Wxuser;

import java.util.ArrayList;
import java.util.List;

public class WxUserUtils {

    private static String GET_USER_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    public static List<WxUserEntity> getAllWxuser(String accesstoken, String next_openid) throws WexinReqException {
        if (accesstoken == null) {
            return null;
        } else {
            UserInfoListGet userInfoListGet = new UserInfoListGet();
            userInfoListGet.setAccess_token(accesstoken);
            userInfoListGet.setNext_openid(next_openid);
            JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(userInfoListGet);
            Object error = result.get("errcode");
            List<WxUserEntity> lstUser = null;
            WxUserEntity mxuser = null;
            int total = result.getInt("total");
            int count = result.getInt("count");
            String strNextOpenId = result.getString("next_openid");
            JSONObject data = result.getJSONObject("data");
            lstUser = new ArrayList(total);
            if (count > 0) {
                JSONArray lstOpenid = data.getJSONArray("openid");
                int iSize = lstOpenid.size();

                for(int i = 0; i < iSize; ++i) {
                    String openId = lstOpenid.getString(i);
                    mxuser = getWxuser(accesstoken, openId);
                    lstUser.add(mxuser);
                }

                if (strNextOpenId != null) {
                    lstUser.addAll(getAllWxuser(accesstoken, strNextOpenId));
                }
            }

            return lstUser;
        }
    }

    public static WxUserEntity getWxuser(String accesstoken, String user_openid) throws WexinReqException {
        if (accesstoken != null) {
            String requestUrl = GET_USER_URL.replace("ACCESS_TOKEN", accesstoken).replace("OPENID", user_openid);
            JSONObject result = WxstoreUtils.httpRequest(requestUrl, "POST", (String)null);
            WxUserEntity wxuser = null;
            Object error = result.get("errcode");
            wxuser = (WxUserEntity)JSONObject.toBean(result, WxUserEntity.class);
            return wxuser;
        } else {
            return null;
        }
    }

}
