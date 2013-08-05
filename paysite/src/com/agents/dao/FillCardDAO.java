package com.agents.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.agents.bean.BaseHibernateDAO;
import com.agents.pojo.FillCard;
import com.agents.util.BaseDao;




public class FillCardDAO extends BaseDao {

	protected void initDao() {
		// do nothing
	}


	public List findFillCards(String datatime,String selectItemValue,String selectItem,String fcardid,int nowPage,String agentID) {
		String sql = "select fid,fCreateTime,fCardNo,fPrice,fMoney,fState from CaOrder where fAgentID='"+agentID+"' and fcreateTime between '"+datatime+" 00:00:00' and '"+datatime+" 23:59:59'";
			if(!"".equals(fcardid)){
				sql += (" and fCardNo='"+fcardid+"'");
			}

			if("fid".equals(selectItem) && !"".equals(selectItemValue)){
				sql += (" and fID='"+selectItemValue+"'");
			}

			if("forderid".equals(selectItem) && !"".equals(selectItemValue)){
				sql += (" and fOrderID='"+selectItemValue+"'");
			}

			sql += (" order by fCreateTime desc");
		 List<String[]> list = super.findList(sql, 1) ;
		return list;
	}
}