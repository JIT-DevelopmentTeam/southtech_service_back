package org.jeecg.modules.management.workorder.service;

import org.jeecg.modules.management.workorder.entity.WorkOrderProgress;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 工单进度
 * @Author: jeecg-boot
 * @Date:   2020-03-16
 * @Version: V1.0
 */
public interface IWorkOrderProgressService extends IService<WorkOrderProgress> {

	public List<WorkOrderProgress> selectByMainId(String mainId);
}
