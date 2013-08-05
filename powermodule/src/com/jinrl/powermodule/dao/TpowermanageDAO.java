package com.jinrl.powermodule.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jinrl.powermodule.pojo.Tposition;
import com.jinrl.powermodule.pojo.Tpowermanage;

/**
 * A data access object (DAO) providing persistence and search support for
 * Tpowermanage entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 *
 * @see com.jinrl.powermodule.pojo.Tpowermanage
 * @author MyEclipse Persistence Tools
 */

public class TpowermanageDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TpowermanageDAO.class);
	// property constants
	public static final String FPOSITIONID = "fpositionid";
	public static final String FFUNCTIONID = "ffunctionid";
	public static final String FPOWERTYPE = "fpowertype";
	public static final String FDATAID = "fdataid";
	public static final String FPOWER = "fpower";

	public void save(Tpowermanage transientInstance) {
		log.debug("saving Tpowermanage instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Tpowermanage persistentInstance) {
		log.debug("deleting Tpowermanage instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Tpowermanage findById(java.lang.String id) {
		log.debug("getting Tpowermanage instance with id: " + id);
		try {
			Tpowermanage instance = (Tpowermanage) getSession().get(
					"com.jinrl.powermodule.pojo.Tpowermanage", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Tpowermanage instance) {
		log.debug("finding Tpowermanage instance by example");
		try {
			List results = getSession().createCriteria(
					"com.jinrl.powermodule.pojo.Tpowermanage").add(
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
		log.debug("finding Tpowermanage instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Tpowermanage as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByFpositionid(Object fpositionid) {
		return findByProperty(FPOSITIONID, fpositionid);
	}

	public List findByFfunctionid(Object ffunctionid) {
		return findByProperty(FFUNCTIONID, ffunctionid);
	}

	public List findByFpowertype(Object fpowertype) {
		return findByProperty(FPOWERTYPE, fpowertype);
	}

	public List findByFdataid(Object fdataid) {
		return findByProperty(FDATAID, fdataid);
	}

	public List findByFpower(Object fpower) {
		return findByProperty(FPOWER, fpower);
	}

	public List findAll() {
		log.debug("finding all Tpowermanage instances");
		try {
			String queryString = "from Tpowermanage";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Tpowermanage merge(Tpowermanage detachedInstance) {
		log.debug("merging Tpowermanage instance");
		try {
			Tpowermanage result = (Tpowermanage) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Tpowermanage instance) {
		log.debug("attaching dirty Tpowermanage instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Tpowermanage instance) {
		log.debug("attaching clean Tpowermanage instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	//控制4个按钮时使用
	@SuppressWarnings("unchecked")
	public List<Tpowermanage> findpowerManages(String positionid,String functionid){
		Session session = getSession().getSessionFactory().openSession();
		String hql = "from Tpowermanage pm where pm.fpositionid=? and pm.ffunctionid=? and pm.fpowertype=1";
		List<Tpowermanage> list = session.createQuery(hql).setString(0, positionid).setString(1, functionid).list();
		session.close();
		return list;
	}

	//修改时，对显示状态的控制
	@SuppressWarnings("unchecked")
	public List<Tpowermanage> findFieldpower(String busiInfoid,List<Tposition> position){
		Session session = getSession().getSessionFactory().openSession();
		List<Tpowermanage> list = null;
		try{
			String hql = "from Tpowermanage pm where pm.ffunctionid=? and pm.fpositionid in (:poslist) and pm.fpowertype=2";
			List positionid = new ArrayList();
			for(Tposition p : position){
				positionid.add(p.getFid());
			}
			list = session.createQuery(hql).setString(0, busiInfoid).setParameterList("poslist", positionid).list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return list;
	}


}