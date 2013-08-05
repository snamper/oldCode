package com.agents.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import oracle.jdbc.OracleTypes;

/**
 * 类名称：ConnOracle   
 * 类描述：
 * 创建人: renfy   
 * 创建时间：2011-12-23 下午03:29:13   
 * @version 1.0
 *  
 */
public class ConnOracle {
	
	DataConnect dc = new DataConnect("hnczoracle", true);
	
	private Statement stmt = null;
	private ResultSet rs = null;
	private Connection conn = null;
	
	private Connection getConn() throws ClassNotFoundException, SQLException{
//		String driver = "oracle.jdbc.driver.OracleDriver";
//		String strUrl = "jdbc:oracle:thin:@172.16.97.2:1521:x5";
//		Class.forName(driver);
//		Connection conn = DriverManager.getConnection(strUrl, "bmw", "zhanglq");
		dc.checkConnect();
		conn = dc.getConnect();
		return conn;
	}
	
	private void closeAll(Connection conn,Statement stmt,ResultSet rs){
		try {
			if(rs != null){
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			if(stmt != null){
				stmt.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
//		try {
//			if(conn != null){
//				conn.close();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	
	/**
	 * 
	 * 方法名称: checkLogin 
	 * 方法描述: 登录，成功返回用户名，失败返回NULL
	 * 创建人: renfy
	 * 创建时间: 2011-12-23 下午04:55:35
	 * @param username
	 * @param password
	 * @return
	 * @version 1.0
	 *
	 */
	public String checkLogin(String username,String password){
		String result = null;

		try {
			conn = getConn();
			CallableStatement proc = conn.prepareCall("{call web_login(?,?,?)}");
			proc.setString(1, username);
			proc.setString(2, password);
			proc.registerOutParameter(3, Types.VARCHAR);
			proc.execute();
			result = proc.getString(3);
			System.out.println(result);
		} catch (Exception ex2) {
			ex2.printStackTrace();
		} finally {
			closeAll(conn, stmt, rs);
		}
		
		return result;
	}
	
	
	/**
	 * 
	 * 方法名称: changPWD 
	 * 方法描述: 修改密码,0失败，1成功
	 * 创建人: renfy
	 * 创建时间: 2011-12-23 下午04:56:00
	 * @param userID
	 * @param oldPWD
	 * @param newPWD
	 * @return
	 * @version 1.0
	 *
	 */
	public boolean changPWD(String userID,String oldPWD,String newPWD){
		String result = null;

		try {
			conn = getConn();
			CallableStatement proc = conn.prepareCall("{call web_changePlatUserInfo(?,?,?,?)}");
			proc.setString(1, userID);
			proc.setString(2, oldPWD);
			proc.setString(3, newPWD);
			proc.registerOutParameter(4, Types.VARCHAR);
			proc.execute();
			result = proc.getString(4);
		} catch (Exception ex2) {
			ex2.printStackTrace();
		} finally {
			closeAll(conn, stmt, rs);
		}
		return ("1".equals(result)) ? true : false;
	}
	
	/**
	 * 
	 * 方法名称: addMoney 
	 * 方法描述: 用户加款
	 * 创建人: renfy
	 * 创建时间: 2011-12-23 下午11:57:38
	 * @param userID
	 * @param money
	 * @return
	 * @version 1.0
	 *
	 */
	public boolean addMoney(String userID,String money){
		String result = null;

		try {
			conn = getConn();
			CallableStatement proc = conn.prepareCall("{call web_AddMoneyForPlat(?,?,?)}");
			proc.setString(1, userID);
			proc.setString(2, money);
			proc.registerOutParameter(3, Types.VARCHAR);
			proc.execute();
			result = proc.getString(3);
		} catch (Exception ex2) {
			ex2.printStackTrace();
		} finally {
			closeAll(conn, stmt, rs);
		}
		return ("1".equals(result)) ? true : false;
	}
	
	
	/**
	 * 
	 * 方法名称: getFillLog 
	 * 方法描述: 获得充值记录
	 * 创建人: renfy
	 * 创建时间: 2011-12-23 下午05:23:49
	 * @param userID
	 * @param startTime
	 * @param endTime
	 * @param orderID	订单号
	 * @param fillNum   充值号码
	 * @return
	 * @version 1.0
	 *
	 */
	public String getFillLog(String userID,String startTime,String endTime,String orderID,String fillNum){
		String result = null;

		try {
			
			conn = getConn();
			CallableStatement proc = conn.prepareCall("{call web_getFdrByPlat(?,?,?,?,?,?)}");
			proc.setString(1, userID);
			proc.setString(2, startTime);
			proc.setString(3, endTime);
			proc.setString(4, orderID);
			proc.setString(5, fillNum);
			proc.registerOutParameter(6, OracleTypes.CURSOR);
			proc.execute();
			rs = (ResultSet)proc.getObject(6);
			
			
			ResultSetMetaData rsd= rs.getMetaData();
			int a = rsd.getColumnCount();
			System.out.println(a);
		} catch (Exception ex2) {
			ex2.printStackTrace();
		} finally {
			closeAll(conn, stmt, rs);
		}
		
		//去掉
//		return result;
		return "";
	}
	
	
	/**
	 * 
	 * 方法名称: getMoneyChange 
	 * 方法描述: 获得资金变动记录
	 * 创建人: renfy
	 * 创建时间: 2011-12-23 下午07:33:26
	 * @param userID
	 * @param startTime
	 * @param endTime
	 * @return
	 * @version 1.0
	 *
	 */
	public String getMoneyChange(String userID,String startTime,String endTime){
		String result = null;

		try {
			conn = getConn();
			CallableStatement proc = conn.prepareCall("{call web_getPMCLog(?,?,?,?)}");
			proc.setString(1, userID);
			proc.setString(2, startTime);
			proc.setString(3, endTime);
			proc.registerOutParameter(4, OracleTypes.CURSOR);
			proc.execute();
			rs = (ResultSet)proc.getObject(4);
			ResultSetMetaData rsd= rs.getMetaData();
			int a = rsd.getColumnCount();
			System.out.println(a);
		} catch (Exception ex2) {
			ex2.printStackTrace();
		} finally {
			closeAll(conn, stmt, rs);
		}
		
		//去掉
//		return result;
		return "test_oracle";
	}
	
	
	public static void main(String[] args) {
		new ConnOracle().getFillLog("hncz001", "2011-12-20 10:00:00", "2011-12-24 10:00:00", null, null);
	}

}
