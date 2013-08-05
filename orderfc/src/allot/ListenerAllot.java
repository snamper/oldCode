package allot;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;


	public class ListenerAllot extends HttpServlet implements ServletContextListener { 
		/**
<listener>
<listener-class>allot.ListenerAllot</listener-class>
</listener>
		*/
	private static final long serialVersionUID = 1L;
	static boolean isrun = false;

	public ListenerAllot() { 
	   } 

	   private java.util.Timer timer = null; 
	   public void contextInitialized(ServletContextEvent event) { 
		     timer = new java.util.Timer(true); 
         // 
	     event.getServletContext().log("[卡密充值超时模块]已启动"); 
	     timer.schedule(new ReAllot(), 3*1000, 2 * 1000); //2秒钟走一次
	   
	   } 

	   public void contextDestroyed(ServletContextEvent event) { 
	     timer.cancel(); 
	   } 
}