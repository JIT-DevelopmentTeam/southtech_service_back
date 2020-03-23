package org.jeecg.modules.management.mobile.token.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: token表
 * @Author: jeecg-boot
 * @Date:   2020-03-23
 * @Version: V1.0
 */
@Data
@TableName("tb_token")
public class Token implements Serializable {
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
	/**token值*/
	@Excel(name = "token值", width = 15)
    private String token;
	/**token有效时间*/
	@Excel(name = "token有效时间", width = 15)
    private String times;
	/**token所属*/
	@Excel(name = "token所属", width = 15)
    private String emplid;
	/**终端id*/
	@Excel(name = "终端id", width = 15)
    private String clientid;
	/**token生成终端*/
	@Excel(name = "token生成终端", width = 15)
    private String clienttype;
	/**生成规则*/
	@Excel(name = "生成规则", width = 15)
    private String rule;
	/**是否已禁用*/
	@Excel(name = "是否已禁用", width = 15)
    private String status;
	/**是否过期*/
	@Excel(name = "是否过期", width = 15)
    private String timeout;
	/**生成时间*/
	@Excel(name = "生成时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date starttime;
}
