package com.lqy.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lqy.entity.Examine;
import com.lqy.mapper.ExamineMapper;
import com.lqy.service.ExamineService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ExamineServiceImpl extends BaseSerciveImpl<Examine> implements ExamineService {

    @Autowired
    ExamineMapper examineMapper;

    @Override
    public PageInfo<Examine> selectPage(int pageNum, int pageSize, Map<String,Object> params) {

        PageHelper.startPage(pageNum,pageSize);
        List<Examine> list = examineMapper.selectPage(params);
        /*
        * 方法1 前端页面添加改变触发事件 @change="_selectPage(1,pageInfo.pageSize)"重新查询
        * 方法2后台处理前端页面条件查询出现的bug，解决条件点击下一页出现的bug
        * */
        Page page=(Page) list;
        if (page.getPageNum()>page.getPages()){
            PageHelper.startPage(1,pageSize);
            list=examineMapper.selectPage(params);
        }

        PageInfo<Examine> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }


}
