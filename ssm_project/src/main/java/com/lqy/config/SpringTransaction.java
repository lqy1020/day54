package com.lqy.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.context.annotation.PropertySource;
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
@ComponentScan("com.lqy.service")
@EnableTransactionManagement
@PropertySource(value = "classpath:system.properties",encoding = "utf-8")
public class SpringTransaction {

//
//    @Bean
//    public DruidDataSource getD

    @Bean
    public DataSourceTransactionManager getTransactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

}
