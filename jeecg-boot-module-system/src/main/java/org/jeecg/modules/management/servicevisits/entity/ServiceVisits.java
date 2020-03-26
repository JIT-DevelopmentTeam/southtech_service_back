package org.jeecg.modules.management.servicevisits.entity;

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
 * @Description: 回访记录
 * @Author: jeecg-boot
 * @Date:   2020-03-17
 * @Version: V1.0
 */
@Data
@TableName("ic_service_visits")
public class ServiceVisits implements Serializable {
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
	/**回访人*/
	@Excel(name = "回访人", width = 15)
    private java.lang.String visitPeople;
	/**回访时间*/
	@Excel(name = "回访时间", width = 15)
    private java.lang.String visitTime;
	/**客户*/
	@Excel(name = "客户", width = 15)
    private java.lang.String customer;
	/**受访人*/
	@Excel(name = "受访人", width = 15)
    private java.lang.String respondents;
	/**是否完成*/
	@Excel(name = "是否完成", width = 15)
    private java.lang.String isCompleted;
	/**回访评分*/
	@Excel(name = "回访评分", width = 15)
    private java.lang.String score;
	/**回访方式*/
	@Excel(name = "回访方式", width = 15)
    private java.lang.String visitWay;
	/**联系电话*/
	@Excel(name = "联系电话", width = 15)
    private java.lang.String contact;
	/**回访意见*/
	@Excel(name = "回访意见", width = 15)
    private java.lang.String opinion;
	/**工单ID*/
	@Excel(name = "工单ID", width = 15)
    private java.lang.String workOrderId;
}
