package accocheck;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.*;

/*
 * 湖南QB充值获取可用号段处理
 */

/**
 * Servlet implementation class for Servlet: HunanQBCheck
 *
 * @web.servlet
 *   name="HunanQBCheck"
 *   display-name="HunanQBCheck" 
 *
 * @web.servlet-mapping
 *   url-pattern="/HunanQBCheck"
 *  
 */
 public class HunanQBCheck extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	   static final long serialVersionUID = 1L;
	   private static final String CONTENT_TYPE = "text/html; charset=GBK";
	   static DataConnect dc = new DataConnect("orderfill", false);
	   static int ConnectCount = 0;
	   static int OKCount = 0, RanCount = 0;
	   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public HunanQBCheck() {
		super();
		ConnectCount = ConnectCount + 1;
		System.out.print("[poolfill.AcceptResult]新的实例,当前共有个" + ConnectCount + "实例");
//		if (dc == null){
//			dc = new DataConnect();		//公用一个连接
//			System.out.print("[poolfill.AcceptResult]第一个实例,初始化数据库连接");
//		}
	} 
	
	/* (non-Javadoc)
	 * @see javax.servlet.Servlet#destroy()
	 */
	public void destroy() {
		ConnectCount = ConnectCount - 1;
		if (ConnectCount <= 0){
			dc.CloseConnect();
			dc = null;
			System.out.print("[poolfill.AcceptResult]实例数为0,已关闭数据库连接");
		}
		super.destroy();
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(getSectionNo(request, response));
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(getSectionNo(request, response));
	}   	  	    
	
	/*
	 * 暂无用处
	 */
	public String getSectionNo(HttpServletRequest request, HttpServletResponse response){
		try{
			//调用
			String sID = fc.getpv(request, "id");
			String sQQNumber = fc.getpv(request, "qq");
			String sMoney = fc.getpv(request, "money");
			String s = getSectionNo(sID, sQQNumber, sMoney);
			//
//			Thread t1 = new Thread(new HunanQBThread(sID, s, sQQNumber, sMoney, "0")); 
//			t1.start(); 
			//
			return s;
		}catch(Exception e){
			e.printStackTrace();
			return gr("false", "意外错误:" + e.toString());
		}
		
		
	}
	
	/*
	 * 返回可用号段
	 */
	public static String getSectionNo(String sID, String sQQNumber, String sMoney){
		try{
			//
			System.out.print("[湖南号段分析]接收:" + sID + "," + sQQNumber + "," + sMoney);
			if (sMoney.equals("")) return "ERROR,金额不能为空";
			if (sQQNumber.equals("")) return "ERROR,QQ号不能为空";
			
			//分析QB充值当日是否到量			
			String sql = "select sum(fMoney) as fMoney from AcNumberAnalysis" +
						" where fPlayName = '" + sQQNumber + "' and fState = '0'" +
						" and fTime >= '" + fc.getTime("yyyy-MM-dd") + " 0:0:0'";
			ResultSet rs = dc.query(sql);
			float fDay = 0;
			if (rs != null && rs.next()){
				fDay = Float.valueOf(fc.getrv(rs, "fMoney", "0")).floatValue();
				if (fDay + 5 > 60){
					dc.CloseResultSet(rs);
					return "ERROR,QQ号超过日最大充值金额";
				}
			}
			dc.CloseResultSet(rs);
			
			//分析QB充值当月是否到量
			sql = "select sum(fMoney) as fMoney from AcNumberAnalysis" +
						" where fPlayName = '" + sQQNumber + "' and fState = '0'" +
						" and fTime >= '" + fc.getTime("yyyy-MM") + "-01 0:0:0'";
			rs = dc.query(sql);
			float fMonth = 0;
			if (rs != null && rs.next()){
				fMonth = Float.valueOf(fc.getrv(rs, "fMoney", "0")).floatValue();
				if (fMonth + 5 > 240){
					dc.CloseResultSet(rs);
					return "ERROR,该QQ充值当月限额已到";
				}
			}
			dc.CloseResultSet(rs);

			//分析可充值面额
			float nValue = 0;
			float nMoney = Float.valueOf(sMoney).floatValue();
			if ((nMoney >= 5) && (fDay + 5 <= 60) && (fMonth + 5 <= 240)) nValue = 5; 
			if ((nMoney >= 10) && (fDay + 10 <= 60) && (fMonth + 10 <= 240)) nValue = 10; 
			if ((nMoney >= 20) && (fDay + 20 <= 60) && (fMonth + 20 <= 240)) nValue = 20; 
			if ((nMoney >= 30) && (fDay + 30 <= 60) && (fMonth + 30 <= 240)) nValue = 30; 
			
			
			//获到可用号段
			sql = "select fID from AcNumberType where fMonthValue <= fMonthMax - " + nValue + "" +
					" and fErrorCount <= 10 order by fCount,fMonthValue";
			rs = dc.query(sql);
			if (rs == null || !rs.next()){
				dc.CloseResultSet(rs);
				return "ERROR,无可用的号段";
			}
			String sNumberType = fc.getrv(rs, "fID", "");
			dc.CloseResultSet(rs);
			dc.execute("update AcNumberType set fCount = fCount + 1 where fid = '" + sNumberType + "'");
			
			//获到可用号码
			String sNumber = sNumberType + (fc.ran(1000, 1999) + "").substring(1);
			
//			int nn = OKCount / (RanCount + OKCount) * 100;	//当前成功率
//			if (nn <= SuccessRate){							//计划成功率
//				sql = "select * from AcNumberPool where fDayValue <= 60 - " + nValue + "" +
//						" and fMonthValue <= 240 - " + nValue + " and fType = '" + sNumberType + "'";
//				rs = dc.query(sql);
//				//获取可用的号码
//				if (rs != null && rs.next()){
//					sNumber = fc.getrv(rs, "fID", "");		//可用的号码
//				}
//				dc.CloseResultSet(rs);
//			}
			//随机选择可用号码

//			dc.CloseResultSet(rs);
			
		
			//返回
			
			return sNumber + "," + nValue;
		}catch(Exception e){
			e.printStackTrace();
			return "ERROR,执行出错:" + e.toString();
		}
		
		
	}

	private static String gr(String sState, String sMsg) {
		return fc.getResultStr("getaccount", sState, sMsg);
	}

	
}