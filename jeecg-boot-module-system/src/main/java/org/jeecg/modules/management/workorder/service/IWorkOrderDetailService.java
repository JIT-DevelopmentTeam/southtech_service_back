package org.jeecg.modules.management.workorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.management.workorder.entity.WorkOrderDetail;
import org.jeecg.modules.management.workorder.vo.MobileWorkOrderDetailDTO;

import java.util.Date;
import java.util.List;

/**
 * @Description: 工单明细
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
public interface IWorkOrderDetailService extends IService<WorkOrderDetail> {

	List<WorkOrderDetail> selectByMainId(String mainId);

	List<MobileWorkOrderDetailDTO> getByWorkOrderId(String workOrderId);

	void updateCurrentProgressByWorkOrderId(String currentProgress, String workOrderId);

	void updateComlpetedTimeByWorkOrderId(Date ACT, String workOrderId);

}
