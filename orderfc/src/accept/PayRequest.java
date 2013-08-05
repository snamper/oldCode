package accept;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.DataConnect;
import common.fc;
import common.Error;



/*
 * 功能:接收商户支付请求 , 检查参数是否合法, 商户身份是否合法, 商品是否可用, 商户订单号是否重复, 保存订单
 */

/**
 * Servlet implementation class for Servlet: PayRequest
 *
 * web.servlet
 *   name="PayRequest"
 *   display-name="PayRequest" 
 *
 * web.servlet-mapping 
 *   url-pattern="/PayRequest"
 *   
 */
 public class PayRequest extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   private static final String CONTENT_TYPE = "text/html; charset=GBK";
   static DataConnect dc = null;		//公用一个连接

   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public PayRequest() {
		super();
		if (dc == null) dc = new DataConnect("orderfill", false);
	}   	
	
	/* (non-Javadoc)
	 * @see javax.servlet.Servlet#destroy()
	 */ 
	public void destroy() {
		if (dc != null){
			System.out.print("[PayRequest]关闭数据库连接");
			dc.CloseConnect();
			dc = null;
		}
		super.destroy();
	}  
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(payRequest(request, response));
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(payRequest(request, response));
	}   	  	    
	
	/*
	 * 接收商户支付请求
	 */
	public String payRequest(HttpServletRequest request, HttpServletResponse response){
		try{
			//商户参数
			String sign = fc.getpv(request, "sign");					//签名(sign)
			String MerchantID = fc.getpv(request, "MerchantID");	 	//商户ID(userid)
			String MerOrderNo = fc.getpv(request, "MerOrderNo");		//商户订单号(orderid)
			String CardType = fc.getpv(request, "CardType");			//卡类型(cardtype)
			String CardNo = fc.getpv(request, "CardNo");				//卡号(cardid)
			String CardPassword = fc.getpv(request, "CardPassword");	//卡密(cardpass)
			String CustomizeA = fc.getpv(request, "CustomizeA");		//商户自定义(usera) 客户自定义A
			String CustomizeB = fc.getpv(request, "CustomizeB");		//商户自定义(userb) 客户自定义B
			String CustomizeC = fc.getpv(request, "CustomizeC");		//商户自定义(userc) 特殊值
			String NoticeURL = fc.getpv(request, "NoticeURL");			//充值结果回馈地址(userurla)
			String NoticePage = fc.getpv(request, "NoticePage");		//充值结果回馈页面(userurlb)
			String SubmitTime = fc.getpv(request, "SubmitTime");		//对账时间yyyyMMddHHmmss
			String IsEncrypt = fc.getpv(request, "IsEncrypt");			//卡号和密码是否使用加密(v310)
			//新版本的参数
			String Money = fc.getpv(request, "Money");						//支付金额,存放在面值字段中
			String BankID = fc.getpv(request, "BankID");					//银行代码,存放在上小类型字段中
			String AccountID= fc.getpv(request, "AccountID");				//充值账号/人民币账号 
			//特殊参数
			String FillID= fc.getpv(request, "FillID");				 	//充值ID,当有这个参数时,充值状态直接为分发成功
			String GiveID= fc.getpv(request, "GiveID");				 	//分发平台,当充值ID不为空时,这个参数才能生效
			
			 
			//IP
			String userip = request.getRemoteAddr();						//商户IP
			String localip = request.getLocalAddr();						//本地IP
			String localport = request.getLocalPort() + "";					//本地端口
			
			//out.key 
			Error.outs("[" + userip + "][Payrequest]接收参数:sign=" + sign + ",MerchantID=" + MerchantID + ",MerOrderNo=" + MerOrderNo 
					+ ",CardType=" + CardType + ",CradNo=" + CardNo + ",BankID=" + BankID + ",CardPassword=" + CardPassword + ",Money=" + Money 
					+ ",CustomizeA=" + CustomizeA + ",CustomizeB=" + CustomizeB + ",CustomizeC=" + CustomizeC + ",AccountID=" + AccountID
					+ ",NoticeURL=" + NoticeURL + ",NoticePage=" + NoticePage + ",SubmitTime=" + SubmitTime, "key");		
			
			//提交订单
			String s = submitOrder(sign, MerchantID, MerOrderNo, CardType, 
					CardNo,	BankID, CardPassword, Money, CustomizeA, CustomizeB, CustomizeC, 
					AccountID, NoticeURL, NoticePage, SubmitTime, 
					localip, localport, IsEncrypt, response, false, FillID, GiveID);
			
			//out.key
			Error.outs("[" + userip + "][PayRequest]返回:" + s);
			
			//
			if (CardType.equals("15")){
				if (s.indexOf("ERROR") == 0){
					response.sendRedirect("error.jsp?s=" + java.net.URLEncoder.encode(s));
					return s;
				}else
					return s;				
			}else{
				return s;
			}
			//
 
		}catch(Exception e){
			Error.oute(e, "error"); 
			return "ERROR,系统忙,请稍候再试!";
		}
	}
 
	//---------------------------------------------------内部调用---------------------------------------------------------

	//全参数
 	public String submitOrder(String sign, String MerchantID, String MerOrderNo, String CardType, 
 			String CardNo, String BankID, String CardPassword, String Money, String CustomizeA, String CustomizeB, String CustomizeC, 
 			String FillAccount, String NoticeURL, String NoticePage, String SubmitTime, 
 			String localip, String localport, String IsEncrypt, 
 			HttpServletResponse response, boolean IsChargeback, String FillID, String GiveID){

	try{
 			
		//1.检查基本参数是否为空
		if (MerOrderNo.equals("")) {
			Error.outs("[Payrequest]用户订单号(MerOrderNo)不能为空", "warning");
			return "ERROR,商户订单号不能为空";
		}
		if (CardType.equals("")) {
			Error.outs("[Payrequest]卡类型(CardType)不能为空", "warning");
			return "ERROR,充值卡类型不能为空";
		}
		if (MerchantID.equals("")) {
			Error.outs("[Payrequest]MerchantID不能为空", "warning");
			return "ERROR,商户编号不能为空";		
		}
		if (Money.equals("")){
			Error.outs("[Payrequest]支付金额(Money)不能为空", "warning");
			return "ERROR,支付面额或金额不能为空";
		}
		if (!CardType.equals("15")){
			if (CardNo.equals("")) {
				Error.outs("[Payrequest]卡号(CardNo)不能为空", "warning");
				return "ERROR,充值卡不能为空";
			}
			if (CardPassword.equals("")) {
				Error.outs("[Payrequest]卡号(BankID)不能为空", "warning");
				return "ERROR,充值卡密码不能为空";
			}
		}else{
			if (BankID.equals("")) {
				Error.outs("[Payrequest]卡号(BankID)不能为空", "warning");
				return "ERROR,网银代码不能为空";
			}
		}
		if (SubmitTime.equals("")) 
			SubmitTime = "GETDATE()";
		else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
			try{
				Date tt = sdf.parse(SubmitTime);
			}catch(Exception te){
				Error.outs("[Payrequest]卡号(BankID)不能为空", "warning");
				return "ERROR,提单时间格式不正确";
			}
			SubmitTime = "'" + SubmitTime + "'";
		}
		
		//参数
		String AgentID = "", key = "", ClientID = "";
		String AgentRate = "", ClientRate = "";
		String ChargeType = "", SingleMoney = "";
		String DefState = "", DefGiveID = "";

		//2.商户身份检查,得到KEY和代理商ID
		String sql = "";
		sql = "select fID, fAgentID, fKey from CoClient where (fId = '" + MerchantID + "' or fFaceID = '" + MerchantID + "') and fState = '0'";
		ResultSet rs = dc.query(sql);
		if ((rs == null) || (!rs.next())){
			dc.CloseResultSet(rs);
			Error.outs("[Payrequest]无效的用户名或用户名没有被启用," + sql, "warning");
			return "ERROR,无效的用户名或用户名没有被启用";	
		} 
		key = fc.getrv(rs, "fKey", "");					//Key
		AgentID = fc.getrv(rs, "fAgentID", "");			//代理商ID
		ClientID = fc.getrv(rs, "fID", "");				//客户ID
		dc.CloseResultSet(rs);
		//
		if (key.equals("")){
			Error.outs("[Payrequest]商户验证KEY不能为空", "warning");
			return "ERROR,商户验证KEY不能为空";		
		}

		//3.检查md5签名是否合法
		String md5source = "";
		if (CardType.equals("15"))
			md5source = MerchantID + MerOrderNo + CardType + BankID + NoticeURL + Money + FillAccount + key;
		else
			md5source = MerchantID + MerOrderNo + CardType + CardNo + CardPassword + NoticeURL + Money + FillAccount + key;
		String md5 = fc.getMd5Str(md5source).toUpperCase();
		if (!md5.equals(sign.toUpperCase())) {
			Error.outs("[Payrequest]md5校验失败:md5(" + md5source + ")=" + md5, "warning");
			return "ERROR,MD5验证失败";
		}
		
		//4.对卡号和密码进行解密
		if (IsEncrypt.equals("v310")){
			try{
			  //解密   
			  CardNo = common.EncryptsE.uncrypt(CardNo, key);   
			  CardPassword = common.EncryptsE.uncrypt(CardPassword,key);   
			  //Error.outs("解密:N:" + CradNo + ",P:" + CardPassword);
			}catch(Exception ee){
				ee.printStackTrace();
				return "ERROR,卡密解密失败";
			}
		}
		
		//5.检测卡密合法性(长度,类型)	
		//5.1检查卡密长度
		String sError = AllotCardInfoTest.Test(CardType, CardNo, CardPassword);
		if (sError.indexOf("ERROR") > -1){
			return sError;
		}
		
		//5.2检查字符(不检查网银订单)
		String ss = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_";
		if (CardType.equals("15")){	//只有网银方式时,密码字段才为空
			for (int i = 0; i < CardNo.length(); i++){
				if (ss.indexOf(CardNo.substring(i, i + 1)) == -1){
					Error.outs("[Payrequest]卡号无效:" + CardNo, "warning");
					return "ERROR,卡号中含有非法字符";
				}
			}
			for (int i = 0; i < CardPassword.length(); i++){
				if (ss.indexOf(CardPassword.substring(i, i + 1)) == -1){
					Error.outs("[Payrequest]密码无效:" + CardPassword, "warning");
					return "ERROR,卡密中含有非法字符";
				}
			}
		}
		
		//6.检查商品是否可用 
		//6.1是否支持当前面值
		sError = "";
		if (!CardType.equals("15")){
			sql = "SELECT fID as fPriceID FROM CaCardPrice WHERE fProductID = '" + CardType + "' and fPrice = " + Money + " and fState = '0'";
			rs = dc.query(sql);
			if (rs!=null && !rs.next()) sError = "ERROR,不支持的卡面值[" + Money + "]";
			dc.CloseResultSet(rs); 
			if (!sError.equals("")) return sError;
		}
		//6.2代理商品是否启用,取出代理费率
		sql = "SELECT fRate, fDefState, fDefGiveID FROM CaAgentProduct WHERE fAgentID = '" + AgentID + "' and fProductID = '" + CardType + "' and fState = '0'";
		rs = dc.query(sql);
		if (rs!=null && !rs.next()) sError = "ERROR,代理商品不存在或未启用[" + CardType + "]";
		AgentRate = fc.getrv(rs, "fRate", "");
		DefState = fc.getrv(rs, "fDefState", "");
		DefGiveID = fc.getrv(rs, "fDefGiveID", "");
		dc.CloseResultSet(rs);
		if (!sError.equals("")) return sError;
		//6.3客户商品是否启用,取出客户费率 
		sql = "SELECT fRate, fChargeType, fSingleMoney FROM CaClientProduct WHERE fClientID = '" + ClientID + "' and fProductID = '" + CardType + "' and fState = '0'";
		rs = dc.query(sql);
		if (rs!=null && !rs.next()) sError = "ERROR,客户商品不存在或未启用[" + CardType + "]";
		ClientRate = fc.getrv(rs, "fRate", "");
		ChargeType = fc.getrv(rs, "fChargeType", "");
		SingleMoney = fc.getrv(rs, "fSingleMoney", "");
		
		dc.CloseResultSet(rs);
		if (!sError.equals("")) return sError;
		
		
		//7.客户订单号是否重复(24小时内)
		sql = "select top 1 fID, fState from CaOrder where fOrderID='" + MerOrderNo + "' and fCreateTime > getdate() - 1 and fClientID <> 'xmxx'";
		rs = dc.query(sql);
		if ((rs != null) && (rs.next())){
			String fState = fc.getrv(rs, "fState", "");
			String id = fc.getrv(rs, "fid", "");
			dc.CloseResultSet(rs);
			Error.outs("[Payrequest]订单号已存在[" + MerOrderNo + "]", "warning");
			//如果是网银支付, 返回js转向代码
			if (CardType.equals("15")){
				if (fState.equals("2")){
					String s = WebPay.PayResult(id, Money, BankID, ClientID);
					if (response != null) response.sendRedirect(s);
					return "<script>location.href='" + s + "';</script>";	
				}else{
					return "ERROR,当前订单已经处理完成或超时,请重新提交";
				}

			}else{
				return "ERROR,商户订单号不能重复提交";
			}
		}
		dc.CloseResultSet(rs);

		//8.生成订单号,对密码进行加密
		String sGUID = fc.getGUID6("");
		CardPassword = "(@_@)" + common.EncryptsE.encrypt(CardPassword, "64Qz9cWPiH0B25CM7IoFGjETmfAbsOxh");
		
		//9.将订单写入数据库
		String sErrorNew = "";
		if (!FillAccount.equals("") && ChargeType.equals("1")){
			//卡&号订单时,扣费写入
			sErrorNew = OrderCharge.orderCharge(sGUID, AgentID, AgentRate, ClientID, MerOrderNo, CardType,
					SubmitTime, CardNo, CardPassword, Money, ClientRate, DefState, CustomizeA, CustomizeB
					,CustomizeC, NoticeURL, NoticePage, SingleMoney);
			if (!sErrorNew.equals("")) return sErrorNew; 
		}else{
			String fLockID = "";
			//9.0处理特殊订单
			if (!FillID.equals("")){
				DefState = "3";		//分发成功
				DefGiveID = GiveID;
				FillID = FillID;
				fLockID = FillID;
			}
			//9.1非卡&号订单时直接写入
			String s  = "";
			s = fc.SetSqlInsertStr(s, "fID", sGUID);
			s = fc.SetSqlInsertStr(s, "fCreateTime", "GETDATE()", false);
			s = fc.SetSqlInsertStr(s, "fAgentID", AgentID);
			s = fc.SetSqlInsertStr(s, "fAgetnRate", AgentRate);
			s = fc.SetSqlInsertStr(s, "fClientID", ClientID);
			s = fc.SetSqlInsertStr(s, "fOrderID", MerOrderNo);
			s = fc.SetSqlInsertStr(s, "fProductID", CardType);
			s = fc.SetSqlInsertStr(s, "fClientTime", SubmitTime, false);
			s = fc.SetSqlInsertStr(s, "fCardNo", CardNo);
			s = fc.SetSqlInsertStr(s, "fPassword", CardPassword);
			s = fc.SetSqlInsertStr(s, "fAccountID", FillAccount);
			s = fc.SetSqlInsertStr(s, "fPrice", Money);
			s = fc.SetSqlInsertStr(s, "fRate", ClientRate);
			s = fc.SetSqlInsertStr(s, "fGiveID", DefGiveID);
			s = fc.SetSqlInsertStr(s, "fGiveOrderID", FillID);
			s = fc.SetSqlInsertStr(s, "fOverTime", "GETDATE()", false);
			s = fc.SetSqlInsertStr(s, "fErrorCount", "0");
			s = fc.SetSqlInsertStr(s, "fState", DefState);
			s = fc.SetSqlInsertStr(s, "fNoticeState", "0");
			s = fc.SetSqlInsertStr(s, "fUserA", CustomizeA);
			s = fc.SetSqlInsertStr(s, "fUserB", CustomizeB);
			s = fc.SetSqlInsertStr(s, "fUserC", CustomizeC);
			s = fc.SetSqlInsertStr(s, "fNoticeURL", NoticeURL);
			s = fc.SetSqlInsertStr(s, "fNoticePage", NoticePage);
			s = fc.SetSqlInsertStr(s, "fLockID", fLockID);
			sql = "INSERT INTO CaOrder " + s;
			int n = dc.execute(sql);
			if (n < 1){
				System.out.print("[Payrequest]写入数据库时失败:" + sql);	
				return  "ERROR,系统忙,请稍后再试";
			}					
		}
		 
		//10.网银转向/线程分发/返回
		if (CardType.equals("15")){
			String s = WebPay.PayResult(sGUID, Money, BankID, ClientID);
			if (response != null && (s.toLowerCase().indexOf("http://") == 0)){
				response.sendRedirect(s);
				s = "正在转向,请稍等...";
			}
			return s; 
		}else{
			//当是等待分发状态时
			if (DefState.equals("1")){
				Thread t1 = new Thread(new allot.AllotThread(sGUID)); 
				t1.start(); 
			}
			//正常返回
			return "OK," + sGUID;
		}

		//返回

	}catch(Exception e){
		System.out.print("[Payrequest]接收订单时出错:" + e.toString());
		return  "ERROR,系统忙,请稍后再试";
	}
 	}

}
 
 
 