package org.donald.enity;

import java.io.Serializable;
/**
 * 
 * @author donald
 * 2017年7月20日
 * 下午12:42:40
 */
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8391519395987064011L;
	
	private Integer userId;
	private String userName;
	private Integer userAge;
	
	public User() {
		super();
	}

	public User(String userName, Integer userAge) {
		super();
		this.userName = userName;
		this.userAge = userAge;
	}
	
	public User(Integer userId, String userName, Integer userAge) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userAge = userAge;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getUserAge() {
		return userAge;
	}
	public void setUserAge(Integer userAge) {
		this.userAge = userAge;
	}
	@Override
	public String  toString(){
		return "[userId:"+this.userId+",userName:"+this.userName+",userAge:"+this.userAge+"]";
	}
   
}
