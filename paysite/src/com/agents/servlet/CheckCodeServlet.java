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
        String code=request.getParameter("code");//�û�����������
     HttpSession session=request.getSession();//��ûỰ����
     String random=(String)session.getAttribute("random");//ϵͳ����������루��RandomServlet���101�У�
      if(random.equals(code)){//���ϵͳ��������������û������������Ƿ�ƥ��
        request.setAttribute("message","�����������ȷ��");
        request.getRequestDispatcher("login.jsp").forward(request,response);
      }else{
        request.setAttribute("message","������������");
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
