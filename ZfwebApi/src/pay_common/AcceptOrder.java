package pay_common;

import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import com.alipay.util.UtilDate;
import common.DataConnect;
import common.funcejb;
/*
 * 接收不同应用的加款需求 
 */
public class AcceptOrder {
	public String out_orderNo ="";		//订单号
	public String alimoney = "";		//支付金额
	public String aliorder = "";		//订单名称
	public String alibody = "";			//订单描述
	 public String getAliorder() {
		return aliorder;
	}
	public void setAliorder(String aliorder) {
		this.aliorder = aliorder;
	}
	public String getAlibody() {
		return alibody;
	 }
     public void setAlibody(String alibody) {
		this.alibody = alibody;
	 }
	 public String partnerID="";        //商户ID
	 public String remark="";           //商户标记
	 public String pkey="";             //商户公钥
	 public String getPartnerID() {
	    return partnerID;
	}
	public void setPartnerID(String partnerID) {
		this.partnerID = partnerID;
	}
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPkey() {
		return pkey;
	}

	public void setPkey(String pkey) {
		this.pkey = pkey;
	}

	

	public String checkOrder(HttpServletRequest request){
		try{
			UtilDate date = new UtilDate(); //调取支付宝工具类生成订单号
			String sOrderID = funcejb.getres(request, "orderid");
			String sMoney = funcejb.getres(request, "money");
			String sError = "";
			String sign = funcejb.getres(request, "sign");
			if (sOrderID == null) sOrderID = "";
			if (sign == null) sign = "";
			//
			out_orderNo = sOrderID;
			alimoney = sMoney;
			
			//md5验证
			if (sign.equals("") || (sOrderID.equals("")))  
				sError = "合法性验证失败";
			else{
				//检查客户ID是否合法
				//95588支付
				String sql = "", name = "";
				int n = 0;			//用来计数,以防多个应用都能接受加款
				if (sign.equals(funcejb.getMd5Str(sOrderID + sMoney + "3vBo5UQqArzPw1t8"))){
					aliorder = "支付加款";
					alibody = "支付加款";
					//name = "fillcard";
					this.setPartnerID("10000254514");
					this.setRemark("95588");
					this.setPkey("30819f300d06092a864886f70d010101050003818d0030818902818100bcc16df8c3fa5f3ae062af8962a8c8f31c2a787a81eb6abb0015c07fdd0ab56a1c6b864ee0f9a897013d868ba10fc63aca14430a789fe5d9f461e4e601ee46945f9ee41265f8e3df710f2284b19c0d1f4cd04ad49b322c9a62fb8664108e6b4e2dcb1c44d2b6b1f8b4ef7b06e1c27cbe4351ec54726491c2512b7d2412f49fe50203010001");
					sql = "select fState from fillcard where id = '" + sOrderID + "'";
					n ++;    
				}
				//华奇加款
				if (sign.equals(funcejb.getMd5Str(sOrderID + sMoney + "qPJuv2yH3OIUieNz"))){
					aliorder = "九易天下";
					alibody = "九易天下";
					n ++;
				}
				//天下加款-财付通1
				if (sign.equals(funcejb.getMd5Str(sOrderID + sMoney + "bDKEXX2IuMlFdho2"))){
					aliorder = "鼎恒天下加款1";
					alibody = "鼎恒天下加款1";
					this.setAliorder("鼎恒天下加款1");  
					this.setPartnerID("1212013101");   
					this.setPkey("7gtme5jm49coylbu2sircxccmbw2b46z");
					this.setRemark("dhtx1");
					n ++;
				}
				//天下加款-财付通2
				if (sign.equals(funcejb.getMd5Str(sOrderID + sMoney + "HGlZfSk4gmir667o"))){
					aliorder = "鼎恒天下加款2";
					alibody = "鼎恒天下加款2";
					this.setAliorder("鼎恒天下加款2");  
					this.setPartnerID("1211155101");   
					this.setPkey("7gtme5jm49coylbu2sircxccmbw2b46z");
					this.setRemark("dhtx2");
					n ++;
				}
				//瑞达加款
				if (sign.equals(funcejb.getMd5Str(sOrderID + sMoney + "HCsaKVBQ6XGElZeN"))){
					aliorder = "瑞达加款";
					alibody = "瑞达加款"; 
					this.setAliorder("瑞达加款");  
					this.setPartnerID("10000254514");   
					this.setRemark("ruida");
					this.setPkey("30819f300d06092a864886f70d010101050003818d0030818902818100bcc16df8c3fa5f3ae062af8962a8c8f31c2a787a81eb6abb0015c07fdd0ab56a1c6b864ee0f9a897013d868ba10fc63aca14430a789fe5d9f461e4e601ee46945f9ee41265f8e3df710f2284b19c0d1f4cd04ad49b322c9a62fb8664108e6b4e2dcb1c44d2b6b1f8b4ef7b06e1c27cbe4351ec54726491c2512b7d2412f49fe50203010001");
					System.out.println("瑞达加款"); 
					n ++;
				}
				//鼎恒天下加款
				if (sign.equals(funcejb.getMd5Str(sOrderID + sMoney + "JHLThN9WWSLQ1Vf3"))){
					//aliorder = "鼎恒天下";
					this.setAliorder("鼎恒天下");
					alibody =  "鼎恒天下";  
					this.setPartnerID("10000406207");   
					this.setRemark("dhtx");
					this.setPkey("30819f300d06092a864886f70d010101050003818d0030818902818100ae677e899841c16317d258103305734e5d180d033950634d86be40efbb01daa4d6b471694d6408ec6daa3d67534cb0589ed3c7db3c10e6f3c977a3bbd77050b30cd1d6a0b35c9a8abba8897b15ea5e1adfa8b11d0fa62f295fc73c416042b3d9cbd5fcee2ba9cdb9f2768fcbda5564f04f8e479322c65cf8cfe56aa5587eb0bb0203010001");
					System.out.println("鼎恒天下加款");    
					n ++; 
				}   
				//点豆加款
				if (sign.equals(funcejb.getMd5Str(sOrderID + sMoney + "ezn7TaWVM0aCDK4d"))){
					aliorder = "点豆加款";
					alibody  = "点豆加款";
					this.setPartnerID("10000406248");
					this.setRemark("diandou");
					this.setPkey("30819f300d06092a864886f70d010101050003818d0030818902818100b795ed88e54c1e3668d80a2c5e6c3334f02d36fa6eb30024607b1da8598dda74b48835b6a3aee56f2283ecaa87b60c2ecead23e6ff58be372a950a75c554e5d12e64bf26fe87b593ac3c1d8cf86ca410f5619b47847034505c34c5e2b18d9bf7d5dc7c6675ce254210b59cc7c097845707e513ba83187800af250336c68463150203010001");
					System.out.println("南京点豆加款");    
					n ++;
				}
				//般豆加款
				if (sign.equals(funcejb.getMd5Str(sOrderID + sMoney + "FELggnGHI26dhGWI"))){
					aliorder = "般豆加款";
					alibody  = "般豆加款";
					this.setPartnerID("10000406247");
					this.setRemark("bandou");
					this.setPkey("30819f300d06092a864886f70d010101050003818d0030818902818100f0afd5975719794e9762981294cb39a78188ca8a2df5e2dcbee16c3d8fbe2d40b231082474550a37ff9657b5e923dbebab1e065a561e3d518512a7ae0a74d2c5aa37ea6591b2a49ed8e4c294dd6cd4ffeeb4f5f787b253c0db846b9f6954730f9c8d5d2f79f4e3f2f1e52b3d0091e50a9638f85a8e8e346646b82de012302df70203010001");
					System.out.println("杭州般豆加款");    
					n ++;
				}
				if (n != 1){
					return "合法性验证失败,错误代码:" + n + "";
				}
				//是否要查询订单是否存在
				if (!name.equals("")){
					DataConnect dc = new DataConnect(name, false);
					ResultSet rs = dc.query(sql);
					if (rs != null){
						if (rs.next()){
							String fState = funcejb.gets(rs, "fState", "");	//商户跳转的页面
							if (fState.equals("支付成功")||(fState.equals("支付失败"))){
								sError = "订单已经支付完成或超时过期,请重新下单!";
							}
						}else   
							sError = "订单验证失败";
					}else
						sError = "订单验证失败";
					 
					dc.CloseResultSet(rs);
					dc.CloseConnect();
				}
				//完成
			}
			return sError;
		}catch(Exception e){
			return "订单验证出错";
		}
	}
}