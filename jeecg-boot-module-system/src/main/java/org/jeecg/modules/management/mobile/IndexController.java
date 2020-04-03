package org.jeecg.modules.management.mobile;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWTCreator;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGetJsapiTicketRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiGetJsapiTicketResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.taobao.api.ApiException;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.dingtalk.constant.DingTalkConstant;
import org.jeecg.modules.dingtalk.exception.OApiException;
import org.jeecg.modules.dingtalk.exception.OApiResultException;
import org.jeewx.api.wxuser.user.JwUserAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/mobile/index")
public class IndexController {

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public Result<?> getUserInfo(HttpServletRequest req) {
        Result<Map<String, Object>> result = new Result<>();
        try {
            DingTalkClient client = new DefaultDingTalkClient(DingTalkConstant.GET_USER_INFO);
            OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
            request.setCode(req.getHeader("code"));
            request.setHttpMethod("GET");
            OapiUserGetuserinfoResponse response = client.execute(request, redisUtil.get(DingTalkConstant.ACCESS_TOKEN_KEY).toString());
            Map<String, Object> map = new HashMap<>();
            map.put("userId", response.getUserid());
            result.setSuccess(true);
            result.setResult(map);
            return result;
        } catch (ApiException e) {
            e.printStackTrace();
            return Result.error("获取用户userId失败！");
        }
    }

    @RequestMapping(value = "/getJsapiTicket", method = RequestMethod.GET)
    public Result<?> getJsapiTicket(HttpServletRequest request) {
        try {
            DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/get_jsapi_ticket");
            OapiGetJsapiTicketRequest req = new OapiGetJsapiTicketRequest();
            req.setTopHttpMethod("GET");
            OapiGetJsapiTicketResponse execute = client.execute(req, redisUtil.get(DingTalkConstant.ACCESS_TOKEN_KEY).toString());
            String path = request.getParameter("url");
            String ticket = execute.getTicket();
            long timestamp = System.currentTimeMillis() / 1000; // 必填，生成签名的时间戳
            String nonceStr = UUID.randomUUID().toString(); // 必填，生成签名的随机串
            String signature = sign(ticket, nonceStr, timestamp, path);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("timeStamp", timestamp);
            jsonObject.put("nonceStr", nonceStr);
            jsonObject.put("signature", signature);
            return Result.ok(jsonObject);
        } catch (ApiException | OApiException e) {
            e.printStackTrace();
            return Result.error("鉴权失败");
        }
    }

    public static String sign(String ticket, String nonceStr, long timeStamp, String url) throws OApiException {
        String plain = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "&timestamp=" + String.valueOf(timeStamp)
                + "&url=" + url;
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            sha1.reset();
            sha1.update(plain.getBytes("UTF-8"));
            return byteToHex(sha1.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new OApiResultException(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            throw new OApiResultException(e.getMessage());
        }
    }

    // 字节数组转化成十六进制字符串
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

}
