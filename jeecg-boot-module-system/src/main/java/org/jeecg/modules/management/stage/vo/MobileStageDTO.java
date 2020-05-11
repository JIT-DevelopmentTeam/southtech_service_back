package org.jeecg.modules.management.stage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class MobileStageDTO {

    // 进度id
    private String id;
    // 阶段名称
    private String name;
    // 完成时间
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date finishTime;
    // 签到
    private String checkIn;
    // 签出
    private String checkOut;
    // 需要拍照
    private String takePicture;
    // 费用模板
    private String costTemplate;
    // 原件归档
    private String archive;
    // 资料状态
    private String stateInformation;
    // 触发消息
    private String triggerMessage;
    // 触发应收
    private String triggerReceivable;
    // 提交附件
    private String attachment;
    // 工作要求
    private String jobDescription;
    // 汇报描述
    private String description;

}
