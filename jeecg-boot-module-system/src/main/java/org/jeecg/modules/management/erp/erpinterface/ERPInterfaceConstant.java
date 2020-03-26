package org.jeecg.modules.management.erp.erpinterface;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;

/**
 * ERP接口信息管理
 */
public class ERPInterfaceConstant {

    public static String API_DOMAIN_NAME;

    static {
        try {
            API_DOMAIN_NAME = PropertiesLoaderUtils.loadAllProperties(("api/api_config.properties")).getProperty("api_domain_name");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ERP接口授权码
    public static final String AUTHORIZATIONCODE = "21b66d6f-0e82-4f5c-8cc8-acba8032bf54";

    // token 键值
    public static final String TOKEN_KEY = "APIToken";

    // 获取接口Token
    public static final String GET_TOKEN_URL = "/api/token/getToken?Authorization=AUTHORIZATION";

    // 获取客户列表(增量同步)
    public static final String LIST_CLIENT_URL = "/api/erp_get/getClient?token=TOKEN&max=MAX";

    // 获取设备编号(增量同步)
    public static final String LIST_DEVICENUMBER_URL = "/api/erp_get/getContract?token=TOKEN&max=MAX";

    // 获取物料列表(增量同步)
    public static final String LIST_ITEM_URL = "/api/erp_get/getItem?token=TOKEN&max=MAX";

    public static final String LIST_ITEM_CLASS_URL = "/api/erp_get/getItemClass?token=TOKEN&max=MAX";

}
