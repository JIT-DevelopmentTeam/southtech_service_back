package org.jeecg.modules.management.stage.service.impl;

import cn.hutool.core.exceptions.ValidateException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.management.stage.entity.Stage;
import org.jeecg.modules.management.stage.mapper.StageMapper;
import org.jeecg.modules.management.stage.service.IStageService;
import org.jeecg.modules.management.stage.vo.MobileStageDTO;
import org.jeecg.modules.system.service.ISysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 阶段信息
 * @Author: jeecg-boot
 * @Date:   2020-03-13
 * @Version: V1.0
 */
@Service
public class StageServiceImpl extends ServiceImpl<StageMapper, Stage> implements IStageService {

    @Autowired
    ISysDictService sysDictService;

    @Autowired
    private StageMapper stageMapper;



    @Override
    public boolean save(Stage entity) {

        /**
         * 检查同一工单类型是否存在序号相同的阶段
         */
        QueryWrapper<Stage> stageQueryWrapper=new QueryWrapper<Stage>();

        stageQueryWrapper.eq("work_order_type",entity.getWorkOrderType())
                .eq("order_index",entity.getOrderIndex());

        int count= this.count(stageQueryWrapper);

        if(count>0){

            String wrokOrderType= sysDictService.queryDictTextByKey("work_order_type",entity.getWorkOrderType());
            String errText=String.format("新增阶段失败：【%s】 已存在排序为【%d】的阶段！",wrokOrderType,entity.getOrderIndex());
            throw new ValidateException(errText);
        }

        

        return super.save(entity);
    }

    /**
     * 根据工单id查询进度列表
     * @param workOrderId
     * @return
     */
    @Override
    public List<MobileStageDTO> queryStageByWorkOrderId(String workOrderId) {
        return stageMapper.queryStageByWorkOrderId(workOrderId);
    }
}
