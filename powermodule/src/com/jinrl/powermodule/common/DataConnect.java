package com.jinrl.powermodule.common;

/*
 * 数据库连接类
 */

import java.io.File;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;



public class DataConnect {
	static String m_LoadDriver = "";	//已装入的驱动

	//连接
	Connection connect = null;			//new一次,就会生成一个新的连接, 需要手工关闭

	//
	String m_JndiName = "";				//连接实例名
	//
	String sError = "";					//最后一次的错误
	//
	boolean IsUsePool = true;			//是否使用连接池
	//



	//
	/*
	 * 初始化
	 */
	public DataConnect(String sJndiName, boolean bIsUsePool) {
		IsUsePool = bIsUsePool;
		m_JndiName = sJndiName;
	}


	/*
	 * 关闭当前连接
	 */
	public void CloseConnect(){
		try{
			ConnectPool.CloseConnect(connect);
			connect = null;
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/*
	 * 关闭当前数据集
	 */
	public void CloseResultSet(ResultSet rs){
		try{
			if (rs == null) return;
			rs.getStatement().close();
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}




	/*
	 * 返回当前连接
	 */
	public Connection getConnect(){
		return connect;
	}

	/*
	 * 检测数据连接是否正确,如果关闭了或异常,再次连接
	 */
	public void checkConnect(){
		try{
			if (connect == null)
				connectDBase();
			if (connect.isClosed())
				ConnectPool.ReConnect(connect);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/*
	 * 连接数据库
	 */
	public Connection connectDBase() {
		try {
			//如果本地IP与测试IP相同,则使用测试IP
			String s = InetAddress.getLocalHost().getHostAddress();
			if ("192.168.9.140".indexOf(s) > -1){
				System.out.print("[" + s + "]连接测试服务器:" + m_JndiName + "_TEST");
			}else
				fc.message("[" + s + "]连接正常服务器:" + m_JndiName);

			//如果参数不一样,重新连接
			if ((connect == null)){
				//如果用连接池
				if (IsUsePool){
				    //从池中返回一个连接
				    connect = ConnectPool.GetConnect(m_JndiName, null);
				}else{
				    connect = ConnectPool.GetConnect(m_JndiName, this);
				}

			}
			return connect;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	/*
	 * 查询,返回数据集, 默认不用二次缓冲查询
	 */
	public ResultSet query(String sql) {
		int nError = 0;
		while (true){
			try {
				checkConnect();
				//
				Statement stmt = connect.createStatement(	//每个query都会产生一个结果集, 需要手工关闭
				java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,	//用它听说读取速特别慢
				java.sql.ResultSet.CONCUR_READ_ONLY
				);
				if (stmt == null) return null;

				//查询
				return stmt.executeQuery(sql);
			} catch (Exception e) {
				System.out.print("[query]查询出错:" + e.toString());
				sError = e.toString();
				if ((sError.indexOf("Connection reset") > -1)
						|| (sError.indexOf("socket closed") > -1)
						|| (sError.indexOf("socket write error") > -1)
						|| (sError.indexOf("该连接已关闭") > -1)){
					try{
						if (connect.isClosed()) connect = null;
						System.out.print("[query]连接被重置,重新连接数据库..." + nError);
						connect = null;
						nError = nError + 1;
						if (nError >= 3) return null;
						continue;
					}catch(Exception ee){

					}
				}
				return null;
			}
		}

	}

	/*
	 * 执行更新,返回成功记录数
	 *
	 * -1 其它错误
	 * 0 成功
	 *
	 * 1x 插入记录错误
	 * 10 主键重复
	 *
	 * 2x 删除记录错误
	 *
	 * 3x 更新记录错误
	 */
	private byte[] lock = new byte[0]; // 特殊的instance变量

	public int execute(String sql) {
		return execute(sql, true);
	}


	public int execute(String sql, boolean bReRowCount) {
		int nError = 0;
		while (true){
			try {
				checkConnect();
				//
				if (connect == null){
					System.out.print("数据库连接为空,sql执行失败:" + sql);
					return -1;
				}
				//
				int n = 0;
				Statement stmt = connect.createStatement();
				if (bReRowCount){
					n = stmt.executeUpdate(sql);
				}else
					if (stmt.execute(sql))
						n = 1;
					else
						n = 0;
				stmt.close();
				return n;
			} catch (Exception e) {
				//处理已知错误
				if (e.toString().indexOf("中插入重复键") != -1) return -2;
				//其它错误
				sError = e.toString();
				System.out.print("[执行出错]" + sError);
				if ((sError.indexOf("Connection reset") > -1)
						|| (sError.indexOf("socket closed") > -1)
						|| (sError.indexOf("socket write error") > -1)
						|| (sError.indexOf("该连接已关闭") > -1)){
					System.out.print("[重新连接]连接被重置,重新连接数据库..." + nError);
					connect = null;
					nError = nError + 1;
					if (nError >= 3) return -1;
					continue;
				}
				return -1;
			}

		}
	}

	/*
	 * 返回最后一次错误信息
	 */
	public String getError() {
		return sError;
	}

	/*
	 * 返回连接串
	 */
	public String getConnUrl(){
		return ConnectPool.GetConnectUrl(this.connect);
	}




}
