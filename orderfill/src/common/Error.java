package common;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/*
 * 错误信息处理类(静态类)
 */

public final class Error {
	


	/*
	 * 显示可容的错误到后台
	 * 级别可自由分类,只要是配置串中包含级别串的, 均会输出, 如debug, warning, error  
	 */
	static public void oute(Exception e){
		oute(e, "error");
	}

	
	/*
	 * 显示可容的错误到后台
	 * 级别可自由分类,只要是配置串中包含级别串的, 均会输出, 如debug, warning, error  
	 */
	static public void oute(Exception e, String sLevle){
		try{
				e.printStackTrace();
		
		}catch(Exception ee){
			System.out.print("输出错误信息时出错," + ee.toString());
			ee.printStackTrace();
		}
		
	}

	/*
	 * 显示可容的错误到后台
	 * 级别可自由分类,只要是配置串中包含级别串的, 均会输出, 如debug, warning, error  
	 */
	static public void outs(String s){
		outs(s, "debug");
	}

	static public void outs(String s, String sLevle){
		try{
			System.out.print(s);
		}catch(Exception ee){
			System.out.print("输出错误信息时出错," + ee.toString());
			ee.printStackTrace();
		}
		
	}

}
