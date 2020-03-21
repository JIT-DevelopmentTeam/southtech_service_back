package org.jeecg.modules.management.client.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.jeecg.modules.management.BaseEntity;
import org.jeecgframework.poi.excel.annotation.Excel;
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
public class Client extends BaseEntity implements Serializable {
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
	/**行业*/
    @Excel(name = "行业", width = 15)
    private java.lang.String industry;
	/**性质*/
    @Excel(name = "性质", width = 15)
    private java.lang.String property;
	/**经度*/
    @Excel(name = "经度", width = 15)
    private java.lang.Double longitude;
	/**纬度*/
    @Excel(name = "纬度", width = 15)
    private java.lang.Double latitude;


}
