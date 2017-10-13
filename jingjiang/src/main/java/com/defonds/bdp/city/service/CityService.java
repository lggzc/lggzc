/** 
 * File Name：CityService.java 
 * 
 * Copyright Defonds Corporation 2015  
 * All Rights Reserved 
 * 
 */   
  package com.defonds.bdp.city.service;  
import java.util.List;

import memcache.MemcachedCacheManager;

import org.apache.commons.logging.Log;  
import org.apache.commons.logging.LogFactory;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Propagation;  
import org.springframework.transaction.annotation.Transactional;  
  







import com.defonds.bdp.cache.redis.RedisCacheConfig;
import com.defonds.bdp.city.bean.City;  
import com.defonds.bdp.city.mapper.CityMapper;  
  
/** 
 *  
 * Project Name：bdp 
 * Type Name：CityService 
 * Type Description： 
 * Author：Defonds 
 * Create Date：2015-08-31 
 * @version  
 *  
 */  
@Service  
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)  
public class CityService {   
    private final Log logger = LogFactory.getLog(this.getClass());  
    @Autowired  
    MemcachedCacheManager cacheManager;
   
    @Autowired
    RedisTemplate<String,String> redisTemplate;
    @Autowired  
    private CityMapper cityMapper;  
      
    public void insertCity() {  
        City city = new City();  
        city.setCityCode("1100");  
        city.setCityJb("1");   
        city.setProvinceCode("1100");  
        city.setCityName("天津市");  
        city.setCity("天津市");  
        city.setProvince("天津市");  
        logger.debug("before insert the first city");  
        cityMapper.insertCity(city);  
        logger.debug("after insert the first city, and before insert the second city");  
        cityMapper.insertCity(new City()); // this will throw an exception  
        logger.debug("after insert the second city");  
    }
    
    @Cacheable("gzc")
    public List<City> provinceCities(String province) { 
//    	cacheManager.getAllKeys();
    	System.out.println("qwe");
        logger.debug("province=" + province);  
        return cityMapper.provinceCities(province);  
    }
    
    @Cacheable(value="zqg")
//    @Cacheable(value="searchCity",keyGenerator = "CacheKeyGenerator")
    public City searchCity(String city_code){  
//    	cacheManager.getAllKeys();
    	System.out.println("ewq");
        logger.debug("city_code=" + city_code);  
        return cityMapper.searchCity(city_code);     
    }

    public void search(){  
    	System.out.println(redisTemplate.keys("*"));
    	cacheManager.getAllKeys();
    }
}
