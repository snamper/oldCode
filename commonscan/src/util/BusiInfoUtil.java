package util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jinrl_exploit_Po.TbusiField;
import jinrl_exploit_Po.TbusiInfo;
import jinrl_exploit_common.fc;

/**
 * 类名称：BusiInfoUtil   
 * 类描述：
 * 创建人: renfy   
 * 创建时间：2011-11-30 上午11:14:04   
 * @version 1.0
 *  
 */
public class BusiInfoUtil {
	
	/**
	 * 
	 * 方法名称: replaceStar 
	 * 方法描述: 替换“*”为具体的查询字段
	 * 创建人: renfy
	 * 创建时间: 2011-11-30 上午11:21:10
	 * @param sql 需要替换的SQL
	 * @param list 包含的字段
	 * @param pkid 主键
	 * @return
	 * @version 1.0
	 *
	 */
	public static StringBuffer replaceStar(StringBuffer sql, List<TbusiField> list, String pkid){
		
		String allSelect = fc.getString(sql.toString().toUpperCase(), "SELECT ", " FROM ").trim();
		
		int repleceStar = 0;//是否替换标示
		int starIndex = 0;//星号位置
		
		if("*".equals(allSelect)){
			repleceStar = 1;
		}else{
			starIndex = allSelect.indexOf("*");
			if(starIndex > -1){//包含星号
				String endSelect = allSelect.substring(starIndex + 1).trim();
				if("".equals(endSelect) || endSelect.indexOf(",") == 0){//是星号，不是乘号
					String startSelect = allSelect.substring(0, starIndex).trim();
					if(!startSelect.endsWith(".")){//前面不是 表名.*，意思就是不支持多表联合查询,联合查询的时候，业务字段表中的字段不能分辨属于哪个表，所以暂时不支持
						repleceStar = 2;
					}
				}
			}
		}
		
		
		if(repleceStar != 0){
			//满足替换*的规则
			
			StringBuffer newSelect = new StringBuffer();
			for (int i = 0; i < list.size(); i++) {
				TbusiField currentField = list.get(i);
				
				String istruefield = currentField.getFistruefield();
				
				//如果为1，代表不是库里面实际有的字段.
				if(!"1".equals(istruefield)){
					newSelect.append("," + currentField.getFfieldname());
				}
				
			}
			
			newSelect.append("," + pkid);
			String newAllSelect = allSelect.substring(0, starIndex) + newSelect.substring(1) + allSelect.substring(starIndex + 1);
			String replece_sql = "SELECT " + newAllSelect + sql.toString().substring(sql.toString().indexOf(" FROM "));
			sql = new StringBuffer(replece_sql);
			
		}
		
		return sql;
		
	}

	/**
	 * 方法名称: activeGroupBy 
	 * 方法描述: 判断是否是自定义group by 方式，是：修改sql
	 * 创建人: renfy
	 * 创建时间: 2011-11-30 上午11:30:03
	 * @param sql
	 * @param busiInfo
	 * @return
	 * @version 1.0
	 * 
	 */ 
	public static StringBuffer activeGroupBy(StringBuffer sql, TbusiInfo busiInfo, HttpServletRequest request) {
		String activeGroupBy = fc.changNull(busiInfo.getFactivegroup()).trim();
		String activeGroupByValue = fc.changNull(busiInfo.getFactivegroupvalue()).trim();
		if(!"".equals(activeGroupBy) && !"".equals(activeGroupByValue)){
			
			String[] agbs = activeGroupBy.split("\\|");
			
			//查找所有页面传递过来的查询项
			List<String> selectList = getActiveGroupField(busiInfo, request);
			
			//如果为空，普通查询,不为空，统计查询
			if(!selectList.isEmpty()){
			
				//拼接sql
				String allSelect = fc.getString(sql.toString().toUpperCase(), "SELECT ", " FROM ").trim();
				
				
				//拼接所有的分类项字段
				StringBuffer selects = new StringBuffer();
				for(String sel : selectList){
					selects.append("," + sel);
				}
				
				sql = new StringBuffer(fc.replace(sql.toString().toUpperCase(), allSelect, activeGroupByValue + selects));
				
				if(selects.length() > 1){
					sql.append(" group by ");
					sql.append(selects.substring(1));
					sql.append(" order by ");
					sql.append(selects.substring(1));
				}
			}
			
			//用于页面显示
			request.setAttribute("activeGroupSelectList", selectList);
			request.setAttribute("activeGroupAllArray", agbs);
				
			
		}
		return sql;
	}
	
	public static List<String> getActiveGroupField(TbusiInfo busiInfo, HttpServletRequest request) {
		List<String> selectList = new ArrayList<String>();
		String activeGroupBy = fc.changNull(busiInfo.getFactivegroup()).trim();
		if(!"".equals(activeGroupBy)){
			
			String[] agbs = activeGroupBy.split("\\|");
			
			//查找所有页面传递过来的查询项
			for(String agb : agbs){
				String value = fc.getpv(request, "activeGroupBy_" + agb);
				if("true".equals(value)){
					selectList.add(agb);
				}
			}
		}
		return selectList;
	}
}
