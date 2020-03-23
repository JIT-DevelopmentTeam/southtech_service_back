package org.jeecg.modules.management.mobile.token.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.management.mobile.token.entity.Token;
import org.jeecg.modules.management.mobile.token.service.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

 /**
 * @Description: token表
 * @Author: jeecg-boot
 * @Date:   2020-03-23
 * @Version: V1.0
 */
@RestController
@RequestMapping("/token/token")
@Slf4j
public class TokenController extends JeecgController<Token, ITokenService> {
	@Autowired
	private ITokenService tokenService;
	
	/**
	 * 分页列表查询
	 *
	 * @param token
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Token token,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Token> queryWrapper = QueryGenerator.initQueryWrapper(token, req.getParameterMap());
		Page<Token> page = new Page<Token>(pageNo, pageSize);
		IPage<Token> pageList = tokenService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param token
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Token token) {
		tokenService.save(token);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param token
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody Token token) {
		tokenService.updateById(token);
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
		tokenService.removeById(id);
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
		this.tokenService.removeByIds(Arrays.asList(ids.split(",")));
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
		Token token = tokenService.getById(id);
		if(token==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(token);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param token
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Token token) {
        return super.exportXls(request, token, Token.class, "token表");
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
        return super.importExcel(request, response, Token.class);
    }

}
