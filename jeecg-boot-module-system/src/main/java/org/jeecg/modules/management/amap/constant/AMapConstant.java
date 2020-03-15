package org.jeecg.modules.management.amap.constant;

/**
 * 高德地图常量
 * @author KicoChan
 */
public class AMapConstant {

    // 捷特高德地图Web服务Key
    public static String AMAP_KEY = "a0105607136d934f9c2fb1c8ec0be2d1";

    // 根据地址获取经纬度
    public static String GET_LOCATION_BY_ADDRESS_URL = "https://restapi.amap.com/v3/geocode/geo?key="+AMAP_KEY+"&address=ADDRESS";


}
