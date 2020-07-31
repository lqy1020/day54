package com.lqy.service;

import com.github.pagehelper.PageInfo;
import com.lqy.entity.Qualification;
import com.lqy.entity.QualificationCondition;
import com.lqy.entity.WorkOrder;

import java.util.Map;


public interface WorkOrderService extends BaseService<WorkOrder> {

    PageInfo<WorkOrder> selectPage(int pageNum, int pageSize, Map<String,Object> params);

    Map<String,Object> selectById(long id);

}
