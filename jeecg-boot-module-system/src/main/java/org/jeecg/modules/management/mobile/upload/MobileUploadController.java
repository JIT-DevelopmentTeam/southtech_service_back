package org.jeecg.modules.management.mobile.upload;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.management.file.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(value = "/mobile/upload")
@Slf4j
public class MobileUploadController {

    @Value(value = "${jeecg.path.upload}")
    private String uploadpath;

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

    @PostMapping(value = "/uploadDetailPicture")
    public Result<?> uploadDetailPicture(HttpServletRequest request){
        Result<?> result = new Result<>();
        try {
            String ctxPath = uploadpath;
            String fileName = null;
            String bizPath = "files";
            String nowday = new SimpleDateFormat("yyyyMMdd").format(new Date());
            File file = new File(ctxPath + File.separator + bizPath + File.separator + nowday);
            if (!file.exists()) {
                file.mkdirs();// 创建文件根目录
            }
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile mf = multipartRequest.getFile("file");// 获取上传文件对象
            String orgName = "workOrderDetail";// 文件名
            fileName = orgName.substring(0, orgName.lastIndexOf(".")) + "_" + System.currentTimeMillis() + orgName.substring(orgName.indexOf("."));
            String savePath = file.getPath() + File.separator + fileName;
            File savefile = new File(savePath);
            FileCopyUtils.copy(mf.getBytes(), savefile);
            String dbpath = bizPath + File.separator + nowday + File.separator + fileName;
            if (dbpath.contains("\\")) {
                dbpath = dbpath.replace("\\", "/");
            }
            result.setMessage(dbpath);
            result.setSuccess(true);
        } catch (IOException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return result;
    }

}
