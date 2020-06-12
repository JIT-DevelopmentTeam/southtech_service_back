package org.jeecg.modules.management.workorder.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.management.workorder.entity.WorkOrder;
import org.jeecg.modules.management.workorder.entity.WorkOrderDetail;
import org.jeecg.modules.management.workorder.vo.MobileWorkOrderDTO;
import org.jeecg.modules.management.workorder.vo.WorkOrderDTO;

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


	/**
	 * 查询出所有待分派的工单（过滤条件：type）
	 * @param type
	 * @return
	 */
	Page<WorkOrderDTO> queryListByType(Page<WorkOrderDTO> page, String type);

	/**
	 * 钉钉-查询所有工单
	 * @param page
	 * @return
	 */
	Page<MobileWorkOrderDTO> queryMobileList(Page<MobileWorkOrderDTO> page, String userName, String status);

	/**
	 * 钉钉-根据工单id查询附件
	 * @param workOrderId
	 * @return
	 */
	WorkOrder getWorkOrderAnnex(String workOrderId);

	/**
	 * 服务号-查询所有工单
	 * @param page
	 * @param status
	 * @return
	 */
	Page<MobileWorkOrderDTO> queryWXworkOrderList(Page<MobileWorkOrderDTO> page, String openId, String status);

    /**
     * 获取当前客户未评价工单
     * @param clientId
     * @param status
     * @return
     */
	List<WorkOrder> queryWorkOrderByComment(String clientId,String status);

	List<WorkOrder> queryTimeoutWorkOrder();
}
