package com.lqy.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lqy.entity.SysResource;
import com.lqy.entity.SysRole;
import com.lqy.mapper.SysResourceMapper;
import com.lqy.service.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
public class SysResourceServiceImpl extends BaseSerciveImpl<SysResource> implements SysResourceService {

    @Autowired
    SysResourceMapper sysResourceMapper;

    @Override
    @Cacheable(cacheNames = "resourceCache",key = "'cn.nyse.service.impl.SysResourceServiceImpl.select.sysResource:'+#sysResource")
    public List<SysResource> select(SysResource sysResource) {
        return super.select(sysResource);
    }


    @Override
    public List<SysResource> selectByRid(Long rid){
        return sysResourceMapper.selectByRid(rid);
    }


    @Override
    public List<SysResource> selectByUid(Long uid){
        return sysResourceMapper.selectByUid(uid);
    }

    @Override
    public List<SysResource> selectResource(){
        return sysResourceMapper.selectResource();
    }


}
