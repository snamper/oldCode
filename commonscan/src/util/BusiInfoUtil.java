package util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jinrl_exploit_Po.TbusiField;
import jinrl_exploit_Po.TbusiInfo;
import jinrl_exploit_common.fc;

/**
 * �����ƣ�BusiInfoUtil   
 * ��������
 * ������: renfy   
 * ����ʱ�䣺2011-11-30 ����11:14:04   
 * @version 1.0
 *  
 */
public class BusiInfoUtil {
	
	/**
	 * 
	 * ��������: replaceStar 
	 * ��������: �滻��*��Ϊ����Ĳ�ѯ�ֶ�
	 * ������: renfy
	 * ����ʱ��: 2011-11-30 ����11:21:10
	 * @param sql ��Ҫ�滻��SQL
	 * @param list �������ֶ�
	 * @param pkid ����
	 * @return
	 * @version 1.0
	 *
	 */
	public static StringBuffer replaceStar(StringBuffer sql, List<TbusiField> list, String pkid){
		
		String allSelect = fc.getString(sql.toString().toUpperCase(), "SELECT ", " FROM ").trim();
		
		int repleceStar = 0;//�Ƿ��滻��ʾ
		int starIndex = 0;//�Ǻ�λ��
		
		if("*".equals(allSelect)){
			repleceStar = 1;
		}else{
			starIndex = allSelect.indexOf("*");
			if(starIndex > -1){//�����Ǻ�
				String endSelect = allSelect.substring(starIndex + 1).trim();
				if("".equals(endSelect) || endSelect.indexOf(",") == 0){//���Ǻţ����ǳ˺�
					String startSelect = allSelect.substring(0, starIndex).trim();
					if(!startSelect.endsWith(".")){//ǰ�治�� ����.*����˼���ǲ�֧�ֶ�����ϲ�ѯ,���ϲ�ѯ��ʱ��ҵ���ֶα��е��ֶβ��ֱܷ������ĸ���������ʱ��֧��
						repleceStar = 2;
					}
				}
			}
		}
		
		
		if(repleceStar != 0){
			//�����滻*�Ĺ���
			
			StringBuffer newSelect = new StringBuffer();
			for (int i = 0; i < list.size(); i++) {
				TbusiField currentField = list.get(i);
				
				String istruefield = currentField.getFistruefield();
				
				//���Ϊ1�������ǿ�����ʵ���е��ֶ�.
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
	 * ��������: activeGroupBy 
	 * ��������: �ж��Ƿ����Զ���group by ��ʽ���ǣ��޸�sql
	 * ������: renfy
	 * ����ʱ��: 2011-11-30 ����11:30:03
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
			
			//��������ҳ�洫�ݹ����Ĳ�ѯ��
			List<String> selectList = getActiveGroupField(busiInfo, request);
			
			//���Ϊ�գ���ͨ��ѯ,��Ϊ�գ�ͳ�Ʋ�ѯ
			if(!selectList.isEmpty()){
			
				//ƴ��sql
				String allSelect = fc.getString(sql.toString().toUpperCase(), "SELECT ", " FROM ").trim();
				
				
				//ƴ�����еķ������ֶ�
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
			
			//����ҳ����ʾ
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
			
			//��������ҳ�洫�ݹ����Ĳ�ѯ��
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
