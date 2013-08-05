package com.agents.dao;

import java.util.List;
import com.agents.util.BaseDao;
import com.agents.util.fc;
public class ClientDao extends BaseDao{
	
	public List checkClientName(String fid) {
		String sql = "select * from CoClient where fid='"+fid+"'";
		 List<String[]> list = super.findList(sql, 1) ;   
		return list;
	}
	public boolean clientReg(String fID,String fAgentID,String fName,String pwd,String Femail,String fphone,String fqq){
		fc fc=new fc();
		String key=fc.getRandom();
		String sql="insert into CoClient (fID,fAgentID,fpassword,fName,fstate,Femail,FcreateTime,fphone,fqq,fkey)values " +
				"('"+fID+"','"+fAgentID+"','"+pwd+"','"+fName+"',1,'"+Femail+"',getDate(),'"+fphone+"','"+fqq+"','"+key+"')"; 
		boolean add=super.updateData(sql);    
		return add;     
	}   
	public String[] findCurrentClient(String clientID) {

		String sql = "select fID,fAgentID,fPassword,fName,fState,fCreateTime,fFaceID,fKey,fWebName,fWebURL,fEmail,fQQ,"+
        "fPhone,fBankID,fBankSeat,fBankName,fBankNum,fQuestion,fAnswer  FROM CoClient WHERE  (fName = '"+clientID+"')";
		return super.findFirstData(sql);
	}

	public boolean updateClient(String clientID, String fpassword,
			String femail, String ftelphone, String fqq, String fkey) {
		String sql = "update coclient set fpassword='"+fpassword+"',femail='"+femail+"',fphone='"+ftelphone+"',fqq='"+fqq+"',fkey='"+fkey+"' where fname='"+clientID+"'";
		return super.updateData(sql);   
	}
	public boolean changeUserState(String fname) {
		String sql = "update coclient set fstate=0 where fname='"+fname+"'";
		System.out.println(sql);   
		return super.updateData(sql);      
	}

	public boolean checkLogin(String username, String password) {
		String sql = "SELECT fName  FROM   Client WHERE  (fName = '"+username+"') and CONVERT(varchar(255), fPassword)='"+password+"'";
		return super.findFirstData(sql) == null ? false : true;
	}
	//check 
	public boolean checkPwd(String userType,String username, String password) {
		String sql="";
		if (userType.equals("agent")) {
			sql ="SELECT * from  CoAgent WHERE   fPassword='"+password+"' and fid = '"+username+"' ";
		}else if(userType.equals("client")){
			sql ="SELECT * from  CoClient WHERE   fPassword='"+password+"' and fid = '"+username+"' ";
		}else if(userType.equals("staff")){  
			sql ="SELECT * from  CoStaff WHERE   fPassword='"+password+"' and fid = '"+username+"' ";
		}   
		return super.findFirstData(sql) == null ? false : true;  
	}
	public boolean updatePwd(String userType,String username, String password) {
		String sql="";
		if (userType.equals("agent")) {
			sql ="update   CoAgent set   fPassword='"+password+"' where fid = '"+username+"' ";
		}else if(userType.equals("client")){
			sql ="update   CoClient set   fPassword='"+password+"' where fid = '"+username+"' ";
		}else if(userType.equals("staff")){
			sql ="update   CoStaff set   fPassword='"+password+"' where fid = '"+username+"' ";
		}
		System.out.println(sql+"*********************");   
		return super.updateData(sql);    
	}
	public String[] findUserInfo(String sql) {
		return super.findFirstData(sql);
	}
	public boolean updateUserInfo(String sql) {
		return super.updateData(sql);
	}//绑定IP
	public boolean bindingIp(String sql) {
		return super.updateData(sql);
	}
	
}
