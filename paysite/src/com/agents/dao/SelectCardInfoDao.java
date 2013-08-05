package com.agents.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.agents.bean.BaseHibernateDAO;
import com.agents.bean.HibernateSessionFactory;
import com.agents.pojo.FillCard;


public class SelectCardInfoDao extends BaseHibernateDAO{
	public int SelectCardInfo(String username,String times,String times1,String cardType,String cardInfo,String orderId,String cardNO){
		String s="";
		Session session = HibernateSessionFactory.getSession();

		s=" fClient='"+username+"' and fCreateTime>='"+times+" 00:00:00' and fCreateTime<='"+times1+" 23:59:59'";
		if( !"".equals(cardType) && cardType != null) s = s + " and fCardTypeId like '"+cardType+"%'";
		if( !"".equals(cardInfo) && cardInfo != null && !cardInfo.equals("null")) s = s + " and fState = '"+cardInfo+"'";
		if( !"".equals(orderId) && orderId != null) s = s + " and fOrderId = '"+orderId+"'";
		if( !"".equals(cardNO) && cardNO != null) s = s + " and fCardId = '"+cardNO+"'";

		//Query query = session.createQuery(hql);
		 List list = null;
		 String hql="select count(id) from FillCard where "+s;
		   // String hql="select rowcnt from sysindexes where id=object_id('FillCard') and indid=1 and "+s;
		    Query query=session.createSQLQuery(hql);
			query.setCacheable(true);
			//query.setFirstResult(0);
			//query.setMaxResults(30000);
			//int count=((BigInteger)query.uniqueResult()).intValue();
			int count=(Integer)query.uniqueResult();
			session.close();
			return count;
	}
	public List SelectCardInfo(String username,int pageNO,String times,String times1,String cardType,String cardInfo,String orderId,String cardNO){
		String s="";
		s=" fClient='"+username+"' and fCreateTime>='"+times+" 00:00:00' and fCreateTime<='"+times1+" 23:59:59'";
		if(!"".equals(cardType) && cardType != null) s = s + " and fCardTypeId like '"+cardType+"%'";
		if(!"".equals(cardInfo) && cardInfo != null && !cardInfo.equals("null")) s = s + " and fState = '"+cardInfo+"'";
		if(!"".equals(orderId) && orderId != null) s = s + " and fOrderId = '"+orderId+"'";
		if(!"".equals(cardNO) && cardNO != null) s = s + " and fCardId = '"+cardNO+"'";
		String hql="from ty_PO.FillCard where"+s+" order by fCreateTime desc";
		//System.out.println(hql);
		List list=null;
		try{
			int beginNumber = 15*(pageNO -1);
			if(beginNumber>=0){
				list =getSession().createQuery(hql).setFirstResult(beginNumber).setMaxResults(15).list();
			}else{
				beginNumber=0;
				list =getSession().createQuery(hql).setFirstResult(beginNumber).setMaxResults(15).list();

			}

		}catch(Exception e){
			e.printStackTrace();
		}
		getSession().close();
		return list;
	}


	public String sumByDay(String times,String times1,String username){
		String sql="select sum(fMoney) from FillCard where fclient='"+username+"' and fcreateTime>='"+times+" 00:00:00' and fcreateTime<='"+times1+" 23:59:59'";
		String num="";
		Query query=getSession().createSQLQuery(sql);
		try{
			num=query.uniqueResult().toString();
		}catch(Exception e){
			num="0.0";
		}
		getSession().close();
		return num;
	}
	public String sumMoney(String times,String times1,String username){
		String sql="select sum(fFactMoney) from FillCard where fclient='"+username+"' and fcreateTime>='"+times+" 00:00:00' and fcreateTime<='"+times1+" 23:59:59'";
		String num="";
		Query query=getSession().createSQLQuery(sql);
		try{
			num=query.uniqueResult().toString();
		}catch(Exception e){
			num="0.0";
		}
		getSession().close();
		return num;
	}

	public FillCard findById(String id){
		Session session = getSession().getSessionFactory().openSession();
		Transaction tr = session.beginTransaction();
		String sql = "from ty_PO.FillCard where id=?";
		FillCard fc = (FillCard)session.createQuery(sql).setString(0, id).uniqueResult();
		tr.commit();
		session.close();
		return fc;
	}
}