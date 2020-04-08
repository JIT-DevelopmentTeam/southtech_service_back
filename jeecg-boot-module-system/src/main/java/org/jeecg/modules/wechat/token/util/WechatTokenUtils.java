package org.jeecg.modules.wechat.token.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.util.HttpHelper;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.wechat.constant.WechatConstant;
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.wxbase.wxtoken.JwTokenAPI;
import org.jeewx.api.wxuser.user.JwUserAPI;
import org.jeewx.api.wxuser.user.model.Wxuser;

/**
 * 公众号AccessToken工具
 *
 * @author KicoChan
 */
@Slf4j
public class WechatTokenUtils {

    private static RedisUtil redisUtil = SpringContextUtils.getApplicationContext().getBean(RedisUtil.class);

    /**
     * 设置Token
     *
     * @return
     */
    public static void setToken() {
        if (oConvertUtils.isEmpty(redisUtil.get(WechatConstant.ACCESS_TOKEN_KEY))) {
            try {
                String accessToken = JwTokenAPI.getAccessToken(WechatConstant.APP_ID, WechatConstant.SECRET);
                redisUtil.set(WechatConstant.ACCESS_TOKEN_KEY, accessToken, WechatConstant.EXPIRE_ID);
            } catch (WexinReqException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 设置用户Token
     * @param code
     */
    public static void setUserToken(String code) {
        // TODO 缓存获取 判断如果AccessToken过期 可以使用refresh_token进行刷新
        JSONObject result = HttpHelper.httpGet(WechatConstant.GET_USER_TOKEN_URL.replace("APPID",WechatConstant.APP_ID).replace("SECRET",WechatConstant.SECRET).replace("CODE",code));
    }

}
