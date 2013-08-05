package commfill;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.DataConnect;
import common.fc;

/**
 * Servlet implementation class for Servlet: ConfirmLogResult
 *
 * @web.servlet
 *   name="ConfirmLogResult"
 *   display-name="ConfirmLogResult" 
 *
 * @web.servlet-mapping
 *   url-pattern="/ConfirmLogResult"
 *  
 */
 public class ConfirmLogResult extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	   static final long serialVersionUID = 1L;
	   private static final String CONTENT_TYPE = "text/html; charset=GBK";
	   static int ConnectCount = 0;
	   static boolean isrerun = false;
	   static int AppCount = 0;
	   static int isrun = 0; 

   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public ConfirmLogResult() {
		super();
		ConnectCount = ConnectCount + 1;
		System.out.print("[poolfill.AcceptLog]新的实例,当前共有个" + ConnectCount + "实例");
//		if (dc == null){
//			dc = new DataConnect();		//公用一个连接
//			System.out.print("[poolfill.AcceptLog]第一个实例,初始化数据库连接");
//		}
	} 
	
	/* (non-Javadoc)
	 * @see javax.servlet.Servlet#destroy()
	 */
	public void destroy() {
		ConnectCount = ConnectCount - 1;
		if (ConnectCount <= 0){
//			dc.CloseConnect();
//			dc = null;
			System.out.print("[poolfill.AcceptLog]实例数为0,已关闭数据库连接");
		}
		super.destroy();
	}  		
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(confirmLogResult(request, response));
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(confirmLogResult(request, response));
	}   	  	 
	
	/*
	 * 手工确认充值结果, 传入充值记录id和状态和金额, 如果成功, 将充值记录设为成功, 读取对应的订单, 将订单金额减去, 置为等待分发.
	 */
	public String confirmLogResult(HttpServletRequest request, HttpServletResponse response){
		try{
			//接收参数
			String id = fc.getpv(request, "id");					//充值记录ID
			String state = fc.getpv(request, "state");				//充值状态
			String money = fc.getpv(request, "money");				//充值金额 
			String oper = fc.getpv(request, "oper");				//操作员 
			String give = fc.getpv(request, "giveid");				//分发平台 
			String sign = fc.getpv(request, "sign");				//md5
			//
			System.out.print("[手工确认]接收:" + id + "," + state + "," + money + "," + oper + "," + sign);
			//
			if (id.equals("")) return "id参数不能为空";
			//if (cid.equals("") && aid.equals("")) return "aid和cid不能都为空";
			if (state.equals("")) return "state参数不能为空";
			if (money.equals("")) return "money参数不能为空";
			if (sign.equals("")) return "sign参数不能为空";
			//
			if ("0,1,2,3".indexOf(state) == -1) return "充值状态不正确((0=充值成功,1=卡密失败,2=账号失败,3=下一通道))";
			try{
				float n = Float.valueOf(money).floatValue();
				if (n < 0) return "money参数必须大于等于0";
			}catch(Exception e){
				return "money参数类型不正确";
			}
			//
			String md5 = fc.getMd5Str(id + state + money + "DTMCp2eIWJyZw7EV");
			//if (!sign.equals(md5)) return "sign签名不合法," + id + state + money + "DTMCp2eIWJyZw7EV" + "," + md5 + "," + sign;
			
			//
			//-----事务开始-----
			DataConnect dc = new DataConnect("orderfill", false);;
			Statement stmt = null;
			try{
				//初始化
				dc.checkConnect();
				stmt = dc.getConnect().createStatement();
				if (stmt == null){
					dc.CloseConnect();
					return "连接失败";
				}
				dc.getConnect().setAutoCommit(false);		//开始事务,更改JDBC事务的默认提交方式
				int m = 0;				
				
				//转换
				if (state.equals("1")) money = "0";

				
				//更新充值记录
				String CardState = "", AccoState="", LogState = ""; 
				String COverTime = "", AOverTime = "";
				if (state.equals("0")){	//成功
					CardState = "4";
					AccoState = "2";
					LogState = "0";
					COverTime = "getdate()";
					AOverTime = "fOverTime";
				}
				if (state.equals("1")){	//卡密失败
					CardState = "5";//卡密失败
					AccoState = "2";//等待充值
					LogState = "1";//卡密失败
					COverTime = "getdate()";
					AOverTime = "fOverTime";
				}
				if (state.equals("2")){ //账号失败
					CardState = "2";	//等待充值
					AccoState = "9";	//账号失败
					LogState = "5";  	//账号失败
					COverTime = "fOverTime";
					AOverTime = "getdate()";
				}
				if (state.equals("3")){  //下一通道
					CardState = "5";//卡密失败
					AccoState = "5";//账号失败
					LogState = "3";//下一通道
					COverTime = "getdate()";
					AOverTime = "getdate()";
				}
				
				//如果是本地卡,除了卡密失败和手工确认,其它均强制转为未充状态(0)
				if (give.equals("waihu-bdk")){
					if (!state.equals("0") && !state.equals("1") && !state.equals("4")){
						CardState = "0";
					}
				}				
				//库存卡
				if (give.equals("waihu-kck")){
					if (!state.equals("0") && !state.equals("1") && !state.equals("4")){
						CardState = "0";
					}
				}
				
				//更新充值记录
				String sql = "update CoFillLog  set fState = '" + LogState + "', " +
						" fMoney='" + money + "' " +
						" where fFillOrderID='" + id + "' and fState = '4'";
				m = stmt.executeUpdate(sql);
				if (m != 1){
					dc.getConnect().rollback();	
					stmt.close();
					dc.CloseConnect();
					return "更新充值记录失败(" + m + ")";
				}

				//更新卡订单信息
				String sCardTable = "CaOrder";												//正常卡订单
				if (id.substring(0, 1).equals("K")) sCardTable = "CaOrderKck";				//如果是库存卡订单
				sql = "update " + sCardTable + " set fLockID='', fFillTime = GETDATE(), fOverTime = " + COverTime + ", fstate = '" + CardState 
					+ "', fMoney= " + money + " where fGiveOrderID='" + id + "' and fstate = '8'";
				m = stmt.executeUpdate(sql);
				if (m == -1){
					dc.getConnect().rollback();	
					stmt.close();
					dc.CloseConnect();
					return "更新订单失败(" + m + ")";
				}

				//更新号订单信息
				sql = "update AcOrder set fLockID='', fFillTime = GETDATE(), fOverTime = " + AOverTime + ", fstate = '" + AccoState 
					+ "', fLackMoney= fLackMoney -" + money + ", fFactMoney = fFactMoney + " + money +
						"  where fGiveOrderID='" + id + "' and fstate = '8'";
				m = stmt.executeUpdate(sql);
				if (m == -1){
					dc.getConnect().rollback();	
					stmt.close();
					dc.CloseConnect();
					return "更新订单失败(" + m + ")";
				}
				sql = "update AcOrder set fLockID='', fFillTime = GETDATE(), fOverTime = GETDATE() , fstate = '4' " +
					"  where fGiveOrderID='" + id + "' and fLackMoney <= 0";
				stmt.executeUpdate(sql);

				//提交事务
				dc.getConnect().commit();
				stmt.close();
				dc.CloseConnect();
				return "事务成功";
			}catch(Exception e){
				try{
					dc.getConnect().rollback();
				}catch(Exception eee){
				}
				try{
					stmt.close();
				}catch(Exception eeee){
				}
				try{
					dc.CloseConnect();
				}catch(Exception eee){
				}
				e.printStackTrace();
				return "事务失败";
			}
		}catch(Exception e){
			e.printStackTrace();
			return "意外错误:" + e.toString();
		}
		
	}
	
}