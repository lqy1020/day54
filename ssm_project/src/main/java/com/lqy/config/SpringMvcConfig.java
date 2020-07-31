package com.lqy.config;

import com.lqy.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @auth lqy
 * @date 2020/7/16
 * @Description
 */
@Configuration
@ComponentScan("com.lqy.controller")
@EnableWebMvc
public class SpringMvcConfig implements WebMvcConfigurer {

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public InternalResourceViewResolver getViewResolver(){
        return new InternalResourceViewResolver("/WEB-INF/html",".html");
    }

    @Bean("multipartResolver")
    public CommonsMultipartResolver getMultipartResolver(){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        return multipartResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LoginInterceptor loginInterceptor = new LoginInterceptor();
        InterceptorRegistration registration = registry.addInterceptor(loginInterceptor);
        registration.addPathPatterns("/**");
        registration.excludePathPatterns("/main/toLogin","/main/loginOut");

    }
}
