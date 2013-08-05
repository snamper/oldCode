package cost;

/*
 * 订单分发线程
 */
import common.*; 

public class CostThread implements Runnable {

	String m_sDefAisle = ""; 
	String m_sGUID = ""; 

	//初始化参数
	public CostThread(String sID, String sToClient) { 
		m_sGUID = sID;
		m_sDefAisle = sToClient;
		}
	
	//执行
	public void run() { 
		try { 
			//分发
			System.out.print("[线程退款]" + m_sGUID + "," + m_sDefAisle);
			AccountCost ps = new AccountCost();
			String s = ps.accountCostMoney(m_sGUID, "", "");
			System.out.print("[线程退款]" + m_sGUID + ">>" + s);
		} catch(Exception e) { 
			e.printStackTrace();
		}
	}

}
