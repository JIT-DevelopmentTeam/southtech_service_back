package org.jeecg.modules.management.workorder.vo;

import lombok.Data;

import java.util.Date;

@Data
public class MobileWorkOrderDTO {
    // 工单id
    private String id;
    // 工单编号
    private String number;
    // 工单状态
    private String status;
    // 工单类型
    private String type;
    // 客户名
    private String clientName;
    // 联系人
    private String contactName;
    // 电话
    private String contactPhone;
    // 邮箱
    private String email;
    // 省
    private String province;
    // 市
    private String city;
    // 区
    private String area;
    // 镇
    private String community;
    // 详细地址
    private String address;
    // 分配时间
    private String assignedTime;
    // 设备名称
    private String deviceName;
    // 描述
    private String description;
    // 经度
    private java.lang.Double longitude;
    // 纬度
    private java.lang.Double latitude;
    // 设备签约日期
    private Date signing;
}
