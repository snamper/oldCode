package cardfill;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import common.*;

public class AddLocalCard {

	/*
	 * 返回商品类型
	 */
	public String getProductType(){
		try{
			
			DataConnect dc = new DataConnect("orderfill", false);
			//
			String sql = "select * from CoProductType order by fid";
			ResultSet rs = dc.query(sql);
			//
			String s = "";
			while (rs != null && rs.next()){
				String id = fc.getrv(rs, "fid", "");
				String fName = fc.getrv(rs, "fName", "");
				//
				s = s + "<OPTION value='" + id + "'>" + fName + "</OPTION>\r\n";
			}
			//
			dc.CloseResultSet(rs);
			dc.CloseConnect();
			//
			return s;
		}catch(Exception e){
			return "";
		}

	}
	
	public String addLocalCard(HttpServletRequest request){
		String sClientId = fc.getpv(request, "ClientID");
		String sCardType = fc.getpv(request, "CardType");
		String sPrice = fc.getpv(request, "Price");
		String sBili = fc.getpv(request, "Bili");
		String CardInfo = fc.getpv(request, "CardInfo");		//卡号,密码,订单号/r/n....
		String sCheckCard = fc.getpv(request, "checkcard");		//true
		String fOperateId = fc.getpv(request, "currentUserid");	//操作人
		
		//
		if (sBili.equals("")) sBili = "1";
		//
		int n = 1;
		// 
		String sCardId = "";
		String sPassword = "";
		String sOrderID = "";
		//
		int nOk = 0, nError = 0, nSkip = 0;
		String sFalseTemp = ""; 
		String sInfo = fc.getString("@#$%^&*()" + CardInfo + "\r\n", "@#$%^&*()", "\r\n");
		if (sInfo.equals("")){
			return "";
		}
		DataConnect dc = new DataConnect("orderfill", false);
		sInfo = sInfo + ",";
		while (!sInfo.equals("")){
			//
			sInfo = sInfo + ",";
			String sTemp = sInfo;
			//
			int n1 = sInfo.indexOf(",");
			if (n1 > 0){
				sCardId = sInfo.substring(0, n1).trim();
				sInfo = sInfo.substring(n1 + 1);
				int n2 = sInfo.indexOf(",");
				if (n2 > 0){
					sPassword = sInfo.substring(0, n2).trim();
					if (sInfo.length() > n2)
						sOrderID = sInfo.substring(n2 + 1).trim();
				}
			}
			  
			//8.生成订单号,对密码进行加密
			String sNewPassword = "(@_@)" + common.EncryptsE.encrypt(sPassword, "64Qz9cWPiH0B25CM7IoFGjETmfAbsOxh");

			//
			String sOk = "ok";
			if (sCheckCard.equals("true")){
				try{
					String sql = "select * from CaOrderKck where fProductID = '" + sCardType + "' and fCardNo = '" + sCardId + "'";
					ResultSet rs = dc.query(sql);
					if (rs != null && rs.next()){
						sOk = "no";
					}
					dc.CloseResultSet(rs);
				}catch(Exception e){
					sOk = "no";
				}
				
			}
			
			if (sOk.equals("ok")){
				String s  = "";
				s = fc.SetSqlInsertStr(s, "fID", fc.getGUID6(""));
				s = fc.SetSqlInsertStr(s, "fCreateTime", "GETDATE()", false);
				s = fc.SetSqlInsertStr(s, "fClientID", sClientId);
				s = fc.SetSqlInsertStr(s, "fOrderID", fc.getGUID6("K")); 
				s = fc.SetSqlInsertStr(s, "fProductID", sCardType);
				s = fc.SetSqlInsertStr(s, "fClientTime", "getdate()", false);
				s = fc.SetSqlInsertStr(s, "fCardNo", sCardId);
				s = fc.SetSqlInsertStr(s, "fPassword", sNewPassword);
				s = fc.SetSqlInsertStr(s, "fAccountID", "");
				s = fc.SetSqlInsertStr(s, "fPrice", sPrice);
				s = fc.SetSqlInsertStr(s, "fRate", sBili);
				s = fc.SetSqlInsertStr(s, "fGiveID", "");
				s = fc.SetSqlInsertStr(s, "fOverTime", "GETDATE()", false);
				s = fc.SetSqlInsertStr(s, "fErrorCount", "0");
				s = fc.SetSqlInsertStr(s, "fState", "0");
				s = fc.SetSqlInsertStr(s, "fNoticeState", "0");
				s = fc.SetSqlInsertStr(s, "fUserA", "");
				s = fc.SetSqlInsertStr(s, "fUserB", "");
				s = fc.SetSqlInsertStr(s, "fUserC", "");
				s = fc.SetSqlInsertStr(s, "fNoticeURL", "");
				s = fc.SetSqlInsertStr(s, "fNoticePage", "");
				s = fc.SetSqlInsertStr(s, "fLockID", "");
				String sql = "INSERT INTO CaOrderKck " + s;
				int m = dc.execute(sql);
				if (m != 1){
					nError = nError + 1;
					sFalseTemp = sFalseTemp + "\r\n" + sTemp;
				}else{
					nOk = nOk + 1;
				}
			}else
				nSkip = nSkip + 1;
				
			//
			CardInfo =  fc.getString(CardInfo + "@#$%^&*()", "\r\n", "@#$%^&*()");
			sInfo = fc.getString("@#$%^&*()" + CardInfo + "\r\n", "@#$%^&*()", "\r\n");
		}
		
		
		//写导入成功记录
		if (nOk > 0){
			String s = nOk * Float.valueOf(sPrice).floatValue() + "";
			String sql = "insert into CaKckAddLog (fID, fTime, fClientId, fOperateId, fCount, fPrice, fMoney, fMemo) " +
					" values('" + fc.getGUID6("") + "', GETDATE(), '" + sClientId + "', '" + fOperateId + "'," +
							" " + nOk + ", " + sPrice + "," + s + ", '" + fOperateId + "成功导入" + nOk + "张面额为" + sPrice + "元的卡,总面值为" + s + "元.')";
			dc.execute(sql);
		}
		
		//
		dc.CloseConnect();
		String s = "";
		if (nError > 0) s = "导入失败: " + nError + "个\r\n-----以下为导入失败列表-----\r\n" + sFalseTemp;
		//
		return "成功导入: " + nOk + "个\r\n跳过重复: " + nSkip + "个\r\n" + s;
	}

}
