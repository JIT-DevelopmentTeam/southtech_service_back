package org.jeecg.modules.management.workorder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.management.workorder.entity.WorkOrderProgress;
import org.jeecg.modules.management.workorder.mapper.WorkOrderProgressMapper;
import org.jeecg.modules.management.workorder.service.IWorkOrderProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

	@Override
	public void updateFinishTimeById(Date finishTime, String progressId) {
		workOrderProgressMapper.updateFinishTimeById(finishTime, progressId);
	}
}
