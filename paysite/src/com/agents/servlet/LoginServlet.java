package com.agents.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import ty_Service.StaffsService;
//import ty_Service.UserService;

import com.agents.pojo.CoAgent;
import com.agents.pojo.Staff;
import com.agents.service.AgentPageService;
import com.agents.service.ClientService;
import com.agents.service.UserService;
import com.agents.util.CreateAgentObj;

public class LoginServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public LoginServlet() {
		super();
	}
	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 *
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 *
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			HttpSession session=request.getSession();
			request.setCharacterEncoding("GB2312");
			response.setCharacterEncoding("GB2312");
			String username=request.getParameter("userName");
			String password=request.getParameter("passWord");
			String checkcode=request.getParameter("checkCode");
			// 硬盘序列号
			String diskId=request.getParameter("disk");
			
			//userType 用户类型 "agent" 代理商，“client” 客户，“staff” 职员
			String userType="";
			//获得域名
			String domainName=request.getServerName();
			
			
			if (domainName!=null) {
			    	userType=domainName.substring(0,domainName.indexOf("."));
				domainName=domainName.substring(domainName.indexOf(".")+1, domainName.length());
			}  
			AgentPageService as = new AgentPageService();
//			CoAgent ag= as.findBydomainName(domainName); 
			CoAgent ag= null; 
			
			if(ag == null){
				ag = CreateAgentObj.createAgent();
			}
			
			if(ag == null){
				response.sendRedirect("agentPage.do?method=findAgentByDomain&sError=1");
				return;
			} 
			
			UserService service=new UserService();   
			com.agents.pojo.Client client = null; 
			CoAgent agent=null;
				
/*          if (checkcode.equals("checkCode")){  
            	session.setAttribute("currentAgent", ag);  
				session.setAttribute("username",username);
            	response.sendRedirect("main"); 
            	return;
			}  */  
			String fName="";
			String bindingstate="";
			String bindingpara="";
			ClientService clients=new ClientService();
			
			String random=(String)session.getAttribute("random");//得到session中的random验证码
				if(random==null || random.equals(checkcode)){//比对验证码
					if(username!=null&&password!=null){
						if (userType.equals(ag.getFdomainAgent())) {
//							agent=service.checkAgent(username, password);   
							
							if(agent == null){
								//oracle
								String agentName = service.getAgentName(username, password);
								if(agentName != null && !"".equals(agentName.trim())){
									ag.setFname(agentName);
									agent = ag;
								}
							}
							
							if (agent!=null) {
								userType="agent";
								fName=agent.getFname();
						        //验证IP绑定  
								bindingstate=agent.getFbindingstate();
								bindingpara=agent.getFbindingpara();
//								boolean binding=clients.checkUserLoginIp(diskId, userType, username, bindingstate, bindingpara);
//								if (!binding) {
//									response.sendRedirect("agentPage.do?method=findAgentByDomain&sError=4");//4 IP绑定验证
//								}else{
								session.setAttribute("currentAgent", ag); 
								session.setAttribute("usertype", userType); 
								session.setAttribute("username",username);
								session.setAttribute("fName", fName); 
								response.sendRedirect("admin/index.jsp"); 
//								}
								
							}else{
								response.sendRedirect("agentPage.do?method=findAgentByDomain&sError=1");
							}
						} else if(userType.equals(ag.getFdomainClient())){
						   client=service.userService(username, password);//先比对用户名和密码是否正确
						   if(client!=null && client.getFagentId().equals(ag.getFid())){
							    userType="client";
							    fName=client.getFname();
							    //验证IP绑定
								bindingstate=client.getFbindingstate();
								bindingpara=client.getFbindingpara();
								boolean binding=clients.checkUserLoginIp(diskId, userType, username, bindingstate, bindingpara);
								System.out.println(bindingpara+"=======客户登陆=========");  
								if (!binding ) {
									response.sendRedirect("agentPage.do?method=findAgentByDomain&sError=4");//4 IP绑定验证
								}else{
								session.setAttribute("currentAgent", ag);  
								session.setAttribute("username",username);
								session.setAttribute("fName",fName);
								session.setAttribute("usertype",userType);
								session.setAttribute("currentClient", client);//添加原因：加款页面添加加款方式选择
								response.sendRedirect("admin/index.jsp"); 
								}
							}else{
								Staff staff = service.checkStaff(username, password);
								if(staff!=null){
									com.agents.pojo.Client cli = service.getAgentID(staff.getfHigherId());
									if(cli != null && ag.getFid().equals(cli.getFagentId())){
										userType="staff";
										fName=staff.getfName();
										//所属上级ID
										String higherId=staff.getfHigherId();
										String fPopedom=staff.getfPopedom();
										//验证IP绑定
										bindingstate=staff.getFbindingstate();
										bindingpara=staff.getFbindingpara();
										boolean binding=clients.checkUserLoginIp(diskId, userType, username, bindingstate, bindingpara);
										if (!binding) {
											response.sendRedirect("agentPage.do?method=findAgentByDomain&sError=4");//4 IP绑定验证
										}else{
										session.setAttribute("currentAgent", ag);  
										session.setAttribute("username",username); 
										session.setAttribute("fName",fName);
										session.setAttribute("fPopedom",fPopedom);
										session.setAttribute("usertype",userType);
										session.setAttribute("higherId",higherId);
										response.sendRedirect("admin/index.jsp");   
										}
									}else{
										response.sendRedirect("agentPage.do?method=findAgentByDomain&sError=1");
									}
							    }else{
									response.sendRedirect("agentPage.do?method=findAgentByDomain&sError=1");
								}
							}  
						}
						else{
							 response.sendRedirect("agentPage.do?method=findAgentByDomain&sError=1");
						}
				    }else{
				    	response.sendRedirect("agentPage.do?method=findAgentByDomain&sError=1");
				    }
				}else{
					response.sendRedirect("agentPage.do?method=findAgentByDomain&sError=2");
				}
	}  
	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs  
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
