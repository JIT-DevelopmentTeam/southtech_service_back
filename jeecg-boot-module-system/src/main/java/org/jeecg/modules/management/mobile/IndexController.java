package org.jeecg.modules.management.mobile;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWTCreator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGetJsapiTicketRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiGetJsapiTicketResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.HttpHelper;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.dingtalk.constant.DingTalkConstant;
import org.jeecg.modules.dingtalk.exception.OApiException;
import org.jeecg.modules.dingtalk.exception.OApiResultException;
import org.jeecg.modules.management.client.entity.WxUser;
import org.jeecg.modules.management.client.service.IWxUserService;
import org.jeecg.modules.management.client.service.impl.WxUserServiceImpl;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.wechat.constant.WechatConstant;
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.mp.aes.WXBizMsgCrypt;
import org.jeewx.api.wxuser.user.JwUserAPI;
import org.jeewx.api.wxuser.user.model.Wxuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/mobile/index")
public class IndexController {

    @Autowired
    private IWxUserService wxUserService;

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

    /**
     * 获取微信用户信息
     * @param code
     * @return
     */
    @GetMapping(value = "/getWxUserInfo")
    public Result<?> getWxUserInfo(@RequestParam("code") String code) {
        JSONObject result = HttpHelper.httpGet(WechatConstant.GET_USER_TOKEN_URL.replace("APPID",WechatConstant.APP_ID).replace("SECRET",WechatConstant.SECRET).replace("CODE",code));
        if (oConvertUtils.isEmpty(result)) {
            log.error("获取微信用户信息失败,请求出错!");
            return Result.error("获取微信用户信息失败");
        }
        String accessToken = result.getString("access_token");
        Long accessTokenExpireId = result.getLong("expires_in");
        String refreshToken = result.getString("refresh_token");
        String openId = result.getString("openid");
        QueryWrapper<WxUser> wxUserQueryWrapper = new QueryWrapper<>();
        wxUserQueryWrapper.eq("open_id",openId);
        WxUser wxUser = wxUserService.getOne(wxUserQueryWrapper);
        if (oConvertUtils.isEmpty(wxUser)) {
            log.error("本地暂无同步该用户信息!");
            return Result.error("本地暂无同步该用户信息!");
        }
        wxUser.setAccessToken(accessToken);
        wxUser.setAccessTokenExpireId(new Timestamp(System.currentTimeMillis() + accessTokenExpireId * 1000));
        wxUser.setRefreshToken(refreshToken);
        wxUser.setAccessTokenExpireId(new Timestamp(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 30));
        wxUserService.updateById(wxUser);
        Wxuser wxuser = null;
        try {
            wxuser = JwUserAPI.getWxuser(accessToken,openId);
        } catch (WexinReqException e) {
            e.printStackTrace();
        }
        return Result.ok(wxuser);
    }

    /**
     * 根据openid获取微信用户信息
     * @param openId
     * @return
     */
    @GetMapping(value = "/getWxUserInfoByOpenId")
    public Result<?> getWxUserInfoByAccessToken(@RequestParam(value = "openId") String openId) {
        QueryWrapper<WxUser> wxUserQueryWrapper = new QueryWrapper<>();
        wxUserQueryWrapper.eq("open_id",openId);
        WxUser wxUser = wxUserService.getOne(wxUserQueryWrapper);
        if (oConvertUtils.isEmpty(wxUser)) {
            log.error("本地暂无该用户信息,确认是否已关注同步!");
            return Result.error("暂无用户信息!");
        }
        return Result.ok(wxUser);
    }

}
