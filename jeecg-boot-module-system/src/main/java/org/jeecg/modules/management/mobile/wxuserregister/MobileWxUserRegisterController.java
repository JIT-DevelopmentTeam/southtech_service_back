package org.jeecg.modules.management.mobile.wxuserregister;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.oConvertUtils;
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
        QueryWrapper<WxUserRegister> wxUserRegisterQueryWrapper = new QueryWrapper<>();
        wxUserRegisterQueryWrapper.eq("open_id",wxUserRegister.getOpenId());
        WxUserRegister check = wxUserRegisterService.getOne(wxUserRegisterQueryWrapper);
        if (oConvertUtils.isNotEmpty(check)) {
            return Result.error("注册失败,您已提交注册,请勿重复提交!");
        }
        wxUserRegisterService.save(wxUserRegister);
        return Result.ok("注册成功,请耐心等待工作人员审核!");
    }

}
