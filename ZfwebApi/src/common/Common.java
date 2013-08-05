package common;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import common.DataConnect;

public class Common {
	//static DataConnect dc = new DataConnect();
	/*
	 * 
	 * ����:SendDataViaPost($url,$port,$data) ����:ͨ��POST������ݵ�ָ��ҳ�� ����:$url ����ҳ,$port
	 * �˿�,$data ���(��ʽΪ&xml=<root></root>&str=abcdefg) ��ע:none
	 */
	public static String SendDataViaPost(String sUrl, String sData) {
		String s = "";
		try { 
			URL url;
			url = new URL(sUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true); // POST��ʽ
			con.setRequestMethod("POST");
			OutputStream os = con.getOutputStream(); // �����д���
			//
			s = sData;
			//
			System.out.println(s);
			os.write(s.getBytes());
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					con.getInputStream())); // ��ȡ���
			String all = "", line;
			while ((line = reader.readLine()) != null) {
				all = all + line;
			}
			return all;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print(e.toString());
		}
		return s;
	}

	/*
	 * ����Post����
	 */
	public static String getPostContent(HttpServletRequest request) {
		ServletInputStream is;
		try {
			is = request.getInputStream();
			ByteArrayOutputStream baos = null;
			baos = new ByteArrayOutputStream();
			int iLength = 1024;
			int bytesRead = 0;
			byte[] buff = new byte[iLength];
			is.skip(0);
			while (true) {
				bytesRead = is.read(buff);
				if (bytesRead < 1)
					break;
				baos.write(buff, 0, bytesRead);
			}
			//
			String s = new String(baos.toByteArray(), "GBK");
			int n = s.indexOf("&xml=<root>");
			if (n > -1) 
				s = s.substring(n);		
			else{
				System.out.print("δ����Post��ݱ�ʶ(&xml=<root>):" + s);
			}
			//
			return s;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return "";
	}
	
	//���ز���ֵ: &aaa=1&bbb=2&ccc=3
	public static String getParameter(String sContext, String sName){
		return getParameter(sContext, sName, "&");
	}
	
	//���ز���ֵ: &aaa=1&bbb=2&ccc=3
	public static String getParameter(String sContext, String sName, String sSeparator){
		String s = sSeparator + sContext;
		String r = "";
		int n = s.indexOf(sSeparator + sName + "=");
		if (n > -1){
			s = s.substring(n + (sSeparator + sName + "=").length());
			n = s.indexOf(sSeparator);
			if (n > -1)
				r = s.substring(0, n);
			else
				r = s;			
		}
		return r;
	}
	
	public static String getParameterNo(String sContext, String sName){
		String s = sContext;
		String r = "";
		int n = s.indexOf("&" + sName + "=");
		if (n > -1){
			String sl = s.substring(0, n);
			String sr = s.substring(n + ("&" + sName + "=").length());
			n = sr.indexOf("&");
			if (n > -1)
				r = sl + sr.substring(n + 1);
			else
				r = sl;			
		}
		return r;
	}
	
	/*
	 * д��־����
	 * sType : ��ȡ�˺�RQA:RequestAccount,���ն���RCA:ReceiveAccount,��!���SR:SendResult;
	 * 			���չ���IC:inceptCard:,��ֵFC:fillcard
	 * sLevel : 0=����, 1=��Ϣ, 2=����, 3=����
	 * sInfo  : ������Ϣ,�ؼ��,���˵�,���õ�
	 */
//	public static int log(String sType, int nLevel, String sInfo){
//		//�õ�ʱ��
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		GregorianCalendar grc = new GregorianCalendar();
//		grc.setTime(new Date());
//		String sDate = sdf.format(grc.getTime());
//		if (sInfo.length() > 254) sInfo = sInfo.substring(0, 255);
//		String sql = "insert into log (ftime, ftype, flevel, finfo) values('" 
//			+ sDate + "', '" + sType + "', '" + nLevel + "', '" + sInfo + "')";
//		if (dc == null)  dc = new DataConnect();
//		return dc.execute(sql);
//	}
//	
	
//	public static String getConfig(String fName){
//		String sql = "select * from config fname='" + fName + "'";
//		ResultSet rs = dc.query(sql);
//		try{
//			if (rs.next()){
//				return rs.getString("fvalue");
//			}else{
//				return "";
//			}
//		}catch(Exception e){
//			return "";
//		}
//	}
	
	/*
	 * ���ض�����Ϣ
	 */
//	private String getOrderInfo(String sData, String sName) {
//		try {
//			String sValue = "";
//			int k = 0;
//			int n = sData.indexOf(sName + "=");	
//			if (n > -1){
//				sData = sData.substring(n + sName.length() + 1);	
//				n = sData.indexOf("&");
//				if (n > 0){
//					sValue = sData.substring(0, n);	
//				}
//			}
//			return sValue;
//		} catch (Exception e) {
//			Common.log("account.Common.getOrderInfo", 3, e.toString());
//			e.printStackTrace();
//			return "";
//		}
//	}

	
}
