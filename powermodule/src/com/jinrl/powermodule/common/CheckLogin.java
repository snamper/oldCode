package com.jinrl.powermodule.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckLogin implements Filter{

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest quest = (HttpServletRequest) request;
		HttpServletResponse sponse = (HttpServletResponse) response;
		String uri = quest.getServletPath();
		String currentUserid = (String)quest.getSession().getAttribute("currentUserid");
		if(uri.equals("/login.jsp") || currentUserid != null){
			chain.doFilter(request, response);
		}else{
			sponse.sendRedirect("login.jsp");
		}

	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

}
