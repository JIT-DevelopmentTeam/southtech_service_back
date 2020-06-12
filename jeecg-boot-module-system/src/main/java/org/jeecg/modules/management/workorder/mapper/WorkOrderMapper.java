package org.jeecg.modules.management.workorder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.management.workorder.entity.WorkOrder;
import org.jeecg.modules.management.workorder.entity.WorkOrderPageDTO;
import org.jeecg.modules.management.workorder.vo.MobileWorkOrderDTO;
import org.jeecg.modules.management.workorder.vo.WorkOrderDTO;

import java.util.List;

/**
 * @Description: 工单信息
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
public interface WorkOrderMapper extends BaseMapper<WorkOrder> {

    /**
     * 查询出所有待分派的工单（过滤条件：type）
     * @param type
     * @return
     */
    List<WorkOrderDTO> queryListByType(Page<WorkOrderDTO> page, @Param(value = "type") String type);

    /**
     * 钉钉-查询所有工单
     * @param page
     * @return
     */
    List<MobileWorkOrderDTO> queryMobileList(Page<MobileWorkOrderDTO> page, @Param(value = "userName") String userName, @Param(value = "status") String status);

    /**
     * 钉钉-根据工单id查询附件
     * @param workOrderId
     * @return
     */
    WorkOrder getWorkOrderAnnex(@Param(value = "workOrderId") String workOrderId);

    /**
     * 服务号-查询所有工单
     * @param page
     * @param status
     * @return
     */
    List<MobileWorkOrderDTO> queryWXworkOrderList(Page<MobileWorkOrderDTO> page, @Param("openId") String openId, @Param("status") String status);

    /**
     * 获取当前客户未评价工单
     * @param clientId
     * @param status
     * @return
     */
    List<WorkOrder> queryWorkOrderByComment(@Param("clientId") String clientId,@Param("status") String status);

    @Select("SELECT\n" +
            "\tworkOrder.id,\n" +
            "\tworkOrder.number,\n" +
            "\tworkOrder.`status`,\n" +
            "\tworkOrder.type,\n" +
            "\tworkOrder.access_method,\n" +
            "\tworkOrder.client_id,\n" +
            "\tworkOrder.contact_id,\n" +
            "\tworkOrder.emergency_level,\n" +
            "\tworkOrder.customer_service_name,\n" +
            "\tworkOrder.declaration_time,\n" +
            "\tworkOrderDetail.device_number,\n" +
            "\tworkOrderDetail.service_engineer_name,\n" +
            "\tworkOrderDetail.fault_location,\n" +
            "\tworkOrderDetail.peers,\n" +
            "\tworkOrderDetail.appointment,\n" +
            "\tworkOrderDetail.planned_completion_time,\n" +
            "\tworkOrderDetail.id AS 'workOrderDetailId' \n" +
            "FROM\n" +
            "\tic_work_order_detail workOrderDetail\n" +
            "\tINNER JOIN ic_work_order workOrder ON workOrderDetail.work_order_id = workOrder.id")
    List<WorkOrderPageDTO> queryListPage(WorkOrderPageDTO workOrderPageDTO);

    List<WorkOrder> queryTimeoutWorkOrder();

}
