package org.donald.service;

import org.donald.enity.User;

/**
 * 
 * @author donald
 * 2017年7月20日
 * 下午12:50:06
 */
public interface IUserService {
	/**
	 * 保存用户
	 * @param user
	 */
	public abstract void saveUser(User user);
	/**
	 * 根据用户id，获取用户信息
	 * @param userId
	 */
	public abstract User findUserById(Integer userId);
	/**
	 * 根据用户id，删除用户信息
	 * @param userId
	 * @throws Exception
	 */
	public abstract void deleteUserById(Integer userId);
	/**
	 * 用户是否登录
	 * @param user
	 * @return
	 */
	public abstract boolean userHasLogin(User user);
	/**
	 * 
	 * @param user
	 * @throws Exception
	 */
	public abstract User UpdateUser(User user);
}
