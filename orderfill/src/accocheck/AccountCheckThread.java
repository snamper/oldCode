package accocheck;

/*
 * 订单分发线程
 */
import common.*; 

public class AccountCheckThread implements Runnable {

	String sReUrl_ = ""; 
	String sFillID_ = ""; 
	String sProductID_ = "";
	String sPlayName_ = "";

	//初始化参数
	public AccountCheckThread(String sReUrl, String sFillID, String sProductID, String sPlayName) { 
		sReUrl_ = sReUrl;
		sFillID_ = sFillID;
		sProductID_ = sProductID;
		sPlayName_ = sPlayName;
		}
	
	//执行
	public void run() { 
		try { 
			String sArea = "";
			//1.移动手机号,验证地区
			if (sProductID_.equals("01") || sProductID_.equals("04") || sProductID_.equals("12")){
				//百度
				String sUrl = "http://api.showji.com/Locating/default.aspx?m=" + sPlayName_ + "&output=json&callback=querycallback";
				String ss = fc.SendDataViaGet2(sUrl, "UTF-8");
				String s = ss; //new String(ss.getBytes(), "GBK");
//				s = new String(s.getBytes("ISO-8859-1"), "GBK");
				sArea = fc.getString2(s, "\"Province\":\"", "\",") + "," + fc.getString2(s, "\"City\":\"", "\",");
				//ip138
				if (sArea.equals("")){
					sUrl = "http://www.ip138.com:8080/search.asp?action=mobile&mobile=" + sPlayName_;
					s = fc.SendDataViaGet(sUrl);
					s = fc.getString2(s, "卡号归属地", "</TR>");
					s = fc.delString(s, "<!--", "-->");
					s = fc.delString(s, "<", ">");
					sArea = fc.replace(s, "&nbsp;", ",");
				}
			}
			
			//3.返回
			String sUrl = sReUrl_ + "?fillid=" + sFillID_ + "&area=" + sArea.trim() ;
			String s = fc.SendDataViaGet(sUrl);
			//System.out.print("[检查地区]返回:" + s);
		} catch(Exception e) { 
			e.printStackTrace();
		}
	}

}
