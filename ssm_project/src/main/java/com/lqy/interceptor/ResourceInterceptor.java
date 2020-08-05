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
public class ResourceInterceptor implements HandlerInterceptor {

    @Autowired
    SysResourceService sysResourceService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        boolean flag=false;
        String uri = request.getRequestURI().replace(request.getContextPath()+"/","");
        List<SysResource> sysResources = sysResourceService.selectResource();
        for (SysResource sysResource : sysResources) {
            if (sysResource.getUrl().equals(uri)){
                flag=true;
            }
        }
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
