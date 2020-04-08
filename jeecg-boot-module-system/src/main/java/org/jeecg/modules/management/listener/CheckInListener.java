package org.jeecg.modules.management.listener;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.dingtalk.checkin.CheckIn;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CheckInListener  implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info(String.format(" 钉钉签到记录任务 CheckInJob !  时间:" + System.currentTimeMillis()));
        CheckIn.getLocation();
    }
}
