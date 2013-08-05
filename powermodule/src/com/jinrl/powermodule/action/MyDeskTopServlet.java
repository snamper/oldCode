package com.jinrl.powermodule.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jinrl.powermodule.common.fc;
import com.jinrl.powermodule.dao.*;
import com.jinrl.powermodule.pojo.Tfunction;
import com.jinrl.powermodule.pojo.TmyDeskTop;

public class MyDeskTopServlet extends HttpServlet {

	private TmyDeskTopDAO myDeskTopDAO;
	private TfunctionDAO functionDAO;
	/**
	 * Constructor of the object.
	 */
	public MyDeskTopServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String currentUserid = (String)request.getSession().getAttribute("currentUserid");

		String ip = request.getRemoteAddr();

		  String ip_userid =ip+"_"+currentUserid;
		  int sport = request.getLocalPort();

		  String s = "http://localhost:"+sport+"/powermodule/checkIPUID?type=setipuserid&ip_userid="+ip_userid;
		  fc.SendDataViaPost(s,"","GB2312");

		  //

		List<TmyDeskTop> listdt = myDeskTopDAO.findMyDeskTops();
		for(TmyDeskTop dt : listdt){
			if(dt.getFfunctionid() == null || "".equals(dt.getFfunctionid())){
				String url = dt.getFurl();
				if(url != null && !"".equals(url)){
					if(url.indexOf("[user()]") != -1){
						url = fc.replace(url, "[user()]", currentUserid);
						dt.setFurl(url);
					}
				}
			}else{
				Tfunction function = functionDAO.findById(dt.getFfunctionid());
				String sign = fc.getMd5Str(currentUserid + dt.getFfunctionid() + "H7F65E49JED5OIF4U4DE664C66D6EET3");
				String newurl = "/commonscan/busiInfo.do?method=getBusiInfo&tbusiInfoID=" + function.getFbusiinfoid() + "&currentUserid=" + currentUserid + "&functionid=" + dt.getFfunctionid() + "&sign=" + sign;
				dt.setFurl(newurl);
			}
		}
		request.setAttribute("listdt", listdt);
		request.getRequestDispatcher("right.jsp").forward(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		myDeskTopDAO = (TmyDeskTopDAO) context.getBean("myDeskTopDAO");
		functionDAO = (TfunctionDAO) context.getBean("functionDAO");
	}

}
