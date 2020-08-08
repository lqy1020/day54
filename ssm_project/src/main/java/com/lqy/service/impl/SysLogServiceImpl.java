package com.lqy.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lqy.entity.SysOffice;
import com.lqy.entity.SysResource;
import com.lqy.entity.SysLog  ;
import com.lqy.mapper.SysLogMapper;
import com.lqy.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @auth lqy
 * @date 2020/7/16
 * @Description
 */
/**
 * propagation:事务传播行为
 *     REQUIRED ：必须有事务 ，如果没有事务存在  会自动开启事务  ，如果有事务存在，则加入当前事务
 *     SUPPORTS ：支持当前事务，如果当前没有事务，就以非事务方式执行
 *     MANDATORY：  必须有事务，如果没有事务则抛异常
 *     REQUIRES_NEW ：  不管有没有事务，方法都会开启一个独立事务
 *     NOT_SUPPORTED  以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。
 *
 *
 * isolation:   事务隔离级别
 * 默认按数据库的默认隔离级别处理
 * readOnly:    是否只读事务     默认非只读事务  可以增删查改  只读事务只支持查询
 * rollbackFor:  默认只有非检查异常才会回滚，检查异常需要自定义设置才会回滚
 */

@Service

@Transactional(readOnly = false,isolation = Isolation.DEFAULT)

public class SysLogServiceImpl extends BaseSerciveImpl<SysLog  > implements SysLogService {

    @Autowired
    SysLogMapper sysLogMapper;


    @Override
    public  PageInfo<SysLog  > selectPage(int pageNum, int pageSize, Map<String, Object> params){

        PageHelper.startPage(pageNum,pageSize);
        List<SysLog> list = sysLogMapper.selectPage(params);
        /*
        * 方法1 前端页面添加改变触发事件 @change="_selectPage(1,pageInfo.pageSize)"重新查询
        * 方法2后台处理前端页面条件查询出现的bug，解决条件点击下一页出现的bug
        * */
        Page page=(Page) list;
        if (page.getPageNum()>page.getPages()){
            PageHelper.startPage(1,pageSize);
            list=sysLogMapper.selectPage(params);
        }

        PageInfo<SysLog> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    //当前方法会独立开启事务进行操作
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int insertSelective(SysLog sysLog) {
        return super.insertSelective(sysLog);
    }
}
