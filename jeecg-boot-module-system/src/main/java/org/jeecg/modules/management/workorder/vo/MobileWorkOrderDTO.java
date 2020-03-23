package org.jeecg.modules.management.workorder.vo;

import lombok.Data;

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
}
