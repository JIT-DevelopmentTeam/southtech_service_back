package org.jeecg.modules.management.progressreport.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

@Data
@ApiModel(value = "exportReportDTO对象", description = "汇报记录")
public class ExportReportDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**所属进度*/
    @Excel(name = "所属进度", width = 15)
    @ApiModelProperty(value = "所属进度")
    private java.lang.String progressName;
    /**描述*/
    @Excel(name = "描述", width = 100)
    @ApiModelProperty(value = "描述")
    private java.lang.String description;

}
