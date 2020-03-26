package org.jeecg.modules.management.workorder.vo;

import lombok.Data;

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
    // 完成状态
    private String isCompleted;

}
