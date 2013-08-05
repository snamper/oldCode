package com.dhtx.sign.util;

import java.io.File;
import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dhtx.sign.util.global.fc;

/**
 * 类名称：Tools   
 * 类描述：
 * 创建人: renfy   
 * 创建时间：2012-1-4 下午01:10:37   
 * @version 1.0
 *  
 */
public class Tools {
	
	private static String currPictureURL = "c:/cftsign/";
	private static String defaultName = "defaults";

	public static Object getRandomObj(List list){
		Object obj = null;
		if(list != null && !list.isEmpty()){
			int num = fc.ran(0, list.size()-1);
			try {
				obj = list.get(num);
			} catch (Exception e) {
				e.printStackTrace();
				obj = list.get(0);
			}
		}
		return obj;
		
	}
	
	
	public static String getBasePath(HttpServletRequest request)
	throws UnknownHostException {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		return basePath;
	}
	
	public static String getFolderName(){

		String fname = "";
		try {
			fname = fc.getTime("yyyyMMdd");
		} catch (Exception e) {
			fname = "";
		}
		if("".equals(fname)){
			fname = defaultName;
		}
		
		return currPictureURL + fname;
	}
	
	

	public static boolean checkFileExists(String pathname) {
		boolean flag = false;
		try {
			File file = new File(pathname);
			if (file.exists()) {
				flag = true;
			}else{
				flag = file.mkdir();
				System.out.println("创建文件夹:"+ file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}
	
	
	public static String modifyDateStyle(String dateTime) {
		try {
			return dateTime.substring(0, 20);
		} catch (Exception e) {
			return dateTime;
		}
	}
	
	public static void main(String[] args) {
//		Tools.checkFileExists("c:/a/c");
	}
}
