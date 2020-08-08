package com.lqy.config;

import com.lqy.interceptor.LoginInterceptor;
import com.lqy.interceptor.ResourceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @auth lqy
 * @date 2020/7/16
 * @Description
 */
@Configuration
@ComponentScan({"com.lqy.controller"})
@EnableWebMvc
public class SpringMvcConfig implements WebMvcConfigurer {

    @Autowired
    private ResourceInterceptor resourceInterceptor;

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public InternalResourceViewResolver getViewResolver(){
        return new InternalResourceViewResolver("/WEB-INF/html",".html");
    }


    /*
     * CommonsMultipartResolver:以commons的fileupload组件实现的文件上传解析器对象
     * 解析Request中的文件流封装成MultipartFile对象
     * */
    @Bean("multipartResolver")
    public CommonsMultipartResolver getMultipartResolver(){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        return multipartResolver;
    }
    //注册拦截器，配置拦截和放行规则
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LoginInterceptor loginInterceptor = new LoginInterceptor();
        InterceptorRegistration registration = registry.addInterceptor(loginInterceptor);
        registration.addPathPatterns("/**");
        registration.excludePathPatterns("/main/toLogin","/main/loginOut");

        InterceptorRegistration interceptorRegistration = registry.addInterceptor(resourceInterceptor);
        interceptorRegistration.addPathPatterns("/manager/**");

    }
}
