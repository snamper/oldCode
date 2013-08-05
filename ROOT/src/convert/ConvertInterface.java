package convert;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.DataConnect;
import common.fc;

/**
 * Servlet implementation class for Servlet: ConvertInterface
 *
 * web.servlet
 *   name="ConvertInterface"
 *   display-name="ConvertInterface" 
 *
 * web.servlet-mapping
 *   url-pattern="/ConvertInterface"
 *   
 */
 public class ConvertInterface extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	 	private static final String CONTENT_TYPE = "text/html; charset=GBK";
	    private static DataConnect dc = new DataConnect("orderfill", false);

   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public ConvertInterface() { 
		super();
	}  
   	
	public void destroy() { 
		if (dc != null) {
			dc.CloseConnect(); 
			dc = null;
		} 
	}
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		String s = convertInterface(request, response); 
		if (s.indexOf("非法访问") == 0)
			response.sendError(403);
		else{
			PrintWriter out = response.getWriter();
			out.print(s);
		}
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		String s = convertInterface(request, response); 
		if (s.indexOf("非法访问") == 0)
			response.sendError(403);
		else{ 
			PrintWriter out = response.getWriter();
			out.print(s);
		}
	}   	  	
	 
	/*
	 * 订单请求转换接口
	 */
	public String convertInterface(HttpServletRequest request, HttpServletResponse response){
		try{
			//接收funcid参数
			String faceid = fc.getpv(request, "faceid"); 
			if (faceid.indexOf("orderfc") > -1) return "非法访问,URL路径含有保留字"; 
			if (faceid.indexOf("orderfa") > -1) return "非法访问,URL路径含有保留字"; 
			if (faceid.equals("")) return "非法访问,URL路径为空"; 
	 		int n1 = (int)System.currentTimeMillis();

			//获得客服端IP
			String ClientIp = request.getHeader("X-Client-Address"); 
			if (ClientIp == null || ClientIp.equals(""))
				ClientIp = request.getRemoteAddr();
			
			//从供应商信息表中读取出对应的代理商ID, 如果代理商不存在, 则跳转到404
			String AgentID = "", fgiveip = "", facfaceid = "", fcafaceid = "", facqueryid = "", fcaqueryid="";
			String sql = "select * from CoAgent where (fcafaceid = '" + faceid + "' or fcaqueryid = '" + faceid + "' " +
					" or facfaceid = '" + faceid + "' or facqueryid = '" + faceid + "')" +
					" and (ffaceip = '" + ClientIp + "' or ffaceip = '*')";
			ResultSet rs = dc.query(sql);
			if (rs == null) return "非法访问,查询URL路径失败"; 
			if (rs.next()){ 
				AgentID = fc.getrv(rs, "fid", "");
				fgiveip = fc.getrv(rs, "fGiveIp", "");		//分发IP
				facfaceid = fc.getrv(rs, "fAcFaceID", "");
				fcafaceid = fc.getrv(rs, "fCaFaceID", "");
				facqueryid = fc.getrv(rs, "fAcQueryID", "");
				fcaqueryid = fc.getrv(rs, "fCaQueryID", "");
			}
			dc.CloseResultSet(rs);	
			if (AgentID.equals(""))	return "非法访问,URL路径及绑定IP校验失败:" + ClientIp + ":" + request.getLocalPort();
			
			 
			String s = "";

			//处理请求
			if (fcafaceid.equals(faceid)) s = OrderRequest_CardAccept(AgentID, fgiveip, request);	//卡订单请求
			if (facfaceid.equals(faceid)) s = OrderRequest_AccoAccept(AgentID, fgiveip, request);	//号订单请求
			//查询请求
			if (fcaqueryid.equals(faceid)) s = OrderRequest_CardQuery(AgentID, fgiveip, request);	//卡查询
			if (facqueryid.equals(faceid)) s = OrderRequest_AccoQuery(AgentID, fgiveip, request);	//号查询
			
			//计时
			int n2 = (int)System.currentTimeMillis();
			System.out.print("用时:" + (n2 - n1) + "ms");

			//
			if (!s.equals(""))
				return s; 
			else
				return "非法访问,转换请求无效";
		}catch(Exception e){ 
			return "非法访问,转换请求时出错";
		}
 
	}

	/*
	 * 号订单请求
	 */
	private String OrderRequest_AccoAccept(String agentID, String ip, HttpServletRequest request) {
		try{
			//查询出转换的参数名, 如果没有定义, 则用原参数名
			String OrderID = "oid";
			String ClientID = "cid";
			String ProductID = "pid";
			String PlayName = "pn";
			String Price = "pr";
			String Number = "nb";
			String Money = "fm";
			String ReUrl = "ru";
			String Info1 = "info1";
			String Info2 = "info2";
			String Info3 = "info3";
			String fAccountType = "at"; 
			String fCountType = "ct"; 
			String fFillRange = "fr"; 
			String fFillServer = "fs";
			String fClientTime = "tm";
			String fClientIp = "ip";
			String fClientArea = "ar";
			String fSource = "source";
			String OkStr = "success";
			String ErrorStr = "fail";
			String sign = "sign";
			//
			String sql = "select * from CoAgentParam where fAgentID = '" + agentID + "' and fType = 'ACCOUNT_REQUEST'";
			ResultSet rs = dc.query(sql);
			while (rs!=null && rs.next()){
				String sOld = fc.getrv2(rs, "fOldParam", "");
				String sNew = fc.getrv2(rs, "fNewParam", "");
				if (sOld.equals("oid")) OrderID = sNew; 
				if (sOld.equals("cid")) ClientID = sNew;
				if (sOld.equals("pid")) ProductID = sNew;
				if (sOld.equals("pn")) PlayName = sNew;
				if (sOld.equals("pr")) Price = sNew;
				if (sOld.equals("nb")) Number = sNew;
				if (sOld.equals("fm")) Money = sNew;
				if (sOld.equals("ru")) ReUrl = sNew;
				if (sOld.equals("info1")) Info1 = sNew; 
				if (sOld.equals("info2")) Info2 = sNew;
				if (sOld.equals("info3")) Info3 = sNew; 
				if (sOld.equals("at")) fAccountType = sNew;
				if (sOld.equals("ct")) fCountType = sNew;
				if (sOld.equals("fr")) fFillRange = sNew;
				if (sOld.equals("fs")) fFillServer = sNew;
				if (sOld.equals("tm")) fClientTime = sNew;
				if (sOld.equals("ip")) fClientIp = sNew;
				if (sOld.equals("ar")) fClientArea = sNew;
				if (sOld.equals("source")) fSource = sNew;
				if (sOld.equals("sign")) sign = sNew;
				if (sOld.equals("OK_STR")) OkStr = sNew;  			//回应给客户的下单成功标志
				if (sOld.equals("ERROR_STR")) ErrorStr = sNew;
			}
			dc.CloseResultSet(rs);	
			
			//用POST方式将URL串转发给127.0.0.1:80
			String sUrl = "http://" + ip + "/faaccept/OrderRequest";
			String sData = "oid=" + fc.getpv(request, OrderID) +
							"&cid=" + fc.getpv(request, ClientID) +
							"&pid=" + fc.getpv(request, ProductID) +
							"&pn=" + fc.getpv(request, PlayName) +
							"&pr=" + fc.getpv(request, Price) +
							"&nb=" + fc.getpv(request, Number) +
							"&fm=" + fc.getpv(request, Money) +
							"&ru=" + fc.getpv(request, ReUrl) +
							"&info1=" + fc.getpv(request, Info1) +
 							"&info2=" + fc.getpv(request, Info2) +
							"&info3=" + fc.getpv(request, Info3) +
							"&at=" + fc.getpv(request, fAccountType) +
							"&ct=" + fc.getpv(request, fCountType) +
							"&fr=" + fc.getpv(request, fFillRange) +
							"&fs=" + fc.getpv(request, fFillServer) +
							"&tm=" + fc.getpv(request, fClientTime) +
							"&ip=" + fc.getpv(request, fClientIp) +
							"&ar=" + fc.getpv(request, fClientArea) +
							"&source=" + fc.getpv(request, fSource) +
							"&sign=" + fc.getpv(request, sign);
			System.out.print("[POST]" + sUrl + "?" + sData);
			String s = fc.SendDataViaPost(sUrl, sData, "");
			//转换回应信息
			s = s.replace("success", OkStr);
			s = s.replace("fail", ErrorStr);
			return s;
		}catch(Exception e){
			e.printStackTrace();
			return "ERROR,接收卡订单时出错";
		}
	}


	/*
	 * 卡订单请求
	 */
	private String OrderRequest_CardAccept(String agentID, String ip, HttpServletRequest request) {
		try{
			//查询出转换的参数名, 如果没有定义, 则用原参数名
			String MerchantID = "MerchantID";
			String MerOrderNo = "MerOrderNo";
			String CardType = "CardType";
			String CardNo = "CardNo";
			String BankID = "BankID";
			String CardPassword = "CardPassword";
			String Money = "Money";
			String AccountID = "AccountID";
			String CustomizeA = "CustomizeA";
			String CustomizeB = "CustomizeB";
			String CustomizeC = "CustomizeC";
			String NoticeURL = "NoticeURL"; 
			String NoticePage = "NoticePage"; 
			String IsEncrypt = "IsEncrypt"; 
			String SubmitTime = "SubmitTime";
			String OkStr = "OK";
			String ErrorStr = "ERROR";
			String sign = "sign";
			String sql = "select * from CoAgentParam where fAgentID = '" + agentID + "' and fType = 'CARD_REQUEST'";
			ResultSet rs = dc.query(sql);
			while (rs!=null && rs.next()){
				String sOld = fc.getrv2(rs, "fOldParam", "");
				String sNew = fc.getrv2(rs, "fNewParam", "");
				if (sOld.equals("MerchantID")) MerchantID = sNew; 
				if (sOld.equals("MerOrderNo")) MerOrderNo = sNew;
				if (sOld.equals("CardType")) CardType = sNew;
				if (sOld.equals("CardNo")) CardNo = sNew;
				if (sOld.equals("BankID")) BankID = sNew;
				if (sOld.equals("Money")) Money = sNew;
				if (sOld.equals("CardPassword")) CardPassword = sNew;
				if (sOld.equals("AccountID")) AccountID = sNew;
				if (sOld.equals("CustomizeA")) CustomizeA = sNew; 
				if (sOld.equals("CustomizeB")) CustomizeB = sNew;
				if (sOld.equals("CustomizeC")) CustomizeC = sNew; 
				if (sOld.equals("NoticeURL")) NoticeURL = sNew;
				if (sOld.equals("NoticePage")) NoticePage = sNew;
				if (sOld.equals("IsEncrypt")) IsEncrypt = sNew;
				if (sOld.equals("SubmitTime")) SubmitTime = sNew;
				if (sOld.equals("sign")) sign = sNew; 
				if (sOld.equals("OK_STR")) OkStr = sNew;  			//回应给客户的下单成功标志
				if (sOld.equals("ERROR_STR")) ErrorStr = sNew;
			}
			dc.CloseResultSet(rs);	
			
			//用POST方式将URL串转发给127.0.0.1:80
			String sUrl = "http://" + ip + "/orderfc/PayRequest";
			String sData = "MerchantID=" + fc.getpv(request, MerchantID) +
							"&MerOrderNo=" + fc.getpv(request, MerOrderNo) +
							"&CardType=" + fc.getpv(request, CardType) +
							"&CardNo=" + fc.getpv(request, CardNo) +
							"&BankID=" + fc.getpv(request, BankID) +
							"&CardPassword=" + fc.getpv(request, CardPassword) +
							"&Money=" + fc.getpv(request, Money) +
							"&AccountID=" + fc.getpv(request, AccountID) +
							"&CustomizeA=" + fc.getpv(request, CustomizeA) +
 							"&CustomizeB=" + fc.getpv(request, CustomizeB) +
							"&CustomizeC=" + fc.getpv(request, CustomizeC) +
							"&NoticeURL=" + fc.getpv(request, NoticeURL) +
							"&NoticePage=" + fc.getpv(request, NoticePage) +
							"&IsEncrypt=" + fc.getpv(request, IsEncrypt) +
							"&SubmitTime=" + fc.getpv(request, SubmitTime) +
							"&sign=" + fc.getpv(request, sign);
			System.out.print("[POST]" + sUrl + "?" + sData);
			String s = fc.SendDataViaPost(sUrl, sData, "");
			//
			s = s.replace("OK", OkStr);
			s = s.replace("ERROR", ErrorStr);
			return s;
		}catch(Exception e){
			e.printStackTrace();
			return "ERROR,接收卡订单时出错";
		}
	}

	
	/*
	 * 号查询请求
	 */
	private String OrderRequest_AccoQuery(String agentID, String ip, HttpServletRequest request) {
		try{
			//查询出转换的参数名, 如果没有定义, 则用原参数名
			String OrderID = "oid";
			String ClientID = "cid";
			String Type = "type";
			String sign = "sign";
			String ste = "ste";
			//
			String sql = "select * from CoAgentParam where fAgentID = '" + agentID + "' and fType = 'ACCOUNT_QUERY'";
			ResultSet rs = dc.query(sql);
			while (rs!=null && rs.next()){
				String sOld = fc.getrv2(rs, "fOldParam", "");
				String sNew = fc.getrv2(rs, "fNewParam", "");
				if (sOld.equals("oid")) OrderID = sNew; 
				if (sOld.equals("cid")) ClientID = sNew;
				if (sOld.equals("type")) Type = sNew;
				if (sOld.equals("sign")) sign = sNew;
				if (sOld.equals("ste")) ste = sNew;
			}
			dc.CloseResultSet(rs);	

			//
			String s = "";
			String TypeValue = fc.getpv(request, Type);
			
			//查询订单
			if (TypeValue.equals("0")){
				//用POST方式将URL串转发给127.0.0.1:80
				String sUrl = "http://" + ip + "/fanotice/PayNotify";
				String sData = "oid=" + fc.getpv(request, OrderID) +
								"&cid=" + fc.getpv(request, ClientID) +
								"&sign=" + fc.getpv(request, sign);
				System.out.print("[POST]" + sUrl + "?" + sData);
				s = fc.SendDataViaPost(sUrl, sData, "");
				
			}else if (TypeValue.equals("1")){//查询余额
				//用POST方式将URL串转发给127.0.0.1:80
				String sUrl = "http://" + ip + "/faaccept/QueryMoney";
				String sData = "cid=" + fc.getpv(request, ClientID) +
								"&sign=" + fc.getpv(request, sign);
				System.out.print("[POST]" + sUrl + "?" + sData);
				s = fc.SendDataViaPost(sUrl, sData, "");	
				
			}else if(TypeValue.equals("2")){//查询通知中的订单
				String sUrl = "http://" + ip + "/faaccept/NoticeSelectServlet";
				String sData = "cid=" + fc.getpv(request, ClientID) +
								"&sign=" + fc.getpv(request, sign);
				System.out.print("[POST]" + sUrl + "?" + sData);
				s = fc.SendDataViaPost(sUrl, sData, "");
				
			}else if(TypeValue.equals("3")){//更新订单通知状态为通知成功
				String sUrl = "http://" + ip + "/faaccept/NoticeStateServlet";
				String sData = "oid=" + fc.getpv(request, OrderID) +
								"&cid=" + fc.getpv(request, ClientID) +
								"&ste=" + fc.getpv(request, ste) +
								"&sign=" + fc.getpv(request, sign);
				System.out.print("[POST]" + sUrl + "?" + sData);
				s = fc.SendDataViaPost(sUrl, sData, "");
				
			}else{
				s = fc.getResultStr("query", "false", "type参数有误");
			}
				
			
			//转换回应信息
			return s;
		}catch(Exception e){
			e.printStackTrace();
			return "ERROR,接收卡订单时出错";
		}
	}




	/*
	 * 卡查询请求
	 */
	private String OrderRequest_CardQuery(String agentID, String ip, HttpServletRequest request) {
		try{
			//查询出转换的参数名, 如果没有定义, 则用原参数名
			String MerchantID = "MerchantID";
			String MerOrderNo = "MerOrderNo";
			String sign = "sign";
			String sql = "select * from CoAgentParam where fAgentID = '" + agentID + "' and fType = 'CARD_QUERY'";
			ResultSet rs = dc.query(sql);
			while (rs!=null && rs.next()){
				String sOld = fc.getrv2(rs, "fOldParam", "");
				String sNew = fc.getrv2(rs, "fNewParam", "");
				if (sOld.equals("MerchantID")) MerchantID = sNew; 
				if (sOld.equals("MerOrderNo")) MerOrderNo = sNew;
				if (sOld.equals("sign")) sign = sNew; 
			}
			dc.CloseResultSet(rs);	
			
			//用POST方式将URL串转发给127.0.0.1:80
			String sUrl = "http://" + ip + "/orderfc/PayRequest";
			String sData = "MerchantID=" + fc.getpv(request, MerchantID) +
							"&MerOrderNo=" + fc.getpv(request, MerOrderNo) +
							"&sign=" + fc.getpv(request, sign);
			System.out.print("[POST]" + sUrl + "?" + sData);
			String s = fc.SendDataViaPost(sUrl, sData, "");
			//
			return s;
		}catch(Exception e){
			e.printStackTrace();
			return "ERROR,接收卡订单时出错";
		}
	}

 }