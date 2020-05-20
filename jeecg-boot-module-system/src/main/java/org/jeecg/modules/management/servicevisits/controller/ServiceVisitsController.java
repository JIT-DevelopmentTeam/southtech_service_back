package org.jeecg.modules.management.servicevisits.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.management.servicevisits.entity.ServiceVisits;
import org.jeecg.modules.management.servicevisits.service.IServiceVisitsService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.management.workorder.entity.WorkOrder;
import org.jeecg.modules.management.workorder.service.IWorkOrderService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;

 /**
 * @Description: 回访记录
 * @Author: jeecg-boot
 * @Date:   2020-03-17
 * @Version: V1.0
 */
@RestController
@RequestMapping("/servicevisits/serviceVisits")
@Slf4j
public class ServiceVisitsController extends JeecgController<ServiceVisits, IServiceVisitsService> {
	@Autowired
	private IServiceVisitsService serviceVisitsService;

     @Autowired
     private IWorkOrderService workOrderService;
	
	/**
	 * 分页列表查询
	 *
	 * @param serviceVisits
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ServiceVisits serviceVisits,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ServiceVisits> queryWrapper = QueryGenerator.initQueryWrapper(serviceVisits, req.getParameterMap());
		Page<ServiceVisits> page = new Page<ServiceVisits>(pageNo, pageSize);
		IPage<ServiceVisits> pageList = serviceVisitsService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param serviceVisits
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ServiceVisits serviceVisits) {
	    QueryWrapper<ServiceVisits> serviceVisitsQueryWrapper = new QueryWrapper<>();
	    serviceVisitsQueryWrapper.eq("work_order_id",serviceVisits.getWorkOrderId());
	    List<ServiceVisits> serviceVisitsList = serviceVisitsService.list(serviceVisitsQueryWrapper);
	    if (serviceVisitsList.isEmpty()) {
	        WorkOrder workOrder = workOrderService.getById(serviceVisits.getWorkOrderId());
            switch (serviceVisits.getIsCompleted()) {
                case "1":
                    workOrder.setStatus("3");
                    break;
                case "2":
                    workOrder.setStatus("6");
                    break;
                default:
                    workOrder.setStatus("5");
            }
            workOrderService.updateById(workOrder);
        }
		serviceVisitsService.save(serviceVisits);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param serviceVisits
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ServiceVisits serviceVisits) {
		serviceVisitsService.updateById(serviceVisits);
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
		serviceVisitsService.removeById(id);
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
		this.serviceVisitsService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ServiceVisits serviceVisits = serviceVisitsService.getById(id);
		if(serviceVisits==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(serviceVisits);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param serviceVisits
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ServiceVisits serviceVisits) {
        return super.exportXls(request, serviceVisits, ServiceVisits.class, "回访记录");
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
        return super.importExcel(request, response, ServiceVisits.class);
    }

}
