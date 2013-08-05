package accocheck;

/*
 * 湖南号段处理线程
 */
import common.*; 

public class HunanQBThread implements Runnable {

	   static DataConnect dc = new DataConnect("orderfill", false);
	   static int ConnectCount = 0;
	   static String sDate_ = fc.getTime("yyyyMMdd"), sMonth_ = fc.getTime("yyyyMM");
	
	String fID_ = ""; 
	String fNumber_ = ""; 
	String fPlayName_ = "";
	String fMoney_ = "";
	String fState_ = "";
	String fFillMsg_ = "";

	//初始化参数
	public HunanQBThread(String fID, String fNumber, String fPlayName, String fMoney, String fState, String fFillMsg) { 
		fID_ = fID;
		fNumber_ = fNumber;
		fPlayName_ = fPlayName;
		fMoney_ = fMoney;
		fState_ = fState;
		fFillMsg_ = fFillMsg;
		if (dc == null) dc = new DataConnect("orderfill", false);
		}
	
	//线程保存湖地号段充值数据
	public void run() { 
		try { 
			//每天一清号段错误次数
			System.out.print("[线程接收号段数据]" + fID_ + "," + fNumber_ + "," + fPlayName_ + "," + fMoney_ + "," + fState_);
			if (fID_.equals("")) return;
			if (fNumber_.equals("")) return;
			if (fPlayName_.equals("")) return;
			if (fMoney_.equals("")) return;
			if (fState_.equals("")) return;
			
			if (!sDate_.equals(fc.getTime("yyyyMMdd"))){
				String sql = "update AcNumberType set fErrorCount = 0 wehre fErrorCount <> '999'";
				int n = dc.execute(sql);
				sDate_ = fc.getTime("yyyyMMdd");
				System.out.print("[线程接收号段数据]每天一清号段错误次数," + n);
			}
			
			//每月一清号段当月量
			if (!sMonth_.equals(fc.getTime("yyyyMM"))){
				String sql = "update AcNumberType set fMonthValue = 0, fCount = 0 ";
				int n = dc.execute(sql);
				sMonth_ = fc.getTime("yyyyMM");
				System.out.print("[线程接收号段数据]每天一清号段错误次数," + n);
			}

			//更新号段总量
			String sql = "";
			if (fState_.equals("0")){
				//成功
				sql = "update AcNumberType set fErrorCount = 0, fCount = fCount + 1" +
					" ,fMonthValue = fMonthValue + " + fMoney_ + " where fID = '" + fNumber_.substring(0, 4) + "' ";
			}else{	
				//失败		
				sql = "update AcNumberType set fErrorCount = fErrorCount + 1" +
					" where fID = '" + fNumber_.substring(0, 4) + "' and fErrorCount <> 999";
			}
			int n = dc.execute(sql);
			System.out.print("[线程接收号段数据]更新号段总量," + n + "," + sql);

			//保存号段分析数据
			sql = "insert into AcNumberAnalysis (fID, fTime, fNumber, fPlayName, fMoney, fState, fFillMsg) " +
					"values ('" + fID_ + "', GETDATE(), '" + fNumber_ + "', '" + fPlayName_ + "'" +
							", '" + fMoney_ + "', '" + fState_ + "', '" + fFillMsg_ + "')";
			n = dc.execute(sql);
			System.out.print("[线程接收号段数据]保存号段分析数据," + n + "," + sql);
			
		} catch(Exception e) { 
			e.printStackTrace();
		}
	}

}
