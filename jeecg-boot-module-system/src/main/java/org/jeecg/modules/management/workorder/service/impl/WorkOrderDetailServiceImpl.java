package org.jeecg.modules.management.workorder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.management.workorder.entity.WorkOrderDetail;
import org.jeecg.modules.management.workorder.mapper.WorkOrderDetailMapper;
import org.jeecg.modules.management.workorder.service.IWorkOrderDetailService;
import org.jeecg.modules.management.workorder.vo.MobileWorkOrderDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 工单明细
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
@Service
public class WorkOrderDetailServiceImpl extends ServiceImpl<WorkOrderDetailMapper, WorkOrderDetail> implements IWorkOrderDetailService {
	
	@Autowired
	private WorkOrderDetailMapper workOrderDetailMapper;
	
	@Override
	public List<WorkOrderDetail> selectByMainId(String mainId) {
		return workOrderDetailMapper.selectByMainId(mainId);
	}

	@Override
	public List<MobileWorkOrderDetailDTO> getByWorkOrderId(String workOrderId) {
		return workOrderDetailMapper.getByWorkOrderId(workOrderId);
	}

	@Override
	public void updateCurrentProgressByWorkOrderId(String currentProgress, String workOrderId) {
		workOrderDetailMapper.updateCurrentProgressByWorkOrderId(currentProgress, workOrderId);
	}
}
