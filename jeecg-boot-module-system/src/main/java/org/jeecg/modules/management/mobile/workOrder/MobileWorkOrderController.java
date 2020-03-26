package org.jeecg.modules.management.mobile.workOrder;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.management.workorder.entity.WorkOrder;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/mobile/workOrder")
public class MobileWorkOrderController {

    @Autowired
    private ISysUserService sysUserService;

    @RequestMapping(value = "/workOrderList")
    public Result<?> workOrderList(@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                   HttpServletRequest req) {
        SysUser user = sysUserService.getByEnterpriseId(req.getParameter("userId"));
        QueryWrapper<WorkOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("", user.getUsername());
        Page<WorkOrder> page = new Page<WorkOrder>(pageNo, pageSize);
        return Result.ok();
    }
}
