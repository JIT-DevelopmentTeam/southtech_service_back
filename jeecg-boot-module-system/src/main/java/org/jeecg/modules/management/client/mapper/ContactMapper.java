package org.jeecg.modules.management.client.mapper;

import java.util.List;
import org.jeecg.modules.management.client.entity.Contact;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 联系人信息
 * @Author: jeecg-boot
 * @Date:   2020-03-10
 * @Version: V1.0
 */
public interface ContactMapper extends BaseMapper<Contact> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<Contact> selectByMainId(@Param("mainId") String mainId);
}
