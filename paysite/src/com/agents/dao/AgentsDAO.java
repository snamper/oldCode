package com.agents.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.agents.bean.BaseHibernateDAO;


/**
 * A data access object (DAO) providing persistence and search support for
 * Agents entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 *
 * @see com.jinrl.pojo.Agents
 * @author MyEclipse Persistence Tools
 */

public class AgentsDAO extends BaseHibernateDAO {

	public List findByFdomainName(String fdomainIP) {  
		  Session session = getSession().getSessionFactory().openSession();
		  Transaction tr = session.beginTransaction();
		  String sql = "from com.agents.pojo.CoAgent ag where ag.fdomainName=?";   
		  List list =  session.createQuery(sql).setString(0, fdomainIP).list();  
		 
		  tr.commit();
		  session.close();   
		return list;
	}
}