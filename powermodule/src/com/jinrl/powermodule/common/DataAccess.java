package com.jinrl.powermodule.common;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class DataAccess {

	DataConnect conn = null;
	public ResultSet rs = null, rs2 = null;
	public int nPageNo = 0;
	public int nPageRowCount = 0;
	public int nPageCount = 0;
	int nRowCount = 0;
	//
	Object array = null;
	String sOldSelect = "";
	String sOldOrderBy = "";
	String sCacheTableName = "";
	String sKeyField = "";
	public boolean bUseCheckQuery = false;
	String sMainTableName="";
	String sOldWhere = "";


	/*
	 * 处理基本的数据存取处�?１．查询，分页读�?
	 */


	public DataAccess(DataConnect dc) {
		conn = dc;
		nPageNo = 1;
		nPageRowCount = 15;
	}

	public DataAccess(DataConnect dc ,int num) {
		conn = dc;
		nPageNo = 1;
		nPageRowCount = num;
	}

	public void destroy() {
		if (conn != null){
			conn.CloseConnect();
			conn = null;
			fc.message("[DataAccess]已关闭数据库连接");
		}
	}

	public void Close(){
		try{
			conn.CloseResultSet(rs);
		}catch(Exception e){

		}
	}

//	/*
//	 * 查询数据，返回记录个�? 同时保留查询结果在内部ResultSet�?之后可用
//	 */
//	public int query(String sql) {
//		try {
//			rs = conn.query(sql);
//			rs.last();
//			nRowCount = rs.getRow();
//			return nRowCount;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return -1;
//		}
//	}


	/*
	 * 查询数据，返回记录个数, 同时保留查询结果在内部ResultSet中,之后可用
	 */
	public int query(String sql) {
		return query(sql, false, "");
	}

	//兼容不使用快速模式的版本
	public int query(String sql, boolean useCheckQuery, String keyField) {
		return query(sql, useCheckQuery, keyField, false);
	}

	//查询言语, 是否使用二次查询, 主键字段, 是否使有快速查询模式(只取前20页)
	public int query(String sql, boolean useCheckQuery, String keyField, boolean IsFast) {
		try {
			String sFastTop = "";
			nPageNo = 0;
			bUseCheckQuery = useCheckQuery;
			if (useCheckQuery){				//在这里只用id查询
				sKeyField = keyField;
				sOldSelect = fc.getString(sql.toLowerCase(), "select", "from");
				sOldOrderBy = fc.getString(sql.toLowerCase() + ")(*&^%$#@!", "order by", ")(*&^%$#@!");
				sOldWhere = fc.getString(sql.toLowerCase() + " ordery by", "where", "order by");
				sCacheTableName = fc.getString(sql.toLowerCase() + "", "from", "with");	//为了兼容锁标识
				if (sCacheTableName.equals(""))
					sCacheTableName = fc.getString(sql.toLowerCase() + "where", "from", "where");
				sMainTableName = fc.getString(" " + sCacheTableName + ",", " ", ",").trim();

				if (IsFast){
					sFastTop = " TOP " + (nPageRowCount * 20);
				}
				String sNewSql = "SELECT " + sFastTop + " " + sMainTableName + "." + keyField + " FROM " + fc.getString(sql.toLowerCase() + "_|_|_!@#$%^&*()_|_|_", "from", "_|_|_!@#$%^&*()_|_|_");
				fc.message("query:" + sNewSql);
				conn.CloseResultSet(rs);
				rs = conn.query(sNewSql);
			}
			else{
				sOldSelect = "";
				if (IsFast){
					sFastTop = "select TOP " + (nPageRowCount * 20);
					sql = sFastTop + sql.substring(6);
				}
				conn.CloseResultSet(rs);
				rs = conn.query(sql);		//原样查询
			}
			rs.last();
			nRowCount = rs.getRow();
			//
			return nRowCount;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}





//	/*
//	 * 定位到某页的某行
//	 */
//	public boolean toRow(int PageNo, int Row) {
//		try {
//			//对页号进行容�?
//			if ((nRowCount / nPageRowCount < PageNo)||(PageNo == -1)){
//				PageNo = nRowCount / nPageRowCount;
//				if (nRowCount % nPageRowCount > 0) PageNo = PageNo + 1;
//			}
//			//�?��页号
//			nPageCount = nRowCount / nPageRowCount;
//			if (nRowCount % nPageRowCount > 0) nPageCount = nPageCount + 1;
//			//
//			nPageNo = PageNo;
//			return  rs.absolute(((PageNo - 1) * nPageRowCount) + Row);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}


	/*
	 * 定位到某页的某行
	 */
	public boolean toRow(int PageNo, int Row) {
		try {
			//对页号进行容错
			if ((nRowCount / nPageRowCount < PageNo)||(PageNo == -1)){
				PageNo = nRowCount / nPageRowCount;
				if (nRowCount % nPageRowCount > 0) PageNo = PageNo + 1;
			}
			//最大页号
			nPageCount = nRowCount / nPageRowCount;
			if (nRowCount % nPageRowCount > 0) nPageCount = nPageCount + 1;

			//得到当前位置的ID, 二次查询记录
			if (bUseCheckQuery) {
				if (nPageNo != PageNo){
					//如果页号是新的, 则重新读取当前页数据
					int n = 1;
					String sIdList = "";
					while (rs.absolute((PageNo - 1) * nPageRowCount + n) && (n <= nPageRowCount)){
						sIdList = sIdList + ",'" + fc.getrv(rs, sKeyField, "") + "'";
						n++;
					}
					sIdList = sIdList.substring(1);
					//组成二次查询语句
					String sNewWhere = " WHERE " +  sMainTableName + "." + sKeyField + " IN (" + sIdList + ")";
					String sNewWhere2 = "";
					String sNewOrderBy = "";
					if (!sOldOrderBy.equals("")) sNewOrderBy = " order by " + sOldOrderBy;
					if (!sOldWhere.equals("")) sNewWhere2 = " and "  + sOldWhere;
					if (!sCacheTableName.equals(",")) sNewWhere2 = "";
					String sNewSql = "SELECT " + sOldSelect + " FROM " + sCacheTableName + " " + sNewWhere + sNewWhere2 + sNewOrderBy;
					//System.out.println("sNewSql:"+sNewSql);
					conn.CloseResultSet(rs2);
					rs2 = conn.query(sNewSql);
					nPageNo = PageNo;
				}
				return rs2.absolute(Row);
			}else{
				//
				nPageNo = PageNo;
				return rs.absolute(((PageNo - 1) * nPageRowCount) + Row);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}



	/*
	 * 设置每页�?��行数
	 */
	public void setPage(int MaxRow) {
		nPageRowCount = MaxRow;
	}

	/*
	 * 返回字段�?执行Query后才可使�?
	 */
	public String getValue(String Name) {
		return getValue(Name, "", "");
	}

	/*
	 * 返回字段�?
	 */
	public String getValue(ResultSet rs, String Name) {
		return getValue(rs, Name, "", "");
	}

	/*
	 * 返回字段�?
	 */
	public byte[] getByteValue(String Name) {
		try{
			return rs.getBytes(Name);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * 返回字段�?
	 */
	public byte[] getByteValue(ResultSet rs, String Name) {
		try{
			return rs.getBytes(Name);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * 返回字段�?
	 */
	public String getValue(String Name, String Type, String DefValue) {
		try {
			ResultSet rs = this.rs;
			if (bUseCheckQuery)	rs = rs2;
			//
			Object o = null;
			String s = null;
			if (Type.equals("date")) o = rs.getDate(Name);
			if (Type.equals("time")) o = rs.getTime(Name);
			if (Type.equals("dec"))	 o = rs.getBigDecimal(Name, 2);
			if (Type.equals(""))  o = rs.getString(Name);
			if (o == null)
				return DefValue;
			else
				return o.toString().trim();
		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("超出结果集范围");
			return "";
		}
	}

	/*
	 * 返回字段�?可指定字段类�?用来返回指定格式),可设置当字段为空时的默认显示�?
	 */
	public String getValue(ResultSet rs, String Name, String Type, String DefValue) {
		try {
			Object o = null;
			String s = null;
			if (Type.equals("date")) o = rs.getDate(Name);
			if (Type.equals("time")) o = rs.getTime(Name);
			if (Type.equals("dec"))	 o = rs.getBigDecimal(Name, 2);
			if (Type.equals(""))  o = rs.getString(Name);
			if (o == null)
				return DefValue;
			else
				return o.toString().trim();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/*
	 * 返回�?��的xml格式的数�?
	 */
	public String getRowXml(int nRow){
		try{
			ResultSetMetaData rsmd = rs.getMetaData() ;
			int columnCount = rsmd.getColumnCount();
			String s = "", s2 = "";
			for (int i=1; i <= columnCount; i++){
				s2 = rs.getString(rsmd.getColumnName(i));
				if (s2 == null) s2 = "";
				s = s + "<" + rsmd.getColumnName(i) + ">"
				+ s2
				+ "</" + rsmd.getColumnName(i) + ">";
			}
			return "<row_" + nRow + ">" + s + "</row_" + nRow + ">";
		}catch(Exception e){
			e.printStackTrace();
			return fc.getResultStr("getRowXml", "false", "取行数据时发生内部错�?" + e.toString());
		}
	}

}
