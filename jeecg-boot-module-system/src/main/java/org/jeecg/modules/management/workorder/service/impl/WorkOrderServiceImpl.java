package org.jeecg.modules.management.workorder.service.impl;

import org.jeecg.modules.management.workorder.entity.WorkOrder;
import org.jeecg.modules.management.workorder.entity.WorkOrderDetail;
import org.jeecg.modules.management.workorder.mapper.WorkOrderDetailMapper;
import org.jeecg.modules.management.workorder.mapper.WorkOrderMapper;
import org.jeecg.modules.management.workorder.service.IWorkOrderService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 工单信息
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
@Service
public class WorkOrderServiceImpl extends ServiceImpl<WorkOrderMapper, WorkOrder> implements IWorkOrderService {

	@Autowired
	private WorkOrderMapper workOrderMapper;
	@Autowired
	private WorkOrderDetailMapper workOrderDetailMapper;
	
	@Override
	@Transactional
	public void delMain(String id) {
		workOrderDetailMapper.deleteByMainId(id);
		workOrderMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			workOrderDetailMapper.deleteByMainId(id.toString());
			workOrderMapper.deleteById(id);
		}
	}

    @Override
    @Transactional
    public void saveMain(WorkOrder workOrder, List<WorkOrderDetail> workOrderDetailList) {
        workOrderMapper.insert(workOrder);
        if (workOrderDetailList != null) {
            for (WorkOrderDetail entity : workOrderDetailList) {
                entity.setWorkOrderId(workOrder.getId());
                workOrderDetailMapper.insert(entity);
            }
        }
    }

    @Override
    @Transactional
    public void updateMain(WorkOrder workOrder, List<WorkOrderDetail> workOrderDetailList) {
        workOrderMapper.updateById(workOrder);

        //1.先删除子表数据
        workOrderDetailMapper.deleteByMainId(workOrder.getId());

        //2.子表数据重新插入
        if (workOrderDetailList != null) {
            for (WorkOrderDetail entity : workOrderDetailList) {
                entity.setWorkOrderId(workOrder.getId());
                workOrderDetailMapper.insert(entity);
            }
        }
    }

}
