package org.jeecg.modules.management.workorder.service.impl;

import org.jeecg.modules.management.workorder.entity.WorkOrderDetail;
import org.jeecg.modules.management.workorder.mapper.WorkOrderDetailMapper;
import org.jeecg.modules.management.workorder.service.IWorkOrderDetailService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

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
}
