package com.lqy.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;
import com.lqy.Utils.MapWrapperFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import tk.mybatis.spring.annotation.MapperScan;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @auth lqy
 * @date 2020/7/16
 * @Description
 */

@Configuration
//@MapperScan("com.lqy.mapper")
@MapperScan("com.lqy.mapper")
@Import({SpringTransaction.class,SpringRedisConfig.class,SpringCacheConfig.class})
public class SpringMybatis {

    @Bean
    public DruidDataSource getDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        InputStream is = SpringMybatis.class.getClassLoader().getResourceAsStream("db.properties");
        Properties properties = new Properties();
        try {
            properties.load(is);
            dataSource.configFromPropety(properties);
            return dataSource;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean
    public SqlSessionFactoryBean getFactoryBean(DruidDataSource dataSource){
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);

        tk.mybatis.mapper.session.Configuration configuration = new tk.mybatis.mapper.session.Configuration();

        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCallSettersOnNulls(true);
        configuration.setObjectWrapperFactory(new MapWrapperFactory());

        factoryBean.setConfiguration(configuration);

        PageInterceptor pageInterceptor = new PageInterceptor();
        pageInterceptor.setProperties(new Properties());
        factoryBean.setPlugins(pageInterceptor);
        return factoryBean;
    }




}
