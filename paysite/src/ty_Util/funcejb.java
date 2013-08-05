package ty_Util;

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
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import ty_Util.yanzheng;



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

	public static String sendEmail(String to_mail, String to_title, String to_content){
		try{
			//建立邮件会话
			Properties props=new Properties();//也可用Properties props = System.getProperties(); 
			props.put("mail.smtp.host","smtp.139.com");//存储发�?邮件服务器的信息
			props.put("mail.smtp.auth","true");//同时通过验证
			Session s=Session.getInstance(props);//根据属�?新建�?��邮件会话
			s.setDebug(true);
	
			//由邮件会话新建一个消息对�?
			MimeMessage message=new MimeMessage(s);//由邮件会话新建一个消息对�?
	
			//设置邮件
			InternetAddress from=new InternetAddress("13910783429@139.com");
			message.setFrom(from);//设置发件�?
			InternetAddress to=new InternetAddress(to_mail);
			message.setRecipient(Message.RecipientType.TO,to);//设置收件�?并设置其接收类型为TO
			message.setSubject(to_title);//设置主题
			message.setText(to_content);//设置信件内容
			message.setSentDate(new Date());//设置发信时间
	
			//发�?邮件
			message.saveChanges();//存储邮件信息
			Transport transport=s.getTransport("smtp");
			//以smtp方式登录邮箱,第一个参数是发�?邮件用的邮件服务器SMTP地址,第二个参数为用户�?第三个参数为密码
			transport.connect("smtp.139.com","13910783429@139.com","shiJia@314902");
			transport.sendMessage(message,message.getAllRecipients());//发�?邮件,其中第二个参数是�?��已设好的收件人地�?
			transport.close();
		}catch(MessagingException e){
			System.out.println("发送失败!"+e);
		}


		return "";
	}
	
	

	

	// 字符串替�?新串=replace(原串,�?换成�?
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

	// 处理中文问题的函�?
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

	// 处理中文问题的函�?
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

	// 取网页参�?不存在时返回""
	public static String getres(HttpServletRequest req, String s) {
		String str = "";
		//
		try {
			str = new String(req.getParameter(s).getBytes("ISO-8859-1"),
					"gbk");
			if (str == null) str = "";
		} catch (Exception e) {
		}
		boolean j=false;
		boolean c= yanzheng.isgb2312(str);
		if(c==false){//如果不为中文，验证商户输入的真实姓名是否含有非法字符
			j= yanzheng.hasElse(str);
			if(j){
				//str = replace(str, "\"", "");
				//str = replace(str, "'", "");
				//str = replace(str, ":", "");
				//str = replace(str, ";", "");
				//str = replace(str, ",", "");
				//
				return str;
			}else{
				return "";
			}
		}else{
			return str;
		}
		//boolean b= yanzheng.hasElse(str);
		
		//在这理处�?不良参数过滤问题
		//System.out.print(str);
		
	}
	public static String getresqu(HttpServletRequest req, String s) {
		String str = "";
		//
		try {
			str = new String(req.getParameter(s).getBytes("ISO-8859-1"),
					"gbk");
			if (str == null) str = "";
		} catch (Exception e) {
		}
		//在这理处�?不良参数过滤问题
		//System.out.print(str);
		str = replace(str, "=", "");
		str = replace(str, "'", "");
		str = replace(str, ";", "");
		str = replace(str, "*", "");
		str = replace(str, "^", "");
		str = replace(str, "&", "");
		str = replace(str, "|", "");
		str = replace(str, ",", "");
		str = replace(str, "<", "");
		str = replace(str, ">", "");
		//str = replace(str, "'", "''");
		//str = replace(str, ":", "");
		//str = replace(str, ";", "");
		//str = replace(str, ",", "");
		//
		return str;
	}

	// 取字段容�?出错时返回自定义的s2
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

	// 字串变数�?
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

	// 字串变数�?数小数点
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

	// 操作日志:写入文件或显示后�?内容包括操做过程和错语信�?
	public static void log(String s) {
		// 显示到后�?
		System.err.println(s);
		// 写入到文�?
	}

	// 向前台显示出错信�?
	public static void error(HttpServletResponse response, String s) {
		response.setContentType("text/html; charset=GBK");
		try {
			PrintWriter out = response.getWriter();
			out.println(s);
		} catch (Exception e) {

		}
	}

	// 产生�?��随机�?
	public static int ran(int upLimit, int downLimit) {
		return (int) (Math.random() * (upLimit - downLimit)) + downLimit;
	}

	// 返回�?��ID
	public static String getGUID(String sPrefix) {
		UUID uuid = UUID.randomUUID();
		String sGUID = uuid.toString();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		GregorianCalendar grc = new GregorianCalendar();
		grc.setTime(new Date());
		String sDate = sdf.format(grc.getTime());
		String s = sDate;
		sGUID = sGUID.replace("-", "");
		s = sDate + "-" + sPrefix + "-" + sGUID.substring(sGUID.length() - 4);
		return s;
	}
	
	public static String getGUIDs() {
		UUID uuid = UUID.randomUUID();
		String sGUID = uuid.toString();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		GregorianCalendar grc = new GregorianCalendar();
		grc.setTime(new Date());
		String sDate = sdf.format(grc.getTime());
		String s = sDate;
		sGUID = sGUID.replace("-", "");
		return sGUID;
	}

	/*
	 * 
	 * 	函数:SendDataViaPost($url,$port,$data)
	 * 	功能:通过POST发�?数据到指定页�?
	 *	参数:$url 接受�?$port 端口,$data 数据(形式�?xml=<root></root>&str=abcdefg)
	 *	备注:none
	 */  
	public static String SendDataViaPost(String sUrl, String sPort, String sData){
		  String s = "";
		try {
		  URL url;
		  url = new URL(sUrl);
	      HttpURLConnection con = (HttpURLConnection) url.openConnection();
	      con.setDoOutput(true); // POST方式
	      con.setRequestMethod("POST");
	      OutputStream os = con.getOutputStream(); // 输出流，写数�?
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

	public static String getTime(String sFormat){
		if (sFormat.equals("")) sFormat = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(sFormat);
		GregorianCalendar grc = new GregorianCalendar();
		grc.setTime(new Date());
		return sdf.format(grc.getTime());
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
			rightNow.add(Calendar.MINUTE, mm);//要加�?���?
			dt = rightNow.getTime();
			s = sdf.format(dt);
		}
		//
		return s;
	}
	/*
	 * 是否不包含字�?
	 */
	public static boolean IsNoComprise(String sData, String sValue){
		for (int i = 0; i < sData.length(); i ++)
			if (sValue.indexOf(sData.substring(i, 1)) > -1) {
				return false;
			}
		return true;
	}
	
	/*
	 * 是否在数�?
	 */
	public static boolean IsNmber(String sData){ 
		String sValue = "0123456789";
		for (int i = 0; i < sData.length(); i ++)
			if (sValue.indexOf(sData.substring(i, i + 1)) == -1) {
				return false;
			}
		return true;
	}
	
   	/*
   	 *返回md5�?
   	 */
}