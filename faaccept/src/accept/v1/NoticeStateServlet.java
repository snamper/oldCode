package accept.v1;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.DataConnect;
import common.fc;

/**
 */
 public class NoticeStateServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
   private static DataConnect dc = new DataConnect("orderfill", false);
   private static final String CONTENT_TYPE = "text/html; charset=GBK";
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public NoticeStateServlet() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(updateNotice(request, response));
	}

	
	private String updateNotice(HttpServletRequest request,
			HttpServletResponse response) {
		
		String fClientID = fc.getpv(request, "cid");
		String fMerOrderNo = fc.getpv(request, "oid");
		String noticeState = fc.getpv(request, "ste");
		String sign = fc.getpv(request, "sign");
		
		//获取客户key
		String key = getKeyByClientID(fClientID);
		
		if(null == key || "".equals(key)){
			return fc.getResultStr("response", "false", "不存在此客户或不存在key");
		}
		
		if(!sign.equalsIgnoreCase(fc.getMd5Str(fClientID + fMerOrderNo + key)) && !sign.equalsIgnoreCase(fc.getMd5Str(fClientID + fMerOrderNo + "2mZbHsip9GIe7jrVX3O16JTAwgzFd4CY"))){
			return fc.getResultStr("response", "false", "MD5验证失败");	
		}
		
		//参数检验
		if (sign.equals("")) return fc.getResultStr("response", "false", "[md5签名]参数有误");		
		if (fClientID.equals("")) return fc.getResultStr("response", "false", "[客户ID]参数有误");
		if (fMerOrderNo.equals("")) return fc.getResultStr("response", "false", "[商户订单号]参数有误");
		if (noticeState.equals("")) return fc.getResultStr("response", "false", "[更新状态]参数有误");
		
		if("0".equals(noticeState)){
			int msgi = updateNoticeState(fClientID,fMerOrderNo);
			if(msgi > 0){
				return fc.getResultStr("response", "true", "订单通知状成更新成功");
			}else{
				return fc.getResultStr("response", "false", "订单通知状成更新失败");
			}
		}else{
			return fc.getResultStr("response", "false", "订单通知状成更新失败");
		}
		
	}   	  	    
	
	/**
	 * 更新订单通知状态为通知成功
	 * @param clientID
	 * @param merOrderNo
	 * @return
	 */
	private int updateNoticeState(String clientID, String merOrderNo) {
		String sql = "update AcOrder set fNoticeState='2' where fOrderId='"+merOrderNo+"' AND fClientID='"+clientID+"'";
		return dc.execute(sql);
		
	}

	/**
	 * 查询客户的key
	 * @param clientID
	 * @return
	 */
	private String getKeyByClientID(String clientID) {
		String sql = "select fKey from CoClient where fID='"+clientID+"'";
		ResultSet rs = null;
		String result = "";
		try {
			rs = dc.query(sql);
			if(rs != null && rs.next()){
				result = fc.getrv(rs, "fKey", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		dc.CloseResultSet(rs);
		return result;
	}   
}