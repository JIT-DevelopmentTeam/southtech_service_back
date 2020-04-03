package org.jeecg.modules.management.mobile.client;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.management.client.entity.Client;
import org.jeecg.modules.management.client.entity.DeviceNumber;
import org.jeecg.modules.management.client.entity.WxUser;
import org.jeecg.modules.management.client.service.IClientService;
import org.jeecg.modules.management.client.service.IDeviceNumberService;
import org.jeecg.modules.management.client.service.IWxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.management.QueryEval;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author KicoChan
 */
@Slf4j
@RestController
@RequestMapping(value = "/mobile/client")
public class MobileClientController {

    @Autowired
    private IClientService clientService;
    @Autowired
    private IDeviceNumberService deviceNumberService;
    @Autowired
    private IWxUserService wxUserService;

    /**
     * 分页列表查询
     * @param client
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @GetMapping(value = "/list")
    public Result<?> queryPageList(Client client,
                                   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                   HttpServletRequest req) {
        if (StringUtils.isNotBlank(client.getNumber())) {
            client.setNumber("*"+client.getNumber().trim()+"*");
        }
        if (StringUtils.isNotBlank(client.getName())) {
            client.setName("*"+client.getName().trim()+"*");
        }
        QueryWrapper<Client> queryWrapper = QueryGenerator.initQueryWrapper(client, req.getParameterMap());
        Page<Client> page = new Page<Client>(pageNo, pageSize);
        IPage<Client> pageList = clientService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 根据id获取客户数据
     * @param id
     * @return
     */
    @GetMapping(value = "/getClientById")
    public Result<?> getClientById(@RequestParam("id") String id) {
        return Result.ok(clientService.getById(id));
    }

    /**
     *
     * @param deviceNumber
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @GetMapping(value = "/listDeviceNumberByMainId")
    public Result<?> listDeviceNumberByMainId(DeviceNumber deviceNumber,
                                          @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                          @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                          HttpServletRequest req) {
        QueryWrapper<DeviceNumber> queryWrapper = QueryGenerator.initQueryWrapper(deviceNumber, req.getParameterMap());
        Page<DeviceNumber> page = new Page<DeviceNumber>(pageNo, pageSize);
        IPage<DeviceNumber> pageList = deviceNumberService.page(page, queryWrapper);
        return Result.ok(pageList.getRecords());
    }


    /**
     * 根据微信用户获取关联客户信息
     * @return
     */
    @GetMapping(value = "/getClientByWxUser")
    public Result<?> getClientByWxUser(WxUser wxUser,HttpServletRequest req) {
        QueryWrapper<WxUser> queryWrapper = QueryGenerator.initQueryWrapper(wxUser, req.getParameterMap());
        WxUser user = wxUserService.getOne(queryWrapper);
        if (oConvertUtils.isEmpty(user)) {
            log.error("微信用户信息不存在!");
            return Result.error("微信用户信息不存在!");
        }
        if (StringUtils.isBlank(user.getClientId())) {
            log.error("微信用户未绑定客户!");
            return Result.error("用户信息未绑定客户!");
        }
        return Result.ok(clientService.getById(wxUser.getClientId()));
    }


}
