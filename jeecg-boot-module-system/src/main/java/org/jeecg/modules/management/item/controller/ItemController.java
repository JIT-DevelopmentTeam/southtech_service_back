package org.jeecg.modules.management.item.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.management.item.entity.Item;
import org.jeecg.modules.management.item.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

 /**
 * @Description: 物料
 * @Author: jeecg-boot
 * @Date:   2020-03-11
 * @Version: V1.0
 */
@RestController
@RequestMapping("/item/item")
@Slf4j
public class ItemController extends JeecgController<Item, IItemService> {
	@Autowired
	private IItemService itemService;
	
	/**
	 * 分页列表查询
	 *
	 * @param item
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Item item,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Item> queryWrapper = QueryGenerator.initQueryWrapper(item, req.getParameterMap());
		Page<Item> page = new Page<Item>(pageNo, pageSize);
		IPage<Item> pageList = itemService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param item
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Item item) {
		itemService.save(item);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param item
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody Item item) {
		itemService.updateById(item);
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
		itemService.removeById(id);
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
		this.itemService.removeByIds(Arrays.asList(ids.split(",")));
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
		Item item = itemService.getById(id);
		if(item==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(item);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param item
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Item item) {
        return super.exportXls(request, item, Item.class, "物料");
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
        return super.importExcel(request, response, Item.class);
    }

    @RequestMapping(value = "/sync", method = RequestMethod.GET)
	 public Result<?> sync() {

    	return Result.ok("同步成功");
	}

}
