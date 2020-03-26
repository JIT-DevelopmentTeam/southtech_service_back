package org.jeecg.modules.management.mobile.dic;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.util.SqlInjectionUtil;
import org.jeecg.modules.system.service.ISysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/mobile/dic")
@Slf4j
public class MobileDicController {

    @Autowired
    private ISysDictService sysDictService;

    @RequestMapping(value = "/getDictItemByCode", method = RequestMethod.GET)
    public Result<?> getDictItemByCode(@RequestParam("dictCode") String dictCode) {
        log.info(" dictCode : "+ dictCode);
        Result<List<DictModel>> result = new Result<List<DictModel>>();
        List<DictModel> ls = null;
        try {
            if(dictCode.indexOf(",")!=-1) {
                //关联表字典（举例：sys_user,realname,id）
                String[] params = dictCode.split(",");

                if(params.length<3) {
                    result.error500("字典Code格式不正确！");
                    return result;
                }
                //SQL注入校验（只限制非法串改数据库）
                final String[] sqlInjCheck = {params[0],params[1],params[2]};
                SqlInjectionUtil.filterContent(sqlInjCheck);

                if(params.length==4) {
                    //SQL注入校验（查询条件SQL 特殊check，此方法仅供此处使用）
                    SqlInjectionUtil.specialFilterContent(params[3]);
                    ls = sysDictService.queryTableDictItemsByCodeAndFilter(params[0],params[1],params[2],params[3]);
                }else if (params.length==3) {
                    ls = sysDictService.queryTableDictItemsByCode(params[0],params[1],params[2]);
                }else{
                    result.error500("字典Code格式不正确！");
                    return result;
                }
            }else {
                //字典表
                ls = sysDictService.queryDictItemsByCode(dictCode);
            }

            result.setSuccess(true);
            result.setResult(ls);
            log.info(result.toString());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            result.error500("操作失败");
            return result;
        }
        return result;
    }

}
