package com.lqy.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lqy.entity.Demand;
import com.lqy.mapper.DemandMapper;
import com.lqy.service.DemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @auth lqy
 * @date 2020/7/18
 * @Description
 */

@Service
@Transactional
public class DemandServiceImpl extends BaseSerciveImpl<Demand> implements DemandService {

    @Autowired
    DemandMapper demandMapper;
    public PageInfo<Demand> getPageInfo(int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        Demand demand = new Demand();
        demand.setDelFlag("0");
        List<Demand> list = demandMapper.select(demand);
        Page page= (Page) list;
        if (page.getPages()<pageNum){
            PageHelper.startPage(1,pageSize);
            list=demandMapper.select(demand);
        }

        PageInfo<Demand> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

}
