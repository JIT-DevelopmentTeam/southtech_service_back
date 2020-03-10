package org.jeecg.modules.management.client.service;

import org.jeecg.modules.management.client.entity.DeviceNumber;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 设备编号
 * @Author: jeecg-boot
 * @Date:   2020-03-10
 * @Version: V1.0
 */
public interface IDeviceNumberService extends IService<DeviceNumber> {

	public List<DeviceNumber> selectByMainId(String mainId);
}
