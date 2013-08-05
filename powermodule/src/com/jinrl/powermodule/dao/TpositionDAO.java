package com.jinrl.powermodule.dao;

import java.util.List;
import java.util.Set;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jinrl.powermodule.pojo.Tposition;

/**
 * A data access object (DAO) providing persistence and search support for
 * Tposition entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.jinrl.powermodule.pojo.Tposition
 * @author MyEclipse Persistence Tools
 */

public class TpositionDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TpositionDAO.class);
	// property constants
	public static final String FPOSITIONNAME = "fpositionname";
	public static final String FISUSED = "fisused";

	public void save(Tposition transientInstance) {
		log.debug("saving Tposition instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Tposition persistentInstance) {
		log.debug("deleting Tposition instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Tposition findById(java.lang.String id) {
		log.debug("getting Tposition instance with id: " + id);
		try {
			Tposition instance = (Tposition) getSession().get(
					"com.jinrl.powermodule.pojo.Tposition", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Tposition instance) {
		log.debug("finding Tposition instance by example");
		try {
			List results = getSession().createCriteria(
					"com.jinrl.powermodule.pojo.Tposition").add(
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
		log.debug("finding Tposition instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Tposition as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByFpositionname(Object fpositionname) {
		return findByProperty(FPOSITIONNAME, fpositionname);
	}

	public List findByFisused(Object fisused) {
		return findByProperty(FISUSED, fisused);
	}

	public List findAll() {
		log.debug("finding all Tposition instances");
		try {
			String queryString = "from Tposition";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Tposition merge(Tposition detachedInstance) {
		log.debug("merging Tposition instance");
		try {
			Tposition result = (Tposition) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Tposition instance) {
		log.debug("attaching dirty Tposition instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Tposition instance) {
		log.debug("attaching clean Tposition instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}