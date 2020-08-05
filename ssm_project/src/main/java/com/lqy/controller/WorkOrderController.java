package com.lqy.controller;


import com.lqy.entity.Result;
import com.lqy.service.WorkOrderService;
import org.aspectj.lang.annotation.After;
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
@RequestMapping("manager")
public class WorkOrderController {


    @Autowired
    WorkOrderService service;

    @RequestMapping("admin/work")
    public ModelAndView toIndex(){
        return new ModelAndView("/work/admin/index");
    }

    @RequestMapping("admin/detail")
    public ModelAndView detail(){
        return new ModelAndView("/work/work-detail");
    }

    @RequestMapping("admin/work/selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize")int pageSize, @RequestBody Map<String,Object> params){
        return new Result(true,"查询成功",service.selectPage(pageNum,pageSize,params));
    }

    @RequestMapping("admin/work/selectById")
    public Result selectById(long id){
        return new Result(true,"查询成功",service.selectById(id));
    }

}
