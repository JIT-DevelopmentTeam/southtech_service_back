package org.jeecg.modules.management.client.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: 客户信息
 * @Author: jeecg-boot
 * @Date:   2020-03-10
 * @Version: V1.0
 */
@Data
@TableName("tb_client")
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
	/**创建人*/
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private java.util.Date createTime;
	/**更新人*/
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private java.util.Date updateTime;
	/**所属部门*/
    private java.lang.String sysOrgCode;
	/**编码*/
    private java.lang.String number;
	/**名称*/
    private java.lang.String name;
	/**类型*/
    private java.lang.String type;
	/**来源*/
    private java.lang.String sourceId;
	/**省*/
    private java.lang.String province;
	/**市*/
    private java.lang.String city;
	/**区*/
    private java.lang.String area;
	/**镇*/
    private java.lang.String community;
	/**详细地址*/
    private java.lang.String address;
	/**所属用户*/
    private java.lang.String userId;
	/**最近联系时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private java.util.Date lastContactTime;
	/**行业*/
    private java.lang.String industry;
	/**社会信用代码*/
    private java.lang.String creditCode;
	/**性质*/
    private java.lang.String property;
	/**法人代表*/
    private java.lang.String legalPerson;
	/**注册资金*/
    private java.lang.Double registeredCapital;
	/**成立日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date establishmentDate;
	/**网址*/
    private java.lang.String webSite;
}
