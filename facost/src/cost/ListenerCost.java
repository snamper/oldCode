package cost;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

/**
 * listener implementation class for listener: ListenerAllot
 *
 * @web.listener 
 *  
 */ 
public class ListenerCost extends HttpServlet implements ServletContextListener {  
		/**
<listener>
<listener-class>cost.Listener</listener-class>
</listener>
		*/
	private static final long serialVersionUID = 1L;
	static boolean isrun = false;

	public ListenerCost() { 
	   } 

	   private java.util.Timer timer = null; 
	   public void contextInitialized(ServletContextEvent event) { 
		     timer = new java.util.Timer(true); 
         //
	     event.getServletContext().log("[账号退款服务]已启动"); 
	     timer.schedule(new ReSend(), 5 * 1000, 5 * 1000); //5秒钟走一次
	   
	   } 

	   public void contextDestroyed(ServletContextEvent event) { 
	     timer.cancel(); 
//	     System.out.println("定时器销毁"); 
//	     event.getServletContext().log("定时器销毁"); 
	   } 
}