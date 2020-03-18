package org.jeecg.modules.management.amap.controller;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.HttpHelper;
import org.jeecg.modules.management.amap.constant.AMapConstant;
import org.jeecg.modules.management.amap.entity.AMap;
import org.springframework.web.bind.annotation.*;

/**
 * 高德地图控制层
 * @author KicoChan
 */
@Slf4j
@RestController
@RequestMapping(value = "/amap/amap")
public class AmapController {

    @GetMapping(value = "/getLocationByAddress")
    public Result<?> getLocationByAddress(@RequestParam String address) {
        Result<AMap> result = new Result<>();
        JSONObject jsonObject = HttpHelper.httpGet(AMapConstant.GET_LOCATION_BY_ADDRESS_URL.replace("ADDRESS",address));
        if (ObjectUtil.isNotEmpty(jsonObject.get("status")) && StringUtils.equals("1",jsonObject.getString("status"))) {
            JSONArray geocodes = jsonObject.getJSONArray("geocodes");
            if (!geocodes.isEmpty()) {
                AMap aMap = new AMap();
                aMap.setLongitude(Double.parseDouble(geocodes.getJSONObject(0).getString("location").split(",")[0]));
                aMap.setLatitude(Double.parseDouble(geocodes.getJSONObject(0).getString("location").split(",")[1]));
                result.setSuccess(true);
                result.setResult(aMap);
                return result;
            }
        }
        return Result.error("获取失败!");
    }

}
