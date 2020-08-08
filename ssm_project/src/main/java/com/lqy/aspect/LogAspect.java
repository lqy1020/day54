package com.lqy.aspect;

import com.lqy.Utils.IPUtils;
import com.lqy.entity.SysLog;
import com.lqy.entity.SysUser;
import com.lqy.service.SysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @auth lqy
 * @date 2020/8/6
 * @Description
 */
/**
 * aop日志切面：
 * 1.正常日志切面记录
 * 2.异常日志切面记录
 *
 */

@Component
@Aspect
public class LogAspect {

    @Autowired
    SysLogService sysLogService;

    @Autowired
    HttpServletRequest request;

    @Pointcut("within(com.lqy.service.impl.*Impl)")
    public void pointcut(){}

    @AfterReturning(pointcut = "pointcut()",returning = "o")
    public Object afterReturning(JoinPoint joinPoint,Object o){
        String className = joinPoint.getTarget().getClass().getSimpleName();
        if (!className.equals("SysLogServiceImpl")){
            insertLog(joinPoint,null);
        }
        return o;
    }

    @AfterThrowing(pointcut = "pointcut()",throwing = "e")
    public void afterThrowing(JoinPoint joinPoint,Exception e){
        insertLog(joinPoint,e);
    }

    public void insertLog(JoinPoint joinPoint,Exception e){
        SysLog sysLog = new SysLog();
        sysLog.setType(e==null?"1":"2");
        if (request!=null){
            SysUser loginUser = (SysUser) request.getSession().getAttribute("loginUser");
            sysLog.setCreateBy(loginUser==null?"":loginUser.getName());
            sysLog.setCreateDate(new Date());
            sysLog.setRemoteAddr(IPUtils.getClientAddress(request));
            sysLog.setUserAgent(request.getHeader("User-Agent"));
            sysLog.setRequestUri(request.getRequestURI());
        }
        sysLog.setMethod(request.getMethod());
        Object[] args = joinPoint.getArgs();
        StringBuilder sb=new StringBuilder();
        if (args!=null&&args.length>0) {
            for (int i = 0; i < args.length; i++) {
                sb.append("[参数:")
                        .append(i + 1)
                        .append(",类型:")
                        .append(args[i].getClass().getSimpleName())
                        .append(",值:")
                        .append(args[i])
                        .append("]");
            }
            sysLog.setParams(sb.toString());
        }
        sysLog.setException(e==null?"":e.getMessage());
        sysLogService.insertSelective(sysLog);
    }

}
