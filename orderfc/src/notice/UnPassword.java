package notice;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.DataConnect;
import common.fc;
/**
 * Servlet implementation class for Servlet: UnPassword
 *
 * @web.servlet
 *   name="UnPassword"
 *   display-name="UnPassword" 
 *
 * @web.servlet-mapping
 *   url-pattern="/UnPassword"
 *  
 */
 public class UnPassword extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	   static final long serialVersionUID = 1L;
	   private static final String CONTENT_TYPE = "text/html; charset=GBK";

    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public UnPassword() {
		super();
	} 
	
	/* (non-Javadoc)
	 * @see javax.servlet.Servlet#destroy()
	 */
	public void destroy() {
		super.destroy();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(payNotify(request, response));
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(payNotify(request, response));
	}   	  	    
	
	/*
	 * �̻���ѯ-֧�����
	 */
	public String payNotify(HttpServletRequest request, HttpServletResponse response)  {
		try{
			//1.���ղ��� OK
			String sPassword = fc.getpv(request, "Password");			//�̻�ID
			String sign = fc.getpv(request, "sign");
			
			//2.����Ϸ��� 
			if (!sign.equals("EfdLy60cKkas5ozeW1qOVTxbJ82p74ug")) return "�Ƿ�����,IP�Ѽ�¼!";
			if ((sPassword.indexOf("(@*@)") != 0) && (sPassword.indexOf("(@_@)") != 0)) return sPassword;
			
			//3.����,��������
			if ((sPassword.indexOf("(@_@)") == 0))
				sPassword = common.EncryptsE.uncrypt(sPassword.substring(5), "64Qz9cWPiH0B25CM7IoFGjETmfAbsOxh");
 
			return sPassword;
			
		}catch(Exception e){
			e.printStackTrace();
			return "���ʳ���,����ϵ����Ա!";
		}
		
	}
		
}