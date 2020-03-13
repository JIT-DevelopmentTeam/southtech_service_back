package org.jeecg.modules.management.itemclass.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.management.itemclass.entity.ItemClass;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 物料分类
 * @Author: jeecg-boot
 * @Date:   2020-03-11
 * @Version: V1.0
 */
public interface ItemClassMapper extends BaseMapper<ItemClass> {

	/**
	 * 编辑节点状态
	 * @param id
	 * @param status
	 */
	void updateTreeNodeStatus(@Param("id") String id, @Param("status") String status);

}
