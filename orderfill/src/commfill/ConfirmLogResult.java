package commfill;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.DataConnect;
import common.fc;

/**
 * Servlet implementation class for Servlet: ConfirmLogResult
 *
 * @web.servlet
 *   name="ConfirmLogResult"
 *   display-name="ConfirmLogResult" 
 *
 * @web.servlet-mapping
 *   url-pattern="/ConfirmLogResult"
 *  
 */
 public class ConfirmLogResult extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	   static final long serialVersionUID = 1L;
	   private static final String CONTENT_TYPE = "text/html; charset=GBK";
	   static int ConnectCount = 0;
	   static boolean isrerun = false;
	   static int AppCount = 0;
	   static int isrun = 0; 

   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public ConfirmLogResult() {
		super();
		ConnectCount = ConnectCount + 1;
		System.out.print("[poolfill.AcceptLog]�µ�ʵ��,��ǰ���и�" + ConnectCount + "ʵ��");
//		if (dc == null){
//			dc = new DataConnect();		//����һ������
//			System.out.print("[poolfill.AcceptLog]��һ��ʵ��,��ʼ�����ݿ�����");
//		}
	} 
	
	/* (non-Javadoc)
	 * @see javax.servlet.Servlet#destroy()
	 */
	public void destroy() {
		ConnectCount = ConnectCount - 1;
		if (ConnectCount <= 0){
//			dc.CloseConnect();
//			dc = null;
			System.out.print("[poolfill.AcceptLog]ʵ����Ϊ0,�ѹر����ݿ�����");
		}
		super.destroy();
	}  		
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(confirmLogResult(request, response));
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(confirmLogResult(request, response));
	}   	  	 
	
	/*
	 * �ֹ�ȷ�ϳ�ֵ���, �����ֵ��¼id��״̬�ͽ��, ����ɹ�, ����ֵ��¼��Ϊ�ɹ�, ��ȡ��Ӧ�Ķ���, ����������ȥ, ��Ϊ�ȴ��ַ�.
	 */
	public String confirmLogResult(HttpServletRequest request, HttpServletResponse response){
		try{
			//���ղ���
			String id = fc.getpv(request, "id");					//��ֵ��¼ID
			String state = fc.getpv(request, "state");				//��ֵ״̬
			String money = fc.getpv(request, "money");				//��ֵ��� 
			String oper = fc.getpv(request, "oper");				//����Ա 
			String give = fc.getpv(request, "giveid");				//�ַ�ƽ̨ 
			String sign = fc.getpv(request, "sign");				//md5
			//
			System.out.print("[�ֹ�ȷ��]����:" + id + "," + state + "," + money + "," + oper + "," + sign);
			//
			if (id.equals("")) return "id��������Ϊ��";
			//if (cid.equals("") && aid.equals("")) return "aid��cid���ܶ�Ϊ��";
			if (state.equals("")) return "state��������Ϊ��";
			if (money.equals("")) return "money��������Ϊ��";
			if (sign.equals("")) return "sign��������Ϊ��";
			//
			if ("0,1,2,3".indexOf(state) == -1) return "��ֵ״̬����ȷ((0=��ֵ�ɹ�,1=����ʧ��,2=�˺�ʧ��,3=��һͨ��))";
			try{
				float n = Float.valueOf(money).floatValue();
				if (n < 0) return "money����������ڵ���0";
			}catch(Exception e){
				return "money�������Ͳ���ȷ";
			}
			//
			String md5 = fc.getMd5Str(id + state + money + "DTMCp2eIWJyZw7EV");
			//if (!sign.equals(md5)) return "signǩ�����Ϸ�," + id + state + money + "DTMCp2eIWJyZw7EV" + "," + md5 + "," + sign;
			
			//
			//-----����ʼ-----
			DataConnect dc = new DataConnect("orderfill", false);;
			Statement stmt = null;
			try{
				//��ʼ��
				dc.checkConnect();
				stmt = dc.getConnect().createStatement();
				if (stmt == null){
					dc.CloseConnect();
					return "����ʧ��";
				}
				dc.getConnect().setAutoCommit(false);		//��ʼ����,����JDBC�����Ĭ���ύ��ʽ
				int m = 0;				
				
				//ת��
				if (state.equals("1")) money = "0";

				
				//���³�ֵ��¼
				String CardState = "", AccoState="", LogState = ""; 
				String COverTime = "", AOverTime = "";
				if (state.equals("0")){	//�ɹ�
					CardState = "4";
					AccoState = "2";
					LogState = "0";
					COverTime = "getdate()";
					AOverTime = "fOverTime";
				}
				if (state.equals("1")){	//����ʧ��
					CardState = "5";//����ʧ��
					AccoState = "2";//�ȴ���ֵ
					LogState = "1";//����ʧ��
					COverTime = "getdate()";
					AOverTime = "fOverTime";
				}
				if (state.equals("2")){ //�˺�ʧ��
					CardState = "2";	//�ȴ���ֵ
					AccoState = "9";	//�˺�ʧ��
					LogState = "5";  	//�˺�ʧ��
					COverTime = "fOverTime";
					AOverTime = "getdate()";
				}
				if (state.equals("3")){  //��һͨ��
					CardState = "5";//����ʧ��
					AccoState = "5";//�˺�ʧ��
					LogState = "3";//��һͨ��
					COverTime = "getdate()";
					AOverTime = "getdate()";
				}
				
				//����Ǳ��ؿ�,���˿���ʧ�ܺ��ֹ�ȷ��,������ǿ��תΪδ��״̬(0)
				if (give.equals("waihu-bdk")){
					if (!state.equals("0") && !state.equals("1") && !state.equals("4")){
						CardState = "0";
					}
				}				
				//��濨
				if (give.equals("waihu-kck")){
					if (!state.equals("0") && !state.equals("1") && !state.equals("4")){
						CardState = "0";
					}
				}
				
				//���³�ֵ��¼
				String sql = "update CoFillLog  set fState = '" + LogState + "', " +
						" fMoney='" + money + "' " +
						" where fFillOrderID='" + id + "' and fState = '4'";
				m = stmt.executeUpdate(sql);
				if (m != 1){
					dc.getConnect().rollback();	
					stmt.close();
					dc.CloseConnect();
					return "���³�ֵ��¼ʧ��(" + m + ")";
				}

				//���¿�������Ϣ
				String sCardTable = "CaOrder";												//����������
				if (id.substring(0, 1).equals("K")) sCardTable = "CaOrderKck";				//����ǿ�濨����
				sql = "update " + sCardTable + " set fLockID='', fFillTime = GETDATE(), fOverTime = " + COverTime + ", fstate = '" + CardState 
					+ "', fMoney= " + money + " where fGiveOrderID='" + id + "' and fstate = '8'";
				m = stmt.executeUpdate(sql);
				if (m == -1){
					dc.getConnect().rollback();	
					stmt.close();
					dc.CloseConnect();
					return "���¶���ʧ��(" + m + ")";
				}

				//���ºŶ�����Ϣ
				sql = "update AcOrder set fLockID='', fFillTime = GETDATE(), fOverTime = " + AOverTime + ", fstate = '" + AccoState 
					+ "', fLackMoney= fLackMoney -" + money + ", fFactMoney = fFactMoney + " + money +
						"  where fGiveOrderID='" + id + "' and fstate = '8'";
				m = stmt.executeUpdate(sql);
				if (m == -1){
					dc.getConnect().rollback();	
					stmt.close();
					dc.CloseConnect();
					return "���¶���ʧ��(" + m + ")";
				}
				sql = "update AcOrder set fLockID='', fFillTime = GETDATE(), fOverTime = GETDATE() , fstate = '4' " +
					"  where fGiveOrderID='" + id + "' and fLackMoney <= 0";
				stmt.executeUpdate(sql);

				//�ύ����
				dc.getConnect().commit();
				stmt.close();
				dc.CloseConnect();
				return "����ɹ�";
			}catch(Exception e){
				try{
					dc.getConnect().rollback();
				}catch(Exception eee){
				}
				try{
					stmt.close();
				}catch(Exception eeee){
				}
				try{
					dc.CloseConnect();
				}catch(Exception eee){
				}
				e.printStackTrace();
				return "����ʧ��";
			}
		}catch(Exception e){
			e.printStackTrace();
			return "�������:" + e.toString();
		}
		
	}
	
}