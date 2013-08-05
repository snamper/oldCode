package com.agents.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.agents.util.Config;


public class EncodeFilter implements javax.servlet.Filter{

	private String encodetype = null;

	public void destroy() {
		// TODO Auto-generated method stub

	}  

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		HttpSession session = request.getSession();
//		System.out.println(request.getServletPath());   
//		System.out.println(session.getAttribute("username"));  
		

		if (session.getAttribute("username")==null && request.getServletPath().equals("/admin/index.jsp")) { 
			response.sendRedirect(request.getContextPath()+"/agentPage.do?method=findAgentByDomain");  
		}else{   
		request.setCharacterEncoding(encodetype);
		response.setCharacterEncoding(encodetype);
		chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		encodetype = filterConfig.getInitParameter("encodetype");

	}

}
