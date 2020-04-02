package org.jeecg.modules.management.mobile.workOrder;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.management.workorder.entity.WorkOrder;
import org.jeecg.modules.management.workorder.service.IWorkOrderDetailService;
import org.jeecg.modules.management.workorder.service.IWorkOrderService;
import org.jeecg.modules.management.workorder.vo.MobileWorkOrderDTO;
import org.jeecg.modules.management.workorder.vo.MobileWorkOrderDetailDTO;
import org.jeecg.modules.management.workorder.vo.WorkOrderPage;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/mobile/workOrder")
public class MobileWorkOrderController {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private IWorkOrderService workOrderService;

    @Autowired
    private IWorkOrderDetailService workOrderDetailService;

    @RequestMapping(value = "/workOrderList", method = RequestMethod.GET)
    public Result<?> workOrderList(@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                   HttpServletRequest req) {
        Result<Page<MobileWorkOrderDTO>> result = new Result<Page<MobileWorkOrderDTO>>();
        Page<MobileWorkOrderDTO> pageList = new Page<>(pageNo, pageSize);
        String enterpriseId = req.getParameter("userId");
        SysUser user = sysUserService.getByEnterpriseId(enterpriseId);
        if (user == null) {
            result.setSuccess(false);
            result.setMessage("用户不存在！");
            return result;
        }
        pageList = workOrderService.queryMobileList(pageList, user.getUsername(), req.getParameter("status"));
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    @RequestMapping(value = "/getAllDetail", method = RequestMethod.GET)
    public Result<?> getAllDetail(HttpServletRequest req) {
        List<MobileWorkOrderDetailDTO> detailList = workOrderDetailService.getByWorkOrderId(req.getParameter("workOrderId"));
        return Result.ok(detailList);
    }

    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody WorkOrderPage workOrderPage) {
        WorkOrder workOrder = new WorkOrder();
        BeanUtils.copyProperties(workOrderPage, workOrder);
        workOrderService.saveMain(workOrder, workOrderPage.getWorkOrderDetailList());
        return Result.ok("添加成功！");
    }

}
