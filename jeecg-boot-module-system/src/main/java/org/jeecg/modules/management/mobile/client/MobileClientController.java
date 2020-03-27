package org.jeecg.modules.management.mobile.client;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.management.client.entity.Client;
import org.jeecg.modules.management.client.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author KicoChan
 */
@RestController
@RequestMapping(value = "/mobile/client")
public class MobileClientController {

    @Autowired
    private IClientService clientService;

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

}
