package org.jeecg.modules.management.workorder.entity;

import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelTarget;

import java.io.Serializable;
import java.util.Date;

/**
 * 维修汇报
 */
@ExcelTarget("serviceReport")
@Data
public class ServiceReport {

    /**序号*/
    @Excel(name = "序号", width = 10)
    private Integer number;
    /**省份*/
    @Excel(name = "省份", width = 15)
    private String province;
    /**城市*/
    @Excel(name = "城市",width = 15)
    private String city;
    /**客户名称*/
    @Excel(name = "客户名称", width = 30)
    private String clientName;
    /**炉型*/
    @Excel(name = "炉型",width = 15)
    private String furnace;
    /**反馈问题*/
    @Excel(name = "反馈问题",width = 30)
    private String feedbackQuestion;
    /**问题类型*/
    @Excel(name = "问题类型",width = 15)
    private String questionType;
    /**发生故障的部件*/
    @Excel(name = "发生故障的部件",width = 30)
    private String faultLocation;
    /**保内/保外*/
    @Excel(name = "保内/保外",width = 15)
    private String isQgp;
    /**是否收费*/
    @Excel(name = "是否收费",width = 10)
    private String isCharge;
    /**前往或电话处理的售后人员*/
    @Excel(name = "前往或电话处理的售后人员",width = 15)
    private String serviceEngineerName;
    /**开始时间*/
    @Excel(name = "开始时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    private Date appointment;
    /**完成时间*/
    @Excel(name = "完成时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    private Date completeTime;
    /**处理结果*/
    @Excel(name = "处理结果",width = 15)
    private String processResult;
    /**状态*/
    @Excel(name = "状态",width = 15)
    private String status;
    /**未完成原因*/
    @Excel(name = "未完成原因",width = 30)
    private String reasonForIncomplete;
    /**客户对处理结果是否满意*/
    @Excel(name = "客户对处理结果是否满意",width = 10)
    private String resultsEvaluation;
    /**客户对服务人员工作是否满意*/
    @Excel(name = "客户对服务人员工作是否满意",width = 10)
    private String staffEvaluation;


}
