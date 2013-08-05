package com.jinrl.powermodule.dao;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jinrl.powermodule.pojo.Tstatistic;

/**
 * A data access object (DAO) providing persistence and search support for
 * Tstatistic entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 *
 * @see com.jinrl.powermodule.pojo.Tstatistic
 * @author MyEclipse Persistence Tools
 */

public class TstatisticDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TstatisticDAO.class);
	// property constants
	public static final String FDESCRIPTION = "fdescription";
	public static final String FCONN_DATABASE = "fconnDatabase";
	public static final String FSQL = "fsql";
	public static final String FISSHOW = "fisshow";
	public static final String FWARN_FIELD = "fwarnField";
	public static final String FWARN_VALUE = "fwarnValue";
	public static final String FWARN_EMAIL = "fwarnEmail";
	public static final String FREFRESH_TIME = "frefreshTime";
	public static final String FBEFORE_VALUE = "fbeforeValue";
	public static final String FISUSED = "fisused";

	public void save(Tstatistic transientInstance) {
		log.debug("saving Tstatistic instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Tstatistic persistentInstance) {
		log.debug("deleting Tstatistic instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Tstatistic findById(java.lang.String id) {
		log.debug("getting Tstatistic instance with id: " + id);
		try {
			SessionFactory sf = this.getSession().getSessionFactory();
			Session session = sf.openSession();
			Tstatistic instance = (Tstatistic) session.get(
					"com.jinrl.powermodule.pojo.Tstatistic", id);
			session.close();
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Tstatistic instance) {
		log.debug("finding Tstatistic instance by example");
		try {
			List results = getSession().createCriteria(
					"com.jinrl.powermodule.pojo.Tstatistic").add(
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
		log.debug("finding Tstatistic instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Tstatistic as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByFdescription(Object fdescription) {
		return findByProperty(FDESCRIPTION, fdescription);
	}

	public List findByFconnDatabase(Object fconnDatabase) {
		return findByProperty(FCONN_DATABASE, fconnDatabase);
	}

	public List findByFsql(Object fsql) {
		return findByProperty(FSQL, fsql);
	}

	public List findByFisshow(Object fisshow) {
		return findByProperty(FISSHOW, fisshow);
	}

	public List findByFwarnField(Object fwarnField) {
		return findByProperty(FWARN_FIELD, fwarnField);
	}

	public List findByFwarnValue(Object fwarnValue) {
		return findByProperty(FWARN_VALUE, fwarnValue);
	}

	public List findByFwarnEmail(Object fwarnEmail) {
		return findByProperty(FWARN_EMAIL, fwarnEmail);
	}

	public List findByFrefreshTime(Object frefreshTime) {
		return findByProperty(FREFRESH_TIME, frefreshTime);
	}

	public List findByFbeforeValue(Object fbeforeValue) {
		return findByProperty(FBEFORE_VALUE, fbeforeValue);
	}

	public List findByFisused(Object fisused) {
		return findByProperty(FISUSED, fisused);
	}

	public List findAll() {
		try {
			SessionFactory sf = this.getSession().getSessionFactory();
			Session session = sf.openSession();
			String queryString = "from Tstatistic s where s.fisused='0'";
			Query queryObject = session.createQuery(queryString);
			List list = queryObject.list();
			session.close();
			return list;
		} catch (RuntimeException re) {
			throw re;
		}
	}



	public List<Tstatistic> finShowStatistic() {
		try {
			SessionFactory sf = this.getSession().getSessionFactory();
			Session session = sf.openSession();
			String queryString = "from Tstatistic s where s.fisused='0' and s.fisshow in ('0','2')";
			Query queryObject = session.createQuery(queryString);
			List list = queryObject.list();
			session.close();
			return list;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void update(Tstatistic detachedInstance) {
		SessionFactory sf = this.getSession().getSessionFactory();
		Session session = sf.openSession();
		Transaction tr = session.beginTransaction();
		try {
			session.update(detachedInstance);
			tr.commit();
			session.close();
		} catch (RuntimeException re) {
			tr.rollback();
			throw re;
		}
	}

	public Tstatistic merge(Tstatistic detachedInstance) {
		log.debug("merging Tstatistic instance");
		try {
			Tstatistic result = (Tstatistic) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Tstatistic instance) {
		log.debug("attaching dirty Tstatistic instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Tstatistic instance) {
		log.debug("attaching clean Tstatistic instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

}