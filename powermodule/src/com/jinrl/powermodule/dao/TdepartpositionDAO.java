package com.jinrl.powermodule.dao;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jinrl.powermodule.pojo.Tdepartposition;

/**
 * A data access object (DAO) providing persistence and search support for
 * Tdepartposition entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.jinrl.powermodule.pojo.Tdepartposition
 * @author MyEclipse Persistence Tools
 */

public class TdepartpositionDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TdepartpositionDAO.class);
	// property constants
	public static final String FISUSED = "fisused";

	public void save(Tdepartposition transientInstance) {
		log.debug("saving Tdepartposition instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Tdepartposition persistentInstance) {
		log.debug("deleting Tdepartposition instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Tdepartposition findById(java.lang.String id) {
		log.debug("getting Tdepartposition instance with id: " + id);
		try {
			Tdepartposition instance = (Tdepartposition) getSession().get(
					"com.jinrl.powermodule.pojo.Tdepartposition", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Tdepartposition instance) {
		log.debug("finding Tdepartposition instance by example");
		try {
			List results = getSession().createCriteria(
					"com.jinrl.powermodule.pojo.Tdepartposition").add(
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
		log.debug("finding Tdepartposition instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Tdepartposition as model where model."
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
		log.debug("finding all Tdepartposition instances");
		try {
			String queryString = "from Tdepartposition";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Tdepartposition merge(Tdepartposition detachedInstance) {
		log.debug("merging Tdepartposition instance");
		try {
			Tdepartposition result = (Tdepartposition) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Tdepartposition instance) {
		log.debug("attaching dirty Tdepartposition instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Tdepartposition instance) {
		log.debug("attaching clean Tdepartposition instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}