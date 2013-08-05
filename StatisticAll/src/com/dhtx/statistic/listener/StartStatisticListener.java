package com.dhtx.statistic.listener;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.dhtx.statistic.service.StatisticAllTimerTask;
import com.dhtx.statistic.service.StatisticDate;

/**
 * 类名称：StartStatisticListener   
 * 类描述：
 * 创建人: renfy   
 * 创建时间：2011-12-16 下午02:48:07   
 * @version 1.0
 *  
 */
public class StartStatisticListener implements ServletContextListener{

	Timer timer =null;
	
	public void contextDestroyed(ServletContextEvent sce) {
		timer.cancel();
	}

	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("启动零点统计");
		timer = new Timer();
//		timer.schedule(new StatisticAllTimerTask(), StatisticDate.nextTime(), 1 * 30 * 1000);
		timer.schedule(new StatisticAllTimerTask(), StatisticDate.nextTime(), 24l * 60 * 60 * 1000);
	}	

}
