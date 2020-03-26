package org.jeecg.modules.management.workorder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.management.stage.entity.Stage;
import org.jeecg.modules.management.stage.mapper.StageMapper;
import org.jeecg.modules.management.workorder.entity.WorkOrder;
import org.jeecg.modules.management.workorder.entity.WorkOrderDetail;
import org.jeecg.modules.management.workorder.entity.WorkOrderProgress;
import org.jeecg.modules.management.workorder.mapper.WorkOrderDetailMapper;
import org.jeecg.modules.management.workorder.mapper.WorkOrderMapper;
import org.jeecg.modules.management.workorder.mapper.WorkOrderProgressMapper;
import org.jeecg.modules.management.workorder.service.IWorkOrderService;
import org.jeecg.modules.management.workorder.vo.WorkOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

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
    @Autowired
    private WorkOrderProgressMapper workOrderProgressMapper;
    @Autowired
    private StageMapper stageMapper;

	@Override
	@Transactional
	public void delMain(String id) {
		workOrderDetailMapper.deleteByMainId(id);
		workOrderProgressMapper.deleteByMainId(id);
		workOrderMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			workOrderDetailMapper.deleteByMainId(id.toString());
            workOrderProgressMapper.deleteByMainId(id.toString());
			workOrderMapper.deleteById(id);
		}
	}

    @Override
    @Transactional
    public void saveMain(WorkOrder workOrder, List<WorkOrderDetail> workOrderDetailList) {
	    workOrder.setStatus("1");
        workOrderMapper.insert(workOrder);
        if (workOrderDetailList != null) {
            for (WorkOrderDetail entity : workOrderDetailList) {
                entity.setWorkOrderId(workOrder.getId());
                workOrderDetailMapper.insert(entity);
            }
        }
        QueryWrapper<Stage> stageQueryWrapper = new QueryWrapper<>();
        stageQueryWrapper.eq("work_order_type",workOrder.getType());
        stageQueryWrapper.orderByAsc("order_index");
        List<Stage> stageList = stageMapper.selectList(stageQueryWrapper);
        for (int i = 0; i < stageList.size(); i++) {
            WorkOrderProgress workOrderProgress = new WorkOrderProgress();
            if (i == 0) {
                workOrderProgress.setFinishTime(DateUtils.getDate());
            }
            workOrderProgress.setName(stageList.get(i).getStageName());
            workOrderProgress.setStageId(stageList.get(i).getId());
            workOrderProgress.setWorkOrderId(workOrder.getId());
            workOrderProgressMapper.insert(workOrderProgress);
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

    /**
     * 查询出所有待分派的工单（过滤条件：type）
     * @param type
     * @return
     */
    @Override
    public Page<WorkOrderDTO> queryListByType(Page<WorkOrderDTO> page, String type) {
        return page.setRecords(workOrderMapper.queryListByType(page, type));
    }
}
