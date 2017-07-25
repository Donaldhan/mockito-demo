package org.donald.dao.impl;

import org.donald.dao.IUserDao;
import org.donald.enity.User;
import org.springframework.stereotype.Repository;
@Repository("userDao")
public class UserDaoImpl implements IUserDao {
	/**
	 * 
	 */
	@Override
	public void saveUser(User user) throws Exception {

	}
	/**
	 * 
	 */
	@Override
	public User findUserById(Integer userId) throws Exception {
		return null;
	}
	/**
	 * 
	 */
	@Override
	public void deleteUserById(Integer userId) throws Exception {

	}
	@Override
	public User UpdateUser(User user) throws Exception {
		return null;
	}

}
