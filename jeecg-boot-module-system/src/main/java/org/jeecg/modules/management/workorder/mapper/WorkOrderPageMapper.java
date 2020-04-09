package org.jeecg.modules.management.workorder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
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
public interface WorkOrderPageMapper extends BaseMapper<WorkOrderPageDTO> {


}
