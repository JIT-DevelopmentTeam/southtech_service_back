package org.jeecg.modules.management.client.service.impl;

import org.jeecg.modules.management.client.entity.DeviceNumber;
import org.jeecg.modules.management.client.mapper.DeviceNumberMapper;
import org.jeecg.modules.management.client.service.IDeviceNumberService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 设备编号
 * @Author: jeecg-boot
 * @Date:   2020-03-10
 * @Version: V1.0
 */
@Service
public class DeviceNumberServiceImpl extends ServiceImpl<DeviceNumberMapper, DeviceNumber> implements IDeviceNumberService {
	
	@Autowired
	private DeviceNumberMapper deviceNumberMapper;
	
	@Override
	public List<DeviceNumber> selectByMainId(String mainId) {
		return deviceNumberMapper.selectByMainId(mainId);
	}
}
