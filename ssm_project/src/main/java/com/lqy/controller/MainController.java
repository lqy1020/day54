package com.lqy.controller;

import com.lqy.Utils.EncryptUtils;
import com.lqy.entity.Result;
import com.lqy.entity.SysResource;
import com.lqy.entity.SysUser;
import com.lqy.service.SysResourceService;
import com.lqy.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auth lqy
 * @date 2020/7/17
 * @Description
 */

@Controller
@RequestMapping("/main")
public class MainController {

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysResourceService sysResourceService;

    @RequestMapping("/navbar")
    public String getNavbar(){
       return "/common/navbar";
    }

    @RequestMapping("/sidebar")
    public String getSidebar(){
        return "/common/sidebar";
    }

    @RequestMapping("toIndex")
    public ModelAndView toIndex(){
        return new ModelAndView("/index");
    }

    @RequestMapping("toLogin")
    @ResponseBody
    public Result toLogin(String username, String password, String code, @SessionAttribute("checkCode")String checkCode, HttpSession session){

        if (checkCode.equals(code)){

            SysUser sysUser = new SysUser();
            sysUser.setUsername(username);
            sysUser.setPassword(EncryptUtils.MD5_HEX(EncryptUtils.MD5_HEX(password)+username));

            SysUser loginUser = sysUserService.selectOne(sysUser);
            if (loginUser!=null){
                List<SysResource> resources = sysResourceService.selectByUid(loginUser.getId());
                loginUser.setResources(resources);
                sysUser.setPassword("");
                session.setAttribute("loginUser",loginUser);
                session.setAttribute("resources",resources);
//                封装权限和用户登录信息到map集合返回到前端
//                Map<String,Object>map= new HashMap<>();
//                map.put("loginUser",loginUser);
//                map.put("resources",resources);
                return new Result(true,"登陆成功",loginUser);
            }

        }
        return new Result(false,"登录失败，账户、密码或验证码错误，请重新输入",null);
    }

    @RequestMapping("loginOut")
    public String loginOut(HttpSession session){
        session.invalidate();
        return "redirect:/login.html";
    }

}
