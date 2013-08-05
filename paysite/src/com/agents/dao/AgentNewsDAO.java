package com.agents.dao;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agents.pojo.AgentNews;

import com.agents.bean.BaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * AgentNews entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 *
 * @see com.jinrl.pojo.AgentNews
 * @author MyEclipse Persistence Tools
 */

public class AgentNewsDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(AgentNewsDAO.class);
	// property constants
	public static final String FAGENT_ID = "fagentId";
	public static final String FTITLE = "ftitle";
	public static final String FAUTHOR = "fauthor";
	public static final String FCONTENT = "fcontent";
	public static final String FTYPE = "ftype";
	public static final String FSTATE = "fstate";
	public static final String FSTATE_RATE = "fstateRate";

	public void save(AgentNews transientInstance) {
		log.debug("saving AgentNews instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(AgentNews persistentInstance) {
		log.debug("deleting AgentNews instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public AgentNews findById(java.lang.Integer id) {
		log.debug("getting AgentNews instance with id: " + id);
		try {
			AgentNews instance = (AgentNews) getSession().get(
					"com.jinrl.pojo.AgentNews", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(AgentNews instance) {
		log.debug("finding AgentNews instance by example");
		try {
			List results = getSession().createCriteria(
					"com.jinrl.pojo.AgentNews").add(Example.create(instance))
					.list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding AgentNews instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from AgentNews as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByFagentId(Object fagentId) {
		return findByProperty(FAGENT_ID, fagentId);
	}

	public List findByFtitle(Object ftitle) {
		return findByProperty(FTITLE, ftitle);
	}

	public List findByFauthor(Object fauthor) {
		return findByProperty(FAUTHOR, fauthor);
	}

	public List findByFcontent(Object fcontent) {
		return findByProperty(FCONTENT, fcontent);
	}

	public List findByFtype(Object ftype) {
		return findByProperty(FTYPE, ftype);
	}

	public List findByFstate(Object fstate) {
		return findByProperty(FSTATE, fstate);
	}

	public List findByFstateRate(Object fstateRate) {
		return findByProperty(FSTATE_RATE, fstateRate);
	}

	public List findAll() {
		log.debug("finding all AgentNews instances");
		try {
			String queryString = "from AgentNews";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public AgentNews merge(AgentNews detachedInstance) {
		log.debug("merging AgentNews instance");
		try {
			AgentNews result = (AgentNews) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(AgentNews instance) {
		log.debug("attaching dirty AgentNews instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(AgentNews instance) {
		log.debug("attaching clean AgentNews instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public List<AgentNews> findByAgentID(String agentID){
		Session session = getSession().getSessionFactory().openSession();
		Transaction tr = session.beginTransaction();
		String hql = "from AgentNews a where a.fagentId=? and a.fstate<>'0' order by a.fstate,a.ftime desc";
		List<AgentNews> list = session.createQuery(hql).setString(0, agentID).setFirstResult(0).setMaxResults(5).list();
		tr.commit();
		session.close();
		return list;
	}

	public AgentNews findByIdAndAgentID(int i, String username) {
		Session session = getSession().getSessionFactory().openSession();
		Transaction tr = session.beginTransaction();
		String hql = "from AgentNews a where a.id=? and a.fagentId=? and a.fstate<>'0'";
		AgentNews an = (AgentNews)session.createQuery(hql).setInteger(0, i).setString(1, username).uniqueResult();
		tr.commit();
		session.close();
		return an;
	}
}