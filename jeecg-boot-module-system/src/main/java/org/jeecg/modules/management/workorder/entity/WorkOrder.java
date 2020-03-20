package org.jeecg.modules.management.workorder.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.jeecg.modules.demo.test.entity.JeecgOrderCustomer;
import org.jeecg.modules.demo.test.entity.JeecgOrderTicket;
import org.jeecgframework.poi.excel.annotation.Excel;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: 工单信息
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
@Data
@TableName("ic_work_order")
public class WorkOrder implements Serializable {
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
	/**编号*/
    @Excel(name = "编号", width = 15)
    private java.lang.String number;
	/**状态*/
    @Excel(name = "状态", width = 15)
    private java.lang.String status;
	/**类型*/
    @Excel(name = "类型", width = 15)
    private java.lang.String type;
	/**客户*/
    @Excel(name = "客户", width = 15)
    private java.lang.String clientId;
	/**联系人*/
    @Excel(name = "联系人", width = 15)
    private java.lang.String contactId;
	/**接入方式*/
    @Excel(name = "接入方式", width = 15)
    private java.lang.String accessMethod;
	/**代报人*/
    @Excel(name = "代报人", width = 15)
    private java.lang.String correspondentName;
	/**紧急程度*/
    @Excel(name = "紧急程度", width = 15)
    private java.lang.String emergencyLevel;
	/**客服*/
    @Excel(name = "客服", width = 15)
    private java.lang.String customerServiceName;
	/**申报时间*/
    @Excel(name = "申报时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private java.util.Date declarationTime;
    /**附件*/
    @Excel(name = "附件", width = 15)
    private java.lang.String annex;


}
