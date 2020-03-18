package org.jeecg.modules.management.workorder.mapper;

import java.util.List;
import org.jeecg.modules.management.workorder.entity.WorkOrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 工单明细
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
public interface WorkOrderDetailMapper extends BaseMapper<WorkOrderDetail> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<WorkOrderDetail> selectByMainId(@Param("mainId") String mainId);

}
