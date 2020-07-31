package com.lqy.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lqy.entity.Statute;
import com.lqy.mapper.StatuteMapper;
import com.lqy.service.StatuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @auth lqy
 * @date 2020/7/23
 * @Description
 */
@Service
@Transactional
public class StatueteServiceImpl extends BaseSerciveImpl<Statute> implements StatuteService {

    @Autowired
    StatuteMapper statuteMapper;

    @Override
    public PageInfo<Statute> selectPage(int pageNum, int pageSize, Statute statute){
        PageHelper.startPage(pageNum,pageSize);
        statute.setDelFlag("0");
        List<Statute> list = statuteMapper.select(statute);
        Page page = (Page) list;
        if (page.getPages()<pageNum){
            PageHelper.startPage(1,pageSize);
            list=statuteMapper.select(statute);
        }
        return new PageInfo<>(list);

    }


}
