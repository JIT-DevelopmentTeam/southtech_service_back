package org.jeecg.modules.management.erp.erptoken.util;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.util.HttpHelper;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.management.erp.erpinterface.ERPInterfaceConstant;


/**
 * ERPToken工具
 * @author KicoChan
 */
@Slf4j
public class ERPTokenUtils {

    private static RedisUtil redisUtil = SpringContextUtils.getApplicationContext().getBean(RedisUtil.class);

    /**
     * 设置Token
     * @return
     */
    public static void setToken() {
        try {
            JSONObject jsonObject = HttpHelper.httpGet(ERPInterfaceConstant.GET_TOKEN_URL.replace("AUTHORIZATION",ERPInterfaceConstant.AUTHORIZATIONCODE));
            if (oConvertUtils.isNotEmpty(jsonObject.get("token"))) {
                if (oConvertUtils.isEmpty(redisUtil.get(ERPInterfaceConstant.TOKEN_KEY))) {
                    if (StringUtils.isNotBlank(jsonObject.getString("token"))) {
                        redisUtil.set(ERPInterfaceConstant.TOKEN_KEY,jsonObject.getString("token"),jsonObject.getLongValue("effective_Time"));
                    }
                } else {
                    if (!redisUtil.get(ERPInterfaceConstant.TOKEN_KEY).toString().equals(jsonObject.getString("token"))) {
                        redisUtil.set(ERPInterfaceConstant.TOKEN_KEY,jsonObject.getString("token"),jsonObject.getLongValue("effective_Time"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取APIToken出错!");
        }
    }

}
