package org.jeecg.modules.dingtalk.checkin;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiCheckinRecordRequest;
import com.dingtalk.api.response.OapiCheckinRecordResponse;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.dingtalk.constant.DingTalkConstant;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 钉钉-签到
 */
@Slf4j
public class CheckIn {

    private static RedisUtil redisUtil = SpringContextUtils.getApplicationContext().getBean(RedisUtil.class);

    private static ISysUserService sysUserService = SpringContextUtils.getApplicationContext().getBean(ISysUserService.class);

    public static void getLocation() {
        try {
            DingTalkClient client = new DefaultDingTalkClient(DingTalkConstant.GET_CHECKIN);
            OapiCheckinRecordRequest request = new OapiCheckinRecordRequest();
            request.setDepartmentId("1");
            request.setStartTime(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(12));
            request.setEndTime(System.currentTimeMillis());
            request.setHttpMethod("GET");
            OapiCheckinRecordResponse response = client.execute(request, (redisUtil.get(DingTalkConstant.ACCESS_TOKEN_KEY)).toString());
            List<OapiCheckinRecordResponse.Data> data = response.getData();
            List<SysUser> list = sysUserService.list();
            for (int i = 0; i < data.size(); i++) {
                System.out.println(data.get(i).getUserId() + ", " + data.get(i).getLongitude() + ", " + data.get(i).getLatitude());
                for (int j = 0; j < list.size(); j++) {
                    if (data.get(i).getUserId().equals(list.get(j).getEnterpriseId())) {
                        sysUserService.updateLocation(Double.parseDouble(data.get(i).getLongitude()), Double.parseDouble(data.get(i).getLatitude()), data.get(i).getUserId());
                        break;
                    }
                }
            }
        } catch (ApiException e) {
            e.printStackTrace();
            log.error("获取钉钉签到记录出错!");
        }
    }

}
