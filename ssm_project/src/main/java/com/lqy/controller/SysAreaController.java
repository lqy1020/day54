package com.lqy.controller;

import com.lqy.entity.Result;
import com.lqy.entity.SysArea;
import com.lqy.service.SysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


/**
 * @auth lqy
 * @date 2020/7/16
 * @Description
 */
@RestController
@RequestMapping("/manager/area")
public class SysAreaController {

    @Autowired
    SysAreaService service;

    @RequestMapping("")
    public ModelAndView toIndex(){
        return new ModelAndView("/area/index");
    }

    @RequestMapping("toModules")
    public ModelAndView toModules(){
        return new ModelAndView("/modules/font-awesome");
    }

    @RequestMapping("toSelect")
    public ModelAndView toSelect(){
        return new ModelAndView("/area/select");
    }

    @RequestMapping("select")
    public Result select(){
        SysArea sysArea = new SysArea();
        sysArea.setDelFlag("0");
        return new Result(true,"查询成功",service.select(sysArea));
    }


    @RequestMapping(value = "selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize, String name){
        Result result=new Result(true,"查询成功",service.selectPage(pageNum,pageSize,name));
        return result;
    }
    @RequestMapping(value = "selectByPid/{pageNum}/{pageSize}")
    public Result selectByPid(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize, Long id){
        Result result=new Result(true,"查询成功",service.selectByPid(pageNum,pageSize,id));
        return result;
    }

    @RequestMapping("toUpdate")
    public ModelAndView toUpdate(){
        return new ModelAndView("/area/update");
    }

    @RequestMapping(value = "doUpdate" , method = RequestMethod.PUT)
    public Result doUpdate(@RequestBody SysArea sysArea){
        sysArea.setUpdateDate(new Date());
        return new Result(true,"更新成功",service.updateByPrimaryKeySelective(sysArea));
    }

    @RequestMapping(value = "doDelete",method =RequestMethod.PUT)
    public Result doDelete(@RequestBody SysArea sysArea){
        return new Result(true,"删除成功",service.deleteParentIds(sysArea));
    }

    @RequestMapping(value = "doInsert",method = RequestMethod.POST)
    public Result doInsert(SysArea sysArea){
        sysArea.setCreateDate(new Date());
        sysArea.setUpdateDate(new Date());
        return new Result(true,"添加成功",service.insertSelective(sysArea));
    }

    @RequestMapping("download")
    public void download(HttpServletResponse response) throws IOException {
        response.setHeader("Content-Disposition","attachment;filename="+new String("区域.xls".getBytes(),"iso-8859-1"));
        service.download( response.getOutputStream());
    }

    @RequestMapping("upload")
    public Result upload(MultipartFile file) throws IOException {
        service.upload(file.getInputStream());
        return new Result(true,"导入数据成功",null);
    }

}
