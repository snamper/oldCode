package com.agents.service;

import java.util.List;

import com.agents.dao.OrderDao;


public class OrderService {
	public int noCount = 0;
	public List<String[]> findOrderList(int page,String key,String type,String starttime,String endtime){
		OrderDao dao = new OrderDao() ;
		List<String[]> list = dao.findOrderList(page,key,type, starttime,endtime) ;
		noCount = dao.noCount ;
		return list;
	}
}
