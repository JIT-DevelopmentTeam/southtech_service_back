package org.jeecg.modules.management.workorder.service.impl;

import org.jeecg.modules.management.workorder.entity.ServiceCommentery;
import org.jeecg.modules.management.workorder.mapper.ServiceCommenteryMapper;
import org.jeecg.modules.management.workorder.service.IServiceCommenteryService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 服务评价
 * @Author: jeecg-boot
 * @Date:   2020-04-07
 * @Version: V1.0
 */
@Service
public class ServiceCommenteryServiceImpl extends ServiceImpl<ServiceCommenteryMapper, ServiceCommentery> implements IServiceCommenteryService {
	
	@Autowired
	private ServiceCommenteryMapper serviceCommenteryMapper;
	
	@Override
	public List<ServiceCommentery> selectByMainId(String mainId) {
		return serviceCommenteryMapper.selectByMainId(mainId);
	}
}
