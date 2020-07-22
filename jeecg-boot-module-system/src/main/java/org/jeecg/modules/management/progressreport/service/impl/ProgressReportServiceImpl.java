package org.jeecg.modules.management.progressreport.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.management.progressreport.entity.ProgressReport;
import org.jeecg.modules.management.progressreport.mapper.ProgressReportMapper;
import org.jeecg.modules.management.progressreport.service.IProgressReportService;
import org.jeecg.modules.management.progressreport.vo.ExportReportDTO;
import org.jeecg.modules.management.progressreport.vo.MobileReportDTO;
import org.jeecg.modules.management.workorder.vo.MobileWorkOrderDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 进度汇报
 * @Author: jeecg-boot
 * @Date:   2020-03-24
 * @Version: V1.0
 */
@Service
public class ProgressReportServiceImpl extends ServiceImpl<ProgressReportMapper, ProgressReport> implements IProgressReportService {

    @Autowired
    private ProgressReportMapper progressReportMapper;

    /**
     * 通过汇报id查出历史记录
     * @param userName 用户名
     * @param progressId 进度id
     * @param reportId 汇报id
     * @return
     */
    @Override
    public MobileReportDTO getByReportId(String progressId, String reportId) {
        return progressReportMapper.getByReportId(progressId, reportId);
    }

    /**
     * 根据workOrderId查询汇报记录列表
     * @param workOrderId
     * @return
     */
    @Override
    public List<MobileWorkOrderDetailDTO> getByWorkOrderId(String workOrderId) {
        return progressReportMapper.getByWorkOrderId(workOrderId);
    }

    /**
     * 根据workOrderDetailId查询汇报记录列表
     * @param workOrderDetailId
     * @return
     */
    @Override
    public List<ExportReportDTO> getByWorkOrderDetailId(String workOrderDetailId) {
        return progressReportMapper.getByWorkOrderDetailId(workOrderDetailId);
    }
}
