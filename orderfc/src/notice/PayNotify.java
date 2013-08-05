package notice;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.DataConnect;
import common.fc;
/**
 * Servlet implementation class for Servlet: PayNotify
 *
 * web.servlet
 *   name="PayNotify"
 *   display-name="PayNotify" 
 *
 * web.servlet-mapping
 *   url-pattern="/PayNotify"
 *  
 */
 public class PayNotify extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	   static final long serialVersionUID = 1L;
	   private static final String CONTENT_TYPE = "text/html; charset=GBK";
	   static DataConnect dc = null;	
	   static int ConnectCount = 0;
	   static String cards = "";

    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public PayNotify() {
		super();
		ConnectCount = ConnectCount + 1;
		System.out.print("[PayNotify]新的实例,当前共有个" + ConnectCount + "连接数");
		if (dc == null){
			dc = new DataConnect("orderfill", false);		//公用一个连接
			System.out.print("[PayNotify]第一个实例,初始化数据库连接");
		}
	} 
	
	/* (non-Javadoc)
	 * @see javax.servlet.Servlet#destroy()
	 */
	public void destroy() {
		ConnectCount = ConnectCount - 1;
		if (ConnectCount <= 0){
			dc.CloseConnect();
			dc = null;
			System.out.print("[PayNotify]实例数为0,已关闭数据库连接");
		}
		super.destroy();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(payNotify(request, response));
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(payNotify(request, response));
	}   	  	    
	
	/*
	 * 生成通知url并发送,如果发送失败, 写入重发列表
	 */
	public String payNotify(HttpServletRequest request, HttpServletResponse response)  {
		//1.接收参数 OK
		String id = fc.getpv(request, "id");							//系统订单号
		String MerchantID = fc.getpv(request, "MerchantID");			//客户编号
		String MerOrderNo = fc.getpv(request, "MerOrderNo");			//客户订单号
		String type = fc.getpv(request, "type");						//类型(空|queryurl)
		String sign = fc.getpv(request, "sign").toUpperCase();
		
		//2.检验合法性 
		if (id.equals("")&&MerOrderNo.equals("")) return "ERROR,订单号不能为空";
		if (!id.equals(""))
			if (!sign.equals(fc.getMd5Str(id + "44fecb4739dfae183e38ee990e70275b").toUpperCase())){
				System.out.print("通知:" + id + ">>MD5验证失败" );
				return "ERROR,MD5验证失败";		//md5有误
			}
		if (!MerOrderNo.equals(""))
			if (!sign.equals(fc.getMd5Str(MerchantID + MerOrderNo + "ma1OtIHujkECqQwRx8B7fbNiSA4PLyTX").toUpperCase())){
				System.out.print("通知:" + id + ">>MD5验证失败" );
				return "ERROR,MD5验证失败";		//md5有误
			}
		//3.通知
		return payNotify_(id, MerOrderNo, MerchantID, type);
	}
	
	
	/*
	 * 通知函数
	 */
	public String payNotify_(String id, String oid, String cid, String type)  {
		String sql = "";
		try{
			System.out.print("通知:" + id + "," + type);
			//1.查询订单
			if (!id.equals("")){
				sql = "select top 1 CoAgent.fReMaxTime, CoAgent.fReInterval, CoAgent.fActualMoney, " +
				" CoClient.fKey, CoClient.fFaceID, CaOrder.* from CaOrder, CoClient, CoAgent " +
				  "where CaOrder.fid = '" + id + "' and CaOrder.fClientID = CoClient.fID and CoAgent.fID = CaOrder.fAgentID ";
			}
			if (!oid.equals("")){
				type = "queryurl";
				sql = "select top 1 CoAgent.fReMaxTime, CoAgent.fReInterval, CoAgent.fActualMoney, " +
				" CoClient.fKey, CoClient.fFaceID, CaOrder.* from CaOrder, CoClient, CoAgent " +
				  "where CaOrder.fOrderid = '" + oid + "' and CaOrder.fClientID = CoClient.fID and CoAgent.fID = CaOrder.fAgentID ";
			}
			ResultSet rs = dc.query(sql); 
			if (rs == null) return "ERROR,查询订单时失败";
			if (!rs.next()){
				dc.CloseResultSet(rs);
				System.out.print("通知:" + id + ">>未查询到订单" );
				return "ERROR,未查询到订单";    
			}
			
			//2.取出数据
			String PayOrderNo = fc.getrv(rs, "fid", "");		  	//系统订单号
			String PayResult = fc.getrv(rs, "fState", "");	  		//支付结果
			String MerchantID = fc.getrv(rs, "fFaceID", "");  		//接口ID
			String ClientID = fc.getrv(rs, "fClientID", "");  		//商户ID
			if (MerchantID.equals("")) MerchantID = ClientID; 		//如果接口ID为空,默认为商户ID				
			String MerOrderNo = fc.getrv(rs, "fOrderId", "");  		//商户订单号
			String CardType = fc.getrv(rs, "fProductID", "");  		//卡类型
			String FactMoney = fc.getrv(rs, "fMoney", "0");  		//实际金额
			String Price = fc.getrv(rs, "fPrice", ""); 				//提交面额
			String CustomizeA = fc.getrv(rs, "fUserA", "");  		//自定义A
			String CustomizeB = fc.getrv(rs, "fUserB", "");  		//自定义B
			String CustomizeC = fc.getrv(rs, "fUserC", "");  		//自定义B
			String CardNo = fc.getrv(rs, "fCardNo", "");			//卡号
			String Key = fc.getrv(rs, "fKey", "");					//KEY
			String ReturnURL = fc.getrv(rs, "fNoticeURL", "");		//通知地址,记录中优先于客服表中的通知地址
			String ReturnPage = fc.getrv(rs, "fNoticePage", "");	//通知页面
			String AccountId = fc.getrv(rs, "fAccountId", "");		//用于显示的订单号
			String PayTime = fc.getrv(rs, "ffilltime", "");		//支付时间
			String FillMsg = fc.getrv(rs, "fFillMsg", "");			//错误信息
			String ActualMoney = fc.getrv(rs, "fActualMoney", "");	//是否按实际金额返回
			String AgentID = fc.getrv(rs, "fAgentID", "");			//代理商ID
			String ReInterval = fc.getrv(rs, "fReInterval", "");	//秒
			String ReMaxTime = fc.getrv(rs, "fReMaxTime", "");		//分
			//
			if (ReInterval.equals("")) ReInterval = "60";
			if (ReMaxTime.equals("")) ReMaxTime = "60";
			//
			dc.CloseResultSet(rs);
			
			if (!oid.equals("")){
				if (!ClientID.equals(cid)){
					return  "ERROR,订单号或商户ID不正确";
				}
			}

			//3.在这里处理移动面值问题: 当实际金额大于面值时, 按面值返回
			if (("," + ActualMoney + ",").indexOf("," + CardType + ",") > -1){	
					if (Float.valueOf(FactMoney).floatValue() > Float.valueOf(Price).floatValue()){
						sql = "update CaOrder set fMoney=fPrice, fFactMoney=fPrice * fRate " +
							" where fid='" + id+ "' and  fNoticeState <> '2'";
						int n = dc.execute(sql);
						if (n == 1)	System.out.print("[移动]按面值金额结算:" + id);
					}
				}
			
			//4.无返回地址时
			if (!type.equals("wynotice") && !type.equals("queryurl") && ReturnURL.equals("")){
				dc.execute("UPDATE CaOrder SET fNoticeState = '2',  fOverTime = GETDATE() WHERE fid = '" + id + "'");
				System.out.print("通知:" + id + ">>订单号不正确,或订单正在处理中,或非通知订单" );
				return "OK,处理非通知订单完成";				
			}

			
			//5.如果不是支付成功或支付失败, 则不去中止通知
			if (!PayResult.equals("4") && !PayResult.equals("5")){
				System.out.print("通知:" + id + ">>订单状态不是支付成功或支付失败: " + PayResult );
				return "ERROR,订单尚未处理完成";
			}

			//6.如果是支付失败,得到映射后的错误信息
			String sMsg = "";
			if (PayResult.equals("5") && (!FillMsg.equals(""))){
				//取出对应错误信息
				sql = "select * from CaErrorInfo where fId = '" + FillMsg + "'";
				rs = dc.query(sql);
				if (rs != null){
					if (rs.next()){
						sMsg = fc.getrv(rs, "fMsg", "");
					}else{
						sql = "insert into CaErrorInfo (fId, fTypeId, fMsg) values('" + FillMsg + "', '" + CardType + "','<新状态>')";
						dc.execute(sql);
					}
					dc.CloseResultSet(rs);
				}
				//如果没有取到,插入新记录
			}
			if (!sMsg.equals("<新状态>")) 
				FillMsg = sMsg;
			else
				FillMsg = "";
			
			//转换充值结果
			if (PayResult.equals("4")) PayResult = "true";
			if (PayResult.equals("5")) PayResult = "false";
			
			//7.查询代理接口参数,生成通知URL
			String sPayOrderNo = "PayOrderNo";
			String sMerchantID = "MerchantID";
			String sMerOrderNo = "MerOrderNo";
			String sCardType = "CardType";
			String sCardNo = "CardNo";
			String sFactMoney = "FactMoney";
			String sPayResult = "PayResult";
			String sAccountId = "AccountId";
			String sCustomizeA = "CustomizeA";
			String sCustomizeB = "CustomizeB";
			String sCustomizeC = "CustomizeC";
			String sFillMsg = "ErrorMsg";
			String sPayTime = "PayTime";
			String sSign = "sign";
			String sOkStr = "OK";
			sql = "select * from CoAgentParam where fAgentID = '" + AgentID + "' and fType = 'CARD'";
			rs = dc.query(sql);
			while (rs!=null && rs.next()){
				String sOld = fc.getrv(rs, "fOldParam", "");
				String sNew = fc.getrv(rs, "fNewParam", "");
				if (sOld.equals("PayOrderNo")) sPayOrderNo = sNew; 
				if (sOld.equals("MerchantID")) sMerchantID = sNew; 
				if (sOld.equals("MerOrderNo")) sMerOrderNo = sNew;
				if (sOld.equals("CardType")) sCardType = sNew;
				if (sOld.equals("CardNo")) sCardNo = sNew;
				if (sOld.equals("FactMoney")) sFactMoney = sNew;
				if (sOld.equals("PayResult")) sPayResult = sNew;
				if (sOld.equals("AccountID")) sAccountId = sNew;
				if (sOld.equals("CustomizeA")) sCustomizeA = sNew; 
				if (sOld.equals("CustomizeB")) sCustomizeB = sNew;
				if (sOld.equals("CustomizeC")) sCustomizeC = sNew; 
				if (sOld.equals("ErrorMsg")) sFillMsg = sNew;
				if (sOld.equals("PayTime")) sPayTime = sNew;
				if (sOld.equals("sign")) sSign = sNew;
				if (sOld.equals("OK_STR")) sOkStr = sNew;  			//回应给客户的下单成功标志
				
			}
			dc.CloseResultSet(rs); 	

			//MD5
//			String sMd5 = fc.getMd5Str(PayOrderNo + MerchantID + MerOrderNo + CardNo + CardType + FactMoney + PayResult
//					+ CustomizeA + CustomizeB + CustomizeC + PayTime + Key).toUpperCase(); 		
			String sMd5 = fc.getMd5Str(MerchantID + MerOrderNo + CardNo + CardType + FactMoney + AccountId + PayResult
					+ CustomizeA + Key).toUpperCase(); 		
			
			//组合
			String sData = sPayOrderNo + "=" + PayOrderNo 
				+ "&" + sMerchantID + "=" + MerchantID 
				+ "&" + sMerOrderNo + "=" + MerOrderNo 
				+ "&" + sCardNo + "=" + CardNo
				+ "&" + sCardType + "=" + CardType 
				+ "&" + sFactMoney + "=" + FactMoney 
				+ "&" + sPayResult + "=" + PayResult 
				+ "&" + sAccountId + "=" + AccountId
				+ "&" + sCustomizeA + "=" + CustomizeA 
				+ "&" + sCustomizeB + "=" + CustomizeB 
				+ "&" + sCustomizeC + "=" + CustomizeC 
				+ "&" + sPayTime + "=" + URLEncoder.encode(PayTime) 
				+ "&" + sFillMsg + "=" + URLEncoder.encode(FillMsg) 
				+ "&" + sSign + "=" + sMd5;

			//8.根据操作类型进行对应操作
			if (type.equals("queryurl"))  return ReturnURL + "?" + sData;
			if (type.equals("queryresult"))	return fc.SendDataViaPost(ReturnURL, sData, "");
			if (type.equals("querymd5"))
				return "source=" + PayOrderNo + MerchantID + MerOrderNo + CardNo + CardType + FactMoney + PayResult
				+ CustomizeA + CustomizeB + CustomizeC + PayTime + Key + ",md5=" + sSign;
			if (type.equals("wynotice"))	//网银跳转串
				if (ReturnPage.indexOf("?") > -1)
					return ReturnPage + "&" + sData;
				else
					return ReturnPage + "?" + sData;
			
			//9.通知客户
			String sError = "";
			if (type.equals("directly")){
				sError = fc.SendDataViaPost(ReturnURL, sData, "");
				if (sError.equals("")){
					System.out.print("尝试GET方式:" + PayOrderNo);
					sError = fc.SendDataViaGet(ReturnURL + "?" + sData);
				}
				if (sError.length() > 32) sError = sError.substring(0, 32);
				System.out.print("通知:" + PayOrderNo + ">>" + sError);
			}

			//10.如果通知失败, 写入重发通知列表
			String s = "";
			if ((!sError.equals("")) && (sError.toUpperCase().indexOf(sOkStr) == 0)){
				System.out.print("通知:" + PayOrderNo + "," + sError);
				sql = "update CaOrder set fNoticeState = '2', fLockID = '' where fid = '" + id + "'";
				dc.execute(sql);
				s = "OK,通知成功";
			}else{
				String ss = "";
				ss = fc.SetSqlInsertStr(ss, "fID", id);
				ss = fc.SetSqlInsertStr(ss, "fClientID", ClientID);
				ss = fc.SetSqlInsertStr(ss, "fEndTime", "GETDATE() + 0.0007 * " + ReMaxTime, false);			//分
				ss = fc.SetSqlInsertStr(ss, "fOverTime", "GETDATE()", false);		//秒
				ss = fc.SetSqlInsertStr(ss, "fData", ReturnURL + "?" + sData);
				ss = fc.SetSqlInsertStr(ss, "fOKStr", sOkStr);
				ss = fc.SetSqlInsertStr(ss, "fResult", "");
				ss = fc.SetSqlInsertStr(ss, "fLockID", "");
				ss = fc.SetSqlInsertStr(ss, "fReInterval", ReInterval);
				sql = "insert into CaReNotice " + ss;
				int n = dc.execute(sql);
				if (n == 1 || n == -2){
					sql = "update CaOrder set fNoticeState = '1', fLockID = '' where fid = '" + id + "'";
					dc.execute(sql);
				}else{
					sql = "update CaOrder set fNoticeState = '0', fLockID = '' where fid = '" + id + "'";
					dc.execute(sql);
				}
				//
				System.out.print("通知:" + PayOrderNo + ">>加入重发列表(" + n + ")");
				//
				s = "OK,已加入重发列表(" + sError + ")";
			}
			return s;
			
		}catch(Exception e){
			System.out.print(sql);
			e.printStackTrace();
			return "ERROR,通知模块出错";
		}
		
	}
		
}