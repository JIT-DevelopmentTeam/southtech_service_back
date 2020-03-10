package org.jeecg.modules.management.client.mapper;

import java.util.List;
import org.jeecg.modules.management.client.entity.DeviceNumber;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 设备编号
 * @Author: jeecg-boot
 * @Date:   2020-03-10
 * @Version: V1.0
 */
public interface DeviceNumberMapper extends BaseMapper<DeviceNumber> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<DeviceNumber> selectByMainId(@Param("mainId") String mainId);
}
