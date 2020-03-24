package org.jeecg.modules.management.mobile.stage;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.management.stage.service.IStageService;
import org.jeecg.modules.management.stage.vo.MobileStageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/mobile/stage")
public class MobileStageController {

    @Autowired
    private IStageService stageService;

    @RequestMapping(value = "/queryStageByWorkOrderId")
    public Result<?> queryStageByWorkOrderId(HttpServletRequest req) {
        List<MobileStageDTO> list = stageService.queryStageByWorkOrderId(req.getParameter("workOrderId"));
        return Result.ok(list);
    }

}
