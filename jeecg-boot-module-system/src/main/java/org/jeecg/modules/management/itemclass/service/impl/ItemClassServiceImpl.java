package org.jeecg.modules.management.itemclass.service.impl;

import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.management.itemclass.entity.ItemClass;
import org.jeecg.modules.management.itemclass.mapper.ItemClassMapper;
import org.jeecg.modules.management.itemclass.service.IItemClassService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 物料分类
 * @Author: jeecg-boot
 * @Date:   2020-03-11
 * @Version: V1.0
 */
@Service
public class ItemClassServiceImpl extends ServiceImpl<ItemClassMapper, ItemClass> implements IItemClassService {

	@Override
	public void addItemClass(ItemClass itemClass) {
		if(oConvertUtils.isEmpty(itemClass.getPid())){
			itemClass.setPid(IItemClassService.ROOT_PID_VALUE);
		}else{
			//如果当前节点父ID不为空 则设置父节点的hasChildren 为1
			ItemClass parent = baseMapper.selectById(itemClass.getPid());
			if(parent!=null && !"1".equals(parent.getHasChild())){
				parent.setHasChild("1");
				baseMapper.updateById(parent);
			}
		}
		baseMapper.insert(itemClass);
	}
	
	@Override
	public void updateItemClass(ItemClass itemClass) {
		ItemClass entity = this.getById(itemClass.getId());
		if(entity==null) {
			throw new JeecgBootException("未找到对应实体");
		}
		String old_pid = entity.getPid();
		String new_pid = itemClass.getPid();
		if(!old_pid.equals(new_pid)) {
			updateOldParentNode(old_pid);
			if(oConvertUtils.isEmpty(new_pid)){
				itemClass.setPid(IItemClassService.ROOT_PID_VALUE);
			}
			if(!IItemClassService.ROOT_PID_VALUE.equals(itemClass.getPid())) {
				baseMapper.updateTreeNodeStatus(itemClass.getPid(), IItemClassService.HASCHILD);
			}
		}
		baseMapper.updateById(itemClass);
	}
	
	@Override
	public void deleteItemClass(String id) throws JeecgBootException {
		ItemClass itemClass = this.getById(id);
		if(itemClass==null) {
			throw new JeecgBootException("未找到对应实体");
		}
		updateOldParentNode(itemClass.getPid());
		baseMapper.deleteById(id);
	}
	
	
	
	/**
	 * 根据所传pid查询旧的父级节点的子节点并修改相应状态值
	 * @param pid
	 */
	private void updateOldParentNode(String pid) {
		if(!IItemClassService.ROOT_PID_VALUE.equals(pid)) {
			Integer count = baseMapper.selectCount(new QueryWrapper<ItemClass>().eq("pid", pid));
			if(count==null || count<=1) {
				baseMapper.updateTreeNodeStatus(pid, IItemClassService.NOCHILD);
			}
		}
	}

}
