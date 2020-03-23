package org.jeecg.modules.management.client.mapper;

import java.util.List;
import org.jeecg.modules.management.client.entity.WxUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 微信用户
 * @Author: jeecg-boot
 * @Date:   2020-03-23
 * @Version: V1.0
 */
public interface WxUserMapper extends BaseMapper<WxUser> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<WxUser> selectByMainId(@Param("mainId") String mainId);

}
