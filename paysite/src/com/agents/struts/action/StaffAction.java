package com.agents.struts.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

//import com.agents.pojo.CoAgent;
import com.agents.service.StaffService;
import com.agents.struts.actionform.StaffActionForm;

public class StaffAction extends DispatchAction {
	
	private StaffService service=new StaffService();
	
//	public ActionForward findDataList(ActionMapping mapping, ActionForm form,
//				HttpServletRequest request, HttpServletResponse response) throws Exception {
//		HttpSession session = request.getSession();
//		//分页设置
//		StaffActionForm Form=new StaffActionForm();
//		int page = 1;  
//        try{
//			if(Form.getNopage() != null){ 
//				page = Form.getNopage();   
//			}
//	  	  }catch(Exception e){  
//	  	  }  
//		String clientID = (String)session.getAttribute("username");
//		if(clientID == null){
//			return mapping.findForward("staffList");      
//		}
//		String userType = (String)session.getAttribute("usertype");
//		//如果是员工登录，取上级ID
//		if (userType.equals("staff")) {  
//			clientID=(String)session.getAttribute("higherId"); 
//		}  
////		 CoAgent agent = (CoAgent)session.getAttribute("currentAgent"); //代理商id
////		  String agentId="";
////		  try{
////			  agentId=agent.getFid();
////		  }catch(Exception  e){
////			  System.out.println(e);  
////		  }
//	  	List <String[]> list=service.findDataList(page,clientID);
//	  	int noCount = service.noCount;  
//		int countPage = 0;
//		if(noCount % 15 == 0){
//			countPage = noCount / 15;
//		}else{  
//			countPage = noCount / 15 + 1;
//		}
//		if(page < 1) page = 1;
//		if(page > countPage) page = countPage;
//
//		request.setAttribute("noCount", noCount);
//		request.setAttribute("noPage", page);  
//		request.setAttribute("countPage", countPage);
//		request.setAttribute("staffs", list) ;  
////		request.setAttribute("pageMap", pageMap);
//		return mapping.findForward("staffList");
//	}
	public ActionForward findAddStaff(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String clientID = (String)session.getAttribute("username");
		
		if(clientID == null ){
			return mapping.findForward("addStaff");    
		}
		
		String sql="select fid,fname from CoPopedom where fState='0'";
		List <String[]> popedomList=service.popedomList(sql);
		 request.setAttribute("list", popedomList) ; 
		 return mapping.findForward("addStaff");  
	}
	public ActionForward addStaff(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		String clientID = (String)session.getAttribute("username");
		String userType=(String)session.getAttribute("usertype");
		if(clientID == null || "".equals(clientID)){
			request.setAttribute("messages", " 请重新登陆 ");
			return mapping.findForward("loginout");      
		}
		//如果是员工登录，取上级ID
		if (userType.equals("staff")) {  
			clientID=(String)session.getAttribute("higherId"); 
		}  
		PrintWriter out=null;  
        out = response.getWriter(); 
		String fid=request.getParameter("fid");
		//判断用户账号是否存在
		int fidExist=service.checkFidExist(fid);
		if (fidExist!=0) {  
			out.write("fidExist");
			return null;  
		}
		
		String fpassword=request.getParameter("fpassword");
		String fname=request.getParameter("fname");
		String fpopedom=request.getParameter("fpopedomArr"); 
		String fstate=request.getParameter("fstate");
		String ftype="1";
		if (userType.equals("client")) {
			ftype="0";
		}   
		String sql="insert into CoStaff (fID,fHigherID,fType,fName,fPassword,fPopedom,fState) values ('"+fid+"','"+clientID+"','"+ftype+"','"+fname+"','"+fpassword+"','"+fpopedom+"','"+fstate+"')";
		String message="false";
		boolean add=service.addStaff(sql);
		if (add) {
			message="true";
		}
		 
            out.println(message); 
            
		 return null;    
	}
	
	//删除员工
	public ActionForward deleteStaff(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		String clientID = (String)session.getAttribute("username");
		//String userType=(String)session.getAttribute("usertype");
		if(clientID == null || "".equals(clientID)){
			request.setAttribute("messages", " 请重新登陆 ");
			return mapping.findForward("loginout");      
		}
		String fids=request.getParameter("fid");
		String sql="delete costaff where fid in ("+fids+")";
		System.out.println(sql);
		StaffService service=new StaffService();  
		String message="false";
		boolean add=service.deleteStaff(sql);
		if (add) {   
			message="true";
		}
		PrintWriter out=null;  
        out = response.getWriter();  
            out.println(message); 
            
		 return null;    
	}
	public ActionForward modifyStaff(ActionMapping mapping, ActionForm form,
		     HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		String clientID = (String)session.getAttribute("username");
		String userType=(String)session.getAttribute("usertype");
		if(clientID == null ){
			return mapping.findForward("modifyStaff");        
		}
		String fid=request.getParameter("fid");
		//得到权限列表
		String sql="select fid,fname from CoPopedom where fState='0'";
		List <String[]> popedomList=service.popedomList(sql);
		String sql2="select fID,fHigherID,fType,fName,fPassword,fPopedom,fState  from costaff where fid = '"+fid+"'";
		StaffService service=new StaffService();  
		String[] staff=service.getModifyStaffById(sql2);
		request.setAttribute("list", popedomList) ;   
		request.setAttribute("staff", staff);    
        return mapping.findForward("modifyStaff"); 
	}
	public ActionForward updateStaff(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		String clientID = (String)session.getAttribute("username");
		String userType=(String)session.getAttribute("usertype");
		if(clientID == null || "".equals(clientID)){
			request.setAttribute("messages", " 请重新登陆 ");
			return mapping.findForward("loginout");      
		}
		String fid=request.getParameter("fid");
		String fpassword=request.getParameter("fpassword");
		String fname=request.getParameter("fname");
		String fpopedom=request.getParameter("fpopedomArr"); 
		String fstate=request.getParameter("fstate");
		
		String sql="update CoStaff set fName='"+fname+"',fPassword='"+fpassword+"',fPopedom='"+fpopedom+"',fState='"+fstate+"' where fid='"+fid+"'";
		String message="false";
		boolean add=service.addStaff(sql);
		if (add) {
			message="true";
		}
		PrintWriter out=null;  
        out = response.getWriter();  
            out.println(message); 
            
		 return null;    
	}
}
