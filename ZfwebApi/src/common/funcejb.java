package common;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.sql.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.imageio.ImageIO;



/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public final class funcejb {
	static int n = 0;
	//static DataConnect dc = new DataConnect();

//	public static String ValidityCheck(String sTableField, String sValue) {
//		// 得到卡检验信息
//		try {
//
//			//DataConnect dc = new DataConnect();
//			ResultSet rs = dc.query("select * from validityCheck where fTableField='"
//							+ sTableField + "'");
//			String sAlias = "";
//			if (rs.next()) {
//				sAlias = rs.getString("fAlias").trim();
//				//检测长度
//				int nLength = rs.getInt("fMaxLength");
//				if ((sValue.length() != 0) && (sValue.length() != nLength)){
//					return "\"" + sAlias + "\" 长度应为 " + nLength ;
//				}
//				//检测有效字段: 字符应在这个范围内
//				String sComprise = rs.getString("fComprise").trim();
//				for (int i = 0; i < sValue.length(); i++){
//					if (sValue.indexOf(sComprise.substring(i, i + 1)) == -1){
//						return "\"" + sAlias + "\" 内包含非法字符, 有效字符:" + sComprise;
//					}
//				}
//				//检测非法字段: 不应包括XXXX字符
//				String sNoComprise = rs.getString("fNoComprise").trim();
//				for (int i = 0; i < sValue.length(); i++){
//					if (sValue.indexOf(sNoComprise.substring(i, i + 1)) > -1){
//						return "\"" + sAlias + "\" 内包含非法字符, 不应含有字符:" + sComprise;
//					}
//				}
//				//检测非法字段: 不应包括XXXX字符
//				boolean bIsNull = rs.getBoolean("fIsNull");
//					if (bIsNull && (sValue.equals(""))){
//						return "\"" + sAlias + "\" 不能为空!";
//					}
//			}
//			return "";
//		} catch (Exception e) {
//			msg.log("funcejb.ValidityCheck", 3, "检查数据有效性时异常:" + e.toString());
//			return "";
//		}
//	}
//...
	
	
	/*
	 * 返回串
	 */ 
	public static String getString(String sData, String sBeginStr, String sEndStr){
		int n1 = sData.toLowerCase().indexOf(sBeginStr.toLowerCase());
		int n2 = sData.toLowerCase().indexOf(sEndStr.toLowerCase());
		if ((n1 > -1) && (n2 > -1) && (n1 < n2)){
			return sData.substring(n1 + sBeginStr.length(), n2);
		}else
			return "";
	}
	
	// 取网站路径
	public static String getRootPath(HttpServletRequest req) {
		String s = req.getRealPath(req.getServletPath());
		int i = s.indexOf(".war\\");
		if (i != -1)
			s = s.substring(0, i + 5);
		//
		// 调试时用开发目录中的路径，实际运行时取运行的临时目录
		s = "D:\\Sun\\workspace\\cardweb\\WebContent\\";
		//
		return s;
	}

	// 字符串替换 新串=replace(原串,啥,换成啥)
	public static java.lang.String replace(String strSource, String strFrom,
			String strTo) {
		java.lang.String strDest = "";
		int intFromLen = strFrom.length();
		int intPos;
		while ((intPos = strSource.indexOf(strFrom)) != -1) {
			strDest = strDest + strSource.substring(0, intPos);
			strDest = strDest + strTo;
			strSource = strSource.substring(intPos + intFromLen);
		}
		strDest = strDest + strSource;

		return strDest;
	}

	// 处理中文问题的函数
	public static String getstr(String str) {
		try {
			String temp_p = str;
			byte[] temp_t = temp_p.getBytes("ISO8859-1");
			String temp = new String(temp_t);
			return temp;
		} catch (Exception e) {
		}
		return "unll";
	}

	// 处理中文问题的函数
	public static String getUTF8(String str) {
		try {
			String temp_p = str;
			byte[] temp_t = temp_p.getBytes("utf-8");
			String temp = new String(temp_t);
			return temp;
		} catch (Exception e) {
		}
		return "";
	}

	// 取网页参数,不存在时返回""
	public static String getres(HttpServletRequest req, String s) {
		String str = "";
		//
		try {
			str = new String(req.getParameter(s).getBytes("ISO-8859-1"),
					"gbk");
			if (str == null) str = "";
		} catch (Exception e) {
		}
		//在这理处理,不良参数过滤问题
		//System.out.print(str);
		//str = replace(str, "\"", "");
		str = replace(str, "'", "");
		//str = replace(str, ":", "");
		//str = replace(str, ";", "");
		//str = replace(str, ",", "");
		//
		return str;
	}

	// 取字段容错 出错时返回自定义的s2
	public static String gets(ResultSet rs, String s1, String s2) {
		// String str = "&nbsp;";
		String str = "";
		try {
			str = rs.getString(s1).trim();
		} catch (Exception e) {
			str = s2;
		}
		return str;
	}

	// 字串变数值
	public static int toint(String s) {
		char t;
		int n = 0, i = 0, l = s.length(), j = 0;
		n = 0;
		i = 0;
		l = s.length();
		j = 0;
		if (l == 0)
			n = 1;
		while (i < l) {
			t = s.charAt(i);
			switch (t) {
			case '0':
				j = 0;
				break;
			case '1':
				j = 1;
				break;
			case '2':
				j = 2;
				break;
			case '3':
				j = 3;
				break;
			case '4':
				j = 4;
				break;
			case '5':
				j = 5;
				break;
			case '6':
				j = 6;
				break;
			case '7':
				j = 7;
				break;
			case '8':
				j = 8;
				break;
			case '9':
				j = 9;
				break;
			default:
				n = 1;
				break;
			}
			n = n * 10 + j;
			i++;
		}
		return n;
	}

	// 字串变数值,数小数点
	public static float toxs(String s) {
		char t;
		int f = 0, div = 1, i = 0, l = s.length(), j = 0;
		f = 0;
		div = 1;
		n = 0;
		i = 0;
		l = s.length();
		j = 0;
		float n = 0;
		if (l == 0)
			n = 1;
		while (i < l) {
			t = s.charAt(i);
			switch (t) {
			case '0':
				j = 0;
				break;
			case '1':
				j = 1;
				break;
			case '2':
				j = 2;
				break;
			case '3':
				j = 3;
				break;
			case '4':
				j = 4;
				break;
			case '5':
				j = 5;
				break;
			case '6':
				j = 6;
				break;
			case '7':
				j = 7;
				break;
			case '8':
				j = 8;
				break;
			case '9':
				j = 9;
				break;
			case '.':
				f = 1;
				break;
			default:
				n = 1;
				break;
			}
			if (t != '.')
				n = n * 10 + j;
			if (f == 1)
				div = div * 10;
			// System.err.println("---"+n+" "+div);
			i++;
		}
		if (f == 1)
			n = n / div * 10;
		return n;
	}

	// 操作日志:写入文件或显示后台,内容包括操做过程和错语信息
	public static void log(String s) {
		// 显示到后台
		System.err.println(s);
		// 写入到文件
	}

	// 向前台显示出错信息
	public static void error(HttpServletResponse response, String s) {
		response.setContentType("text/html; charset=GBK");
		try {
			PrintWriter out = response.getWriter();
			out.println(s);
		} catch (Exception e) {

		}
	}

	// 产生一个随机数
	public static int ran(int upLimit, int downLimit) {
		return (int) (Math.random() * (upLimit - downLimit)) + downLimit;
	}

	// 返回一个ID
	public static String getGUID(String sPrefix) {
		UUID uuid = UUID.randomUUID();
		String sGUID = uuid.toString();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		GregorianCalendar grc = new GregorianCalendar();
		grc.setTime(new Date());
		String sDate = sdf.format(grc.getTime());
		String s = sDate;
		sGUID = sGUID.replace("-", "");
		s = funcejb.getTime("yyMMddHHmmss") + "-" + sPrefix + "-" + sGUID.substring(sGUID.length() - 4);
		return s;
	}

	/*
	 * 
	 * 	函数:SendDataViaPost($url,$port,$data)
	 * 	功能:通过POST发送数据到指定页面
	 *	参数:$url 接受页,$port 端口,$data 数据(形式为&xml=<root></root>&str=abcdefg)
	 *	备注:none
	 */  
	public String SendDataViaPost(String sUrl, String sPort, String sData){
		  String s = "";
		try {
		  URL url;
		  url = new URL(sUrl);
	      HttpURLConnection con = (HttpURLConnection) url.openConnection();
	      con.setDoOutput(true); // POST方式
	      con.setRequestMethod("POST");
	      OutputStream os = con.getOutputStream(); // 输出流，写数据
	      //
	      s = "POST " + url.getFile() + " HTTP/1.1" + "\r\n" + 
					"Host: " + url.getHost() + "\r\n" +
					"Content-Length: " + sData.length() + "\r\n" +
					"Content-Type: application/x-www-form-urlencoded" + "\r\n" +
					"Connection: close " + "\r\n\r\n";
	      //
	      os.write(s.getBytes());
	      BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream())); // 读取结果
	      String line;
	      while ((line = reader.readLine()) != null) {
	        s = s + line;
	      }
		return s;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	
	
	/*
	 * 是否包含字符
	 */
	public static boolean IsComprise(String sData, String sValue){
		for (int i = 0; i < sData.length(); i ++)
			if (sValue.indexOf(sData.substring(i, 1)) == -1) {
				return false;
			}
		return true;
	}

	/*
	 * 是否在数字
	 */
	public static boolean IsNmber(String sData){ 
		String sValue = "0123456789";
		for (int i = 0; i < sData.length(); i ++)
			if (sValue.indexOf(sData.substring(i, i + 1)) == -1) {
				return false;
			}
		return true;
	}
	
	public static String getTime(String sFormat){
		return getTime(sFormat, 0);
	}

	public static String getTime(String sFormat, int mm){
		if (sFormat.equals("")) sFormat = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(sFormat);
		GregorianCalendar grc = new GregorianCalendar();
		grc.setTime(new Date());
		String s = sdf.format(grc.getTime());
		//
		if (mm != 0){
			Date dt=sdf.parse(s,new ParsePosition(0));
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(dt);
			rightNow.add(Calendar.MINUTE, mm);//要加一分钟 
			dt = rightNow.getTime();
			s = sdf.format(dt);
		}
		//
		return s;
	}
	
	/*
	 * 是否不包含字符
	 */
	public boolean IsNoComprise(String sData, String sValue){
		for (int i = 0; i < sData.length(); i ++)
			if (sValue.indexOf(sData.substring(i, 1)) > -1) {
				return false;
			}
		return true;
	}
	
   	/*
   	 *返回md5串 
   	 */
	public static String getMd5Str(String s){
		MD5 md5 = new MD5();
		String s1 = md5.getMD5(s.getBytes());
		md5 = null;
		return s1;
	}
}