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
 * @web.servlet
 *   name="PayNotify"
 *   display-name="PayNotify" 
 *
 * @web.servlet-mapping
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
		String oid = fc.getpv(request, "oid");							//客户订单号
		String cid = fc.getpv(request, "cid");							//客户ID
		String source = fc.getpv(request, "source");					//来源
		String type = fc.getpv(request, "type");						//类型(空|queryurl)
		String sign = fc.getpv(request, "sign").toUpperCase();
		
		//2.检验合法性 
		if ((id.equals("")&&oid.equals(""))) return fc.getResultStr("query", "false", "订单号不能为空");
		if (!id.equals(""))
			if (!sign.equals(fc.getMd5Str(id + "44fecb4739dfae183e38ee990e70275b").toUpperCase())){
				System.out.print("通知:" + id + ">>MD5验证失败" );
				return fc.getResultStr("query", "false", "MD5验证失败");		//md5有误
			}
		if (!oid.equals(""))
			if (!sign.equals(fc.getMd5Str(oid + cid + "2mZbHsip9GIe7jrVX3O16JTAwgzFd4CY").toUpperCase())){
				System.out.print("通知:" + oid + ">>MD5验证失败" );
				return fc.getResultStr("query", "false", "MD5验证失败");		//md5有误
			}
		//3.通知
		return payNotify_(id, oid, cid, type);
	}
	
	
	/*
	 * 通知函数
	 */
	public String payNotify_(String id, String oid, String cid, String type)  {
		String sql = "";
		try{
			
			//1.查询订单
			if (!id.equals("")){
				sql = "select top 1 CoAgent.fReMaxTime, CoAgent.fReInterval, CoAgent.fActualMoney, " +
				" CoClient.fKey, CoClient.fFaceID, AcOrder.* from AcOrder, CoClient, CoAgent " +
				  "where AcOrder.fid = '" + id + "' and AcOrder.fClientID = CoClient.fID and (CoAgent.fID = AcOrder.fAgentID or AcOrder.fAgentID = '') ";
			}
			if (!oid.equals("")){
				type = "queryurl";	//客户查询时,强制查询通知url
				sql = "select top 1 CoAgent.fReMaxTime, CoAgent.fReInterval, CoAgent.fActualMoney, " +
				" CoClient.fKey, CoClient.fFaceID, AcOrder.* from AcOrder, CoClient, CoAgent " +
				  "where AcOrder.fOrderID = '" + oid + "' and AcOrder.fClientID = CoClient.fID and (CoAgent.fID = AcOrder.fAgentID or AcOrder.fAgentID = '') ";
			}
			ResultSet rs = dc.query(sql); 
			if (rs == null) return  fc.getResultStr("query", "false", "查询订单时失败");
			if (!rs.next()){
				dc.CloseResultSet(rs);
				System.out.print("通知:" + id + ">>未查询到订单" );
				return fc.getResultStr("query", "false", "未查询到订单"); 
			}
			
			//2.取出数据
			String PayOrderNo = fc.getrv(rs, "fid", "");		  	//系统订单号
			String PayResult = fc.getrv(rs, "fState", "");	  		//充值结果
			String MerchantID = fc.getrv(rs, "fFaceID", "");  		//接口ID
			String ClientID = fc.getrv(rs, "fClientID", "");  		//商户ID
			//if (MerchantID.equals("")) 
			MerchantID = ClientID; 		//如果接口ID为空,默认为商户ID				
			String MerOrderNo = fc.getrv(rs, "fOrderId", "");  		//商户订单号
			String ProductID = fc.getrv(rs, "fProductID", "");  		//商品类型
			String fInsideID = fc.getrv(rs, "fInsideID", "");  		//商品类型
			String FactMoney = fc.getrv(rs, "fFactMoney", "0");  	//充值金额
			String Money = fc.getrv(rs, "fMoney", "0");  			//金额
			String CustomizeA = fc.getrv(rs, "fInfo1", "");  		//自定义A
			String CustomizeB = fc.getrv(rs, "fInfo2", "");  		//自定义B
			String CustomizeC = fc.getrv(rs, "fInfo3", "");  		//自定义B
			String fSource = fc.getrv(rs, "fSource", "");  			//来源			
			String PlayName = fc.getrv(rs, "fPlayName", "");		//账号
			String Key = fc.getrv(rs, "fKey", "");					//KEY
			String ReturnURL = fc.getrv(rs, "fNoticeURL", "");		//通知地址,记录中优先于客服表中的通知地址
			String AccountId = fc.getrv(rs, "fAccountId", "");		//用于显示的订单号
			String PayTime = fc.getrv(rs, "ffilltime", "");		    //支付时间
			String FillMsg = fc.getrv(rs, "fFillMsg", "");			//错误信息
			String AgentID = fc.getrv(rs, "fAgentID", "");			//代理商ID
			String ReInterval = fc.getrv(rs, "fReInterval", "");	//秒
			String ReMaxTime = fc.getrv(rs, "fReMaxTime", "");		//分
			//
			if (ReInterval.equals("")) ReInterval = "60";
			if (ReMaxTime.equals("")) ReMaxTime = "60";
			//
			dc.CloseResultSet(rs);
			
			//
			if (!oid.equals("")){
				if (!ClientID.equals(cid)){
					return fc.getResultStr("query", "false", "订单号或商户ID不正确");
				}
			}
			
			//5.如果不是支付成功或支付失败, 则不去中止通知
			if (!PayResult.equals("4") && !PayResult.equals("5")){
				System.out.print("通知:" + id + ">>订单状态不是充值成功或充值失败: " + PayResult );
				return fc.getResultStr("query", "false", "订单尚未处理完成");
			}

			//处理退款
			
			//7.查询代理接口参数,生成通知URL
			//查询出转换的参数名, 如果没有定义, 则用原参数名
			String vID = "sid";
			String vState = "ste";
			String vClientID = "cid";
			String vProductID = "pid";
			String vOrderID = "oid";
			String vPlayName = "pn";
			String vMoney = "fm";
			String vReUrl = "ru";
			String vInfo1 = "info1";
			String vInfo2 = "info2";
			String vInfo3 = "info3";
			String vSource = "source";
			String vErrorMsg = "em";
			String vFillTime = "ft";
			String vsign = "sign";
			String vOkStr = "success";
			String vErrorStr = "fail";
			//
			sql = "select * from CoAgentParam where fAgentID = '" + AgentID + "' and fType = 'ACCOUNT_REQUEST'";
			rs = dc.query(sql);
			while (rs!=null && rs.next()){
				String sOld = fc.getrv(rs, "fOldParam", "");
				String sNew = fc.getrv(rs, "fNewParam", "");
				if (sOld.equals("sid")) vID = sNew; 
				if (sOld.equals("ste")) vState = sNew; 
				if (sOld.equals("cid")) vClientID = sNew;
				if (sOld.equals("pid")) vProductID = sNew;
				if (sOld.equals("oid")) vOrderID = sNew; 
				if (sOld.equals("pn")) vPlayName = sNew;
				if (sOld.equals("fm")) vMoney = sNew;
				if (sOld.equals("ru")) vReUrl = sNew;
				if (sOld.equals("info1")) vInfo1 = sNew; 
				if (sOld.equals("info2")) vInfo2 = sNew;
				if (sOld.equals("info3")) vInfo3 = sNew; 
				if (sOld.equals("source")) vSource = sNew; 
				if (sOld.equals("em")) vErrorMsg = sNew; 
				if (sOld.equals("ft")) vFillTime = sNew; 
				if (sOld.equals("sign")) vsign = sNew;
				if (sOld.equals("OK_STR")) vOkStr = sNew;  			//回应给客户的下单成功标志
				if (sOld.equals("ERROR_STR")) vErrorStr = sNew;
			}
			dc.CloseResultSet(rs);	

			//处理部分充值的返回值
			if (!Money.equals(FactMoney) && PayResult.equals("4")) 
				FillMsg = "账号部分充值";

			//转换充值信息
			String sMsg = "";
			if (PayResult.equals("5") || FillMsg.equals("账号部分充值")){
				//取出对应错误信息
				sql = "select * from AcErrorInfo where fId = '" + FillMsg + "' and fTypeID = '" + fInsideID + "'";
				rs = dc.query(sql);
				if (rs != null){
					if (rs.next()){
						sMsg = fc.getrv(rs, "fMsg", "");
					}else{
						sql = "insert into AcErrorInfo (fId, fTypeId, fMsg) values('" + FillMsg + "', '" + fInsideID + "','<新状态>')";
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

			
			//充值结果
			if (PayResult.equals("4")) PayResult = "0";
			if (PayResult.equals("5")) PayResult = "1";
			

			String noticeMoney = FactMoney;
			
			//对财付通通知时的金额特殊修改、通知金额为订单金额，不是已冲金额
			if("caift".equals(ClientID)){
				noticeMoney = Money;
			}
			//MD5
			String sMd5Source = PayOrderNo + PayResult + MerchantID  + ProductID + MerOrderNo + PlayName + noticeMoney + Key;
			String sMd5 = fc.getMd5Str(sMd5Source).toUpperCase(); 		
			
			
			//组合
			String sData = vID + "=" + PayOrderNo 
				+ "&" + vState + "=" + PayResult 
				+ "&" + vClientID + "=" + MerchantID 
				+ "&" + vProductID + "=" + ProductID
				+ "&" + vOrderID + "=" + MerOrderNo 
				+ "&" + vPlayName + "=" + PlayName 
				+ "&" + vMoney + "=" + noticeMoney 
				+ "&" + vInfo1 + "=" + CustomizeA 
				+ "&" + vInfo2 + "=" + CustomizeB 
				+ "&" + vInfo3 + "=" + CustomizeC 
				+ "&" + vFillTime + "=" + URLEncoder.encode(PayTime) 
				+ "&" + vErrorMsg + "=" + URLEncoder.encode(FillMsg) 
				+ "&" + vsign + "=" + sMd5;


			//返回客户查询
			if (!oid.equals("")){
				sData = "<" + vID + ">" + PayOrderNo + "</" + vID + ">"
				+ "<" + vState + ">" + PayResult + "</" + vState + ">"
				+ "<" + vClientID + ">" + MerchantID + "</" + vClientID + ">"
				+ "<" + vProductID + ">" + ProductID + "</" + vProductID + ">" 
				+ "<" + vOrderID + ">" + MerOrderNo + "</" + vOrderID + ">" 
				+ "<" + vPlayName + ">" + PlayName + "</" + vPlayName + ">"
				+ "<" + vMoney + ">" + noticeMoney + "</" + vMoney + ">"
				+ "<" + vInfo1 + ">" + CustomizeA + "</" + vInfo1 + ">"
				+ "<" + vInfo2 + ">" + CustomizeB + "</" + vInfo2 + ">"
				+ "<" + vInfo3 + ">" + CustomizeC + "</" + vInfo3 + ">"
				+ "<" + vFillTime + ">" + URLEncoder.encode(PayTime) + "</" + vFillTime + ">" 
				+ "<" + vErrorMsg + ">" + URLEncoder.encode(FillMsg) + "</" + vErrorMsg + ">"
				+ "<" + vsign + ">" + sMd5 + "</" + vsign + ">";
				return fc.getResultStr("query", sData, "true", "查询成功");
			}

			//8.根据操作类型进行对应操作
			if (type.equals("queryurl"))  return ReturnURL + "?" + sData;
			if (type.equals("queryresult")){
				return fc.SendDataViaPost(ReturnURL, sData, "");
			}
			if (type.equals("querymd5")){
				return "source=" + sMd5Source + ",md5=" + sMd5;
			}
			
			//9.通知客户
			String sError = "";
			if (type.equals("directly")){
				if (!ReturnURL.equals("")){
					sError = fc.SendDataViaPost(ReturnURL, sData, "");
					if (sError.equals("")){
						System.out.print("尝试GET方式:" + PayOrderNo);
						sError = fc.SendDataViaGet(ReturnURL + "?" + sData);
					}
					if (sError.length() > 32) sError = sError.substring(0, 32);
				}else
					sError = "URL地址为空,不发送通知";
				System.out.print("通知:" + PayOrderNo + ">>" + sError);
			}

			//10.如果通知失败, 写入重发通知列表
			String s = "";
			if ((!sError.equals("")) && (sError.toUpperCase().indexOf(vOkStr.toUpperCase()) == 0)){
				sql = "update AcOrder set fNoticeState = '2', fLockID = '' where fid = '" + id + "'";
				int n = dc.execute(sql);
				return fc.getResultStr("query", "true", "通知成功");
			}else{
				
				/*
				 * 添加时间：2011年11月25日13:08:25
				 * 添加原因：通知时,无通知地址时,不加入重发列表.并置通知状态为自取通知  5:自取通知
				 */
				if("".equals(ReturnURL)){
					sql = "update AcOrder set fNoticeState = '5', fLockID = '' where fid = '" + id + "'";
					dc.execute(sql);
					return fc.getResultStr("query", "true", "更新为自取通知");
				}else{
					
					String ss = "";
					ss = fc.SetSqlInsertStr(ss, "fID", id);
					ss = fc.SetSqlInsertStr(ss, "fClientID", ClientID);
					ss = fc.SetSqlInsertStr(ss, "fEndTime", "GETDATE() + 0.0007 * " + ReMaxTime, false);			//分
					ss = fc.SetSqlInsertStr(ss, "fOverTime", "GETDATE()", false);									//秒
					ss = fc.SetSqlInsertStr(ss, "fData", ReturnURL + "?" + sData);
					ss = fc.SetSqlInsertStr(ss, "fOKStr", vOkStr);
					ss = fc.SetSqlInsertStr(ss, "fResult", "");
					ss = fc.SetSqlInsertStr(ss, "fLockID", "");
					ss = fc.SetSqlInsertStr(ss, "fSource", fSource);
					ss = fc.SetSqlInsertStr(ss, "fReInterval", ReInterval);
					sql = "insert into AcReNotice " + ss;
					int n = dc.execute(sql);
					if (n == 1 || n == -2){
						sql = "update AcOrder set fNoticeState = '1', fLockID = '' where fid = '" + id + "'";
						dc.execute(sql);
					}
					//
					System.out.print("通知:" + PayOrderNo + ">>加入重发列表(" + n + ")");
					//
					if (type.equals("directly"))
						return fc.getResultStr("query", "false", "通知失败(" + sError + ")");
					else
						return fc.getResultStr("query", "true", "已加入通知列表");
				}

			}

			
		}catch(Exception e){
			System.out.print(sql);
			e.printStackTrace();
			return fc.getResultStr("query", "false", "通知模块出错");
		}
		
	}
		
}