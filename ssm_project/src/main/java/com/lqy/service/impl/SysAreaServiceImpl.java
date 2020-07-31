package com.lqy.service.impl;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lqy.entity.SysArea;
import com.lqy.listener.SysAreaListener;
import com.lqy.mapper.SysAreaMapper;
import com.lqy.service.SysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @auth lqy
 * @date 2020/7/23
 * @Description
 */
@Service
@Transactional
public class SysAreaServiceImpl extends BaseSerciveImpl<SysArea> implements SysAreaService {

    @Autowired
    SysAreaMapper sysAreaMapper;

    @Override
    public PageInfo<SysArea> selectByPid(int pageNum, int pageSize, Long id) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysArea> list = sysAreaMapper.selectByPid(id);
        Page page = (Page) list;
        if (page.getPages() < pageNum) {
            PageHelper.startPage(1, pageSize);
            list = sysAreaMapper.selectByPid(id);
        }
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<SysArea> selectPage(int pageNum, int pageSize, String name) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysArea> list = sysAreaMapper.selectByName(name);
        Page page = (Page) list;
        if (page.getPages() < pageNum) {
            PageHelper.startPage(1, pageSize);
            list = sysAreaMapper.selectByName(name);
        }
        return new PageInfo<>(list);
    }

    @Override
    public int updateByPrimaryKeySelective(SysArea sysArea) {
        int result=0;
        try {
            result = sysAreaMapper.updateByPrimaryKeySelective(sysArea);
            if (!sysArea.getParentIds().equals(sysArea.getOldParentIds())) {
                result += sysAreaMapper.updateParentIds(sysArea);
            }
        } catch (Exception e) {
            throw new RuntimeException("更新失败...");
        }
        return result;
    }

    @Override
    public int deleteParentIds(SysArea sysArea){
        return sysAreaMapper.deleteParentIds(sysArea);
    }

    @Override
    public void download(OutputStream outputStream){
        SysArea sysArea = new SysArea();
        sysArea.setDelFlag("0");
        List<SysArea> list = mapper.select(sysArea);
        EasyExcel.write(outputStream,SysArea.class).sheet().doWrite(list);
    }

    @Override
    public void upload(InputStream inputStream){
        EasyExcel.read(inputStream,SysArea.class,new SysAreaListener(sysAreaMapper)).sheet().doRead();
    }


}
