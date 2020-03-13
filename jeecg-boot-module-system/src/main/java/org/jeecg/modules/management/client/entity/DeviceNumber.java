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
 * @Description: 设备编号
 * @Author: jeecg-boot
 * @Date:   2020-03-10
 * @Version: V1.0
 */
@Data
@TableName("tb_device_number")
public class DeviceNumber implements Serializable {
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
	/**名称*/
	@Excel(name = "名称", width = 15)
	private java.lang.String name;
	/**类型*/
	@Excel(name = "类型", width = 15)
	private java.lang.String type;
	/**描述*/
	@Excel(name = "描述", width = 15)
	private java.lang.String description;
    /**保质期至*/
    @Excel(name = "保质期至", width = 15)
    private java.util.Date qgp;
    /**保质期至*/
    @Excel(name = "签约时间", width = 15)
    private java.util.Date signing;
	/**时间戳*/
	@Excel(name = "时间戳", width = 15)
	private java.sql.Timestamp modifyTime;
	/**所属客户*/
	private java.lang.String clientId;
}
