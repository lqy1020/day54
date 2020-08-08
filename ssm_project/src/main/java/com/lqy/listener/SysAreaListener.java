package com.lqy.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lqy.entity.SysArea;
import com.lqy.mapper.SysAreaMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @auth lqy
 * @date 2020/7/30
 * @Description
 */

/*
 * 分析事件监听器：
 * 实现了读监听接口，需要实现读取一行自动调用的方法invoke和读取文件完成后自动调用的方法doAfterAllAnalysed
 * easyexcel官方规范：需要使用spring管理的bean通过构造器传入，不要将SysAreaListener给spring容器管理
 * */
public class SysAreaListener extends AnalysisEventListener<SysArea> {

    SysAreaMapper mapper;
    List<SysArea>list=new ArrayList<>();

    public SysAreaListener(SysAreaMapper mapper) {
        this.mapper = mapper;
    }

    public SysAreaListener() {
    }

    /*
     * 每读取一行excel自动执行的方法
     * sysArea是解析一行excel记录后自动封装的java对象
     * analysisContext是easyexcel的上下文对象
     * */
    @Override
    public void invoke(SysArea sysArea, AnalysisContext analysisContext) {
        list.add(sysArea);
        if (list.size()==10){
//            sysAreaMapper.insertBatch(list);
//            list.clear();
//            System.out.println("批量插入。。。。");
            mapper.insertBatch(list);
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (list.size()>0){
//            sysAreaMapper.insertBatch(list);
//            list.clear();
//            System.out.println("最后一条插入...");
            mapper.insertBatch(list);

        }
    }
}
