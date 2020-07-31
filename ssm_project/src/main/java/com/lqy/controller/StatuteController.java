package com.lqy.controller;

import com.lqy.entity.Statute;
import com.lqy.entity.Result;
import com.lqy.service.StatuteService;
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
@RequestMapping("/manager/statute")
public class StatuteController {

    @Autowired
    StatuteService service;

    @RequestMapping("index")
    public ModelAndView toIndex(){
        return new ModelAndView("/statute/index");
    }


    @RequestMapping(value = "selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize,Integer type){
        Statute statute = new Statute();
        statute.setType(type);
        Result result=new Result(true,"查询成功",service.selectPage(pageNum,pageSize,statute));
        return result;
    }

    @RequestMapping("toUpdate")
    public ModelAndView toUpdate(){
        return new ModelAndView("/statute/update");
    }

    @RequestMapping(value = "doUpdate" , method = RequestMethod.PUT)
    public Result doUpdate(@RequestBody Statute statute){
        statute.setUpdateDate(new Date());
        return new Result(true,"更新成功",service.updateByPrimaryKeySelective(statute));
    }



    @RequestMapping(value = "doInsert",method = RequestMethod.POST)
    public Result doInsert(@RequestBody Statute statute){
        statute.setCreateDate(new Date());
        statute.setUpdateDate(new Date());
        statute.setDelFlag("0");
        return new Result(true,"保存成功",service.insertSelective(statute));
    }

}
