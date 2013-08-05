package com.testweb.test1;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jinrl_exploit_common.DataConnect;

/**
 * 类名称：TestRS 类描述： 创建人: renfy 创建时间：2011-12-8 下午12:13:55
 * 
 * @version 1.0
 * 
 */
public class TestRS extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public TestRS() {
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
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		DataConnect sysdc = new DataConnect("sysdb", false);
		String sql = "select * from tBusiInfo";
		ResultSet rs = sysdc.query(sql);
		try {
			if (rs!=null) {
				ResultSetMetaData rsmd = rs.getMetaData();
				int ccount = rsmd.getColumnCount();
				for(int i = 1; i <= ccount; i++){
					String cname = rsmd.getColumnName(i);
					System.out.println(cname);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sysdc.CloseResultSet(rs);
			sysdc.CloseConnect();
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
