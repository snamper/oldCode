package com.jinrl.powermodule.dao;

import com.jinrl.powermodule.pojo.Tfunction;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * Tfunction entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 *
 * @see com.jinrl.powermodule.pojo.Tfunction
 * @author MyEclipse Persistence Tools
 */

public class TfunctionDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TfunctionDAO.class);
	// property constants
	public static final String FSYSTEMID = "fsystemid";
	public static final String FMODULEID = "fmoduleid";
	public static final String FFUNCTIONNAME = "ffunctionname";
	public static final String FFUNCTIONRUL = "ffunctionrul";
	public static final String FORDERBYNUM = "forderbynum";
	public static final String FISUSED = "fisused";

	public void save(Tfunction transientInstance) {
		log.debug("saving Tfunction instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Tfunction persistentInstance) {
		log.debug("deleting Tfunction instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Tfunction findById(java.lang.String id) {
		SessionFactory sf = this.getSession().getSessionFactory();
		Session session = sf.openSession();
		Transaction tr = session.beginTransaction();

		log.debug("getting Tfunction instance with id: " + id);
		try {
			Tfunction instance = (Tfunction) session.get("com.jinrl.powermodule.pojo.Tfunction", id);
			tr.commit();
			session.close();
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public Tfunction findByBusiInfoID(java.lang.String busiINfoID) {
		SessionFactory sf = this.getSession().getSessionFactory();
		Session session = sf.openSession();
		Transaction tr = session.beginTransaction();

		try {
			Tfunction instance = (Tfunction) session.createQuery("from Tfunction f where f.fbusiinfoid=?").setString(0, busiINfoID).uniqueResult();
			tr.commit();
			session.close();
			return instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}


	public List findByExample(Tfunction instance) {
		log.debug("finding Tfunction instance by example");
		try {
			List results = getSession().createCriteria(
					"com.jinrl.powermodule.pojo.Tfunction").add(
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
		log.debug("finding Tfunction instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Tfunction as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByFsystemid(Object fsystemid) {
		return findByProperty(FSYSTEMID, fsystemid);
	}

	public List findByFmoduleid(Object fmoduleid) {
		return findByProperty(FMODULEID, fmoduleid);
	}

	public List findByFfunctionname(Object ffunctionname) {
		return findByProperty(FFUNCTIONNAME, ffunctionname);
	}

	public List findByFfunctionrul(Object ffunctionrul) {
		return findByProperty(FFUNCTIONRUL, ffunctionrul);
	}

	public List findByForderbynum(Object forderbynum) {
		return findByProperty(FORDERBYNUM, forderbynum);
	}

	public List findByFisused(Object fisused) {
		return findByProperty(FISUSED, fisused);
	}

	public List findAll() {
		log.debug("finding all Tfunction instances");
		try {
			String queryString = "from Tfunction";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Tfunction merge(Tfunction detachedInstance) {
		log.debug("merging Tfunction instance");
		try {
			Tfunction result = (Tfunction) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Tfunction instance) {
		log.debug("attaching dirty Tfunction instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Tfunction instance) {
		log.debug("attaching clean Tfunction instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public List findAllCommonFuntions() {
		SessionFactory sf = this.getSession().getSessionFactory();
		Session session = sf.openSession();
		try {
			List l = session.createQuery("from Tfunction f where f.fcommon='0'").list();
			session.close();
			return l;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<Tfunction> findAllFunByUserId(String userid){
		Session session = this.getSession().getSessionFactory().openSession();
		Transaction tr  = session.beginTransaction();
		String sql = "select * from tfunction where (fid in (select distinct ffunctionid from tpositionfunction where fpositionid in (select fpositionid from tpositionuser where fuserid=? and fisused='0') and fisused='0') or  fcommon='0') and fisused='0' order by forderbynum";
		List list = session.createSQLQuery(sql).addEntity(Tfunction.class).setString(0, userid).list();
		tr.commit();
		session.close();
		return list;
	}
}