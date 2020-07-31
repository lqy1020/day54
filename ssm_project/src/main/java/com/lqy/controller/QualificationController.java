package com.lqy.controller;

import com.lqy.entity.Qualification;
import com.lqy.entity.QualificationCondition;
import com.lqy.entity.Result;
import com.lqy.service.QualificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * @auth lqy
 * @date 2020/7/20
 * @Description
 */
@RestController
@RequestMapping("manager/qualification")
public class QualificationController {

    @Autowired
    QualificationService service;

    @RequestMapping("index")
    public ModelAndView toIndex(){
        return new ModelAndView("/qualification/index");
    }

    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum")int pageNum,@PathVariable("pageSize") int pageSize, @RequestBody QualificationCondition condition){
        return new Result(true,"查询成功",service.selectPage(pageNum,pageSize,condition));
    }


    @RequestMapping("toUpdate")
    public ModelAndView toUpdate(){
        return new ModelAndView("/qualification/update");
    }

    @RequestMapping("getPath/{uid}")
    public Result getPath(@PathVariable("uid") Long uid){
        return new Result(true,"查询成功",service.getPath(uid));
    }

    @RequestMapping(value = "doUpdate",method = RequestMethod.PUT)
    public Result doUpdate(@RequestBody Qualification qualification){
        qualification.setUpdateDate(new Date());
        return new Result(true,"更新成功",service.updateByPrimaryKeySelective(qualification));
    }

}
