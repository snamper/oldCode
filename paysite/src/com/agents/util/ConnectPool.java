package com.agents.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/*
 * 连接池类
 */
public class ConnectPool {


	static List<ConnectInfo> list = new ArrayList<ConnectInfo>();

	/*
	 * 从池中返回一个连接,根据mssql-ds.xml
	 */
	public static Connection GetConnect(String JndiName, DataConnect dc) {
		try{
			//获得连接参数
			String DriverClass="", ConnectionUrl="", UserName="", Password = "";

			//装入配置
			if (JndiName.equals("")){
				System.out.print("[ConnectPool]JNDI名称为空,无法装入配置");
				return null;
			}
			//
			System.out.print("[ConnectPool]装入配置," + JndiName);
			//
			String sFile = DataConnect.class.getResource("").toString().substring(6) + "mssql-ds.xml";
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
		    		if (s.equals(JndiName)){
		    			ConnectionUrl = foo.elementText("connection-url");
		    			DriverClass = foo.elementText("driver-class");
		    			UserName = foo.elementText("user-name");
		    			Password = foo.elementText("password");
		    		}
		    	}
		    }

		    //是否要解密
//		    if (ConnectionUrl.indexOf("(@*@)") == 0 ) ConnectionUrl = Encrypts.uncrypt(ConnectionUrl.substring(5), "connection");
//		    if (DriverClass.indexOf("(@*@)") == 0 ) ConnectionUrl = Encrypts.uncrypt(DriverClass.substring(5), "driver");
//		    if (UserName.indexOf("(@*@)") == 0 ) ConnectionUrl = Encrypts.uncrypt(UserName.substring(5), "user");
//		    if (Password.indexOf("(@*@)") == 0 ) ConnectionUrl = Encrypts.uncrypt(Password.substring(5), "password");

		    //获取连接
			return GetConnect(DriverClass, ConnectionUrl, UserName, Password, dc);

		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * 从池中返回一个连接,根据指定参数
	 */
	public static Connection GetConnect(String DriverClass, String ConnectionUrl, String UserName, String Password, DataConnect dc) {
		try{
			//在池中查找是否有已建立好的连接
			for (int i=0; i < list.size(); i++){
				ConnectInfo con = list.get(i);
				if (con.DriverClass.equals(DriverClass) && con.ConnectionUrl.equals(ConnectionUrl)
						&& con.UserName.equals(UserName) && con.Password.equals(Password)
						&& con.dc == dc){
					con.UseCount ++;
					System.out.print("[ConnectPool]返回已有连接:" + con + ">>" + con.conn);
					PrintConnect();
					return con.conn;	//如果有,返回已有连接
				}
			}
			//如果没有新建一个
			//装入驱动
			System.out.print("[ConnectPool]建立新连接:" + ConnectionUrl);
			//
			String sDBDriver = DriverClass;
			Class.forName(sDBDriver);
			//建立连接
			Connection newConn = DriverManager.getConnection(ConnectionUrl, UserName, Password);
			//保存到池
			ConnectInfo con = new ConnectInfo();
			con.conn = newConn;
			con.dc = dc;
			con.DriverClass = DriverClass;
			con.ConnectionUrl = ConnectionUrl;
			con.UserName = UserName;
			con.Password = Password;
			con.UseCount ++;
			list.add(con);
			System.out.print("[ConnectPool]返回新连接:" + con + ">>" + con.conn);
			//
			PrintConnect();
			return newConn;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}

	}

	/*
	 * 从池中返回一个连接,根据mssql-ds.xml
	 */
	public static void CloseConnect(Connection connect) {
		try{
			//从池中找到连接
			for (int i=0; i < list.size(); i++){
				ConnectInfo con = list.get(i);
				if (con.conn.equals(connect)){
					//如果是独占的连接,关闭连接
					if (con.dc != null) {
						System.out.print("[ConnectPool]关闭连接:" + connect.toString());
						con.conn.close();
						con.conn = null;
						con.dc = null;
						list.remove(i);
					}else{
						System.out.print("[ConnectPool]释放连接:" + connect.toString());
						con.UseCount --;
					}
					PrintConnect();
					return;
				}
			}
		}catch(Exception e){

		}
	}

	/*
	 * 重新连接
	 */
	public static Connection ReConnect(Connection connect) {
		try{
			//从池中找到连接
			for (int i=0; i < list.size(); i++){
				ConnectInfo con = list.get(i);
				if (con.conn.equals(connect)){
					System.out.print("[ConnectPool]重新连接:" + connect.toString());
					//建立连接
					Connection newConn = DriverManager.getConnection(con.ConnectionUrl, con.UserName, con.Password);
					//保存
					con.conn = newConn;
					//
					PrintConnect();
					return newConn;
				}
			}
			System.out.print("[ConnectPool]重新连接失败");
			PrintConnect();
			return null;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/*
	 *
	 */
	public static void PrintConnect() {
		if (true) return;
		System.out.print("---------连接池信息---------");
		for (int i=0; i < list.size(); i++){
			ConnectInfo con = list.get(i);
			System.out.print("------[" + i + "]" + con.toString());
			System.out.print("		[ConnectObject]" + con.conn);
			System.out.print("		[DataConnect]" + con.dc);
			System.out.print("		[DriverClass]" + con.DriverClass);
			System.out.print("		[ConnectionUrl]" + con.ConnectionUrl);
			System.out.print("		[UserName]" + con.UserName);
			System.out.print("		[Password]" + con.Password);
			System.out.print("		[UseCount]" + con.UseCount);
		}
	}

	/*
	 * 从池中返回一个连接信息
	 */
	public static String CloseConnectUrl(Connection connect) {
		try{
			//从池中找到连接
			for (int i=0; i < list.size(); i++){
				ConnectInfo con = list.get(i);
				if (con.conn.equals(connect)){
					return con.ConnectionUrl;
				}
			}
			return "";
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}
}
