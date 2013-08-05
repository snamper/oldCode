package accept.v1;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.DataConnect;
import common.fc;

/**
 */
 public class NoticeSelectServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
   private static DataConnect dc = new DataConnect("orderfill", false);
   private static final String CONTENT_TYPE = "text/html; charset=GBK";
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public NoticeSelectServlet() {
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
		out.print(noticeSelect(request, response));
		
		
	}
	
	private String noticeSelect(HttpServletRequest request, HttpServletResponse response){
		String fClientID = fc.getpv(request, "cid");
		String sign = fc.getpv(request, "sign");
//		fClientID = "gai213";
		
		//参数检验
		if (sign.equals("")) return fc.getResultStr("response", "false", "[md5签名]参数有误");		
		if (fClientID.equals("")) return fc.getResultStr("response", "false", "[客户ID]参数有误");
		
		//获取客户key
		String key = getKeyByClientID(fClientID);
		
		if(null == key || "".equals(key)){
			return fc.getResultStr("response", "false", "不存在此客户或不存在key");
		}
		
		if(!sign.equalsIgnoreCase(fc.getMd5Str(fClientID + key)) && !sign.equalsIgnoreCase(fc.getMd5Str(fClientID + "2mZbHsip9GIe7jrVX3O16JTAwgzFd4CY"))){
			return fc.getResultStr("response", "false", "MD5验证失败");	
		}
		
		List<Map<String,String>> noticeSelList = getNoticeOrder(fClientID);
		
		if(noticeSelList == null){
			return fc.getResultStr("response", "false", "订单查询失败");
		}
		
		StringBuffer sData = new StringBuffer();
		int nCount = noticeSelList.size();
		for(int i = 0; i < nCount; i++){
			Map<String,String> map = noticeSelList.get(i);
				sData.append("<row_"+(i+1)+">");
				
				sData.append("<sid>" +map.get("PayOrderNo")+ "</sid>");
				sData.append("<ste>" +map.get("PayResult")+ "</ste>");
				sData.append("<cid>" +map.get("ClientID")+ "</cid>");
				sData.append("<pid>" +map.get("ProductID")+ "</pid>");
				sData.append("<oid>" +map.get("MerOrderNo")+ "</oid>");
				sData.append("<pn>" +map.get("PlayName")+ "</pn>");
				sData.append("<fm>" +map.get("FactMoney")+ "</fm>");
				sData.append("<info1>" +map.get("CustomizeA")+ "</info1>");
				sData.append("<info2>" +map.get("CustomizeB")+ "</info2>");
				sData.append("<info3>" +map.get("CustomizeC")+ "</info3>");
				
				sData.append("</row_"+(i+1)+">");
		}
		
		return fc.getResultStr("query", sData.toString(), "<RowCount>"+nCount+"</RowCount>", "true", "查询成功");
		
	}

	private List<Map<String,String>> getNoticeOrder(String clientID) {
		List<Map<String,String>> result = new ArrayList<Map<String,String>>(20);
		String sql = "select top 20 fid,fState,fClientID,fProductID,fOrderId,fPlayName,fFactMoney,fInfo1,fInfo2,fInfo3 from AcOrder WITH (NOLOCK) where fCreateTime > GETDATE() - 7 AND fClientID='"+clientID+"' AND fNoticeState='1' order by fCreateTime asc";
		ResultSet rs = null;
		try {
			rs = dc.query(sql);
			Map<String,String> oneOrder = null;
			while(rs != null && rs.next()){
				//取出数据
				String PayOrderNo = fc.getrv(rs, "fid", "");		  	//系统订单号
				String PayResult = fc.getrv(rs, "fState", "");	  		//充值结果
				String ClientID = fc.getrv(rs, "fClientID", "");  		//商户ID
				String ProductID = fc.getrv(rs, "fProductID", "");  	//商品类型
				String MerOrderNo = fc.getrv(rs, "fOrderId", "");  		//商户订单号
				String PlayName = fc.getrv(rs, "fPlayName", "");		//账号
				String FactMoney = fc.getrv(rs, "fFactMoney", "0");  	//充值金额
				
				String CustomizeA = fc.getrv(rs, "fInfo1", "");  		//自定义A
				String CustomizeB = fc.getrv(rs, "fInfo2", "");  		//自定义B
				String CustomizeC = fc.getrv(rs, "fInfo3", "");  		//自定义B
				
				oneOrder = new HashMap<String, String>();
				oneOrder.put("PayOrderNo", PayOrderNo);
				oneOrder.put("PayResult", PayResult);
				oneOrder.put("ClientID", ClientID);
				oneOrder.put("ProductID", ProductID);
				oneOrder.put("MerOrderNo", MerOrderNo);
				oneOrder.put("PlayName", PlayName);
				oneOrder.put("FactMoney", FactMoney);
				oneOrder.put("CustomizeA", CustomizeA);
				oneOrder.put("CustomizeB", CustomizeB);
				oneOrder.put("CustomizeC", CustomizeC);
				
				result.add(oneOrder);
			}
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		}
		dc.CloseResultSet(rs);
		
		return result;
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