package org.jeecg.modules.management.itemclass.service;

import org.jeecg.modules.management.itemclass.entity.ItemClass;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.exception.JeecgBootException;

/**
 * @Description: 物料分类
 * @Author: jeecg-boot
 * @Date:   2020-03-11
 * @Version: V1.0
 */
public interface IItemClassService extends IService<ItemClass> {

	/**根节点父ID的值*/
	public static final String ROOT_PID_VALUE = "0";
	
	/**树节点有子节点状态值*/
	public static final String HASCHILD = "1";
	
	/**树节点无子节点状态值*/
	public static final String NOCHILD = "0";

	/**新增节点*/
	void addItemClass(ItemClass itemClass);
	
	/**修改节点*/
	void updateItemClass(ItemClass itemClass) throws JeecgBootException;
	
	/**删除节点*/
	void deleteItemClass(String id) throws JeecgBootException;

}
