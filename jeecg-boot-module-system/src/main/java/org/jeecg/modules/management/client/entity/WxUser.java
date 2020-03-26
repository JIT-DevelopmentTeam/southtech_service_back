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
 * @Description: 微信用户
 * @Author: jeecg-boot
 * @Date:   2020-03-23
 * @Version: V1.0
 */
@Data
@TableName("tb_wx_user")
public class WxUser implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
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
	/**标识*/
	@Excel(name = "标识", width = 15)
	private String openId;
	/**昵称*/
	@Excel(name = "昵称", width = 15)
	private String nickname;
	/**性别*/
	@Excel(name = "性别", width = 15)
	private String sex;
	/**城市*/
	@Excel(name = "城市", width = 15)
	private String city;
	/**国家*/
	@Excel(name = "国家", width = 15)
	private String country;
	/**省份*/
	@Excel(name = "省份", width = 15)
	private String province;
	/**备注*/
	@Excel(name = "备注", width = 15)
	private String remark;
	/**所属客户*/
	private String clientId;
}
