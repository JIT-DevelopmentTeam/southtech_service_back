package org.jeecg.modules.management.workorder.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class MobileWorkOrderDetailDTO {

    // 工单明细id
    private String detailId;
    // 设备名
    private String deviceName;
    // 工程师
    private String enginerName;
    // 当前阶段
    private String currentProgress;
    // 进度id
    private String progressId;
    // 完成状态
    private String isCompleted;
    // 汇报id
    private String reportId;
    // 故障部位
    private String faultLocation;
    // 预约时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date appointment;
    // 钉钉userid
    private String enterpriseId;

}
