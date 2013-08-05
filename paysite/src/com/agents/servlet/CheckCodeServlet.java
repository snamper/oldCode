package com.agents.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class CheckCodeServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=GBK";

    //Initialize global variables
    public void init() throws ServletException {
    }  

    //Process the HTTP Get request
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        String code=request.getParameter("code");//用户输入的随机码
     HttpSession session=request.getSession();//获得会话对象
     String random=(String)session.getAttribute("random");//系统产生的随机码（见RandomServlet类第101行）
      if(random.equals(code)){//检查系统产生的随机码与用户输入的随机码是否匹配
        request.setAttribute("message","随机码输入正确！");
        request.getRequestDispatcher("login.jsp").forward(request,response);
      }else{
        request.setAttribute("message","随机码输入错误！");
        request.getRequestDispatcher("login.jsp").forward(request,response);
      }
    }

    //Process the HTTP Post request
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        doGet(request, response);
    }

    //Clean up resources
    public void destroy() {
    }
}
