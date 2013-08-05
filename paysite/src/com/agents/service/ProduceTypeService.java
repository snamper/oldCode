package com.agents.service;

import java.util.List;

import com.agents.dao.ProduceTypeDao;
import com.agents.pojo.CaAgentProduct;

public class ProduceTypeService {
	
	public List<String[]> findByAgentId(String id){  
		ProduceTypeDao dao = new ProduceTypeDao() ;
		List<String[]> list = dao.findByAgentsId(id) ;
		return list;
	}   
}
