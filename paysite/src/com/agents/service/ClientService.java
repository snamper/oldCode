package com.agents.service;

import java.util.List;
import com.agents.dao.ClientDao;
import com.agents.dao.ClientHibernateDao;
import com.agents.pojo.Client; 
public class ClientService {
	private ClientDao cd=new ClientDao();
	
	    public List<String[]> checkClientName(String fid){
		  return cd.checkClientName(fid);
	    }
	    public boolean clientReg(String fID,String fAgentID,String fName,String pwd,String Femail,String fphone,String fqq){
		  return cd.clientReg( fID, fAgentID, fName,pwd, Femail,fphone,fqq);
	    }
	    public String[] findCurrentClient(String clientID) {
			ClientDao cdao = new ClientDao();
			return cdao.findCurrentClient(clientID);
	    }
		public boolean updateClient(String clientID, String fpassword, String femail, String ftelphone, String fqq, String fkey) {
			ClientDao cdao = new ClientDao();
			return  cdao.updateClient(clientID, fpassword,femail, ftelphone, fqq, fkey);

		}
		public boolean changeUserState( String femail) {
			ClientDao cdao = new ClientDao();
			return  cdao.changeUserState(femail);
		}
		public boolean checkLogin(String username, String password) {
			ClientDao cdao = new ClientDao();
			return cdao.checkLogin(username,password);
		}
		private ClientHibernateDao clientDao = new ClientHibernateDao();
		//**********************
			public Client checkAgentId(String agentid,String username){
				return clientDao.findByAgentId(agentid,username);
			}

			public Client findByFname(String id){
				return clientDao.findByFname(id);
			}  
			
			public Client findByFnameAndState(String id){
				return clientDao.findByFnameAndState(id);
			}
			
		    //**********************
			public boolean checkPwd(String userType,String username, String password) {  
				return cd.checkPwd(userType,username,password);  
			}
			public boolean updatePwd(String userType,String username, String password) {  
				return cd.updatePwd(userType,username,password);  
			}
			//查看用户详细信息
			public String[] findUserInfo(String sql) {
				ClientDao cdao = new ClientDao();
				return cdao.findUserInfo(sql);
			}
			public boolean updateUserInfo(String sql) {
				ClientDao cdao = new ClientDao();
				return cdao.updateUserInfo(sql);  
			}//验证用户登录ip
			public boolean checkUserLoginIp(String diskIp,String userType,String fid,String fbindingstate,String fbindingpara) {
				//未绑定IP,记录用户当前IP
				String sql="";  
				ClientDao cdao = new ClientDao();  
				if (fbindingstate==null || fbindingstate.equals("1") || fbindingstate.equals("")) {
				   if (userType.equals("agent")) {
					   sql="update CoAgent set fbindingpara='"+diskIp+"' where fid='"+fid+"'";
				   }else if(userType.equals("client")){
					   sql="update CoClient set fbindingpara='"+diskIp+"' where fid='"+fid+"'";
				   }else{
					   sql="update CoStaff set fbindingpara='"+diskIp+"' where fid='"+fid+"'";
				   }
				   boolean bindingIp=cdao.bindingIp(sql);
				   System.out.println(bindingIp);  
				   return true;
				}else{
					//用户登录IP与数据库不匹配
					if (fbindingpara==null || diskIp==null) {
						return false;
					}
					if (!diskIp.equals(fbindingpara)) {
						System.out.println("用户登录IP与数据库不匹配");
						return false;
					}else{
						return true;
					}
				}
			} 
}
