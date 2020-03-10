package org.jeecg.modules.management.client.service;

import org.jeecg.modules.management.client.entity.Contact;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 联系人信息
 * @Author: jeecg-boot
 * @Date:   2020-03-10
 * @Version: V1.0
 */
public interface IContactService extends IService<Contact> {

	public List<Contact> selectByMainId(String mainId);
}
