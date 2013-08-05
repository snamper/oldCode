package com.agents.struts.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.agents.service.ClientInfoService;
import com.agents.pojo.CoAgent;

/**
 * MyEclipse Struts
 * Creation date: 04-28-2011  
 *
 * XDoclet definition:
 * @struts.action path="/clientInfo" name="clientInfoForm" scope="request"
 */
public class AgentInfoAction extends DispatchAction {
	/*
	 * Generated Methods
	 */

	/**
	 */

	public ActionForward findMain(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		CoAgent agent = (CoAgent)session.getAttribute("currentAgent");
		String angentID =agent.getFid();
		ClientInfoService cinfoService = new ClientInfoService();
		String[] agentInfo = cinfoService.findAgent(angentID);
		session.setAttribute("agentInfo",agentInfo);
		return mapping.findForward("findMain");
	}

}