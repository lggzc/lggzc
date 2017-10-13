/** 
 * File Name：CityController.java 
 * 
 * Copyright Defonds Corporation 2015  
 * All Rights Reserved 
 * 
 */   
  
package com.defonds.bdp.city.controller;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;  
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;  
  
import java.util.Map;

import org.apache.commons.logging.Log;  
import org.apache.commons.logging.LogFactory;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.ResponseBody;  
import org.springframework.web.servlet.ModelAndView;  
  






import com.danga.MemCached.MemCachedClient;
import com.defonds.bdp.city.bean.City;  
import com.defonds.bdp.city.service.CityService;  
  
/** 
 *  
 * Project Name：bdp 
 * Type Name：CityController 
 * Type Description： 
 * Author：Defonds 
 * Create Date：2015-08-27 
 * @version  
 *  
 */  
@Controller  
@RequestMapping("/city")  
public class CityController  {  
    private final Log logger = LogFactory.getLog(this.getClass());  
      
    @Autowired  
    private CityService cityService;  
      
    @RequestMapping("/welcome")  
    public String helloWorld() {  
   
        String message = "<br><div style='text-align:center;'>"  
                + "<h3>********** Hello Worwerld, Spring MVC Tutorial</h3>This message is coming from CityController.java **********</div><br><br>";  
//        return new ModelAndView("index", "message", message);  
        return "index";  
    }  
      
    @RequestMapping("/province/cities")  
    @ResponseBody  
    public Object provinceCities() {  
          
        List<City> list = new ArrayList<City>();  
        City city1 = new City();  
        city1.setId("126");  
        city1.setCity("济南市");  
        city1.setCityCode("4510");  
        city1.setCityJb("省级");  
        city1.setCityName("济南市");  
        city1.setProvince("山东省");  
          
        list.add(city1);  
          
        City city2 = new City();  
        city2.setId("127");  
        city2.setCity("济南市");  
        city2.setCityCode("4510");  
        city2.setCityJb("县级");  
        city2.setCityName("商河县");  
        city2.setProvince("山东省");  
          
        list.add(city2);  
          
        return list;  
    }  
      
    @RequestMapping("/create")  
    @ResponseBody  
    public Integer create() {  
        try {  
            this.cityService.insertCity();  
            return 1;  
        } catch (Exception e) {  
            logger.error(e);  
        }  
        return 0;  
    }  
    
    @RequestMapping("/province")  
    @ResponseBody  
    public List<City> provinceCities2() {  
        try {  
            return this.cityService.provinceCities("hebei");   
        } catch (Exception e) {  
            logger.error(e);  
        }  
        return null;  
          
    }  
    
    @RequestMapping("/searchCity")  
    @ResponseBody  
    public City searchCity() {  
        try {  
            return this.cityService.searchCity("hebei");
        } catch (Exception e) {  
            logger.error(e);  
        }  
        return null;  
          
    }  
    
    @RequestMapping("/search")  
    @ResponseBody 
    public void search() {  
    	this.cityService.search();
    }  
    
}  