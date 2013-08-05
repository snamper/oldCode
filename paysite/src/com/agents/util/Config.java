package com.agents.util;

import java.util.*;
import org.jdom.*;
import java.net.*;
public class Config {
    public Config() {
    }
    private static String  webappPath = null;
    private static Boolean debug = null;
    private static Element Config;

    public static String getWebAppPath() {
        if (webappPath == null) {
            URL url = Config.class.getResource("Config.class");
            String filePath = url.getFile();
            int beginPos = 0;
            if(filePath.startsWith("file:/")){
                beginPos = 6;  
            }
            webappPath = filePath.substring(beginPos, filePath.indexOf("/WEB-INF/"));
            if(getDebugMode()){
            	System.out.println("WEBAPP-PATH->" + webappPath);
            }
        }
        return webappPath;
    }
    public static boolean getDebugMode() {
        if (debug == null) {
            try {
                debug = Boolean.valueOf(ResourceBundle.getBundle("config").getString(
                        "debug"));
            } catch (Exception e) {
                debug = new Boolean(false);
            }
        }
        return debug.booleanValue();
    }

    public static String getChildValue(String filePath,String fileName,String ChildName) {
        return getConfigElement(filePath,fileName).getChildText(ChildName);
    }  
    public static String getChildValue(String fileName,String ChildName) {
        return getConfigElement(fileName).getChildText(ChildName);
    }
    public static Element getConfigElement(String fileName) {
    	
      // if (Config == null || getDebugMode()) {
            Config = XMLHandler.openXML(fileName+".xml");
       // }  
        return Config;
    }
  public static Element getConfigElement(String filePath,String fileName) {
    // if (Config == null || getDebugMode()) {
        Config = XMLHandler.openXML(filePath,fileName+".xml");
    //}  
    return Config;
  }  
	//得到子节点属性值
	public static String getAttributeValue(String filePath,String fileName,String ChildName,String attribute) {
	    return getConfigElement(filePath,fileName).getChild(ChildName).getAttributeValue(attribute);
	}
	public static String getAttributeValue(String fileName,String ChildName,String attribute) {
	    return getConfigElement(fileName).getChild(ChildName).getAttributeValue(attribute);
	}
		public static Map<String ,String> firstMenu; 
		public static Map<String ,Map> secondMenu;

	public static String getFirstlevelMenu(String fid,String fileName){
			 //一级菜单
			 firstMenu=new HashMap<String , String>();
			 secondMenu=new HashMap<String, Map>();
			 //得到根节点列表
			List root=getConfigElement(fid,fileName).getChildren();  
			Iterator rootIt = root.iterator();
			 int i=0;
			 Map <String,String> secondMap = null;
			 while(rootIt.hasNext()){
				 Element element = (Element)rootIt.next();
				 String title=element.getAttributeValue("title");
				 firstMenu.put(Integer.toString(i),title);
				 //二级菜单节点
				 List secondChild=element.getChildren();
			     Iterator secondIt = secondChild.iterator();
			     secondMap=new HashMap<String ,String>();
			     int j=0;
				 while(secondIt.hasNext()){
			    	  Element elementT = (Element)secondIt.next();
			    	  String titleT=elementT.getAttributeValue("title");  
			    	  String href=elementT.getAttributeValue("href");
			    	  String id="ABC";
			    	    if (elementT.getAttributeValue("id")!=null) {
			    	    	id=elementT.getAttributeValue("id");//权限
						}
			          secondMap.put(Integer.toString(j),titleT+"@"+href+"@"+id);
			    	  j++;
			      }
				 secondMenu.put(Integer.toString(i),secondMap);
				 i++;
			 } 
			 return "true";
		}
	public static void main(String[] args) {  
//	     Element element=c.getConfigElement("modifyData");
//	     List elements=element.getChild("elements").getChildren();
//	     Iterator rootIt = elements.iterator();
//	     Map<String ,Map> elementsRow=new HashMap<String, Map>();
//	     Map<String ,String> elementRow=null;
//	     int i=0;
//	     while(rootIt.hasNext()){
//	    	 elementRow=new HashMap<String, String>();
//	    	 Element e = (Element)rootIt.next();
//			 String name=e.getAttributeValue("name");
//			 elementRow.put("name", name);
//			 
//			 elementsRow.put(Integer.toString(i), elementRow);
//			 i++;
//	    }
//	    
//	    for (int j = 0; j < elementsRow.size(); j++) {
//	    	Map<String ,String> element22=elementsRow.get(Integer.toString(j));
//	    	System.out.println(element22.get("name"));
//		}
	}  
}   
