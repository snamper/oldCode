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

import com.jinrl.powermodule.pojo.Tuser;

/**
 * A data access object (DAO) providing persistence and search support for Tuser
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 *
 * @see com.jinrl.powermodule.pojo.Tuser
 * @author MyEclipse Persistence Tools
 */

public class TuserDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(TuserDAO.class);
	// property constants
	public static final String FUSERNAME = "fusername";
	public static final String FPASSWORD = "fpassword";
	public static final String FSEX = "fsex";
	public static final String FISUSED = "fisused";

	public void save(Tuser transientInstance) {
		log.debug("saving Tuser instance");
		try {
			getSession().save(transientInstance);
			getSession().flush();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void update(Tuser transientInstance) {
		SessionFactory sf = this.getSession().getSessionFactory();
		Session session = sf.openSession();
		Transaction tr = session.beginTransaction();
		try {
			session.update(transientInstance);
			tr.commit();
			session.close();
		} catch (RuntimeException re) {
			tr.rollback();
			throw re;

		}
	}

	public void delete(Tuser persistentInstance) {
		log.debug("deleting Tuser instance");
		try {
			getSession().delete(persistentInstance);
			getSession().flush();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Tuser findById(java.lang.String id) {
		SessionFactory sf = this.getSession().getSessionFactory();
		Session session = sf.openSession();
		log.debug("getting Tuser instance with id: " + id);
		try {
			Tuser instance = (Tuser) session.get(
					"com.jinrl.powermodule.pojo.Tuser", id);
			session.close();
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Tuser instance) {
		log.debug("finding Tuser instance by example");
		try {
			List results = getSession().createCriteria(
					"com.jinrl.powermodule.pojo.Tuser").add(
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
		log.debug("finding Tuser instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Tuser as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByFusername(Object fusername) {
		return findByProperty(FUSERNAME, fusername);
	}

	public List findByFpassword(Object fpassword) {
		return findByProperty(FPASSWORD, fpassword);
	}

	public List findByFsex(Object fsex) {
		return findByProperty(FSEX, fsex);
	}

	public List findByFisused(Object fisused) {
		return findByProperty(FISUSED, fisused);
	}

	public List findAll() {
		log.debug("finding all Tuser instances");
		try {
			String queryString = "from Tuser";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Tuser merge(Tuser detachedInstance) {
		log.debug("merging Tuser instance");
		try {
			Tuser result = (Tuser) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Tuser instance) {
		log.debug("attaching dirty Tuser instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Tuser instance) {
		log.debug("attaching clean Tuser instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/**
	 *检验用户名密码是否正确
	 * @param userid
	 * @param password
	 * @return
	 */
	public Tuser isLogin(String userid,String password){
		Tuser user = null;
		try{
			user = (Tuser) getSession().createQuery("from Tuser user where user.fid=? and user.fpassword=? and user.fisused='0'").setString(0, userid).setString(1, password).uniqueResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return user;
	}

	//邮箱验证
	public String getEmail(String userid){
		String email = (String)getSession().createQuery("select user.email from Tuser user where user.fid=?").setString(0, userid).uniqueResult();
		return email;
	}

}