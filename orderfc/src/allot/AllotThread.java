package allot;

/*
 * 订单分发线程
 */

public class AllotThread implements Runnable {

	String m_sID = ""; 

	//初始化参数
	public AllotThread(String sID) { 
		m_sID = sID;
	}
	
	//执行
	public void run() { 
		try { 
			//分发
			System.out.print("[线程分发]" + m_sID);
			PaymentCard ps = new PaymentCard();
			String s = ps.paymentStd_(m_sID, "");
			ps.destroy();
			System.out.print("[线程分发]" + m_sID + ">>" + s);
		} catch(Exception e) { 
			e.printStackTrace();
		}
	}

}
