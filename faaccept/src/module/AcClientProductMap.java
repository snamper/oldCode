package module;

import java.util.Hashtable;
import java.util.Map;

public class AcClientProductMap {
	
	private static Map<String, String[]> clientProductMap = new Hashtable<String, String[]>();
	
	/**
	 * 检验是否存在当前商品
	 * @param productID
	 * @return
	 */
	public static boolean checkKey(String clientID_productID){
		
		if(clientProductMap.containsKey(clientID_productID)){
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
	public static String[] getValue(String clientID_productID){
		syso("clientProductMap.size()=" + clientProductMap.size());
		syso("读取AcClientProductMap中的值..." + clientID_productID);
		return clientProductMap.get(clientID_productID);
	}
	
	/**
	 * 存入当前商品
	 * @param productID
	 * @param productInfo
	 */
	public static void setValue(String clientID_productID, String[] productInfo){
		clientProductMap.put(clientID_productID, productInfo);
		syso("设值到AcClientProductMap中..." + clientID_productID);
	}
	
	public static void removeAll(){
		clientProductMap.clear();
	}
	
	private static void syso(String msg){
//		System.out.println(msg);
	}

}
