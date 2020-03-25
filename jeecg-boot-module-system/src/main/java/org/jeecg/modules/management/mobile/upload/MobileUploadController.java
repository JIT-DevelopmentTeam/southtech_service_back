package org.jeecg.modules.management.mobile.upload;

import org.jeecg.common.api.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mobile/upload")
public class MobileUploadController {

    @RequestMapping(value = "/uploadPicture", method = RequestMethod.GET)
    public Result<?> uploadPicture() {
        return Result.ok();
    }
}
