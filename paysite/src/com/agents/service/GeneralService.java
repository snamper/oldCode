package com.agents.service;

import java.util.List;

import org.jdom.Element;

import com.agents.dao.GeneralDao;
import com.agents.dao.StaffDao;
import com.agents.util.Config;
import com.agents.util.CreatExcel;
import com.agents.util.fc;


public class GeneralService {
	public int noCount = 0;
	public List<String[]> findStaffDataList(int page,String agentId){
		GeneralDao dao = new GeneralDao();
		List<String[]> list = dao.findStaffDataList(page,agentId) ;  
		noCount = dao.noCount ;
		return list;    
	}
	public List<String[]> findDataList(int page,String whereStr,String sql){
		GeneralDao dao = new GeneralDao();
		List<String[]> list = dao.findDataList(page,whereStr,sql) ;  
		noCount = dao.noCount ;
		return list;
	}
	public boolean delete(String whereStr,String sql){
		GeneralDao dao = new GeneralDao() ;
		boolean del = dao.delete(whereStr,sql) ;  
		return del;
	}
	public List<String[]> findSelectLists(String sql){
		GeneralDao dao = new GeneralDao() ;
		List<String[]> list = dao.findSelectLists(sql) ;  
		return list;
	}
	public String[] findModifyList(String sql){
		GeneralDao dao = new GeneralDao() ;
		String[] list = dao.findModifyList(sql) ;  
		return list;
	}
	public boolean update(String sql){
		GeneralDao dao = new GeneralDao() ;
		boolean del = dao.update(sql) ;  
		return del;
	}   
	//替换List字段中的内容: <col_9>4=充值成功,5=充值失败,?=充值中</col_9>
	private List getfieldChangeData(List<String[]> list, String sChangeData) {
		for (int i=0; i<list.size(); i++){
			String[] aa = (String[])list.get(i);	//取得一行
			for (int j=0; j<aa.length; j++){
				String s = fc.getString(sChangeData, "<" + j + ">", "</" + j + ">");
				String v = fc.getString(s + ",", aa[j] + "=", ",");   			//替换
				if (v.equals("")) v = fc.getString(s + ",", "?=", ",");  		//默认值
				if (v.equals("") && (s.indexOf("?=")==-1)) v = aa[j];
				aa[j] = v;
			}
			list.set(i, aa);
		}
		return list;
	}	
	public String createExcel(String filepath,String fieldName,String sql,String xmlfile,String agentId){
		 //将xml中字段转换列表内容并格式化: <fstate>4=充值成功,5=充值失败,?=充值中</fstate>
		
		Element element=Config.getConfigElement(agentId,xmlfile);
        String sChangeData = "";
        String fieldChangeList=element.getChild("exportData_fieldChange").getChildTextTrim("fieldChangeList");
        String sFieldName = fc.getString("!@#$%"+fieldChangeList+",", "!@#$%", ",");
        while (!sFieldName.equals("")){  
            String ChangeData =element.getChild("exportData_fieldChange").getChildTextTrim("col_" + sFieldName);
      	  sChangeData = sChangeData + "<" + sFieldName + ">" + ChangeData + "</" + sFieldName + ">";
      	  fieldChangeList = fc.replace(fieldChangeList + ",", sFieldName + ",", "");
      	  sFieldName = fc.getString("!@#$%"+fieldChangeList+",", "!@#$%", ",");
        }
        
		GeneralDao dao = new GeneralDao() ;
		//int page=1;
		List<String[]> list = dao.getExportDataList(sql) ; 
		list=getfieldChangeData(list,sChangeData);
		
		//int count = dao.noCount ;//总记录数
		CreatExcel ce=new CreatExcel();
		String create=ce.createExcel(filepath,fieldName, list);
		return create;  
	}
	public String[] findProductInfo(String sql){
		GeneralDao dao = new GeneralDao() ;
		String aa[]=dao.findProductInfo(sql);
		return aa;  
	}
	//获得关联字段
	public String getfAreaTypeByProductId(String id){
		GeneralDao dao = new GeneralDao() ;
		String aa=dao.getfAreaTypeByProductId(id);
		return aa;  
	}
	//获得关联信息
	public String[] getRelationList(String id){
		GeneralDao dao = new GeneralDao() ;
		String aa[]=dao.getRelationList(id);
		return aa;  
	}//获得二级菜单关联信息
	public String getRelationList(String relationField,String condition){
		GeneralDao dao = new GeneralDao();
		String str="";
		String aa[]=dao.getRelationList(relationField, condition);
		str=aa[0]+"@-@"+aa[1]+"@-@"+aa[2];  
		return str;    
	}
	//
	public String[] getClientKeyById(String id){
		GeneralDao dao = new GeneralDao() ;
		String aa[]=dao.getClientKeyById(id);
		return aa;  
	}
}
