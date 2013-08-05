package product;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.DataConnect;
import common.fc;

/**
 *  
 */
 public class GetProduct extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	   static final long serialVersionUID = 1L;
	   private static final String CONTENT_TYPE = "text/html; charset=GBK";;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public GetProduct() {
		super();
	} 
	
	/* (non-Javadoc)
	 * @see javax.servlet.Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(getProduct(request, response));
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(getProduct(request, response));
	}   	  	  
	
	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
	}   
	
	/*
	 * 取商品信息
	 */
	public String getProduct(HttpServletRequest request, HttpServletResponse response){
		try{
			//1.接收参数
			String sClientID = fc.getpv(request, "id");		//客户ID
			String sGetType = fc.getpv(request, "type");	//取单方式: 0无1卡易售后台
			String sClientKey = fc.getpv(request, "key");	//客户端验证KEY,与接口KEY不同
			String sSign = fc.getpv(request, "sign");		//签名
			//2.md5验证
			if (!sSign.equals(fc.getMd5Str(sClientID + sGetType + sClientKey + "&%)H9WU(#!SGXz$a8HkbSBcQIamfsT$o"))){
				return fc.getResultStr("getproduct", "false", "md5验证失败");
			}
			
			//3.用户身份验证
			String sql = "select * from CoClient where fid = '" + sClientID + "'";
			DataConnect dc = new DataConnect("orderfill", false);
			ResultSet rs = dc.query(sql);
			if (rs==null || !rs.next()){
				dc.CloseResultSet(rs);
				dc.CloseConnect();
				return fc.getResultStr("getproduct", "false", "身份验证失败");
			}
			String fMainKey = fc.getrv(rs, "fKey", "");
			String fGetKey = fc.getrv(rs, "fGetOrderKey", "");
			String fURL1 = fc.getrv(rs, "fKyshtURL1", "");
			String fURL2 = fc.getrv(rs, "fKyshtURL2", "");
			String fLogonID = fc.getrv(rs, "fKyshtID", "");
			String fLogonPassword = fc.getrv(rs, "fKyshtPassword", "");
			String fTaobaoID = fc.getrv(rs, "fTaobaoID", "");
			String fTaobaoPassword = fc.getrv(rs, "fTaobaoPassword", "");
			dc.CloseResultSet(rs);
			if (!fGetKey.equals("") && !fGetKey.equals(sClientKey)){
				dc.CloseConnect();
				return fc.getResultStr("getproduct", "false", "绑定验证失败");
			}
			
			//4.获取商品信息
			sql = "select * from AcClientProduct where fClientID = '" + sClientID + "' and fGetType = '" + sGetType + "'";
			rs = dc.query(sql);
			if (rs==null){
				dc.CloseConnect();
				return fc.getResultStr("getproduct", "false", "查询商品失败");
			}
			String sData = "";
			int n = 0;
			while (rs.next()){
				n++;
				String sID = fc.getrv(rs, "fProductID", "");
				String sPid = fc.getrv(rs, "fProductCode", "");
				String sPname = fc.getrv(rs, "fProductName", "");
				String sPPrice = fc.getrv(rs, "fProductPrice", "");
				sData = sData + "<row_" + n + ">" +
						"<id>" + sID + "</id>" +
						"<code>" + sPid + "</code>" +
						"<name>" + sPname + "</name>" +
						"<price>" + sPPrice + "</price>" +
						"</row_" + n + ">";
			}
			sData = "<kysurl1>" + fURL1 + "</kysurl1>" +
					"<kysurl2>" + fURL2 + "</kysurl2>" +
					"<kysloid>" + fLogonID + "</kysloid>" +
					"<kyslopass>" + fLogonPassword + "</kyslopass>" + 
					"<tbloid>" + fTaobaoID + "</tbloid>" +
					"<tblopass>" + fTaobaoPassword + "</tblopass>" + 
					"<key>" + fMainKey + "</key>" +  
					"<count>" + n + "</count>" + 
					"" + sData;
			
			//5.返回
			dc.CloseConnect();
			return fc.getResultStr("getproduct", sData, "true", "操作成功");
		}catch(Exception e){
			return fc.getResultStr("getproduct", "false", "接口运行异常");
		}
	}
}