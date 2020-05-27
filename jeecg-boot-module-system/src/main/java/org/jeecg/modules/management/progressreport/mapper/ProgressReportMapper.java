package org.jeecg.modules.management.progressreport.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.management.progressreport.entity.ProgressReport;
import org.jeecg.modules.management.progressreport.vo.MobileReportDTO;
import org.jeecg.modules.management.workorder.vo.MobileWorkOrderDetailDTO;

import java.util.List;

/**
 * @Description: 进度汇报
 * @Author: jeecg-boot
 * @Date:   2020-03-24
 * @Version: V1.0
 */
public interface ProgressReportMapper extends BaseMapper<ProgressReport> {

    /**
     * 通过汇报id查出历史记录
     * @param userName 用户名
     * @param progressId 进度id
     * @param reportId 汇报id
     * @return
     */
    MobileReportDTO getByReportId(@Param("progressId") String progressId, @Param("reportId") String reportId);

    /**
     * 根据workOrderId查询汇报记录列表
     * @param workOrderId
     * @return
     */
    List<MobileWorkOrderDetailDTO> getByWorkOrderId(@Param("workOrderId") String workOrderId);

}
