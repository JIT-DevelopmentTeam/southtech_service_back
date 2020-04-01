package org.jeecg.modules.management.mobile.upload;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
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
import java.util.HashMap;
import java.util.Map;

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
        Result<?> result = fileService.uploadFiles(photo, "Photo", id, progressReportId);
        return result;
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public Result<?> uploadFile(@RequestParam(value = "file", required = false) MultipartFile[] files, HttpServletRequest req) {
        String id = req.getParameter("id");
        String progressReportId = req.getParameter("progressReportId");
        Result<?> result = fileService.uploadFiles(files, "File", id, progressReportId);
        return result;
    }

    @RequestMapping(value = "/deleteULPicture", method = RequestMethod.GET)
    public Result<?> deleteULPicture(HttpServletRequest req) {
        String ids = req.getParameter("ids");
        String[] idArr = ids.split(",");
        for (String id : idArr) {
            org.jeecg.modules.management.file.entity.File file = fileService.getById(id);
            if (file != null) {
                String path = uploadpath + "/" + file.getUrl();
                File delFile = new File(path);
                delFile.delete();
                fileService.removeById(id);
            }
        }
        return Result.ok();
    }

    @PostMapping(value = "/uploadDetailPicture")
    public Result<?> uploadDetailPicture(@RequestParam(value = "photos", required = false) MultipartFile[] files){
        Result<String> result = new Result<>();
        try {
            String ctxPath = uploadpath;
            String bizPath = "files";
            String nowday = new SimpleDateFormat("yyyyMMdd").format(new Date());
            File file = new File(ctxPath + File.separator + bizPath + File.separator + nowday);
            if (!file.exists()) {
                file.mkdirs();// 创建文件根目录
            }
            StringBuffer dbpaths = new StringBuffer();
            for (MultipartFile multipartFile : files) {
                String orgName = "workOrderDetail";// 文件名
                String fileName = orgName + "_" + System.currentTimeMillis()+"."+FilenameUtils.getExtension(multipartFile.getOriginalFilename());
                String savePath = file.getPath() + File.separator + fileName;
                File savefile = new File(savePath);
                FileCopyUtils.copy(multipartFile.getBytes(), savefile);
                String dbpath = bizPath + File.separator + nowday + File.separator + fileName;
                if (dbpath.contains("\\")) {
                    dbpath = dbpath.replace("\\", "/");
                }
                dbpaths.append(dbpath+",");
            }
            if (dbpaths.length() > 0) {
                result.setResult(dbpaths.deleteCharAt(dbpaths.length()-1).toString());
                result.setSuccess(true);
            }
        } catch (IOException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return result;
    }

}
