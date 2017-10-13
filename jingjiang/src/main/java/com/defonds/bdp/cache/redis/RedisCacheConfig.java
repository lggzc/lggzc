package com.defonds.bdp.cache.redis;  
  
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
/** 
 *  
 * Project Name：bdp  
 * Type Name：RedisCacheConfig  
 * Type Description： 
 *  Author：Defonds 
 * Create Date：2015-09-21 
 *  
 * @version 
 *  
 */  
@Configuration  
@EnableCaching  
public class RedisCacheConfig extends CachingConfigurerSupport {  
  
    @Bean  
    public JedisConnectionFactory redisConnectionFactory() {  
        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();  
  
        // Defaults  
        redisConnectionFactory.setHostName("192.168.20.77");  
        redisConnectionFactory.setPort(6378); 
        //可配置連接屬性
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();  
//        jedisPoolConfig.setMaxTotal(maxActive);  
//        jedisPoolConfig.setMaxIdle(maxIdle);  
//        jedisPoolConfig.setMinIdle(minIdle);  
//        jedisPoolConfig.setMaxWaitMillis(maxWait);  
//        redisConnectionFactory.setPoolConfig(jedisPoolConfig);  
        return redisConnectionFactory;  
    }  
  
//    @Bean  
//    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {  
//    	RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
//    	redisTemplate.setConnectionFactory(cf);  
//    	return redisTemplate;  
//    }  
    @Bean  
    public RedisTemplate<String, String> redisTemplate(JedisConnectionFactory cf) {  
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
        redisTemplate.setConnectionFactory(cf);
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);  
        ObjectMapper om = new ObjectMapper();  
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);  
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);  
        jackson2JsonRedisSerializer.setObjectMapper(om); 
        
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);  
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);  
        redisTemplate.afterPropertiesSet();  
        return redisTemplate;  
    }   
  
    @Bean  
    public CacheManager cacheManager(RedisTemplate<String,String> redisTemplate) {  
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);  
        cacheManager.setDefaultExpiration(3000); 
        return cacheManager;  
    }  

}  
