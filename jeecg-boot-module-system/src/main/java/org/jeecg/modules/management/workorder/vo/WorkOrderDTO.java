package org.jeecg.modules.management.workorder.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class WorkOrderDTO {

    /**
     * 主键
     */
    private String id;

    /**
     * 类型
     */
    private String type;

    /**
     * 申报时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date declarationTime;

    /**
     * 紧急程度
     */
    private String emergencyLevel;

    /**
     * 客户
     */
    private String clientName;

    /**
     * 详细地址
     */
    private java.lang.String address;
    /**
     * 经度
     */
    private java.lang.Double longitude;
    /**
     * 纬度
     */
    private java.lang.Double latitude;
    /**
     * 工单详细id
     */
    private java.lang.String detailId;
    /**
     * 工单编号
     */
    private java.lang.String number;

}
