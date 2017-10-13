package com.defonds.bdp;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.defonds.bdp.city.bean.City;
import com.defonds.bdp.city.controller.CityController;

public class MainCache {
public static void main(String[] args) {
	ApplicationContext context = new ClassPathXmlApplicationContext("bdp-applicationContext.xml");
	 
	CityController obj = (CityController) context.getBean("CityController");
	obj.create();
}
}
