package com.dhtx.util;

import java.sql.Connection;
import java.util.Date;

public class ConnectInfo {
	Connection conn = null;
	DataConnect dc = null;
	String JndiName = "";
	String DriverClass = "";
	String ConnectionUrl = "";
	String UserName = "";
	String Password = "";
	int UseCount = 0;
	Date LastTime = new Date();   //最后访问时间 
}
