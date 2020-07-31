package com.lqy.controller;

import com.lqy.entity.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @auth lqy
 * @date 2020/7/17
 * @Description
 */
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler
    @ResponseBody
    public Result getException(Exception e){
        System.out.println(e.getMessage());
        return new Result(false,e.getMessage(),null);

    }

}
