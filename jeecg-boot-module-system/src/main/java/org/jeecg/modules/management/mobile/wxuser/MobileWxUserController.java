package org.jeecg.modules.management.mobile.wxuser;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.management.client.entity.Client;
import org.jeecg.modules.management.client.entity.WxUser;
import org.jeecg.modules.management.client.service.IClientService;
import org.jeecg.modules.management.client.service.IWxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信用户控制层
 * @author KicoChan
 */
@RestController
@RequestMapping(value = "/mobile/wxUser")
@Slf4j
public class MobileWxUserController {

    @Autowired
    private IWxUserService wxUserService;

    @Autowired
    private IClientService clientService;

    @GetMapping(value = "/getClientByOpenId")
    public Result<?> getClientByOpenId(@RequestParam("openId") String openId) {
        QueryWrapper<WxUser> wxUserQueryWrapper = new QueryWrapper<>();
        wxUserQueryWrapper.eq("open_id",openId);
        WxUser wxUser = wxUserService.getOne(wxUserQueryWrapper);
        if (StringUtils.isBlank(wxUser.getClientId())) {
            return Result.error("当前用户未绑定客户信息!");
        }
        return Result.ok(clientService.getById(wxUser.getClientId()));
    }

}
