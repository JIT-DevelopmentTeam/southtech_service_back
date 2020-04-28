package org.jeecg.modules.management.workorder.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

@Data
@TableName("view_work_order")
public class WorkOrderPageDTO implements Serializable {

    private java.lang.String id;
    private java.lang.String createBy;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private java.util.Date createTime;
    private java.lang.String updateBy;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private java.util.Date updateTime;
    private java.lang.String sysOrgCode;
    private java.lang.String number;
    private java.lang.String status;
    private java.lang.String type;
    private java.lang.String accessMethod;
    private java.lang.String clientId;
    private java.lang.String contactId;
    private java.lang.String emergencyLevel;
    private java.lang.String customerServiceName;
    private java.util.Date declarationTime;
    private java.lang.String annex;
    private java.lang.String deviceNumber;
    private java.lang.String serviceEngineerName;
    private java.lang.String faultLocation;
    private java.util.Date assignedTime;
    private java.lang.String peers;
    private java.util.Date appointment;
    private java.util.Date plannedCompletionTime;
    private java.lang.String workOrderDetailId;

}
