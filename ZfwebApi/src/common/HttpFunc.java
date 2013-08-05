package common;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import common.DataConnect;

public class HttpFunc {

	//static DataConnect dc = new DataConnect();
	/*
	 * 
	 * 函数:SendDataViaPost($url,$port,$data) 功能:通过POST发送数据到指定页面 参数:$url 接受页,$port
	 * 端口,$data 数据(形式为&xml=<root></root>&str=abcdefg) 备注:none
	 */
	public static String SendDataViaPost(String sUrl, String sData) {
		String s = "", all = "";
		try { 
			URL url;
			url = new URL(sUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			try{
				con.setDoOutput(true); // POST方式
				con.setRequestMethod("POST");
				OutputStream os = con.getOutputStream(); // 输出流，写数据
				//
				s = sData;
				//
				//System.out.println(s);
				os.write(s.getBytes());
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						con.getInputStream())); // 读取结果
				String line;
				while ((line = reader.readLine()) != null) {
					all = all + line;
				}
				//
				reader.close();
				os.close();
				con.disconnect();
			}catch(Exception e){
				con.disconnect();
				all = "";
			}
			
			//
			return all;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print(e.toString());
			return "";
		}
	}


	public static String SendDataViaGet(String sUrl) {
		String s = "", all = "";
		try { 
			URL url;
			url = new URL(sUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			try{
				con.setDoOutput(true); // POST方式
				con.setRequestMethod("GET");
				OutputStream os = con.getOutputStream(); // 输出流，写数据
				//
				//s = sData;
				//
				//System.out.println(s);
				//os.write(s.getBytes());
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						con.getInputStream())); // 读取结果
				String line;
				while ((line = reader.readLine()) != null) {
					all = all + line;
				}
				//
				reader.close();
				os.close();
				con.disconnect();
			}catch(Exception e){
				con.disconnect();
				all = "";
			}
			
			//
			return all;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print(e.toString());
			return "";
		}
	}

/*
	 * 返回Post内容
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
				System.out.print("未发现Post数据标识(&xml=<root>):" + s);
			}
			//
			return s;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return "";
	}
	
	//返回参数值: &aaa=1&bbb=2&ccc=3
	public static String getParameter(String sContext, String sName){
		return getParameter(sContext, sName, "&");
	}
	
	//返回参数值: &aaa=1&bbb=2&ccc=3
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
	

}
