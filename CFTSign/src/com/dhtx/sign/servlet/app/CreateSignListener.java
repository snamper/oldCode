package com.dhtx.sign.servlet.app;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.dhtx.sign.util.Tools;

/**
 * 类名称：CreateSignListener   
 * 类描述：
 * 创建人: renfy   
 * 创建时间：2012-1-9 下午05:03:11   
 * @version 1.0
 *  
 */
public class CreateSignListener implements ServletContextListener {

	private Timer timer = null;
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		
		System.out.println("销毁签名生成定时器");
		timer.cancel();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		
		Tools.checkFileExists("c:/cftsign");
		//
		timer = new Timer(true);
		System.out.println("启动签名生成定时器");
		timer.schedule(new CreateSignTimerTask(),3 * 1000,2 * 1000);

	}

}
