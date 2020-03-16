package org.jeecg.modules.management.client.controller;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.system.query.QueryGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.HttpHelper;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.management.erp.erpinterface.ERPInterfaceConstant;
import org.jeecg.modules.management.workorder.entity.WorkOrder;
import org.jeecg.modules.management.workorder.service.IWorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jeecg.modules.management.client.entity.Contact;
import org.jeecg.modules.management.client.entity.DeviceNumber;
import org.jeecg.modules.management.client.entity.Client;
import org.jeecg.modules.management.client.service.IClientService;
import org.jeecg.modules.management.client.service.IContactService;
import org.jeecg.modules.management.client.service.IDeviceNumberService;


 /**
 * @Description: 客户信息
 * @Author: jeecg-boot
 * @Date:   2020-03-10
 * @Version: V1.0
 */
@RestController
@RequestMapping("/client/client")
@Slf4j
public class ClientController extends JeecgController<Client, IClientService> {

	@Autowired
	private IClientService clientService;

	@Autowired
	private IContactService contactService;

	@Autowired
	private IDeviceNumberService deviceNumberService;

     @Autowired
     private RedisUtil redisUtil;

     @Autowired
     private IWorkOrderService workOrderService;


	/*---------------------------------主表处理-begin-------------------------------------*/

	/**
	 * 分页列表查询
	 * @param client
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Client client,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
	    if (StringUtils.isNotBlank(client.getNumber())) {
	        client.setNumber("*"+client.getNumber().trim()+"*");
        }
        if (StringUtils.isNotBlank(client.getName())) {
            client.setName("*"+client.getName().trim()+"*");
        }
		QueryWrapper<Client> queryWrapper = QueryGenerator.initQueryWrapper(client, req.getParameterMap());
		Page<Client> page = new Page<Client>(pageNo, pageSize);
		IPage<Client> pageList = clientService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
     *   添加
     * @param client
     * @return
     */
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody Client client) {
        clientService.save(client);
        return Result.ok("添加成功！");
    }

    /**
     *  编辑
     * @param client
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody Client client) {
        clientService.updateById(client);
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name="id",required=true) String id) {
        clientService.delMain(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        this.clientService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Client client) {
        return super.exportXls(request, client, Client.class, "客户信息");
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Client.class);
    }

     /**
      * 同步
      * @return
      */
    @RequestMapping(value = "/synchronizeClient", method = RequestMethod.GET)
    public Result<?> synchronizeClient() {
        QueryWrapper<Client> clientQueryWrapper = new QueryWrapper<>();
        clientQueryWrapper.orderByDesc("modify_time");
        List<Client> clientList = clientService.list(clientQueryWrapper);
        try {
            JSONObject jsonObject;
            if (clientList.isEmpty()) {
                jsonObject = HttpHelper.httpGet(ERPInterfaceConstant.LIST_CLIENT_URL.replace("TOKEN",redisUtil.get(ERPInterfaceConstant.TOKEN_KEY).toString()).replace("MAX",""));
            } else {
                jsonObject = HttpHelper.httpGet(ERPInterfaceConstant.LIST_CLIENT_URL.replace("TOKEN",redisUtil.get(ERPInterfaceConstant.TOKEN_KEY).toString()).replace("MAX",clientList.get(0).getModifyTime().getTime()+""));
            }
            JSONArray dataArray = jsonObject.getJSONArray("data");
            DateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (int i = 0; i < dataArray.size(); i++) {
                JSONObject data = dataArray.getJSONObject(i);
                Client client = new Client();
                client.setNumber(data.getString("FNumber"));
                client.setName(data.getString("FName"));
                client.setType("1");
                client.setCreateTime(dataFormat.parse(data.getString("FRegDate")));
                client.setModifyTime(data.getTimestamp("FModifyTime"));
                clientService.save(client);
                // TODO ERP联系人
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("同步客户出错!");
            return Result.error("同步出错,请联系管理员!");
        }
        return Result.ok("同步成功!");
    }

     /**
      * 获取工单客户
      * @return
      */
    @GetMapping(value = "listByWorkOrder")
    public Result<?> listByWorkOrder() {
        Result<List<Client>> result = new Result<>();
        List<WorkOrder> workOrderList = workOrderService.list();
        List<String> clientIdsList = new ArrayList<>();
        for (WorkOrder workOrder : workOrderList) {
            clientIdsList.add(workOrder.getClientId());
        }
        if (clientIdsList.isEmpty()) {
            result.setSuccess(false);
            return result;
        }
        QueryWrapper<Client> clientQueryWrapper = new QueryWrapper<>();
        clientQueryWrapper.in("id",clientIdsList);
        List<Client> clientList = clientService.list(clientQueryWrapper);
        result.setSuccess(true);
        result.setResult(clientList);
        return result;
    }

	/*---------------------------------主表处理-end-------------------------------------*/
	

    /*--------------------------------子表处理-联系人信息-begin----------------------------------------------*/
	/**
	 * 查询子表信息 会传入主表ID
	 * @return
	 */
	@GetMapping(value = "/listContactByMainId")
    public Result<?> listContactByMainId(Contact contact,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<Contact> queryWrapper = QueryGenerator.initQueryWrapper(contact, req.getParameterMap());
        Page<Contact> page = new Page<Contact>(pageNo, pageSize);
        IPage<Contact> pageList = contactService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

	/**
	 * 添加
	 * @param contact
	 * @return
	 */
	@PostMapping(value = "/addContact")
	public Result<?> addContact(@RequestBody Contact contact) {
		contactService.save(contact);
		return Result.ok("添加成功！");
	}

    /**
	 * 编辑
	 * @param contact
	 * @return
	 */
	@PutMapping(value = "/editContact")
	public Result<?> editContact(@RequestBody Contact contact) {
		contactService.updateById(contact);
		return Result.ok("编辑成功!");
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/deleteContact")
	public Result<?> deleteContact(@RequestParam(name="id",required=true) String id) {
		contactService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@DeleteMapping(value = "/deleteBatchContact")
	public Result<?> deleteBatchContact(@RequestParam(name="ids",required=true) String ids) {
		this.contactService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
	}

    /*--------------------------------子表处理-联系人信息-end----------------------------------------------*/

    /*--------------------------------子表处理-设备编号-begin----------------------------------------------*/

	/**
	 * 查询子表信息 会传入主表ID
	 * @return
	 */
	@GetMapping(value = "/listDeviceNumberByMainId")
    public Result<?> listDeviceNumberByMainId(DeviceNumber deviceNumber,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
	    if (StringUtils.isNotBlank(deviceNumber.getNumber())) {
	        deviceNumber.setNumber(deviceNumber.getNumber().trim());
        }
        if (StringUtils.isNotBlank(deviceNumber.getName())) {
            deviceNumber.setName(deviceNumber.getName().trim());
        }
        if (StringUtils.isNotBlank(deviceNumber.getType())) {
            deviceNumber.setType(deviceNumber.getType().trim());
        }
        QueryWrapper<DeviceNumber> queryWrapper = QueryGenerator.initQueryWrapper(deviceNumber, req.getParameterMap());
        if (StringUtils.isNotBlank(req.getParameter("clientName"))) {
            QueryWrapper<Client> clientQueryWrapper = new QueryWrapper<>();
            clientQueryWrapper.like("name",req.getParameter("clientName").trim());
            List<Client> clientList = clientService.list(clientQueryWrapper);
            if (!clientList.isEmpty()) {
                List<String> clientIdsList = new ArrayList<>();
                for (Client client : clientList) {
                    clientIdsList.add(client.getId());
                }
                queryWrapper.in("client_id",clientIdsList);
            } else {
                queryWrapper.eq("client_id","0");
            }
        }
        Page<DeviceNumber> page = new Page<DeviceNumber>(pageNo, pageSize);
        IPage<DeviceNumber> pageList = deviceNumberService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

	/**
	 * 添加
	 * @param deviceNumber
	 * @return
	 */
	@PostMapping(value = "/addDeviceNumber")
	public Result<?> addDeviceNumber(@RequestBody DeviceNumber deviceNumber) {
		deviceNumberService.save(deviceNumber);
		return Result.ok("添加成功！");
	}

    /**
	 * 编辑
	 * @param deviceNumber
	 * @return
	 */
	@PutMapping(value = "/editDeviceNumber")
	public Result<?> editDeviceNumber(@RequestBody DeviceNumber deviceNumber) {
		deviceNumberService.updateById(deviceNumber);
		return Result.ok("编辑成功!");
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/deleteDeviceNumber")
	public Result<?> deleteDeviceNumber(@RequestParam(name="id",required=true) String id) {
		deviceNumberService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@DeleteMapping(value = "/deleteBatchDeviceNumber")
	public Result<?> deleteBatchDeviceNumber(@RequestParam(name="ids",required=true) String ids) {
		this.deviceNumberService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

     /**
      * 同步设备编号
      * @return
      */
	@RequestMapping(value = "/synchronizeDeviceNumber", method = RequestMethod.GET)
    public Result<?> synchronizeDeviceNumber() {
	    QueryWrapper<DeviceNumber> deviceNumberQueryWrapper = new QueryWrapper<>();
	    deviceNumberQueryWrapper.orderByDesc("modify_time");
	    List<DeviceNumber> deviceNumberList = deviceNumberService.list(deviceNumberQueryWrapper);
	    try {
            JSONObject jsonObject;
            if (deviceNumberList.isEmpty()) {
                jsonObject = HttpHelper.httpGet(ERPInterfaceConstant.LIST_DEVICENUMBER_URL.replace("TOKEN",redisUtil.get(ERPInterfaceConstant.TOKEN_KEY).toString()).replace("MAX",""));
            } else {
                jsonObject = HttpHelper.httpGet(ERPInterfaceConstant.LIST_DEVICENUMBER_URL.replace("TOKEN",redisUtil.get(ERPInterfaceConstant.TOKEN_KEY).toString()).replace("MAX",deviceNumberList.get(0).getModifyTime().getTime()+""));
            }
            JSONArray dataArray = jsonObject.getJSONArray("data");
            DateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            List<DeviceNumber> addDeviceNumberList = new ArrayList<>();
            for (int i = 0; i < dataArray.size(); i++) {
                JSONObject data = dataArray.getJSONObject(i);
                QueryWrapper<Client> clientQueryWrapper = new QueryWrapper<>();
                clientQueryWrapper.eq("number",data.getString("custNo"));
                Client client = clientService.getOne(clientQueryWrapper);
                if (oConvertUtils.isNotEmpty(client)) {
                    DeviceNumber deviceNumber = new DeviceNumber();
                    deviceNumber.setNumber(data.getString("number"));
                    deviceNumber.setName(data.getString("name"));
                    deviceNumber.setType(data.getString("type"));
                    /*deviceNumber.setDescription(data.getString("describe"));
                    deviceNumber.setSigning(dataFormat.parse(data.getString("signing")));
                    deviceNumber.setQgp(dataFormat.parse(data.getString("QGP")));*/
                    deviceNumber.setModifyTime(data.getTimestamp("FModifyTime"));
                    deviceNumber.setClientId(client.getId());
                    addDeviceNumberList.add(deviceNumber);
                }
            }
            deviceNumberService.saveBatch(addDeviceNumberList);
        } catch (Exception e) {
	        e.printStackTrace();
	        log.error("同步设备编号出错!");
	        return Result.error("同步出错,请联系管理员!");
        }
	    return Result.ok("同步成功!");
    }

    /*--------------------------------子表处理-设备编号-end----------------------------------------------*/

}
