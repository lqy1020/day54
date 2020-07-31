package com.lqy.controller;

import com.lqy.entity.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @auth lqy
 * @date 2020/7/17
 * @Description
 */

@Controller
@RequestMapping("/main")
public class MainController {

    @RequestMapping("/navbar")
    public String getNavbar(){
       return "/common/navbar";
    }
    @RequestMapping("/sidebar")
    public String getSidebar(){
        return "/common/sidebar";
    }

}
