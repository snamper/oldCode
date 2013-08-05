package com.agents.dao;

import java.util.List;

import com.agents.util.BaseDao;

public class GeneralDao extends BaseDao{
	public List<String[]> findStaffDataList(int page,String agentId){
		String sql="select fID,fName,fType,fState from CoStaff where fHigherID='"+agentId+"'";
		List<String[]> list = super.findList(sql, page) ;
		noCount = super.noCount ;             
		return list; 
	}   
	public List<String[]> findDataList(int page,String whereStr,String sql){
		List<String[]> list = super.findList(sql+whereStr, page) ;
		noCount = super.noCount ;        
		return list;         
	}    
	public boolean delete(String whereStr,String sql){
		System.out.println(sql+whereStr);   
		boolean del = super.updateData(sql+whereStr);
		return del;       
	}   
	public List<String[]> findSelectLists(String sql){
		List<String[]> list = super.findListTop500(sql, 1) ;  
		noCount = super.noCount ;          
		return list;         
	}    
	public String[] findModifyList(String sql){
	     String[] list=super.findFirstData(sql);
		return list;         
	}
	public boolean update(String sql){
		boolean del = super.updateData(sql);
		return del;       
	} 
	public List<String[]> getExportDataList(String sql){
		List<String[]> list = super.findListTopQW(sql,1) ;
		noCount = super.noCount ;
		return list;       
	} 
	public String[] findProductInfo(String sql){
		String[] list = super.findFirstData(sql);
		return list;       
	}
	public String getfAreaTypeByProductId(String id){
		String sql="select fAreaType from acproduct where fid='"+id+"'";
		String list = "";    
		try{
			String date[]=super.findFirstData(sql);
			if (date!=null) {
				list = date[0];     
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;       
	}
	public String[] getRelationList(String id){
		String sql="Select fSelectField,fRelationField,FtagName,fValue  from AcRelationSelect where fid='"+id+"'";
		String[] list = super.findFirstData(sql);
		return list;       
	}//得到二级关联菜单
	public String[] getRelationList(String relationField,String condition){
		String sql="Select fSelectField,FtagName,fValue  from AcRelationSelect where  fRelationField ='"+relationField+"' and fCondition='"+condition+"'";
		String[] list = super.findFirstData(sql);
		return list;         
	}
	public String[] getClientKeyById(String id){
		String sql="Select fid,fkey  from coclient where fid='"+id+"'";
		String[] list = super.findFirstData(sql);
		return list;       
	}
}
