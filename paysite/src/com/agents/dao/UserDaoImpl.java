package com.agents.dao;

import java.util.List;
import com.agents.pojo.Client;

import com.agents.bean.BaseHibernateDAO;

 public class UserDaoImpl extends BaseHibernateDAO{
	public List LoginImpl(String username, String password) {
		
		String hql="from com.agents.pojo.Client m where m.fid=? and m.fpassword=? and m.fstate='0'";
		List list =getSession().createQuery(hql).setString(0, username).setString(1, password).list();
		getSession().close();
		return list;     
	}   
public List LoginCheckStaff(String username, String password) {
		
		String hql="from com.agents.pojo.Staff s where s.fId=? and s.fPassword=?";
		List list =getSession().createQuery(hql).setString(0, username).setString(1, password).list();  
		getSession().close();
		return list;          
	} 
public List LoginCheckAgent(String username, String password) {   
	
	String hql="from com.agents.pojo.CoAgent ag  where ag.fid=? and ag.fpassword=?";
	List list =getSession().createQuery(hql).setString(0, username).setString(1, password).list();
	getSession().close();
	return list;       
}   

public Client getAgentID(String username) {
	
	String hql="from com.agents.pojo.Client m where m.fid=? and m.fstate='0'";
	Client c = (Client)getSession().createQuery(hql).setString(0, username).uniqueResult();
	getSession().close();
	return c;     
}
}        
