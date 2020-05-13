package org.jeecg.modules.management.workorder.vo;

import java.util.List;
import org.jeecg.modules.management.workorder.entity.WorkOrder;
import org.jeecg.modules.management.workorder.entity.WorkOrderDetail;
import org.jeecg.modules.management.workorder.entity.WorkOrderProgress;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

/**
 * @Description: 工单信息
 * @Author: jeecg-boot
 * @Date:   2020-03-16
 * @Version: V1.0
 */
@Data
public class WorkOrderPage {
	
	/**主键*/
	private String id;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
	private String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
	private String updateBy;
	/**更新日期*/
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	/**所属部门*/
	@Excel(name = "所属部门", width = 15)
	private String sysOrgCode;
	/**编号*/
	@Excel(name = "编号", width = 15)
	private String number;
	/**状态*/
	@Excel(name = "状态", width = 15)
	private String status;
	/**类型*/
	@Excel(name = "类型", width = 15)
	private String type;
	/**客户*/
	@Excel(name = "客户", width = 15)
	private String clientId;
	/**联系人*/
	@Excel(name = "联系人", width = 15)
	private String contactId;
	/**接入方式*/
	@Excel(name = "接入方式", width = 15)
	private String accessMethod;
	/**代报人*/
	@Excel(name = "代报人", width = 15)
	private String correspondentName;
	/**紧急程度*/
	@Excel(name = "紧急程度", width = 15)
	private String emergencyLevel;
	/**客服*/
	@Excel(name = "客服", width = 15)
	private String customerServiceName;
    /**需要派工*/
    private java.lang.String needDispatch;
	/**申报时间*/
	@Excel(name = "申报时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date declarationTime;
    /**附件*/
    @Excel(name = "附件", width = 15)
    private java.lang.String annex;

	@ExcelCollection(name="工单明细")
	private List<WorkOrderDetail> workOrderDetailList;	
	@ExcelCollection(name="工单进度")
	private List<WorkOrderProgress> workOrderProgressList;	
	
}
