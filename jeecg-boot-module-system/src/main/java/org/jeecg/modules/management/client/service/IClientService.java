package org.jeecg.modules.management.client.service;

import org.jeecg.modules.management.client.entity.Contact;
import org.jeecg.modules.management.client.entity.DeviceNumber;
import org.jeecg.modules.management.client.entity.Client;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 客户信息
 * @Author: jeecg-boot
 * @Date:   2020-03-10
 * @Version: V1.0
 */
public interface IClientService extends IService<Client> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(Client client,List<Contact> contactList,List<DeviceNumber> deviceNumberList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(Client client,List<Contact> contactList,List<DeviceNumber> deviceNumberList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
