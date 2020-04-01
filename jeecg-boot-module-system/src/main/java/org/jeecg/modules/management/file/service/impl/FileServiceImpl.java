package org.jeecg.modules.management.file.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.apache.commons.io.FilenameUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.management.file.entity.File;
import org.jeecg.modules.management.file.mapper.FileMapper;
import org.jeecg.modules.management.file.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: 文件表
 * @Author: jeecg-boot
 * @Date:   2020-03-25
 * @Version: V1.0
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {

    @Autowired
    private FileMapper fileMapper;

    @Value(value = "${jeecg.path.upload}")
    private String uploadpath;

    @Override
    public List<File> getByReportId(String reportId, String type) {
        return fileMapper.getByReportId(reportId, type);
    }

    @Override
    public Result<File> uploadFiles(MultipartFile[] files, String dic, String sid, String progressReportId) {
        Result<File> result = new Result<>();
        File filesEntity = files(files, dic,sid, progressReportId);
        if (filesEntity == null) {
            result.setSuccess(false);
            result.setMessage("上传失败");
            return result;
        }
        result.setResult(filesEntity);
        result.setSuccess(true);
        result.setMessage("上传成功");
        return result;
    }

    public synchronized File files(MultipartFile[] files, String dis,String sid, String progressReportId) {
        boolean open = true;
        List<File> fileList = fileMapper.selectList(null);
        for (File file : fileList) {
            if (sid.equals(file.getId())) {
                open = false;
                break;
            }
        }
        File file = new File();
        if (open) {
            List<Map> list = uploadFile(files, dis);
            File fileEntity = null;
            String fileName = "";
            String filePath = "";
            String fileId = "";
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> params = list.get(i);
                fileEntity =new File();
                fileEntity.setOriginalFilename(params.get("oldFileName").toString());
                fileEntity.setFilename(params.get("newFileName").toString());
                fileEntity.setUrl(params.get("path").toString());
                fileEntity.setId(sid);
                fileEntity.setProgressReportId(progressReportId);
                fileEntity.setType(dis);
                // todo 查询数据库
                fileMapper.insert(fileEntity);
                String oldFileName = params.get("oldFileName").toString();
                String path = params.get("path").toString();
                String id = fileEntity.getId();
                if(list.size() - 1 == i){
                    fileName = fileName + oldFileName;
                    filePath = filePath + path;
                    fileId = fileId + id;
                }else{
                    fileName = fileName + oldFileName +",";
                    filePath = filePath + path+",";
                    fileId = fileId + id+",";
                }
            }
            file.setId(fileId);
            file.setUrl(filePath);
            file.setFilename(fileName);
        }
        return file;
    }

    public List<Map> uploadFile(MultipartFile[] files, String dis) {
        List<Map> objectList = Lists.newArrayList();
        String ctxPath = uploadpath;
        String bizPath = dis;
        String nowday = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String path = ctxPath + "/" + bizPath + "/" + nowday;
        //判断file数组不能为空并且长度大于0
        if (files != null && files.length > 0) {
            //循环获取file数组中得文件
            for (int i = 0; i < files.length; i++) {
                Map<String, Object> params =  new HashMap<>();
                MultipartFile file = files[i];
                // 判断文件是否为空
                if (!file.isEmpty()) {
                    try {
                        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                        // 图片名称
                        String uuid = df.format(new Date()) ;
                        String oldFileName = file.getOriginalFilename();
                        String mine = file.getContentType();
                        String suffix = FilenameUtils.getExtension(oldFileName);
                        // 文件保存路径
                        String savePath = "";
                        // 保存数据库url路径
                        String fileUrl = "";
                        String newFileName = dis + uuid +"."+suffix;
                        savePath = path + "/" + newFileName;
                        fileUrl = "/" + bizPath + "/" + nowday + "/" + newFileName;
                        java.io.File filepath = new java.io.File(path);
                        if (!filepath.exists())
                            filepath.mkdirs();
                        // 转存文件
                        file.transferTo(new java.io.File(savePath));
                        // 返回参数
                        params.put("oldFileName", oldFileName);
                        params.put("newFileName", newFileName);
                        params.put("size", file.getSize() / 1024);
                        params.put("type", suffix);
                        params.put("path", fileUrl);
                        params.put("mine", mine);
                        params.put("smallUrl", "smallUrl");
                        objectList.add(params);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return objectList;
    }

}
