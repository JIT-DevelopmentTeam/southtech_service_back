package org.jeecg.modules.management.progressreport.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.modules.management.file.entity.File;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class MobileReportDTO {

    private String time;
    private String isNeedToReplace;
    private String isCompleted;
    private String description;
    private String faultLocation;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date appointment;

    private List<File> photoList;
    private List<File> fileList;

}
