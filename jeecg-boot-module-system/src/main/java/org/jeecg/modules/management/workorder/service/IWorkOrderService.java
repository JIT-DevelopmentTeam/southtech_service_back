package org.jeecg.modules.management.workorder.service;

import org.jeecg.modules.management.workorder.entity.WorkOrderDetail;
import org.jeecg.modules.management.workorder.entity.WorkOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 工单信息
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
public interface IWorkOrderService extends IService<WorkOrder> {

	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);

    /**
     * 添加一对多
     *
     */
    public void saveMain(WorkOrder workOrder,List<WorkOrderDetail> workOrderDetailList) ;

    /**
     * 修改一对多
     *
     */
    public void updateMain(WorkOrder workOrder,List<WorkOrderDetail> workOrderDetailList);


}
