package com.agents.service;
import com.agents.dao.ClientInfoDAO;

public class ClientInfoService {


	public String[] findAgent(String angentID){
		return new ClientInfoDAO().findAgent(angentID);
	}

}
