package org.jeecg.modules.management.amap.constant;

/**
 * 高德地图常量
 * @author KicoChan
 */
public class AMapConstant {

    // 捷特高德地图Web服务Key
    public static String AMAP_KEY = "e109135f32e210caa9205a3ae323b09d";

    // 根据地址获取经纬度
    public static String GET_LOCATION_BY_ADDRESS_URL = "https://restapi.amap.com/v3/geocode/geo?key="+AMAP_KEY+"&address=ADDRESS";


}
