package org.jeecg.modules.management.progressreport.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.management.file.entity.File;
import org.jeecg.modules.management.file.service.IFileService;
import org.jeecg.modules.management.progressreport.entity.ProgressReport;
import org.jeecg.modules.management.progressreport.service.IProgressReportService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;

 /**
 * @Description: 进度汇报
 * @Author: jeecg-boot
 * @Date:   2020-03-24
 * @Version: V1.0
 */
@RestController
@RequestMapping("/progressreport/progressReport")
@Slf4j
public class ProgressReportController extends JeecgController<ProgressReport, IProgressReportService> {
	@Autowired
	private IProgressReportService progressReportService;

	@Autowired
    private IFileService fileService;
	
	/**
	 * 分页列表查询
	 *
	 * @param progressReport
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ProgressReport progressReport,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ProgressReport> queryWrapper = QueryGenerator.initQueryWrapper(progressReport, req.getParameterMap());
		Page<ProgressReport> page = new Page<ProgressReport>(pageNo, pageSize);
		IPage<ProgressReport> pageList = progressReportService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param progressReport
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ProgressReport progressReport) {
		progressReportService.save(progressReport);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param progressReport
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ProgressReport progressReport) {
		progressReportService.updateById(progressReport);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		progressReportService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.progressReportService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ProgressReport progressReport = progressReportService.getById(id);
		if(progressReport==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(progressReport);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param progressReport
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ProgressReport progressReport) {
        return super.exportXls(request, progressReport, ProgressReport.class, "进度汇报");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ProgressReport.class);
    }

}
