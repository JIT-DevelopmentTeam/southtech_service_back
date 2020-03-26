package org.jeecg.modules.management.client.service.impl;

import org.jeecg.modules.management.client.entity.WxUser;
import org.jeecg.modules.management.client.mapper.WxUserMapper;
import org.jeecg.modules.management.client.service.IWxUserService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 微信用户
 * @Author: jeecg-boot
 * @Date:   2020-03-23
 * @Version: V1.0
 */
@Service
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, WxUser> implements IWxUserService {
	
	@Autowired
	private WxUserMapper wxUserMapper;
	
	@Override
	public List<WxUser> selectByMainId(String mainId) {
		return wxUserMapper.selectByMainId(mainId);
	}
}
