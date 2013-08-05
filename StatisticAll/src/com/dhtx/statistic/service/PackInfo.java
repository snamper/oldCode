package com.dhtx.statistic.service;

import java.util.List;

/**
 * 类名称：PackInfo   
 * 类描述：
 * 创建人: renfy   
 * 创建时间：2011-12-16 下午04:06:58   
 * @version 1.0
 *  
 */
public class PackInfo {

	/**
	 * 方法名称: packAllInfo 
	 * 方法描述: 
	 * 创建人: renfy
	 * 创建时间: 2011-12-16 下午04:08:14
	 * @param platformMoney
	 * @param qbMoney
	 * @param huaqiMoney
	 * @param caiftMoney
	 * @return
	 * @version 1.0
	 * 
	 */ 
	public String packAllInfo(List<String[]> HQplatformMoney, List<String[]> ZHCplatformMoney, List<String[]> qbMoney,
			List<String[]> huaqiMoney, List<String[]> caiftMoney,String hqkcCard,String cftcCard) {
		
		String HQplatformMoneyStr = packHQplatformMoney(HQplatformMoney);
		String ZHCplatformMoneyStr = packZHCplatformMoney(ZHCplatformMoney);
		String QBMoneyStr = packQBMoney(qbMoney);
		String HQMoneyStr = packHQMoney(huaqiMoney);
		String CFTMoneyStr = packCFTMoney(caiftMoney);
		
		StringBuffer sb = new StringBuffer("\n\n");
		sb.append(HQplatformMoneyStr + "\n\n");
		sb.append(ZHCplatformMoneyStr + "\n\n");
		sb.append(QBMoneyStr + "\n\n");
		sb.append(HQMoneyStr + "\n\n");
		sb.append(CFTMoneyStr + "\n\n");
		sb.append("华奇库存卡的库存统计为:"+hqkcCard + "\n\n");
		sb.append("财付通库存卡的库存统计为:"+cftcCard + "\n\n");
//		System.out.println(sb);
		return sb.toString();
	}

	/**
	 * 方法名称: packHQMoney 
	 * 方法描述: 
	 * 创建人: renfy
	 * 创建时间: 2011-12-29 下午01:44:41
	 * @param huaqiMoney
	 * @return
	 * @version 1.0
	 * 
	 */ 
	private String packHQMoney(List<String[]> huaqiMoney) {
		return packCommon(huaqiMoney, "华奇各渠道成功总量:");
	}

	/**
	 * 方法名称: packCFTMoney 
	 * 方法描述: 
	 * 创建人: renfy
	 * 创建时间: 2011-12-29 下午01:35:35
	 * @param caiftMoney
	 * @return
	 * @version 1.0
	 * 
	 */ 
	private String packCFTMoney(List<String[]> caiftMoney) {
		return packCommon(caiftMoney, "财付通各渠道成功总量:");
	}

	/**
	 * 方法名称: packQBMoney 
	 * 方法描述: 
	 * 创建人: renfy
	 * 创建时间: 2011-12-29 上午10:34:05
	 * @param qbMoney
	 * @return
	 * @version 1.0
	 * 
	 */ 
	private String packQBMoney(List<String[]> qbMoney) {
		return packCommon(qbMoney, "QB充值各渠道成功总量:");
	}

	/**
	 * 方法名称: packZHCMoney 
	 * 方法描述: 
	 * 创建人: renfy
	 * 创建时间: 2011-12-29 上午10:00:17
	 * @param zHCplatformMoney
	 * @return
	 * @version 1.0
	 * 
	 */ 
	private String packZHCplatformMoney(List<String[]> zHCplatformMoney) {
		return packCommon(zHCplatformMoney, "账号池各渠道资金为:");
	}

	/**
	 * 方法名称: packHQMoney 
	 * 方法描述: 
	 * 创建人: renfy
	 * 创建时间: 2011-12-29 上午10:00:11
	 * @param hQplatformMoney
	 * @return
	 * @version 1.0
	 * 
	 */ 
	private String packHQplatformMoney(List<String[]> hQplatformMoney) {
		return packCommon(hQplatformMoney, "华奇各渠道资金为:");
	}
	
	/**
	 * 
	 * 方法名称: packCommon 
	 * 方法描述: 通用的
	 * 创建人: renfy
	 * 创建时间: 2011-12-29 上午10:37:29
	 * @param zHCplatformMoney
	 * @param name
	 * @return
	 * @version 1.0
	 *
	 */
	private String packCommon(List<String[]> zHCplatformMoney,String name) {
		StringBuffer result = new StringBuffer();
		try {
			if(zHCplatformMoney != null && !zHCplatformMoney.isEmpty()){
				result.append(name + "\n");
				for(String[] str : zHCplatformMoney){
					result.append("\t" + str[0] + "：" + str[1] + "\n");
				}
			}
		} catch (Exception e) {
			System.out.println(name);
			e.printStackTrace();
		}
		return result.toString();
	}

}
