package commfill;

import javax.servlet.http.HttpServletRequest;

import common.DataAccess;
import common.DataConnect;
import common.fc;

/*
 * 页面控制基类,xml格式记录
 */
public class Common {

//	static DataConnect dc = new DataConnect("com.microsoft.jdbc.sqlserver.SQLServerDriver",
//			"jdbc:sqlserver://118.144.91.33:6433;databaseName=fillcard", 
//			"jk1166", "Ths0qeZSyQhpWooB0UAmR9uMB76t6D5S", false);
	static DataConnect dc = new DataConnect("orderfill", false);
//	static DataConnect dc = new DataConnect("sysdb", false);
	
	public boolean isUpdate = false;	//是否有更新操作,会影响页面的刷新方式
	String m_QueryData = "";			//查询到的数据

	public void destroy() {
		if (dc != null) {
			dc.CloseConnect();
			dc = null;
		}
	}
	
	/*
	 * 返回结果
	 */
	public String getResult(String sName){
		return fc.getString(m_QueryData, "<result>", "</result>");
	}
	
	/*
	 * 返回描述
	 */
	public String getMsg(String sName){
		return fc.getString(m_QueryData, "<msg>", "</msg>");
	}
	
	/*
	 * 返回行数据
	 */
	public String getData(int nRow, String sName){
		String sRow = fc.getString(m_QueryData, "<row_" + nRow + ">", "</row_" + nRow + ">");
		return fc.getString(sRow, "<" + sName + ">", "</" + sName + ">");
	}
	
	/*
	 * 返回行数据
	 */
	public String getData(String sData, int nRow, String sName){
		String sRow = fc.getString(sData, "<row_" + nRow + ">", "</row_" + nRow + ">");
		return fc.getString(sRow, "<" + sName + ">", "</" + sName + ">");
	}
	
	/*
	 * 返回统计数据
	 */
	public String getStat(String sName){
		String sRow = fc.getString(m_QueryData, "<stat>", "</stat>");
		return fc.getString(sRow, "<" + sName + ">", "</" + sName + ">");
	}
	
	/*
	 * 返回统计数据
	 */
	public String getStat(String sData, String sName){
		String sRow = fc.getString(sData, "<stat>", "</stat>");
		return fc.getString(sRow, "<" + sName + ">", "</" + sName + ">");
	}
	
	/*
	 * 查询数据,返回xml格式记录
	 */
	public int m_RecCount = 0;
	public String queryData(int nPage, String sql){
		return queryData(nPage, sql, 20);
	}
	public String queryData(int nPage, String sql, int nRowCount){
		try{
			//初始化
			DataAccess da = new DataAccess(dc);
			da.nPageRowCount = nRowCount;
			//查询
			int n = da.query(sql);
			m_RecCount = n;
			if (n == -1){
				da.Close();
				m_QueryData = fc.getResultStr("querydata","false", "查询失败");
				return m_QueryData;
			}
			//读取
			String sData = "";
			int nRow = 1;
			for (; (nRow <= n)&&(nRow <= da.nPageRowCount); nRow++){
				if (da.toRow(nPage, nRow))
					sData = sData + da.getRowXml(da.rs, nRow);
				else
					break; 
			}
			nPageNo = da.nPageNo; 
			nPageCount = da.nPageCount;
			nRow = nRow -1;
			String sStat = "<RowCount>" + nRow + "</RowCount>"
				+ "<RecordCount>" + n + "</RecordCount>"; 
			//关闭
			da.Close();
			//返回
			m_QueryData = fc.getResultStr("querydata", sData, sStat, "true", "查询成功");
			return m_QueryData;
		}catch(Exception e){
			m_QueryData = fc.getResultStr("querydata","false", "查询失败");
			return m_QueryData;
		}
	}
	
	
	//-------------------------------------------------------------------------//

	/*
	 * 返回单选框name与id, 当nRow=-1时,表示全选复选框
	 */
	public String getCheckboxName(int nRow, String sFormID){ 
		String s = "";
		if (nRow == -1){
			s = " name='CheckBox_All' id='CheckBox_All' onclick=\"" +
					"javaScript:" +
					"var form = document.getElementById('" + sFormID + "');" +
					"for(var i=0;i<form.elements.length;i++)" +
					"if(form.elements[i].id.charCodeAt() == (\'CheckBox_\' + (i + 1)).charCodeAt())" +
                   	"form.elements[i].checked = this.checked;" +
					"\"" ;
		}else{
			s = " name='CheckBox_" + nRow + "' id='CheckBox_" + nRow + "' ";
		}
		return s;
	}

	/*
	 * 返回当前选中的id值(订单号),用逗号分隔
	 */
	public String getCheckboxSelect(HttpServletRequest request, int nMaxRow){
		String s = "";
		int count = 0;
		for (int nRow = 1; nRow <= nMaxRow; nRow++){
			String ss = fc.getpv(request, "CheckBox_" + nRow);
			if (!ss.equals("")){
				count ++;
				s = s + "<row_" + count + "><value>" + ss + "</value></row_" + count + ">";
			}
		}
		return fc.getResultStr("selectorder", s, "<RowCount>" + count + "</RowCount>", "true", "");
	}

	
	//-------------------------------------------------------------------------//
	
	/* 
	 * 页控制,返回当前页号
	 */
	public String m_LastPageButton = "";
	public int getPageNo(HttpServletRequest request){
		//
		int nPage = 0;
		//是不是刷新的页号
 		String s = fc.getpv(request, "page");
		if (!s.equals("")){ 
			isUpdate = false;
			try{
				nPage = Integer.valueOf(s).intValue();
			}catch(Exception e){
				nPage = 0;
			}
		}
		
		//
		if (!fc.getpv(request, "e_InputPage").equals("")){
			try{
				nPage = Integer.valueOf("0" + fc.getpv(request, "e_InputPage")).intValue();
			}catch(Exception e){
				nPage = 0;
			}
		}
		//
		if (nPage == 0) nPage = 1;
		if (!fc.getpv(request, "b_FirstPage").equals("")){
			m_LastPageButton = "b_FirstPage";
			nPage = 1;
			isUpdate = true;
		}
		if (nPage > 1)
		if (!fc.getpv(request, "b_PrevPage").equals("")){
			m_LastPageButton = "b_PrevPage";
			nPage = nPage - 1;
			isUpdate = true;
		}
		if (!fc.getpv(request, "b_NextPage").equals("")){
			m_LastPageButton = "b_NextPage";
			nPage = nPage + 1;
			isUpdate = true;
		}
		if (!fc.getpv(request, "b_LastPage").equals("")){
			m_LastPageButton = "b_LastPage";
			nPage = -1;
			isUpdate = true;
		}
		if (!fc.getpv(request, "b_GotoPage").equals("")){
			m_LastPageButton = "b_GotoPage";
			nPage = nPage;
			isUpdate = true;
		}
		return nPage;
	}

	
	/*
	 * 返回按钮名称及可用属性,first,prev,next,last,goto,inpage
	 */
	public int nPageNo = 0;
	public int nPageCount = 0;
	public String getButtonName(String sType){
		String s = "", e = "";
		//首页
		if ((sType.equals("first"))||(sType.equals("f"))){
			s = " name='b_FirstPage' ";
			if (nPageNo <= 1)	e = " disabled ";
		}
		//上页
		if ((sType.equals("prev"))||(sType.equals("p"))){
			s = " name='b_PrevPage' ";
			if (nPageNo <= 1) e = " disabled ";
		}
		//下页
		if ((sType.equals("next"))||(sType.equals("n"))){
			s = " name='b_NextPage' ";
			if (nPageNo >= nPageCount) e = " disabled ";
		}
		//尾页
		if ((sType.equals("last"))||(sType.equals("l"))){
			s = " name='b_LastPage' ";
			if (nPageNo >= nPageCount) e = " disabled ";
		}
		//指定页
		if ((sType.equals("goto"))||(sType.equals("g"))){
			s = " name='b_GotoPage' ";
			if (nPageCount <= 1) e = " disabled ";
		}
		//页号输入框
		if ((sType.equals("inpage"))||(sType.equals("i"))){
			s = " name='e_InputPage' ";
			if (nPageCount <= 1) e = " disabled ";
		}
		return s + e;
	}
	

}
