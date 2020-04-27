package org.jeecg.modules.management.client.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.jeecg.common.aspect.annotation.Dict;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;

/**
 * @Description: 联系人信息
 * @Author: jeecg-boot
 * @Date:   2020-03-10
 * @Version: V1.0
 */
@Data
@TableName("tb_contact")
public class Contact implements Serializable {
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
	/**名称*/
	@Excel(name = "名称", width = 15)
	private java.lang.String name;
	/**性别*/
	@Excel(name = "性别", width = 15)
    @Dict(dicCode = "contact_sex")
	private java.lang.String sex;
	/**部门*/
	@Excel(name = "部门", width = 15)
	private java.lang.String department;
	/**职务*/
	@Excel(name = "职务", width = 15)
	private java.lang.String position;
	/**手机*/
	@Excel(name = "手机", width = 15)
    private java.lang.String mobilePhone;
	/**电话*/
	@Excel(name = "电话", width = 15)
	private java.lang.String phone;
	/**微信号*/
	@Excel(name = "微信号", width = 15)
	private java.lang.String wechatNumber;
	/**qq号*/
	@Excel(name = "qq号", width = 15)
	private java.lang.String qqNumber;
	/**电子邮件*/
	@Excel(name = "电子邮件", width = 15)
	private java.lang.String email;
	/**所属客户*/
	private java.lang.String clientId;
}
