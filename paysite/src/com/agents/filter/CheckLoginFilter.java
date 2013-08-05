package com.agents.filter;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class CheckLoginFilter implements Filter{
	public void destroy() {
		// TODO Auto-generated method stub

	}
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		HttpSession session = request.getSession();
		Object obj=session.getAttribute("username");  
		if (obj==null) {
			response.sendRedirect("/agentPage.do?method=findAgentByDomain");
		}
		
		chain.doFilter(request, response);
	}
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
}
