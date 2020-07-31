package com.lqy.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lqy.entity.Qualification;
import com.lqy.entity.QualificationCondition;
import com.lqy.entity.SysUser;
import com.lqy.mapper.QualificationMapper;
import com.lqy.mapper.SysUserMapper;
import com.lqy.service.QualificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @auth lqy
 * @date 2020/7/16
 * @Description
 */
@Service
@Transactional
public class QualificationServiceImpl extends BaseSerciveImpl<Qualification> implements QualificationService {

    @Autowired
    QualificationMapper qualificationMapper;

    @Autowired
    SysUserMapper sysUserMapper;

    @Value("/uploads/")
    String path;

    @Override
    public PageInfo<Qualification> selectPage(int pageNum, int pageSize, QualificationCondition condition) {
        PageHelper.startPage(pageNum,pageSize);
        List<Qualification> list = qualificationMapper.selectPage(condition);

        /*
        * 方法1 前端页面添加改变触发事件 @change="_selectPage(1,pageInfo.pageSize)"重新查询
        * 方法2后台处理前端页面条件查询出现的bug，解决条件点击下一页出现的bug
        * */
        Page page=(Page) list;
        if (page.getPageNum()>page.getPages()){
            PageHelper.startPage(1,pageSize);
            list=qualificationMapper.selectPage(condition);
        }
        PageInfo<Qualification> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public String getPath(long uid){
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(uid);
        return path+sysUser.getOfficeId();
    }

}
