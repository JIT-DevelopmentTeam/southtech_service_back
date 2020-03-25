package org.jeecg.modules.management.mobile.upload;

import org.jeecg.common.api.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/mobile/upload")
public class MobileUploadController {

    @RequestMapping(value = "/uploadPicture", method = RequestMethod.POST)
    public Result<?> uploadPicture(HttpServletRequest req) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
        MultipartFile mf = multipartRequest.getFile("photo");// 获取上传文件对象
        return Result.ok();
    }

    @RequestMapping(value = "/deleteULPicture", method = RequestMethod.GET)
    public Result<?> deleteULPicture(HttpServletRequest req) {
        return Result.ok();
    }
}
