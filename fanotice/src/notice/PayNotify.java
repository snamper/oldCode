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
 * @web.servlet
 *   name="PayNotify"
 *   display-name="PayNotify" 
 *
 * @web.servlet-mapping
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
		String oid = fc.getpv(request, "oid");							//�ͻ�������
		String cid = fc.getpv(request, "cid");							//�ͻ�ID
		String source = fc.getpv(request, "source");					//��Դ
		String type = fc.getpv(request, "type");						//����(��|queryurl)
		String sign = fc.getpv(request, "sign").toUpperCase();
		
		//2.����Ϸ��� 
		if ((id.equals("")&&oid.equals(""))) return fc.getResultStr("query", "false", "�����Ų���Ϊ��");
		if (!id.equals(""))
			if (!sign.equals(fc.getMd5Str(id + "44fecb4739dfae183e38ee990e70275b").toUpperCase())){
				System.out.print("֪ͨ:" + id + ">>MD5��֤ʧ��" );
				return fc.getResultStr("query", "false", "MD5��֤ʧ��");		//md5����
			}
		if (!oid.equals(""))
			if (!sign.equals(fc.getMd5Str(oid + cid + "2mZbHsip9GIe7jrVX3O16JTAwgzFd4CY").toUpperCase())){
				System.out.print("֪ͨ:" + oid + ">>MD5��֤ʧ��" );
				return fc.getResultStr("query", "false", "MD5��֤ʧ��");		//md5����
			}
		//3.֪ͨ
		return payNotify_(id, oid, cid, type);
	}
	
	
	/*
	 * ֪ͨ����
	 */
	public String payNotify_(String id, String oid, String cid, String type)  {
		String sql = "";
		try{
			
			//1.��ѯ����
			if (!id.equals("")){
				sql = "select top 1 CoAgent.fReMaxTime, CoAgent.fReInterval, CoAgent.fActualMoney, " +
				" CoClient.fKey, CoClient.fFaceID, AcOrder.* from AcOrder, CoClient, CoAgent " +
				  "where AcOrder.fid = '" + id + "' and AcOrder.fClientID = CoClient.fID and (CoAgent.fID = AcOrder.fAgentID or AcOrder.fAgentID = '') ";
			}
			if (!oid.equals("")){
				type = "queryurl";	//�ͻ���ѯʱ,ǿ�Ʋ�ѯ֪ͨurl
				sql = "select top 1 CoAgent.fReMaxTime, CoAgent.fReInterval, CoAgent.fActualMoney, " +
				" CoClient.fKey, CoClient.fFaceID, AcOrder.* from AcOrder, CoClient, CoAgent " +
				  "where AcOrder.fOrderID = '" + oid + "' and AcOrder.fClientID = CoClient.fID and (CoAgent.fID = AcOrder.fAgentID or AcOrder.fAgentID = '') ";
			}
			ResultSet rs = dc.query(sql); 
			if (rs == null) return  fc.getResultStr("query", "false", "��ѯ����ʱʧ��");
			if (!rs.next()){
				dc.CloseResultSet(rs);
				System.out.print("֪ͨ:" + id + ">>δ��ѯ������" );
				return fc.getResultStr("query", "false", "δ��ѯ������"); 
			}
			
			//2.ȡ������
			String PayOrderNo = fc.getrv(rs, "fid", "");		  	//ϵͳ������
			String PayResult = fc.getrv(rs, "fState", "");	  		//��ֵ���
			String MerchantID = fc.getrv(rs, "fFaceID", "");  		//�ӿ�ID
			String ClientID = fc.getrv(rs, "fClientID", "");  		//�̻�ID
			//if (MerchantID.equals("")) 
			MerchantID = ClientID; 		//����ӿ�IDΪ��,Ĭ��Ϊ�̻�ID				
			String MerOrderNo = fc.getrv(rs, "fOrderId", "");  		//�̻�������
			String ProductID = fc.getrv(rs, "fProductID", "");  		//��Ʒ����
			String fInsideID = fc.getrv(rs, "fInsideID", "");  		//��Ʒ����
			String FactMoney = fc.getrv(rs, "fFactMoney", "0");  	//��ֵ���
			String Money = fc.getrv(rs, "fMoney", "0");  			//���
			String CustomizeA = fc.getrv(rs, "fInfo1", "");  		//�Զ���A
			String CustomizeB = fc.getrv(rs, "fInfo2", "");  		//�Զ���B
			String CustomizeC = fc.getrv(rs, "fInfo3", "");  		//�Զ���B
			String fSource = fc.getrv(rs, "fSource", "");  			//��Դ			
			String PlayName = fc.getrv(rs, "fPlayName", "");		//�˺�
			String Key = fc.getrv(rs, "fKey", "");					//KEY
			String ReturnURL = fc.getrv(rs, "fNoticeURL", "");		//֪ͨ��ַ,��¼�������ڿͷ����е�֪ͨ��ַ
			String AccountId = fc.getrv(rs, "fAccountId", "");		//������ʾ�Ķ�����
			String PayTime = fc.getrv(rs, "ffilltime", "");		    //֧��ʱ��
			String FillMsg = fc.getrv(rs, "fFillMsg", "");			//������Ϣ
			String AgentID = fc.getrv(rs, "fAgentID", "");			//������ID
			String ReInterval = fc.getrv(rs, "fReInterval", "");	//��
			String ReMaxTime = fc.getrv(rs, "fReMaxTime", "");		//��
			//
			if (ReInterval.equals("")) ReInterval = "60";
			if (ReMaxTime.equals("")) ReMaxTime = "60";
			//
			dc.CloseResultSet(rs);
			
			//
			if (!oid.equals("")){
				if (!ClientID.equals(cid)){
					return fc.getResultStr("query", "false", "�����Ż��̻�ID����ȷ");
				}
			}
			
			//5.�������֧���ɹ���֧��ʧ��, ��ȥ��ֹ֪ͨ
			if (!PayResult.equals("4") && !PayResult.equals("5")){
				System.out.print("֪ͨ:" + id + ">>����״̬���ǳ�ֵ�ɹ����ֵʧ��: " + PayResult );
				return fc.getResultStr("query", "false", "������δ�������");
			}

			//�����˿�
			
			//7.��ѯ����ӿڲ���,����֪ͨURL
			//��ѯ��ת���Ĳ�����, ���û�ж���, ����ԭ������
			String vID = "sid";
			String vState = "ste";
			String vClientID = "cid";
			String vProductID = "pid";
			String vOrderID = "oid";
			String vPlayName = "pn";
			String vMoney = "fm";
			String vReUrl = "ru";
			String vInfo1 = "info1";
			String vInfo2 = "info2";
			String vInfo3 = "info3";
			String vSource = "source";
			String vErrorMsg = "em";
			String vFillTime = "ft";
			String vsign = "sign";
			String vOkStr = "success";
			String vErrorStr = "fail";
			//
			sql = "select * from CoAgentParam where fAgentID = '" + AgentID + "' and fType = 'ACCOUNT_REQUEST'";
			rs = dc.query(sql);
			while (rs!=null && rs.next()){
				String sOld = fc.getrv(rs, "fOldParam", "");
				String sNew = fc.getrv(rs, "fNewParam", "");
				if (sOld.equals("sid")) vID = sNew; 
				if (sOld.equals("ste")) vState = sNew; 
				if (sOld.equals("cid")) vClientID = sNew;
				if (sOld.equals("pid")) vProductID = sNew;
				if (sOld.equals("oid")) vOrderID = sNew; 
				if (sOld.equals("pn")) vPlayName = sNew;
				if (sOld.equals("fm")) vMoney = sNew;
				if (sOld.equals("ru")) vReUrl = sNew;
				if (sOld.equals("info1")) vInfo1 = sNew; 
				if (sOld.equals("info2")) vInfo2 = sNew;
				if (sOld.equals("info3")) vInfo3 = sNew; 
				if (sOld.equals("source")) vSource = sNew; 
				if (sOld.equals("em")) vErrorMsg = sNew; 
				if (sOld.equals("ft")) vFillTime = sNew; 
				if (sOld.equals("sign")) vsign = sNew;
				if (sOld.equals("OK_STR")) vOkStr = sNew;  			//��Ӧ���ͻ����µ��ɹ���־
				if (sOld.equals("ERROR_STR")) vErrorStr = sNew;
			}
			dc.CloseResultSet(rs);	

			//�����ֳ�ֵ�ķ���ֵ
			if (!Money.equals(FactMoney) && PayResult.equals("4")) 
				FillMsg = "�˺Ų��ֳ�ֵ";

			//ת����ֵ��Ϣ
			String sMsg = "";
			if (PayResult.equals("5") || FillMsg.equals("�˺Ų��ֳ�ֵ")){
				//ȡ����Ӧ������Ϣ
				sql = "select * from AcErrorInfo where fId = '" + FillMsg + "' and fTypeID = '" + fInsideID + "'";
				rs = dc.query(sql);
				if (rs != null){
					if (rs.next()){
						sMsg = fc.getrv(rs, "fMsg", "");
					}else{
						sql = "insert into AcErrorInfo (fId, fTypeId, fMsg) values('" + FillMsg + "', '" + fInsideID + "','<��״̬>')";
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

			
			//��ֵ���
			if (PayResult.equals("4")) PayResult = "0";
			if (PayResult.equals("5")) PayResult = "1";
			

			String noticeMoney = FactMoney;
			
			//�ԲƸ�֪ͨͨʱ�Ľ�������޸ġ�֪ͨ���Ϊ�����������ѳ���
			if("caift".equals(ClientID)){
				noticeMoney = Money;
			}
			//MD5
			String sMd5Source = PayOrderNo + PayResult + MerchantID  + ProductID + MerOrderNo + PlayName + noticeMoney + Key;
			String sMd5 = fc.getMd5Str(sMd5Source).toUpperCase(); 		
			
			
			//���
			String sData = vID + "=" + PayOrderNo 
				+ "&" + vState + "=" + PayResult 
				+ "&" + vClientID + "=" + MerchantID 
				+ "&" + vProductID + "=" + ProductID
				+ "&" + vOrderID + "=" + MerOrderNo 
				+ "&" + vPlayName + "=" + PlayName 
				+ "&" + vMoney + "=" + noticeMoney 
				+ "&" + vInfo1 + "=" + CustomizeA 
				+ "&" + vInfo2 + "=" + CustomizeB 
				+ "&" + vInfo3 + "=" + CustomizeC 
				+ "&" + vFillTime + "=" + URLEncoder.encode(PayTime) 
				+ "&" + vErrorMsg + "=" + URLEncoder.encode(FillMsg) 
				+ "&" + vsign + "=" + sMd5;


			//���ؿͻ���ѯ
			if (!oid.equals("")){
				sData = "<" + vID + ">" + PayOrderNo + "</" + vID + ">"
				+ "<" + vState + ">" + PayResult + "</" + vState + ">"
				+ "<" + vClientID + ">" + MerchantID + "</" + vClientID + ">"
				+ "<" + vProductID + ">" + ProductID + "</" + vProductID + ">" 
				+ "<" + vOrderID + ">" + MerOrderNo + "</" + vOrderID + ">" 
				+ "<" + vPlayName + ">" + PlayName + "</" + vPlayName + ">"
				+ "<" + vMoney + ">" + noticeMoney + "</" + vMoney + ">"
				+ "<" + vInfo1 + ">" + CustomizeA + "</" + vInfo1 + ">"
				+ "<" + vInfo2 + ">" + CustomizeB + "</" + vInfo2 + ">"
				+ "<" + vInfo3 + ">" + CustomizeC + "</" + vInfo3 + ">"
				+ "<" + vFillTime + ">" + URLEncoder.encode(PayTime) + "</" + vFillTime + ">" 
				+ "<" + vErrorMsg + ">" + URLEncoder.encode(FillMsg) + "</" + vErrorMsg + ">"
				+ "<" + vsign + ">" + sMd5 + "</" + vsign + ">";
				return fc.getResultStr("query", sData, "true", "��ѯ�ɹ�");
			}

			//8.���ݲ������ͽ��ж�Ӧ����
			if (type.equals("queryurl"))  return ReturnURL + "?" + sData;
			if (type.equals("queryresult")){
				return fc.SendDataViaPost(ReturnURL, sData, "");
			}
			if (type.equals("querymd5")){
				return "source=" + sMd5Source + ",md5=" + sMd5;
			}
			
			//9.֪ͨ�ͻ�
			String sError = "";
			if (type.equals("directly")){
				if (!ReturnURL.equals("")){
					sError = fc.SendDataViaPost(ReturnURL, sData, "");
					if (sError.equals("")){
						System.out.print("����GET��ʽ:" + PayOrderNo);
						sError = fc.SendDataViaGet(ReturnURL + "?" + sData);
					}
					if (sError.length() > 32) sError = sError.substring(0, 32);
				}else
					sError = "URL��ַΪ��,������֪ͨ";
				System.out.print("֪ͨ:" + PayOrderNo + ">>" + sError);
			}

			//10.���֪ͨʧ��, д���ط�֪ͨ�б�
			String s = "";
			if ((!sError.equals("")) && (sError.toUpperCase().indexOf(vOkStr.toUpperCase()) == 0)){
				sql = "update AcOrder set fNoticeState = '2', fLockID = '' where fid = '" + id + "'";
				int n = dc.execute(sql);
				return fc.getResultStr("query", "true", "֪ͨ�ɹ�");
			}else{
				
				/*
				 * ���ʱ�䣺2011��11��25��13:08:25
				 * ���ԭ��֪ͨʱ,��֪ͨ��ַʱ,�������ط��б�.����֪ͨ״̬Ϊ��ȡ֪ͨ  5:��ȡ֪ͨ
				 */
				if("".equals(ReturnURL)){
					sql = "update AcOrder set fNoticeState = '5', fLockID = '' where fid = '" + id + "'";
					dc.execute(sql);
					return fc.getResultStr("query", "true", "����Ϊ��ȡ֪ͨ");
				}else{
					
					String ss = "";
					ss = fc.SetSqlInsertStr(ss, "fID", id);
					ss = fc.SetSqlInsertStr(ss, "fClientID", ClientID);
					ss = fc.SetSqlInsertStr(ss, "fEndTime", "GETDATE() + 0.0007 * " + ReMaxTime, false);			//��
					ss = fc.SetSqlInsertStr(ss, "fOverTime", "GETDATE()", false);									//��
					ss = fc.SetSqlInsertStr(ss, "fData", ReturnURL + "?" + sData);
					ss = fc.SetSqlInsertStr(ss, "fOKStr", vOkStr);
					ss = fc.SetSqlInsertStr(ss, "fResult", "");
					ss = fc.SetSqlInsertStr(ss, "fLockID", "");
					ss = fc.SetSqlInsertStr(ss, "fSource", fSource);
					ss = fc.SetSqlInsertStr(ss, "fReInterval", ReInterval);
					sql = "insert into AcReNotice " + ss;
					int n = dc.execute(sql);
					if (n == 1 || n == -2){
						sql = "update AcOrder set fNoticeState = '1', fLockID = '' where fid = '" + id + "'";
						dc.execute(sql);
					}
					//
					System.out.print("֪ͨ:" + PayOrderNo + ">>�����ط��б�(" + n + ")");
					//
					if (type.equals("directly"))
						return fc.getResultStr("query", "false", "֪ͨʧ��(" + sError + ")");
					else
						return fc.getResultStr("query", "true", "�Ѽ���֪ͨ�б�");
				}

			}

			
		}catch(Exception e){
			System.out.print(sql);
			e.printStackTrace();
			return fc.getResultStr("query", "false", "֪ͨģ�����");
		}
		
	}
		
}