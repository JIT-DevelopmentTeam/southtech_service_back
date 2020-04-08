package org.jeecg.modules.management.workorder.service;

import org.jeecg.modules.management.workorder.entity.ServiceCommentery;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 服务评价
 * @Author: jeecg-boot
 * @Date:   2020-04-07
 * @Version: V1.0
 */
public interface IServiceCommenteryService extends IService<ServiceCommentery> {

	public List<ServiceCommentery> selectByMainId(String mainId);
}
