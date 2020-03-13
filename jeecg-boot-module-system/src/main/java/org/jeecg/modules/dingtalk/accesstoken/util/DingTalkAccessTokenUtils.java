package org.jeecg.modules.dingtalk.accesstoken.util;


import cn.hutool.core.util.ObjectUtil;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.dingtalk.constant.DingTalkConstant;

/**
 * AccessToken工具包
 * @author KicoChan
 */
@Slf4j
public class DingTalkAccessTokenUtils {

    private static RedisUtil redisUtil = SpringContextUtils.getApplicationContext().getBean(RedisUtil.class);

    /**
     * 设置Token
     * @return
     */
    public static void setToken() {
        try {
            DefaultDingTalkClient client = new DefaultDingTalkClient(DingTalkConstant.GET_TOKEN_URL);
            OapiGettokenRequest request = new OapiGettokenRequest();
            request.setAppkey(DingTalkConstant.APP_KEY);
            request.setAppsecret(DingTalkConstant.APP_SECRET);
            request.setHttpMethod("GET");
            OapiGettokenResponse response = client.execute(request);
            if (response.getErrcode() == 0) {
                if (oConvertUtils.isEmpty(redisUtil.get(DingTalkConstant.ACCESS_TOKEN_KEY))) {
                    redisUtil.set(DingTalkConstant.ACCESS_TOKEN_KEY,response.getAccessToken(),response.getExpiresIn());
                } else {
                    if (!redisUtil.get(DingTalkConstant.ACCESS_TOKEN_KEY).toString().equals(response.getAccessToken())) {
                        redisUtil.set(DingTalkConstant.ACCESS_TOKEN_KEY,response.getAccessToken(),response.getExpiresIn());
                    }
                }
            }
        } catch (ApiException e) {
            e.printStackTrace();
            log.error("获取钉钉Token出错!");
        }
    }

}
