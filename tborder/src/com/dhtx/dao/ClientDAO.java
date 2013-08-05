package com.dhtx.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dhtx.util.DataConnect;
import com.dhtx.util.fc;

/**
 * 类名称：ClientDAO   
 * 类描述：
 * 创建人: renfy   
 * 创建时间：2011-12-13 下午05:31:55   
 * @version 1.0
 *  
 */
public class ClientDAO {

	/**
	 * 方法名称: checkLogin 
	 * 方法描述: 
	 * 创建人: renfy
	 * 创建时间: 2011-12-13 下午05:40:56
	 * @param userName
	 * @param passWord
	 * @return
	 * @version 1.0
	 * 
	 */ 
	public Map<String, String> checkLogin(String userName, String passWord) {
		
		DataConnect dc = new DataConnect("orderfill", false);
		String sql = "select top 1 fFaceID,fKey from CoClient WITH (NOLOCK) where fID ='"+userName+"' AND fPassword='"+passWord+"' AND fState='0'";
		ResultSet rs = dc.query(sql);
		
		Map<String,String> result = new HashMap<String, String>();
		
		try {
			if(rs != null && rs.next()){
				String fFaceID = fc.getrv(rs, "fFaceID", "");
				String fKey = fc.getrv(rs, "fKey", "");
				if(!fFaceID.equals("")){
					result.put("fFaceID", fFaceID);
					result.put("fKey", fKey);
				}
			}
			dc.CloseResultSet(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dc.CloseConnect();
		
		return result;
	}

	/**
	 * 方法名称: getClientProduct 
	 * 方法描述: 
	 * 创建人: renfy
	 * 创建时间: 2011-12-13 下午06:09:13
	 * @param userName
	 * @return
	 * @version 1.0
	 * 
	 */ 
	public List<String[]> getClientProduct(String userName) {
//		String[] p1 = {"GG0101","全国通用移动话费100元","1"};
//		String[] p2 = {"gg1201","电信测试","10"};
//		result.add(p1);
//		result.add(p2);
		
		DataConnect dc = new DataConnect("orderfill", false);
		String sql = "select fProductID,fProductName,fProductPrice from AcClientProduct WITH (NOLOCK) where fClientID ='"+userName+"' AND fState='0'";
		ResultSet rs = dc.query(sql);
		
		List<String[]> result = new ArrayList<String[]>();
		
		try {
			while(rs != null && rs.next()){
				String fProductID = fc.getrv(rs, "fProductID", "");
				String fProductName = fc.getrv(rs, "fProductName", "");
				String fProductPrice = fc.getrv(rs, "fProductPrice", "");
				String[] pro = {fProductID, fProductName, fProductPrice};
				result.add(pro);
			}
			dc.CloseResultSet(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dc.CloseConnect();
		
		return result;
	}

	/**
	 * 方法名称: getClientMoney 
	 * 方法描述: 
	 * 创建人: renfy
	 * 创建时间: 2011-12-14 下午04:58:30
	 * @param userName
	 * @return
	 * @version 1.0
	 * 
	 */ 
	public String getClientMoney(String userName) {
		DataConnect dc = new DataConnect("orderfill", false);
		String sql = "select fMoney from CoClientMoney WITH (NOLOCK) where fID='"+userName+"'";
		ResultSet rs = dc.query(sql);
		String fMoney = "0";
		try {
			if(rs != null && rs.next()){
				fMoney = fc.getrv(rs, "fMoney", "");
				if(fMoney.equals("")){
					fMoney = "0";
				}
			}
			dc.CloseResultSet(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dc.CloseConnect();
		
		return fMoney;
	}

}
