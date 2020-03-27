package org.jeecg.modules.management.mobile.upload;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.management.file.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/mobile/upload")
public class MobileUploadController {

    @Autowired
    private IFileService fileService;

    @RequestMapping(value = "/uploadPicture", method = RequestMethod.POST)
    public Result<?> uploadPicture(@RequestParam(value = "photo", required = false) MultipartFile[] photo, HttpServletRequest req) {
        String id = req.getParameter("id");
        String progressReportId = req.getParameter("progressReportId");
        Result<?> result = fileService.uploadFiles(photo, "photo", id, progressReportId);
        return result;
    }

    @RequestMapping(value = "/deleteULPicture", method = RequestMethod.GET)
    public Result<?> deleteULPicture(HttpServletRequest req) {
        return Result.ok();
    }
}
