package common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class msg {

//	static DataConnect dc = new DataConnect();
//
	public static void out(String s) {
		System.out.print(s);
	}
//
//
//	public static int log(String sType, int nLevel, String sInfo) {
//		return log("", "", sType, nLevel, sInfo);
//	}
//
//
//	public static int log(String sIp, String sName, String sType, int nLevel, String sInfo) {
//		// 得到时间
//		try{
//			//msg.out("[" + sType + "]" + sInfo);
//			//
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			GregorianCalendar grc = new GregorianCalendar();
//			grc.setTime(new Date());
//			String sDate = sdf.format(grc.getTime());
//			if (sInfo.length() > 254)
//				sInfo = sInfo.substring(0, 255);
//			String sTable = "";
//			sInfo = common.funcejb.replace(sInfo, "'", "''");
//			sTable = "log_work";	//默认
//			if (nLevel == 1) sTable = "log_work";	//操作记录
//			if (nLevel == 2) sTable = "log_work";	//业务错误
//			if (nLevel == 3) sTable = "log_work";	//系统错误
//			String sql = "insert into " + sTable + " (ftime, fip, fname, ftype, flevel, finfo) values('"
//					+ sDate + "', '" + sIp + "', '" + sName + "', '" + sType + "', '" + nLevel + "', '" + sInfo
//					+ "')";
//			if (dc == null)
//				dc = new DataConnect();
//			return dc.execute(sql);
//		}catch(Exception e){
//			System.out.print("[system.common.msg]写日志信息时出错!");
//			e.printStackTrace();
//			return -1;
//		}
//	}

}
