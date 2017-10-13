package com.defonds.bdp.cache.redis;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.nio.charset.Charset;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;

@Configuration  
@EnableCaching 
class CacheKeyGenerator implements KeyGenerator {  
	  
    // custom cache key  
    public static final int NO_PARAM_KEY = 0;  
    public static final int NULL_PARAM_KEY = 53;  
      
    @Override  
    public Object generate(Object target, Method method, Object... params) {  
  
        StringBuilder key = new StringBuilder();  
        key.append(target.getClass().getSimpleName()).append(".").append(method.getName()).append(":");  
        if (params.length == 0) {  
            return key.append(NO_PARAM_KEY).toString();  
        }  
        for (Object param : params) {  
            if (param == null) {  
                key.append(NULL_PARAM_KEY);  
            } else if (ClassUtils.isPrimitiveArray(param.getClass())) {  
                int length = Array.getLength(param);  
                for (int i = 0; i < length; i++) {  
                    key.append(Array.get(param, i));  
                    key.append(',');  
                }  
            } else if (ClassUtils.isPrimitiveOrWrapper(param.getClass()) || param instanceof String) {  
                key.append(param);  
            } else {  
                key.append(param.hashCode());  
            }  
        }  
  
//        String finalKey = key.toString();  
//        long cacheKeyHash = Hashing.murmur3_128().hashString(finalKey, Charset.defaultCharset()).asLong();  
        return key.toString();  
    }
 
} 