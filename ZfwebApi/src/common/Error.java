package common;


import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Iterator;

import javax.servlet.ServletContext;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/*
 * 错误信息处理类
 */

public class Error {
	
	static String m_ErrorSavePath = System.getenv("JBOSS_HOME");	//显示到控制台的错误级别
	static String m_PrintErrorLevel = "";	//显示到控制台的错误级别
	static String m_WriteErrorLevel = "";	//写到文件的错误级别
	static String m_DebugClassName = "busias";	//调试的类名
	static String IsLoadConfig = "false";
	private URL resource;

	
	/* 
	 * 初始化
	 */
	public Error() {
		
		//装入配置gai-error.xml
//		try {    
//				String sFile = Error.class.getResource("").toString().substring(6) + "gai-error.xml";
//				File f = new File(sFile);
//				if (!f.exists()){
//					IsLoadConfig = "false";
//					System.out.print("[Error.java]装入文件失败," + sFile);
//					return;
//				}
//			    SAXReader reader = new SAXReader();     
//			    Document doc = reader.read(f);    
//			    Element root = doc.getRootElement();    
//			    Element foo;    
//			    for (Iterator i = root.elementIterator("config"); i.hasNext();) {    
//			    	foo = (Element) i.next();    
//				    m_PrintErrorLevel = foo.elementText("out-type");    
//				    m_WriteErrorLevel = foo.elementText("write-type");    
//				    String s = foo.elementText("write-file-path");    
//				    if (!s.equals("")) m_ErrorSavePath = s;
//			    }
//			    doc.clearContent();
//			    IsLoadConfig = "true";
//			} catch (Exception e) { 
//				IsLoadConfig = "false";
//				System.out.print("读取gai-error.xml文件时出错,<config><out-level><write-level>");
//			    e.printStackTrace();    
//			}

	}

	/*
	 * 显示可容的错误到后台
	 * 级别可自由分类,只要是配置串中包含级别串的, 均会输出, 如debug, warning, error  
	 */
	public static void oute(Exception e){
		oute(e, "");
	}

	
	/*
	 * 显示可容的错误到后台
	 * 级别可自由分类,只要是配置串中包含级别串的, 均会输出, 如debug, warning, error  
	 */
	public static void oute(Exception e, String sLevle){
		try{
//			if (IsLoadConfig.equals("false")) return;
			
			//
//			String s1 = "[---------- Controlled within the scope of " + sLevle + " begin ----------]";
//			String s2 = "[---------- Controlled within the scope of " + sLevle + " end   ----------]";
			
			//输出到后台
//			if ((("," + m_PrintErrorLevel + ",").indexOf("," + sLevle + ",") > -1) || (sLevle.equals(""))){
//				System.out.print(s1);
				e.printStackTrace();
//				System.out.print(s2);
//			}
			
//			//输出到文件
//			if ((("," + m_WriteErrorLevel + ",").indexOf("," + sLevle + ",") > -1) || (sLevle.equals(""))){
//				//
//				if (!m_ErrorSavePath.substring(m_ErrorSavePath.length()).equals("\\")) 
//					m_ErrorSavePath = m_ErrorSavePath + "\\"; 
//				//
//				FileWriter resultsFile = new FileWriter(m_ErrorSavePath + m_DebugClassName + "_" + funcejb.getTime("yyyyMMddHH") + ".txt", true);
//			    PrintWriter toFile = new PrintWriter(resultsFile);
//			    toFile.write(s1 + "\r\n");
//			    toFile.write("[" + funcejb.getTime("") + "]" + e.toString() + "\r\n");
//			    for (int i = 0; i < e.getStackTrace().length; i++){
//			    	toFile.write("[" + funcejb.getTime("") + "]" 
//			    					+ "	at " + e.getStackTrace()[i].getClassName() 
//			    					+ "." + e.getStackTrace()[i].getMethodName() 
//			    					+ "(" + e.getStackTrace()[i].getFileName()
//			    					+ ":" + e.getStackTrace()[i].getLineNumber() + ")"
//			    					+ "\r\n");
//			    }
//			    toFile.write(s2 + "\r\n");
//			    toFile.close();
//			    resultsFile.close();
//			}
		
		}catch(Exception ee){
			System.out.print("输出错误信息时出错," + ee.toString());
			ee.printStackTrace();
		}
		
	}

	/*
	 * 显示可容的错误到后台
	 * 级别可自由分类,只要是配置串中包含级别串的, 均会输出, 如debug, key, warning, error  
	 */
	public static void outs(String s){
		outs(s, "");
	}

	public static void outs(String s, String sLevle){
		try{
//			if (IsLoadConfig == null) return;
//			if (IsLoadConfig.equals("false")) return;
			
			//输出到后台
//			if ((("," + m_PrintErrorLevel + ",").indexOf("," + sLevle + ",") > -1) || (sLevle.equals(""))){
				System.out.print(s); 
//			}
			
//			s = "[" + funcejb.getTime("") + "]" + s;
//			//输出到文件
//			if ((("," + m_WriteErrorLevel + ",").indexOf("," + sLevle + ",") > -1) || (sLevle.equals(""))){
//				//
//				if (!m_ErrorSavePath.substring(m_ErrorSavePath.length()).equals("\\")) 
//					m_ErrorSavePath = m_ErrorSavePath + "\\"; 
//				//
//				FileWriter resultsFile = new FileWriter(m_ErrorSavePath + m_DebugClassName + "_" + funcejb.getTime("yyyyMMddHH") + ".txt", true);
//			    PrintWriter toFile = new PrintWriter(resultsFile);
//			    toFile.write(s + "\r\n"); 
//			    toFile.close();
//			    resultsFile.close();
//			}
		
		}catch(Exception ee){
			System.out.print("输出错误信息时出错," + ee.toString());
			ee.printStackTrace();
		}
		
	}

}
