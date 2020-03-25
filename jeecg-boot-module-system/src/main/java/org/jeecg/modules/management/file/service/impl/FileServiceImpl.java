package org.jeecg.modules.management.file.service.impl;

import org.jeecg.modules.management.file.entity.File;
import org.jeecg.modules.management.file.mapper.FileMapper;
import org.jeecg.modules.management.file.service.IFileService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 文件表
 * @Author: jeecg-boot
 * @Date:   2020-03-25
 * @Version: V1.0
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {

}
