package org.jeecg.modules.management.workorder.service.impl;

import org.jeecg.modules.management.workorder.entity.WorkOrderProgress;
import org.jeecg.modules.management.workorder.mapper.WorkOrderProgressMapper;
import org.jeecg.modules.management.workorder.service.IWorkOrderProgressService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 工单进度
 * @Author: jeecg-boot
 * @Date:   2020-03-16
 * @Version: V1.0
 */
@Service
public class WorkOrderProgressServiceImpl extends ServiceImpl<WorkOrderProgressMapper, WorkOrderProgress> implements IWorkOrderProgressService {
	
	@Autowired
	private WorkOrderProgressMapper workOrderProgressMapper;
	
	@Override
	public List<WorkOrderProgress> selectByMainId(String mainId) {
		return workOrderProgressMapper.selectByMainId(mainId);
	}
}
