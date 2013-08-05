package com.dhtx.statistic.service;

import java.util.List;
import java.util.TimerTask;

import com.dhtx.statistic.dao.StatisticDAO;

/**
 * 类名称：StatisticAllTimerTask   
 * 类描述：
 * 创建人: renfy   
 * 创建时间：2011-12-16 下午02:52:50   
 * @version 1.0
 *  
 */
public class StatisticAllTimerTask extends TimerTask{

	/* (non-Javadoc)
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run() {
		StatisticDAO sdao = new StatisticDAO();
		
		//1.当前各渠道(账号池->充值管理>平台资金)的资金合计 
		List<String[]> HQplatformMoney = sdao.getHQPlatformMoney();
		List<String[]> ZHCplatformMoney = sdao.getZHCPlatformMoney();
		
		//2.前一天QB充值成功总量，按渠道分
		List<String[]> qbMoney = sdao.getQBMoney();
		
		//3.前一天华奇充值成功总量，按渠道分
		List<String[]> huaqiMoney = sdao.getHuaQiMoney();
		
		//4.前一天财付通充值成功总量，按渠道分
		List<String[]> caiftMoney = sdao.getCFTMoney();
		
		//获得华奇库存卡面值总额
		String hqkcCard = sdao.getHQkcCardMoney();
		
		//获得财付通库存卡面值总额
		String cftcCard = sdao.getCFTkcCardMoney();
		
		//整理要发送的邮件内容
		PackInfo pack = new PackInfo();
		String emailMessage = pack.packAllInfo(HQplatformMoney,ZHCplatformMoney,qbMoney,huaqiMoney,caiftMoney,hqkcCard,cftcCard);
		
		//发送邮件
		SendMail sm = new SendMail();
		sm.send(emailMessage);
		
	}

}
