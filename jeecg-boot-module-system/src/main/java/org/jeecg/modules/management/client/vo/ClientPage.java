package org.jeecg.modules.management.client.vo;

import java.util.List;
import org.jeecg.modules.management.client.entity.Client;
import org.jeecg.modules.management.client.entity.Contact;
import org.jeecg.modules.management.client.entity.DeviceNumber;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

/**
 * @Description: 客户信息
 * @Author: jeecg-boot
 * @Date:   2020-03-10
 * @Version: V1.0
 */
@Data
public class ClientPage {
	
	/**主键*/
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
	/**编码*/
	@Excel(name = "编码", width = 15)
	private java.lang.String number;
	/**名称*/
	@Excel(name = "名称", width = 15)
	private java.lang.String name;
	/**类型*/
	@Excel(name = "类型", width = 15)
	private java.lang.String type;
	/**来源*/
	@Excel(name = "来源", width = 15)
	private java.lang.String sourceId;
	/**省*/
	@Excel(name = "省", width = 15)
	private java.lang.String province;
	/**市*/
	@Excel(name = "市", width = 15)
	private java.lang.String city;
	/**区*/
	@Excel(name = "区", width = 15)
	private java.lang.String area;
	/**镇*/
	@Excel(name = "镇", width = 15)
	private java.lang.String community;
	/**详细地址*/
	@Excel(name = "详细地址", width = 15)
	private java.lang.String address;
	/**所属用户*/
	@Excel(name = "所属用户", width = 15)
	private java.lang.String userId;
	/**最近联系时间*/
	@Excel(name = "最近联系时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date lastContactTime;
	/**行业*/
	@Excel(name = "行业", width = 15)
	private java.lang.String industry;
	/**社会信用代码*/
	@Excel(name = "社会信用代码", width = 15)
	private java.lang.String creditCode;
	/**性质*/
	@Excel(name = "性质", width = 15)
	private java.lang.String property;
	/**法人代表*/
	@Excel(name = "法人代表", width = 15)
	private java.lang.String legalPerson;
	/**注册资金*/
	@Excel(name = "注册资金", width = 15)
	private java.lang.Double registeredCapital;
	/**成立日期*/
	@Excel(name = "成立日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date establishmentDate;
	/**网址*/
	@Excel(name = "网址", width = 15)
	private java.lang.String webSite;
	
	@ExcelCollection(name="联系人信息")
	private List<Contact> contactList;	
	@ExcelCollection(name="设备编号")
	private List<DeviceNumber> deviceNumberList;	
	
}
