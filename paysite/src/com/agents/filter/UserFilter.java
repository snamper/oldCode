package com.agents.filter;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class UserFilter implements Filter {
	public void init(FilterConfig filterConfig) throws ServletException {


	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		String path = session.getServletContext().getRealPath(req.getRequestURI());
		String paths = path.substring(path.lastIndexOf(File.separator)+1);
		if (paths.equals("agentNews.jsp") || paths.equals("search.jsp") || paths.equals("charges.jsp") || paths.equals("service.jsp") || paths.equals("regdit.jsp") || session.getAttribute("username") != null || (paths.equals("checkcodeservlet") || paths.equals("rs") || paths.equals("loginServlet") || paths.equals("*.gif") || paths.equals("*.jpg") || paths.equals("*.ico"))) {
			if(paths.equals("agentNews.jsp") || paths.equals("search.jsp") || paths.equals("charges.jsp") || paths.equals("service.jsp") || paths.equals("regdit.jsp")){
				if(session.getAttribute("currentAgent") != null){
					chain.doFilter(request, response);
				}else{
					request.getRequestDispatcher("/agentPage.do?method=findAgentByDomain").forward(request, response);
				}

			}else{
				chain.doFilter(request, response);
			}
		} else {

			request.getRequestDispatcher("/agentPage.do?method=findAgentByDomain").forward(request, response);
		}
	}
	public void destroy() {
	}
}
