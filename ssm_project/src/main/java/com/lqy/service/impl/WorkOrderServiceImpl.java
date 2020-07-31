package com.lqy.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lqy.entity.WorkOrder;
import com.lqy.mapper.DetailMapper;
import com.lqy.mapper.TransferMapper;
import com.lqy.mapper.WorkOrderMapper;
import com.lqy.service.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @auth lqy
 * @date 2020/7/16
 * @Description
 */
@Service
@Transactional
public class WorkOrderServiceImpl extends BaseSerciveImpl<WorkOrder> implements WorkOrderService {

    @Autowired
    WorkOrderMapper workOrderMapper;

    @Autowired
    DetailMapper detailMapper;

    @Autowired
    TransferMapper transferMapper;

    @Cacheable(cacheNames = "orderCache",key = "'com.lqy.service.impl.WorkOrderServiceImpl.'+methodName+':pageNum:'+#pageNum+':pageSize:'+#pageSize+':params[status]:'+#params['status']")
    @Override
    public PageInfo<WorkOrder> selectPage(int pageNum, int pageSize, Map<String,Object> params) {

        PageHelper.startPage(pageNum,pageSize);
        List<WorkOrder> list = workOrderMapper.selectPage(params);
        /*
        * 方法1 前端页面添加改变触发事件 @change="_selectPage(1,pageInfo.pageSize)"重新查询
        * 方法2后台处理前端页面条件查询出现的bug，解决条件点击下一页出现的bug
        * */
        Page page=(Page) list;
        if (page.getPageNum()>page.getPages()){
            PageHelper.startPage(1,pageSize);
            list=workOrderMapper.selectPage(params);
        }

        PageInfo<WorkOrder> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }


    @Override
    public Map<String, Object> selectById(long id) {

        Map<String, Object> workOrder = workOrderMapper.selectById(id);

        List<Map<String, Object>> details = detailMapper.selectById(id);

        List<Map<String, Object>> transfer = transferMapper.selectById(id);

        workOrder.put("details",details);
        workOrder.put("transfers",transfer);

        return workOrder;
    }
}
