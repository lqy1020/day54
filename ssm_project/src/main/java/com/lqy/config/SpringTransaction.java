package com.lqy.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import com.lqy.interceptor.ResourceInterceptor;
import com.lqy.web.DruidStatViewFilter;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.*;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
//import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @auth lqy
 * @date 2020/7/16
 * @Description
 */
@Configuration
@ComponentScan({"com.lqy.service","com.lqy.aspect"})
@EnableTransactionManagement
@EnableAspectJAutoProxy
@PropertySource(value = "classpath:system.properties",encoding = "utf-8")
public class SpringTransaction {

//
//    @Bean
//    public DruidDataSource getD

    @Bean
    public DataSourceTransactionManager getTransactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }


    @Bean
    public ResourceInterceptor getResourceInterceptor(){
        return new ResourceInterceptor();
    }


    @Bean(name = "druidStatInterceptor")
    public DruidStatInterceptor getDruidStatInterceptor(){
        DruidStatInterceptor druidStatInterceptor = new DruidStatInterceptor();
        return druidStatInterceptor;
    }

    @Bean//配置spring监控
    public BeanNameAutoProxyCreator getAutoProxyCreator(){
        BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
        beanNameAutoProxyCreator.setProxyTargetClass(true);
        beanNameAutoProxyCreator.setBeanNames(new String[]{"*Mapper","*ServiceImpl"});
        beanNameAutoProxyCreator.setInterceptorNames("druidStatInterceptor");
        return beanNameAutoProxyCreator;
    }



}
