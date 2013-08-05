package allot;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.DataConnect;
import common.fc;

/**
 * Servlet implementation class for Servlet: AccountAllotQuery
 *
 * @web.servlet
 *   name="AccountAllotQuery"
 *   display-name="AccountAllotQuery" 
 *
 * @web.servlet-mapping
 *   url-pattern="/AccountAllotQuery"
 *  
 */
 public class AccountAllotQuery extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	   static final long serialVersionUID = 1L;
	   private static final String CONTENT_TYPE = "text/html; charset=GBK"; 
	   static DataConnect dc = new DataConnect("orderfill", false);
	   static String m_sDate = "";
	   static String m_AutoIP = ""; 
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public AccountAllotQuery() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(payment2(request, response));
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(payment2(request, response));
	}   	  	    
	
	public String payment2(HttpServletRequest request, HttpServletResponse response){
		//接收参数OK
		String id = fc.getpv(request, "id");
		String desc = fc.getpv(request, "desc");  //指定平台,指定分发给哪平台
		String ip = fc.getpv(request, "ip");  //来源平台,指明上次分发的平台,用来定位下次分发平台
		String count = fc.getpv(request, "count");  //来源平台,指明上次分发的平台,用来定位下次分发平台
		String outtime = fc.getpv(request, "outtime");  //来源平台,指明上次分发的平台,用来定位下次分发平台
		String sign = fc.getpv(request, "sign").toUpperCase();
//		if (!sign.equals(funcejb.getMd5Str(id + "789").toUpperCase())) return "ERROR,md5签名不正确";	
		//
		String s = paymentStd(id, desc, ip, count, outtime);
		return s;
	}
	
	
	
	/*  
	 * 账号充值分发
	 */ 
	public String paymentStd(String ids, String isdesc, String sip, String scount, String outtime){
		try{
			if (sip.equals("")) return "";
			//fQueueID in '" + ids + "' and 
			if (scount.equals(""))	scount = "20";
			//
			String swhere = "";
			if (outtime.equals("true"))
				swhere = "(fGiveTime < GETDATE() and fState = '2') ";
			else
				swhere = "(fGiveTime >= GETDATE() - 1 and fState = '1') ";
			//
			if (isdesc.equals("desc")) 
				isdesc = " order by fCreateTime desc ";
			else
				isdesc = " order by fCreateTime ";
			String sql = "select top " + scount + " fid from AcOrder where fLockID='' and " + swhere + isdesc ;
			ResultSet rs = dc.query(sql);
			if (rs!=null){
				int count = 0;
				String sData = "";
				while (rs.next()){
					count++;
					String id = fc.getrv(rs, "fid", "");
					sData = sData + "<row_" + count + ">" +
							"<fID>" + fc.getrv(rs, "fid", "") + "</fID>" +
							"<fClientID>" + fc.getrv(rs, "fClientID", "") +"</fClientID>" +
							"<fData>http://" + sip +"/faallot/AccountAllot?id=" + fc.getrv(rs, "fid", "") + "</fData>" +
							"<fOKStr></fOKStr>" + "</row_" + count + ">";
				} 
				dc.CloseResultSet(rs);
				return fc.getResultStr("allotquery", sData, "<RowCount>" + count + "</RowCount>", "true", "");
			}else
				System.out.print("[分发查询]查询时失败" );
			return "";
		}catch(Exception e){
			System.out.print("[分发查询]运行时失败" );
			return "";
		}
	}
	
	
	
}