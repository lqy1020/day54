package com.lqy.controller;

import com.lqy.entity.Result;
import com.lqy.entity.SysArea;
import com.lqy.service.SysAreaService;
import com.lqy.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;


/**
 * @auth lqy
 * @date 2020/7/16
 * @Description
 */
@RestController
@RequestMapping("/manager/syslog")
public class SysLogController {

    @Autowired
    SysLogService service;

    @RequestMapping("")
    public ModelAndView toIndex(){
        return new ModelAndView("/log/log");
    }

    @RequestMapping("toDetail")
    public ModelAndView toModules(){
        return new ModelAndView("/log/log-detail");
    }


    @RequestMapping(value = "selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize,  @RequestBody Map<String,Object> params){
        return new Result(true,"查询成功",service.selectPage(pageNum,pageSize,params));

    }


}
