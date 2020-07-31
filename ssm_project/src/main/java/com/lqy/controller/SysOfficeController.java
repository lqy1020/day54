package com.lqy.controller;

import com.lqy.entity.Result;
import com.lqy.entity.SysOffice;
import com.lqy.service.SysOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auth lqy
 * @date 2020/7/21
 * @Description
 */
@RestController
@RequestMapping("manager/office")
public class SysOfficeController {

    @Autowired
    SysOfficeService service;

    @RequestMapping("select")
    public Result select(){
        SysOffice sysOffice = new SysOffice();
        sysOffice.setDelFlag("0");
        return new Result(true,"查询成功",service.select(sysOffice));
    }
}
