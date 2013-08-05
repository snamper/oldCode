package common;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DataAccess {

	static DataConnect conn = null;
	ResultSet rs = null;
	int nPageNo = 0;
	int nPageRowCount = 0;
	public String sql = "";
	Error oe = new Error();

	/*
	 * 这里主要处理分页读取
	 * 
	 */
	
	/*
	 * 处理基本的数据存取处理 １．查询，分页读取 ２．插入 ３．删除 ４．修改 5.
	 */

	public DataAccess(String name, boolean ispool) {
		if (conn == null) conn = new DataConnect(name, ispool);
		nPageNo = 1;
		nPageRowCount = 20;
	}
	


	public void Close(){
		try{
			if (rs != null){
				rs.getStatement().close();
				rs.close();
			}
		}catch(Exception e){
			
		}
	}
	
	/*旧的
	 * 查询数据，返回记录个数, 同时保留查询结果在内部ResultSet中,之后可用
	 */
	public int query(String sql) {
		try {
			//关闭上次结果集
			if (rs != null){
				if (!rs.isClosed()){
					conn.CloseResultSet(rs);
					rs = null;
				}
			}
			//新的结果集
			rs = conn.query(sql);
			rs.last();
			return rs.getRow();
		} catch (Exception e) {
			oe.outs("[DataAccess.query]" + sql, "error");
			return -1; 
		}
	}

	/*
	 * 定位到某页的某行
	 */
	public boolean toRow(int PageNo, int Row) {
		try {
			nPageNo = PageNo;
			return rs.absolute(((PageNo - 1) * nPageRowCount) + Row);
		} catch (Exception e) {
			oe.outs("[DataAccess.toRow]" + e.toString(), "error");
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
			oe.outs("[DataAccess.getValue]" + e.toString(), "error");
			return "";
		}
	}
	
	
	/*-------以下的不会用到内部的ResultSet------------------------------------------------------------*/
	/*
	 * 查询数据，返回记录记录集
	 */
	public ResultSet queryRs(String sql) {
		try {
			return conn.query(sql);
		} catch (Exception e) {
			oe.outs("[DataAccess.query]" + sql, "error");
			return null;
		}
	}

	
	/*
	 * 查询个数  OK,默认字段为id
	 */
	public int queryCount(String sTable, String sWhereStr) {
		return queryCount("", sTable, sWhereStr);
	}

	/*
	 * 查询个数  OK, 可指定字段,为空时表示用id
	 */
	public int queryCount(String sField, String sTable, String sWhereStr) {
		try {
			if (sField.equals("")) sField = "id";
			if (sWhereStr.equals("")) sWhereStr = " 1=1 ";
			String sql = "select count(" + sField + ") as maxcount from " + sTable + " where " + sWhereStr;
			ResultSet rs = conn.query(sql);
			int n = 0;
			if (rs.next()){
				n = rs.getInt("maxcount");
			}else
				n = 0;
			rs.getStatement().close();
			rs.close();
			rs = null;
			return n;
		}catch(Exception e){
			oe.outs("[DataAccess.query]" + sql, "error");
			e.printStackTrace();
			return 0;
		}
		
	}
	

	
	/*
	 * 新的
	 * 查询指定页数据，返回数据集 :单表查询,分页取数据时,以sTable的id为依据,暂时为升序
	 */
	public ResultSet queryRS(int nPage, String sField, String sTable, String sWhereStr) {
		return queryRS(nPage, sTable, sField, sTable, sWhereStr);
	}

	
	/*
	 * 分页查询,主表无过滤条件,可关系其它表信息,分页取数据时,以sMainTable的id为依据,暂时为升序,t
	 * 处理过滤条件时,还有些问题
	 */
	public ResultSet queryRS(int nPage, String sMainTable, String sField, String sTable, String sWhereStr) {
		return queryRS(nPage, sMainTable, "", sField, sTable, sWhereStr);
	}
	
	/*
	 * 分页查询,可定义主表过滤条件,可关系其它表信息,分页取数据时,以sMainTable的id为依据,暂时为升序,t
	 * 处理过滤条件时,还有些问题
	 */
	public ResultSet queryRS(int nPage, String sMainTable, String sMainWhile, String sField, String sTable, String sWhereStr) {

			try {
				if (sMainWhile.equals("")) sMainWhile = " 1=1 ";
				if (sWhereStr.equals("")) sWhereStr = " 1=1 ";
				if (nPage == 0) nPage = 1;
			if (sField.equals("")) sField = " * ";
			//找到第几页开始ID 
			String sql = "SELECT MAX(id) AS maxid FROM (SELECT TOP " 
						+ ((nPage - 1) * nPageRowCount + 1) 
						+ " id FROM " + sMainTable + " WHERE (1 = 1) and (" + sMainWhile + ")) DERIVEDTBL";
			ResultSet rs = conn.query(sql);
			if (rs.next()){
				String maxid = funcejb.gets(rs, "maxid", "0");
				//查询20条
				rs.close();
				sql = "select top 15 " + sField + " from " + sTable + " where (" + sWhereStr + ") and (" + sMainTable + ".id >= '" + maxid + "')";
				rs = conn.query(sql);
				return rs;
			}else{
				rs.close();
				sql = "select * from " + sTable + " where (0=1)";
				return rs = conn.query(sql);
			}
		} catch (Exception e) {
			oe.outs("[DataAccess.query]" + sql, "error");
			return null;
		}
	}

	/*
	 * 分页查询,可定义主表过滤条件,可关系其它表信息,分页取数据时,以sMainTable的id为依据,暂时为升序,t
	 * 处理过滤条件时,还有些问题
	 */
	public ResultSet queryRS2(int nPage, String sMainId, String sField, String sTable, String sWhereStr) {

			try {
				if (sWhereStr.equals("")) sWhereStr = " 1=1 ";
			if (sField.equals("")) sField = " * ";
			//找到第几页开始ID 
			String sql = "SELECT MAX(id) AS maxid FROM (SELECT TOP " 
						+ ((nPage - 1) * nPageRowCount + 1) 
						+ " " + sMainId + " FROM " + sTable + " WHERE (1 = 1) and (" + sWhereStr + ")) DERIVEDTBL";
			ResultSet rs = conn.query(sql);
			if (rs.next() && (rs.getString("maxid") != null)){
				String maxid = rs.getString("maxid");
				//查询20条
				rs.getStatement().close();
				rs.close();
				sql = "select top 15 " + sField + " from " + sTable + " where (" + sWhereStr + ") and (" + sMainId + " >= '" + maxid + "')";
				rs = conn.query(sql);
				return rs;
			}else{
				rs.getStatement().close();
				rs.close();
				sql = "select * from " + sTable + " where (0=1)";
				return rs = conn.query(sql);
			}
		} catch (Exception e) {
			oe.outs("[DataAccess.query]" + sql, "error");
			return null;
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
			oe.outs("[DataAccess.getValue]" + e.toString(), "error");
			return "";
		}
	}

	/*
	 * 设置字段值, Value的格式为:  字段名1='值1',字段名2='值2'
	 */
	public int setValue(String id, int verify, String Table, String Value) {
		try {
			String sql = "update " + Table + " set " + Value + " where id='"
					+ id + "'";
			return conn.execute(sql);
		} catch (Exception e) {
			oe.outs("[DataAccess.setValue]" + sql, "error");
			return -1;
		}

	}

	/*
	 * 插入记录,Name格式为: 字段1,字段2   Value的格式为: '值1','值2'
	 */
	public int insert(String Table, String Name, String Value) {
//		String sql = "";
		try {
			sql = "insert into " + Table + " (" + Name + ") values("
					+ Value + ")";
			return conn.execute(sql);
		} catch (Exception e) {
			oe.outs("[DataAccess.inset]" +  sql , "error");
			return -1;
		}
	}

	/*
	 * 删除记录, Condition的格式就是删除条件表达式
	 */
	public int delete(String Table, String Condition) {
		try {
			String sql = "delete from " + Table + " where " + Condition;
			return conn.execute(sql);
		} catch (Exception e) {
			oe.outs("[DataAccess.delete]" + sql, "error");
			return -1;
		}
	}

	/*
	 * 执行sql语句 
	 */	
	public int execute(String sql){
		return conn.execute(sql);	
	}
	
	
	/*---------------------------------------*/
	/*
	 * 检测数据是否合法正确
	 * if (sError.equals("")) sError = card.validityCheck("client_ftelphone", sTelPhone);
	 */
	public String validityCheck(String sName, String sValue){
		String sError = "";
		try{
			//DataConnect dc = new DataConnect();
			ResultSet rs = conn.query("select * from validityCheck where fTableField='" + sName + "'");
			if (!rs.next()) {
				conn.CloseResultSet(rs);
				return "系统错误,没有找到\"" + sName + "\"的合法性规则!";
			}
			String sAlias = rs.getString("fAlias");
			//检查最小长度
			int nLength = rs.getInt("fMinLingth");
			if (nLength > 0)
			if (sValue.length() < nLength){
				conn.CloseResultSet(rs);
				return "\"" + sAlias + "\" 字符长度不能小于 " + nLength + "    ";
			}
			//检查最大长度
			nLength = rs.getInt("fMaxLingth");
			if (nLength > 0)
			if (sValue.length() > nLength){
				conn.CloseResultSet(rs);
				return "\"" + sAlias + "\" 字符长度不能大于 " + nLength + "   ";
			}
			//检查可用字符
			String s = "";
			String sData = rs.getString("fUsable");
			if ((sData != null) && (!sData.equals("")))
			for (int i = 0; i < sValue.length(); i ++){
				s = sValue.substring(i, i + 1);
				if (sData.indexOf(s) == -1) {
					if (s.equals("'")) s = "单引号";
					if (s.equals("\"")) s = "双引号";
					if (s.equals(" ")) s = "空格";
					conn.CloseResultSet(rs);
					return "\"" + sAlias + "\" 内容不合法, 中不应含有 \"" + s + "\"    ";
				}
			}
			//检查必须包含的字符
			sData = rs.getString("fComprise");
			if ((sData != null) && (!sData.equals("")))
			for (int i = 0; i < sData.length(); i ++){
				s = sData.substring(i, i + 1);
				if (sValue.indexOf(s) == -1) {
					conn.CloseResultSet(rs);
					return "\"" + sAlias + "\" 内容中应含有字符: \"" + sData + "\"    ";
				}
			}
			//检查不能包含的字符
			sData = rs.getString("fNoComprise");
			if ((sData != null) && (!sData.equals("")))
			for (int i = 0; i < sData.length(); i ++){
				s = sData.substring(i, i + 1);
				if (sValue.indexOf(s) > -1) {
					if (s.equals("'")) s = "单引号";
					if (s.equals("\"")) s = "双引号";
					if (s.equals(" ")) s = "空格";
					conn.CloseResultSet(rs);
					return "\"" + sAlias + "\" 内容不合法, 不应含有 \"" + s + "\"    ";
				}
			}
			//检查是否可以为空
			int nIsNull = rs.getInt("fIsNull");
			if ((nIsNull != 1) && ((sValue == null) || (sValue.equals("")))) {
				conn.CloseResultSet(rs);
				return "\"" + sAlias + "\" 内容不能为空 ";
			}
			//
			conn.CloseResultSet(rs);
			rs = null;
		}catch(Exception e){
			e.printStackTrace();
			rs = null;
		}
		return sError;	
	}
}
