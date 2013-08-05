package com.agents.dao;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.agents.bean.BaseHibernateDAO;
import com.agents.pojo.Client;

public class ClientHibernateDao extends BaseHibernateDAO {



	public Client findByFname(String id){
		String sql = "from com.agents.pojo.Client cl where cl.fid='"+id+"'";
		Session session = getSession().getSessionFactory().openSession();
		Transaction tr = session.beginTransaction();
		List list = session.createQuery(sql).list();
		tr.commit();   
		session.close();
		if(list.size() == 0){
			return null;
		}else{
			return (Client)list.get(0);
		}
	}


	public Client findByFnameAndState(String id){
		String sql = "from com.agents.pojo.Client cl where cl.fid='"+id+"' and cl.fstate='0'";
		Session session = getSession().getSessionFactory().openSession();
		Transaction tr = session.beginTransaction();
		List list = session.createQuery(sql).list();
		tr.commit();
		session.close();
		if(list.size() == 0){
			return null;
		}else{
			return (Client)list.get(0);
		}
	}
	public Client findByAgentId(String agentid,String username) {
		String sql = "from com.agents.pojo.Client c where c.fagentId=? and c.fname=?";
		Session session = getSession().getSessionFactory().openSession();
		Transaction tr = session.beginTransaction();    
		List list = session.createQuery(sql).setString(0, agentid).setString(1, username).list();
		Client act = null;
		if(list.size() != 0){   
			act = (Client)list.get(0);  
		}     
		tr.commit();
		session.close();  
		return act;
	}
	
}