package org.donald.service.impl;

import org.donald.dao.IUserDao;
import org.donald.enity.User;
import org.donald.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("userService")
public class UserServiceImpl implements IUserService {
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
	private IUserDao userDao; 
    /**
     * 
     */
	@Override
	public void saveUser(User user){
		try {
			userDao.saveUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * 
	 */
	@Override
	public User findUserById(Integer userId){
		User user = null;
		try {
			user=  userDao.findUserById(userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	/**
	 * 
	 */
	@Override
	public void deleteUserById(Integer userId){
		try {
			userDao.deleteUserById(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
    /**
     * 判断用户是否登录
     */
	@Override
	public boolean userHasLogin(User user) {
//		session.hasUser(user);
		return false;
	}
	@Override
	public User UpdateUser(User user) {
		User userResult = null;
		try {
			userResult = userDao.UpdateUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userResult;
	}

}
