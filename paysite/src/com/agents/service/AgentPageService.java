package com.agents.service;
import java.util.List;
import com.agents.dao.AgentsDAO;
import com.agents.pojo.CoAgent;
public class AgentPageService {
	private AgentsDAO agentsDao = new AgentsDAO();
	public void setAgentsDao(AgentsDAO agentsDao) {
		this.agentsDao = agentsDao;
	}
	public CoAgent findBydomainName(String domainName){  
		try{
			List list = agentsDao.findByFdomainName(domainName);
			if(list.size()==0){
				return null;
			}else{
				return (CoAgent)list.get(0);
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
