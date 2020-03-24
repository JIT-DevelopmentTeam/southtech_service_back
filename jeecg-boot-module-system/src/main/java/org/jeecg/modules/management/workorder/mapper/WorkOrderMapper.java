package org.jeecg.modules.management.workorder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.management.workorder.entity.WorkOrder;
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

}
