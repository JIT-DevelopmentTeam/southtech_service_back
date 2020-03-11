package org.jeecg.modules.management.listener;

import org.jeecg.modules.dingtalk.accesstoken.util.DingTalkAccessTokenUtils;
import org.jeecg.modules.management.erp.erptoken.util.ERPTokenUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * ERPToken监听器
 * @author KicoChan
 */
@Component
public class TokenListener  implements ApplicationListener<ContextRefreshedEvent>{

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // 服务器启动后更新Token
        DingTalkAccessTokenUtils.setToken();
        ERPTokenUtils.setToken();
    }

}