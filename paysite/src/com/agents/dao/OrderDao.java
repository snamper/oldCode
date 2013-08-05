package com.agents.dao;

import java.util.List;

import com.agents.util.BaseDao;

public class OrderDao extends BaseDao{
	public List<String[]> findOrderList(int page,String key,String type,String starttime,String endtime){
		String whereStr="";
		if (!type.equals("all")) {
			whereStr="and  "+type+"="+"'"+key+"'";  
		}
		if (!starttime.equals("")) {
			whereStr=whereStr+"and fCreateTime > '"+starttime+" 00:00:00.000' and fCreateTime < '"+endtime+" 00:00:00.000'";   
		}
		String sql = "select  CONVERT(VARCHAR(23),fCreateTime,120) fCreateTime,fCardNo,fOrderID,p.fName,fAccountID,fMoney from CaOrder o left join  AcProduct p on o.fProductID=p.fID where 1=1 " ;
		System.out.println(sql+whereStr);  
		List<String[]> list = super.findList(sql+whereStr, page) ;
		noCount = super.noCount ;        
		return list;       
	}    
}
