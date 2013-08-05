package com.agents.util;

import java.sql.ResultSet;
import java.util.List;

import org.apache.log4j.Logger;

public class BaseDao {

	static Logger log = Logger.getLogger(BaseDao.class);
	DataConnect dc = new DataConnect("hnczoracle", true);    
	public int noCount = 0;

	public List<String[]> findList(String sql, int page) {
		return this.findList(sql, page, 15);
	}
	public List<String[]> findListTop500(String sql, int page) {
		return this.findList(sql, page, 500);
	}
	public List<String[]> findListTopQW(String sql, int page) {
		return this.findList(sql, page, 10000000);
	}

	
	public List<String[]> findListFromOracle(String sql, int page,ResultSet rs) {
		return null;
	}
	
	public List<String[]> findList(String sql, int page, int num) {
		log.info(sql);
		System.out.println(sql);
		try {
			DataAccess da = new DataAccess(dc, num);
			noCount = da.query(sql);

			int countPage = 0;
			if (noCount % num == 0) {
				countPage = noCount / num;
			} else {
				countPage = noCount / num + 1;
			}
			if (page < 1)
				page = 1;
			if (page > countPage)
				page = countPage;

			List<String[]> list = da.getList(da.rs, page);
			list.remove(0);
			da.Close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String[] findFirstData(String sql) {
		log.debug(sql);
		try {
			DataAccess da = new DataAccess(dc);
			da.query(sql);
			List<String[]> list = da.getList(da.rs, 1);
			da.Close();
			return list.get(1);
		} catch (Exception e) {
			return null;
		}
	}

	public String[] findField(String sql) {
		try {
			DataAccess da = new DataAccess(dc);
			da.query(sql);
			List<String[]> list = da.getList(da.rs, 1);
			da.Close();
			return list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean updateData(String sql) {
		log.info(sql);
		int n = dc.execute(sql);
		if (n > 0) {
			return true;
		} else {
			return false;
		}
	}

	public List<String[]> findAll(String sql) {
		log.info(sql);
		try {
			DataAccess da = new DataAccess(dc, 10000);
			da.query(sql);
			List<String[]> list = da.getList(da.rs, 1);
			list.remove(0);
			da.Close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
