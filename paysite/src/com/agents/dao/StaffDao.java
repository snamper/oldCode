package com.agents.dao;

import java.util.List;

import com.agents.util.BaseDao;

public class StaffDao extends BaseDao {
	public List<String[]> findDataList(int page,String agentId){
		String sql="select fID,fName,fType,fState from CoStaff where fHigherID='"+agentId+"'";
		List<String[]> list = super.findList(sql, page) ;
		noCount = super.noCount ;             
		return list; 
	}   
	
	public List<String[]> popedomList(String sql){
		return super.findListTop500(sql, 1);       
	} 
	public boolean addStaff(String sql){
		return super.updateData(sql);           
	}
	public boolean deleteStaff(String sql){
		return super.updateData(sql);           
	}
	public String [] getModifyStaffById(String sql){
		return super.findFirstData(sql);             
	}
	public int checkFidExist(String fid){
		List<String[]> list = super.findList("select * from costaff where fid='"+fid+"'", 1) ;
		int size=super.noCount;
		return size;             
	}
}
