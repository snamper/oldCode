package com.jinrl.powermodule.dao;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jinrl.powermodule.pojo.Tpositionfunction;

/**
 * A data access object (DAO) providing persistence and search support for
 * Tpositionfunction entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.jinrl.powermodule.pojo.Tpositionfunction
 * @author MyEclipse Persistence Tools
 */

public class TpositionfunctionDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TpositionfunctionDAO.class);
	// property constants
	public static final String FISUSED = "fisused";

	public void save(Tpositionfunction transientInstance) {
		log.debug("saving Tpositionfunction instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Tpositionfunction persistentInstance) {
		log.debug("deleting Tpositionfunction instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Tpositionfunction findById(java.lang.String id) {
		log.debug("getting Tpositionfunction instance with id: " + id);
		try {
			Tpositionfunction instance = (Tpositionfunction) getSession().get(
					"com.jinrl.powermodule.pojo.Tpositionfunction", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Tpositionfunction instance) {
		log.debug("finding Tpositionfunction instance by example");
		try {
			List results = getSession().createCriteria(
					"com.jinrl.powermodule.pojo.Tpositionfunction").add(
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
		log.debug("finding Tpositionfunction instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Tpositionfunction as model where model."
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
		log.debug("finding all Tpositionfunction instances");
		try {
			String queryString = "from Tpositionfunction";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Tpositionfunction merge(Tpositionfunction detachedInstance) {
		log.debug("merging Tpositionfunction instance");
		try {
			Tpositionfunction result = (Tpositionfunction) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Tpositionfunction instance) {
		log.debug("attaching dirty Tpositionfunction instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Tpositionfunction instance) {
		log.debug("attaching clean Tpositionfunction instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}