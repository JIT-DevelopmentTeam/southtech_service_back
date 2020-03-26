package org.jeecg.modules.management.mobile.stage;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.management.progressreport.entity.ProgressReport;
import org.jeecg.modules.management.progressreport.service.IProgressReportService;
import org.jeecg.modules.management.signin.entity.SignIn;
import org.jeecg.modules.management.signin.service.ISignInService;
import org.jeecg.modules.management.stage.service.IStageService;
import org.jeecg.modules.management.stage.vo.MobileStageDTO;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/mobile/stage")
public class MobileStageController {

    @Autowired
    private IStageService stageService;

    @Autowired
    private ISignInService signInService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private IProgressReportService progressReportService;

    @RequestMapping(value = "/queryStageByWorkOrderId")
    public Result<?> queryStageByWorkOrderId(HttpServletRequest req) {
        List<MobileStageDTO> list = stageService.queryStageByWorkOrderId(req.getParameter("workOrderId"));
        return Result.ok(list);
    }

    @RequestMapping(value = "/progressReport", method = RequestMethod.POST)
    public Result<?> progressReport(@RequestParam Map<String, Object> params) {
        SysUser user = sysUserService.getByEnterpriseId(params.get("userId").toString());
        // 签到 签出保存
        signInSave("1", params, user, signInService);
        signInSave("2", params, user, signInService);
        // 进度汇报保存
        reportSave(params);
        return Result.ok();
    }

    /**
     * 签到表（保存操作）
     * @param type
     * @param params
     * @param user
     * @param signInService
     */
    private void signInSave(String type, Map<String, Object> params, SysUser user, ISignInService signInService) {
        SignIn signIn = new SignIn();
        signIn.setProgressId(params.get("progressId").toString());
        signIn.setServiceEngineerName(user.getUsername());
        signIn.setType(type);
        switch (type) {
            case "1":
                signIn.setLongitude(Double.parseDouble(params.get("signInLongitude").toString()));
                signIn.setLatitude(Double.parseDouble(params.get("signInLatitude").toString()));
                break;
            case "2":
                signIn.setLongitude(Double.parseDouble(params.get("signOutLongitude").toString()));
                signIn.setLatitude(Double.parseDouble(params.get("signOutLatitude").toString()));
                break;
        }
        signInService.save(signIn);
    }

    /**
     * 进度汇报（保存操作）
     * @param params
     */
    private void reportSave(Map<String, Object> params) {
        ProgressReport progressReport = new ProgressReport();
        progressReport.setDescription(params.get("faultJudgement").toString());
        progressReport.setProgressId(params.get("progressId").toString());
        progressReport.setIsCompleted(params.get("completeStatus").toString());
        progressReport.setWorkOrderDetailId(params.get("detailId").toString());
        progressReport.setWorkOrderId(params.get("ticketId").toString());
        progressReport.setFaultLocation(params.get("faultLocation").toString());
        progressReportService.save(progressReport);
    }

}
