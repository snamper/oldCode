package com.dhtx.sign.servlet.app;

import java.util.TimerTask;

import com.dhtx.sign.dao.OrderDAO;
import com.dhtx.sign.pojo.Order;

/**
 * 类名称：CreateSignTimerTask   
 * 类描述：
 * 创建人: renfy   
 * 创建时间：2012-1-9 下午05:06:13   
 * @version 1.0
 *  
 */
public class CreateSignTimerTask extends TimerTask {

	/* (non-Javadoc)
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run() {
		try {
			OrderDAO odao = new OrderDAO();
			//获得一个订单
			Order order = odao.getOrderToApp();
			if(order != null){
				//锁定
				if(odao.lockOrder(order.getFid())){
					CreateSign cs = new CreateSign();
					cs.doSign(order);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
