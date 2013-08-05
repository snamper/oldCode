package accofill;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.*;

/*
 * ���˺ų���, ���ؿɳ�ֵ�˺�
 */

/**
 * Servlet implementation class for Servlet: GetAccount
 *
 * @web.servlet
 *   name="GetAccount"
 *   display-name="GetAccount" 
 *
 * @web.servlet-mapping
 *   url-pattern="/GetAccount"
 *  
 */
 public class GetAccount extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	   static final long serialVersionUID = 1L;
	   private static final String CONTENT_TYPE = "text/html; charset=GBK";
	   static DataConnect dc = new DataConnect("orderfill", false);
	   static int ConnectCount = 0;
	   static byte[] lock = new byte[0]; // �����instance����
	   static boolean isrekeyi = false;
	   static boolean isrerun = false;
	   static int AppCount = 0;
	   static int isrun = 0;
	   static String version = "";		//��ֵ����汾,���ڸ���
	   static String updateapp = ""; 	//����ָ����Щ�һ����и���
	   
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public GetAccount() {
		super();
		ConnectCount = ConnectCount + 1;
		System.out.print("[poolfill.GetAccount]�µ�ʵ��,��ǰ���и�" + ConnectCount + "ʵ��");
//		if (dc == null){
//			dc = new DataConnect();		//����һ������
//			System.out.print("[poolfill.GetAccount]��һ��ʵ��,��ʼ�����ݿ�����");
//		}
	} 
	
	/* (non-Javadoc)
	 * @see javax.servlet.Servlet#destroy()
	 */
	public void destroy() {
		ConnectCount = ConnectCount - 1;
		if (ConnectCount <= 0){
			dc.CloseConnect();
			dc = null;
			System.out.print("[poolfill.GetAccount]ʵ����Ϊ0,�ѹر����ݿ�����");
		}
		super.destroy();
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(getAccount(request, response));
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(getAccount(request, response));
	}   	  	    
	
	/*
	 * ȡ�Žӿ�: ��ȡ��, ��ȡ��:�ÿ���ֵ���˺Ż���й���
	 */
	public String getAccount(HttpServletRequest request, HttpServletResponse response){
		try{
			//���ղ���
			String sGiveClient = fc.getpv(request, "giveid");			//*ȡ��ͨ��ID: ��: tianshi-01,19pay-05,enlian-02��
			String sign = fc.getpv(request, "sign");
			//
			String sFillID = fc.getpv(request, "fillid");				//��ֵ������,������ֵ���غ�,��Ҫ�ӱ��غ�ģʽ.
			String sMinPrice = fc.getpv(request, "minprice");   		//��ǰ��ֵ,���Ϊ��,����������ƥ�估��ֵ����
			String sProgramId = fc.getpv(request, "appid");				//��ֵ����Id, ���Ӧ��Ψһ��
			String sOperType = fc.getpv(request, "skipzt");				//�Ƿ�������;��ͬ�˺ż��
			String sQueueID = fc.getpv(request, "queue");				//���к�

			//
			smg("[" + sProgramId + "]ȡ��:" + sGiveClient + "," + sFillID + "," + sMinPrice);

			//У��Ϸ���
//			if (!sign.equals(fc.getMd5Str("A74B766AEAE474EAAEF8465A6A0A4885"))) return "ERROR_MD5";
			if (sGiveClient.equals("")) return gr("false", "giveid��������Ϊ��"); 
			if (!sMinPrice.equals("") && !fc.isPlusFloat(sMinPrice)) return gr("false", "minprice����������ֵ");
			
			//2.ȡ����ֵ����
			ResultSet rs = null;
			sGiveClient = "'" + fc.replace(sGiveClient, ",", "','") + "'"; 
			sProgramId = sProgramId + "." + fc.getTime("HHmmss") + "." + fc.ran(10000, 99999);
			
			//ȡ�Ž�����
			String sPriceRule = "";
			String sProductID = fc.getString2(sGiveClient, "-", "'");//.substring(sGiveClient.length() - 2);
			if (!sMinPrice.equals("")){
				String sql = "select top 1 * from AcMoneyRule where fInsideID = '" + sProductID + "'" +
						" and fMinPrice <= " + sMinPrice + " and fMaxPrice >= " + sMinPrice + " and fState = '0'";
				rs = dc.query(sql); 
				if (rs != null && rs.next()){
					sPriceRule = fc.getrv(rs, "fMoneyList", "");
				}
				dc.CloseResultSet(rs);
				sPriceRule = fc.replace(sPriceRule, ";", " OR ");
				sPriceRule = fc.replace(sPriceRule, ",", " AND ");
				sPriceRule = fc.replace(sPriceRule, "=", " fLackMoney=");
				sPriceRule = fc.replace(sPriceRule, ">", " fLackMoney>");
				sPriceRule = fc.replace(sPriceRule, "<", " fLackMoney<");
				if (!sPriceRule.equals("")) System.out.print("[ȡ������]" + sPriceRule);
			}
			
			//��ȡ����
			String sLockSql = "";
			if (!sQueueID.equals("")) sQueueID = " (fQueueID = '" + sQueueID + "') AND ";
			
			//ֱ���������ϵ�״̬, ��ΪҪ�ĸ�ƽ̨��ֵ,�Ѿ������, like my% ��ʾmy��ϵ�г�ֵ
			sLockSql = "SELECT TOP 1 fid FROM AcOrder " +				
			  			" WHERE " +sQueueID + " (fGiveTime > GETDATE()) and (fLockId = '') " +
			  			" AND (fState = '2') and (fGiveId in (" + sGiveClient + ")) ";
			if (!sMinPrice.equals("")) sLockSql = sLockSql + " AND (" + sMinPrice + " <= fLackMoney)";
			if (!sPriceRule.equals("")) sLockSql = sLockSql + " AND (" + sPriceRule + ")";
			sLockSql = sLockSql + " ORDER BY fLevel desc, fCreateTime";
			String sql = "update AcOrder set fLockId = '" + sProgramId + "' where fid = (" + sLockSql + ") ";
			int n = dc.execute(sql);
			smg("[" + sProgramId + "]����" + n);

			//ȡ������
			String money = "", playname = "", insideid = "", productid = "";
			String sData = "", fid = "", fGiveOrderID = "", fGiveTime = ""; 
			if (n == 1){
				sql = "SELECT TOP 1 * FROM AcOrder where fLockId = '" + sProgramId + "' and fState = '2'";
				rs = dc.query(sql);
				if ((rs != null) && (rs.next())){
					//ȡ������
					fid = fc.getrv(rs, "fid", "");
					insideid = fc.getrv(rs, "fInsideId", "");					//��Ʒ����
					playname = fc.getrv(rs, "fplayname", "");					//����˺�
					money = fc.getrv(rs, "fLackMoney", "0");					//δ���� 
					//String price = fc.getrv(rs, "fPrice", "0");				//��ֵ
					String counttype = fc.getrv(rs, "fcounttype", "");			//�Ʒ�����
					String accounttype = fc.getrv(rs, "faccounttype", "");		//�˺�����
					String fillrange = fc.getrv(rs, "ffillrange", "");			//��ֵ������,֮���ڽ��ն���ʱͳһ��Ϣ
					String fillserver = fc.getrv(rs, "ffillserver", "");		//��ֵ����,֮���ڽ��ն���ʱͳһ��Ϣ
					String userip = fc.getrv(rs, "fuserip", "");				//�ͻ�IP
					String userarea = fc.getrv(rs, "fUserArea", "");			//�ͻ�����
					String giveid = fc.getrv(rs, "fGiveID", "");				//���ηַ�ƽ̨, ��,19pay,zf,hd
					//fGiveOrderID = fc.getrv(rs, "fGiveOrderID", "");			//������
					fGiveTime = fc.getrv(rs, "fGiveTime", "");					//�ַ�ʱ��
					String lockid = fc.getrv(rs, "fLockId", "");				//����ID
					if (sFillID.equals("")){
						if (giveid.indexOf("-kck") > 0)
							sFillID = fc.getGUID6("K");
						else
							sFillID = fc.getGUID6("A");
					}
					System.out.print(fid + "," + sFillID + "," + playname + "," + money + "," + fGiveTime);
					
					//�Ƿ�ȡ���˶���
					if (!lockid.equals(sProgramId)){
						dc.CloseResultSet(rs);
						return gr("false", "����ʧ��,����ID�Ѹı�");
					}
					
					
					//��ȡ�Խ���ƷID
					String fillcode = "", fCountTypeFc="", fAccountTypeFc="", fFillRangeFc="", fFillServerFc="";
					String  fCountType="", fAccountType="", fFillRange="", fFillServer="";
					money = fc.replace(money, ".00", "");
					sql = "select * from AcRelation where fproductid = '" + productid + "' " +
							" and fPlatformID = '" + giveid + "' and fState = '0' ";
					ResultSet rs2 = dc.query(sql);
					if ((rs2 != null) && (rs2.next())){
						fillcode = fc.getrv(rs2, "fFillCode", "");				//Ĭ�ϵĳ�ֵ����
						fCountTypeFc = fc.getrv(rs2, "fCountTypeFc", "");		//�Ʒ�����ת��
						fAccountTypeFc = fc.getrv(rs2, "fAccountTypeFc", "");	//�˺�����ת��
						fFillRangeFc = fc.getrv(rs2, "fFillRangeFc", "");		//��ֵ����ת��
						fFillServerFc = fc.getrv(rs2, "fFillServerFc", "");		//��ֵ������ת��
						fCountType = fc.getrv(rs2, "fCountTypeCode", "");		//��ֵ����ת��
						fAccountType = fc.getrv(rs2, "fAccountTypeCode", "");	//��ֵ����ת��
						fFillRange = fc.getrv(rs2, "fFillRangeCode", "");		//��ֵ����ת��
						fFillServer = fc.getrv(rs2, "fFillServerCode", "");		//��ֵ����ת��
					}
					dc.CloseResultSet(rs2);
					//����Ʒ�ԽӴ������ת��
					if ((fillcode+fCountTypeFc+fAccountTypeFc+fFillRangeFc+fFillServerFc+fCountType+fAccountType+fFillRange+fFillServer).equals("")){
						fCountTypeFc = fc.replace(fCountTypeFc, " ", "");
						fAccountTypeFc = fc.replace(fAccountTypeFc, " ", "");
						fFillRangeFc = fc.replace(fFillRangeFc, " ", "");
						fFillServerFc = fc.replace(fFillServerFc, " ", "");
						String code = fc.getString2("\r\n" + fCountTypeFc + "\r\n", "\r\n" + counttype + "=", "\r\n");
						if (!code.equals("")) fillcode = code;
						code = fc.getString2("\r\n" + fAccountTypeFc + "\r\n", "\r\n" + accounttype + "=", "\r\n");
						if (!code.equals("")) fillcode = code;
						code = fc.getString2("\r\n" + fFillRangeFc + "\r\n", "\r\n" + fillrange + "=", "\r\n");
						if (!code.equals("")) fillcode = code;
						code = fc.getString2("\r\n" + fFillServerFc + "\r\n", "\r\n" + fillserver + "=", "\r\n");
						if (!code.equals("")) fillcode = code;
						//��������ת��
						fCountType = fc.replace(fCountType, " ", "");
						fAccountType = fc.replace(fAccountType, " ", "");
						fFillRange = fc.replace(fFillRange, " ", "");
						fFillServer = fc.replace(fFillServer, " ", "");
						fCountType = fc.replace(fCountType, "\r\n", ",");
						fAccountType = fc.replace(fAccountType, "\r\n", ",");
						fFillRange = fc.replace(fFillRange, "\r\n", ",");
						fFillServer = fc.replace(fFillServer, "\r\n", ",");
						code = fc.getString2("," + fCountType + ",", "," + counttype + "=", ",");
						if (!code.equals("")) counttype = code;
						code = fc.getString2("," + fAccountType + ",", "," + accounttype + "=", ",");
						if (!code.equals("")) accounttype = code;
						code = fc.getString2("," + fFillRange + ",", "," + fillrange + "=", ",");
						if (!code.equals("")) fillrange = code;
						code = fc.getString2("," + fFillServer + ",", "," + fillserver + "=", ",");
						if (!code.equals("")) fillserver = code;
					}
					
					//���úŶδ���ģ��
					String section = "";
					if ((giveid.indexOf("waihu-05") > -1)){
						section = accocheck.HunanQBCheck.getSectionNo(fid, playname, money);
						if (section.indexOf("ERROR") > -1){
							System.out.print("[���ϺŶη���]" + section);
							section = "";
							sql = "update AcOrder set fLockID='',fGiveID = '" + giveid + "', fGivetime=GETDATE(), fState='1'" +
									" , fErrorCount = 0 where fid = '" + fid + "'";
							dc.execute(sql);
							dc.CloseResultSet(rs);
							return gr("false", "��������,�����ϺŶι���");
						}
					}
					
					// 
					sData = "<accoorder>" +
							"<giveid>" + giveid + "</giveid>" +			//��ǰָ���ĳ�ֵ��ʽ,����ɰ����ȳ�ֵ
							"<id>" + fid + "</id>" +
							"<fillid>" + sFillID + "</fillid>" +
							"<insideid>" + insideid + "</insideid>" +
							"<playname>" + playname + "</playname>" +
							//"<price>" + price + "</price>" +   
							"<money>" + money + "</money>" +
							"<counttype>" + counttype + "</counttype>" +
							"<accounttype>" + accounttype + "</accounttype>" +
							"<fillrange>" + fillrange + "</fillrange>" +	
							"<fillserver>" + fillserver + "</fillserver>" +
							"<userip>" + userip + "</userip>" +
							"<userarea>" + userarea + "</userarea>" +
							"<fillcode>" + fillcode + "</fillcode>" +
							"<section>" + section + "</section>" +
							"</accoorder>"; 	
					sData = fc.getResultStr("getaccount", sData, "true", "ȡ���ɹ�");
					
					//�������;, ��ѯ��ǰ�����Ķ����Ƿ�������ͬ�˺���������,�����, �ָ�����, ��ȡ��
					String IsSkip = "true";
					if ((!sOperType.equals("skip_zt")) && (insideid.equals("06"))){
						sql = "select top 1 fid as count from AcOrder where fplayname = '" + playname + "' and fState = '3' and fInsideid = '06'";
						rs2 = dc.query(sql);
						if (rs2!=null && rs2.next()){
							smg("[" + sProgramId + "]�������зַ��ɹ����˺���ͬ����;����,ȡ������");
							IsSkip = "false";
						}
						dc.CloseResultSet(rs2);
					}
					
					//����״̬
					if (IsSkip.equals("true")){ 
						sql = "update AcOrder set fState = '3',fGiveOrderID = '" + sFillID + "'  where fid = '" + fid + "'";
						n = dc.execute(sql);
						if (n != 1){
							sql = "update AcOrder set fLockId = '' where fid = '" + fid + "' and (fState = '2')"; 	//����ʱ�ָ�
							dc.execute(sql);
							sData = gr("false", "LOCK_FALSE");  //����ʧ��
						}
					}else{
						sql = "update AcOrder set " +
						" fMemo = 'ȡ���ַ�[�������зַ��ɹ����˺���ͬ����;����]'," +
						" fState = '6', fLockId = '' " +
						" where fID = '" + fid + "' and (fState ='2')";
					dc.execute(sql);
					sData = gr("false", "ȡ����;��������");  //ȡ��ʧ��
					}
						
				}else{
					sql = "update AcOrder set fLockId = '' where fLockId = '" + sProgramId + "' and fState ='2'";
					dc.execute(sql);
					sData = gr("false", "����ʧ��");	//����ʧ��
				}
				dc.CloseResultSet(rs);
			}else
				if (n == -1){
					sql = "update AcOrder set fLockId = '' where fLockId = '" + sProgramId + "' and (fState = '2')";
					dc.execute(sql);
					sData = gr("false", "����ʧ��");	//����ʧ��
					try{
						if (dc.getConnectError().indexOf("Error establishing socket") > -1 ) sData = gr("false", "���ݿ�����ʧ��");
					}catch(Exception eee){
						
					}
				}else
					sData = gr("false", "���޶�������");	//�޿ɴ�����
			

	
			//��������
			if (fid.equals("")) fid = sData;
			smg("[" + sProgramId + "]����:" + fid + "," + sFillID);
			return sData;
			
		}catch(Exception e){
			isrun = 0;
			isrerun = false;
			e.printStackTrace();
			return gr("false", "ȡ������");
		}
		
	}
	
	private String gr(String sState, String sMsg) {
		// TODO Auto-generated method stub
		return fc.getResultStr("getaccount", sState, sMsg);
	}

	public void smg(String s){
		System.out.print(s);
		return;
	}
}