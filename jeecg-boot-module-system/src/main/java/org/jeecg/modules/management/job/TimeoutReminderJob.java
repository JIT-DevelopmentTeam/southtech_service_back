package org.jeecg.modules.management.job;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.management.workorder.entity.WorkOrder;
import org.jeecg.modules.management.workorder.service.IWorkOrderService;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工单逾期提醒
 */
@Slf4j
public class TimeoutReminderJob implements Job {

    private static IWorkOrderService workOrderService = SpringContextUtils.getApplicationContext().getBean(IWorkOrderService.class);

    private static ISysUserService sysUserService = SpringContextUtils.getApplicationContext().getBean(ISysUserService.class);

    private static ISysBaseAPI sysBaseAPI = SpringContextUtils.getApplicationContext().getBean(ISysBaseAPI.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        log.info(String.format(" 工单延期提醒任务 TimeoutReminderJob !  时间:" + DateUtils.getTimestamp()));
        List<WorkOrder> workOrders = workOrderService.queryTimeoutWorkOrder();
        StringBuilder sb = new StringBuilder();
        for (WorkOrder workOrder : workOrders) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(workOrder.getNumber());
        }
        if (sb.length() > 0) {
            List<SysUser> userList = sysUserService.listByRoleCode("customer_service");
            Map<String, String> map = new HashMap<>();
            map.put("workOrders", sb.toString());
            for (SysUser user : userList) {
                sysBaseAPI.sendSysAnnouncement("admin", user.getUsername(), "工单延期提醒", map, "timeout_remind");
            }
        }
    }
}
