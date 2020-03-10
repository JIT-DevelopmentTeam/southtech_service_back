package org.jeecg.modules.management.client.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.management.client.entity.Contact;
import org.jeecg.modules.management.client.entity.DeviceNumber;
import org.jeecg.modules.management.client.entity.Client;
import org.jeecg.modules.management.client.vo.ClientPage;
import org.jeecg.modules.management.client.service.IClientService;
import org.jeecg.modules.management.client.service.IContactService;
import org.jeecg.modules.management.client.service.IDeviceNumberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;

 /**
 * @Description: 客户信息
 * @Author: jeecg-boot
 * @Date:   2020-03-10
 * @Version: V1.0
 */
@RestController
@RequestMapping("/client/client")
@Slf4j
public class ClientController {
	@Autowired
	private IClientService clientService;
	@Autowired
	private IContactService contactService;
	@Autowired
	private IDeviceNumberService deviceNumberService;
	
	/**
	 * 分页列表查询
	 *
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
		QueryWrapper<Client> queryWrapper = QueryGenerator.initQueryWrapper(client, req.getParameterMap());
		Page<Client> page = new Page<Client>(pageNo, pageSize);
		IPage<Client> pageList = clientService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param clientPage
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ClientPage clientPage) {
		Client client = new Client();
		BeanUtils.copyProperties(clientPage, client);
		clientService.saveMain(client, clientPage.getContactList(),clientPage.getDeviceNumberList());
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param clientPage
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ClientPage clientPage) {
		Client client = new Client();
		BeanUtils.copyProperties(clientPage, client);
		Client clientEntity = clientService.getById(client.getId());
		if(clientEntity==null) {
			return Result.error("未找到对应数据");
		}
		clientService.updateMain(client, clientPage.getContactList(),clientPage.getDeviceNumberList());
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		clientService.delMain(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.clientService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		Client client = clientService.getById(id);
		if(client==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(client);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryContactByMainId")
	public Result<?> queryContactListByMainId(@RequestParam(name="id",required=true) String id) {
		List<Contact> contactList = contactService.selectByMainId(id);
		return Result.ok(contactList);
	}
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryDeviceNumberByMainId")
	public Result<?> queryDeviceNumberListByMainId(@RequestParam(name="id",required=true) String id) {
		List<DeviceNumber> deviceNumberList = deviceNumberService.selectByMainId(id);
		return Result.ok(deviceNumberList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param client
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Client client) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<Client> queryWrapper = QueryGenerator.initQueryWrapper(client, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<Client> queryList = clientService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<Client> clientList = new ArrayList<Client>();
      if(oConvertUtils.isEmpty(selections)) {
          clientList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          clientList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<ClientPage> pageList = new ArrayList<ClientPage>();
      for (Client main : clientList) {
          ClientPage vo = new ClientPage();
          BeanUtils.copyProperties(main, vo);
          List<Contact> contactList = contactService.selectByMainId(main.getId());
          vo.setContactList(contactList);
          List<DeviceNumber> deviceNumberList = deviceNumberService.selectByMainId(main.getId());
          vo.setDeviceNumberList(deviceNumberList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "客户信息列表");
      mv.addObject(NormalExcelConstants.CLASS, ClientPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("客户信息数据", "导出人:"+sysUser.getRealname(), "客户信息"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
    }

    /**
    * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<ClientPage> list = ExcelImportUtil.importExcel(file.getInputStream(), ClientPage.class, params);
              for (ClientPage page : list) {
                  Client po = new Client();
                  BeanUtils.copyProperties(page, po);
                  clientService.saveMain(po, page.getContactList(),page.getDeviceNumberList());
              }
              return Result.ok("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.ok("文件导入失败！");
    }

}
