package org.jeecg.modules.management.gantttask.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 甘特任务实体
 * @author KicoChan
 */
@Data
public class GanttTask implements Serializable {

    private String id;
    private String text; // 文本名称
    private String start_date; // 开始日期(格式:yyyy-MM-dd)
    private Integer duration;   // 占数(天数)
    private Boolean open; // 是否打开
    private String parent; // 父级id

}
