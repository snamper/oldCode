package notice;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.DataConnect;
import common.fc;
/**
 * Servlet implementation class for Servlet: PayNotify
 *
 * web.servlet
 *   name="PayNotify"
 *   display-name="PayNotify" 
 *
 * web.servlet-mapping
 *   url-pattern="/PayNotify"
 *  
 */
 public class PayNotify extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	   static final long serialVersionUID = 1L;
	   private static final String CONTENT_TYPE = "text/html; charset=GBK";
	   static DataConnect dc = null;	
	   static int ConnectCount = 0;
	   static String cards = "";

    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public PayNotify() {
		super();
		ConnectCount = ConnectCount + 1;
		System.out.print("[PayNotify]�µ�ʵ��,��ǰ���и�" + ConnectCount + "������");
		if (dc == null){
			dc = new DataConnect("orderfill", false);		//����һ������
			System.out.print("[PayNotify]��һ��ʵ��,��ʼ�����ݿ�����");
		}
	} 
	
	/* (non-Javadoc)
	 * @see javax.servlet.Servlet#destroy()
	 */
	public void destroy() {
		ConnectCount = ConnectCount - 1;
		if (ConnectCount <= 0){
			dc.CloseConnect();
			dc = null;
			System.out.print("[PayNotify]ʵ����Ϊ0,�ѹر����ݿ�����");
		}
		super.destroy();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(payNotify(request, response));
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(payNotify(request, response));
	}   	  	    
	
	/*
	 * ����֪ͨurl������,�������ʧ��, д���ط��б�
	 */
	public String payNotify(HttpServletRequest request, HttpServletResponse response)  {
		//1.���ղ��� OK
		String id = fc.getpv(request, "id");							//ϵͳ������
		String MerchantID = fc.getpv(request, "MerchantID");			//�ͻ����
		String MerOrderNo = fc.getpv(request, "MerOrderNo");			//�ͻ�������
		String type = fc.getpv(request, "type");						//����(��|queryurl)
		String sign = fc.getpv(request, "sign").toUpperCase();
		
		//2.����Ϸ��� 
		if (id.equals("")&&MerOrderNo.equals("")) return "ERROR,�����Ų���Ϊ��";
		if (!id.equals(""))
			if (!sign.equals(fc.getMd5Str(id + "44fecb4739dfae183e38ee990e70275b").toUpperCase())){
				System.out.print("֪ͨ:" + id + ">>MD5��֤ʧ��" );
				return "ERROR,MD5��֤ʧ��";		//md5����
			}
		if (!MerOrderNo.equals(""))
			if (!sign.equals(fc.getMd5Str(MerchantID + MerOrderNo + "ma1OtIHujkECqQwRx8B7fbNiSA4PLyTX").toUpperCase())){
				System.out.print("֪ͨ:" + id + ">>MD5��֤ʧ��" );
				return "ERROR,MD5��֤ʧ��";		//md5����
			}
		//3.֪ͨ
		return payNotify_(id, MerOrderNo, MerchantID, type);
	}
	
	
	/*
	 * ֪ͨ����
	 */
	public String payNotify_(String id, String oid, String cid, String type)  {
		String sql = "";
		try{
			System.out.print("֪ͨ:" + id + "," + type);
			//1.��ѯ����
			if (!id.equals("")){
				sql = "select top 1 CoAgent.fReMaxTime, CoAgent.fReInterval, CoAgent.fActualMoney, " +
				" CoClient.fKey, CoClient.fFaceID, CaOrder.* from CaOrder, CoClient, CoAgent " +
				  "where CaOrder.fid = '" + id + "' and CaOrder.fClientID = CoClient.fID and CoAgent.fID = CaOrder.fAgentID ";
			}
			if (!oid.equals("")){
				type = "queryurl";
				sql = "select top 1 CoAgent.fReMaxTime, CoAgent.fReInterval, CoAgent.fActualMoney, " +
				" CoClient.fKey, CoClient.fFaceID, CaOrder.* from CaOrder, CoClient, CoAgent " +
				  "where CaOrder.fOrderid = '" + oid + "' and CaOrder.fClientID = CoClient.fID and CoAgent.fID = CaOrder.fAgentID ";
			}
			ResultSet rs = dc.query(sql); 
			if (rs == null) return "ERROR,��ѯ����ʱʧ��";
			if (!rs.next()){
				dc.CloseResultSet(rs);
				System.out.print("֪ͨ:" + id + ">>δ��ѯ������" );
				return "ERROR,δ��ѯ������";    
			}
			
			//2.ȡ������
			String PayOrderNo = fc.getrv(rs, "fid", "");		  	//ϵͳ������
			String PayResult = fc.getrv(rs, "fState", "");	  		//֧�����
			String MerchantID = fc.getrv(rs, "fFaceID", "");  		//�ӿ�ID
			String ClientID = fc.getrv(rs, "fClientID", "");  		//�̻�ID
			if (MerchantID.equals("")) MerchantID = ClientID; 		//����ӿ�IDΪ��,Ĭ��Ϊ�̻�ID				
			String MerOrderNo = fc.getrv(rs, "fOrderId", "");  		//�̻�������
			String CardType = fc.getrv(rs, "fProductID", "");  		//������
			String FactMoney = fc.getrv(rs, "fMoney", "0");  		//ʵ�ʽ��
			String Price = fc.getrv(rs, "fPrice", ""); 				//�ύ���
			String CustomizeA = fc.getrv(rs, "fUserA", "");  		//�Զ���A
			String CustomizeB = fc.getrv(rs, "fUserB", "");  		//�Զ���B
			String CustomizeC = fc.getrv(rs, "fUserC", "");  		//�Զ���B
			String CardNo = fc.getrv(rs, "fCardNo", "");			//����
			String Key = fc.getrv(rs, "fKey", "");					//KEY
			String ReturnURL = fc.getrv(rs, "fNoticeURL", "");		//֪ͨ��ַ,��¼�������ڿͷ����е�֪ͨ��ַ
			String ReturnPage = fc.getrv(rs, "fNoticePage", "");	//֪ͨҳ��
			String AccountId = fc.getrv(rs, "fAccountId", "");		//������ʾ�Ķ�����
			String PayTime = fc.getrv(rs, "ffilltime", "");		//֧��ʱ��
			String FillMsg = fc.getrv(rs, "fFillMsg", "");			//������Ϣ
			String ActualMoney = fc.getrv(rs, "fActualMoney", "");	//�Ƿ�ʵ�ʽ���
			String AgentID = fc.getrv(rs, "fAgentID", "");			//������ID
			String ReInterval = fc.getrv(rs, "fReInterval", "");	//��
			String ReMaxTime = fc.getrv(rs, "fReMaxTime", "");		//��
			//
			if (ReInterval.equals("")) ReInterval = "60";
			if (ReMaxTime.equals("")) ReMaxTime = "60";
			//
			dc.CloseResultSet(rs);
			
			if (!oid.equals("")){
				if (!ClientID.equals(cid)){
					return  "ERROR,�����Ż��̻�ID����ȷ";
				}
			}

			//3.�����ﴦ���ƶ���ֵ����: ��ʵ�ʽ�������ֵʱ, ����ֵ����
			if (("," + ActualMoney + ",").indexOf("," + CardType + ",") > -1){	
					if (Float.valueOf(FactMoney).floatValue() > Float.valueOf(Price).floatValue()){
						sql = "update CaOrder set fMoney=fPrice, fFactMoney=fPrice * fRate " +
							" where fid='" + id+ "' and  fNoticeState <> '2'";
						int n = dc.execute(sql);
						if (n == 1)	System.out.print("[�ƶ�]����ֵ������:" + id);
					}
				}
			
			//4.�޷��ص�ַʱ
			if (!type.equals("wynotice") && !type.equals("queryurl") && ReturnURL.equals("")){
				dc.execute("UPDATE CaOrder SET fNoticeState = '2',  fOverTime = GETDATE() WHERE fid = '" + id + "'");
				System.out.print("֪ͨ:" + id + ">>�����Ų���ȷ,�򶩵����ڴ�����,���֪ͨ����" );
				return "OK,�����֪ͨ�������";				
			}

			
			//5.�������֧���ɹ���֧��ʧ��, ��ȥ��ֹ֪ͨ
			if (!PayResult.equals("4") && !PayResult.equals("5")){
				System.out.print("֪ͨ:" + id + ">>����״̬����֧���ɹ���֧��ʧ��: " + PayResult );
				return "ERROR,������δ�������";
			}

			//6.�����֧��ʧ��,�õ�ӳ���Ĵ�����Ϣ
			String sMsg = "";
			if (PayResult.equals("5") && (!FillMsg.equals(""))){
				//ȡ����Ӧ������Ϣ
				sql = "select * from CaErrorInfo where fId = '" + FillMsg + "'";
				rs = dc.query(sql);
				if (rs != null){
					if (rs.next()){
						sMsg = fc.getrv(rs, "fMsg", "");
					}else{
						sql = "insert into CaErrorInfo (fId, fTypeId, fMsg) values('" + FillMsg + "', '" + CardType + "','<��״̬>')";
						dc.execute(sql);
					}
					dc.CloseResultSet(rs);
				}
				//���û��ȡ��,�����¼�¼
			}
			if (!sMsg.equals("<��״̬>")) 
				FillMsg = sMsg;
			else
				FillMsg = "";
			
			//ת����ֵ���
			if (PayResult.equals("4")) PayResult = "true";
			if (PayResult.equals("5")) PayResult = "false";
			
			//7.��ѯ����ӿڲ���,����֪ͨURL
			String sPayOrderNo = "PayOrderNo";
			String sMerchantID = "MerchantID";
			String sMerOrderNo = "MerOrderNo";
			String sCardType = "CardType";
			String sCardNo = "CardNo";
			String sFactMoney = "FactMoney";
			String sPayResult = "PayResult";
			String sAccountId = "AccountId";
			String sCustomizeA = "CustomizeA";
			String sCustomizeB = "CustomizeB";
			String sCustomizeC = "CustomizeC";
			String sFillMsg = "ErrorMsg";
			String sPayTime = "PayTime";
			String sSign = "sign";
			String sOkStr = "OK";
			sql = "select * from CoAgentParam where fAgentID = '" + AgentID + "' and fType = 'CARD'";
			rs = dc.query(sql);
			while (rs!=null && rs.next()){
				String sOld = fc.getrv(rs, "fOldParam", "");
				String sNew = fc.getrv(rs, "fNewParam", "");
				if (sOld.equals("PayOrderNo")) sPayOrderNo = sNew; 
				if (sOld.equals("MerchantID")) sMerchantID = sNew; 
				if (sOld.equals("MerOrderNo")) sMerOrderNo = sNew;
				if (sOld.equals("CardType")) sCardType = sNew;
				if (sOld.equals("CardNo")) sCardNo = sNew;
				if (sOld.equals("FactMoney")) sFactMoney = sNew;
				if (sOld.equals("PayResult")) sPayResult = sNew;
				if (sOld.equals("AccountID")) sAccountId = sNew;
				if (sOld.equals("CustomizeA")) sCustomizeA = sNew; 
				if (sOld.equals("CustomizeB")) sCustomizeB = sNew;
				if (sOld.equals("CustomizeC")) sCustomizeC = sNew; 
				if (sOld.equals("ErrorMsg")) sFillMsg = sNew;
				if (sOld.equals("PayTime")) sPayTime = sNew;
				if (sOld.equals("sign")) sSign = sNew;
				if (sOld.equals("OK_STR")) sOkStr = sNew;  			//��Ӧ���ͻ����µ��ɹ���־
				
			}
			dc.CloseResultSet(rs); 	

			//MD5
//			String sMd5 = fc.getMd5Str(PayOrderNo + MerchantID + MerOrderNo + CardNo + CardType + FactMoney + PayResult
//					+ CustomizeA + CustomizeB + CustomizeC + PayTime + Key).toUpperCase(); 		
			String sMd5 = fc.getMd5Str(MerchantID + MerOrderNo + CardNo + CardType + FactMoney + AccountId + PayResult
					+ CustomizeA + Key).toUpperCase(); 		
			
			//���
			String sData = sPayOrderNo + "=" + PayOrderNo 
				+ "&" + sMerchantID + "=" + MerchantID 
				+ "&" + sMerOrderNo + "=" + MerOrderNo 
				+ "&" + sCardNo + "=" + CardNo
				+ "&" + sCardType + "=" + CardType 
				+ "&" + sFactMoney + "=" + FactMoney 
				+ "&" + sPayResult + "=" + PayResult 
				+ "&" + sAccountId + "=" + AccountId
				+ "&" + sCustomizeA + "=" + CustomizeA 
				+ "&" + sCustomizeB + "=" + CustomizeB 
				+ "&" + sCustomizeC + "=" + CustomizeC 
				+ "&" + sPayTime + "=" + URLEncoder.encode(PayTime) 
				+ "&" + sFillMsg + "=" + URLEncoder.encode(FillMsg) 
				+ "&" + sSign + "=" + sMd5;

			//8.���ݲ������ͽ��ж�Ӧ����
			if (type.equals("queryurl"))  return ReturnURL + "?" + sData;
			if (type.equals("queryresult"))	return fc.SendDataViaPost(ReturnURL, sData, "");
			if (type.equals("querymd5"))
				return "source=" + PayOrderNo + MerchantID + MerOrderNo + CardNo + CardType + FactMoney + PayResult
				+ CustomizeA + CustomizeB + CustomizeC + PayTime + Key + ",md5=" + sSign;
			if (type.equals("wynotice"))	//������ת��
				if (ReturnPage.indexOf("?") > -1)
					return ReturnPage + "&" + sData;
				else
					return ReturnPage + "?" + sData;
			
			//9.֪ͨ�ͻ�
			String sError = "";
			if (type.equals("directly")){
				sError = fc.SendDataViaPost(ReturnURL, sData, "");
				if (sError.equals("")){
					System.out.print("����GET��ʽ:" + PayOrderNo);
					sError = fc.SendDataViaGet(ReturnURL + "?" + sData);
				}
				if (sError.length() > 32) sError = sError.substring(0, 32);
				System.out.print("֪ͨ:" + PayOrderNo + ">>" + sError);
			}

			//10.���֪ͨʧ��, д���ط�֪ͨ�б�
			String s = "";
			if ((!sError.equals("")) && (sError.toUpperCase().indexOf(sOkStr) == 0)){
				System.out.print("֪ͨ:" + PayOrderNo + "," + sError);
				sql = "update CaOrder set fNoticeState = '2', fLockID = '' where fid = '" + id + "'";
				dc.execute(sql);
				s = "OK,֪ͨ�ɹ�";
			}else{
				String ss = "";
				ss = fc.SetSqlInsertStr(ss, "fID", id);
				ss = fc.SetSqlInsertStr(ss, "fClientID", ClientID);
				ss = fc.SetSqlInsertStr(ss, "fEndTime", "GETDATE() + 0.0007 * " + ReMaxTime, false);			//��
				ss = fc.SetSqlInsertStr(ss, "fOverTime", "GETDATE()", false);		//��
				ss = fc.SetSqlInsertStr(ss, "fData", ReturnURL + "?" + sData);
				ss = fc.SetSqlInsertStr(ss, "fOKStr", sOkStr);
				ss = fc.SetSqlInsertStr(ss, "fResult", "");
				ss = fc.SetSqlInsertStr(ss, "fLockID", "");
				ss = fc.SetSqlInsertStr(ss, "fReInterval", ReInterval);
				sql = "insert into CaReNotice " + ss;
				int n = dc.execute(sql);
				if (n == 1 || n == -2){
					sql = "update CaOrder set fNoticeState = '1', fLockID = '' where fid = '" + id + "'";
					dc.execute(sql);
				}else{
					sql = "update CaOrder set fNoticeState = '0', fLockID = '' where fid = '" + id + "'";
					dc.execute(sql);
				}
				//
				System.out.print("֪ͨ:" + PayOrderNo + ">>�����ط��б�(" + n + ")");
				//
				s = "OK,�Ѽ����ط��б�(" + sError + ")";
			}
			return s;
			
		}catch(Exception e){
			System.out.print(sql);
			e.printStackTrace();
			return "ERROR,֪ͨģ�����";
		}
		
	}
		
}