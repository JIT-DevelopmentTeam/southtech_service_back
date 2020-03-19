package org.jeecg.modules.management.servicevisits.service.impl;

import org.jeecg.modules.management.servicevisits.entity.ServiceVisits;
import org.jeecg.modules.management.servicevisits.mapper.ServiceVisitsMapper;
import org.jeecg.modules.management.servicevisits.service.IServiceVisitsService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 回访记录
 * @Author: jeecg-boot
 * @Date:   2020-03-17
 * @Version: V1.0
 */
@Service
public class ServiceVisitsServiceImpl extends ServiceImpl<ServiceVisitsMapper, ServiceVisits> implements IServiceVisitsService {

}
