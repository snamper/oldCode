package check.v1;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;




public class ListenerCheck extends HttpServlet implements ServletContextListener { 
		/**
<listener>
<listener-class>check.ListenerCheck</listener-class>
</listener>
		*/
	private static final long serialVersionUID = 1L;
	static boolean isrun = false;

	public ListenerCheck() { 
	   } 

	   private java.util.Timer timer = null; 
	   public void contextInitialized(ServletContextEvent event) { 
		     timer = new java.util.Timer(true); 
         //
	     event.getServletContext().log("[账号退款服务]已启动"); 
	     timer.schedule(new ReCheck(), 1 * 1000, 1 * 1000); //1秒钟走一次
	   
	   } 

	   public void contextDestroyed(ServletContextEvent event) { 
	     timer.cancel(); 
//	     System.out.println("定时器销毁"); 
//	     event.getServletContext().log("定时器销毁"); 
	   } 
}