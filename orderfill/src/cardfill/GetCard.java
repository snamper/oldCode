package cardfill;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.DataConnect;
import common.fc;

/*
 * 充值取卡
 * 
 */

/**
 * Servlet implementation class for Servlet: GetCard
 *
 * @web.servlet
 *   name="GetCard"
 *   display-name="GetCard"  
 *
 * @web.servlet-mapping
 *   url-pattern="/GetCard" 
 *  
 */
 public class GetCard extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	   static final long serialVersionUID = 1L;
	   private static final String CONTENT_TYPE = "text/html; charset=GBK";
		//static DataConnect dc = new DataConnect("fillcard", false);
	   
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public GetCard() {
		super();
	}   	
	public void destroy() { 
//		if (dc != null) {
//			dc.CloseConnect();
//			dc = null;
//		}
	}
	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(getCard(request, response));
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(getCard(request, response));
	}   	  	    
	
		public String getCard(HttpServletRequest request, HttpServletResponse response){
			String s = "";
			 
			//1.取参数
			String mode = fc.getpv(request, "mode");				//*模式: 0正常充值,1验证卡,2库存卡充值
			String fGiveId = fc.getpv(request, "giveid");			//*分发平台id: huaqi-01,guanwang-05
			String sSign = fc.getpv(request, "sign");				//*签名: mode + giveid + hfdt534yd7890h636w9058htdn3s65d
			//可选
			String sAppId = fc.getpv(request, "appid");				//充值程序ID
			String sFillID = fc.getpv(request, "fillid");			//卡取号,号取卡时传时.
			String sMoney = fc.getpv(request, "maxmoney");			//取卡最大面值, 为空时, 表示对取卡面值不限制
			String sOrderType = fc.getpv(request, "ordertype");		//订单类型, 0表示卡订单, 1表示卡&号订单
			
			//
			System.out.print("[取卡]" + mode + "," + fGiveId + "," + sSign + "," + sAppId + "," + sMoney + "," );
	//		if (!sSign.equals(fc.getMd5Str(mode + fGiveId + "hfdt534yd7890h636w9058htdn3s65d"))) 
	//			return fc.getResultStr("getcard", "false", "md5验证失败");
			
			//容错
			if (mode.equals("")) return fc.getResultStr("getcard", "false", "mode不能为空");
			if (fGiveId.equals("")) return fc.getResultStr("getcard", "false", "giveid不能为空");
			if (sAppId.equals("")) sAppId = request.getRemoteAddr();	//如果程序ID为空,则用访问IP来区分
			sAppId = sAppId + "." + fc.getTime("HHmmss") + "." + fc.ran(10000, 99999);	//防止重复
			
			//检查mode
			String sState = "";
			if (mode.equals("0")) sState = "2";			//正常
			else if (mode.equals("1")) sState = "7";	//验证
			else if (mode.equals("2")) sState = "0";	//库存
			else return fc.getResultStr("getcard", "false", "mode取值不对");
			
			//订单类型ordertype
			String sOrderRule = "";
			if (sOrderType.equals("")) sOrderType = "0";							//默认取所有
			if (sOrderType.equals("0")) sOrderRule = " fAccountID = ''";			//只取卡
			else if (sOrderType.equals("1")) sOrderRule = " fAccountID <> ''";		//取卡&号
			else return fc.getResultStr("getcard", "false", "ordertype取值不对");
			
			//检查最大面值
			if (!sMoney.equals("")){
				try{
					if (Float.valueOf(sMoney).floatValue() == 0);
				}catch(Exception e){
					return fc.getResultStr("getcard", "false", "money取值不对");
				}
			}
			
			//3.取卡开始
			String sCardTable = "CaOrder";								//正常卡
			String sCardOrderBy = " order by fCreateTime";				//按时间取单
			if (mode.equals("2")){
				sCardTable = "CaOrderKck";								//库存卡
				sCardOrderBy = " order by fPrice desc, fCreateTime";	//按面值,时间取单
			}
			//
			DataConnect dc = new DataConnect("orderfill", false);
			try{
				//2.取卡面值规则
				String sPriceRule = ""; 
				String sInsideID = fGiveId.substring(fGiveId.length() - 2);
				String sGiveID = fGiveId.substring(0, fGiveId.length() - 3);
				if (!sMoney.equals("")){
					String sql = "select top 1 * from CaPriceRule where fInsideID = '" + sInsideID + "'" +
							" and (fGiveID = '" + sGiveID + "' or fGiveID = '') " +
							" and fMinMoney <= " + sMoney + " and fMaxMoney >= " + sMoney + " and fState = '0' order by fGiveID desc";
					ResultSet rs = dc.query(sql); 
					if (rs != null && rs.next()){
						sPriceRule = fc.getrv(rs, "fPriceList", "");	//取卡规则
					}
					dc.CloseResultSet(rs);
					sPriceRule = fc.replace(sPriceRule, ";", " OR ");
					sPriceRule = fc.replace(sPriceRule, ",", " AND "); 
					sPriceRule = fc.replace(sPriceRule, "=", " fPrice=");
					sPriceRule = fc.replace(sPriceRule, ">", " fPrice>");
					sPriceRule = fc.replace(sPriceRule, "<", " fPrice<");
					if (!sPriceRule.equals("")) System.out.print("[取卡规则]" + sPriceRule);
				}
				//
				String sLockSql = "";
				//3.1锁定记录 //直接锁定符合的状态, 因为要哪个平台充值,已经分配好
				String fGiveId2 = "";
				if (sState.equals("0")) 
					fGiveId2 = "";
				else
					fGiveId2 = " and fGiveID in ('" + fc.replace(fGiveId, ",", "','") + "')";
				sLockSql = "SELECT TOP 1 fid FROM " + sCardTable + " WHERE fLockID = '' " + fGiveId2 + " AND (fState = '" + sState + "') ";
				if (!sMoney.equals("")) sLockSql = sLockSql + " AND fPrice <= " + sMoney;
				if (!sPriceRule.equals("")) sLockSql = sLockSql + " AND (" + sPriceRule + ")";
				if (!sOrderRule.equals("") && !mode.equals("2")) sLockSql = sLockSql + " AND (" + sOrderRule + ")";
				sLockSql = sLockSql + sCardOrderBy;
				String sql = "update " + sCardTable + " set fLockID = '" + sAppId + "' where fid = (" + sLockSql + ") AND fLockID = ''";
				int n = dc.execute(sql);
				if (n == 0){
					dc.CloseConnect();
					return fc.getResultStr("getcard", "false", "暂无订单处理");
				}
				if (n == -1){
					dc.CloseConnect();
					return fc.getResultStr("getcard", "false", "锁定卡订单失败(" + n + ")," + dc.getError());
				}
				
				//3.2读取记录
				System.out.print("[" + sAppId + "]锁定:" + n);
				sql = "select * from " + sCardTable + " where fLockID = '" + sAppId + "'";
				ResultSet rs = dc.query(sql);
				System.out.print("[" + sAppId + "]读取:" + rs);
				if (rs == null){
					dc.CloseConnect();
					return fc.getResultStr("getcard", "false", "锁定成功,但读取订单时失败," + dc.getError());
				}
				if (!rs.next()){ 
					dc.CloseResultSet(rs);
					dc.CloseConnect();
					return fc.getResultStr("getcard", "false", "锁定成功,但未读取到订单,奇怪啊!");
				}
	
				//3.3将密码加密存储, 如果密码字段有前缀: (@*@), 即为加密的结果
				String fPassword = fc.getrv(rs, "fPassword", "");
	
				//解密
				if (fPassword.indexOf("(@_@)") == 0){
					fPassword = common.EncryptsE.uncrypt(fPassword.substring(5), "64Qz9cWPiH0B25CM7IoFGjETmfAbsOxh");
					//System.out.print("新解密:" + fPassword);
				}
				fPassword = "(@_@)" + common.EncryptsE.encrypt(fPassword, "qzYPnw8VWIDLxy1SbAOB7H03FkaoZQgK");
	
				//3.4取出数据
				if (sFillID.equals("")) sFillID = fc.getrv(rs, "fGiveOrderID", "");
				if (!sState.equals("0"))
					fGiveId = fc.getrv(rs, "fGiveID", "");
				String fid = fc.getrv(rs, "fid", ""); 
				String sData = "<cardorder>" +
					"<row_1>" +
					"<fGiveID>" + fGiveId + "</fGiveID>" +    									//(必须)充值通道
					"<fID>" + fid + "</fID>" +    												//(必须)订单号
					"<fProductID>" + fc.getrv(rs, "fProductID", "") + "</fProductID>" +     	//(必须)类型
					"<fPrice>" + fc.getrv(rs, "fPrice", "") + "</fPrice>" +       				//(必须)面值
					"<fCardNo>" + fc.getrv(rs, "fCardNo", "") + "</fCardNo>" +      			//(必须)卡号
					"<fPassword>" + fPassword + "</fPassword>" +    							//(必须)密码,到程序中解密
					"<fFillAccount>" + fc.getrv(rs, "fAccountId", "") + "</fFillAccount>" +    	//(可选)充值账号
					"<fFillID>" + sFillID + "</fFillID>" +    									//(必须)充值订单号
					"</row_1>" +
					"</cardorder>";
				
				//3.5更新主订单表
				sql = "update " + sCardTable + " set fState = '3', fGiveId = '" + fGiveId + "', fGiveOrderID = '" + sFillID + "' where fid = '" + fid + "'";
				dc.execute(sql);
				//
				dc.CloseResultSet(rs);
				dc.CloseConnect();
				//
				System.out.print("[" + sAppId + "]返回:" + fGiveId + "," + fid);
				
				//返回记录
				return fc.getResultStr("getcard", sData, s, "true", "取单成功");  
			}catch(Exception e){
				dc.CloseConnect();
				return fc.getResultStr("getcard", "false", "服务器运行出错");
			}
	
		}
}
