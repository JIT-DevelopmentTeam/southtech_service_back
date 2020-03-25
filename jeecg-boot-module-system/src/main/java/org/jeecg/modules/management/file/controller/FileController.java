package org.jeecg.modules.management.file.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.management.file.entity.File;
import org.jeecg.modules.management.file.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

 /**
 * @Description: 文件表
 * @Author: jeecg-boot
 * @Date:   2020-03-25
 * @Version: V1.0
 */
@RestController
@RequestMapping("/file/file")
@Slf4j
public class FileController extends JeecgController<File, IFileService> {
	@Autowired
	private IFileService fileService;
	
	/**
	 * 分页列表查询
	 *
	 * @param file
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(File file,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<File> queryWrapper = QueryGenerator.initQueryWrapper(file, req.getParameterMap());
		Page<File> page = new Page<File>(pageNo, pageSize);
		IPage<File> pageList = fileService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param file
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody File file) {
		fileService.save(file);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param file
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody File file) {
		fileService.updateById(file);
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
		fileService.removeById(id);
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
		this.fileService.removeByIds(Arrays.asList(ids.split(",")));
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
		File file = fileService.getById(id);
		if(file==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(file);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param file
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, File file) {
        return super.exportXls(request, file, File.class, "文件表");
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
        return super.importExcel(request, response, File.class);
    }

}
