package accept;

import common.*;
import java.sql.ResultSet;

/*
 * 返回网银支付的转向url
 */
public class WebPay {
	//static String WEBPAY_IP = "118.144.91.38:8080";

	//
	public static String PayResult(String orderID, String sMoney, String bankid, String sClient) {
		//
		try{
			String WebPlayType = "", WebApiIp = "";
			//选择网银通道并转换网银代码
			String sql = "SELECT c.fName, c.fReUrl, c.fOutTime, b.fType, b.fCode FROM CaPlatform as c, CaBankRule as b " +
					" where b.fType = c.fName and b.fBankID = '" + bankid + "' and c.fState = '0' order by b.fLevel desc";
			ResultSet rs = PayRequest.dc.query(sql);
			if (rs == null || !rs.next()){
				PayRequest.dc.CloseResultSet(rs);
				PayRequest.dc.execute("update CaOrder set fState = '5', fNoticeState='0', fFillMsg='当前银行代码不可用', " +
						"  fFillTime=GETDATE(), fOverTime=GETDATE(),  fMoney=0, fErrorCount = 0 where fid = '" + orderID + "'");
				System.out.print("[分发]" + orderID + ",当前银行代码不可用");
				return "ERROR,当前银行代码不可用";
			}
			//查询出分发参数			
			String sCode = fc.getrv(rs, "fCode", "");
			String sClientId = fc.getrv(rs, "fName", "");
			String sReUrl = fc.getrv(rs, "fReUrl", "");			
			String sOutTime = fc.getrv(rs, "fOutTime", "");				
			if (sOutTime.equals("")) sOutTime = "60";
			PayRequest.dc.CloseResultSet(rs);
			
			//更新订单状态, 不再用分发程序更新
			PayRequest.dc.execute("update CaOrder set fState='2', fGiveID = '" + sClientId + "', " +
					"  fOverTime=GETDATE() + 0.0000116 * " + sOutTime + " where fId = '" + orderID + "'");
			//
			if (!sClientId.equals(""))  WebPlayType = sClientId;	//如果不为空,就用指定的, 否则用默认的
			if (!sReUrl.equals("")) WebApiIp = sReUrl;
			//WebApiIp = "127.0.0.1:8080";			
			//19PAY网银
			String s = "ERROR007,无法处理当前类型的支付";
			if (WebPlayType.equals("19pay")) s = WebPay_19pay(WebApiIp, orderID, sMoney);
			if (WebPlayType.equals("alipay")) s = WebPay_zfb(WebApiIp, orderID, sMoney);
			if (WebPlayType.equals("kuaiqian")) s = WebPay_kuaiqian(WebApiIp, orderID, sMoney, sCode);
			if (WebPlayType.equals("shengft")) s = WebPay_shengft(WebApiIp, orderID, sMoney, sCode);
			if (WebPlayType.equals("xinsheng")) s = WebPay_xinsheng(WebApiIp, orderID, sMoney, sCode);
			return s;		
		}catch(Exception e){
			e.printStackTrace(); 
			return "ERROR007,处理网银支付时出错"; 
		}
	}

	private static String WebPay_xinsheng(String webApiIp, String orderID,
			String money, String bankid) {
		//新生网银
		try{
			String sURL = "http://" + webApiIp + "/ZfwebApi/xinsheng_order.jsp";
			String sParam = "orderid=" + orderID + "&money=" + money + "&bankid=" + bankid + "&sign=" +
				fc.getMd5Str(orderID + money + "H7489D648H5847548");
			return sURL + "?" + sParam;		
		}catch(Exception e){
			e.printStackTrace();
			return "ERROR007,处理网银支付时出错";
		}
	}

	private static String WebPay_shengft(String webApiIp, String orderID,
			String money, String bankid) {
		//盛付通网银
		try{
			String sURL = "http://" + webApiIp + "/ZfwebApi/sdo_SendOrder.jsp";
			String sParam = "orderid=" + orderID + "&money=" + money + "&bankid=" + bankid + "&sign=" +
				fc.getMd5Str(orderID + money + "H7489D648H5847548");
			return sURL + "?" + sParam;		
		}catch(Exception e){
			e.printStackTrace();
			return "ERROR007,处理网银支付时出错";
		}
	}

	private static String WebPay_kuaiqian(String WebApiIp, String orderID,	String sMoney, String bankid) {
		//快钱网银
		try{
			String sURL = "http://" + WebApiIp + "/ZfwebApi/kuaiqian_send.jsp";
			String sParam = "orderid=" + orderID + "&money=" + sMoney + "&bankid=" + bankid + "&sign=" +
				fc.getMd5Str(orderID + sMoney + "H7489D648H5847548");
			return sURL + "?" + sParam;		
		}catch(Exception e){
			e.printStackTrace();
			return "ERROR007,处理网银支付时出错";
		}
	}

	private static String WebPay_zfb(String WebApiIp, String orderID, String sMoney) {
		try{
			//支付宝网银
			String sURL = "http://" + WebApiIp + "/ZfwebApi/alipay_index.jsp";
			String sParam = "orderid=" + orderID + "&money=" + sMoney + "&sign=" +
				fc.getMd5Str(orderID + sMoney + "H7489D648H5847548");
			return sURL + "?" + sParam;		
		}catch(Exception e){
			e.printStackTrace();
			return "ERROR007,处理网银支付时出错";
		}
	}

	private static String WebPay_19pay(String WebApiIp, String orderID, String sMoney) {
		try{
			//19pay网银
			String sURL = "http://" + WebApiIp + "/ZfwebApi/19pay_index.jsp";
			String sParam = "orderid=" + orderID + "&money=" + sMoney + "&sign=" +
				fc.getMd5Str(orderID + sMoney + "H7489D648H5847548");	
			return "" + sURL + "?" + sParam + "";		
		}catch(Exception e){
			e.printStackTrace();
			return "ERROR007,处理网银支付时出错";
		}
	}


}
