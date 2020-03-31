package org.jeecg.modules.management.progressreport.service;

import org.jeecg.modules.management.progressreport.entity.ProgressReport;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.management.progressreport.vo.MobileReportDTO;

/**
 * @Description: 进度汇报
 * @Author: jeecg-boot
 * @Date:   2020-03-24
 * @Version: V1.0
 */
public interface IProgressReportService extends IService<ProgressReport> {

    /**
     * 通过汇报id查出历史记录
     * @param userName 用户名
     * @param progressId 进度id
     * @param reportId 汇报id
     * @return
     */
    MobileReportDTO getByReportId(String userName, String progressId, String reportId);

}
