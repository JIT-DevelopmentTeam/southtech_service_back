package org.jeecg.modules.management.stage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.management.stage.entity.Stage;
import org.jeecg.modules.management.stage.vo.MobileStageDTO;

import java.util.List;

/**
 * @Description: 阶段信息
 * @Author: jeecg-boot
 * @Date:   2020-03-16
 * @Version: V1.0
 */
public interface IStageService extends IService<Stage> {

    List<MobileStageDTO> queryStageByWorkOrderId(String workOrderId);

}
