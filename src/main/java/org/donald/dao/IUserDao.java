package org.donald.dao;

import org.donald.enity.User;

/**
 * 
 * @author donald
 * 2017年7月20日
 * 下午12:50:00
 */
public interface IUserDao {
	/**
	 * 
	 * @param user
	 */
	public abstract void saveUser(User user) throws Exception;
	/**
	 * 
	 * @param userId
	 */
	public abstract User findUserById(Integer userId) throws Exception;
	/**
	 * 
	 * @param userId
	 * @throws Exception
	 */
	public abstract void deleteUserById(Integer userId) throws Exception;
	/**
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public abstract User UpdateUser(User user) throws Exception;
	
}
