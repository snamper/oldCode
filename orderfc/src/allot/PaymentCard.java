package allot;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.sql.ResultSet;
import java.util.regex.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.DataConnect;
import common.fc;

/**
 * Servlet implementation class for Servlet: PaymentStd
 *
 * web.servlet
 *   name="PaymentStd"
 *   display-name="PaymentStd" 
 *
 * web.servlet-mapping
 *   url-pattern="/PaymentStd"
 *  
 */
 public class PaymentCard extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	   static final long serialVersionUID = 1L;
	   private static final String CONTENT_TYPE = "text/html; charset=GBK"; 
	   static DataConnect dc = new DataConnect("orderfill", false);
	   static String m_sDate = "";
	   static String m_AutoIP = ""; 
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public PaymentCard() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(paymentStd(request, response));
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(paymentStd(request, response));
	}   	  	    
	
	/*  
	 * 发送充值请求
	 */
	public String paymentStd(HttpServletRequest request, HttpServletResponse response){
		//接收参数
		String fid = fc.getpv(request, "id");
		String fSoPlatformID = fc.getpv(request, "soclient");  //上次分发平台,用来定位下次分发平台
		String sign = fc.getpv(request, "sign").toUpperCase();
		
		//验证md5及参数合法性
		if (fid.equals("")) return "ERROR,订单号不能为空";
		if (!sign.equals(fc.getMd5Str(fid + "ohdt4w8gt489edy5ht3w9dg5r3t4sdgt4b").toUpperCase())) return "ERROR,md5校验失败";
		return paymentStd_(fid, fSoPlatformID);
	}
	
	
	/**
	 * 网游卡验证规则（如下）：
	 * n  :  表示需要验证卡号或者密码的数字个数
	 * 验证卡号的正则(Nregex):^\d{n}$
	 * 验证密码的正则(Pregex):^\d{n}$
	 * 
	 * 
	 * 移动验证规则-帐号：^\d{17}$,^\d{16}$,^\d{10}$,^\d{16}$,^\d{16}$
	 * 			         密码：^\d{18}$,^\d{17}$,^\d{8}$,^\d{21}$,^\d{17}$
	 */
	public boolean validate(String number,String password,String Nregex,String Pregex) {
		boolean b = false;
		String Nr[] = Nregex.split(",") ;
		String Pr[] = Pregex.split(",") ;
		for(int i=0; i<Nr.length; i++){
			if((Pattern.matches(Nr[i], number)||(Nr[i].equals(""))) && (Pattern.matches(Pr[i], password)||Pr[i].equals(""))){
				b = true ;
			}
		}
		return b ;
	}


	public String paymentStd_(String fid, String fSoPlatformID){
		try{
			//锁定订单
			String hostAddress = "allot|" + InetAddress.getLocalHost().toString();
			if (hostAddress.length() > 32) hostAddress = hostAddress.substring(0, 32);
			String sql = "update CaOrder set fLockID = '" + hostAddress + "' where fLockID = '' and fid = '" + fid + "' ";
			int n = dc.execute(sql);
			if (n != 1) return "ERROR,当前订单分发锁定失败";
			
			//查询订单信息
			sql = "select top 1 * from CaOrder where fid = '" + fid + "' and  fState in ('1','2','6')";
			ResultSet rs = dc.query(sql);
			if (rs == null) return "ERROR,查询订单失败";
			if (!rs.next()){
				dc.CloseResultSet(rs);
				System.out.print("[分发]" + fid + ",订单不能分发或已处理完成");
				return "OK,订单不能分发或已处理完成";					//订单Id不存在		
			}
			
			//取出信息
			String fClientID = fc.getrv(rs, "fClientID", "");				//客户ID
			String fCardNo = fc.getrv(rs, "fCardNo", "");					//卡号
			String fPassword = fc.getrv(rs, "fPassword", "");				//密码
			String fPrice = fc.getrv(rs, "fPrice", "");						//面值
			String fRate = fc.getrv(rs, "fRate", "");						//折扣
			String fProductID = fc.getrv(rs, "fProductID", "");				//卡类型
			String fLastGiveID = fc.getrv(rs, "fGiveID", "");				//上次分发平台
			String fState = fc.getrv(rs, "fState", "");						//订单状态
			dc.CloseResultSet(rs);
			
			
			//如果接口未指定上次分发平台,用订单中的分发平台来做分发依据
			if (fLastGiveID.indexOf("-") > 0)
				fLastGiveID = fLastGiveID.substring(0, fLastGiveID.length() - 3);
			if (fSoPlatformID.equals("")) fSoPlatformID = fLastGiveID;

			//特殊处理: 易宝骏网超时退单
			n = 0;
			if (fClientID.equals("yibao") && (fProductID.equals("03")) && (!fSoPlatformID.equals(""))){
				sql = "update CaOrder set fState = '5', fLockID = '', fNoticeState='0', fUserC = 'retreatment' " +
						" ,   fFillTime=GETDATE(), foverTime=GETDATE(), fErrorCount = 0 , fMoney=0, fFactMoney=null" +
						" where fid = '" + fid + "' and fState = '3'";
				n = dc.execute(sql);
				System.out.print("[分发]" + fid + ",易宝骏网超时失败");
				return "OK,处理完成,易宝超时订单";
			}
			
			//每天清减一次分发量, 以免溢出出错.
			if (!m_sDate.equals(fc.getTime("yyyyMMdd"))){
				sql = "update CaRules set fMaxValue = 0, fValue = 0 ";
				dc.execute(sql);
				m_sDate =  fc.getTime("yyyyMMdd");
			}
			
			//自动分发
			//订算出可以发送的商户:分发百分比-(分发量/分发总量*100).
			sql = "SELECT * FROM (SELECT *, fScale - fValue / (fMaxValue + " + fPrice + ") * 100 AS fn FROM CaRules " +
				" WHERE (fProductId = '" + fProductID + "') " +
						" AND (fClientID = '" + fClientID + "' OR isnull(fClientID, '') = '')" +
						" AND (isnull(fSoPlatformID,'') = '" + fSoPlatformID + "' OR ('" + fSoPlatformID + "' = '')) ) " +
				" a INNER JOIN CaPlatform c ON a.fPlatformID = c.fID WHERE (a.fState = '0') ORDER BY fClientID desc, a.flevel desc, a.fn DESC";
			//System.out.print(sql);
			rs = dc.query(sql);
			if (rs == null) {
				dc.execute("update CaOrder set fState='6', fLockID = '', fFillMsg = '计算分发规则时失败', fFillTime=GETDATE(), foverTime=GETDATE(), " +
						" fNoticeState='0', fErrorCount = 0 where fid = '" + fid + "'");
				System.out.print("[分发]" + fid + ",计算分发规则时失败");
				return "ERROR,计算分发规则时失败";
			}

			if (!rs.next()){
					dc.CloseResultSet(rs);
					String s = "";
					if (fSoPlatformID.equals("")) s = ",fFillMsg='无可用的充值通道'";
					if (fState.equals("2")) s = ",fFillMsg = '等待超时'";
					dc.execute("update CaOrder set fState = '5', fLockID = '', fNoticeState='0', fFillTime=GETDATE()," +
							" foverTime=GETDATE(), fMoney=0, fErrorCount = 0 " + s + " where fid = '" + fid + "'");
					System.out.print("[分发]" + fid + ",无可用的充值通道");
					//线程通知
					Thread t1 = new Thread(new NoticeThread(fid, "")); 
					t1.start(); 
					//
					return "ERROR,无可用的充值通道";
			}
			
			//查询出分发参数			
			String sID = fc.getrv(rs, "fID", "");					//分发规则记录ID
			String sGiveID = fc.getrv(rs, "fPlatformID", "");
			String sUrl = fc.getrv(rs, "fUrl", "");
			String sParam = fc.getrv(rs, "fParam", "");
			String sSign = fc.getrv(rs, "fSign", "");
			String sOkStr = fc.getrv(rs, "fOkStr", "");
			String sReUrl = fc.getrv(rs, "fReUrl", "");			
			String sOutTime = fc.getrv(rs, "fOutTime", "");			
			String sProductID = fc.getrv(rs, "fProductId", "");			
			String fToClientID = fc.getrv(rs, "fClientID", "");		//分发的客户ID,用来更新分发量,空或不空
			String fCardExpr = fc.getrv(rs, "fCardExpr", "");
			String fPassExpr = fc.getrv(rs, "fPassExpr", "");
			if (sOutTime.equals("")) sOutTime = "60";
			dc.CloseResultSet(rs);
			
			//对密码进行解密
			if (fPassword.indexOf("(@_@)") == 0){
				fPassword = common.EncryptsE.uncrypt(fPassword.substring(5), "64Qz9cWPiH0B25CM7IoFGjETmfAbsOxh");
				System.out.print("新解密:" + fPassword);
			}
			
			//检查是否符合分发条件
			if (!validate(fCardNo, fPassword, fCardExpr, fPassExpr)){
				dc.execute("update CaOrder set fLockID = '', fGiveID = '" + sGiveID + "', fovertime=GETDATE() + 0.0000116 * " + sOutTime + ", fState='6'," +
						" fFillMsg='卡密不符合分发规则," + sOutTime + "秒后下一通道', fErrorCount = 0 where fid = '" + fid + "'");
				System.out.print("[分发]" + fid + ",卡密不符合分发规则");
				return "ERROR,卡密不符合分发规则";
			}

			
			//如果分发平台为self, 则置状态为等待充值,当取走时置充值中,并写充值日志(用挂机ID和订单号来识别) 
			if (sUrl.equals("#")){
				String fFillID = fc.getGUID6("C"); 								//正常卡
				dc.execute("update CaOrder set fState='2', fLockID = '', fGiveID = '" + sGiveID + "-" + sProductID + "', " +
						"  fGiveOrderID='" + fFillID + "', foverTime=GETDATE() + 0.0000116 * " + sOutTime + " where fid = '" + fid + "'");
				System.out.print("[分发]" + fid + ",分发成功");
				
				//更新分发总量
				sql = "update CaRules set fMaxValue = fMaxValue + " + fPrice + " where (fProductId = '" + sProductID + "') and isnull(fClientID, '') = '" + fToClientID + "' and fState = '0'";
				dc.execute(sql);

				//累加分发量
				sql = "update CaRules set fValue = fValue + " + fPrice + " where fId = '" + sID + "' ";
				dc.execute(sql);

				return "OK,分发成功[" + sGiveID + "]";
			}
			
			//默认充值代码
			String sFillCode = sProductID;
			
			//特殊的转换处理
			if (sGiveID.indexOf("xiaoye") > -1){
				if (sProductID.equals("01")) sFillCode = "5";		//01	神州行充值卡
				if (sProductID.equals("02")) sFillCode = "6";		//02	盛大骏网一卡通
				if (sProductID.equals("03")) sFillCode = "7";		//03	骏网一卡通
				if (sProductID.equals("04")) sFillCode = "34";	//04	联通充值卡
				if (sProductID.equals("05")) sFillCode = "28";	//05	QQ卡
				if (sProductID.equals("06")) sFillCode = "22";	//06	征途游戏卡
				if (sProductID.equals("07")) sFillCode = "27";	//07	久游一卡通
				if (sProductID.equals("08")) sFillCode = "31";	//08	完美一卡通
				if (sProductID.equals("09")) sFillCode = "23";	//09	网易一卡通
				if (sProductID.equals("10")) sFillCode = "32";	//10	魔兽世界点卡
				if (sProductID.equals("11")) sFillCode = "24";	//11	搜狐一卡通
				if (sProductID.equals("12")) sFillCode = "36";	//11	电信一卡通
			}
			

			//如果是xiaoyie2, 则对卡号和密码进行md9加密
			if (sGiveID.indexOf("xiaoye2") == 0){
				try{
					fCardNo = callmd9.Md9.md9(fCardNo, "13649632604512456123132164231351213543213543213523163535123163031566554126543215");
					fPassword = callmd9.Md9.md9(fPassword, "13649632604512456123132164231351213543213543213523163535123163031566554126543215");
				}catch(Exception ee){
					System.out.print(ee.toString());
				}
			}
			
			//用替换法计算出URL及sign值
			sSign = sSign.replace("[fuserid]", fClientID);			//客户ID
			sSign = sSign.replace("[id]", fid);						//订单号
			sSign = sSign.replace("[fcardid]", fCardNo);			//卡号
			sSign = sSign.replace("[fpassword]", fPassword);		//密码
			sSign = sSign.replace("[fprice]", fPrice);				//面值
			sSign = sSign.replace("[f15id]", sFillCode);			//充值代码
			sSign = sSign.replace("[ftypeid]", sProductID);			//商品大类
			//
			sParam = sParam.replace("[id]", fid);
			sParam = sParam.replace("[fuserid]", fClientID);
			sParam = sParam.replace("[freurl]", sReUrl);
			sParam = sParam.replace("[fcardid]", fCardNo);
			sParam = sParam.replace("[fpassword]", fPassword);
			sParam = sParam.replace("[fprice]", fPrice);
			sParam = sParam.replace("[f15id]", sFillCode);
			sParam = sParam.replace("[ftypeid]", sProductID);
			sParam = sParam.replace("[sRate]", fRate);
			
			sParam = sParam.replace("[sign]", fc.getMd5Str(sSign).toUpperCase());
						
				
			//发送充值请求
			String s = fc.SendDataViaPost(sUrl, sParam, "");
			System.out.println("[发送]" + fid + ">>" + sUrl + "," + sParam + ">>" + s);
			if ((s.indexOf(sOkStr) == 0)||(s.indexOf("error_orderid_exist") > -1)) {	//发送ok或订单号已存在
				
				//更新订单表
				sql = "update CaOrder set fState='3', fLockID = '', fGiveID = '" + sGiveID + "-" + sProductID + "', fErrorCount = 0 where fid = '" + fid + "'";
				n = dc.execute(sql);
				if (n != 1){
					System.out.println("[更新失败]" + fid + ">>" + sql);
				}

				//更新分发总量
				sql = "update CaRules set fMaxValue = fMaxValue + " + fPrice + " where (fProductId = '" + sProductID + "') and isnull(fClientID, '') = '" + fToClientID + "' and fState = '0'";
				dc.execute(sql);

				//累加分发量
				sql = "update CaRules set fValue = fValue + " + fPrice + " where fId = '" + sID + "' ";
				dc.execute(sql);
				
				//
				System.out.println("[分发]" + fid + ">>发送成功" );
				return "OK,分发成功[" +sGiveID + "]";		//发送成功
			} else {
				//如果分发失败,应向备用通道分发
				System.out.println("[分发]" + fid + ">>分发失败," + s + "");
				if ((s.indexOf("ERROR0082") > -1) || (s.trim().equals(""))){
					dc.execute("update CaOrder set fLockID = '', fGiveID = '" + sGiveID + "', fovertime=GETDATE() + 0.0000116 * " + sOutTime + ", fState='6'," +
							" fFillMsg='分发" + s + "失败," + sOutTime + "秒后尝试下一充值通道', fErrorCount = 0 where fid = '" + fid + "'");
//					//线程重发
//					Thread t1 = new Thread(new AllotThread(id, "", 8080)); 
//					t1.start(); 
					//
					return "ERROR,分发" + s + "失败," + sOutTime + "秒后尝试下一充值通道";		//发送失败
				}else{
					dc.execute("update CaOrder set fLockID = '', fstate = '5', fFillMsg='" + s + "', fGiveID = '" + sGiveID + "', fMoney=0," +
							" fFillTime=GETDATE(), fovertime=GETDATE(), fNoticeState='0', fErrorCount = 0 where fid = '" + fid + "'");
					return "ERROR,分发失败:" + s;		//卡信息有误
				}
					
			}

		}catch(Exception e){
			e.printStackTrace();
			return "ERROR,分发接口出错,请速联系技术!";
		}
		
	}
	
}