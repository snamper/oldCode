package com.dhtx.statistic.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dhtx.statistic.util.fc;

/**
 * 类名称：StatisticDate 
 * 类描述：
 * 创建人: renfy 
 * 创建时间：2011-12-16 下午03:06:48
 * 
 * @version 1.0
 * 
 */
public class StatisticDate {
	private static Date date = null;
	private static SimpleDateFormat sdf = null;
	private final static int MINTIME= 24 * 60;
	static{
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	public static Date nextTime() {
		updateTime();
		return date;
	}

	public static void updateTime() {
		String dateStr = fc.getTime("yyyy-MM-dd", MINTIME) + " 00:00:00";
//		String dateStr = fc.getTime("yyyy-MM-dd HH:mm", 1) + ":00";
		System.out.println("~~~~~第一次执行统计时间~~~~~~~~~"+dateStr);
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
}
