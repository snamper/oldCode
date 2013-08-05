package test;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

/**
 * listener implementation class for listener: 
 *
 * web.listener
 *  
 */ 

public class ListenerFillTest extends HttpServlet implements ServletContextListener { 

	private static final long serialVersionUID = 1L;
	static boolean isrun = false;
 
	public ListenerFillTest() { 
	   } 

	   private java.util.Timer timer = null; 
	   public void contextInitialized(ServletContextEvent event) { 
		     timer = new java.util.Timer(true); 
	     event.getServletContext().log("[账号充值超时模块]已启动");  
         //处理等待分发订单
	     timer.schedule(new ReSendA("1", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("2", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("3", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("4", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("5", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("6", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("7", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("8", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("9", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("0", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("1", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("2", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("3", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("4", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("5", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("6", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("7", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("8", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("9", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("0", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("1", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("2", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("3", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("4", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("5", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("6", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("7", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("8", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("9", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("0", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("1", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("2", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("3", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("4", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("5", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("6", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("7", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("8", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("9", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("0", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("1", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("2", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("3", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("4", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("5", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("6", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("7", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("8", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("9", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("0", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("1", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("2", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("3", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("4", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("5", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("6", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("7", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("8", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("9", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("0", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("1", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("2", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("3", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("4", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("5", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("6", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("7", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("8", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("9", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("0", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("1", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("2", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("3", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("4", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("5", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("6", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("7", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("8", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("9", "desc"), 1 * 1000, 1 * 1000); 
	     timer.schedule(new ReSendA("0", "desc"), 1 * 1000, 1 * 1000); 
	   } 

	   public void contextDestroyed(ServletContextEvent event) { 
	     timer.cancel(); 
//	     System.out.println("定时器销毁"); 
//	     event.getServletContext().log("定时器销毁"); 
	   } 
}