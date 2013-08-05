package com.agents.util;

import org.jdom.Element;

import com.agents.pojo.CoAgent;

/**
 * 类名称：CreateAgentObj   
 * 类描述：
 * 创建人: renfy   
 * 创建时间：2011-12-23 下午01:19:44   
 * @version 1.0
 *  
 */
public class CreateAgentObj {

	/**
	 * 方法名称: createAgent 
	 * 方法描述: 
	 * 创建人: renfy
	 * 创建时间: 2011-12-23 下午01:20:39
	 * @return
	 * @version 1.0
	 * 
	 */ 
	public static CoAgent createAgent() {
		Element e= Config.getConfigElement("showPage");
		
		if(e == null){
			return null;
		}
		
		String id = e.getChildText("id");
		String name = e.getChildText("name");
		String domainName = e.getChildText("domainName");
		String domainAgent = e.getChildText("domainAgent");
		String giveIP = e.getChildText("giveIP");
		String menuConfig = e.getChildText("menuConfig");
		String styleConfig = e.getChildText("styleConfig");
		String masterCSS = e.getChildText("masterCSS");
		String leftCSS = e.getChildText("leftCSS");
		String topCSS = e.getChildText("topCSS");
		String photoName = e.getChildText("photoName");
		String jsName = e.getChildText("jsName");
		String contentCSS = e.getChildText("contentCSS");
		String login = e.getChildText("login");
		
		//
		CoAgent agent = new CoAgent();
		agent.setFid(id);
		agent.setFname(name);
		agent.setFdomainName(domainName);
		agent.setFdomainAgent(domainAgent);
		agent.setFgiveIP(giveIP);
		agent.setfMenuConfig(menuConfig);
		agent.setfStyleConfig(styleConfig);
		agent.setFmasterCSS(masterCSS);
		agent.setFleftCSS(leftCSS);
		agent.setFtopCSS(topCSS);
		agent.setFphotoName(photoName);
		agent.setFjsName(jsName);
		agent.setFcontentCSS(contentCSS);
		agent.setFlogin(login);
		
		return agent;
	}

}
