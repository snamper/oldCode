package ty_Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataConnect {
	static Connection connect = null;
	static boolean bIsLoad = false;
	String sError = "";
	static int ConnectCount = 0;
	String m_sHost = "", m_sUser = "", m_sPassword = "", m_sDBaseName = "";
 
	/* 
	 * 初始化
	 */
	public DataConnect() {
		loadDriver();
//		Encrypts.getkey();
//		String user = Encrypts.unencrypt("Z9C5H7X4KT0YUN3FO12VWMJEI8BGAQPRS6LDINIXJSJM");
//		String pass = Encrypts.unencrypt("Q1TDPIKYX0758HCB9NSOAZ2LFWJ43MEUVGR6VMVBVYVPVBVV");
	}

	/*
	 * 返回一个连接
	 */
	public Connection getConnect(String sID){
		return connect;
	}
	
	
	/*
	 * 装入驱动
	 */
	public static void loadDriver() {
		try {
			if (!bIsLoad) {
				String sDBDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
				Class.forName(sDBDriver);
				bIsLoad = true;
			}
		} catch (Exception e) {
			System.out.print("[DataConnect][loadDriver]Error:" + e.toString());
			bIsLoad = false;
		}
	}

	/*
	 * 测试数据连接是否正确,如果关闭了或异常,再次连接
	 */
	public void testConnect(){
		if (connect == null) {
			System.out.print("[Dataconnect.testConnect]默认连接到库fillcard");
			connDBase(m_sHost, m_sUser, m_sPassword, m_sDBaseName);
		}
		try{
			if (connect.isClosed())
				connDBase(m_sHost, m_sUser, m_sPassword, m_sDBaseName);
		}catch(Exception e){
			connDBase(m_sHost, m_sUser, m_sPassword, m_sDBaseName);
		}
	}
	
	/*
	 * 连接数据库
	 */
	public Connection connDBase(String sHost, String sUser, String sPassword,
			String sDBaseName) {
		try {
			//默认值"192.168.2.32:1433", "zflogin", "P@ssw0rdzf", "fillcard"
			if (sHost.equals("")) sHost = "59.151.123.72:2433";
			if (sUser.equals("")) sUser = "tyhl";
			if (sPassword.equals("")) sPassword = "810213";
			if (sDBaseName.equals("")) sDBaseName = "fillcard";
			//如果参数不一样,重新连接
			if (!sHost.equals(m_sHost) || !sUser.equals(m_sUser) || !sPassword.equals(m_sPassword) || !sDBaseName.equals(m_sDBaseName)){
				//保存参数
				m_sHost = sHost;
				m_sUser = sUser;
				m_sPassword = sPassword;
				m_sDBaseName = sDBaseName;
				//关闭原来的连接
				if (connect != null) { 
					try{
						connect.close();
					}catch(Exception e){
						System.out.print("[Dataconnect.connDBase]关闭数据库失败");
					}
					connect = null;
				}
				//重新得到一个连接
				String sConnStr = "jdbc:sqlserver://" + sHost + ";User="
						+ sUser + ";Password=" + sPassword + ";DatabaseName="
						+ sDBaseName;
				connect = DriverManager.getConnection(sConnStr);
				ConnectCount = ConnectCount + 1;
			}
			return connect;
		} catch (Exception e) {
			//msg.out("[DataConnect.connDBase]意外错误:" + e.toString());
			return null;
		}
	}


	/*
	 * 查询,返回数据集
	 */	
	public ResultSet query(String sql) {
		try {
			testConnect();
			//
			Connection conn = connect;
			//
			Statement stmt = conn.createStatement(
					java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
					java.sql.ResultSet.CONCUR_READ_ONLY);
			try{
				return stmt.executeQuery(sql);
			}catch(Exception e){
					return stmt.executeQuery(sql);
			}
		} catch (Exception e) {
			//msg.out("[DataConnect.query]Error:" + e.toString());
			return null;
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
	public int execute(String sql) {
		try {
			testConnect();
			//
			Connection conn = connect;
			Statement stmt = conn.createStatement();
			int n = stmt.executeUpdate(sql);
			stmt.close();
			return n;
		} catch (Exception e) {
			//处理已知错误
			if (e.toString().indexOf("中插入重复键") != -1) return 10;
			//其它错误
			sError = e.toString();
			//msg.out("[execute]Error:" + e.toString());
			return -1;
		}
	}

	/*
	 * 返回最后一次错误信息
	 */
	public String getError() {
		return sError;
	}


}
