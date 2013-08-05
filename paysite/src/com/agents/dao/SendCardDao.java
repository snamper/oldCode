package com.agents.dao;

import java.util.List;

import com.agents.bean.BaseHibernateDAO;

public class SendCardDao extends BaseHibernateDAO{
	public List SendCard(String cardName,String cardNameType){
		String hql="from CardType p where p.ftype='"+cardName+"' and p.fprice='"+cardNameType+"'";
		List list=getSession().createQuery(hql).list();
		getSession().close();
		return list;
	}
}
