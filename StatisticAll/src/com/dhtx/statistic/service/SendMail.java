package com.dhtx.statistic.service;

import com.dhtx.statistic.util.fc;

/**
 * 类名称：SendMail   
 * 类描述：
 * 创建人: renfy   
 * 创建时间：2011-12-16 下午04:08:55   
 * @version 1.0
 *  
 */
public class SendMail {

	private static String to_title = "零点统计";
	private static String[] to_mails = {"z@dhtx.me","gaihl@dhtx.me","pengxh@dhtx.me"};
//	private static String[] to_mails = {"renfy@dhtx.me","gaihl@dhtx.me"};
//	private static String[] to_mails = {"renfy@dhtx.me"};
	
	/**
	 * 方法名称: send 
	 * 方法描述: 
	 * 创建人: renfy
	 * 创建时间: 2011-12-16 下午04:09:35
	 * @param emailMessage
	 * @version 1.0
	 * 
	 */ 
	public void send(String emailMessage) {
		for(String to_mail:to_mails){
			fc.sendEmail(to_mail, to_title, emailMessage);
		}
	}
}
