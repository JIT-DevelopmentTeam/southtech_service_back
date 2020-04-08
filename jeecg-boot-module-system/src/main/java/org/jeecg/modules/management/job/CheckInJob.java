package org.jeecg.modules.management.job;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.dingtalk.checkin.CheckIn;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * 钉钉签到任务
 */
@Slf4j
public class CheckInJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        log.info(String.format(" 钉钉签到记录任务 CheckInJob !  时间:" + System.currentTimeMillis()));
        CheckIn.getLocation();
    }

}
