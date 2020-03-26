package org.jeecg.modules.management.mobile;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.taobao.api.ApiException;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.dingtalk.constant.DingTalkConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/mobile/index")
public class IndexController {

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public Result<?> getUserInfo(HttpServletRequest req) {
        try {
            DingTalkClient client = new DefaultDingTalkClient(DingTalkConstant.GET_USER_INFO);
            OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
            request.setCode(req.getHeader("code"));
            request.setHttpMethod("GET");
            OapiUserGetuserinfoResponse response = client.execute(request, redisUtil.get(DingTalkConstant.ACCESS_TOKEN_KEY).toString());
            return Result.ok(response.getUserid());
        } catch (ApiException e) {
            e.printStackTrace();
            return Result.error("获取用户userId失败！");
        }
    }

}
