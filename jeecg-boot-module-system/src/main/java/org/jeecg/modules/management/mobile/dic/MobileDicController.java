package org.jeecg.modules.management.mobile.dic;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.modules.system.service.ISysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/mobile/dic")
public class MobileDicController {

    @Autowired
    private ISysDictService sysDictService;

    @RequestMapping(value = "/getDictItemByCode", method = RequestMethod.GET)
    public Result<?> getDictItemByCode(HttpServletRequest req) {
        List<DictModel> list = sysDictService.queryDictItemsByCode(req.getParameter("dicCode"));
        return Result.ok(list);
    }

}
