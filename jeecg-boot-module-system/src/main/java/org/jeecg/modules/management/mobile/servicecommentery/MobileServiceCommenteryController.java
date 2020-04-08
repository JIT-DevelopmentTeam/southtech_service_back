package org.jeecg.modules.management.mobile.servicecommentery;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.management.workorder.entity.ServiceCommentery;
import org.jeecg.modules.management.workorder.service.IServiceCommenteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/mobile/serviceCommentery")
public class MobileServiceCommenteryController {

    @Autowired
    private IServiceCommenteryService serviceCommenteryService;

    @PostMapping(value = "/batchSave")
    public Result<?> batchSave(@RequestBody String serviceCommenteryStr) {
        JSONArray jsonArray = JSONArray.parseArray(serviceCommenteryStr);
        List<ServiceCommentery> serviceCommenteryList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            ServiceCommentery serviceCommentery = new ServiceCommentery();
            serviceCommentery.setWorkOrderId(jsonObject.getString("workOrderId"));
            serviceCommentery.setScore(Double.parseDouble(jsonObject.get("score").toString()));
            serviceCommentery.setEvaluation(jsonObject.getString("evaluation"));
            serviceCommenteryList.add(serviceCommentery);
        }
        if (!serviceCommenteryList.isEmpty()) {
            serviceCommenteryService.saveBatch(serviceCommenteryList);
        }
        return Result.ok("评价成功!");
    }

}
