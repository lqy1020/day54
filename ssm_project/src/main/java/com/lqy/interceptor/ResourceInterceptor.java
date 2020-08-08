package com.lqy.interceptor;

import com.lqy.entity.SysResource;
import com.lqy.service.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @auth lqy
 * @date 2020/8/5
 * @Description
 */
/**
 * 授权拦截：
 * 1.获取系统所有需要授权资源(type为1的按钮级别、url为''和公共资源不授权，直接可以访问)
 * 2.获取用户请求 判断是否需要授权   不需要授权则直接放行
 * 3.需要授权的资源，再从用户已授权资源中查找是否有授权 有则放行，无则阻止访问  跳转到notauth.html
 */
public class ResourceInterceptor implements HandlerInterceptor {

    @Autowired
    SysResourceService sysResourceService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        boolean flag=false;
        //     /应用名/xxx请求   ->  xxx请求
        String uri = request.getRequestURI().replace(request.getContextPath()+"/","");
        List<SysResource> sysResources = sysResourceService.selectResource();
        for (SysResource sysResource : sysResources) {
            if (sysResource.getUrl().equals(uri)){
                flag=true;
            }
        }
        //获取用户登录的时候放入状态对象中的  已授权资源
        HttpSession session = request.getSession();
        List<SysResource> resources = (List<SysResource>) session.getAttribute("resources");

        if (flag){
            for (SysResource resource : resources) {
                if (resource.getUrl().equals(uri)){
                    return true;
                }
            }
            request.getRequestDispatcher("/notauth.html").forward(request,response);
            return false;
        }
        return true;
    }
}
