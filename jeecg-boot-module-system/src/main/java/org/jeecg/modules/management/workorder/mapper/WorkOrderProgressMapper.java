package org.jeecg.modules.management.workorder.mapper;

import java.util.List;
import org.jeecg.modules.management.workorder.entity.WorkOrderProgress;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 工单进度
 * @Author: jeecg-boot
 * @Date:   2020-03-16
 * @Version: V1.0
 */
public interface WorkOrderProgressMapper extends BaseMapper<WorkOrderProgress> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<WorkOrderProgress> selectByMainId(@Param("mainId") String mainId);
}
