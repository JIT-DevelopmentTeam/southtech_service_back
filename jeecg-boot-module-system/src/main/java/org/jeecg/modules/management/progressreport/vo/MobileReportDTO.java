package org.jeecg.modules.management.progressreport.vo;

import lombok.Data;
import org.jeecg.modules.management.file.entity.File;

import java.util.List;

@Data
public class MobileReportDTO {

    private String time;
    private String isCompleted;
    private String description;
    private String faultLocation;

    private List<File> photoList;
    private List<File> fileList;

}
