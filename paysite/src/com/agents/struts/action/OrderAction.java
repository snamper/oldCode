package com.agents.struts.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.agents.service.OrderService;
import com.agents.struts.actionform.OrderActionForm;

public class OrderAction extends DispatchAction {
	private OrderService orderService=new OrderService();
	

	public ActionForward findOrderList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		  OrderActionForm orderForm = (OrderActionForm) form ;
		  String key=request.getParameter("key");
		  String type=request.getParameter("type");
		  String endtime=request.getParameter("endtime");
		  String starttime=request.getParameter("starttime");
		  if (type==null) {
			  key="";
			  type="all";  
			  starttime="";
			  endtime="";
			
		}
		int page = 1;
		try{
			if(orderForm.getNopage() != null){ 
				page = orderForm.getNopage();
			}
		}catch(Exception e){
		}   
		List<String[]> list = orderService.findOrderList(page,key,type,starttime,endtime) ;
		int noCount = orderService.noCount;
		
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
		request.setAttribute("orders", list) ;
		return mapping.findForward("orderList") ;      
	}   

}
