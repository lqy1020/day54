package com.lqy.controller;

import com.lqy.entity.AppVersion;
import com.lqy.entity.Result;
import com.lqy.service.AppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;


/**
 * @auth lqy
 * @date 2020/7/16
 * @Description
 */
@RestController
@RequestMapping("/manager/app")
public class AppVersionController {

    @Autowired
    AppVersionService service;

    @RequestMapping("index")
    public ModelAndView toIndex(){
        return new ModelAndView("/app/index");
    }


    @RequestMapping("selectAll")
    public Result selectAll(){
//        String str=null;
//        str.length();
       Result result=new Result(true,"查询成功",service.selectAll());
       return result;
    }

    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize){
        Result result=new Result(true,"查询成功",service.selectPage(pageNum,pageSize));
        return result;
    }

    @RequestMapping("toUpdate")
    public ModelAndView toUpdate(){
        return new ModelAndView("/app/update");
    }

    @RequestMapping(value = "doUpdate" , method = RequestMethod.PUT)
    public Result doUpdate(@RequestBody AppVersion appVersion){
        appVersion.setUpdateDate(new Date());
        return new Result(true,"更新成功",service.updateByPrimaryKeySelective(appVersion));
    }

    @RequestMapping(value = "doInsert",method = RequestMethod.POST)
    public Result doInsert(@RequestBody AppVersion appVersion){
        return new Result(true,"保存成功",service.insertSelective(appVersion));
    }

}
