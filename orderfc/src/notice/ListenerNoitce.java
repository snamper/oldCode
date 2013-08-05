package notice;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;


	public class ListenerNoitce extends HttpServlet implements ServletContextListener { 
	/**
	<listener>
	<listener-class>notice.ListenerNoitce</listener-class>
	</listener>
	*/
	private static final long serialVersionUID = 1L; 
	public ListenerNoitce() { 
	   } 

	   private java.util.Timer timer = null; 
	   public void contextInitialized(ServletContextEvent event) { 
		     timer = new java.util.Timer(true); 
         //
	     event.getServletContext().log("订单结果通知检测已启动"); 
	     timer.schedule(new ReNotice(), 1 * 1000, 5 * 1000); //执行间隔5秒
	   
	   } 

	   public void contextDestroyed(ServletContextEvent event) { 
	     timer.cancel(); 
//	     System.out.println("定时器销�?); 
//	     event.getServletContext().log("定时器销�?); 
	   } 
}