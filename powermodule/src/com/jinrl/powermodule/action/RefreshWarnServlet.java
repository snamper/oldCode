package com.jinrl.powermodule.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jinrl.powermodule.dao.TstatisticDAO;
import com.jinrl.powermodule.pojo.Tstatistic;

public class RefreshWarnServlet extends HttpServlet {
	TstatisticDAO stDao = null;
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
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 *
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/text;charset=gb2312");
		// 禁止缓存
		response.setHeader("Pragma", "No-Cache");
		response.setHeader("Cache-Control", "No-Cache");
		response.setDateHeader("Expires", 0);
		PrintWriter out = response.getWriter();

		// 此部分是现实预警信息的
		List<Tstatistic> stlist = stDao.finShowStatistic();

		//存放最后结果
		List<String[]> stresult = new ArrayList<String[]>();


		for(Tstatistic s : stlist){
			//String[0]='显示字',String[1]='显示的值',String[2]='是否显示成红字'
			String[] warns = new String[4];
			warns[0]=s.getFdescription();
			warns[1]=s.getFbeforeValue();
			warns[3]=s.getFurl();

			//
			String warnValue = s.getFwarnValue();
			String nowValue = s.getFbeforeValue();
			String warnType = s.getFwarnType();
			//对应比较方式，分开比较
			if("<".equals(warnType)){
				try{
					//放到try catch 里面，是为了当数据库中数据不正常时，发生异常，跳过此行记录，不影响后续的结果显示
					if(Float.valueOf(nowValue) < Float.valueOf(warnValue)){
						//标志成红色
						warns[2]="red";
						stresult.add(warns);
					}
					else{
						if(!"2".equals(s.getFisshow())){
							warns[2]="";
							stresult.add(warns);
						}
					}
				}catch(Exception e){
					//System.out.println("表中数据错误(<)");
				}
			}else
			if(">".equals(warnType)){
				try{
					if(Float.valueOf(nowValue) > Float.valueOf(warnValue)){
						warns[2]="red";
						stresult.add(warns);
					}else{
						if(!"2".equals(s.getFisshow())){
							warns[2]="";
							stresult.add(warns);
						}
					}
				}catch(Exception e){
					//System.out.println("表中数据错误(>)");
				}
			}else
			if("=".equals(warnType)){
				try{
					if(Float.valueOf(nowValue) == Float.valueOf(warnValue)){
						warns[2]="red";
						stresult.add(warns);
					}else{
						if(!"2".equals(s.getFisshow())){
							warns[2]="";
							stresult.add(warns);
						}
					}
				}catch(Exception e){
					//System.out.println("表中数据错误(=)");
				}
			}else
			if("<>".equals(warnType)){
				try{
					if(Float.valueOf(nowValue) != Float.valueOf(warnValue)){
						warns[2]="red";
						stresult.add(warns);
					}else{
						if(!"2".equals(s.getFisshow())){
							warns[2]="";
							stresult.add(warns);
						}
					}
				}catch(Exception e){
					//System.out.println("表中数据错误(<>)");
				}
			}else
			if("@".equals(warnType)){
				try{
					if(nowValue.indexOf(warnValue) != -1){
						warns[2]="red";
						stresult.add(warns);
					}else{
						if(!"2".equals(s.getFisshow())){
							warns[2]="";
							stresult.add(warns);
						}
					}
				}catch(Exception e){
					//System.out.println("表中数据错误(@)");
				}
			}

		}


		//配接字符串，用于页面显示，调用此自定义标签
		StringBuffer sb = new StringBuffer();
		for(String[] s : stresult){
			sb.append("&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;");
			if(s[3] != null && !"".equals(s[3].trim())){
				sb.append("<a href='"+s[3]+"' target='_blank'  class='link1'>"+s[0]+"</a>");
			}else{
				sb.append(s[0]);
			}
			sb.append("(");
			if(!"red".equals(s[2])){
				sb.append(s[1]);
			}else{
				sb.append("<span style='color: red'>");
				sb.append(s[1]);
				sb.append("</span>");
			}
			sb.append(")");
		}
		try {
			if(sb.length() > 18){
				sb = new StringBuffer(sb.substring(18));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//输出到页面
		out.print(sb.toString());
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		ApplicationContext context = new ClassPathXmlApplicationContext(
		"applicationContext.xml");
		stDao = (TstatisticDAO) context.getBean("statisticDAO");
	}

}
