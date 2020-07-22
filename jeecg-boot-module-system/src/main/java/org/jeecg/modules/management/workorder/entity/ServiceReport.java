package org.jeecg.modules.management.workorder.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.management.progressreport.vo.ExportReportDTO;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import org.jeecgframework.poi.excel.annotation.ExcelTarget;

import java.util.Date;
import java.util.List;

/**
 * 维修汇报
 */
@ExcelTarget("serviceReport")
@Data
@ApiModel(value = "serviceReport对象", description = "维修记录")
public class ServiceReport {

    /**序号*/
    @Excel(name = "序号", width = 10, needMerge = true)
    @ApiModelProperty(value = "序号")
    private Integer number;
    /**省份*/
    @Excel(name = "省份", width = 15, needMerge = true)
    @ApiModelProperty(value = "省份")
    private String province;
    /**城市*/
    @Excel(name = "城市",width = 15, needMerge = true)
    @ApiModelProperty(value = "城市")
    private String city;
    /**客户名称*/
    @Excel(name = "客户名称", width = 30, needMerge = true)
    @ApiModelProperty(value = "客户名称")
    private String clientName;
    /**炉型*/
    @Excel(name = "炉型",width = 15, needMerge = true)
    @ApiModelProperty(value = "炉型")
    private String furnace;
    /**反馈问题*/
    @Excel(name = "反馈问题",width = 30, needMerge = true)
    @ApiModelProperty(value = "反馈问题")
    private String feedbackQuestion;
    /**问题类型*/
    @Excel(name = "问题类型",width = 15, needMerge = true)
    @ApiModelProperty(value = "问题类型")
    private String questionType;
    /**发生故障的部件*/
    @Excel(name = "发生故障的部件",width = 30, needMerge = true)
    @ApiModelProperty(value = "发生故障的部件")
    private String faultLocation;
    /**保内/保外*/
    @Excel(name = "保内/保外",width = 15, needMerge = true)
    @ApiModelProperty(value = "保内/保外")
    private String isQgp;
    /**是否收费*/
    @Excel(name = "是否收费",width = 10, needMerge = true)
    @ApiModelProperty(value = "是否收费")
    private String isCharge;
    /**前往或电话处理的售后人员*/
    @Excel(name = "前往或电话处理的售后人员",width = 15, needMerge = true)
    @ApiModelProperty(value = "前往或电话处理的售后人员")
    private String serviceEngineerName;
    /**开始时间*/
    @Excel(name = "开始时间", width = 20, format = "yyyy-MM-dd", needMerge = true)
    @ApiModelProperty(value = "开始时间")
    private Date appointment;
    /**完成时间*/
    @Excel(name = "完成时间", width = 20, format = "yyyy-MM-dd", needMerge = true)
    @ApiModelProperty(value = "完成时间")
    private Date completeTime;
    /**处理结果*/
    @Excel(name = "处理结果",width = 15, needMerge = true)
    @ApiModelProperty(value = "处理结果")
    private String processResult;
    /**状态*/
    @Excel(name = "状态",width = 15, needMerge = true)
    @ApiModelProperty(value = "状态")
    private String status;
    /**未完成原因*/
    @Excel(name = "未完成原因",width = 30, needMerge = true)
    @ApiModelProperty(value = "未完成原因")
    private String reasonForIncomplete;
    /**客户对处理结果是否满意*/
    @Excel(name = "客户对处理结果是否满意",width = 10, needMerge = true)
    @ApiModelProperty(value = "客户对处理结果是否满意")
    private String resultsEvaluation;
    /**客户对服务人员工作是否满意*/
    @Excel(name = "客户对服务人员工作是否满意",width = 10, needMerge = true)
    @ApiModelProperty(value = "客户对服务人员工作是否满意")
    private String staffEvaluation;
    /**汇报记录*/
    @ExcelCollection(name = "汇报记录")
    @ApiModelProperty(value = "汇报记录")
    private List<ExportReportDTO> progressReportList;


}
