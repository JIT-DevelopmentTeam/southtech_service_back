package org.jeecg.modules.management.item.thread;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.management.BaseEntity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author KicoChan
 */
public class ThreadRunItem<T extends BaseEntity> implements Runnable {

    JSONArray dataArray;
    Method saveMethod;
    IService service;
    Object object;
    List<T> dataList;
    List<T> list = new ArrayList<>();

    public ThreadRunItem(JSONArray dataArray, Method saveMethod, IService service, List<T> dataList, Object object) {
        this.dataArray = dataArray;
        this.saveMethod = saveMethod;
        this.service = service;
        this.object = object;
        this.dataList = dataList;
    }

    @Override
    public void run() {
        for (int i = 0; i < dataArray.size(); i++) {
            JSONObject data = dataArray.getJSONObject(i);
            T obj = null;
            try {
                obj = (T) saveMethod.invoke(object, data);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            if (dataList.isEmpty()) {
                list.add(obj);
                if (i > 0 && i % 5000 == 0 || i == dataArray.size() - 1) {
                    service.saveBatch(list, 5000);
                    list.clear();
                }
            } else {
                for (int j = 0; j < dataList.size(); j++) {
                    if (obj.getNumber().equals(dataList.get(j).getNumber())) {
                        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
                        queryWrapper.eq("id", dataList.get(j).getId());
                        service.update(obj, queryWrapper);
                    } else {
                        list.add(obj);
                        if (i > 0 && i % 5000 == 0 || i == dataArray.size() - 1) {
                            service.saveBatch(list, 5000);
                            list.clear();
                        }
                    }
                }
            }
        }
    }

}
