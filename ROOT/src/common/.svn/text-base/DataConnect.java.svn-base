package common;

/*
 * 数据连接管理
 */

import java.io.File;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import common.Error;


public class DataConnect {
	static String m_LoadDriver = "";	//已装入的驱动
	String test_ip = "192.168.9.140";
	
	//连接
	Connection connect = null;			//new一次,就会生成一个新的连接, 需要手工关闭

	//池用到
	DataSource ds = null;				//
	
	//非池连接用到
	String m_JndiName = "";				//连接实例名
	String m_DriverClass = "";			//驱动串
	String m_ConnectionUrl = "";		//连接串
	String m_UserName = "";				//用户名
	String m_Password = "";				//密码
	//
	String sError = "";					//最后一次的错误
	//
	boolean IsUsePool = true;			//是否使用连接池

	/* 
	 * 初始化
	 */
	public DataConnect(String sJndiName, boolean bIsUsePool) {
		
		IsUsePool = bIsUsePool;
		
		Error.outs("[DataConnect]初始化," + sJndiName, "debug");
		
		m_JndiName = sJndiName;

	}

	public DataConnect(String sDriverClass, String sConnectionUrl, String sUserName, String sPassword, boolean bIsUsePool) {
		
		IsUsePool = bIsUsePool;
		m_DriverClass = sDriverClass;			//驱动串
		m_ConnectionUrl = sConnectionUrl;		//连接串
		m_UserName = sUserName;					//用户名
		m_Password = sPassword;					//密码		
		//
		Error.outs("[DataConnect]初始化," + m_ConnectionUrl, "debug");
		
		m_JndiName = "";

	}

	//装入数据库连接配
	public void loadDbConfig(String sJndiName){ 
		try {    
			Error.outs("[loadDbConfig]装入配置," + sJndiName, "debug");
			if (sJndiName.equals("")) return;
			
			//装入配置
			String sFile = Error.class.getResource("").toString().substring(6) + "mssql-ds.xml";
			int n = sFile.indexOf("default");
			sFile = sFile.substring(0, n) + "default/deploy/mssql-ds.xml";
		    File f = new File(sFile);  
		    SAXReader reader = new SAXReader();    
		    Document doc = reader.read(f);    
		    Element root = doc.getRootElement();    
		    Element foo;    
		    for (Iterator i = root.elementIterator("local-tx-datasource"); i.hasNext();) {    
		    	foo = (Element) i.next();    
		    	String sIsActive = foo.elementText("is-active");    
		    	if ((sIsActive == null) || !sIsActive.equals("false")){		//不为false时有效,如果有多个无isacive值时,最后一个配置有效
		    		String s = foo.elementText("jndi-name");    
		    		if (s.equals(sJndiName)){
		    			m_ConnectionUrl = foo.elementText("connection-url");    
		    			m_DriverClass = foo.elementText("driver-class");    
		    			m_UserName = foo.elementText("user-name");    
		    			m_Password = foo.elementText("password");    
		    			//对用户名和密码解密	<old_password>jk1215_Tam8kJAltwBPRz4L</old_password> 
		    			if (m_UserName.indexOf("(@_@)")==0) 
		    				m_UserName = EncryptsE.uncrypt(m_UserName.substring(5), "EoCR6mZOl9J2qBwYUQp048zhAFWNMLs3");
		    			if (m_Password.indexOf("(@_@)")==0) 
		    				m_Password = EncryptsE.uncrypt(m_Password.substring(5), "EoCR6mZOl9J2qBwYUQp048zhAFWNMLs3");
		    		}
		    	}
		    } 
		    
		} catch (Exception e) {    
		   Error.oute(e);    
		}
	}

	/*
	 * 装入驱动
	 */
	public void loadDriver() {
		//装入驱动
		try {
			if (m_DriverClass.equals("")) return;
			if (m_LoadDriver.indexOf(m_DriverClass) == -1) {	//如果没有装载过驱动
				Error.outs("[loadDriver]装入驱动," + m_DriverClass);
				String sDBDriver = m_DriverClass;
				Class.forName(sDBDriver);
				m_LoadDriver = m_LoadDriver + m_DriverClass;
			}else; 
//				Error.outs("[loadDriver]驱动已经装入," + m_LoadDriver);
		} catch (Exception e) {
			Error.oute(e, "error");
		}
	}

	/*
	 * 关闭当前连接
	 */
	public void CloseConnect(){
		try{
			String s = "";
			if (connect != null){
				Error.outs("[CloseConnect]关闭连接," + s, "debug"); 
				s = connect.toString();
			}
			if (connect == null) return;
			if (!connect.isClosed()) connect.close();
		}catch(Exception e){
			Error.oute(e);
		}
		return;
	}
	
	/*
	 * 关闭当前数据集
	 */
	public void CloseResultSet(ResultSet rs){ 
		try{
			String s = "";
			if (rs != null) s = rs.toString();
			//Error.outs("[CloseResultSet]关闭结果集," + s, "debug"); 
			if (rs == null) return;
			rs.getStatement().close();
			rs.close();
		}catch(Exception e){
			Error.oute(e);	
		}
		return;
	}
	
	

	
	/*
	 * 返回当前连接
	 */
	public Connection getConnect(){
		//Error.outs("[getConnect]返回连接", "debug"); 
		return connect;
	}
	
	/*
	 * 检测数据连接是否正确,如果关闭了或异常,再次连接
	 */
	public void checkConnect(){
		if (connect == null) {
			connectDBase(m_JndiName);
		}
		try{
			if (connect.isClosed())
				connectDBase(m_JndiName);
		}catch(Exception e){
			connect = null;
		}
	}
	
	/*
	 * 连接数据库
	 */
	public Connection connectDBase(String sJndiName) {
		try {
			if (sJndiName.equals("")) sJndiName = m_JndiName;

			//如果本地IP与测试IP相同,则使用测试IP
			String s = InetAddress.getLocalHost().getHostAddress(); 
			System.out.print("本地IP:" + s);
			if (s.equals(test_ip)){
				m_JndiName = m_JndiName + "_TEST";
				System.out.print("连接测试服务器:" + m_JndiName);
			}else
				System.out.print("连接正常服务器:" + m_JndiName);
			//如果参数不一样,重新连接
			if ((connect == null) || (!sJndiName.equals(m_JndiName))){
				//关闭原来的连接
				CloseConnect();
								
				//如果用连接池
				if (IsUsePool){
					//重新得到一个连接
					if (ds == null){
						InitialContext ctx=new InitialContext(); 
						ds = (DataSource)ctx.lookup("java:/" + sJndiName); 
					}
					connect = ds.getConnection();
				}else{
					//装入数据库连接配置gai-dc.xml
					loadDbConfig(m_JndiName);
					
				    //装入当前驱动
				    loadDriver();	

					//创建一个新的连接
					String sConnStr = m_ConnectionUrl;
					Error.outs("连接:" + sConnStr);
					connect = DriverManager.getConnection(sConnStr, m_UserName, m_Password);
				}				
				
			}
			return connect;
		} catch (Exception e) {
			Error.outs("建立数据库连接失败," + e.toString(), "error");
			return null;
		}
	}


	/*
	 * 查询,返回数据集, 默认不用二次缓冲查询
	 */	
	public ResultSet query(String sql) {
		return query(sql, false, "", 0, 0);
	}
	
	/*
	 * 查询,返回数据集
	 */	
	public ResultSet query(String sql, boolean UseCache, String KeyFieldName, int nBeginRow, int nReadCount) {
		int nError = 0;
		while (true){
			try {
				checkConnect();
				//
				//Error.outs("[query]查询," + sql);
				Statement stmt = connect.createStatement(	//每个query都会产生一个结果集, 需要手工关闭
				java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,	//用它听说读取速特别慢
				java.sql.ResultSet.CONCUR_READ_ONLY
				);
				if (stmt == null) return null;
				//在这里做二次缓冲,先读取主表id,再读取指定id的记录
				if (UseCache)  
					return queryBuffer(stmt, sql, KeyFieldName, nBeginRow, nReadCount);
				else
					return stmt.executeQuery(sql);
			} catch (Exception e) {
				Error.outs("[query]查询出错:" + e.toString());
				String s = e.toString();
				if ((s.indexOf("Connection reset") > -1)
						||(s.indexOf("该连接已关闭") > -1)){
					try{
						if (connect.isClosed()) connect = null;
						Error.outs("[query]连接被重置,重新连接数据库..." + nError);
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
					Error.outs("数据库连接为空,sql执行失败:" + sql, "error");
					return -1;
				}
				//Error.outs("[execute]执行," + sql);
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
				sError = e.toString();
				if (e.toString().indexOf("中插入重复键") != -1) return -2;
				//其它错误
				Error.outs("[执行出错]" + sql);
				Error.oute(e);
				String s = e.toString();
				if ((s.indexOf("Connection reset") > -1)
					||(s.indexOf("该连接已关闭") > -1)){
					Error.outs("[重新连接]连接被重置,重新连接数据库..." + nError);
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
	 * 
	 */
	private ResultSet queryBuffer(Statement stmt, String sql, String KeyFieldName, int nBeginRow, int nReadCount){
		try{
			//用主键查询
			String sOldSelect = fc.getString(sql.toLowerCase(), "select", "from");
			String sNewSql = "SELECT " + KeyFieldName + " FROM " + fc.getString(sql.toLowerCase() + "_|_|_!@#$%^&*()_|_|_", "from", "_|_|_!@#$%^&*()_|_|_");
			ResultSet rs = stmt.executeQuery(sNewSql);
			if (rs == null) return null;
			int n = 0;
			String sIdList = "";
			while (rs.absolute(nBeginRow) && (n < nReadCount)){
				sIdList = sIdList + ",'" + fc.getrv(rs, KeyFieldName, "") + "'";
				//
				n++;
				rs.next();
			}
			//组成二次查询语句
			String sNewWhere = " " + KeyFieldName + " IN (" + sIdList + ")";
			rs.close();
			sNewSql = "SELECT " + sOldSelect + " FROM " + sNewWhere;
			rs = stmt.executeQuery(sNewSql);
			
			//执行查询
			return stmt.executeQuery(sql);
		}catch(Exception e){
			return null;
		}
	}
	
}
