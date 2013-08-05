package com.agents.service;

import java.util.List;

import com.agents.dao.GeneralDao;
import com.agents.dao.StaffDao;

public class StaffService {
	public int noCount = 0;
	
	public List<String[]> findDataList(int page,String agentId){
		 StaffDao StaffDao = new StaffDao() ;
		List<String[]> list = StaffDao.findDataList(page,agentId) ;  
		noCount = StaffDao.noCount ;
		return list;    
	}
	public List<String []> popedomList(String sql){
		StaffDao dao = new StaffDao() ;
		return dao.popedomList(sql) ;
	}
	public boolean  addStaff(String sql){
		StaffDao dao = new StaffDao() ;
		return dao.addStaff(sql) ;
	}
	public boolean  deleteStaff(String sql){
		StaffDao dao = new StaffDao() ;
		return dao.deleteStaff(sql) ;
	}
	public String[]  getModifyStaffById(String sql){
		StaffDao dao = new StaffDao() ;
		return dao.getModifyStaffById(sql) ;
	}
	
	public int  checkFidExist(String fid){
		StaffDao dao = new StaffDao() ;
		return dao.checkFidExist(fid) ;
	}

}
