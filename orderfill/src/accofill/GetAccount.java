package accofill;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.*;

/*
 * 从账号池中, 返回可充值账号
 */

/**
 * Servlet implementation class for Servlet: GetAccount
 *
 * @web.servlet
 *   name="GetAccount"
 *   display-name="GetAccount" 
 *
 * @web.servlet-mapping
 *   url-pattern="/GetAccount"
 *  
 */
 public class GetAccount extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	   static final long serialVersionUID = 1L;
	   private static final String CONTENT_TYPE = "text/html; charset=GBK";
	   static DataConnect dc = new DataConnect("orderfill", false);
	   static int ConnectCount = 0;
	   static byte[] lock = new byte[0]; // 特殊的instance变量
	   static boolean isrekeyi = false;
	   static boolean isrerun = false;
	   static int AppCount = 0;
	   static int isrun = 0;
	   static String version = "";		//充值程序版本,用于更新
	   static String updateapp = ""; 	//用于指定哪些挂机进行更新
	   
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public GetAccount() {
		super();
		ConnectCount = ConnectCount + 1;
		System.out.print("[poolfill.GetAccount]新的实例,当前共有个" + ConnectCount + "实例");
//		if (dc == null){
//			dc = new DataConnect();		//公用一个连接
//			System.out.print("[poolfill.GetAccount]第一个实例,初始化数据库连接");
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
			System.out.print("[poolfill.GetAccount]实例数为0,已关闭数据库连接");
		}
		super.destroy();
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(getAccount(request, response));
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(getAccount(request, response));
	}   	  	    
	
	/*
	 * 取号接口: 单取号, 卡取号:用卡面值对账号或进行过滤
	 */
	public String getAccount(HttpServletRequest request, HttpServletResponse response){
		try{
			//接收参数
			String sGiveClient = fc.getpv(request, "giveid");			//*取单通道ID: 如: tianshi-01,19pay-05,enlian-02等
			String sign = fc.getpv(request, "sign");
			//
			String sFillID = fc.getpv(request, "fillid");				//充值订单号,如果想充值本地号,需要加本地号模式.
			String sMinPrice = fc.getpv(request, "minprice");   		//当前面值,如果为空,不进行优先匹配及面值过滤
			String sProgramId = fc.getpv(request, "appid");				//充值程序Id, 这个应是唯一的
			String sOperType = fc.getpv(request, "skipzt");				//是否跳过征途相同账号检查
			String sQueueID = fc.getpv(request, "queue");				//队列号

			//
			smg("[" + sProgramId + "]取号:" + sGiveClient + "," + sFillID + "," + sMinPrice);

			//校验合法性
//			if (!sign.equals(fc.getMd5Str("A74B766AEAE474EAAEF8465A6A0A4885"))) return "ERROR_MD5";
			if (sGiveClient.equals("")) return gr("false", "giveid参数不能为空"); 
			if (!sMinPrice.equals("") && !fc.isPlusFloat(sMinPrice)) return gr("false", "minprice参数不是数值");
			
			//2.取卡面值规则
			ResultSet rs = null;
			sGiveClient = "'" + fc.replace(sGiveClient, ",", "','") + "'"; 
			sProgramId = sProgramId + "." + fc.getTime("HHmmss") + "." + fc.ran(10000, 99999);
			
			//取号金额规则
			String sPriceRule = "";
			String sProductID = fc.getString2(sGiveClient, "-", "'");//.substring(sGiveClient.length() - 2);
			if (!sMinPrice.equals("")){
				String sql = "select top 1 * from AcMoneyRule where fInsideID = '" + sProductID + "'" +
						" and fMinPrice <= " + sMinPrice + " and fMaxPrice >= " + sMinPrice + " and fState = '0'";
				rs = dc.query(sql); 
				if (rs != null && rs.next()){
					sPriceRule = fc.getrv(rs, "fMoneyList", "");
				}
				dc.CloseResultSet(rs);
				sPriceRule = fc.replace(sPriceRule, ";", " OR ");
				sPriceRule = fc.replace(sPriceRule, ",", " AND ");
				sPriceRule = fc.replace(sPriceRule, "=", " fLackMoney=");
				sPriceRule = fc.replace(sPriceRule, ">", " fLackMoney>");
				sPriceRule = fc.replace(sPriceRule, "<", " fLackMoney<");
				if (!sPriceRule.equals("")) System.out.print("[取卡规则]" + sPriceRule);
			}
			
			//获取订单
			String sLockSql = "";
			if (!sQueueID.equals("")) sQueueID = " (fQueueID = '" + sQueueID + "') AND ";
			
			//直接锁定符合的状态, 因为要哪个平台充值,已经分配好, like my% 表示my的系列充值
			sLockSql = "SELECT TOP 1 fid FROM AcOrder " +				
			  			" WHERE " +sQueueID + " (fGiveTime > GETDATE()) and (fLockId = '') " +
			  			" AND (fState = '2') and (fGiveId in (" + sGiveClient + ")) ";
			if (!sMinPrice.equals("")) sLockSql = sLockSql + " AND (" + sMinPrice + " <= fLackMoney)";
			if (!sPriceRule.equals("")) sLockSql = sLockSql + " AND (" + sPriceRule + ")";
			sLockSql = sLockSql + " ORDER BY fLevel desc, fCreateTime";
			String sql = "update AcOrder set fLockId = '" + sProgramId + "' where fid = (" + sLockSql + ") ";
			int n = dc.execute(sql);
			smg("[" + sProgramId + "]锁定" + n);

			//取出数据
			String money = "", playname = "", insideid = "", productid = "";
			String sData = "", fid = "", fGiveOrderID = "", fGiveTime = ""; 
			if (n == 1){
				sql = "SELECT TOP 1 * FROM AcOrder where fLockId = '" + sProgramId + "' and fState = '2'";
				rs = dc.query(sql);
				if ((rs != null) && (rs.next())){
					//取出数据
					fid = fc.getrv(rs, "fid", "");
					insideid = fc.getrv(rs, "fInsideId", "");					//商品类型
					playname = fc.getrv(rs, "fplayname", "");					//玩家账号
					money = fc.getrv(rs, "fLackMoney", "0");					//未充金额 
					//String price = fc.getrv(rs, "fPrice", "0");				//面值
					String counttype = fc.getrv(rs, "fcounttype", "");			//计费类型
					String accounttype = fc.getrv(rs, "faccounttype", "");		//账号类型
					String fillrange = fc.getrv(rs, "ffillrange", "");			//充值服务器,之后在接收订单时统一信息
					String fillserver = fc.getrv(rs, "ffillserver", "");		//充值区域,之后在接收订单时统一信息
					String userip = fc.getrv(rs, "fuserip", "");				//客户IP
					String userarea = fc.getrv(rs, "fUserArea", "");			//客户地区
					String giveid = fc.getrv(rs, "fGiveID", "");				//本次分发平台, 空,19pay,zf,hd
					//fGiveOrderID = fc.getrv(rs, "fGiveOrderID", "");			//订单号
					fGiveTime = fc.getrv(rs, "fGiveTime", "");					//分发时间
					String lockid = fc.getrv(rs, "fLockId", "");				//锁定ID
					if (sFillID.equals("")){
						if (giveid.indexOf("-kck") > 0)
							sFillID = fc.getGUID6("K");
						else
							sFillID = fc.getGUID6("A");
					}
					System.out.print(fid + "," + sFillID + "," + playname + "," + money + "," + fGiveTime);
					
					//是否取到了订单
					if (!lockid.equals(sProgramId)){
						dc.CloseResultSet(rs);
						return gr("false", "锁定失败,锁定ID已改变");
					}
					
					
					//读取对接商品ID
					String fillcode = "", fCountTypeFc="", fAccountTypeFc="", fFillRangeFc="", fFillServerFc="";
					String  fCountType="", fAccountType="", fFillRange="", fFillServer="";
					money = fc.replace(money, ".00", "");
					sql = "select * from AcRelation where fproductid = '" + productid + "' " +
							" and fPlatformID = '" + giveid + "' and fState = '0' ";
					ResultSet rs2 = dc.query(sql);
					if ((rs2 != null) && (rs2.next())){
						fillcode = fc.getrv(rs2, "fFillCode", "");				//默认的充值代码
						fCountTypeFc = fc.getrv(rs2, "fCountTypeFc", "");		//计费类型转换
						fAccountTypeFc = fc.getrv(rs2, "fAccountTypeFc", "");	//账号类型转换
						fFillRangeFc = fc.getrv(rs2, "fFillRangeFc", "");		//充值区域转换
						fFillServerFc = fc.getrv(rs2, "fFillServerFc", "");		//充值服务器转换
						fCountType = fc.getrv(rs2, "fCountTypeCode", "");		//充值代码转换
						fAccountType = fc.getrv(rs2, "fAccountTypeCode", "");	//充值代码转换
						fFillRange = fc.getrv(rs2, "fFillRangeCode", "");		//充值代码转换
						fFillServer = fc.getrv(rs2, "fFillServerCode", "");		//充值代码转换
					}
					dc.CloseResultSet(rs2);
					//对商品对接代码进行转换
					if ((fillcode+fCountTypeFc+fAccountTypeFc+fFillRangeFc+fFillServerFc+fCountType+fAccountType+fFillRange+fFillServer).equals("")){
						fCountTypeFc = fc.replace(fCountTypeFc, " ", "");
						fAccountTypeFc = fc.replace(fAccountTypeFc, " ", "");
						fFillRangeFc = fc.replace(fFillRangeFc, " ", "");
						fFillServerFc = fc.replace(fFillServerFc, " ", "");
						String code = fc.getString2("\r\n" + fCountTypeFc + "\r\n", "\r\n" + counttype + "=", "\r\n");
						if (!code.equals("")) fillcode = code;
						code = fc.getString2("\r\n" + fAccountTypeFc + "\r\n", "\r\n" + accounttype + "=", "\r\n");
						if (!code.equals("")) fillcode = code;
						code = fc.getString2("\r\n" + fFillRangeFc + "\r\n", "\r\n" + fillrange + "=", "\r\n");
						if (!code.equals("")) fillcode = code;
						code = fc.getString2("\r\n" + fFillServerFc + "\r\n", "\r\n" + fillserver + "=", "\r\n");
						if (!code.equals("")) fillcode = code;
						//区服代码转换
						fCountType = fc.replace(fCountType, " ", "");
						fAccountType = fc.replace(fAccountType, " ", "");
						fFillRange = fc.replace(fFillRange, " ", "");
						fFillServer = fc.replace(fFillServer, " ", "");
						fCountType = fc.replace(fCountType, "\r\n", ",");
						fAccountType = fc.replace(fAccountType, "\r\n", ",");
						fFillRange = fc.replace(fFillRange, "\r\n", ",");
						fFillServer = fc.replace(fFillServer, "\r\n", ",");
						code = fc.getString2("," + fCountType + ",", "," + counttype + "=", ",");
						if (!code.equals("")) counttype = code;
						code = fc.getString2("," + fAccountType + ",", "," + accounttype + "=", ",");
						if (!code.equals("")) accounttype = code;
						code = fc.getString2("," + fFillRange + ",", "," + fillrange + "=", ",");
						if (!code.equals("")) fillrange = code;
						code = fc.getString2("," + fFillServer + ",", "," + fillserver + "=", ",");
						if (!code.equals("")) fillserver = code;
					}
					
					//调用号段处理模块
					String section = "";
					if ((giveid.indexOf("waihu-05") > -1)){
						section = accocheck.HunanQBCheck.getSectionNo(fid, playname, money);
						if (section.indexOf("ERROR") > -1){
							System.out.print("[湖南号段分析]" + section);
							section = "";
							sql = "update AcOrder set fLockID='',fGiveID = '" + giveid + "', fGivetime=GETDATE(), fState='1'" +
									" , fErrorCount = 0 where fid = '" + fid + "'";
							dc.execute(sql);
							dc.CloseResultSet(rs);
							return gr("false", "跳过订单,不符合号段规则");
						}
					}
					
					// 
					sData = "<accoorder>" +
							"<giveid>" + giveid + "</giveid>" +			//当前指定的充值方式,以完成按优先充值
							"<id>" + fid + "</id>" +
							"<fillid>" + sFillID + "</fillid>" +
							"<insideid>" + insideid + "</insideid>" +
							"<playname>" + playname + "</playname>" +
							//"<price>" + price + "</price>" +   
							"<money>" + money + "</money>" +
							"<counttype>" + counttype + "</counttype>" +
							"<accounttype>" + accounttype + "</accounttype>" +
							"<fillrange>" + fillrange + "</fillrange>" +	
							"<fillserver>" + fillserver + "</fillserver>" +
							"<userip>" + userip + "</userip>" +
							"<userarea>" + userarea + "</userarea>" +
							"<fillcode>" + fillcode + "</fillcode>" +
							"<section>" + section + "</section>" +
							"</accoorder>"; 	
					sData = fc.getResultStr("getaccount", sData, "true", "取单成功");
					
					//如果是征途, 查询当前锁定的订单是否已有相同账号名被锁定,如果有, 恢复订单, 不取走
					String IsSkip = "true";
					if ((!sOperType.equals("skip_zt")) && (insideid.equals("06"))){
						sql = "select top 1 fid as count from AcOrder where fplayname = '" + playname + "' and fState = '3' and fInsideid = '06'";
						rs2 = dc.query(sql);
						if (rs2!=null && rs2.next()){
							smg("[" + sProgramId + "]发现已有分发成功且账号相同的征途订单,取消锁定");
							IsSkip = "false";
						}
						dc.CloseResultSet(rs2);
					}
					
					//更新状态
					if (IsSkip.equals("true")){ 
						sql = "update AcOrder set fState = '3',fGiveOrderID = '" + sFillID + "'  where fid = '" + fid + "'";
						n = dc.execute(sql);
						if (n != 1){
							sql = "update AcOrder set fLockId = '' where fid = '" + fid + "' and (fState = '2')"; 	//出错时恢复
							dc.execute(sql);
							sData = gr("false", "LOCK_FALSE");  //锁定失败
						}
					}else{
						sql = "update AcOrder set " +
						" fMemo = '取消分发[发现已有分发成功且账号相同的征途订单]'," +
						" fState = '6', fLockId = '' " +
						" where fID = '" + fid + "' and (fState ='2')";
					dc.execute(sql);
					sData = gr("false", "取消征途订单锁定");  //取消失败
					}
						
				}else{
					sql = "update AcOrder set fLockId = '' where fLockId = '" + sProgramId + "' and fState ='2'";
					dc.execute(sql);
					sData = gr("false", "锁定失败");	//锁定失败
				}
				dc.CloseResultSet(rs);
			}else
				if (n == -1){
					sql = "update AcOrder set fLockId = '' where fLockId = '" + sProgramId + "' and (fState = '2')";
					dc.execute(sql);
					sData = gr("false", "锁定失败");	//锁定失败
					try{
						if (dc.getConnectError().indexOf("Error establishing socket") > -1 ) sData = gr("false", "数据库连接失败");
					}catch(Exception eee){
						
					}
				}else
					sData = gr("false", "暂无订单处理");	//无可处理订单
			

	
			//返回数据
			if (fid.equals("")) fid = sData;
			smg("[" + sProgramId + "]返回:" + fid + "," + sFillID);
			return sData;
			
		}catch(Exception e){
			isrun = 0;
			isrerun = false;
			e.printStackTrace();
			return gr("false", "取单出错");
		}
		
	}
	
	private String gr(String sState, String sMsg) {
		// TODO Auto-generated method stub
		return fc.getResultStr("getaccount", sState, sMsg);
	}

	public void smg(String s){
		System.out.print(s);
		return;
	}
}