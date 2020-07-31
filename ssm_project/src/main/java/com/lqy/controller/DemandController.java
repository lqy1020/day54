package com.lqy.controller;

import com.lqy.entity.Demand;
import com.lqy.entity.Result;
import com.lqy.service.DemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @auth lqy
 * @date 2020/7/18
 * @Description
 */
@RestController
@RequestMapping("/manager/demand")
public class DemandController {

    @Autowired
    DemandService demandService;

    @RequestMapping("index")
    public ModelAndView toIndex(){
       return new ModelAndView("/demand/index");
    }

    @RequestMapping("toUpdate")
    public ModelAndView toUpdate(){
        return new ModelAndView("/demand/update");
    }
    @RequestMapping("toDetail")
    public ModelAndView toDetail(){
        return new ModelAndView("/demand/detail");
    }

    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") Integer pageNum,@PathVariable("pageSize") Integer pageSize){

        return new Result(true,"查询成功",demandService.selectPage(pageNum,pageSize));
    }

    @RequestMapping(value = "doUpdate",method = RequestMethod.PUT)
    public Result doUpdate(@RequestBody Demand demand){
        return new Result(true,"更新成功",demandService.updateByPrimaryKeySelective(demand));
    }

}
