package org.jeecg.modules.management.mobile.user;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户控制器
 * @author KicoChan
 */
@RestController
@RequestMapping(value = "/mobile/user")
@Slf4j
public class MobileUserController {

    @Autowired
    private ISysUserService sysUserService;

    /**
     * 根据角色编码获取该角色用户
     * @param roleCode
     * @return
     */
    @GetMapping(value = "/listUserByRoleCode")
    public Result<?> listUserByRoleCode(@RequestParam("roleCode") String roleCode) {
        List<SysUser> userList = sysUserService.listByRoleCode(roleCode);
        return Result.ok(userList);
    }

}
