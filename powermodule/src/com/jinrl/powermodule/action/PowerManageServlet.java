package com.jinrl.powermodule.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jinrl.powermodule.pojo.Tposition;
import com.jinrl.powermodule.pojo.Tpowermanage;
import com.jinrl.powermodule.pojo.Tuser;
import com.jinrl.powermodule.service.CheckPower;

public class PowerManageServlet extends HttpServlet {

	CheckPower cp;

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
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String busiInfoid = request.getParameter("busiInfoid");
		String userid = request.getParameter("currentUserid");
		Tuser user = cp.getUserById(userid);
		List<Tposition> listposition = cp.getPositionsByuser2(user);
		StringBuffer fpower=new StringBuffer();
			List<Tpowermanage> listpm = cp.findFieldpower(busiInfoid,listposition);
			for(Tpowermanage pm :listpm){
				fpower.append("|"+pm.getFdataid()+"="+pm.getFpower());
			}
		out.print(fpower);
		out.flush();
		out.close();
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
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		cp = (CheckPower) context.getBean("checkpowerService");
	}

}
