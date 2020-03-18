package org.jeecg.modules.management.amap.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 高德地图
 * @author KicoChan
 */
@Data
public class AMap implements Serializable {

    private static final long serialVersionUID = 1L;

    private Double longitude;  // 经度
    private Double latitude;    // 纬度


}
