package com.agents.dao;

import java.util.List;

import com.agents.util.BaseDao;


public class ClientInfoDAO extends BaseDao{

	/*
	 */
	

	public String[] findAgent(String agentID){
		String sql = "select * from coagent where fid='"+agentID+"'";
		return super.findFirstData(sql);
	}

}
