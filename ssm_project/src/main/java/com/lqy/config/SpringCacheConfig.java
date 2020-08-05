package com.lqy.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * @auth lqy
 * @date 2020/7/28
 * @Description
 */
@Configuration
@EnableCaching
public class SpringCacheConfig {

    @Bean
    public CacheManager getCacheManager(RedisOperations redisOperations){
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisOperations);
        List<String> cacheNames = new ArrayList<>();
        cacheNames.add("officeCache");
        cacheNames.add("orderCache");
        cacheNames.add("resourceCache");
        redisCacheManager.setCacheNames(cacheNames);
        redisCacheManager.setDefaultExpiration(600);//设置缓存的默认生存时间
        return redisCacheManager;
    }

}
