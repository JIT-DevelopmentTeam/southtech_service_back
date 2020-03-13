package org.jeecg.modules.management.item.service.impl;

import org.jeecg.modules.management.item.entity.Item;
import org.jeecg.modules.management.item.mapper.ItemMapper;
import org.jeecg.modules.management.item.service.IItemService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 物料
 * @Author: jeecg-boot
 * @Date:   2020-03-11
 * @Version: V1.0
 */
@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements IItemService {

}
