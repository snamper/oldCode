package module;

import java.util.Hashtable;
import java.util.Map;

public class AcAgentProductMap {
	
	private static Map<String, String[]> agentProductMap = new Hashtable<String, String[]>();
	
	/**
	 * 检验是否存在当前商品
	 * @param productID
	 * @return
	 */
	public static boolean checkKey(String AgentID_fProductID){
		
		if(agentProductMap.containsKey(AgentID_fProductID)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 根据商品ID获得当前商品
	 * @param productID
	 * @return
	 */
	public static String[] getValue(String AgentID_fProductID){
		syso("agentProductMap.size()=" + agentProductMap.size());
		syso("读取AcAgentProductMap中的值..."+ AgentID_fProductID);
		return agentProductMap.get(AgentID_fProductID);
	}
	
	/**
	 * 存入当前商品
	 * @param productID
	 * @param productInfo
	 */
	public static void setValue(String AgentID_fProductID, String[] productInfo){
		agentProductMap.put(AgentID_fProductID, productInfo);
		syso("设值AcAgentProductMap中的值..."+ AgentID_fProductID);
	}
	
	public static void removeAll(){
		agentProductMap.clear();
	}
	
	private static void syso(String msg){
//		System.out.println(msg);
	}

}
