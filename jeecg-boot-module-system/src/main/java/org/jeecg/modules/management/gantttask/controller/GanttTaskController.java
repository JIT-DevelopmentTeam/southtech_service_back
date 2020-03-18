package org.jeecg.modules.management.gantttask.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.management.gantttask.entity.GanttTask;
import org.jeecg.modules.management.workorder.entity.WorkOrder;
import org.jeecg.modules.management.workorder.entity.WorkOrderDetail;
import org.jeecg.modules.management.workorder.service.IWorkOrderDetailService;
import org.jeecg.modules.management.workorder.service.IWorkOrderService;
import org.jeecg.modules.system.entity.SysRole;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysRoleService;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 甘特图任务
 */
@Slf4j
@RestController
@RequestMapping(value = "/gantttask/gantttask")
public class GanttTaskController {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private IWorkOrderService workOrderService;

    @Autowired
    private IWorkOrderDetailService workOrderDetailService;


    /**
     * 获取工程师甘特任务
     * @return
     */
    @GetMapping(value = "/listTaskByEngineer")
    public Result<?> listTaskByEngineer() {
        QueryWrapper<SysRole> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq("role_code","service_engineer");
        SysRole role = sysRoleService.getOne(roleQueryWrapper);
        if (oConvertUtils.isEmpty(role)) {
            return Result.error("获取角色出错!");
        }
        IPage<SysUser> userPage = sysUserService.getUserByRoleId(new Page<SysUser>(1,100),role.getId(),null);
        if (userPage.getRecords().isEmpty()) {
            return Result.error("服务工程师角色未绑定用户信息!");
        }
        List<GanttTask> ganttTaskList = new ArrayList<>();
        for (SysUser user : userPage.getRecords()) {
            QueryWrapper<WorkOrderDetail> workOrderDetailQueryWrapper = new QueryWrapper<>();
            workOrderDetailQueryWrapper.eq("service_engineer_name",user.getUsername());
            List<WorkOrderDetail> workOrderDetailList = workOrderDetailService.list(workOrderDetailQueryWrapper);
            List<WorkOrderDetail> addWorkOrderList = new ArrayList<>();
            for (WorkOrderDetail workOrderDetail : workOrderDetailList) {
                WorkOrder workOrder = workOrderService.getById(workOrderDetail.getWorkOrderId());
                if (StringUtils.equals("3",workOrder.getStatus()) || StringUtils.equals("4",workOrder.getStatus()) || StringUtils.equals("8",workOrder.getStatus()) || StringUtils.equals("9",workOrder.getStatus())) {
                    continue;
                }
                addWorkOrderList.add(workOrderDetail);
            }
            if (!addWorkOrderList.isEmpty()) {
                GanttTask userGanttTask = new GanttTask();
                userGanttTask.setId(user.getUsername());
                userGanttTask.setText(user.getRealname());
                ganttTaskList.add(userGanttTask);
                for (WorkOrderDetail workOrderDetail : addWorkOrderList) {
                    WorkOrder workOrder = workOrderService.getById(workOrderDetail.getWorkOrderId());
                    GanttTask workDetailGanttTask = new GanttTask();
                    workDetailGanttTask.setId(workOrderDetail.getId());
                    workDetailGanttTask.setText(workOrder.getNumber());
                    workDetailGanttTask.setStart_date(DateUtils.formatDate(workOrderDetail.getDispatchTime(),"yyyy-MM-dd HH:mm:ss"));
                    workDetailGanttTask.setDuration(Integer.parseInt(DateUtils.getBetweenDays(workOrderDetail.getDispatchTime(),workOrderDetail.getPlannedCompletionTime())));
                    workDetailGanttTask.setParent(userGanttTask.getId());
                    ganttTaskList.add(workDetailGanttTask);
                }
            }
        }
        if (ganttTaskList.isEmpty()) {
            return Result.error("暂无数据!");
        }
        return Result.ok(ganttTaskList);
    }

}
