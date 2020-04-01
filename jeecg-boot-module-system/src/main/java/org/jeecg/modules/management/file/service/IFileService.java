package org.jeecg.modules.management.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.management.file.entity.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Description: 文件表
 * @Author: jeecg-boot
 * @Date:   2020-03-25
 * @Version: V1.0
 */
public interface IFileService extends IService<File> {

    Result<?> uploadFiles(MultipartFile[] files, String dic, String sid, String progressId);

    List<File> getByReportId(String reportId, String type);

}
