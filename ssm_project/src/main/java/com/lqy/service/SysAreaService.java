package com.lqy.service;

import com.github.pagehelper.PageInfo;
import com.lqy.entity.SysArea;

import java.io.InputStream;
import java.io.OutputStream;

public interface SysAreaService extends BaseService<SysArea> {


//    PageInfo<SysArea> selectPage(int pageNum, int pageSize, SysArea sysArea);


    PageInfo<SysArea> selectByPid(int pageNum, int pageSize, Long id);

    PageInfo<SysArea> selectPage(int pageNum, int pageSize, String name);


    int deleteParentIds(SysArea sysArea);

    void download(OutputStream outputStream);

    void upload(InputStream inputStream);
}
