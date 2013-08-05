package module;

import java.util.Hashtable;
import java.util.Map;

public class AcProductMap {
	
	private static Map<String, String[]> productMap = new Hashtable<String, String[]>();
	
	/**
	 * 检验是否存在当前商品
	 * @param productID
	 * @return
	 */
	public static boolean checkKey(String productID){
		
		if(productMap.containsKey(productID)){
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
	public static String[] getValue(String productID){
		syso("productMap.size()=" + productMap.size());
		syso("读取AcProductMap中的值..." + productID);
		return productMap.get(productID);
	}
	
	/**
	 * 存入当前商品
	 * @param productID
	 * @param productInfo
	 */
	public static void setValue(String productID, String[] productInfo){
		productMap.put(productID, productInfo);
		syso("设值到AcProductMap中..." + productID);
	}
	
	public static void removeAll(){
		productMap.clear();
		syso(" 清空productMap ");
	}
	
	
	private static void syso(String msg){
//		System.out.println(msg);
	}

}
