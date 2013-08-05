package com.jinrl.powermodule.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jinrl.powermodule.pojo.Tmoduletype;

/**
 * A data access object (DAO) providing persistence and search support for
 * Tmoduletype entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 *
 * @see com.jinrl.powermodule.pojo.Tmoduletype
 * @author MyEclipse Persistence Tools
 */

public class TmoduletypeDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TmoduletypeDAO.class);
	// property constants
	public static final String FMODULENAME = "fmodulename";
	public static final String FISUSED = "fisused";

	public void save(Tmoduletype transientInstance) {
		log.debug("saving Tmoduletype instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Tmoduletype persistentInstance) {
		log.debug("deleting Tmoduletype instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Tmoduletype findById(java.lang.Integer id) {
		try {
			Session session = getSession().getSessionFactory().openSession();
			Transaction tr = session.beginTransaction();
			String hql = "from Tmoduletype as mt where mt.fid=?";
			Tmoduletype instance = (Tmoduletype)session.createQuery(hql).setInteger(0, id).uniqueResult();
			tr.commit();
			session.close();
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Tmoduletype instance) {
		log.debug("finding Tmoduletype instance by example");
		try {
			List results = getSession().createCriteria(
					"com.jinrl.powermodule.pojo.Tmoduletype").add(
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
		log.debug("finding Tmoduletype instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Tmoduletype as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByFmodulename(Object fmodulename) {
		return findByProperty(FMODULENAME, fmodulename);
	}

	public List findByFisused(Object fisused) {
		return findByProperty(FISUSED, fisused);
	}

	public List findAll() {
		log.debug("finding all Tmoduletype instances");
		try {
			String queryString = "from Tmoduletype";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Tmoduletype merge(Tmoduletype detachedInstance) {
		log.debug("merging Tmoduletype instance");
		try {
			Tmoduletype result = (Tmoduletype) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Tmoduletype instance) {
		log.debug("attaching dirty Tmoduletype instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Tmoduletype instance) {
		log.debug("attaching clean Tmoduletype instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	//使用
	public List<Tmoduletype> findAllisused(Set<Integer> systemset) {
		try {
			String queryString = "from Tmoduletype mt where mt.fisused='0' and (mt.fid in (:sysset) or mt.fid='0')";
			Query queryObject = getSession().createQuery(queryString).setParameterList("sysset", systemset);
			return queryObject.list();
		} catch (RuntimeException re) {
			//当没有设置任何功能的岗位登录时，发生此异常
			if(re.getMessage().indexOf("unexpected end of subtree") != -1){
				return null;
			}else{
				re.printStackTrace();
				return null;
			}
		}
	}
}