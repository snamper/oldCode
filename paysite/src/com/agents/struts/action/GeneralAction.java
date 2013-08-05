package com.agents.struts.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.jdom.Element;

import com.agents.pojo.CoAgent;
import com.agents.service.GeneralService;
import com.agents.struts.actionform.GeneralActionFrom;
import com.agents.util.Config;
import com.agents.util.ConnOracle;
import com.agents.util.Tools;
import com.agents.util.fc;

public class GeneralAction extends DispatchAction {
private GeneralService generalService=new GeneralService();

public ActionForward findStaffDataList(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		//分页设置
		GeneralActionFrom Form = (GeneralActionFrom) form ;
		int page = 1;  
		try{
			if(Form.getNopage() != null){ 
				page = Form.getNopage();   
			}
			  }catch(Exception e){  
			  }  
		String clientID = (String)session.getAttribute("username");
		if(clientID == null){
			return mapping.findForward("staffList");      
		}
		String userType = (String)session.getAttribute("usertype");
		//如果是员工登录，取上级ID
		if (userType.equals("staff")) {  
			clientID=(String)session.getAttribute("higherId"); 
		}  
			List <String[]> list=generalService.findStaffDataList(page,clientID);
			int noCount = generalService.noCount;  
		int countPage = 0;
		if(noCount % 15 == 0){
			countPage = noCount / 15;
		}else{  
			countPage = noCount / 15 + 1;
		}
		if(page < 1) page = 1;
		if(page > countPage) page = countPage;
		
		request.setAttribute("noCount", noCount);
		request.setAttribute("noPage", page);  
		request.setAttribute("countPage", countPage);
		request.setAttribute("staffs", list) ;  
		//request.setAttribute("pageMap", pageMap);
		return mapping.findForward("staffList");
	}

	public ActionForward findDataList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		GeneralActionFrom Form = (GeneralActionFrom) form ;
		  //页面搜索条件  
		  HttpSession session = request.getSession();
		  String key=fc.getpv(request, "key");
		  String type=request.getParameter("type");
		  String endtime=Tools.correctDate(request.getParameter("endtime"));
		  String starttime=Tools.correctDate(request.getParameter("starttime"));  
		  CoAgent agent = (CoAgent)session.getAttribute("currentAgent");
		
		  
		  if (agent==null) {  
			  return mapping.findForward("dataList") ;      
		  }
		  //代理商id
		  String agentId="";
		  try{
			  agentId=agent.getFid();
		  }catch(Exception  e){
			  System.out.println(e);  
		  }
		  String userId=(String) session.getAttribute("username");
		  
		 //如果是员工登录，取上级ID
		  String userType = (String)session.getAttribute("usertype");
			if (userType.equals("staff")) {  
				userId=(String)session.getAttribute("higherId"); 
			}
		  //xml文件名
		  String fileName=request.getParameter("name");
		  Element element=Config.getConfigElement(agentId,fileName);  
//*****************************************************
		  //解析XML elements节点
		    List elementList=element.getChild("searchTypeTwo").getChildren();
		  
		     Iterator rootIt = elementList.iterator();
		     Map<String ,Map> elements=new HashMap<String, Map>();
		     Map<String ,String> elementRow=null;
		     int i=0;
		    while(rootIt.hasNext()){
		    	elementRow=new HashMap<String, String>();
		    	 Element e = (Element)rootIt.next();
		    	 String Type=e.getAttributeValue("type");
				 String name=e.getAttributeValue("name");//element 节点属性值   
				 String Class=e.getAttributeValue("class");
				 String tag=e.getAttributeValue("tag");
				 String tip=e.getAttributeValue("tip");
				 
				 String onKeypress="";
				 String sql="";
				 String option="";
				 elementRow.put("type", Type);
				 elementRow.put("name", name);
				 elementRow.put("class", Class);
				 elementRow.put("tag", tag);
				 elementRow.put("tip", tip);
				 
				   
				 if (Type.equals("select")) {
					 sql= e.getAttributeValue("sql");
					 if (sql.lastIndexOf("{agentId}")!=-1) {
						 sql=sql.replace("{agentId}", agentId);        
					 }
					
					 if (sql.lastIndexOf("{clientId}")!=-1) {
						 sql=sql.replace("{clientId}", userId);          
					 }
					 List<String[]> list = generalService.findSelectLists(sql); 
					 request.setAttribute(name+"list", list);    
				 } if (Type.equals("selectT")) {
					 option= e.getAttributeValue("option");
					 elementRow.put("option", option); 
				 }
				 elements.put(Integer.toString(i), elementRow);  
				 i++;
		    }
		  //*********************************
          String pageUrl=Config.getChildValue(agentId,fileName,"pageUrl")+"&name="+fileName;
          String listSql=element.getChild("listData").getChildTextTrim("sql");
          //sql 是否有order 条件
          String orderby="";
          if(element.getChild("listData").getChildTextTrim("orderby")!=null){
        	   orderby=element.getChild("listData").getChildTextTrim("orderby");
          }
         
          //list sql 过滤
          if (listSql.lastIndexOf("{agentId}")!=-1) {
			listSql=listSql.replace("{agentId}", agentId);   
		  }
        
          if (listSql.lastIndexOf("{clientId}")!=-1) {
  			listSql=listSql.replace("{clientId}", userId);     
  		  }
          String subtitle=Config.getChildValue(agentId,fileName,"subtitle"); 
          String menuName=element.getChild("listData").getChildTextTrim("menuName");
          String optionvalue=element.getChild("searchType").getChildTextTrim("optionvalue");
          String optionname=element.getChild("searchType").getChildTextTrim("optionname");
          String dateField=Config.getChildValue(agentId,fileName,"dateField");
          String searchTypeId=element.getChild("searchType").getAttributeValue("id");
          String exportDataId=element.getChild("exportData").getAttributeValue("id");
        
          //将xml中字段转换列表内容并格式化: <fstate>4=充值成功,5=充值失败,?=充值中</fstate>
          String sChangeData = "";
          String fieldChangeList=element.getChild("fieldChange").getChildTextTrim("fieldChangeList");
          String sFieldName = fc.getString("!@#$%"+fieldChangeList+",", "!@#$%", ",");
          while (!sFieldName.equals("")){  
              String ChangeData =element.getChild("fieldChange").getChildTextTrim("col_" + sFieldName);
        	  sChangeData = sChangeData + "<" + sFieldName + ">" + ChangeData + "</" + sFieldName + ">";
        	  fieldChangeList = fc.replace(fieldChangeList + ",", sFieldName + ",", "");
        	  sFieldName = fc.getString("!@#$%"+fieldChangeList+",", "!@#$%", ",");
          }
          int page = 1;  
          try{
  			if(Form.getNopage() != null){ 
  				page = Form.getNopage();
  			}
	  	  }catch(Exception e){  
	  	  }     
	  	//*********************************************
	  	  String fields=element.getChild("searchTypeTwo").getAttributeValue("field");
		  String fieldsValue="";  
		  Map <String ,String> fieldMap=new HashMap<String, String>();
		  if (!fields.equals("")) {
           	String fieldsArr[]=fields.split(",");
           	for (int k = 0; k < fieldsArr.length; k++){
           		String fieldvalue="$";
           		if (request.getParameter(fieldsArr[k])!=null) {  
           			if (!request.getParameter(fieldsArr[k]).equals("")) {  
           				fieldvalue=fc.getpv(request, fieldsArr[k]);  
						}
					}    
           		fieldMap.put(fieldsArr[k], fieldvalue);
        		fieldsValue=fieldsValue+fieldvalue+",";  
				}  
		  }else{
			  fields="all";
		  }
			 Date dt=new Date();
		     SimpleDateFormat matter1=new SimpleDateFormat("yyyy-M-d");
		     String newDate=matter1.format(dt);
		     
		  if (type==null) {
			  key="";   
			  type="all";  
			  starttime=newDate;   
			  endtime=newDate;  
			  fields="all";
		  }
		  //*****************************************************
		  
         
			
				 
		    Map pageMap=new HashMap<String, String>();
	          pageMap.put("subtitle",subtitle);
	          pageMap.put("pageUrl", pageUrl);
	          pageMap.put("menuName", menuName);
	          pageMap.put("optionvalue", optionvalue);
	          pageMap.put("optionname", optionname);
	          pageMap.put("xmlName", fileName);
	          pageMap.put("dateField", dateField);  
	          pageMap.put("searchTypeId", searchTypeId);
	          pageMap.put("exportDataId", exportDataId);
	          request.setAttribute("key",key);
	          request.setAttribute("type",type);
	          request.setAttribute("endtime",endtime);
	          request.setAttribute("starttime",starttime);
	          
	          request.setAttribute("fields",fields); 
	          request.setAttribute("fieldsValue",fieldsValue); 
	            
	          request.setAttribute("elements",elements);     
	          String whereStr=getWhereString(fields,fieldsValue,dateField,key,type,starttime,endtime)+" "+orderby;
	          List<String[]> list = generalService.findDataList(page,whereStr,listSql);
		//判断是有修改功能
        String modifyCol=element.getChild("modifyconfig").getChildTextTrim("col");   
        String buttonname="";
        String url="";
        if (!modifyCol.equals("")) {
      	  buttonname=element.getChild("modifyconfig").getChildTextTrim("buttonname");
      	  url=element.getChild("modifyconfig").getChildTextTrim("url");
      	  list=getListToModifyData(list,modifyCol,url,buttonname);  
        }
		     
		//对字段内容进行转换处理
		list=getfieldChangeData(list, sChangeData);
	     

		int noCount = generalService.noCount;
		
		int countPage = 0;
		if(noCount % 15 == 0){
			countPage = noCount / 15;
		}else{
			countPage = noCount / 15 + 1;
		}  
		if(page < 1) page = 1;
		if(page > countPage) page = countPage;

		request.setAttribute("noCount", noCount);
		request.setAttribute("noPage", page);
		request.setAttribute("countPage", countPage);
		request.setAttribute("list", list) ;
		request.setAttribute("pageMap", pageMap);
		request.setAttribute("fieldMap", fieldMap); 
		return mapping.findForward("dataList") ;         
	}
	
	
	
	//统计模板
	public ActionForward findStatList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		GeneralActionFrom Form = (GeneralActionFrom) form ;
		  //页面搜索条件  
		  HttpSession session = request.getSession();
		  String key=request.getParameter("key");
		  String type=request.getParameter("type");
		  String endtime=Tools.correctDate(request.getParameter("endtime"));
		  String starttime=Tools.correctDate(request.getParameter("starttime"));  
		  CoAgent agent = (CoAgent)session.getAttribute("currentAgent");
		  if (agent==null) {
		     return mapping.findForward("statList") ;        
		  }
		  //代理商id
		  String agentId=agent.getFid();
		  String userId=(String) session.getAttribute("username");
		//如果是员工登录，取上级ID
		  String userType = (String)session.getAttribute("usertype");
			if (userType.equals("staff")) {  
				userId=(String)session.getAttribute("higherId"); 
			}
		  //xml文件名
		  String fileName=request.getParameter("name");
		  Element element=Config.getConfigElement(agentId,fileName);
		  
		  //*****************************************************
		  
		  //解析XML elements节点
		    List elementList=element.getChild("searchTypeTwo").getChildren();
		  
		    Iterator rootIt = elementList.iterator();
		     Map<String ,Map> elements=new HashMap<String, Map>();
		     Map<String ,String> elementRow=null;
		     int i=0;
		    while(rootIt.hasNext()){
		    	elementRow=new HashMap<String, String>();
		    	 Element e = (Element)rootIt.next();
		    	 String Type=e.getAttributeValue("type");
				 String name=e.getAttributeValue("name");//element 节点属性值   
				 String Class=e.getAttributeValue("class");
				 String tag=e.getAttributeValue("tag");
				 String tip=e.getAttributeValue("tip");
				 
				 String onKeypress="";
				 String sql="";
				 String option="";
				 elementRow.put("type", Type);
				 elementRow.put("name", name);
				 elementRow.put("class", Class);
				 elementRow.put("tag", tag);
				 elementRow.put("tip", tip);
				 
				   
				 if (Type.equals("select")) {
					 sql= e.getAttributeValue("sql");
					 if (sql.lastIndexOf("{agentId}")!=-1) {
						 sql=sql.replace("{agentId}", agentId);        
					 }
				    
					 if (sql.lastIndexOf("{clientId}")!=-1) {
						 sql=sql.replace("{clientId}", userId);          
					 }
					 List<String[]> list = generalService.findSelectLists(sql); 
					 request.setAttribute(name+"list", list);    
				 } if (Type.equals("selectT")) {
					 option= e.getAttributeValue("option");
					 elementRow.put("option", option); 
				 }
				 elements.put(Integer.toString(i), elementRow);  
				 i++;
		    }
		  
		  //**********************************
		  
          String pageUrl=Config.getChildValue(agentId,fileName,"pageUrl")+"&name="+fileName;
          String listSql=element.getChild("listData").getChildTextTrim("sql");
          //
          if (listSql.lastIndexOf("{agentId}")!=-1) {
			listSql=listSql.replace("{agentId}", agentId);   
		  }
          if (listSql.lastIndexOf("{clientId}")!=-1) {
  			listSql=listSql.replace("{clientId}", userId);  
  		  }  
               
          String subtitle=Config.getChildValue(agentId,fileName,"subtitle"); 
          String menuName=element.getChild("listData").getChildTextTrim("menuName");
          String optionvalue=element.getChild("searchType").getChildTextTrim("optionvalue");
          String optionname=element.getChild("searchType").getChildTextTrim("optionname");
          String searchTypeId=element.getChild("searchType").getAttributeValue("id");
          String dateField=Config.getChildValue(agentId,fileName,"dateField");
          String group=element.getChild("listData").getChildTextTrim("groupby");
        
          //将xml中字段转换列表内容并格式化: <fstate>4=充值成功,5=充值失败,?=充值中</fstate>
          String sChangeData = "";
          String fieldChangeList=element.getChild("fieldChange").getChildTextTrim("fieldChangeList");
          String sFieldName = fc.getString("!@#$%"+fieldChangeList+",", "!@#$%", ",");
          while (!sFieldName.equals("")){
              String ChangeData =element.getChild("fieldChange").getChildTextTrim("col_" + sFieldName);
        	  sChangeData = sChangeData + "<" + sFieldName + ">" + ChangeData + "</" + sFieldName + ">";
        	  fieldChangeList = fc.replace(fieldChangeList + ",", sFieldName + ",", "");
        	  sFieldName = fc.getString("!@#$%"+fieldChangeList+",", "!@#$%", ",");
          }
  
            
	  	//*********************************************
	  	  
	  	  String fields=element.getChild("searchTypeTwo").getAttributeValue("field");
		  String fieldsValue="";
		  Map <String ,String> fieldMap=new HashMap<String, String>();
		  if (!fields.equals("")) {
            	String fieldsArr[]=fields.split(",");
            	for (int k = 0; k < fieldsArr.length; k++) {
            		
            		String fieldvalue="$";
            		if (request.getParameter(fieldsArr[k])!=null) {
            			if (!request.getParameter(fieldsArr[k]).equals("")) {
            				fieldvalue=request.getParameter(fieldsArr[k]);
						}
            			  
					}    
            		fieldMap.put(fieldsArr[k], fieldvalue);
            		fieldsValue=fieldsValue+fieldvalue+",";  
				}  
		  }
		  
			 Date dt=new Date();
		     SimpleDateFormat matter1=new SimpleDateFormat("yyyy-M-d");
		     String newDate=matter1.format(dt);
		     
		  if (type==null) {
			  key="";   
			  type="all";  
			  starttime=newDate;   
			  endtime=newDate;  
			  fields="all";
		  }
				 
		    Map pageMap=new HashMap<String, String>();
	          pageMap.put("subtitle",subtitle);
	          pageMap.put("pageUrl", pageUrl);
	          pageMap.put("menuName", menuName);
	          pageMap.put("optionvalue", optionvalue);
	          pageMap.put("optionname", optionname);
	          pageMap.put("xmlName", fileName);
	          pageMap.put("dateField", dateField); 
	          pageMap.put("searchTypeId", searchTypeId);
	          
	          request.setAttribute("key",key);
	          request.setAttribute("type",type);
	          request.setAttribute("endtime",endtime);
	          request.setAttribute("starttime",starttime);  
	          
	          request.setAttribute("fields",fields); 
	          request.setAttribute("fieldsValue",fieldsValue); 
	          
	          request.setAttribute("elements",elements);  
	          String whereString=getWhereString(fields,fieldsValue,dateField,key,type,starttime,endtime);
	          if (!group.equals("")) {
	        	  if(group.toLowerCase().indexOf("group by") > -1){
	        		  whereString=whereString+group;
	        	  }else{
	        		  whereString=whereString+" group by "+group;
	        	  }
			  }  
		List<String[]> list = generalService.findDataList(1,whereString,listSql) ; 
		
		//对字段内容进行转换处理
		list=getfieldChangeData(list,sChangeData);
		request.setAttribute("list", list);  
		request.setAttribute("pageMap", pageMap);  
		request.setAttribute("fieldMap", fieldMap); 
		return mapping.findForward("statList");           
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
	//列表修改功能过滤
	private List getListToModifyData(List<String[]> list, String col,String url,String buttonname) {
		
		for (int i=0; i<list.size(); i++){
			String[] aa = (String[])list.get(i);	//取得一行
			for (int j=0; j<aa.length; j++){
				if (j==Integer.parseInt(col)-1) {   
					aa[j]="<a href="+url+aa[j]+">"+buttonname+"</a>";
				}
			}  
			list.set(i, aa);  
		}
		return list;
	}
	public static String getWhereString(String fields,String fieldsValue,String dateField,String searchKey,String searchType,String startTime,String endTime){
		String whereStr="";
		if (!searchType.equals("all")) {
			whereStr="and  "+searchType+"="+"'"+searchKey+"'";  
		}
		if (!startTime.equals("") && !dateField.equals("0")) {
			whereStr=whereStr+" and " +"to_char("+dateField+",'yyyy-mm-dd hh24:mi:ss')"+" >= '"+startTime+" 00:00:00' and "+"to_char("+dateField+",'yyyy-mm-dd hh24:mi:ss')"+" <= '"+endTime+" 23:59:59'";   
		}if (!fields.equals("all")) {
			if(fields != null && !"".equals(fields.trim())){
				String fieldsArr[]=fields.split(",");
				String fieldsValueArr[]=fieldsValue.split(",");
				if (fieldsArr.length!=fieldsValueArr.length) {
					 return whereStr;  
				}    
				for (int i = 0; i < fieldsArr.length; i++) {
					if (!fieldsValueArr[i].equals("$")) {  
						if (fieldsValueArr[i].lastIndexOf("(")!=-1) {
							whereStr=whereStr+" and "+fieldsArr[i]+" not in "+fieldsValueArr[i].replace("_", ",")+"";  
						}else{
						whereStr=whereStr+" and "+fieldsArr[i]+"='"+fieldsValueArr[i]+"'";
						}
					}
				}
			}
			}
		return whereStr;  
	}
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		 Config c=new Config();  
		
		GeneralActionFrom Form = (GeneralActionFrom) form ;
		  String key=request.getParameter("key");
		  String type=request.getParameter("type");
		  String endtime=request.getParameter("endtime");
		  String starttime=request.getParameter("starttime");
		  
		  HttpSession session = request.getSession();
		  CoAgent agent = (CoAgent)session.getAttribute("currentAgent");
		  //代理商id
		  String agentId="";
		  try{
			  agentId=agent.getFid();
		  }catch(Exception  e){
			  System.out.println(e);  
		  }
		  
		  //xml文件名  
		    
		  String id="("+request.getParameter("id")+")";        
		  String fileName=request.getParameter("name");
		  String listSql=c.getConfigElement(agentId,fileName).getChild("listData").getChildTextTrim("sql");
          String deleteSql=c.getConfigElement(agentId,fileName).getChild("deletemethod").getChildTextTrim("sql");
          String subtitle=c.getChildValue(agentId,fileName,"subtitle"); 
          String pageUrl=c.getChildValue(agentId,fileName,"pageUrl"); 
          String menuName=c.getConfigElement(agentId,fileName).getChild("listData").getChildTextTrim("menuName");
          String optionvalue=c.getConfigElement(agentId,fileName).getChild("searchType").getChildTextTrim("optionvalue");
          String optionname=c.getConfigElement(agentId,fileName).getChild("searchType").getChildTextTrim("optionname");
          String dateField=Config.getChildValue(agentId,fileName,"dateField");
          
          Map pageMap=new HashMap<String, String>();
          pageMap.put("subtitle",subtitle);   
          pageMap.put("pageUrl", pageUrl);
          pageMap.put("menuName", menuName);
          pageMap.put("optionvalue", optionvalue);
          pageMap.put("optionname", optionname);
          pageMap.put("xmlName", fileName);
		  if (type==null) {
			  key="";   
			  type="all";  
			  starttime="";   
			  endtime="";   
		  }
		  
		  boolean del = generalService.delete(id,deleteSql) ; 
		  List<String[]> list = generalService.findDataList(1,getWhereString("","",dateField,key,type,starttime,endtime),listSql) ; 
		  
		request.setAttribute("list", list) ;
		request.setAttribute("pageMap", pageMap) ;
		return mapping.findForward("dataList") ;        
	}   
	

	
	//添加页面模板
	public ActionForward addData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
//		GeneralActionFrom Form = (GeneralActionFrom) form ;
		String fmoney = request.getParameter("fmoney");
		  HttpSession session = request.getSession();
		  //用户ID
		  if (session.getAttribute("username")==null) {
			  return mapping.findForward("add") ;   
		  }
		  CoAgent agent = (CoAgent)session.getAttribute("currentAgent");
		  com.agents.pojo.Client client = (com.agents.pojo.Client)session.getAttribute("currentClient");
		  String agentId=agent.getFid();  //代理商id
		  
		  String userId=session.getAttribute("username").toString();
		//如果是员工登录，取上级ID
		  String userType = (String)session.getAttribute("usertype");
			if (userType.equals("staff")) {  
				userId=(String)session.getAttribute("higherId"); 
			}
		  String id="";
		  String  fileName=request.getParameter("name");
		  Element element= Config.getConfigElement(agentId,fileName); 
		  
		  String urlparaRequest= request.getParameter("urlpara");  
		  
		  //get xmlchild
		  String subtitle= Config.getChildValue(agentId,fileName,"subtitle");
		  String buttonname=Config.getChildValue(agentId,fileName,"buttonname");
		  String tablename=Config.getChildValue(agentId,fileName,"tablename");
		  String selectsql=Config.getChildValue(agentId,fileName,"selectsql");
		  String submiteurl=Config.getChildValue(agentId,fileName,"submiteurl");
		  String urlpara=Config.getChildValue(agentId,fileName,"urlpara");
		  
		  String massage="";
		  if (urlparaRequest!=null) {  
			// 判断字url中有没有md5加密
			  if (urlparaRequest.lastIndexOf("md5")!=-1) {
					String urlArr[]=urlparaRequest.split("md5");
					urlparaRequest=urlArr[0]+fc.getMd5Str(urlArr[1]+"qPJuv2yH3OIUieNz");  
				}
			  urlparaRequest=submiteurl+urlparaRequest;
//                System.out.println("请求URL=="+urlparaRequest);
				String urlparaArray[]=urlparaRequest.replace("?", "@-@").split("@-@");
//				String sendPost=fc.SendDataViaPost(urlparaArray[0], urlparaArray[1], "");
				boolean sendPost = new ConnOracle().addMoney(agentId, fmoney);
				if(sendPost){
					massage = "success";
				}
		  }     
		  if (urlpara.lastIndexOf("操作员")!=-1) {
			  urlpara=urlpara.replaceAll("操作员", userId);
		  }
		  String orderId=fc.GetOrderID("");
		  if (urlpara.lastIndexOf("{orderID}")!=-1) {
			  urlpara=urlpara.replace("{orderID}", orderId);        
		  }   
		  
		  Map pageMap=new HashMap<String, String>();  
          pageMap.put("subtitle", subtitle);
          pageMap.put("buttonname", buttonname);
          pageMap.put("tablename", tablename);
          pageMap.put("selectsql", selectsql);
          pageMap.put("submiteurl", submiteurl);
          pageMap.put("urlpara", urlpara);   
          pageMap.put("fileName", fileName);
          pageMap.put("id", "0");
		  
          
          //解析XML elements节点
		    List elementList=element.getChild("elements").getChildren();
		    Iterator rootIt = elementList.iterator();
		     Map<String ,Map> elements=new HashMap<String, Map>();
		     Map<String ,String> elementRow=null;
		     int i=0;
		    while(rootIt.hasNext()){
		    	elementRow=new HashMap<String, String>();
		    	 Element e = (Element)rootIt.next();
		    	 String type=e.getAttributeValue("type");
				 String name=e.getAttributeValue("name");//element 节点属性值   
				 String Class=e.getAttributeValue("class");
				 String tag=e.getAttributeValue("tag");
				 String tip=e.getAttributeValue("tip");
				 String onKeypress="";
				 String value="";
				 String sql="";
				 elementRow.put("type", type);
				 elementRow.put("name", name);
				 elementRow.put("class", Class);
				 elementRow.put("tag", tag);
				 elementRow.put("tip", tip);
				 if (type.equals("text")) {
					 onKeypress=e.getAttributeValue("onKeypress");
					 elementRow.put("onKeypress", onKeypress);
				}
				 if (type.equals("textT")) {
					 value=e.getAttributeValue("value");
					 if (value.lastIndexOf("{clientId}")!=-1) {
						 value=value.replace("{clientId}", userId);          
					 }
					 elementRow.put("value", value);  
				 }
				 if (type.equals("select") || type.equals("selectAjax") || type.equals("enumerate")){  
					 sql= e.getAttributeValue("sql");
					 if (sql.lastIndexOf("{agentId}")!=-1) {
						 sql=sql.replace("{agentId}", agentId);        
					 }
					 if (sql.lastIndexOf("{clientId}")!=-1) {
						 sql=sql.replace("{clientId}", userId);          
					 }
					 List<String[]> list = generalService.findSelectLists(sql); 
					 request.setAttribute(name+"list", list);    
				 }
				 elements.put(Integer.toString(i), elementRow);  
				 i++;
		    }
		  
		  request.setAttribute("pageMap", pageMap) ;
		  request.setAttribute("elements", elements) ;
		  request.setAttribute("massage", massage) ;
		  
		  return mapping.findForward("add") ;         
	}   
	//支付宝加款URL拼接
	public ActionForward addMoneyUrlAjax(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		  String fileName=request.getParameter("name");
		  String urlpara=request.getParameter("urlpara"); 
		  //代理商id
		  HttpSession session = request.getSession();
		  CoAgent agent = (CoAgent)session.getAttribute("currentAgent");
		  PrintWriter out=null;  
          out = response.getWriter(); 
		  String agentId=agent.getFid(); 
//		  String faceDomain=agent.getFfaceDomain();
//		  Element element=Config.getConfigElement(agentId,fileName); 
		//取得访问地址

		  String  submiteurl=Config.getChildValue(agentId,fileName,"submiteurl");
//		  if (submiteurl.lastIndexOf("{agentdomain}")!=-1) {
//			  submiteurl=submiteurl.replace("{agentdomain}", faceDomain);
//		  }
		  String path = request.getScheme() + "://" + request.getServerName()	+ ":" + request.getServerPort();	// "http://190.10.2.33:7001/"; 
		  if (request.getServerPort() == 80) path = request.getScheme() + "://" + request.getServerName();
		  
		  int addMoneyNum = urlpara.indexOf("addMoneyType=");
		  String addMType = "0";
		  if( addMoneyNum > -1){
			  try {
				  
				  //0代表支付宝，     1代表财付通1,   2代表财付通2
				  String typeNum = urlpara.substring(addMoneyNum + 13, addMoneyNum + 14);
				  if("0".equals(typeNum)){
					  submiteurl = path+"/ZfwebApi/alipay_index.jsp?";
					  addMType = "0";
				  }else if("1".equals(typeNum)){
					  submiteurl = path+"/ZfwebApi/caiftRequest.jsp?";
					  addMType = "1";
				  }else if("2".equals(typeNum)){
					  submiteurl = path+"/ZfwebApi/caiftRequest.jsp?";
					  addMType = "2";
				  }else if("3".equals(typeNum)){
					  submiteurl = path+"/ZfwebApi/alipay_index.jsp?";
					  addMType = "3";
				  }
			} catch (Exception e) {
				e.printStackTrace();
			}
		  }
		  
		  // 判断字url中有没有md5加密
		  if (urlpara.lastIndexOf("md5")!=-1) {
				String urlArr[]=urlpara.split("md5");
				if("0".equals(addMType)){
					urlpara=urlArr[0]+fc.getMd5Str(urlArr[1]+"xqX3QD0aOhLDOz66"); //鼎恒支付宝
				} else if("1".equals(addMType)) {
					urlpara=urlArr[0]+fc.getMd5Str(urlArr[1]+"bDKEXX2IuMlFdho2");//专用财付通
				} else if("2".equals(addMType)) {
					urlpara=urlArr[0]+fc.getMd5Str(urlArr[1]+"HGlZfSk4gmir667o");//鼎恒财付通
				} else if("3".equals(addMType)) {
					urlpara=urlArr[0]+fc.getMd5Str(urlArr[1]+"qPJuv2yH3OIUieNz"); //华丹支付宝
				}
			}
		  urlpara=submiteurl+urlpara;
		  urlpara=urlpara.replace("@-@", "&");
		  System.out.println("urlpara:"+urlpara);
		  
//           if (faceDomain==null || faceDomain.equals("") ) {
//        	   out.println("faceDomain_null");  
//     		  return null;
//		   }
              out.println(urlpara);    
                
		  return null;
	}
		  //用户ID
	//修改页面模板
	public ActionForward updateData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		GeneralActionFrom Form = (GeneralActionFrom) form ;
		  HttpSession session = request.getSession();
		  //代理商id
		  CoAgent agent = (CoAgent)session.getAttribute("currentAgent");
		  String agentId=agent.getFid(); 
		  //用户ID
		  if (session.getAttribute("username")==null) {
			  return mapping.findForward("modify") ;     
		  }
		  String userId=session.getAttribute("username").toString();
		//如果是员工登录，取上级ID
		  String userType = (String)session.getAttribute("usertype");
			if (userType.equals("staff")) {  
				userId=(String)session.getAttribute("higherId"); 
			}
		  String fileName=request.getParameter("name");
		  String id=request.getParameter("id");
		  
		  Element element=Config.getConfigElement(agentId,fileName); 
		  
		  String urlparaRequest= request.getParameter("urlpara");
		  
		   
		  //get xmlchild
		  String subtitle= Config.getChildValue(agentId,fileName,"subtitle");
		  String buttonname=Config.getChildValue(agentId,fileName,"buttonname");
		  String tablename=Config.getChildValue(agentId,fileName,"tablename");
		  String selectsql=Config.getChildValue(agentId,fileName,"selectsql");
		  String submiteurl=Config.getChildValue(agentId,fileName,"submiteurl");
		  String urlpara=Config.getChildValue(agentId,fileName,"urlpara");
		  String submittype=Config.getChildValue(agentId,fileName,"submittype");
		  if (!id.equals("") && selectsql.lastIndexOf("{id}")!=-1 ) {
			  selectsql=selectsql.replace("{id}", id);	  
		  }
		  
		  String massage="";
		  if (urlparaRequest!=null) {
			  if (urlparaRequest.lastIndexOf("$")!=-1) {
				  urlparaRequest=urlparaRequest.replace("$","'");
			  } 
			  if(submittype.equals("sql")){  
				  boolean aa=generalService.update(urlparaRequest);  
				  if(aa){
					  massage="success";
				  }  
			  }else{    
			    urlparaRequest=submiteurl+urlparaRequest;
				String urlparaArray[]=urlparaRequest.replace("?", "@-@").split("@-@");
				String sendPost=fc.SendDataViaPost(urlparaArray[0], urlparaArray[1], "");
				massage=sendPost;  
			  }
		  }       
		//修改列表数据
		  String[] lists = generalService.findModifyList(selectsql);
		  
		  if (urlpara.lastIndexOf("操作员")!=-1) {
			  urlpara=urlpara.replaceAll("操作员", userId);
		  }
		  String orderId=fc.GetOrderID("");
		  if (urlpara.lastIndexOf("{orderID}")!=-1) {
			  urlpara=urlpara.replace("{orderID}", orderId);        
		  }   
		  Map pageMap=new HashMap<String, String>();  
          pageMap.put("subtitle", subtitle);
          pageMap.put("buttonname", buttonname);
          pageMap.put("tablename", tablename);
          pageMap.put("selectsql", selectsql);
          pageMap.put("submiteurl", submiteurl);
          pageMap.put("urlpara", urlpara);
          pageMap.put("fileName", fileName);
          pageMap.put("id", id);
          
		  
          
          //解析XML elements节点
		    List elementList=element.getChild("elements").getChildren();
		    Iterator rootIt = elementList.iterator();
		     Map<String ,Map> elements=new HashMap<String, Map>();
		     Map<String ,String> elementRow=null;
		     int i=0;
		    while(rootIt.hasNext()){
		    	elementRow=new HashMap<String, String>();
		    	 Element e = (Element)rootIt.next();
		    	 String type=e.getAttributeValue("type");
				 String name=e.getAttributeValue("name");//element 节点属性值   
				 String Class=e.getAttributeValue("class");
				 String tag=e.getAttributeValue("tag");
				 String tip=e.getAttributeValue("tip");
				//不需要有值的为空
				 String value="";
					 if(lists.length-1>=i){
						 value=lists[i];
					 }  
					 
				 String onKeypress="";
				 String sql="";
				 String option="";
				 elementRow.put("type", type);
				 elementRow.put("name", name);
				 elementRow.put("class", Class);
				 elementRow.put("tag", tag);
				 elementRow.put("tip", tip);
				 elementRow.put("value", value);  
				 if (type.equals("text")) {
					 onKeypress=e.getAttributeValue("onKeypress");
					 elementRow.put("onKeypress", onKeypress);
				}  
				 if (type.equals("select")) {
					 sql= e.getAttributeValue("sql");
					 if (sql.lastIndexOf("{agentId}")!=-1) {
						 sql=sql.replace("{agentId}", agentId);        
					 }
					 List<String[]> list = generalService.findSelectLists(sql); 
					 request.setAttribute(name+"list", list);    
				 } if (type.equals("selectT")) {
					 option= e.getAttributeValue("option");
					 elementRow.put("option", option); 
				 }
				 elements.put(Integer.toString(i), elementRow);  
				 i++;
		    }
		 
		  request.setAttribute("pageMap", pageMap) ;
		  request.setAttribute("elements", elements) ;
		  request.setAttribute("massage", massage) ;
		return mapping.findForward("modify") ;         
	}  
	//查看详细模板
	public ActionForward viewDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		GeneralActionFrom Form = (GeneralActionFrom) form ;
		  HttpSession session = request.getSession();
		  //用户ID
		  if (session.getAttribute("username")==null) {
			  return mapping.findForward("detail") ;     
		  }
		//代理商id
		  CoAgent agent = (CoAgent)session.getAttribute("currentAgent");
		  String agentId=agent.getFid(); 
		  
		  String userId=session.getAttribute("username").toString();
		//如果是员工登录，取上级ID
		  String userType = (String)session.getAttribute("usertype");
			if (userType.equals("staff")) {  
				userId=(String)session.getAttribute("higherId"); 
			}
		  String fileName=request.getParameter("name");
		  String id=request.getParameter("id");
		  if (id.equals("clientid")) {
			id=userId;  
		  }
		  Element element=Config.getConfigElement(agentId,fileName); 
		  
		  //get xmlchild
		  String subtitle= Config.getChildValue(agentId,fileName,"subtitle");
		  String buttonname=Config.getChildValue(agentId,fileName,"buttonname");
		  String selectsql=Config.getChildValue(agentId,fileName,"selectsql");
		  if (!id.equals("") && selectsql.lastIndexOf("{id}")!=-1 ) {
			  selectsql=selectsql.replace("{id}", id);	  
		  }
		  
		  String massage="";
		 
		  //将xml中字段转换列表内容并格式化: <fstate>4=充值成功,5=充值失败,?=充值中</fstate>
          String sChangeData = "";
          String fieldChangeList=element.getChild("fieldChange").getChildTextTrim("fieldChangeList");
          String sFieldName = fc.getString("!@#$%"+fieldChangeList+",", "!@#$%", ",");
          while (!sFieldName.equals("")){
              String ChangeData =element.getChild("fieldChange").getChildTextTrim("col_" + sFieldName);
        	  sChangeData = sChangeData + "<" + sFieldName + ">" + ChangeData + "</" + sFieldName + ">";
        	  fieldChangeList = fc.replace(fieldChangeList + ",", sFieldName + ",", "");
        	  sFieldName = fc.getString("!@#$%"+fieldChangeList+",", "!@#$%", ",");
          }
          
		//修改列表数据
		  String[] lists = generalService.findModifyList(selectsql);
		  List<String[]> listT = new ArrayList<String[]>();
		  listT.add(lists);  
		  listT=getfieldChangeData(listT,sChangeData);
		  
		  lists=listT.get(0);  
		  Map pageMap=new HashMap<String, String>();  
          pageMap.put("subtitle", subtitle);
          pageMap.put("buttonname", buttonname);
          pageMap.put("selectsql", selectsql);
             
          //解析XML elements节点
		    List elementList=element.getChild("elements").getChildren();
		    Iterator rootIt = elementList.iterator();
		     Map<String ,Map> elements=new HashMap<String, Map>();
		     Map<String ,String> elementRow=null;
		     int i=0;
		    while(rootIt.hasNext()){
		    	elementRow=new HashMap<String, String>();
		    	 Element e = (Element)rootIt.next();
		    	 String type=e.getAttributeValue("type");
				 String name=e.getAttributeValue("name");//element 节点属性值   
				 String Class=e.getAttributeValue("class");
				 String tag=e.getAttributeValue("tag");
				 String tip=e.getAttributeValue("tip");
				 String size=e.getAttributeValue("size");
				 if (lists[i]==null) {
					 lists[i]="";
				 }
				 String value=lists[i];
				 String sql="";
				 String option="";
				 elementRow.put("type", type);
				 elementRow.put("name", name);
				 elementRow.put("class", Class);
				 elementRow.put("tag", tag);
				 elementRow.put("tip", tip);
				 elementRow.put("size", size);
				 elementRow.put("value", value);  
				 if (type.equals("select")) {
					 sql= e.getAttributeValue("sql");
					 if (sql.lastIndexOf("{agentId}")!=-1) {
						 sql=sql.replace("{agentId}", agentId);        
					 }
					 List<String[]> list = generalService.findSelectLists(sql); 
					 request.setAttribute(name+"list", list);    
				 } if (type.equals("selectT")) {
					 option= e.getAttributeValue("option");
					 elementRow.put("option", option); 
				 }
				 elements.put(Integer.toString(i), elementRow);  
				 i++;
		    }
		 
		  request.setAttribute("pageMap", pageMap) ;
		  request.setAttribute("elements", elements) ;
		  return mapping.findForward("detail") ;         
	}    
	//ajax 查询
	public ActionForward findProductInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		    String fid=request.getParameter("id");
		    String sql="select frate from AcClientProduct where fid='"+fid+"'";
		    String data[]=generalService.findProductInfo(sql); 
		    String message="费率为："+data[0];
		    PrintWriter out=null;  
            out = response.getWriter();  
                out.println(message);  
		   return null;
	}
	//提交卡定单模板
	public ActionForward addCardOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		GeneralActionFrom Form = (GeneralActionFrom) form ;
		 //代理商id
		  HttpSession session = request.getSession();
		  CoAgent agent = (CoAgent)session.getAttribute("currentAgent");
		  String agentId=agent.getFid(); 
		  //用户ID
		  if (session.getAttribute("username")==null) {
			  return mapping.findForward("loginout") ;   
		  }
		  String userId=session.getAttribute("username").toString();
		//如果是员工登录，取上级ID
		  String userType = (String)session.getAttribute("usertype");
			if (userType.equals("staff")) {  
				userId=(String)session.getAttribute("higherId"); 
			}
		  String fileName=request.getParameter("name");
		  //商品ID   
		  String id=request.getParameter("id");
		  Element element=Config.getConfigElement(agentId,fileName); 
		  String urlparaRequest= request.getParameter("urlpara");
		  
		   
		  //get xmlchild
		  String subtitle= Config.getChildValue(agentId,fileName,"subtitle");
		  String buttonname=Config.getChildValue(agentId,fileName,"buttonname");
		  String tablename=Config.getChildValue(agentId,fileName,"tablename");
		  String selectsql=Config.getChildValue(agentId,fileName,"selectsql");
		  String submiteurl=Config.getChildValue(agentId,fileName,"submiteurl");
		  String urlpara=Config.getChildValue(agentId,fileName,"urlpara");
		  String submittype=Config.getChildValue(agentId,fileName,"submittype");
		  if (!id.equals("") && selectsql.lastIndexOf("{id}")!=-1 ) {
			  selectsql=selectsql.replace("{id}", id);	  
		  }
		  
		  String massage="";
		  if (urlparaRequest!=null) {
			  if (urlparaRequest.lastIndexOf("$")!=-1) {
				  urlparaRequest=urlparaRequest.replace("$","'");
			  } 
			  if(submittype.equals("sql")){  
				  boolean aa=generalService.update(urlparaRequest);  
				  if(aa){
					  massage="success";
				  }  
			  }else{    
			    urlparaRequest=submiteurl+urlparaRequest;
				String urlparaArray[]=urlparaRequest.replace("?", "@-@").split("@-@");
				String sendPost=fc.SendDataViaPost(urlparaArray[0], urlparaArray[1], "");
				massage=sendPost;  
			  }
		  }       
		  //修改列表数据
		  String[] lists = generalService.findModifyList(selectsql);
		  
		  if (urlpara.lastIndexOf("操作员")!=-1) {
			  urlpara=urlpara.replaceAll("操作员", userId);
		  }
		  String orderId=fc.GetOrderID("");
		  if (urlpara.lastIndexOf("{orderID}")!=-1) {
			  urlpara=urlpara.replace("{orderID}", orderId);        
		  }   
		  Map pageMap=new HashMap<String, String>();  
          pageMap.put("subtitle", subtitle);
          pageMap.put("buttonname", buttonname);
          pageMap.put("tablename", tablename);
          pageMap.put("selectsql", selectsql);
          pageMap.put("submiteurl", submiteurl);
          pageMap.put("urlpara", urlpara);
          pageMap.put("fileName", fileName);
          pageMap.put("id", id);
          
		 
          
          //解析XML elements节点
		    List elementList=element.getChild("elements").getChildren();
		    Iterator rootIt = elementList.iterator();
		     Map<String ,Map> elements=new HashMap<String, Map>();
		     Map<String ,String> elementRow=null;
		     int i=0;
		    while(rootIt.hasNext()){
		    	elementRow=new HashMap<String, String>();
		    	 Element e = (Element)rootIt.next();
		    	 String type=e.getAttributeValue("type");
				 String name=e.getAttributeValue("name");//element 节点属性值   
				 String Class=e.getAttributeValue("class");
				 String tag=e.getAttributeValue("tag");
				 String tip=e.getAttributeValue("tip");
				 //不需要有值的为空
				 String value="";
					 if(lists.length-1>=i){
						 value=lists[i];
					 }  
				 String onKeypress="";
				 String sql="";
				 String option="";
				 elementRow.put("type", type);
				 elementRow.put("name", name);
				 elementRow.put("class", Class);
				 elementRow.put("tag", tag);
				 elementRow.put("tip", tip);
				 elementRow.put("value", value);  
				 if (type.equals("text")) {
					 onKeypress=e.getAttributeValue("onKeypress");
					 elementRow.put("onKeypress", onKeypress);
				}  
				 if (type.equals("select")) {
					 sql= e.getAttributeValue("sql");  
					 if (sql.lastIndexOf("{agentId}")!=-1) {
						 sql=sql.replace("{agentId}", agentId);        
					 }
					 if (sql.lastIndexOf("{id}")!=-1) {
						 sql=sql.replace("{id}", id);          
					 }
					 List<String[]> list = generalService.findSelectLists(sql); 
					 request.setAttribute(name+"list", list);    
				 } if (type.equals("selectT")) {
					 option= e.getAttributeValue("option");
					 elementRow.put("option", option); 
				 }
				 elements.put(Integer.toString(i), elementRow);  
				 i++;
		    }
		      //判断商品是否有关联字段,如果有则在列表后添加
			  String is=generalService.getfAreaTypeByProductId(id);
			  
			  if (is!=null && !is.equals("")) {  
				 //一级关联字段    
				 String [] firstRelation =generalService.getRelationList(is);
				 elementRow=new HashMap<String, String>();
				 if (firstRelation[1].equals("#")) {
					 elementRow.put("type", "selectRelation");
					 elementRow.put("name", firstRelation[0]);  
					 elementRow.put("class", "");  
					 elementRow.put("tag", firstRelation[2]);
					 elementRow.put("tip", "");
					 elementRow.put("value", "");  
					 elementRow.put("option", firstRelation[3]); 
					 elements.put(Integer.toString(i), elementRow);
				 }else{
					elementRow.put("type", "text");
					 elementRow.put("name", firstRelation[0]);  
					 elementRow.put("class", "");
					 elementRow.put("tag", firstRelation[2]);
					 elementRow.put("tip", "");  
					 elementRow.put("value", "");    
					 elementRow.put("onKeypress", "");
					 elements.put(Integer.toString(i), elementRow);
				}
			  }
		  request.setAttribute("pageMap", pageMap) ;
		  request.setAttribute("elements", elements) ;
		  request.setAttribute("massage", massage) ;
		return mapping.findForward("addcardorder") ;         
	}//提交定单模板
	
	
	public ActionForward addOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		GeneralActionFrom Form = (GeneralActionFrom) form ;
		 //代理商id
		  HttpSession session = request.getSession();
		  CoAgent agent = (CoAgent)session.getAttribute("currentAgent");
		  String agentId=agent.getFid(); 
		  //用户ID
		  if (session.getAttribute("username")==null) {
			  return mapping.findForward("loginout") ;   
		  }
		  String userId=session.getAttribute("username").toString();
		//如果是员工登录，取上级ID
		  String userType = (String)session.getAttribute("usertype");
			if (userType.equals("staff")) {  
				userId=(String)session.getAttribute("higherId"); 
			}
		  String fileName=request.getParameter("name");
		  //商品ID   
		  String id=request.getParameter("id");
		  Element element=Config.getConfigElement(agentId,fileName); 
		  String urlparaRequest= request.getParameter("urlpara");
		  
		   
		  //get xmlchild
		  String subtitle= Config.getChildValue(agentId,fileName,"subtitle");
		  String buttonname=Config.getChildValue(agentId,fileName,"buttonname");
		  String tablename=Config.getChildValue(agentId,fileName,"tablename");
		  String selectsql=Config.getChildValue(agentId,fileName,"selectsql");
		  String submiteurl=Config.getChildValue(agentId,fileName,"submiteurl");
		  String urlpara=Config.getChildValue(agentId,fileName,"urlpara");
		  String submittype=Config.getChildValue(agentId,fileName,"submittype");
		  if (!id.equals("") && selectsql.lastIndexOf("{id}")!=-1 ) {
			  selectsql=selectsql.replace("{id}", id);	  
		  }
		  
		  String massage="";
		  if (urlparaRequest!=null) {
			  if (urlparaRequest.lastIndexOf("$")!=-1) {
				  urlparaRequest=urlparaRequest.replace("$","'");
			  } 
			  if(submittype.equals("sql")){  
				  boolean aa=generalService.update(urlparaRequest);  
				  if(aa){
					  massage="success";
				  }  
			  }else{    
			    urlparaRequest=submiteurl+urlparaRequest;
				String urlparaArray[]=urlparaRequest.replace("?", "@-@").split("@-@");
				String sendPost=fc.SendDataViaPost(urlparaArray[0], urlparaArray[1], "");
				massage=sendPost;  
			  }
		  }       
		  //修改列表数据
		  String[] lists = generalService.findModifyList(selectsql);
		  
		  if (urlpara.lastIndexOf("操作员")!=-1) {
			  urlpara=urlpara.replaceAll("操作员", userId);
		  }
		  String orderId=fc.GetOrderID("");
		  if (urlpara.lastIndexOf("{orderID}")!=-1) {
			  urlpara=urlpara.replace("{orderID}", orderId);        
		  }   
		  Map pageMap=new HashMap<String, String>();  
          pageMap.put("subtitle", subtitle);
          pageMap.put("buttonname", buttonname);
          pageMap.put("tablename", tablename);
          pageMap.put("selectsql", selectsql);
          pageMap.put("submiteurl", submiteurl);
          pageMap.put("urlpara", urlpara);
          pageMap.put("fileName", fileName);
          pageMap.put("id", id);
          
		 
          
          //解析XML elements节点
		    List elementList=element.getChild("elements").getChildren();
		    Iterator rootIt = elementList.iterator();
		     Map<String ,Map> elements=new HashMap<String, Map>();
		     Map<String ,String> elementRow=null;
		     int i=0;
		    while(rootIt.hasNext()){
		    	elementRow=new HashMap<String, String>();
		    	 Element e = (Element)rootIt.next();
		    	 String type=e.getAttributeValue("type");
				 String name=e.getAttributeValue("name");//element 节点属性值   
				 String Class=e.getAttributeValue("class");
				 String tag=e.getAttributeValue("tag");
				 String tip=e.getAttributeValue("tip");
				 //不需要有值的为空
				 String value="";
					 if(lists.length-1>=i){
						 value=lists[i];
					 }  
				 String onKeypress="";
				 String sql="";
				 String option="";
				 elementRow.put("type", type);
				 elementRow.put("name", name);
				 elementRow.put("class", Class);
				 elementRow.put("tag", tag);
				 elementRow.put("tip", tip);
				 elementRow.put("value", value);  
				 if (type.equals("text")) {
					 onKeypress=e.getAttributeValue("onKeypress");
					 elementRow.put("onKeypress", onKeypress);
				}  
				 if (type.equals("select")) {
					 sql= e.getAttributeValue("sql");  
					 if (sql.lastIndexOf("{agentId}")!=-1) {
						 sql=sql.replace("{agentId}", agentId);        
					 }
					 List<String[]> list = generalService.findSelectLists(sql); 
					 request.setAttribute(name+"list", list);    
				 } if (type.equals("selectT")) {
					 option= e.getAttributeValue("option");
					 elementRow.put("option", option); 
				 }
				 elements.put(Integer.toString(i), elementRow);  
				 i++;
		    }
		      //判断商品是否有关联字段,如果有则在列表后添加
			  String is=generalService.getfAreaTypeByProductId(id);
			  if (is!=null && !is.equals("")) {  
				 //一级关联字段    
				 String [] firstRelation =generalService.getRelationList(is);
				 elementRow=new HashMap<String, String>();
				 if (firstRelation[1].equals("#")) {
					 elementRow.put("type", "selectRelation");
					 elementRow.put("name", firstRelation[0]);  
					 elementRow.put("class", "");  
					 elementRow.put("tag", firstRelation[2]);
					 elementRow.put("tip", "");
					 elementRow.put("value", "");  
					 elementRow.put("option", firstRelation[3]); 
					 elements.put(Integer.toString(i), elementRow);
				 }else{
					elementRow.put("type", "text");
					 elementRow.put("name", firstRelation[0]);  
					 elementRow.put("class", "");
					 elementRow.put("tag", firstRelation[2]);
					 elementRow.put("tip", "");  
					 elementRow.put("value", "");    
					 elementRow.put("onKeypress", "");
					 elements.put(Integer.toString(i), elementRow);
				}
			  }
		  request.setAttribute("pageMap", pageMap) ;
		  request.setAttribute("elements", elements) ;
		  request.setAttribute("massage", massage) ;
		return mapping.findForward("addorder") ;         
	}
	
	
	//ajax 获取二级关联菜单
	public ActionForward secondRelationMenu(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String relationField=request.getParameter("relationField");
		String condition=request.getParameter("condition");
		String str = generalService.getRelationList(relationField,condition); 
		 PrintWriter out=null;  
         out = response.getWriter();    
         out.println(str);    
	    return null;	
	}
	//账号在线提单URL拼接
	public ActionForward addOrderUrlAjax(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		  //代理商id
		  HttpSession session = request.getSession();
		  CoAgent agent = (CoAgent)session.getAttribute("currentAgent");
		  String agentId=agent.getFid(); 
		  
		  String ffacedomain=agent.getFfaceDomain(); 
		  String facfaceId=agent.getFacFaceID(); 
          
		    
		  String xmlFile=request.getParameter("xmlFile");
		  Element element=Config.getConfigElement(agentId,xmlFile); 
		  String submiteurl=Config.getChildValue(agentId,xmlFile,"submiteurl");
		  String clientId=request.getParameter("clientId");
		//如果是员工登录，取上级ID
		  String userType = (String)session.getAttribute("usertype");
			if (userType.equals("staff")) {  
				clientId=(String)session.getAttribute("higherId"); 
			}  
		  String key="";
		  String client[]=generalService.getClientKeyById(clientId);
		  if (client[1]!=null) { 
			  key=client[1];
		   }else{
			   
		   }
		  //{agentdomain} 替换coagent 表的  ffacedomain/facfaceId
		  String orderID=fc.GetOrderID("");
		  String productId=fc.getpv(request,"productId");
		  String fPlayName=fc.getpv(request,"fPlayName");
		  String fPrice=fc.getpv(request,"fPrice");
		  String fNumber=fc.getpv(request,"fNumber");
		  String fFillArea=fc.getpv(request,"fFillArea");
		  String fFillServer=fc.getpv(request,"fFillServer");
		  String sumMoney=Integer.toString((Integer.parseInt(fPrice)*Integer.parseInt(fNumber)));
		  submiteurl=submiteurl.replace("{orderID}", orderID).replace("{clientId}", clientId)
		  .replace("{productId}", productId).replace("{fPlayName}", fPlayName).replace("{fPrice}",fPrice).replace("{fNumber}", fNumber)
		  .replace("{fPrice*fNumber}", sumMoney).replace("{fFillArea}", fFillArea).replace("{fFillServer}", fFillServer).replace("{agentdomain}", ffacedomain+"/"+facfaceId);
		   // 判断字url中有没有md5加密[oid][cid][pid][pn][pr][nb][fm][ru][key]
		      
			String sign=fc.getMd5Str(orderID+clientId+productId+fPlayName+fPrice+fNumber+sumMoney+key); 
			submiteurl=submiteurl+sign;
			System.out.println(submiteurl);
			String sendPost=fc.SendDataViaPost(submiteurl, "", "");
			String message="您的订单提交成功,";
			if (sendPost.lastIndexOf("false")!=-1) {  
				message="您的订单提交失败,";  
			}
			if (sendPost.lastIndexOf("ERROR")!=-1) {  
				message="您的订单提交失败,接口地址不存在!";  
			}
			
			sendPost=fc.getString(sendPost, "<msg>","</msg>");
		    PrintWriter out=null;  
              out = response.getWriter();  
              out.println(message+sendPost+"!");        
		  return null;
	}
	//卡在线提单URL拼接
	public ActionForward addCardOrderUrlAjax(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		  //代理商id
		  HttpSession session = request.getSession();
		  CoAgent agent = (CoAgent)session.getAttribute("currentAgent");
		  String agentId=agent.getFid(); 
		  String ffacedomain=agent.getFfaceDomain(); 
		  String fcafaceId=agent.getFcaFaceID();   
		  
		  String xmlFile=request.getParameter("xmlFile");
		  String clientId=request.getParameter("clientId");
		  
		  Element element=Config.getConfigElement(agentId,xmlFile); 
		  String submiteurl=Config.getChildValue(agentId,xmlFile,"submiteurl");
		    
		//如果是员工登录，取上级ID
		  String userType = (String)session.getAttribute("usertype");
			if (userType.equals("staff")) {  
				clientId=(String)session.getAttribute("higherId"); 
			}  
		  String key="";
		  String client[]=generalService.getClientKeyById(clientId);
		  if (client[1]!=null) { 
			  key=client[1];
		   }
		  //{agentdomain} 替换coagent 表的  ffacedomain/facfaceId
		  String orderID=fc.GetOrderID("");
		  String productId=request.getParameter("productId");
		  String cradno=request.getParameter("cradno");
		  String money=request.getParameter("money");
		  String cardpassword=request.getParameter("cardpassword");

		  submiteurl=submiteurl.replace("{orderID}", orderID).replace("{clientId}", clientId)
		  .replace("{productId}", productId).replace("{cradno}", cradno)
		  .replace("{cardpassword}",cardpassword).replace("{money}", money)    
		  .replace("{agentdomain}", ffacedomain+"/"+fcafaceId);  
			String sign=fc.getMd5Str(clientId+orderID+productId+cradno+cardpassword+money+key); 
			submiteurl=submiteurl+sign;
			String sendPost=fc.SendDataViaPost(submiteurl, "", "");
			String message="您的订单提交成功,";
			if (sendPost.lastIndexOf("ERROR")!=-1) {  
				message="您的订单提交失败,";  
			}  
			sendPost=sendPost.substring(sendPost.indexOf(",")+1, sendPost.length());    
		    PrintWriter out=null;  
              out = response.getWriter();    
              out.println(message+sendPost+"!");        
		      return null;
	}
}