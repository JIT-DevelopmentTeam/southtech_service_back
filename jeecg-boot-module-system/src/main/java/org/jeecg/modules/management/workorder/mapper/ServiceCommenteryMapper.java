package org.jeecg.modules.management.workorder.mapper;

import java.util.List;
import org.jeecg.modules.management.workorder.entity.ServiceCommentery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 服务评价
 * @Author: jeecg-boot
 * @Date:   2020-04-07
 * @Version: V1.0
 */
public interface ServiceCommenteryMapper extends BaseMapper<ServiceCommentery> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<ServiceCommentery> selectByMainId(@Param("mainId") String mainId);

}
