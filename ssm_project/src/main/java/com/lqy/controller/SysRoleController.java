package com.lqy.controller;


import com.lqy.entity.Result;
import com.lqy.entity.SysRole;
import com.lqy.service.SysRoleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @auth lqy
 * @date 2020/7/21
 * @Description
 */

@RestController
@RequestMapping("manager/role")
public class SysRoleController {


    @Autowired
    SysRoleService service;

    @RequestMapping("")
    public ModelAndView toIndex(){
        return new ModelAndView("/role/role");
    }

    @RequestMapping("toUser")
    public ModelAndView toUser(){
        return new ModelAndView("/role/role-user");
    }

    @RequestMapping("toUpdate")
    public ModelAndView toUpdate(){
        return new ModelAndView("/role/role-save");
    }

    @RequestMapping("toSelect")
    public ModelAndView toSelect(){
        return new ModelAndView("/role/role-select");
    }

    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize")int pageSize, @RequestBody Map<String,Object> params){
        return new Result(true,"查询成功",service.selectPage(pageNum,pageSize,params));
    }

    @RequestMapping("deleteBatch")
    public Result deleteBatch(@Param("rid") long rid, @Param("ids") long[] ids){
        return new Result(true,"删除成功",service.deleteBatch(rid,ids));
    }
    @RequestMapping("insertBatch")
    public Result insertBatch(@Param("rid") long rid, @Param("cids")Long[] cids){
        List<Long> list = Arrays.asList(cids);
        return new Result(true,"角色添加人员成功",service.insertBatch(rid,list));
    }

    @RequestMapping(value = "doUpdate",method = RequestMethod.PUT)
    public Result doUpdate(@RequestBody SysRole sysRole){
        sysRole.setUpdateDate(new Date());
       return new Result(true,"更新成功",service.updateByPrimaryKeySelective(sysRole));
    }

}
