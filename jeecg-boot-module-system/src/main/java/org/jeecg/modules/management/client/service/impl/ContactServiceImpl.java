package org.jeecg.modules.management.client.service.impl;

import org.jeecg.modules.management.client.entity.Contact;
import org.jeecg.modules.management.client.mapper.ContactMapper;
import org.jeecg.modules.management.client.service.IContactService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 联系人信息
 * @Author: jeecg-boot
 * @Date:   2020-03-10
 * @Version: V1.0
 */
@Service
public class ContactServiceImpl extends ServiceImpl<ContactMapper, Contact> implements IContactService {
	
	@Autowired
	private ContactMapper contactMapper;
	
	@Override
	public List<Contact> selectByMainId(String mainId) {
		return contactMapper.selectByMainId(mainId);
	}
}
