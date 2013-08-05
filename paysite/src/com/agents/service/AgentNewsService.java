package com.agents.service;

import java.util.List;


import com.agents.dao.AgentNewsDAO;
import com.agents.pojo.AgentNews;

public class AgentNewsService {
	private AgentNewsDAO anDao = new AgentNewsDAO();

	public List<com.agents.pojo.AgentNews> findByAgentID(String agentID){
		return anDao.findByAgentID(agentID);  
	}

	public com.agents.pojo.AgentNews findByID(String agentNewsID,String username) {
		int i;
		try{
			i = Integer.parseInt(agentNewsID);
		}catch(Exception e){  
			return null;
		}
		if(username == null || "".equals(username)){
			return null;
		}

		return anDao.findByIdAndAgentID(i,username);
	}
}
