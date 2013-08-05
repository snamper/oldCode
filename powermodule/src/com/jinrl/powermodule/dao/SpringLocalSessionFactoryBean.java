package com.jinrl.powermodule.dao;

import java.io.FileInputStream;
import java.io.InputStream;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

import com.jinrl.powermodule.common.fc;


public class SpringLocalSessionFactoryBean extends LocalSessionFactoryBean {
	public Resource config = null;
	
	public SpringLocalSessionFactoryBean(){
		try {
			InputStream is = null;
	        try {
	        	String s = SpringLocalSessionFactoryBean.class.getResource("").toString();
	        	fc.message("类路径:"+s);
	        	s = s.substring(6);
	        	int n = s.indexOf("classes");
	            is = new FileInputStream(s.substring(0, n) + "classes/hibernate.test.cfg.xml");
	        } catch (Exception ee) {
	            ee.printStackTrace();
	        }
	        Resource resource = new InputStreamResource(is);
			this.setConfigLocation(resource);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void configLocation(){
		fc.message("-----------configLocation-----------");
	}
	
}
