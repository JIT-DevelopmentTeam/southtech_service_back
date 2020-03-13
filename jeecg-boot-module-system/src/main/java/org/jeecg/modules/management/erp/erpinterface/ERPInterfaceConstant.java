package org.jeecg.modules.management.erp.erpinterface;

/**
 * ERP接口信息管理
 */
public class ERPInterfaceConstant {

    // ERP接口授权码
    public static final String AUTHORIZATIONCODE = "21b66d6f-0e82-4f5c-8cc8-acba8032bf54";

    // token 键值
    public static final String TOKEN_KEY = "APIToken";

    // 获取接口Token
    public static final String GET_TOKEN_URL = "http://192.168.3.252:8080/sac_erp_api/api/token/getToken?Authorization=AUTHORIZATION";

    // 获取客户列表(增量同步)
    public static final String LIST_CLIENT_URL = "http://192.168.3.252:8080/sac_erp_api/api/erp_get/getClient?token=TOKEN&max=MAX";

    // 获取设备编号(增量同步)
    public static final String LIST_DEVICENUMBER_URL = "http://192.168.3.252:8080/sac_erp_api/api/erp_get/getContract?token=TOKEN&max=MAX";

}
