package org.jeecg.modules.management.item.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.HttpHelper;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.management.BaseEntity;
import org.jeecg.modules.management.erp.erpinterface.ERPInterfaceConstant;
import org.jeecg.modules.management.item.entity.Item;
import org.jeecg.modules.management.item.service.IItemService;
import org.jeecg.modules.management.item.thread.ThreadRunItem;
import org.jeecg.modules.management.itemclass.entity.ItemClass;
import org.jeecg.modules.management.itemclass.service.IItemClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

	@Autowired
	private IItemClassService itemClassService;

	@Autowired
	private RedisUtil redisUtil;
	
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
		if (StringUtils.isNotEmpty(item.getNumber())) {
			item.setNumber(item.getNumber() + "*");
		}
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

	 /**
	  * 同步ERP物料信息
	  * @return
	  */
    @RequestMapping(value = "/sync", method = RequestMethod.GET)
	public Result<?> sync() {
    	String msg = "";
		try {
			msg = synchronization(ItemClass.class, itemClassService, ERPInterfaceConstant.LIST_ITEM_CLASS_URL, "1");
			msg = synchronization(Item.class, itemService, ERPInterfaceConstant.LIST_ITEM_URL, "2");
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
    	return Result.ok(msg);
	}


	public <T extends BaseEntity> String synchronization(Class<T> tClass, IService service, String url, String syncType) throws Exception {
		QueryWrapper<T> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByDesc("modifytime");
		List<T> dataList = service.list(queryWrapper);
		try {
			url = url.replace("TOKEN", redisUtil.get(ERPInterfaceConstant.TOKEN_KEY).toString());
			if (dataList.isEmpty()) {
				url = url.replace("MAX", "");
			} else {
				url = url.replace("MAX", dataList.get(0).getModifytime().getTime() + "");
			}
			System.out.println("-->" + url);
			Method saveMethod = null;
			switch (syncType) {
				case "1":
					saveMethod = this.getClass().getMethod("saveItemClass", JSONObject.class);
					break;
				case "2":
					saveMethod = this.getClass().getMethod("saveItem", JSONObject.class);
					break;
			}
			List<T> list = new ArrayList<>();
			JSONObject jsonObject = HttpHelper.httpGet(url);
			JSONArray dataArray = jsonObject.getJSONArray("data");
            if (dataList.isEmpty() && "2".equals(syncType) && dataArray.size() > 10000) {
                // 首次同步
                JSONArray itemArray1 = new JSONArray();
                JSONArray itemArray2 = new JSONArray();
                JSONArray itemArray3 = new JSONArray();
                JSONArray itemArray4 = new JSONArray();
                JSONArray itemArray5 = new JSONArray();
                // 数据拆分五个数组
                for (int i = 0; i < dataArray.size(); i++) {
                    int remainder = i % 5;
                    switch (remainder) {
                        case 0 :
                            itemArray1.add(dataArray.get(i));
                            break;
                        case 1:
                            itemArray2.add(dataArray.get(i));
                            break;
                        case 2:
                            itemArray3.add(dataArray.get(i));
                            break;
                        case 3:
                            itemArray4.add(dataArray.get(i));
                            break;
                        case 4:
                            itemArray5.add(dataArray.get(i));
                            break;
                    }
                }
                ThreadRunItem threadRunItem1 = new ThreadRunItem(dataArray,saveMethod,service,this);
                ThreadRunItem threadRunItem2 = new ThreadRunItem(dataArray,saveMethod,service,this);
                ThreadRunItem threadRunItem3 = new ThreadRunItem(dataArray,saveMethod,service,this);
                ThreadRunItem threadRunItem4 = new ThreadRunItem(dataArray,saveMethod,service,this);
                ThreadRunItem threadRunItem5 = new ThreadRunItem(dataArray,saveMethod,service,this);
                System.out.println("======>>开始:"+ DateUtils.formatDateTime());
                threadRunItem1.run();
                threadRunItem2.run();
                threadRunItem3.run();
                threadRunItem4.run();
                threadRunItem5.run();

                System.out.println("======>>结束:"+DateUtils.formatDateTime());
            } else {
                // 增量同步
                saveItemBatch(dataArray,saveMethod,list,service);
            }
		} catch (Exception e) {
			e.printStackTrace();
			String msg = "";
			switch (syncType) {
				case "1":
					msg = "物料分类：";
					break;
				case "2":
					msg = "物料：";
					break;
			}
			log.error(msg + "同步出错!");
			throw new Exception(msg + "同步出错,请联系管理员!");
		}
		return "同步成功";
	}

    /**
     * 批量储存商品
     */
	private void saveItemBatch(JSONArray dataArray,Method saveMethod,List list,IService service) {
        for (int i = 0; i < dataArray.size(); i++) {
            JSONObject data = dataArray.getJSONObject(i);
            T obj = null;
            try {
                obj = (T) saveMethod.invoke(this, data);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            list.add(obj);
            if (i % 10000 == 0 || i == dataArray.size() - 1) {
                service.saveBatch(list, 10000);
                list.clear();
            }
        }
    }

	// 物料信息保存方法
	public Item saveItem(JSONObject data) {
    	Item item = new Item();
    	item.setName(data.getString("iName"));
    	item.setNumber(data.getString("iNumber"));
    	item.setModel(data.getString("iModel"));
    	item.setUname(data.getString("uName"));
    	item.setSname(data.getString("sName"));
    	item.setIkfperiod(data.getString("iKFPeriod"));
    	item.setModifytime(data.getTimestamp("FModifyTime"));
    	return item;
	}

	// 物料分类保存方法
	public ItemClass saveItemClass(JSONObject data) {
    	ItemClass itemClass = new ItemClass();
    	itemClass.setId(data.getString("FClassID"));
    	itemClass.setPid(data.getString("FParentID"));
    	itemClass.setName(data.getString("FName"));
    	itemClass.setNumber(data.getString("FNumber"));
    	itemClass.setModifytime(data.getTimestamp("FModifyTime"));
    	return itemClass;
	}

}
