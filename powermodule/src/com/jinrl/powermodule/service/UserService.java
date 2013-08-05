package com.jinrl.powermodule.service;

import com.jinrl.powermodule.dao.TuserDAO;
import com.jinrl.powermodule.pojo.Tuser;

public class UserService {
	private TuserDAO userDao;

	public void setUserDao(TuserDAO userDao) {
		this.userDao = userDao;
	}

	public Tuser getUserById(String id) {
		return userDao.findById(id);
	}

	public boolean updateUser(Tuser user) {
		try {
			userDao.update(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
