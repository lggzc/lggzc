/** 
 * File Name：CityMapper.java 
 * 
 * Copyright Defonds Corporation 2015  
 * All Rights Reserved 
 * 
 */   
package com.defonds.bdp.city.mapper;    
import java.util.List;

import com.defonds.bdp.city.bean.City;  
  
/** 
 *  
 * Project Name：bdp 
 * Type Name：CityMapper 
 * Type Description： 
 * Author：Defonds 
 * Create Date：2015-08-31 
 * @version  
 *  
 */  
public interface CityMapper {  
    public void insertCity(City city);  
    public List provinceCities(String province);  
    public City searchCity(String code);  
}  