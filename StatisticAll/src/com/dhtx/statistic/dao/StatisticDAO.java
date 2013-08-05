package com.dhtx.statistic.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.dhtx.statistic.util.DataConnect;
import com.dhtx.statistic.util.fc;

/**
 * 类名称：StatisticDAO   
 * 类描述：
 * 创建人: renfy   
 * 创建时间：2011-12-16 下午03:58:55   
 * @version 1.0
 *  
 */
public class StatisticDAO {
	
	private static String date = null;
	private static String startTime = null;
	private static String endTime = null;
	
	public StatisticDAO(){
		date = fc.getTime("yyyy-MM-dd",-24*60);
		startTime = date + " 00:00:00.000";
		endTime = date + " 23:59:59.999";
	}

	/**
	 * 方法名称: getHQPlatformMoney 
	 * 方法描述: 华奇的
	 * 创建人: renfy
	 * 创建时间: 2011-12-16 下午04:01:23
	 * @return
	 * @version 1.0
	 * 
	 */ 
	public List<String[]> getHQPlatformMoney() {
		String hqsql = "SELECT fname,isnull(fmoney,0) as fmoney,ftitle FROM MonitorPlatform where ftitle='华奇' and fmoney<>0 and fstate='0' order by ftitle";
		List<String[]> hqvalue =  getAllValue("AccountPool",hqsql,3);
		return hqvalue;
	}
	
	/**
	 * 
	 * 方法名称: getZHCPlatformMoney 
	 * 方法描述: 账号池的,和getHQPlatformMoney是一个方法来着，现在拆分开了
	 * 创建人: renfy
	 * 创建时间: 2011-12-29 上午09:53:57
	 * @return
	 * @version 1.0
	 *
	 */
	public List<String[]> getZHCPlatformMoney() {
		String zhcsql = "SELECT fname,isnull(fmoney,0) as fmoney,ftitle FROM MonitorPlatform where ftitle='帐号池' and fmoney<>0 and fstate='0' order by ftitle";
		List<String[]> zhcvalue = getAllValue("AccountPool",zhcsql,3);
		return zhcvalue;
	}

	/**
	 * 方法名称: getQBMoney 
	 * 方法描述: 
	 * 创建人: renfy
	 * 创建时间: 2011-12-16 下午04:02:07
	 * @return
	 * @version 1.0
	 * 
	 */ 
	public List<String[]> getQBMoney() {
		String sql = "SELECT fGiveClientId,SUM(fFactMoney) as a1 FROM tbAccount where fCreateTime between '"+startTime+"' and '"+endTime+"' and fInsideId='05' and fState='充值成功'  group by fGiveClientId order by fGiveClientId";
		List<String[]> value = getAllValue("AccountPool",sql,2);
		String csql = "select fName,fType from LowerClient where fState = '启用' order by fname";
		List<String[]> changeValue = getAllValue("AccountPool",csql,2);
		
		changeFirstValue(value,changeValue);
		
		return value;
	}



	/**
	 * 方法名称: getHuaQiMoney 
	 * 方法描述: 
	 * 创建人: renfy
	 * 创建时间: 2011-12-16 下午04:02:47
	 * @return
	 * @version 1.0
	 * 
	 */ 
	public List<String[]> getHuaQiMoney() {
		String sql = "SELECT fGiveID,SUM(fFactMoney) as a1 FROM AcOrder where fCreateTime between '"+startTime+"' and '"+endTime+"' and fState='4' and fInsideID in ('01','04','12') group by fGiveID order by fGiveID";
		List<String[]> value = getAllValue("Recharge",sql,2);
		
		String csql = "select a.fID+'-'+b.fID,a.fName+'-'+b.fName from  AcPlatform as a full join CoProductType as b on 1=1";
		List<String[]> changeValue = getAllValue("Recharge",csql,2);
		
		changeFirstValue(value,changeValue);
		
		return value;
	}

	/**
	 * 方法名称: getCFTMoney 
	 * 方法描述: 
	 * 创建人: renfy
	 * 创建时间: 2011-12-16 下午04:03:38
	 * @return
	 * @version 1.0
	 * 
	 */ 
	public List<String[]> getCFTMoney() {
		String sql = "SELECT fGiveID,SUM(fFactMoney) as a1 FROM AcOrder where fCreateTime between '"+startTime+"' and '"+endTime+"' and fState='4' group by fGiveID order by fGiveID";
		List<String[]> value = getAllValue("RechargeCft",sql,2);
		
		String csql = "select a.fID+'-'+b.fID,a.fName+'-'+b.fName from  AcPlatform as a full join CoProductType as b on 1=1";
		List<String[]> changeValue = getAllValue("RechargeCft",csql,2);
		
		changeFirstValue(value,changeValue);
		
		return value;
	}
	
	/**
	 * 
	 * 方法名称: getHQkcCardMoney 
	 * 方法描述: 获得华奇库存卡面值总额
	 * 创建人: renfy
	 * 创建时间: 2011-12-31 下午12:17:37
	 * @return
	 * @version 1.0
	 *
	 */
	public String getHQkcCardMoney(){
		String sql = "select  sum(fPrice) as a1 from CaOrderKck where fState='0'";
		return getSelectOneFieldValue("Recharge", sql);
	}
	
	/**
	 * 
	 * 方法名称: getCFTkcCardMoney 
	 * 方法描述: 获得财付通库存卡面值总额
	 * 创建人: renfy
	 * 创建时间: 2011-12-31 下午12:20:03
	 * @return
	 * @version 1.0
	 *
	 */
	public String getCFTkcCardMoney(){
		String sql = "select  sum(fPrice) as a1 from CaOrderKck where fState='0'";
		return getSelectOneFieldValue("RechargeCft", sql);
	}
	
	/**
	 * 
	 * 方法名称: getSelectOneFieldValue 
	 * 方法描述: 
	 * 创建人: renfy
	 * 创建时间: 2011-12-16 下午04:30:49
	 * @param sJndiName
	 * @param sql
	 * @return
	 * @version 1.0
	 *
	 */
	public String getSelectOneFieldValue(String sJndiName, String sql) {
		DataConnect dc = new DataConnect(sJndiName, false);
		ResultSet rs = null;
		String value = "";
		try {
			rs = dc.query(sql);
			if(rs != null && rs.next()){
				value = rs.getString(1);
			}
		} catch (Exception e) {
			System.out.println("总结统计查询出错,SQL:"+sql);
			e.printStackTrace();
		}
		dc.CloseResultSet(rs);
		dc.CloseConnect();
		return value == null ? "" : value;
	}
	
	/**
	 * 
	 * 方法名称: getAllValue 
	 * 方法描述: 
	 * 创建人: renfy
	 * 创建时间: 2011-12-29 上午10:18:28
	 * @param sJndiName
	 * @param sql
	 * @param fieldNum 查询的字段的个数
	 * @return
	 * @version 1.0
	 *
	 */
	public List<String[]> getAllValue(String sJndiName, String sql, int fieldNum) {
		DataConnect dc = new DataConnect(sJndiName, false);
		ResultSet rs = null;
		List<String[]> result = new ArrayList<String[]>();
		try {
			rs = dc.query(sql);
			while(rs != null && rs.next()){
				String[] sv = new String[fieldNum];
				for(int i = 0; i < fieldNum; i++){
					sv[i] = fc.changNull(rs.getString(i+1));
				}
				result.add(sv);
			}
		} catch (Exception e) {
			System.out.println("总结统计查询出错,SQL:"+sql);
			e.printStackTrace();
		}
		dc.CloseResultSet(rs);
		dc.CloseConnect();
		return result;
	}
	
	/**
	 * 方法名称: changeFirstValue 
	 * 方法描述: 
	 * 创建人: renfy
	 * 创建时间: 2011-12-29 下午01:01:25
	 * @param value
	 * @param changeValue
	 * @version 1.0
	 * 
	 */ 
	private void changeFirstValue(List<String[]> value,
			List<String[]> changeValue) {
		if(!value.isEmpty() && !changeValue.isEmpty()){
			for(int i = 0; i <value.size(); i++){
				String[] str = value.get(i);
				for(String[] cs : changeValue){
					if(str[0].equalsIgnoreCase(cs[0])){
						str[0] = cs[1];
						break;
					}
				}
			}
		}
		
	}

}
