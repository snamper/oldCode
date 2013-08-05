package com.dhtx.sign.util.global;

import java.sql.ResultSet;

/*
 * 数据库管理类
 */
public class DataBase {
	DataConnect m_dc = null;

	/*
	 * 批量建表
	 */
	public int CreateTableAll(){
		try{
			String sql = "SELECT fTable FROM TableField GROUP BY fTable";
			ResultSet rs = m_dc.query(sql);
			while (rs != null && rs.next()){
				CreateTable(fc.getrv(rs, "fTable", ""));
			}
			m_dc.CloseResultSet(rs);
		}catch(Exception e){

		}
		return 0;
	}

	/*
	 * 建表
	 */
	public int CreateTable(String sTable){
		try{
			String sql = "SELECT * FROM TableField WHERE fTable = '" + sTable + "' ORDER BY fIndex ";
			ResultSet rs = m_dc.query(sql);
			String s = "CREATE TABLE [dbo].[" + sTable + "] ( ";
			while (rs != null && rs.next()){
				String ss = "";
				if (fc.getrv(rs, "fField", "").toLowerCase().equals("fid")) ss = " COLLATE Chinese_PRC_CI_AS NOT NULL";
				s = s + " [" + fc.getrv(rs, "fField", "") + "] " + fc.getrv(rs, "fType", "") + " " + ss + " ,";
			}
			s = s.substring(0, s.length() - 1) + ") ON [PRIMARY]";
			m_dc.CloseResultSet(rs);
			m_dc.execute(s, false);
			//
			sql = "ALTER TABLE [dbo].[" + sTable + "] ADD " +
			  "CONSTRAINT [PK_" + sTable + "] PRIMARY KEY  CLUSTERED" +
			  "([fid])  ON [PRIMARY]";
			m_dc.execute(sql, false);
			//
			return 0;
		}catch(Exception e){
			return -1;
		}
	}

	/*
	 * 加新�?
	 */
	public int AddTable(String fName, String fAlias, String fMemo){
		String sql = "INSERT INTO TableInfo (fID, fAlias, fMemo) VALUES('" + fName + "', '" + fAlias + "', '" + fMemo + "')";
		return m_dc.execute(sql);
	}

	/*
	 * 加新字段
	 */
	public int AddField(String fTable, String fField, String fAlias, String fType, String fMemo){
		String sql = "INSERT INTO TableField (fID, fTable, fField, fAlias, fType, fMemo) " +
				" VALUES('" + fTable + "_" + fField + "', '" + fField + "', '" + fAlias + "', '" + fType + "', '" + fMemo + "')";
		return m_dc.execute(sql);
	}

	/*
	 * 初始化系统表
	 */
	public void InitSysTable(){
		String sql = "CREATE TABLE [dbo].[TableInfo] ( " +
				"[fid] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ," +
				"[fAlias] [varchar] (32) COLLATE Chinese_PRC_CI_AS NULL ," +
				"[fMome] [varchar] (32) COLLATE Chinese_PRC_CI_AS NULL" +
				") ON [PRIMARY]";
		m_dc.execute(sql);
		sql = "CREATE TABLE [dbo].[TableField] (" +
				"[fID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL ," +
				"[fTable] [varchar] (32) COLLATE Chinese_PRC_CI_AS NULL ," +
				"[fAlias] [varchar] (32) COLLATE Chinese_PRC_CI_AS NULL ," +
				"[fField] [varchar] (32) COLLATE Chinese_PRC_CI_AS NULL ," +
				"[fType] [varchar] (32) COLLATE Chinese_PRC_CI_AS NULL ," +
				"[fIsIndex] [varchar] (32) COLLATE Chinese_PRC_CI_AS NULL ," +
				"[fIndex] [int] ," +
				"[fMemo] [varchar] (64) COLLATE Chinese_PRC_CI_AS NULL" +
				") ON [PRIMARY]";
		m_dc.execute(sql);
		sql = "ALTER TABLE [dbo].[TableInfo] ADD " +
			  "CONSTRAINT [PK_TableInfo] PRIMARY KEY  CLUSTERED" +
			  "([fid])  ON [PRIMARY]";
		m_dc.execute(sql);
		sql = "ALTER TABLE [dbo].[TableField] ADD " +
				"CONSTRAINT [PK_TableField] PRIMARY KEY  CLUSTERED" +
				"([fID])  ON [PRIMARY] ";
		m_dc.execute(sql);
	}

	public void SetDataConnect(DataConnect dc){
		m_dc = dc;
	}
}
