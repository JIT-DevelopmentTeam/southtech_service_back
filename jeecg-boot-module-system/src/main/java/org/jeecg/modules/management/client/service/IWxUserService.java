package org.jeecg.modules.management.client.service;

import org.jeecg.modules.management.client.entity.WxUser;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 微信用户
 * @Author: jeecg-boot
 * @Date:   2020-03-23
 * @Version: V1.0
 */
public interface IWxUserService extends IService<WxUser> {

	public List<WxUser> selectByMainId(String mainId);
}
