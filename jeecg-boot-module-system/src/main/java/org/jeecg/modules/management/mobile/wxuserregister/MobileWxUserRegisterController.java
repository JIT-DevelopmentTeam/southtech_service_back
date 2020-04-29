package org.jeecg.modules.management.mobile.wxuserregister;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.management.wxuserregister.entity.WxUserRegister;
import org.jeecg.modules.management.wxuserregister.service.IWxUserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/mobile/wxUserRegister")
public class MobileWxUserRegisterController {

    @Autowired
    private IWxUserRegisterService wxUserRegisterService;

    @PostMapping(value = "/save")
    public Result<?> save(@RequestBody WxUserRegister wxUserRegister) {
        wxUserRegisterService.save(wxUserRegister);
        return Result.ok("注册成功,请耐心等待工作人员审核!");
    }

}
