package com.agents.bean;

/*
 * 验证码管�?
 */
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.agents.util.fw;

/**
 * Servlet implementation class for Servlet: CheckCode
 *
 * @web.servlet
 *   name="CheckCode"
 *   display-name="CheckCode"
 *
 * @web.servlet-mapping
 *   url-pattern="/CheckCode"
 *
 */
 public class CheckCode extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	   static final long serialVersionUID = 1L;
	   private static final String CONTENT_TYPE = "text/html; charset=GBK";

    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public CheckCode() {
		super();
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			fw.getCheckCode(request, response,72, 22, 20, 16, "1234567890");
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}