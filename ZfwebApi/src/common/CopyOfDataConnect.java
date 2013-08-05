package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CopyOfDataConnect {
	static boolean bIsLoad = false;	//所有调用当前类的,共用一个bIsLoad标识
	Connection connect = null;	//new一次,就会生成一个新的连接, 需要手工关闭
	String sError = "";
	static int ConnectCount = 0;
//	String m_sHost = "192.168.13.136:2433", m_sUser = "jk1115", m_sPassword = "1A0B8E710252C980", m_sDBaseName = "FillCard";
	//String m_sHost = "192.168.13.136:2433", m_sUser = "tyhl", m_sPassword = "810213", m_sDBaseName = "FillCard";
//	String m_sHost = "99.99.99.100:2433", m_sUser = "jk1115", m_sPassword = "1A0B8E710252C980", m_sDBaseName = "FillCard";
	String m_sHost = "99.99.99.100:2433", m_sUser = "jk1019", m_sPassword = "tv4CRONTPAHinrYd", m_sDBaseName = "FillCard";
//		String m_sHost = "59.151.123.72:2433", m_sUser = "tyhl", m_sPassword = "810213", m_sDBaseName = "FillCard";
//	String m_sHost = "118.144.91.36:2433", m_sUser = "tyhl", m_sPassword = "810213", m_sDBaseName = "FillCard";
//	String m_sHost = "123.103.64.122:2433", m_sUser = "tyhl", m_sPassword = "810213", m_sDBaseName = "FillCard";
//	String m_sHost = "127.0.0.1:2433", m_sUser = "tyhl", m_sPassword = "810213", m_sDBaseName = "AccountPool";
	/*
	 * 关闭当前连接
	 */
	public void CloseConnect(){
		try{
			if ((connect != null) && (!connect.isClosed()))
				connect.close();
			//System.out.print("[busics]关闭数据库连接," + m_sHost + "," + m_sDBaseName);
		}catch(Exception e){
			System.out.print("[DataConnect.Close]关闭连接时出错: " + e.toString());
			e.printStackTrace();
		}
		return;
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
			System.out.print("[DataConnect.Close(ResultSet)]关闭结果集时出错" + e.toString());
			e.printStackTrace();
		}
		return;
	}
	
	/* 
	 * 初始化
	 */
	public CopyOfDataConnect() {
		loadDriver();
	}

	/*
	 * 返回一个连接
	 */
	public Connection getConnect(){
		return connect;
	}
	
	
	/*
	 * 装入驱动
	 */
	public static void loadDriver() {
		try {
			if (!bIsLoad) {
				String sDBDriver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
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
			connDBase(m_sHost, m_sUser, m_sPassword, m_sDBaseName);
		}
		try{
			if (connect.isClosed())
				connDBase(m_sHost, m_sUser, m_sPassword, m_sDBaseName);
		}catch(Exception e){
			connect = null;
			connDBase(m_sHost, m_sUser, m_sPassword, m_sDBaseName);
		}
	}
	
	/*
	 * 连接数据库
	 */
	public Connection connDBase(String sHost, String sUser, String sPassword,
			String sDBaseName) {
		try {
			//如果参数不一样,重新连接
			if ((connect==null) || !sHost.equals(m_sHost) || !sUser.equals(m_sUser) || !sPassword.equals(m_sPassword) || !sDBaseName.equals(m_sDBaseName)){
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
						System.out.print("[busics]关闭数据库时异常" + e.toString());
					}
					connect = null;
				}
				//重新得到一个连接
				String sConnStr = "jdbc:microsoft:sqlserver://" + sHost + ";User="
						+ sUser + ";Password=" + sPassword + ";DatabaseName="
						+ sDBaseName;
				connect = DriverManager.getConnection(sConnStr);
				ConnectCount = ConnectCount + 1;
				System.out.print("[busics]建立数据库连接(" + ConnectCount + ")," + sHost + "," + sDBaseName + "");

			}
			return connect;
		} catch (Exception e) {
			msg.out("[DataConnect.connDBase]意外错误:" + e.toString());
			return null;
		}
	}


	/*
	 * 查询,返回数据集
	 */	
	public ResultSet query(String sql) {
		int nError = 0;
		while (true){
			try {
				testConnect();
				//
				Statement stmt = connect.createStatement(	//每个query都会产生一个结果集, 需要手工关闭
				java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
				java.sql.ResultSet.CONCUR_READ_ONLY);
				return stmt.executeQuery(sql);
			} catch (Exception e) {
				msg.out("[DataConnect.query]Error:" + e.toString() + "," + sql);
				String s = e.toString();
				if (s.indexOf("Connection reset") > -1){
					try{
						if (connect.isClosed()) connect = null;
						msg.out("[DataConnect.query]连接被重置,重新连接数据库..." + nError);
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
		int nError = 0;
		while (true){
			try {				
				testConnect();
				//
				Statement stmt = connect.createStatement();
				//同步锁
				int n = 0;
					n = stmt.executeUpdate(sql);
				stmt.close();
				return n;
			} catch (Exception e) {
				//处理已知错误
				if (e.toString().indexOf("中插入重复键") != -1) return -2;
				//其它错误
				sError = e.toString();
				msg.out("[执行出错]" + e.toString() + "," + sql);
				String s = e.toString();
				if (s.indexOf("Connection reset") > -1){
					msg.out("[重新连接]连接被重置,重新连接数据库..." + nError);
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


}
