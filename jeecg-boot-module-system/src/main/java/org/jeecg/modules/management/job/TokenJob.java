package org.jeecg.modules.management.job;


import org.jeecg.modules.dingtalk.accesstoken.util.DingTalkAccessTokenUtils;
import org.jeecg.modules.management.erp.erptoken.util.ERPTokenUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * Token任务
 * @author KicoChan
 */
public class TokenJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        DingTalkAccessTokenUtils.setToken();
        ERPTokenUtils.setToken();
    }

}
