package check.v1;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Pattern;
 
import javax.servlet.ServletException; 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.DataConnect;
import common.fc;

/*
 * 接收客户下单请求 
 */

/**
 */
 public class OrderCheck extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	   static final long serialVersionUID = 1L;
	   private static final String CONTENT_TYPE = "text/html; charset=GBK";
	   private static DataConnect dc = new DataConnect("orderfill", false);
	   static int m_count = 0;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public OrderCheck() {
		super();
	} 
   	
	public void destroy() { 
		if (dc != null) {
			//dc.CloseConnect();
			dc = null;
		}
	}
	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(orderRequest(request, response));
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(orderRequest(request, response));
	}   	
	
	
	
	/**
	 * 验证规则-帐号：^\d{17}$,^\d{16}$,^\d{10}$,^\d{16}$,^\d{16}$
	 */
	public boolean validate(String number,String Nregex) {
		boolean b = false;
		String Nr[] = Nregex.split(",") ;
		for(int i=0; i<Nr.length; i++){
			if (!Nr[i].equals("")&&(Pattern.matches(Nr[i], number))) b = true ;
		}
		return b;
	}



	/*
	 * 验证订单
	 */
	public String orderRequest(HttpServletRequest request, HttpServletResponse response){
		String lockid = fc.getpv(request, "");
		String clientid = fc.getpv(request, "");
		return orderCheck(lockid, clientid);
	}
	
	public String orderCheck(String lockid, String clientid){
		try{
			//1.接收参数,必须
			smg("[验证订单]" + lockid);

			
			//2.参数合法性检查
			if (!lockid.equals("")) lockid = " and fLockID = '" + lockid + "'"; 
			if (!clientid.equals("")) clientid = " and fClientID = '" + clientid + "'"; 
			
			//3.取出订单
			String sql = " select top 100 * from AcOrderCache WITH (NOLOCK) where (1=1) " + lockid + " " + clientid;
			ResultSet rs = dc.query(sql);
			if (rs == null){
				dc.CloseResultSet(rs);
				return fc.getResultStr("ordercheck", "false", "取单失败");	
			} 
			while (rs.next()){
				String fCheckID = fc.getrv(rs, "fID", "");
				String fCreateTime = fc.getrv(rs, "fCreateTime", "");
				String fClientID = fc.getrv(rs, "fClientID", "");
				String fOrderID = fc.getrv(rs, "fOrderID", "");
				String fProductID = fc.getrv(rs, "fProductID", "");
				String fPrice = fc.getrv(rs, "fPrice", "");
				String fNumber = fc.getrv(rs, "fNumber", "");
				String fMoney = fc.getrv(rs, "fMoney", "");
				String fPlayName = fc.getrv(rs, "fPlayName", "");
				String fCountType = fc.getrv(rs, "fCountType", "");
				String fAccountType = fc.getrv(rs, "fAccountType", "");
				String fFillRange = fc.getrv(rs, "fFillRange", "");
				String fFillServer = fc.getrv(rs, "fFillServer", "");
				String fUserIP = fc.getrv(rs, "fUserIP", "");
				String fUserArea = fc.getrv(rs, "fUserArea","");
				String fClientTime = fc.getrv(rs, "fClientTime", "");
				String fInfo1 = fc.getrv(rs, "fInfo1", "");
				String fInfo2 = fc.getrv(rs, "fInfo2", "");
				String fInfo3 = fc.getrv(rs, "fInfo3", "");
				String fNoticeUrl = fc.getrv(rs, "fNoticeUrl", "");
				String fAgentID = fc.getrv(rs, "fAgentID", "");
				//
				String ss = String.valueOf((1000000 + Integer.valueOf(fCheckID)));
				String fID = fc.getTime("yyMMddHHmmss") + ss.substring(ss.length() - 6);
				OrderCheck oc = new OrderCheck();
				oc.orderCheck2("", fID, fCreateTime, fClientID, fOrderID, fProductID, fPrice, 
						fNumber, fMoney, fPlayName, fCountType, fAccountType, fFillRange, fFillServer, 
						fUserIP, fUserArea, fClientTime, fInfo1, fInfo2, fInfo3, fNoticeUrl, fAgentID, fCheckID);
				rs.next();
			}
			dc.CloseResultSet(rs);
			return fc.getResultStr("response", "true", "执行完成");
		}catch(Exception e){
			return fc.getResultStr("response", "false", "执行失败");
		}
	}

	/*
	 * 验证订单
	 */
public String orderCheck2(String fSource, String fID, String fCreateTime, String fClientID, String fOrderID, String fProductID, String fPrice, 
		String fNumber, String fMoney, String fPlayName, String fCountType, String fAccountType, String fFillRange, String fFillServer, 
		String fUserIP, String fUserArea, String fClientTime, String fInfo1, String fInfo2, String fInfo3, String fNoticeUrl, String AgentID, String fCheckID){
	try{
		System.out.println("----去掉不用了,如果看到这句话，说明调用有问题了----");
//		//4.取出客户密钥ok
//		String sql = "select top 1 fID, fAgentID, fKey from CoClient where (fId = '" + fClientID + "' or fFaceID = '" + fClientID + "') and fState = '0'";
//		ResultSet rs = dc.query(sql);
//		if ((rs == null) || (!rs.next())){
//			dc.CloseResultSet(rs);
//			return fc.getResultStr("response", "false", "无效的用户名或用户名没有被启用");	
//		} 
//		String key = fc.getrv(rs, "fKey", "");					//Key
//		String AgentID = fc.getrv(rs, "fAgentID", "");			//代理商ID
//		String ClientID = fc.getrv(rs, "fID", "");				//客户ID
//		dc.CloseResultSet(rs);
//		//
//		if (key.equals("")){
//			dc.CloseConnect();
//			return fc.getResultStr("response", "false", "商户验证KEY不能为空");		
//		}
//
//
//		//5.MD5校验ok
//		String s = fOrderID + fClientID + fProductID + fPlayName + fPrice + fNumber + fMoney + fNoticeUrl;
//		String sSource = fc.getMd5Str(s + key);
//		if (!fSign.toLowerCase().equals(sSource.toLowerCase())){ 
//			smg("source:" + s);
//			smg("md5:" + sSource + " <> " + fSign);
//			dc.CloseConnect();
//			return fc.getResultStr("response", "false", "MD5校验失败");
//		}
		
		String fDefState = "1", fNoticeState = "0", fCostState="1", fFillMsg = "";
		//6.检查商品合法性 
		//6.1是否存在当前商品
		String sql = "select top 1 * from AcProduct where fid = '" + fProductID + "' and fState = '0'";
		ResultSet rs = dc.query(sql);
		if((rs==null) || (rs!=null && !rs.next())){
			dc.CloseResultSet(rs);
			saveErrorOrder(fID, fCreateTime, fClientID, fOrderID, fProductID, fPrice, fNumber, fMoney, fPlayName, fCountType, fAccountType, fFillRange, fFillServer, 
					fUserIP, fUserArea, fClientTime, fInfo1, fInfo2, fInfo3, fNoticeUrl, "商品不存在或不可用", fCheckID);
			return fc.getResultStr("response", "false", "商品不存在或不可用");
		}
		String fFlowType = fc.getrv(rs, "fFlowType", "");
		fDefState = fc.getrv(rs, "fDefState", "");
		String fRate = fc.getrv(rs, "fRate", "");
		String fDefLevel = fc.getrv(rs, "fLevel", "");
		String fInsideID = fc.getrv(rs, "fInsideID", "");
		String fMultiple = fc.getrv(rs, "fMultiple", "");
		String fAccountLeng = fc.getrv(rs, "fAccountLeng", "");
		String fAccountChar = fc.getrv(rs, "fAccountChar", "");
		String fAccountExpr = fc.getrv(rs, "fAccountExpr", "");
		String fMaxMoney = fc.getrv(rs, "fMaxMoney", "");
		String fMinMoney = fc.getrv(rs, "fMinMoney", "");
		String fIsBlacklist = fc.getrv(rs, "fIsBlacklist", "");
		dc.CloseResultSet(rs);

		//6.2是否代理商商品
		sql = "select top 1 * from AcAgentProduct where fAgentID = '" + AgentID + "' and fProductID = '" + fProductID + "' and fState = '0'";
		rs = dc.query(sql);
		if((rs==null) || (rs!=null && !rs.next())){
			dc.CloseResultSet(rs);
			saveErrorOrder(fID, fCreateTime, fClientID, fOrderID, fProductID, fPrice, fNumber, fMoney, fPlayName, fCountType, fAccountType, fFillRange, fFillServer, 
					fUserIP, fUserArea, fClientTime, fInfo1, fInfo2, fInfo3, fNoticeUrl, "代理商品不存在或不可用", fCheckID);
			return fc.getResultStr("response", "false", "代理商品不存在或不可用");
		}
		String fAgentRate = fc.getrv(rs, "fRate", "");
		String fAgentClientRate = fc.getrv(rs, "fClientRate", "");
		String fAgentLevel = fc.getrv(rs, "fDefLevel", "");
		dc.CloseResultSet(rs);
		
		//6.3是否存在客户商品
		sql = "select top 1 * from AcClientProduct where fClientID = '" + fClientID + "' and fProductID = '" + fProductID + "' and fState = '0'";
		rs = dc.query(sql);
		if((rs==null) || (rs!=null && !rs.next())){
			dc.CloseResultSet(rs);
			saveErrorOrder(fID, fCreateTime, fClientID, fOrderID, fProductID, fPrice, fNumber, fMoney, fPlayName, fCountType, fAccountType, fFillRange, fFillServer, 
					fUserIP, fUserArea, fClientTime, fInfo1, fInfo2, fInfo3, fNoticeUrl, "客户商品不存在或不可用", fCheckID);
			return fc.getResultStr("response", "false", "客户商品不存在或不可用");
		}
		String fChargeType = fc.getrv(rs, "fChargeType", "");	//0=充值扣款,1=单次计费
		String fClientRate = fc.getrv(rs, "fRate", "");
		String fSingleMoney = fc.getrv(rs, "fSingleMoney", "");
		String fClientLevel = fc.getrv(rs, "fDefLevel", "");
		dc.CloseResultSet(rs);

		//确定优先级
		if (!fAgentLevel.equals("")) fDefLevel = fAgentLevel;
		if (!fClientLevel.equals("")) fDefLevel = fClientLevel;
		//确定折扣
		if (fRate.equals("")) fRate = "1";							//默认代理折扣
		if (fAgentClientRate.equals("")) fAgentClientRate = "1";	//默认客户折扣
		if (fAgentRate.equals("")) fAgentRate = fRate;				//代理折扣
		if (fClientRate.equals("")) fClientRate = fAgentClientRate;	//客户折扣 
//		//检查折扣关系:  平台>代理商>客户
//		if (Float.valueOf(fRate).floatValue() < Float.valueOf(fAgentRate).floatValue()) 
//			return fc.getResultStr("response", "false", "折扣设置不正确");
//		if (Float.valueOf(fAgentRate).floatValue() < Float.valueOf(fClientRate).floatValue()) 
//			return fc.getResultStr("response", "false", "折扣设置不正确");
		
		//7.检查订单是否符合接收规则
		//7.1黑名单
		if (fIsBlacklist.equals("0")){
			sql = "select top 1 * from AcBlacklist where fInsideID = '" + fInsideID + "' and fPlayName = '" + fPlayName + "' ";
			rs = dc.query(sql);
			if (rs!=null && rs.next()){
				dc.CloseResultSet(rs);
				saveErrorOrder(fID, fCreateTime, fClientID, fOrderID, fProductID, fPrice, fNumber, fMoney, fPlayName, fCountType, fAccountType, fFillRange, fFillServer, 
						fUserIP, fUserArea, fClientTime, fInfo1, fInfo2, fInfo3, fNoticeUrl, "当前账号被限制提交", fCheckID);
				return fc.getResultStr("response", "false", "当前账号被限制提交");
			}
			dc.CloseResultSet(rs);
		}
		//7.2金额倍数
		if (!fMultiple.equals("") && !fMultiple.equals("0")){
			if (Float.valueOf(fMoney).intValue() % Float.valueOf(fMultiple).intValue() != 0) {
				saveErrorOrder(fID, fCreateTime, fClientID, fOrderID, fProductID, fPrice, fNumber, fMoney, fPlayName, fCountType, fAccountType, fFillRange, fFillServer, 
						fUserIP, fUserArea, fClientTime, fInfo1, fInfo2, fInfo3, fNoticeUrl, "当前商品提交金额要求" + fMultiple + "的倍数", fCheckID);
				return fc.getResultStr("response", "false", "当前商品提交金额要求" + fMultiple + "的倍数");
			}
		}
		//7.3账号长度
		if (!fAccountLeng.equals("") && !fAccountLeng.equals("0")){
			int nAccountLeng = 0;
			String sCsf = "", sAccountLeng = "";
			try{
				sCsf = fAccountLeng.substring(0, 1);
				sAccountLeng = fAccountLeng.substring(1);
				nAccountLeng = Integer.valueOf(sAccountLeng).intValue();
			}catch(Exception en){
				nAccountLeng = 0;
			}
			if (sCsf.equals("=")){
				if ((nAccountLeng > 0)&&(nAccountLeng != fPlayName.length())) {
					saveErrorOrder(fID, fCreateTime, fClientID, fOrderID, fProductID, fPrice, fNumber, fMoney, fPlayName, fCountType, fAccountType, fFillRange, fFillServer, 
							fUserIP, fUserArea, fClientTime, fInfo1, fInfo2, fInfo3, fNoticeUrl, "账号长度不符合要求", fCheckID);
					return fc.getResultStr("response", "false", "账号长度不符合要求");
				}
			}else
			if (sCsf.equals("<")){
				if ((nAccountLeng > 0)&&(nAccountLeng >= fPlayName.length())) {
					saveErrorOrder(fID, fCreateTime, fClientID, fOrderID, fProductID, fPrice, fNumber, fMoney, fPlayName, fCountType, fAccountType, fFillRange, fFillServer, 
							fUserIP, fUserArea, fClientTime, fInfo1, fInfo2, fInfo3, fNoticeUrl, "账号长度不符合要求", fCheckID);
					return fc.getResultStr("response", "false", "账号长度不符合要求");
				}
			}else
			if (sCsf.equals(">")){
				if ((nAccountLeng > 0)&&(nAccountLeng <= fPlayName.length())) {
					saveErrorOrder(fID, fCreateTime, fClientID, fOrderID, fProductID, fPrice, fNumber, fMoney, fPlayName, fCountType, fAccountType, fFillRange, fFillServer, 
							fUserIP, fUserArea, fClientTime, fInfo1, fInfo2, fInfo3, fNoticeUrl, "账号长度不符合要求", fCheckID);
					return fc.getResultStr("response", "false", "账号长度不符合要求");
				}
			}else{
				try{
					nAccountLeng = Integer.valueOf(fAccountLeng).intValue();
				}catch(Exception en){
					nAccountLeng = 0;
				}						
				if (nAccountLeng < fPlayName.length()) {
					saveErrorOrder(fID, fCreateTime, fClientID, fOrderID, fProductID, fPrice, fNumber, fMoney, fPlayName, fCountType, fAccountType, fFillRange, fFillServer, 
							fUserIP, fUserArea, fClientTime, fInfo1, fInfo2, fInfo3, fNoticeUrl, "账号长度不符合要求", fCheckID);
					return fc.getResultStr("response", "false", "账号长度不符合要求");
				}
			}
		}
		//7.4账号字符
//		if (!fAccountChar.equals("")) {
//			for(int k=0; k<fPlayName.length(); k++){
//				if (fAccountChar.indexOf(fPlayName.substring(k, k + 1)) == -1){
//					dc.CloseConnect();
//					return fc.getResultStr("response", "false", "账号含有非法字符");
//				}
//			}
//		}
		//7.5最大金额
		if (!fMaxMoney.equals("") && !fMaxMoney.equals("0")){
			if (Float.valueOf(fMoney).floatValue() > Float.valueOf(fMaxMoney).floatValue() ){
				saveErrorOrder(fID, fCreateTime, fClientID, fOrderID, fProductID, fPrice, fNumber, fMoney, fPlayName, fCountType, fAccountType, fFillRange, fFillServer, 
						fUserIP, fUserArea, fClientTime, fInfo1, fInfo2, fInfo3, fNoticeUrl, "充值金额不能超过" + fMaxMoney + "", fCheckID);
				return fc.getResultStr("response", "false", "充值金额不能超过" + fMaxMoney + "");
			}
		}
		//7.6最小金额
		if (!fMinMoney.equals("") && !fMinMoney.equals("0")){
			if (Float.valueOf(fMoney).floatValue() < Float.valueOf(fMinMoney).floatValue() ){
				saveErrorOrder(fID, fCreateTime, fClientID, fOrderID, fProductID, fPrice, fNumber, fMoney, fPlayName, fCountType, fAccountType, fFillRange, fFillServer, 
						fUserIP, fUserArea, fClientTime, fInfo1, fInfo2, fInfo3, fNoticeUrl, "充值金额不能低于" + fMaxMoney + "", fCheckID);
				return fc.getResultStr("response", "false", "充值金额不能低于" + fMinMoney + "");
			}
		}
		//7.7账号名规则
		if (!fAccountExpr.equals("")){
			if (!validate(fPlayName, fAccountExpr)){
				saveErrorOrder(fID, fCreateTime, fClientID, fOrderID, fProductID, fPrice, fNumber, fMoney, fPlayName, fCountType, fAccountType, fFillRange, fFillServer, 
						fUserIP, fUserArea, fClientTime, fInfo1, fInfo2, fInfo3, fNoticeUrl, "账号名不符合要求", fCheckID);
				return fc.getResultStr("response", "false", "账号名不符合要求");
			}
		}


		
		//8.检查客户订单号是否已存在
//		sql = "select top 1 fid from AcOrder where fOrderID = '" + fOrderID + "' and fClientID = '" + fClientID + "'";
//		rs = dc.query(sql);
//		if (rs == null){
//			saveErrorOrder(fID, fCreateTime, fClientID, fOrderID, fProductID, fPrice, fNumber, fMoney, fPlayName, fCountType, fAccountType, fFillRange, fFillServer, 
//					fUserIP, fUserArea, fClientTime, fInfo1, fInfo2, fInfo3, fNoticeUrl, "客户订单号已存在", fCheckID);
//			return fc.getResultStr("response", "false", "客户订单号已存在");
//		}
//		if (rs.next()){
//			String s = fc.getrv(rs, "fState", "");
//			dc.CloseResultSet(rs);
//			saveErrorOrder(fID, fCreateTime, fClientID, fOrderID, fProductID, fPrice, fNumber, fMoney, fPlayName, fCountType, fAccountType, fFillRange, fFillServer, 
//					fUserIP, fUserArea, fClientTime, fInfo1, fInfo2, fInfo3, fNoticeUrl, "客户订单号已存在", fCheckID);
//			return fc.getResultStr("response", "false", "客户订单号已存在");
//		}
//		dc.CloseResultSet(rs);
		String ChangeMoney = String.valueOf(Float.valueOf(fMoney).floatValue() * Float.valueOf(fClientRate).floatValue());
		if (fChargeType.equals("1")){
			if (fSingleMoney.equals("")) fSingleMoney = ChangeMoney;	//即使是1,如果没有设置单笔扣款金额,将按折扣价扣款.
			ChangeMoney = fSingleMoney; 
		}
		
//		//9.扣费并保存订单(事务)
			//-----事务开始---------
			String fClientMoney = "0";
			fID = "";
			DataConnect dc2 = new DataConnect("orderfill", false);
			dc2.checkConnect();
			Statement stmt = null;
			try{
				//1.初始化
				stmt = dc2.getConnect().createStatement();
				if (stmt == null){
					dc2.CloseConnect();
					return fc.getResultStr("response", "false", "下单失败,初始化连接失败");
				}
				dc2.getConnect().setAutoCommit(false);		//开始事务,更改JDBC事务的默认提交方式
				
				//2.从客户账户中扣费
				int m = 0;
				int i = 0;
				do{
					//重取账户金额一次
					fClientMoney = "0";
					sql = "select * from CoClientMoney where fid = '" + fClientID + "'";
					rs = stmt.executeQuery(sql);
					if (rs!=null && rs.next())	fClientMoney = fc.getrv(rs, "fMoney", "0");
					rs.close();
					//如果余额不足,直接失败
					if (Float.valueOf(fClientMoney).floatValue() < Float.valueOf(ChangeMoney).floatValue()){
						dc2.getConnect().rollback();	
						stmt.close();
						dc2.CloseConnect();
						saveErrorOrder(fID, fCreateTime, fClientID, fOrderID, fProductID, fPrice, fNumber, fMoney, fPlayName, fCountType, fAccountType, fFillRange, fFillServer, 
								fUserIP, fUserArea, fClientTime, fInfo1, fInfo2, fInfo3, fNoticeUrl, "下单失败,额户余额不足", fCheckID);
						return fc.getResultStr("response", "false", "下单失败,额户余额不足:" + fClientMoney);
					}
					//
					sql = "update CoClientMoney set fMoney = fMoney - " + ChangeMoney + " " +
					" where fID = '" + fClientID + "' and fMoney - " + ChangeMoney + " >= 0 and fMoney = " + fClientMoney + "";
					m = stmt.executeUpdate(sql);
					i++;
					if (m !=1 ){
						smg("扣款失败,再次重试...");
						Thread.sleep(fc.ran(1, 3) * 1000);
					}
				}while((m != 1)&&(i < 5));

				if (m != 1){
					dc2.getConnect().rollback();	
					stmt.close();
					dc2.CloseConnect();
					return fc.getResultStr("response", "false", "下单失败,扣款失败,稍后重试");
				}
				
				//3.写资金变动表
				fID = fc.getGUID6("");
				String sis = "";
				sis = fc.SetSqlInsertStr(sis, "fID", fc.getGUID6(""));
				sis = fc.SetSqlInsertStr(sis, "fTime", "GETDATE()", false);
				sis = fc.SetSqlInsertStr(sis, "fAgentID", AgentID);
				sis = fc.SetSqlInsertStr(sis, "fClientID", fClientID);
				sis = fc.SetSqlInsertStr(sis, "fType", "0");
				sis = fc.SetSqlInsertStr(sis, "fOrderID", fID);
				sis = fc.SetSqlInsertStr(sis, "fMoney", ChangeMoney, false);
				sis = fc.SetSqlInsertStr(sis, "fBeforeMoney", fClientMoney, false);
				sis = fc.SetSqlInsertStr(sis, "fAfterMoney", fClientMoney + "-" + ChangeMoney, false);
				sis = fc.SetSqlInsertStr(sis, "fOperateId", "自动");
				sql = "insert into CoMoneyChange " + sis;
				m = stmt.executeUpdate(sql);
				if (m != 1){
					dc2.getConnect().rollback();	
					stmt.close();
					dc2.CloseConnect();
					return fc.getResultStr("response", "false", "下单失败,计费失败");
				}

				//4.写订单到数据库
				m_count = (m_count + 1) % 10;
				if (!fClientTime.equals("GETDATE()")) 
					fClientTime = "'" + fClientTime + "'";
				sis = "";
				sis = fc.SetSqlInsertStr(sis, "fID", fID);
				sis = fc.SetSqlInsertStr(sis, "fCreateTime", "GETDATE()", false);
				sis = fc.SetSqlInsertStr(sis, "fAgentID", AgentID);
				sis = fc.SetSqlInsertStr(sis, "fAgentRate", fAgentRate, false);
				sis = fc.SetSqlInsertStr(sis, "fClientID", fClientID);
				sis = fc.SetSqlInsertStr(sis, "fOrderID", fOrderID);
				sis = fc.SetSqlInsertStr(sis, "fProductID", fProductID);
				sis = fc.SetSqlInsertStr(sis, "fInsideID", fInsideID);
				sis = fc.SetSqlInsertStr(sis, "fFlowType", fFlowType);
				sis = fc.SetSqlInsertStr(sis, "fPrice", fPrice, false);
				sis = fc.SetSqlInsertStr(sis, "fNumber", fNumber, false);
				sis = fc.SetSqlInsertStr(sis, "fMoney", fMoney, false);
				sis = fc.SetSqlInsertStr(sis, "fLackMoney", fMoney, false);
				sis = fc.SetSqlInsertStr(sis, "fFactMoney", "0", false);
				sis = fc.SetSqlInsertStr(sis, "fCostMoney", ChangeMoney, false);	//新加的结算金额
				sis = fc.SetSqlInsertStr(sis, "fRate", fClientRate, false);
				sis = fc.SetSqlInsertStr(sis, "fPlayName", fPlayName);
				sis = fc.SetSqlInsertStr(sis, "fCountType", fCountType);
				sis = fc.SetSqlInsertStr(sis, "fAccountType", fAccountType);
				sis = fc.SetSqlInsertStr(sis, "fFillRange", fFillRange);
				sis = fc.SetSqlInsertStr(sis, "fFillServer", fFillServer);
				sis = fc.SetSqlInsertStr(sis, "fUserIP", fUserIP);
				sis = fc.SetSqlInsertStr(sis, "fUserArea",fUserArea);
				sis = fc.SetSqlInsertStr(sis, "fClientTime", fClientTime, false);
				sis = fc.SetSqlInsertStr(sis, "fLevel", fDefLevel, false);
				sis = fc.SetSqlInsertStr(sis, "fGiveID", "");
				sis = fc.SetSqlInsertStr(sis, "fGiveOrderID", fc.getGUID6("A"));
				sis = fc.SetSqlInsertStr(sis, "fGiveTime", "GETDATE()", false);
				sis = fc.SetSqlInsertStr(sis, "fOverTime", "GETDATE()", false);
				sis = fc.SetSqlInsertStr(sis, "fState", fDefState);
				sis = fc.SetSqlInsertStr(sis, "fNoticeState", "0");
				sis = fc.SetSqlInsertStr(sis, "fCostState", "1");
				sis = fc.SetSqlInsertStr(sis, "fErrorCount", "0", false);
				sis = fc.SetSqlInsertStr(sis, "fInfo1", fInfo1);
				sis = fc.SetSqlInsertStr(sis, "fInfo2", fInfo2);
				sis = fc.SetSqlInsertStr(sis, "fInfo3", fInfo3);
				sis = fc.SetSqlInsertStr(sis, "fSource", fSource);
				sis = fc.SetSqlInsertStr(sis, "fNoticeUrl", fNoticeUrl);
				sis = fc.SetSqlInsertStr(sis, "fFillMsg", fFillMsg);
				sis = fc.SetSqlInsertStr(sis, "fMemo", "");
				sis = fc.SetSqlInsertStr(sis, "fLockID", "");
				sis = fc.SetSqlInsertStr(sis, "fQueueID", m_count + "");
				sql = "insert into AcOrder " + sis;
				m = stmt.executeUpdate(sql);
//				int m = dc.execute(sql);
				if (m != 1){
					dc2.getConnect().rollback();	
					stmt.close();
					dc2.CloseConnect();
					return fc.getResultStr("response", "false", "下单失败,写入订单失败");
				}
				if ((m == 1) || (m == 2)){
					if (!fCheckID.equals(""))
						dc.execute("delete from AcOrderCache where fid = '" + fCheckID + "'");
				}				
				//5.提交事务
				dc2.getConnect().commit();
				stmt.close();
				dc2.CloseConnect();
				//分发
//				if (fDefState.equals("1")){
//					Thread t1 = new Thread(new AllotThread(fID, "")); 
//					t1.start(); 
//				}
				//
				String sData = "<sid>" + fID + "</sid>";
				return fc.getResultStr("response", sData, "true", "下单成功,订单号:" + fID);  
			}catch(Exception e){
				try{
					dc2.getConnect().rollback();
				}catch(Exception eee){
				}
				try{
					stmt.close();
				}catch(Exception eeee){
				}
				try{
					dc2.CloseConnect();
				}catch(Exception eeee){
				}
				e.printStackTrace();
				return fc.getResultStr("response", "false", "下单失败,请稍后重试");
			}
		//----事务结束----------

	}catch(Exception e){
		return fc.getResultStr("response", "false", "下单失败,接口运行异常");
	}
}
	//信息输出
public void smg(String s){
		System.out.print(s);
		return;
	}

public void saveErrorOrder(String fID, String fCreateTime, String fClientID, String fOrderID, String fProductID, String fPrice, 
		String fNumber, String fMoney, String fPlayName, String fCountType, String fAccountType, String fFillRange, String fFillServer, 
		String fUserIP, String fUserArea, String fClientTime, String fInfo1, String fInfo2, String fInfo3, String fNoticeUrl,  String fFillMsg){
	saveErrorOrder(fID, fCreateTime, fClientID, fOrderID, fProductID, fPrice, fNumber, fMoney, fPlayName, fCountType, fAccountType, fFillRange, fFillServer, 
			fUserIP, fUserArea, fClientTime, fInfo1, fInfo2, fInfo3, fNoticeUrl, fFillMsg, "");
}
public void saveErrorOrder(String fID, String fCreateTime, String fClientID, String fOrderID, String fProductID, String fPrice, 
		String fNumber, String fMoney, String fPlayName, String fCountType, String fAccountType, String fFillRange, String fFillServer, 
		String fUserIP, String fUserArea, String fClientTime, String fInfo1, String fInfo2, String fInfo3, String fNoticeUrl,  String fFillMsg, String fCheckID){
	//
	if(true)return;	//暂时不写主表
	//
	if (fClientTime.equals("")||fClientTime.equals("GETDATE()")) 
		fClientTime = "GETDATE()";
	else
		fClientTime = "'" + fClientTime + "'";
	//4.写订单到数据库
	m_count = (m_count + 1) % 10;
	String sis = "";
	sis = fc.SetSqlInsertStr(sis, "fID", fID);
	sis = fc.SetSqlInsertStr(sis, "fCreateTime", "GETDATE()", false);
	sis = fc.SetSqlInsertStr(sis, "fAgentID", "");
	sis = fc.SetSqlInsertStr(sis, "fAgentRate", "1", false);
	sis = fc.SetSqlInsertStr(sis, "fClientID", fClientID);
	sis = fc.SetSqlInsertStr(sis, "fOrderID", fOrderID);
	sis = fc.SetSqlInsertStr(sis, "fProductID", fProductID);
	sis = fc.SetSqlInsertStr(sis, "fInsideID", "");
	sis = fc.SetSqlInsertStr(sis, "fFlowType", "");
	sis = fc.SetSqlInsertStr(sis, "fPrice", fPrice, false);
	sis = fc.SetSqlInsertStr(sis, "fNumber", fNumber, false);
	sis = fc.SetSqlInsertStr(sis, "fMoney", fMoney, false);
	sis = fc.SetSqlInsertStr(sis, "fLackMoney", fMoney, false);
	sis = fc.SetSqlInsertStr(sis, "fFactMoney", "0", false);
	sis = fc.SetSqlInsertStr(sis, "fRate", "1", false);
	sis = fc.SetSqlInsertStr(sis, "fPlayName", fPlayName);
	sis = fc.SetSqlInsertStr(sis, "fCountType", fCountType);
	sis = fc.SetSqlInsertStr(sis, "fAccountType", fAccountType);
	sis = fc.SetSqlInsertStr(sis, "fFillRange", fFillRange);
	sis = fc.SetSqlInsertStr(sis, "fFillServer", fFillServer);
	sis = fc.SetSqlInsertStr(sis, "fUserIP", fUserIP);
	sis = fc.SetSqlInsertStr(sis, "fUserArea",fUserArea);
	sis = fc.SetSqlInsertStr(sis, "fClientTime", fClientTime, false);
	sis = fc.SetSqlInsertStr(sis, "fLevel", "0", false);
	sis = fc.SetSqlInsertStr(sis, "fGiveID", "");
	sis = fc.SetSqlInsertStr(sis, "fGiveOrderID", fc.getGUID6("A"));
	sis = fc.SetSqlInsertStr(sis, "fGiveTime", "GETDATE()", false);
	sis = fc.SetSqlInsertStr(sis, "fOverTime", "GETDATE()", false);
	sis = fc.SetSqlInsertStr(sis, "fState", "5");
	sis = fc.SetSqlInsertStr(sis, "fNoticeState", "0");
	sis = fc.SetSqlInsertStr(sis, "fCostState", "1");
	sis = fc.SetSqlInsertStr(sis, "fErrorCount", "0", false);
	sis = fc.SetSqlInsertStr(sis, "fInfo1", fInfo1);
	sis = fc.SetSqlInsertStr(sis, "fInfo2", fInfo2);
	sis = fc.SetSqlInsertStr(sis, "fInfo3", fInfo3);
	sis = fc.SetSqlInsertStr(sis, "fNoticeUrl", fNoticeUrl);
	sis = fc.SetSqlInsertStr(sis, "fFillMsg", fFillMsg);
	sis = fc.SetSqlInsertStr(sis, "fMemo", "");
	sis = fc.SetSqlInsertStr(sis, "fLockID", "");
	sis = fc.SetSqlInsertStr(sis, "fQueueID", m_count + "");
	String sql = "insert into AcOrder " + sis;
	int m = dc.execute(sql);
	if ((m == 1) || (m == 2)){
		if (!fCheckID.equals(""))
			dc.execute("delete from AcOrderCache where fid = '" + fCheckID + "'");
	}else
		fc.getResultStr("response", "false", "下单失败,写入订单失败");

}

}