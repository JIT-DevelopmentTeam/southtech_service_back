package org.jeecg.modules.management.stage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.management.stage.entity.Stage;
import org.jeecg.modules.management.stage.vo.MobileStageDTO;

import java.util.List;

/**
 * @Description: 阶段信息
 * @Author: jeecg-boot
 * @Date:   2020-03-16
 * @Version: V1.0
 */
public interface StageMapper extends BaseMapper<Stage> {

    /**
     * 根据工单id查询进度列表
     * @param workOrderId
     * @return
     */
    List<MobileStageDTO> queryStageByWorkOrderId(@Param(value = "workOrderId") String workOrderId);

}
