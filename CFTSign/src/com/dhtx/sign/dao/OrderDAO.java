package com.dhtx.sign.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.dhtx.sign.pojo.Order;
import com.dhtx.sign.util.Tools;
import com.dhtx.sign.util.global.DataConnect;
import com.dhtx.sign.util.global.fc;

/**
 * 类名称：OrderDAO   
 * 类描述：
 * 创建人: renfy   
 * 创建时间：2012-1-4 下午01:30:39   
 * @version 1.0
 *  
 */
public class OrderDAO {
	
	private static DataConnect dc = new DataConnect("cftsign", false);
	
	/**
	 * 
	 * 方法名称: addOrder 
	 * 方法描述: 写入订单
	 * 创建人: renfy
	 * 创建时间: 2012-1-4 下午02:01:17
	 * @param order
	 * @return
	 * @version 1.0
	 *
	 */
	public boolean addOrder(Order order){
		
		//信息内容验证
		if(order == null){
			return false;
		}
		if("".equals(fc.changNull(order.getFclientID()))){
			return false;
		}
		if("".equals(fc.changNull(order.getfSignName()))){
			return false;
		}
		if("".equals(fc.changNull(order.getFmoney()))){
			return false;
		}
		if("".equals(fc.changNull(order.getfCostMoney()))){
			return false;
		}
		if("".equals(fc.changNull(order.getfCharTypeName()))){
			return false;
		}
//		if("".equals(fc.changNull(order.getFbgPictureID()))){
//			return false;
//		}
		
		try {
			float money = Float.parseFloat(order.getFmoney());
			float costMoney  = Float.parseFloat(order.getfCostMoney());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		//
		String pictureURL = Tools.getFolderName();
		//
		String sis = "";
		sis = fc.SetSqlInsertStr(sis, "fID", order.getFid());
		sis = fc.SetSqlInsertStr(sis, "fClientID", order.getFclientID());
		sis = fc.SetSqlInsertStr(sis, "fSignName", order.getfSignName());
		sis = fc.SetSqlInsertStr(sis, "fState", "0");
		sis = fc.SetSqlInsertStr(sis, "fCreateTime", "GETDATE()", false);
		sis = fc.SetSqlInsertStr(sis, "fEndTime", "GETDATE()", false);
		sis = fc.SetSqlInsertStr(sis, "fProductName", order.getFproductName());
		sis = fc.SetSqlInsertStr(sis, "fMoney", order.getFmoney(),false);
		sis = fc.SetSqlInsertStr(sis, "fCostMoney", order.getfCostMoney(), false);
		sis = fc.SetSqlInsertStr(sis, "fCharTypeName", order.getfCharTypeName());
//		sis = fc.SetSqlInsertStr(sis, "fBGPictureID", order.getFbgPictureID());
		sis = fc.SetSqlInsertStr(sis, "fLockID", "");
		sis = fc.SetSqlInsertStr(sis, "fPictureName", order.getFid());
		sis = fc.SetSqlInsertStr(sis, "fPictureURL", pictureURL);
		String sql = "insert into tOrder " + sis;
		int m = dc.execute(sql);
		return m == 1 ? true : false;
		//
//		DataConnect dc2 = new DataConnect("cftsign", false);
//		Statement stmt = null;
//		int m = 0;
//		try {
//			dc2.checkConnect();
//			stmt = dc2.getConnect().createStatement();
//			dc2.getConnect().setAutoCommit(false);
//			m = stmt.executeUpdate(sql);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		try {
//			System.out.println(m);
//			if (m != 1){
//				dc2.getConnect().rollback();	
//				stmt.close();
//				dc2.CloseConnect();
//				return false;
//			}else{
//				dc2.getConnect().commit();
//				stmt.close();
//				dc2.CloseConnect();
//				return true;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
	}

	/**
	 * 
	 * 方法名称: getOrderById 
	 * 方法描述: 
	 * 创建人: renfy
	 * 创建时间: 2012-1-4 下午05:59:34
	 * @param orderID
	 * @return
	 * @version 1.0
	 *
	 */
	public Order getOrderById(String orderID){
		//SQL需要加fstate
		String sql = "select * from tOrder where fID='"+orderID+"'";
		ResultSet rs = null;
		Order order = null;
		try {
			rs = dc.query(sql);
			if(rs != null && rs.next()){
				order = new Order();
				order.setFid(fc.getrv(rs, "fID", ""));
				order.setFclientID(fc.getrv(rs, "fClientID", ""));
				order.setfSignName(fc.getrv(rs, "fSignName", ""));
				order.setFstate(fc.getrv(rs, "fState", ""));
				order.setFcreateTime(fc.getrv(rs, "fCreateTime", ""));
				order.setFendTime(fc.getrv(rs, "fEndTime", ""));
				order.setFproductName(fc.getrv(rs, "fProductName", ""));
				order.setFmoney(fc.getrv(rs, "fMoney", ""));
				order.setfCostMoney(fc.getrv(rs, "fCostMoney", ""));
				order.setfCharTypeName(fc.getrv(rs, "fCharTypeName", ""));
//				order.setFbgPictureID(fc.getrv(rs, "fBGPictureID", ""));
				order.setFlockID(fc.getrv(rs, "fLockID", ""));
				order.setFpictureName(fc.getrv(rs, "fPictureName", ""));
				order.setFpictureURL(fc.getrv(rs, "fPictureURL", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		dc.CloseResultSet(rs);
		return order;
	}
	
	/**
	 * 
	 * 方法名称: getOrderByClientID 
	 * 方法描述: 获得用户的个性签名
	 * 创建人: renfy
	 * 创建时间: 2012-1-9 下午07:37:27
	 * @param clientID
	 * @return
	 * @version 1.0
	 *
	 */
	public List<Order> getSuccessOrderByClientID(String clientID){
		List<Order> result = new ArrayList<Order>();
		String sql = "select * from tOrder where fClientID='"+clientID+"'  and fState='2' order by fCreateTime desc";
		ResultSet rs = null;
		Order order = null;
		try {
			rs = dc.query(sql);
			while(rs != null && rs.next()){
				order = new Order();
				order.setFid(fc.getrv(rs, "fID", ""));
				order.setFclientID(fc.getrv(rs, "fClientID", ""));
				order.setfSignName(fc.getrv(rs, "fSignName", ""));
				order.setFstate(fc.getrv(rs, "fState", ""));
				order.setFcreateTime(fc.getrv(rs, "fCreateTime", "").substring(0, 10));
				order.setFendTime(fc.getrv(rs, "fEndTime", ""));
				order.setFproductName(fc.getrv(rs, "fProductName", ""));
				order.setFmoney(fc.getrv(rs, "fMoney", ""));
				order.setfCostMoney(fc.getrv(rs, "fCostMoney", ""));
				order.setfCharTypeName(fc.getrv(rs, "fCharTypeName", ""));
//				order.setFbgPictureID(fc.getrv(rs, "fBGPictureID", ""));
				order.setFlockID(fc.getrv(rs, "fLockID", ""));
				order.setFpictureName(fc.getrv(rs, "fPictureName", ""));
				order.setFpictureURL(fc.getrv(rs, "fPictureURL", ""));
				result.add(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		dc.CloseResultSet(rs);
		return result;
	}
	
	/**
	 * 
	 * 方法名称: getAllOrderByClientID 
	 * 方法描述: 获得当前用户所有的订单信息
	 * 创建人: renfy
	 * 创建时间: 2012-1-9 下午07:41:18
	 * @param clientID
	 * @return
	 * @version 1.0
	 *
	 */
	public List<Order> getAllOrderByClientID(String clientID){
		List<Order> result = new ArrayList<Order>();
		String sql = "select * from tOrder where fClientID='"+clientID+"' and fstate in ('1','2') order by fState,fCreateTime desc";
		ResultSet rs = null;
		Order order = null;
		try {
			rs = dc.query(sql);
			while(rs != null && rs.next()){
				order = new Order();
				order.setFid(fc.getrv(rs, "fID", ""));
				order.setFclientID(fc.getrv(rs, "fClientID", ""));
				order.setfSignName(fc.getrv(rs, "fSignName", ""));
				order.setFstate(fc.getrv(rs, "fState", ""));
				order.setFcreateTime(fc.getrv(rs, "fCreateTime", ""));
				order.setFendTime(fc.getrv(rs, "fEndTime", ""));
				order.setFproductName(fc.getrv(rs, "fProductName", ""));
				order.setFmoney(fc.getrv(rs, "fMoney", ""));
				order.setfCostMoney(fc.getrv(rs, "fCostMoney", ""));
				order.setfCharTypeName(fc.getrv(rs, "fCharTypeName", ""));
//				order.setFbgPictureID(fc.getrv(rs, "fBGPictureID", ""));
				order.setFlockID(fc.getrv(rs, "fLockID", ""));
				order.setFpictureName(fc.getrv(rs, "fPictureName", ""));
				order.setFpictureURL(fc.getrv(rs, "fPictureURL", ""));
				result.add(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		dc.CloseResultSet(rs);
		return result;
	}
	
	
	/**
	 * 
	 * 方法名称: updateOrderState 
	 * 方法描述: 
	 * 创建人: renfy
	 * 创建时间: 2012-1-5 上午09:53:01
	 * @param orderID
	 * @return
	 * @version 1.0
	 *
	 */
	public boolean updateOrderState(String orderID){
		String sql = "update tOrder set fState='1',fEndTime=GETDATE() where fid='"+orderID+"' and fState='0'";
		int m = dc.execute(sql);
		return m == 1 ? true : false;
	}
	
	/**
	 * 
	 * 方法名称: getOrderToApp 
	 * 方法描述: 为生成个性签名程序提供的接口
	 * 创建人: renfy
	 * 创建时间: 2012-1-5 下午02:47:40
	 * @return
	 * @version 1.0
	 *
	 */
	public Order getOrderToApp(){
		String sql = "select top 1 * from tOrder where fCreateTime > GETDATE() - 0.5 and fstate='1' and fLockID='' order by fCreateTime asc";
		ResultSet rs = null;
		Order order = null;
		try {
			rs = dc.query(sql);
			if(rs != null && rs.next()){
				order = new Order();
				order.setFid(fc.getrv(rs, "fID", ""));
				order.setFclientID(fc.getrv(rs, "fClientID", ""));
				order.setfSignName(fc.getrv(rs, "fSignName", ""));
				order.setFstate(fc.getrv(rs, "fState", ""));
				order.setFcreateTime(fc.getrv(rs, "fCreateTime", ""));
				order.setFendTime(fc.getrv(rs, "fEndTime", ""));
				order.setFproductName(fc.getrv(rs, "fProductName", ""));
				order.setFmoney(fc.getrv(rs, "fMoney", ""));
				order.setfCostMoney(fc.getrv(rs, "fCostMoney", ""));
				order.setfCharTypeName(fc.getrv(rs, "fCharTypeName", ""));
//				order.setFbgPictureID(fc.getrv(rs, "fBGPictureID", ""));
				order.setFlockID(fc.getrv(rs, "fLockID", ""));
				order.setFpictureName(fc.getrv(rs, "fPictureName", ""));
				order.setFpictureURL(fc.getrv(rs, "fPictureURL", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		dc.CloseResultSet(rs);
		return order;
	}
	
	public boolean lockOrder(String orderID){
		String lockID = fc.getGUID6("");
		String sql = "update tOrder set fLockID='"+lockID+"' where fid='"+orderID+"' and fLockID=''";
		int m = dc.execute(sql);
		return m == 1 ? true : false;
	}

	/**
	 * 方法名称: appUpdateState 
	 * 方法描述: app通知制图完成
	 * 创建人: renfy
	 * 创建时间: 2012-1-5 下午04:17:15
	 * @param orderID
	 * @return
	 * @version 1.0
	 * 
	 */ 
	public boolean appUpdateState(String orderID) {
		String sql = "update tOrder set fState='2',fEndTime=GETDATE(),fLockID=''  where fid='"+orderID+"' and fState='1'";
		int m = dc.execute(sql);
		return m == 1 ? true : false;
	}
	
	/**
	 * 
	 * 方法名称: getOrderByClient 
	 * 方法描述: 查询用户的所有订单
	 * 创建人: renfy
	 * 创建时间: 2012-1-5 下午04:58:04
	 * @param fClientID
	 * @return
	 * @version 1.0
	 *
	 */
	public Order getOrderByClient(String fClientID){
		//SQL需要加fstate
		String sql = "select * from tOrder where fClientID='"+fClientID+"'";
		ResultSet rs = null;
		Order order = null;
		try {
			rs = dc.query(sql);
			if(rs != null && rs.next()){
				order = new Order();
				order.setFid(fc.getrv(rs, "fID", ""));
				order.setFclientID(fc.getrv(rs, "fClientID", ""));
				order.setfSignName(fc.getrv(rs, "fSignName", ""));
				order.setFstate(fc.getrv(rs, "fState", ""));
				order.setFcreateTime(fc.getrv(rs, "fCreateTime", ""));
				order.setFendTime(fc.getrv(rs, "fEndTime", ""));
				order.setFproductName(fc.getrv(rs, "fProductName", ""));
				order.setFmoney(fc.getrv(rs, "fMoney", ""));
				order.setfCostMoney(fc.getrv(rs, "fCostMoney", ""));
				order.setfCharTypeName(fc.getrv(rs, "fCharTypeName", ""));
//				order.setFbgPictureID(fc.getrv(rs, "fBGPictureID", ""));
				order.setFlockID(fc.getrv(rs, "fLockID", ""));
				order.setFpictureName(fc.getrv(rs, "fPictureName", ""));
				order.setFpictureURL(fc.getrv(rs, "fPictureURL", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		dc.CloseResultSet(rs);
		return order;
	}
}
