package com.agents.dao;

import java.util.List;

import com.agents.util.BaseDao;

public class ProduceTypeDao extends BaseDao{
	public List<String[]>  findByAgentsId(String id) {
			String sql = "select fID,fAgentID,fProductID,fRate,fClientRate,fDefState,fDefGiveID,fState from CaAgentProduct where fAgentID='"+id+"'" ;
			System.out.println("select fID,fAgentID,fProductID,fRate,fClientRate,fDefState,fDefGiveID,fState from CaAgentProduct where fAgentID='"+id+"'");
			List<String[]> list = super.findList(sql, 1) ;
			return list ;  
		}

}
