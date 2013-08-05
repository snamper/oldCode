package com.jinrl.powermodule.dao;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jinrl.powermodule.pojo.Tpositionuser;

/**
 * A data access object (DAO) providing persistence and search support for
 * Tpositionuser entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.jinrl.powermodule.pojo.Tpositionuser
 * @author MyEclipse Persistence Tools
 */

public class TpositionuserDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TpositionuserDAO.class);
	// property constants
	public static final String FISUSED = "fisused";

	public void save(Tpositionuser transientInstance) {
		log.debug("saving Tpositionuser instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Tpositionuser persistentInstance) {
		log.debug("deleting Tpositionuser instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Tpositionuser findById(java.lang.String id) {
		log.debug("getting Tpositionuser instance with id: " + id);
		try {
			Tpositionuser instance = (Tpositionuser) getSession().get(
					"com.jinrl.powermodule.pojo.Tpositionuser", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Tpositionuser instance) {
		log.debug("finding Tpositionuser instance by example");
		try {
			List results = getSession().createCriteria(
					"com.jinrl.powermodule.pojo.Tpositionuser").add(
					Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Tpositionuser instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Tpositionuser as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByFisused(Object fisused) {
		return findByProperty(FISUSED, fisused);
	}

	public List findAll() {
		log.debug("finding all Tpositionuser instances");
		try {
			String queryString = "from Tpositionuser";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Tpositionuser merge(Tpositionuser detachedInstance) {
		log.debug("merging Tpositionuser instance");
		try {
			Tpositionuser result = (Tpositionuser) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Tpositionuser instance) {
		log.debug("attaching dirty Tpositionuser instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Tpositionuser instance) {
		log.debug("attaching clean Tpositionuser instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}