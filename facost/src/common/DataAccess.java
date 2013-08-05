package common;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

public class DataAccess {

	DataConnect conn = null;
	public ResultSet rs = null, rs2 = null;
	public int nPageNo = 0; 
	public int nPageRowCount = 0;
	public int nPageCount = 0;
	int nRowCount = 0;
	Object array = null;
	String sOldSelect = "";
	String sCacheTableName = "";
	String sKeyField = "";
	boolean bUseCheckQuery = false;
	


	
	/*
	 * 处理基本的数据存取处理 １．查询，分页读取 
	 */

	
	public DataAccess(DataConnect dc) {
		conn = dc;
		nPageNo = 1;
		nPageRowCount = 15;
	}
	
	public void destroy() {
		if (conn != null){
			conn.CloseConnect();
			conn = null;
			System.out.print("[DataAccess]已关闭数据库连接");
		}
	}  	

	public void Close(){
		try{
			conn.CloseResultSet(rs);
		}catch(Exception e){
			
		}
	}
	
	/*
	 * 查询数据，返回记录个数, 同时保留查询结果在内部ResultSet中,之后可用
	 */
	public int query(String sql) {
		return query(sql, false, "");
	}
	//
	public int query(String sql, boolean useCheckQuery, String keyField) {
		try {
			nPageNo = 0;
			bUseCheckQuery = useCheckQuery;
			if (useCheckQuery){				//在这里只用id查询
				sKeyField = keyField;
				sOldSelect = fc.getString(sql.toLowerCase(), "select", "from");
				sCacheTableName = fc.getString(sql.toLowerCase() + "where", "from", "where");
				String sNewSql = "SELECT " + keyField + " FROM " + fc.getString(sql.toLowerCase() + "_|_|_!@#$%^&*()_|_|_", "from", "_|_|_!@#$%^&*()_|_|_");
				rs = conn.query(sNewSql);
			}
			else{
				sOldSelect = "";
				rs = conn.query(sql);		//原样查询
			}
			rs.last();
			nRowCount = rs.getRow();
			//
			return nRowCount;
		} catch (Exception e) {
			Error.outs("[DataAccess.query]" + e.toString());
			return -1;
		}
	}


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
					String sNewWhere = " WHERE " + sKeyField + " IN (" + sIdList + ")";
					String sNewSql = "SELECT " + sOldSelect + " FROM " + sCacheTableName + " " + sNewWhere;
					rs2 = conn.query(sNewSql);
				}
				return rs2.absolute(Row);
			}else{
				//
				nPageNo = PageNo;
				return rs.absolute(((PageNo - 1) * nPageRowCount) + Row);	
			}
		} catch (Exception e) {
			Error.outs("[DataAccess.toRow]" + e.toString());
			return false;
		}
	}

	/*
	 * 设置每页最大行数
	 */
	public void setPage(int MaxRow) { 
		nPageRowCount = MaxRow;
	} 

	/*
	 * 返回字段值,执行Query后才可使用
	 */
	public String getValue(String Name) {
		return getValue(Name, "", "");
	}

	/*
	 * 返回字段值
	 */
	public String getValue(ResultSet rs, String Name) {
		return getValue(rs, Name, "", "");
	}

	/*
	 * 返回字段值
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
	 * 返回字段值
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
	 * 返回字段值
	 */
	public String getValue(String Name, String Type, String DefValue) {
		try {
			Object o = null; 
			String s = null;
			if (Type.equals("date")) o = rs.getDate(Name);
			if (Type.equals("time")) o = rs.getTime(Name);
			if (Type.equals("dec"))	 o = rs.getBigDecimal(Name, 2);
			if (Type.equals(""))  o = rs.getString(Name).trim();
			if (o == null)
				return DefValue; 
			else			
				return o.toString().trim();
		} catch (Exception e) {
			Error.outs("[DataAccess.getValue]" + e.toString());
			return "";
		}
	}
	
	/*
	 * 返回字段值,可指定字段类型(用来返回指定格式),可设置当字段为空时的默认显示值
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
			Error.outs("[DataAccess.getValue]" + e.toString());
			return "";
		}
	}

	/*
	 * 返回一行的xml格式的数据 
	 */
	public static String getRowXml(ResultSet rs, int nRow){
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
			return fc.getResultStr("getRowXml", "false", "取行数据时发生内部错误:" + e.toString());
		}
	}
}
