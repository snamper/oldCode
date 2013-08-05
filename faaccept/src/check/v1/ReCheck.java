package check.v1;


import java.util.TimerTask;


import common.*;

public class ReCheck extends TimerTask {

	static DataConnect dc = new DataConnect("orderfill", false);
	static int nTime = 0;
	String sRunDate = "";

	/*
	 * 超时订单处理
	 */
	public void run() {
//		Listener.isrun = true;
		if (ListenerCheck.isrun) return ;
		ListenerCheck.isrun = true;
		// 
		try{
			OrderCheck oc = new OrderCheck();
			oc.orderCheck("", "");
		}catch(Exception e){
			System.out.print("[账号退款]处理时失败," + e.toString());
		}
		ListenerCheck.isrun = false;
	}
	
	

}
