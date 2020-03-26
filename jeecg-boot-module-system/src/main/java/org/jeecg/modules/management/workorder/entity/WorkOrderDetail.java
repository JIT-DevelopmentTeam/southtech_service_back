package org.jeecg.modules.management.workorder.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Description: 工单明细
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
@Data
@TableName("ic_work_order_detail")
public class WorkOrderDetail implements Serializable {
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
	/**设备编号*/
	@Excel(name = "设备编号", width = 15)
	private java.lang.String deviceNumber;
	/**服务工程师*/
	@Excel(name = "服务工程师", width = 15)
	private java.lang.String serviceEngineerName;
	/**故障部位*/
	@Excel(name = "故障部位", width = 15)
	private java.lang.String faultLocation;
	/**描述*/
	@Excel(name = "描述", width = 15)
	private java.lang.String description;
	/**派工时间*/
	@Excel(name = "派工时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date dispatchTime;
	/**同行人员*/
	@Excel(name = "同行人员", width = 15)
	private java.lang.String peers;
	/**所属工单*/
	private java.lang.String workOrderId;
	/**指派人*/
	private java.lang.String assigneeName;
    /**指派时间*/
    private java.util.Date assignedTime;
    /**计划完成时间*/
    private java.util.Date plannedCompletionTime;
    /**当前进度id*/
    private java.lang.String currentProgress;
    /**附件*/
    private java.lang.String annex;
}
