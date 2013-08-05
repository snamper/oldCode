package accept.v2;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.DataConnect;
import common.fc;
import check.v2.OrderCheck;

/*
 * 接收客户下单请求 
 */

/**
 */
 public class OrderRequest extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	   static final long serialVersionUID = 1L;
	   private static final String CONTENT_TYPE = "text/html; charset=GBK";
	   private static DataConnect dc = new DataConnect("orderfill", false);
	   static int m_count = 0;
	   static String IsCache_CoClient = "";
	   
	   private static long lastTime = 0l;
	   private final static long REFRESHTIME =1l * 60 * 1000;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public OrderRequest() {
		super();
		IsCache_CoClient = "";
	} 
   	
	public void destroy() { 
		if (dc != null) {
			////dc.CloseConnect();
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
	 * 取订单
	 */
	public String orderRequest(HttpServletRequest request, HttpServletResponse response){
		
		long nowTime = System.currentTimeMillis();
		if(nowTime - lastTime > REFRESHTIME){
			IsCache_CoClient = "";
			lastTime = nowTime;
		}
		
		try{
			int n1 = (int)System.currentTimeMillis();
			//1.接收参数,必须
			String sign = fc.getpv(request, "sign");
			String fClientOrderID = fc.getpv(request, "oid");	//客户订单号
			String fClientID = fc.getpv(request, "cid");		//客户ID
			String fProductID = fc.getpv(request, "pid");		//商品ID
			String fPlayName = fc.getpv(request, "pn");			//玩家号码
			String fPrice = fc.getpv(request, "pr");			//单价   用于校验商品及金额
			String fNumber = fc.getpv(request, "nb");			//数量	用于校验商品及金额
			String fMoney = fc.getpv(request, "fm");			//充值金额
			String fClientReUrl = fc.getpv(request, "ru");		//通知URL
			//可选
			String fAccountType = fc.getpv(request, "at");	//账号类型
			String fCountType = fc.getpv(request, "ct");	//计费类型
			String fFillRange = fc.getpv(request, "fr");	//充值区域
			String fFillServer = fc.getpv(request, "fs");	//充值服务器
			String fClientTime = fc.getpv(request, "tm");	//客户时间
			String fClientIp = fc.getpv(request, "ip");		//客户IP
			String fClientArea = fc.getpv(request, "ar");		//客户地区
			//扩展
			String fInfo1 = fc.getpv(request, "info1");
			String fInfo2 = fc.getpv(request, "info2");
			String fInfo3 = fc.getpv(request, "info3");
			String fSource = fc.getpv(request, "source");	//订单来源
//			String fSkipMainOrderCheck = fc.getpv(request, "isskipcheck");
//			String fIsSaveMainTable = fc.getpv(request, "issavemaintable");
			//新增
			String command = fc.getpv(request, "command");
			
			
			//
			if (fSource.equals("")) fSource = "0";
			//
//			String sClientIp = request.getRemoteAddr(); 
//			smg("[" + fClientIp + "]接单:" + fClientOrderID + "," + fClientID + "," + fProductID + ","   
//					+ fPlayName + "," + fPrice + "," + fNumber + "," + fMoney + "," + fClientReUrl + "," 
//					+ fAccountType + "," + fCountType + "," + fFillRange + "," + fFillServer + "," + fClientTime + "," 
//					+ fClientIp + "," + fClientArea + "," + fInfo1 + "," + fInfo2 + "," + fInfo3 + "," 
//					+ sign + "," + command);

			//2.参数合法性检查
			if(!("".equals(command) || "1".equals(command) || "2".equals(command))){
				return fc.getResultStr("response", "false", "[command]参数有误");	 //新增
			}
			if (sign.equals("")) return fc.getResultStr("response", "false", "[md5签名]参数有误");		
			if (fClientID.equals("")) return fc.getResultStr("response", "false", "[客户ID]参数有误");		
			if (fProductID.equals("")) return fc.getResultStr("response", "false", "[商品ID]参数有误");		
			if (fClientOrderID.equals("")) return fc.getResultStr("response", "false", "[客户订单号]参数有误");		
			if (fPlayName.equals("")) return fc.getResultStr("response", "false", "[玩家账号]参数有误");		
			if (fPrice.equals("")) return fc.getResultStr("response", "false", "[面值]参数有误");		
			if (fNumber.equals("")) return fc.getResultStr("response", "false", "[数量]参数有误");		
			if (fMoney.equals("")) return fc.getResultStr("response", "false", "[金额]参数有误");		
			//if (fClientReUrl.equals("")) return fc.getResultStr("response", "false", "参数有误");		
			if (!fc.isPlusFloat(fMoney)) return fc.getResultStr("response", "false", "[金额]参数有误");			
			if (!fc.isPlusFloat(fPrice)) return fc.getResultStr("response", "false", "[面值]参数有误");			
			if (!fc.isPlusFloat(fNumber)) return fc.getResultStr("response", "false", "[数量]参数有误");			
			if (!(Float.valueOf(fPrice).floatValue() * Float.valueOf(fNumber).floatValue() == Float.valueOf(fMoney).floatValue()))
					return fc.getResultStr("response", "false", "[金额,面值,数量]换算关系有误");
			if (fClientTime.equals("")) 
				fClientTime = "GETDATE()";
			else{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
				try{
					Date tt = sdf.parse(fClientTime);
				}catch(Exception te){
					return fc.getResultStr("response", "false", "[客户时间]格式不正确");
				}
			}
			
			
			//4.取出客户密钥ok
			boolean bIsSaveCache = false;
			String key = "", AgentID="", ClientID="";
			String s = fc.getString(IsCache_CoClient, "<" + fClientID + ">", "</" + fClientID + ">");
			if (s.equals("")){
				//取库表中取出9
				String sql = "select top 1 fID, fAgentID, fKey from CoClient where (fId = '" + fClientID + "' or fFaceID = '" + fClientID + "') and fState = '0'";
				ResultSet rs = dc.query(sql);
				if ((rs == null) || (!rs.next())){
					dc.CloseResultSet(rs);
					return fc.getResultStr("response", "false", "无效的用户名或用户名没有被启用");	
				} 
				key = fc.getrv(rs, "fKey", "");							//Key
				AgentID = fc.getrv(rs, "fAgentID", "");			//代理商ID
				ClientID = fc.getrv(rs, "fID", "");				//客户ID
				//
				bIsSaveCache = true;
				dc.CloseResultSet(rs);
			}else{
				//从本地缓存中取出
				bIsSaveCache = false;
				s = fc.getString(IsCache_CoClient, "<" + fClientID + ">", "</" + fClientID + ">");
				key = fc.getString(s, "<key>", "</key>");
				AgentID = fc.getString(s, "<AgentID>", "</AgentID>");
				ClientID = fc.getString(s, "<ClientID>", "</ClientID>");
			} 
			//
			if (key.equals("")){
				//dc.CloseConnect();
				return fc.getResultStr("response", "false", "商户验证KEY不能为空");		
			}


			//5.MD5校验ok
			s = fClientOrderID + fClientID + fProductID + fPlayName + fPrice + fNumber + fMoney + fClientReUrl;
			String sSource = fc.getMd5Str(s + key);
			if (!sign.toLowerCase().equals(sSource.toLowerCase())){ 
//				smg("source:" + s);
//				smg("md5:" + sSource + " <> " + sign);
				//dc.CloseConnect();
				//清除标记,下次缓存
				s = fc.getString(IsCache_CoClient, "<" + fClientID + ">", "</" + fClientID + ">");
				IsCache_CoClient = fc.replace(IsCache_CoClient, "<" + fClientID + ">" + s + "</" + fClientID + ">", "");
//				smg("商户信息缓存:"+IsCache_CoClient);
				//
				return fc.getResultStr("response", "false", "MD5校验失败");
			}

			//6.保存到缓存中
			if (bIsSaveCache)
				if (!key.equals("")&&(!AgentID.equals(""))&&!ClientID.equals("")){
					IsCache_CoClient = "<" +fClientID + ">" + "<key>" + key + "</key><AgentID>" + AgentID + "</AgentID><ClientID>" + ClientID + "</ClientID></" +fClientID + ">" + IsCache_CoClient; 
//					smg("商户信息缓存:"+IsCache_CoClient);
				}
			
	
			//7.检查主库订单号是否已存在
			//if (fSkipMainOrderCheck.equals("true")){
				String sql = "select top 1 fid from AcOrder WITH (NOLOCK) where fOrderID = '" + fClientOrderID + "' and fClientID = '" + fClientID + "' and fCreateTime > GETDATE() - 0.5";
				ResultSet rs = dc.query(sql);
				if (rs == null){
					return fc.getResultStr("response", "false", "客户订单号已存在");
				}
				if (rs.next()){
					s = fc.getrv(rs, "fState", "");
					String smg = "订单已存在,充值中";
					if (s.equals("4")) smg = "订单已存在,充值成功";
					if (s.equals("5")) smg = "订单已存在,充值失败";
					dc.CloseResultSet(rs);
//					dc.CloseConnect();
					return fc.getResultStr("response", "false", smg);
				}
				dc.CloseResultSet(rs);
			//}
			
			//	
			m_count = (m_count + 1) % 10;
			
			//8.直接保证到主表
				OrderCheck oc = new OrderCheck();
//				System.out.print(this.toString());
				String ss = oc.orderCheck2(fSource, fc.getGUID6(""), "", fClientID, fClientOrderID, fProductID, fPrice, 
						fNumber, fMoney, fPlayName, fCountType, fAccountType, fFillRange, fFillServer, 
						fClientIp, fClientArea, fClientTime, fInfo1, fInfo2, fInfo3, fClientReUrl, AgentID, "", command);
				int n2 = (int)System.currentTimeMillis(); 
				System.out.print("[接收]" + fClientOrderID + ",用时:" + (n2 - n1) + "ms");
				return ss;
			
			//					
			//5.完成
			////dc.CloseConnect();
//			int n2 = (int)System.currentTimeMillis(); 
//			System.out.print("[接收]" + fClientOrderID + ",用时:" + (n2 - n1) + "ms");
//			return fc.getResultStr("response", "true", "下单成功,客户订单号:" + fClientOrderID);  
		}catch(Exception e){
			return fc.getResultStr("response", "false", "下单失败,接口运行异常");
		}
	}
	//信息输出
	public void smg(String s){
//		System.out.print(s);
//		return;
	}
}