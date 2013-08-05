package com.agents.util;

import java.sql.Connection;

public class ConnectInfo {
	Connection conn = null;
	DataConnect dc = null;
	String JndiName = "";
	String DriverClass = "";
	String ConnectionUrl = "";
	String UserName = "";
	String Password = "";
	int UseCount = 0;
}
