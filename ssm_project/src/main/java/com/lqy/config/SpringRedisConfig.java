package com.lqy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @auth lqy
 * @date 2020/7/28
 * @Description
 */

@Configuration
@PropertySource(value = "classpath:redis.properties",encoding = "utf-8")
public class SpringRedisConfig {

    @Bean
    public RedisConnectionFactory getConnectionFactory(@Value("${redis.host}")String host, @Value("${redis.password}")String password,
                                                       @Value("${redis.port}")int port,@Value("${redis.minIdle}")int minIdel){
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setHostName(host);
        connectionFactory.setPassword(password);
        connectionFactory.setPort(port);

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMinIdle(minIdel);
        connectionFactory.setPoolConfig(poolConfig);
        return connectionFactory;
    }

    @Bean
    public RedisTemplate<Object,Object> getRedisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        GenericJackson2JsonRedisSerializer redisSerializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setDefaultSerializer(redisSerializer);
        return redisTemplate;
    }

}
