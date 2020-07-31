package com.lqy.controller;

import com.lqy.entity.Result;
import com.lqy.service.ExamineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @auth lqy
 * @date 2020/7/21
 * @Description
 */

@RestController
@RequestMapping("manager/examine")
public class ExamineController {

    @Autowired
    ExamineService service;

    @RequestMapping("index")
    public ModelAndView toIndex(){
        return new ModelAndView("/examine/index");
    }

//    @RequestMapping("selectPage/{pageNum}/{pageSize}")
//    public Result selectPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize")int pageSize, @RequestBody Map<String,Object> params){
//        params.put("type","1");
//        params.put("officeId","56");
//        return new Result(true,"查询成功",service.selectPage(pageNum,pageSize,params));
//    }

    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum")int pageNum,@PathVariable("pageSize")int pageSiz,@RequestBody Map<String,Object>params){
        return new Result(true,"查询成功",service.selectPage(pageNum,pageSiz,params));
    }



}
