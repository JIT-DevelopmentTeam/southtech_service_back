package org.jeecg.modules.management.workorder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.management.workorder.entity.WorkOrderProgress;

import java.util.Date;
import java.util.List;

/**
 * @Description: 工单进度
 * @Author: jeecg-boot
 * @Date:   2020-03-16
 * @Version: V1.0
 */
public interface WorkOrderProgressMapper extends BaseMapper<WorkOrderProgress> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<WorkOrderProgress> selectByMainId(@Param("mainId") String mainId);

	void updateFinishTimeById(@Param("finishTime") Date finishTime, @Param("progressId") String progressId);
}
