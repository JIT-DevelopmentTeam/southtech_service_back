package org.jeecg.modules.management.stage.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;

/**
 * @Description: 阶段信息
 * @Author: jeecg-boot
 * @Date:   2020-03-16
 * @Version: V1.0
 */
@Data
@TableName("tb_stage")
public class Stage implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
    private java.lang.String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private java.util.Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
    private java.lang.String updateBy;
	/**更新日期*/
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private java.util.Date updateTime;
	/**所属部门*/
	@Excel(name = "所属部门", width = 15)
    private java.lang.String sysOrgCode;
	/**工单类型*/
	@Excel(name = "工单类型", width = 15)
    private java.lang.String workOrderType;
	/**阶段编号*/
	@Excel(name = "阶段编号", width = 15)
    private java.lang.String stageNumber;
	/**阶段名称*/
	@Excel(name = "阶段名称", width = 15)
    private java.lang.String stageName;
	/**累计百分比*/
	@Excel(name = "累计百分比", width = 15)
    private java.lang.Double cumulativePercentage;
	/**排序*/
	@Excel(name = "排序", width = 15)
    private java.lang.Integer orderIndex;
	/**操作规程*/
	@Excel(name = "操作规程", width = 15)
    private java.lang.String operatingProcedures;
	/**需要签到*/
	@Excel(name = "需要签到", width = 15)
    private java.lang.String checkIn;
	/**需要签出*/
	@Excel(name = "需要签出", width = 15)
    private java.lang.String checkOut;
	/**需要拍照*/
	@Excel(name = "需要拍照", width = 15)
    private java.lang.String takePicture;
	/**需要费用模板*/
	@Excel(name = "需要费用模板", width = 15)
    private java.lang.String costTemplate;
	/**需要原件归档*/
	@Excel(name = "需要原件归档", width = 15)
    private java.lang.String archive;
	/**资料状态*/
	@Excel(name = "资料状态", width = 15)
    private java.lang.String stateInformation;
	/**触发消息*/
	@Excel(name = "触发消息", width = 15)
    private java.lang.String triggerMessage;
	/**触发应收*/
	@Excel(name = "触发应收", width = 15)
    private java.lang.String triggerReceivable;
	/**提交附件*/
	@Excel(name = "提交附件", width = 15)
    private java.lang.String attachment;
	/**工作说明*/
	@Excel(name = "工作说明", width = 15)
    private java.lang.String jobDescription;
}
