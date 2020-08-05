package com.lqy.controller;

import com.lqy.entity.Result;
import com.lqy.entity.SysResource;
import com.lqy.service.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auth lqy
 * @date 2020/7/21
 * @Description
 */
@RestController
@RequestMapping("manager/menu")
public class SysResourceController {

    @Autowired
    SysResourceService service;

    @RequestMapping("list")
    public Result select(){
        SysResource sysResource = new SysResource();
        sysResource.setDelFlag("0");
        return new Result(true,"查询成功",service.select(sysResource));
    }
    @RequestMapping("selectByRid")
    public Result selectByRid(Long rid){
        return new Result(true,"查询成功",service.selectByRid(rid));
    }
}
