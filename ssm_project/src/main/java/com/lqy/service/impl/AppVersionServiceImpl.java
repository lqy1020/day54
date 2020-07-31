package com.lqy.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lqy.entity.AppVersion;
import com.lqy.service.AppVersionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @auth lqy
 * @date 2020/7/16
 * @Description
 */
@Service
@Transactional
public class AppVersionServiceImpl extends BaseSerciveImpl<AppVersion> implements AppVersionService {

    @Override
    public PageInfo<AppVersion> selectPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        AppVersion appVersion = new AppVersion();
        appVersion.setDelFlag("0");
        List<AppVersion> list = mapper.select(appVersion);
        PageInfo<AppVersion> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int insertSelective(AppVersion appVersion) {
        appVersion.setCreateDate(new Date());
        appVersion.setUpdateDate(new Date());
        appVersion.setDelFlag("0");
        return mapper.insertSelective(appVersion);
    }
}
