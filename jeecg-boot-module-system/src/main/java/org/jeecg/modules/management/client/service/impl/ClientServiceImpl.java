package org.jeecg.modules.management.client.service.impl;

import org.jeecg.modules.management.client.entity.Client;
import org.jeecg.modules.management.client.entity.Contact;
import org.jeecg.modules.management.client.entity.DeviceNumber;
import org.jeecg.modules.management.client.mapper.ContactMapper;
import org.jeecg.modules.management.client.mapper.DeviceNumberMapper;
import org.jeecg.modules.management.client.mapper.ClientMapper;
import org.jeecg.modules.management.client.service.IClientService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 客户信息
 * @Author: jeecg-boot
 * @Date:   2020-03-10
 * @Version: V1.0
 */
@Service
public class ClientServiceImpl extends ServiceImpl<ClientMapper, Client> implements IClientService {

	@Autowired
	private ClientMapper clientMapper;
	@Autowired
	private ContactMapper contactMapper;
	@Autowired
	private DeviceNumberMapper deviceNumberMapper;
	
	@Override
	@Transactional
	public void delMain(String id) {
		contactMapper.deleteByMainId(id);
		deviceNumberMapper.deleteByMainId(id);
		clientMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			contactMapper.deleteByMainId(id.toString());
			deviceNumberMapper.deleteByMainId(id.toString());
			clientMapper.deleteById(id);
		}
	}
	
}
