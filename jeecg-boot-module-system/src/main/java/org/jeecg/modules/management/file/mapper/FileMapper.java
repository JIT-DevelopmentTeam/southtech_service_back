package org.jeecg.modules.management.file.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.management.file.entity.File;

import java.util.List;

/**
 * @Description: 文件表
 * @Author: jeecg-boot
 * @Date:   2020-03-25
 * @Version: V1.0
 */
public interface FileMapper extends BaseMapper<File> {

    List<File> getByReportId(@Param("reportId") String reportId, @Param("type") String type);

}
