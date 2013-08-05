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
 * Servlet implementation class for Servlet: AccountAllot
 *
 * @web.servlet
 *   name="AccountAllot"
 *   display-name="AccountAllot"  
 *
 * @web.servlet-mapping
 *   url-pattern="/AccountAllot"
 *  
 */
 public class AccountAllot extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	   static final long serialVersionUID = 1L;
	   private static final String CONTENT_TYPE = "text/html; charset=GBK"; 
	   static DataConnect dc = new DataConnect("orderfill", false);
	   static String m_sDate = "";
	   static String m_AutoIP = ""; 
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public AccountAllot() {
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
		String toclient = fc.getpv(request, "toclient");  //指定平台,指定分发给哪平台
		String soclient = fc.getpv(request, "soclient");  //来源平台,指明上次分发的平台,用来定位下次分发平台
		String sign = fc.getpv(request, "sign").toUpperCase();
		System.out.println("[分发] id=" + id + "," + toclient + "," + soclient);
//		if (!sign.equals(funcejb.getMd5Str(id + "789").toUpperCase())) return "ERROR,md5签名不正确";	
		String s = paymentStd(id, toclient, soclient);
		return s;
	}
	
	
	public String paymentStd(String fid, String toclient, String soclient){
		int n1 = (int)System.currentTimeMillis();
		String s = paymentStd_(fid, toclient, soclient);
		int n2 = (int)System.currentTimeMillis(); 
		System.out.print("[分发]" + fid + ",用时:" + (n2 - n1) + "ms"); 
		return s;
	}
	
	/*  
	 * 账号充值分发
	 */ 
	public String paymentStd_(String fid, String toclient, String soclient){
		try{
			System.out.println("[分发] fid=" + fid + "," + toclient + "," + soclient);
			//1.验证md5及参数合法性OK
			if (fid.equals("")) return "ERROR,订单号不能为空";
			
			//3.锁定订单
			smg("锁定订单");
			String ss = "FA_" + fc.getTime("HHmmss_") + fc.ran(100000, 999999);
			int n = dc.execute("update AcOrder set fLockID = '" + ss + "' where fid = '" + fid + "' and fLockID = '' " +
					" and ((fGiveTime < GETDATE() and fState = '2') or (fGiveTime >= GETDATE() - 1 and fState = '1')) ");
			if (n<=0){
				System.out.print("[分发]" + fid + ",锁定失败:订单号不存在或不符合分发要求");
				return "ERROR,锁定失败:订单号不存在或不符合分发要求";
			}

			//2.查询订单信息
			smg("查询订单信息");
			String sql = "select top 1 * from AcOrder where fLockID = '" + ss + "' and fState in ('1', '2')";
			ResultSet rs = dc.query(sql);
			if (rs == null) return "ERROR,查询订单失败";
			if (!rs.next()){
				dc.CloseResultSet(rs);
				System.out.print("[分发]" + fid + ",订单号不存在或已处理完成");
				return "OK,订单号不存在或已处理完成";							
			}
			

			//4.取出订单信息
			smg("取出订单信息OK");
			fid = fc.getrv(rs, "fid", "");									//订单号
			String sProductID = fc.getrv(rs, "fProductId", "");				//商品编号
			String fPlayName = fc.getrv(rs, "fPlayName", "");				//玩家账号
			String fLackMoney = fc.getrv(rs, "fLackMoney", "0");			//未充金额 
			String fFactMoney = fc.getrv(rs, "fFactMoney", "0");			//已充金额,用来判断是否可以置为充值失败状态 
			String fOldClientId = fc.getrv(rs, "fGiveId", "");				//上次分发平台
			String fGiveOrderID = fc.getrv(rs, "fGiveOrderID", "");				//充值订单号
			String fGiveTime = fc.getrv(rs, "fGiveTime", "");				    //分发时间
			String fState = fc.getrv(rs, "fState", "");				    		//订单状态
			String fInsideID = fc.getrv(rs, "fInsideID", "");					//商品类型
			String fFillType = fc.getrv(rs, "fFillType", "");					//充值类型
			String fCountType = fc.getrv(rs, "fCountType", "");					//计费类型
			String fAccountType = fc.getrv(rs, "fAccountType", "");				//账号类型
			String fFillRange = fc.getrv(rs, "fFillRange", "");					//充值区域
			String fFillServer = fc.getrv(rs, "fFillServer", "");				//充值服务器
			String fUserIp = fc.getrv(rs, "fUserIp", "");						//用户IP
			String fUserArea = fc.getrv(rs, "fUserArea", "");					//用户地区
			String fLevel = fc.getrv(rs, "fLevel", "");							//用户IP
			String fLockID = fc.getrv(rs, "fLockID", "");						//锁定ID
			double dRate = Double.parseDouble(fc.getrv(rs, "fRate", "0"));		//转换为小数费率
			String fRate = Double.toString(dRate); 							 	//此单费率
			float nLackMoney = Float.valueOf(fLackMoney).floatValue();
			//
			dc.CloseResultSet(rs);
			
			//如果未充金额为0时,直接成功
			if ((Float.valueOf(fLackMoney).floatValue() <= 0)){
				dc.execute("update AcOrder set fLockID = '', fState = '4', " +
						"  fFillTime=GETDATE(), fOverTime=GETDATE(), fGiveTime=GETDATE(),  fErrorCount = 0 where fid = '" + fid + "'");
				return "OK,未充为0,订单处理完毕";
			}
			
			//如果接口未指定分发平台,用订单中的分发平台来做分发依据OK
			
			if (soclient.equals("")) soclient = fOldClientId;
			if (!soclient.equals("")) soclient = soclient.substring(0, soclient.length() - 3);

			//5.分发计算
			smg("分发计算");
			//每天清减一次分发量, 以免溢出出错.OK
			if (!m_sDate.equals(fc.getTime("yyyyMMdd"))){
				sql = "update AcRules set fMaxValue = 0, fValue = 0 ";
				dc.execute(sql);
				m_sDate =  fc.getTime("yyyyMMdd");
			}
			
			//6.订算出可以发送的商户:分发百分比-(分发量/分发总量*100).OK
			String fUserArea_ = "";
			if (fUserArea.indexOf(",")>-1){
				fUserArea_ = fc.getString("!@#$%" + fUserArea, "!@#$%", ",");
			}
			
			//初始化
			String fRuleID = "";
			String sClientId = "";
			String fRuleProductID = "";
			String fPlatformID = "";
			String sKey = "";
			String sUrl = "";
			String sParam = "";
			String sSign = "";
			String sOkStr = "";
			String sReUrl = "";			
			String fOutTime = "";			
			String fFillCode = "";				
			String fToLevel = "";				
			String fGiveArea = "";
			String fGivePrice = "";	
			String fScale = "";
			String fData = "";
			float fMinMoney = 0;				
			float fMaxMoney = 0;
			
			//判断是否为指定地区
			String sAreaYes = "false";
			sql = "SELECT * FROM AcRules WHERE fState = '0' and isnull(fArea,'') = '" + fUserArea_ + "' and '' <> '" + fUserArea_ + "'";
			rs = dc.query(sql);
			if (rs!=null && rs.next()){
				sAreaYes = "true";
			}
			dc.CloseResultSet(rs); 
			if (sAreaYes.equals("true"))
				sAreaYes = " and fArea = '" + fUserArea_ + "' ";
			else
				sAreaYes = " and isnull(fArea, '') = '' ";
			//判断是否为指定商品
			String sProcYes = "false";
			sql = "SELECT * FROM AcRules WHERE fState = '0' and isnull(fProductID,'') = '" + sProductID + "' and '' <> '" + sProductID + "'";
			rs = dc.query(sql);
			if (rs!=null && rs.next()){
				sProcYes = "true";
			}
			dc.CloseResultSet(rs);
			if (sProcYes.equals("true"))
				sProcYes = " and fProductID = '" + sProductID + "' ";
			else
				sProcYes = " and isnull(fProductID, '') = '' ";
			
			//直到找到通道或没有可用通道为止
			boolean b = true;
			while (b){
				
				smg("订算出可以发送的商户");
//				sql = "SELECT * FROM (SELECT *, fScale - fValue / (fMaxValue + " + fLackMoney + " + 0.00001) * 100 AS fn FROM AcRules " +
//				" WHERE (fInsideID = '" + fInsideID + "' and (isnull(fArea,'') = '" + fUserArea_ + "' or isnull(fArea,'')='')" +
//						" AND (isnull(fProductID,'') = '" + sProductID + "' OR isnull(fProductID, '') = '')" +
//						" AND (isnull(fSoPlatformID,'') = '" + soclient + "' OR ('" + soclient + "' = ''))    )) " +
//				" a INNER JOIN AcPlatform c ON a.fPlatformID = c.fid WHERE (a.fState = '0') " +
//				" ORDER BY fArea desc,fProductID desc,  a.fLevel desc, a.fn DESC"; 
				//
				sql = "SELECT * FROM (SELECT *, fScale - fValue / (fMaxValue + " + fLackMoney + " + 0.00001) * 100 AS fn FROM AcRules " +
				" WHERE (fInsideID = '" + fInsideID + "' AND (isnull(fSoPlatformID,'') = '" + soclient + "' OR ('" + soclient + "' = ''))" +
						" " + sAreaYes + sProcYes + ")) " +
				" a INNER JOIN AcPlatform c ON a.fPlatformID = c.fid WHERE (a.fState = '0') " +
				" ORDER BY fArea desc,fProductID desc,  a.fLevel desc, a.fn DESC"; 
				
				rs = dc.query(sql);
				if (rs == null) {
					System.out.print("[分发]" + fid + ",计算分发规则时失败");
					SaveFillLog(fInsideID, "", fid, fPlayName, sProductID, fLackMoney, fGiveOrderID, "8", "计算分发规则时失败", fUserArea);
					return "ERROR,计算分发规则时失败";
				}
				if (!rs.next()){
					System.out.print("[分发]" + fid + ",无可用的充值通道:" + fLackMoney + "," + fInsideID + "," + sProductID + "," + fOldClientId + "," + soclient + "," + fGiveTime + "," + fState);
					dc.CloseResultSet(rs);
					if (Float.valueOf(fFactMoney).floatValue() == 0)
						fState = "5";
					else
						fState = "4";
					dc.execute("update AcOrder set fLockID = '', fState = '" + fState + "', fFillMsg='分发失败,无可用的充值通道', " +
							"  fFillTime=GETDATE(), fOverTime=GETDATE(), fGiveTime=GETDATE(),  fErrorCount = 0 where fid = '" + fid + "'");
					SaveFillLog(fInsideID, "", fid, fPlayName, sProductID, fLackMoney, fGiveOrderID, "8", "无可用的充值通道", fUserArea);
					return "ERROR,无可用的充值通道";
				}
				
				//7.查询出分发参数			
				smg("查询出分发参数");
				fRuleID = fc.getrv(rs, "fID", "");				//分发规则ID
				fRuleProductID = fc.getrv(rs, "fProductID", "");	//指定商品
				fPlatformID = fc.getrv(rs, "fPlatformID", "");	//分发平台ID
				sKey = fc.getrv(rs, "fKey", "");
				sUrl = fc.getrv(rs, "fUrl", "");
				sParam = fc.getrv(rs, "fParam", "");
				sSign = fc.getrv(rs, "fSign", "");
				sOkStr = fc.getrv(rs, "fOkStr", "");
				sReUrl = fc.getrv(rs, "fReUrl", "");			
				fOutTime = fc.getrv(rs, "fOutTime", "");			
				fFillCode = fc.getrv(rs, "fFillCode", "");				
				fToLevel = fc.getrv(rs, "fLevel", "");		
				fScale = fc.getrv(rs, "fScale", "0");
				String fIsCheckArea = fc.getrv(rs, "fIsCheckArea", "");				
				fMinMoney = rs.getFloat("fMinMoney");				
				fMaxMoney = rs.getFloat("fMaxMoney");				
				if (fMaxMoney == 0) fMaxMoney = 999999999;
				if (fOutTime.equals("")) fOutTime = "60";
				dc.CloseResultSet(rs);
				
				//为下一次分发做准备
				//String OldSoclient = soclient;
				soclient = sClientId;
				
				//8.是否符合分发金额大小范围
				if (nLackMoney < fMinMoney || nLackMoney > fMaxMoney){
					smg("不符合分发金额大小范围");
					dc.CloseResultSet(rs);

//					dc.execute("update AcOrder set fLockID='',fGiveID = '" + fPlatformID + "-" + fInsideID + "', fOverTime=GETDATE(), fGivetime=GETDATE(), fState='1'" +
//							" , fErrorCount = 0 where fid = '" + fid + "'");
//					SaveFillLog(fInsideID, fPlatformID, fid, fPlayName, sProductID, fLackMoney, fGiveOrderID, "8", "未充金额不在分发范围之内", fUserArea);
					System.out.println("[分发]" + fid + ">>分发失败,未充金额不在分发范围之内");
					continue;
				}
				
				//9.查询商品信息OK
				smg("查询商品信息");
				sql = "select * from AcProduct where fid='" + sProductID + "'" ;
				rs = dc.query(sql);
				if (rs == null || !rs.next()){
					dc.CloseResultSet(rs);
					System.out.print("[分发]" + fid + ",读取商品信息失败");
					continue;
//					SaveFillLog(fInsideID, fPlatformID, fid, fPlayName, sProductID, fLackMoney, fGiveOrderID, "8", "读取商品信息失败", fUserArea);
//					return "ERROR,读取商品信息失败";		
				}
				fData = fc.getrv(rs, "fData", "");						//自定义的转换数据
				
				//9.1是否检查充值地区维护, 0不检查, 1检查维护, 2检查正常
				if (fIsCheckArea.equals("")) fIsCheckArea = "0";
				if (!fIsCheckArea.equals("0")){
					String s1 = fc.getString("!@#$%" + fUserArea + ",", "!@#$%", ",");	//省份
					String s2 = fc.getString(fUserArea + "!@#$%", ",", "!@#$%");		//市
					if (s1.equals(s2)) s2 = "";				 //如果省市一样, 认为是整个省都在维护
					//要找指定及默认商品规则
					sql = "select * from AcAreaRule where  fInsideID = '" + fInsideID + "' " +
						" and (fProductID = '" + sProductID + "' or isnull(fProductID, '') = '')" + //  order by fProductID desc ";
						" and fCheckType = '" + fIsCheckArea + "'  order by fProductID desc";
					ResultSet rsArea = dc.query(sql);
					boolean bOK = true;	//是否可以分发
					int n1 = 0, n2 = 0, n3 = 0;
					while (rsArea != null && rsArea.next()){
						String pid = fc.getrv(rsArea, "fProductID", "");		//商品ID
						String fProvince = fc.getrv(rsArea, "fProvince", "");	//省份
						String fCity = fc.getrv(rsArea, "fCity", "");			//城市
						//如果有指定商品规则
						if (pid.equals(sProductID)){
							n3 = n3 + 1;
							//如果是符合的地区
							if (fProvince.equals(s1) && (fCity.equals(s2) || fCity.equals(""))){
								if (fIsCheckArea.equals("1")) n1 = n1 + 1;  //bOK = false;
								if (fIsCheckArea.equals("2")) n2 = n2 + 1;	//bOK = true;
							}
						}					
						//如果没有指定商品规则
						if ((n3 == 0) && pid.equals("")){
							//如果是符合的地区
							if (fProvince.equals(s1) && (fCity.equals(s2) || fCity.equals(""))){
								if (fIsCheckArea.equals("1")) n1 = n1 + 1;  //bOK = false;
								if (fIsCheckArea.equals("2")) n2 = n2 + 1;	//bOK = true;
							}
						}
					}
					if (fIsCheckArea.equals("1")){
						if (n1 > 0) 
							bOK = false;		//维护
						else
							bOK = true;
					}else
						if (fIsCheckArea.equals("2")){
							if(n2 > 0)
								bOK = true;			//正常
							else
								bOK = false;
						}
					//
					dc.CloseResultSet(rsArea);
					//
					if (bOK == false){
//						dc.execute("update AcOrder set fLockID='',fGiveID = '" + fPlatformID + "-" + fInsideID + "', fOverTime=GETDATE(),fGivetime=GETDATE(), fState='1'" +
//								" , fErrorCount = 0 where fid = '" + fid + "'");
//						SaveFillLog(fInsideID, fPlatformID, fid, fPlayName, sProductID, fLackMoney, fGiveOrderID, "8", "充值通道地区维护或不支持", fUserArea);
						System.out.print("[分发]" + fid + ",充值通道地区维护或不支持");
//						dc.CloseResultSet(rsArea);
//						return "ERROR,充值通道地区维护";
						dc.CloseResultSet(rs);
						continue;
					}
					
				}
				//如果没有被中途拦截,说明可以分发到当前平台,跳出
				break;
			}
			
			//10.如果分发平台为self, 则置状态为等待充值,当取走时置充值中,并写充值日志(用挂机ID和订单号来识别) OK
			if (sUrl.equals("#")){
				smg("内部分发平台");
				dc.CloseResultSet(rs);
				
				//置再次充值状态
				String fFillID = fc.getGUID6("A");									//正常订单(取正常卡或直充)
				if (fPlatformID.indexOf("-kck") > 0) fFillID = fc.getGUID6("K");	//取库存卡订单
				System.out.print(fid + "," + fPlatformID + "-" + fInsideID + "," + fFillID + "," + fOutTime);
				dc.execute("update AcOrder set fLockID='', fState='2', fGiveId = '" + fPlatformID + "-" + fInsideID + "', " +
						"  fGiveOrderID='" + fFillID + "', fOverTime=GETDATE(), fGiveTime=GETDATE() + 0.0000116 * " + fOutTime + "" +
								" where fid = '" + fid + "' and fLockID = '" + ss + "'");

				//如果为0,将不计数 
				if (!fScale.equals("0")){ 
					//更新分发总量
					sql = "update AcRules set fMaxValue = fMaxValue + " + fLackMoney + " where (fLevel='" + fToLevel + "') and (fInsideId = '" + fInsideID + "') and isnull(fProductID, '') = '" + fRuleProductID + "' and fState = '0'";
					dc.execute(sql);
					//累加分发量
					sql = "update AcRules set fValue = fValue + " + fLackMoney + " where fId = '" + fRuleID + "'";
					dc.execute(sql);
				}
				System.out.print("[分发]" + fid + ",分发成功");
				return "OK,分发成功[" + fPlatformID + "]";
			}
			
			//11.用替换法计算出URL及sign值x=[fFillLevel]&czType=[fFillType]&Region=&czfu=[fFillServer]&sdgame=[sdgame]&
			smg("用替换法计算出URL");
			//商品转换设置优先级最高,对sParam中的自定义值进行替换: <sdgame> <sdgame field="fFillRange">完美世界=1,武林外传=2</sdgame>
			sSign = GetConvertData(sSign, fData, rs);	
			//字段内容优先级最低
			sSign = sSign.replace("[fID]", fid);								
			sSign = sSign.replace("[fGiveOrderID]", fGiveOrderID);								
			sSign = sSign.replace("[fInsideID]", fInsideID);					
			sSign = sSign.replace("[fPlayName]", fPlayName);					
			sSign = sSign.replace("[fLackMoney]", fLackMoney);					
			sSign = sSign.replace("[fLevel]", fLevel);				
			sSign = sSign.replace("[fFillType]", fFillType);				
			sSign = sSign.replace("[fCountType]", fCountType);					
			sSign = sSign.replace("[fAccountType]", fAccountType);				
			sSign = sSign.replace("[fFillRange]", fFillRange);			
			sSign = sSign.replace("[fFillServer]", fFillServer);			
			sSign = sSign.replace("[fUserIp]", fUserIp);	
			sSign = sSign.replace("[fRate]", fRate);		
			sSign = sSign.replace("[fFillCode]", fFillCode);
			sSign = sSign.replace("[fOutTime]", fOutTime);	
			sSign = sSign.replace("[fReUrl]", sReUrl);
			sSign = sSign.replace("[key]", sKey);
			System.out.print(sSign);

			//商品转换设置优先级最高, 对sParam中的自定义值进行替换: <sdgame> <sdgame field="fFillRange">完美世界=1,武林外传=2</sdgame>
			sParam = GetConvertData(sParam, fData, rs);	
			//字段内容优先级最低
			sParam = sParam.replace("[fID]", fid);								
			sParam = sParam.replace("[fGiveOrderID]", fGiveOrderID);								
			sParam = sParam.replace("[fInsideID]", fInsideID);					
			sParam = sParam.replace("[fPlayName]", fPlayName);					
			sParam = sParam.replace("[fLackMoney]", fLackMoney);	
			sParam = sParam.replace("[fLevel]", fLevel);				
			sParam = sParam.replace("[fFillType]", fFillType);				
			sParam = sParam.replace("[fCountType]", fCountType);					
			sParam = sParam.replace("[fAccountType]", fAccountType);				
			sParam = sParam.replace("[fFillRange]", fFillRange);			
			sParam = sParam.replace("[fFillServer]", fFillServer);			
			sParam = sParam.replace("[fUserIp]", fUserIp);	
			sParam = sParam.replace("[fRate]", fRate);		
			sParam = sParam.replace("[fFillCode]", fFillCode);
			sParam = sParam.replace("[fOutTime]", fOutTime);		
			sParam = sParam.replace("[fReUrl]", sReUrl);
			sParam = sParam.replace("[sign]", fc.getMd5Str(sSign).toUpperCase());
						
			
			dc.CloseResultSet(rs);

			//12.发送充值请求
			smg("发送充值请求");
			n = 0;
			System.out.println("[发送]" + fid + ">>" + sUrl + "," + sParam);
			String s = fc.SendDataViaPost(sUrl, sParam, "");
			if (s.indexOf(sOkStr) == 0) {	//发送ok或订单号已存在
				
				//更新订单表
				smg("分发成功,更新订单表");	//
				dc.execute("update AcOrder set fState='3', fOverTime=GETDATE(), fGiveID = '" + fPlatformID + "-" + fInsideID + "', fErrorCount = 0 where fid = '" + fid + "' and fLockID = '" + ss + "'"); 

				//如果为0,将不计数 
				if (!fScale.equals("0")){
					//更新分发总量
					sql = "update AcRules set fMaxValue = fMaxValue + " + fLackMoney + " where (fLevel='" + fToLevel + "') and (fInsideId = '" + fInsideID + "') and isnull(fProductID, '') = '" + fRuleProductID + "' and fState = '0'";
					dc.execute(sql);

					//累加分发量
					sql = "update AcRules set fValue = fValue + " + fLackMoney + " where fId = '" + fRuleID + "'";
					dc.execute(sql);
				}
				//
				System.out.println("[分发]" + fid + ">>发送成功:" + fPlatformID);
				return "OK,分发成功[" +fPlatformID + "]";						//发送成功
			} else {
				smg("分发失败,写充值记录");
				//
				if (s.indexOf("alert(") > -1){
					s = fc.getString(s, "alert(", ")");
					s = fc.replace(s, "'", "");
				}
				if (s.length() > 32) s = s.substring(0, 16);

				//如果分发失败,应向备用通道分发
				SaveFillLog(fInsideID, fPlatformID, fid, fPlayName, sProductID, fLackMoney, fGiveOrderID, "8", s, fUserArea);
				System.out.println("[分发]" + fid + ">>分发失败," + s + "");
				if ((s.indexOf("ERROR0082") > -1) || (s.trim().equals(""))){
					dc.execute("update AcOrder set fLockID='',fGiveID = '" + fPlatformID + "-" + fInsideID + "', fOverTime=GETDATE(), fGivetime=GETDATE(), fState='1'," +
							" fFillMsg='分发" + s + "失败,尝试下一通道', fErrorCount = 0 where fid = '" + fid + "' and fLockID = '" + ss + "'");
					//
					return "ERROR,分发" + s + "失败,尝试下一通道";		//发送失败
				}else{
					dc.execute("update AcOrder set fLockID='',fstate = '1', fMemo='" + s + "', fGiveID = '" + fPlatformID + "', " +
							" fFillTime=GETDATE(), fOverTime=GETDATE(), fGivetime=GETDATE(), fErrorCount = 0 where fid = '" + fid + "' and fLockID = '" + ss + "'");
					return "ERROR,分发失败:" + s;		//卡信息有误
				}

			}

		}catch(Exception e){
			e.printStackTrace();
			return "ERROR,分发接口出错,请速联系技术!";
		}
		
	}

	
	private void SaveFillLog(String fProductType, String fGiveID, String fAccoID, String sPlayName, String fAProductID, String fAPrice
			, String fFillID, String fOrderState, String fFillMsg, String fUserArea){
		String sql = "";
		sql = fc.SetSqlInsertStr(sql, "fID", fc.getGUID6(""));
		sql = fc.SetSqlInsertStr(sql, "fTime", "GETDATE()", false);
		sql = fc.SetSqlInsertStr(sql, "fType", fProductType);
		sql = fc.SetSqlInsertStr(sql, "fPlatformID", fGiveID);
		sql = fc.SetSqlInsertStr(sql, "fAcOrderID", fAccoID);
		sql = fc.SetSqlInsertStr(sql, "fPlayName", sPlayName);
		sql = fc.SetSqlInsertStr(sql, "fAcProductID", fAProductID);
		sql = fc.SetSqlInsertStr(sql, "fPrice", fAPrice, false);
		sql = fc.SetSqlInsertStr(sql, "fFillOrderID", fFillID);
		sql = fc.SetSqlInsertStr(sql, "fMoney", "0");
		sql = fc.SetSqlInsertStr(sql, "fIpArea", fUserArea);
		sql = fc.SetSqlInsertStr(sql, "fState", fOrderState);	//充值记录与状态规则中的定义是一致的. 在充值结果接口转成号或卡订单状态
		sql = fc.SetSqlInsertStr(sql, "fFillInfo", fFillMsg);
		sql = "insert into CoFillLog " + sql;
		dc.execute(sql);
	}
	
	private void smg(String s) {
//		System.out.print(s);
	}
	/*
	 * 对sParam中的自定义值进行替换: <sdgame> <sdgame>字段名=fFillRange,完美世界=1,武林外传=[fInsideId]</sdgame>
	 */
	private String GetConvertData(String param, String data, ResultSet rs) {
		try{
			//对字段替换值进行处理<字段名>[字段1]常量[字段2]<字段名/>
				ResultSetMetaData rsmd = rs.getMetaData() ;
				int columnCount = rsmd.getColumnCount();
				String s = "";
				for (int i=1; i <= columnCount; i++){
					//得到字段名
					s = rsmd.getColumnName(i);
					if (s == null) s = "";
					//替换
					//从data中取数据
					String v = fc.getString(data, "<" + s + ">", "</" + s + ">");
					if (!v.equals("")){
						//对其中的字段进行替换
						ResultSetMetaData rsmd2 = rs.getMetaData() ;
						int columnCount2 = rsmd.getColumnCount();
						String s2 = "";
						for (int i2=1; i2 <= columnCount2; i2++){
							//得到字段值
							String r = rs.getString(rsmd.getColumnName(i2));
							//替换
							v = fc.setString(v, "[" + rsmd.getColumnName(i2) + "]", r);
						}
						//替换
						param = fc.setString(param, "[" + s + "]", v);
					}

				}

			//对特殊的替换值进行处理取出<sdgame>
			s = fc.getString(param, "<", ">");
			while (!s.equals("")){
				//从data中取数据
				String v = fc.getString(data, "<" + s + ">", "</" + s + ">");
				//得到fFillRange
				String m = fc.getString("," + v + ",", ",字段名=", ",");
				//得到完美世界
				String f = fc.getrv(rs, m, "");
				//得到1
				String r = fc.getString("," + v + ",", "," + f + "=", ",");
				//对r再替则[]
				String t = fc.getString(r, "[", "]");			//得到字段名
				while(!t.equals("")){
					v = fc.getrv(rs, t, "");					//到得字段值
					r = fc.replace(r, "[" + t + "]", v);
					t = fc.getString(r, "[", "]");				
				}				
				//替换
				param = fc.replace(param, "<" + s + ">", r);
				//下一个
				s = fc.getString(param, "<", ">");
			}
			return param;
		}catch(Exception e){
			e.printStackTrace();
			return param;
		}
	}
	
	
	
}