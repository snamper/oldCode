package com.agents.service;

import java.util.List;

import com.agents.dao.UserDaoImpl;
import com.agents.pojo.Client;
import com.agents.pojo.CoAgent;
import com.agents.pojo.Staff;
import com.agents.util.ConnOracle;

public class UserService {
	//客户登录验证
	public Client userService(String username,String password){
		UserDaoImpl dao=new UserDaoImpl();
		List list=dao.LoginImpl(username, password);
		if(list.size()>0){
			return (Client) list.get(0);
		}else{
			return null;
		}
	}
	//职员登录验证
	public Staff checkStaff(String username,String password){
		UserDaoImpl dao=new UserDaoImpl();
		List list=dao.LoginCheckStaff(username, password);
		if(list.size()>0){
			return (Staff) list.get(0);
		}else{
			return null;
		}
	}
	//职员登录验证
	public CoAgent checkAgent(String username,String password){
		UserDaoImpl dao=new UserDaoImpl();
		List list=dao.LoginCheckAgent(username, password);
		if(list.size()>0){
			return (CoAgent) list.get(0);  
		}else{
			return null;
		}
	}
	
	public String getAgentName(String username,String password){
		ConnOracle co = new ConnOracle();
		return co.checkLogin(username, password);
	}
	
	public Client getAgentID(String username) {
		return new UserDaoImpl().getAgentID(username);
	}
}
