package org.jeecg.modules.management.workorder.controller;

import org.jeecg.common.system.query.QueryGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.management.workorder.entity.WorkOrderPage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import java.util.Arrays;
import java.util.List;

import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.management.workorder.entity.WorkOrderDetail;
import org.jeecg.modules.management.workorder.entity.WorkOrder;
import org.jeecg.modules.management.workorder.service.IWorkOrderService;
import org.jeecg.modules.management.workorder.service.IWorkOrderDetailService;


 /**
 * @Description: 工单信息
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
@RestController
@RequestMapping("/workorder/workOrder")
@Slf4j
public class WorkOrderController extends JeecgController<WorkOrder, IWorkOrderService> {

	@Autowired
	private IWorkOrderService workOrderService;

	@Autowired
	private IWorkOrderDetailService workOrderDetailService;


	/*---------------------------------主表处理-begin-------------------------------------*/

	/**
	 * 分页列表查询
	 * @param workOrder
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WorkOrder workOrder,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WorkOrder> queryWrapper = QueryGenerator.initQueryWrapper(workOrder, req.getParameterMap());
		Page<WorkOrder> page = new Page<WorkOrder>(pageNo, pageSize);
		IPage<WorkOrder> pageList = workOrderService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
     *   添加
     * @param workOrderPage
     * @return
     */
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody WorkOrderPage workOrderPage) {
        WorkOrder workOrder = new WorkOrder();
        BeanUtils.copyProperties(workOrderPage, workOrder);
        workOrderService.saveMain(workOrder, workOrderPage.getWorkOrderDetailList());
        return Result.ok("添加成功！");
    }

    /**
     *  编辑
     * @param workOrderPage
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody WorkOrderPage workOrderPage) {
        WorkOrder workOrder = new WorkOrder();
        BeanUtils.copyProperties(workOrderPage, workOrder);
        workOrderService.updateMain(workOrder, workOrderPage.getWorkOrderDetailList());
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name="id",required=true) String id) {
        workOrderService.delMain(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        this.workOrderService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WorkOrder workOrder) {
        return super.exportXls(request, workOrder, WorkOrder.class, "工单信息");
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, WorkOrder.class);
    }
	/*---------------------------------主表处理-end-------------------------------------*/
	

    /*--------------------------------子表处理-工单明细-begin----------------------------------------------*/
	/**
	 * 查询子表信息 会传入主表ID
	 * @return
	 */
	@GetMapping(value = "/listWorkOrderDetailByMainId")
    public Result<?> listWorkOrderDetailByMainId(WorkOrderDetail workOrderDetail,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<WorkOrderDetail> queryWrapper = QueryGenerator.initQueryWrapper(workOrderDetail, req.getParameterMap());
        Page<WorkOrderDetail> page = new Page<WorkOrderDetail>(pageNo, pageSize);
        IPage<WorkOrderDetail> pageList = workOrderDetailService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

     /**
      * 通过id查询
      *
      * @param id
      * @return
      */
     @GetMapping(value = "/queryWorkOrderDetailListByMainId")
     public Result<?> queryOrderCustomerListByMainId(@RequestParam(name = "id", required = true) String id) {
         List<WorkOrderDetail> workOrderDetailList = workOrderDetailService.selectByMainId(id);
         return Result.ok(workOrderDetailList);
     }

	/**
	 * 添加
	 * @param workOrderDetail
	 * @return
	 */
	@PostMapping(value = "/addWorkOrderDetail")
	public Result<?> addWorkOrderDetail(@RequestBody WorkOrderDetail workOrderDetail) {
		workOrderDetailService.save(workOrderDetail);
		return Result.ok("添加成功！");
	}

    /**
	 * 编辑
	 * @param workOrderDetail
	 * @return
	 */
	@PutMapping(value = "/editWorkOrderDetail")
	public Result<?> editWorkOrderDetail(@RequestBody WorkOrderDetail workOrderDetail) {
		workOrderDetailService.updateById(workOrderDetail);
		return Result.ok("编辑成功!");
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/deleteWorkOrderDetail")
	public Result<?> deleteWorkOrderDetail(@RequestParam(name="id",required=true) String id) {
		workOrderDetailService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@DeleteMapping(value = "/deleteBatchWorkOrderDetail")
	public Result<?> deleteBatchWorkOrderDetail(@RequestParam(name="ids",required=true) String ids) {
		this.workOrderDetailService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

    /*--------------------------------子表处理-工单明细-end----------------------------------------------*/




}
