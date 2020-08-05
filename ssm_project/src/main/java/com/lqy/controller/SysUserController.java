package com.lqy.controller;



import com.lqy.entity.Result;
import com.lqy.entity.SysUser;
import com.lqy.service.SysUserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @auth lqy
 * @date 2020/7/21
 * @Description
 */

@RestController
@RequestMapping("manager/sysuser")
public class SysUserController {

    @Autowired
    SysUserService service;


    @RequestMapping("selectByRid")
    public Result selectByRid(Long rid){
        return new Result(true,"查询成功",service.selectByRid(rid));
    }

    @RequestMapping("selectNoRole")
    public Result selectNoRole(@Param("oid") long oid, @Param("rid") long rid){
        return new Result(true,"查询成功",service.selectNoRole(oid,rid));
    }

}
