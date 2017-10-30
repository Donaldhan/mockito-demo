/**
 * 
 */
package util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.donald.enity.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试JacksonUtil
 * @author donald
 * 2017年7月20日
 * 下午9:57:07
 */
public class TestJacksonUtil {
   private static final Logger log = LoggerFactory.getLogger(TestacksonUtil.class);
   private static JacksonUtil jacksonUtil = null;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		jacksonUtil = JacksonUtil.getInstance();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		jacksonUtil = null;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link util.JacksonUtil#toJson(java.lang.Object)}.
	 */
	@Test
	public void testToJson() {
		User user = new User(1,"donald",23);
		log.info("==>toJson:"+jacksonUtil.toJson(user));
		List<User> userList = new ArrayList<User>(2);
		userList.add(user);
		userList.add(new User(2,"jamel",30));
		log.info("==>ListToJson:"+jacksonUtil.toJson(userList));
		Map<String,User> userMap = new HashMap<String,User>(2);
		userMap.put(user.getUserName(), user);
		userMap.put("jamel", new User(2,"jamel",30));
		log.info("==>MapToJson:"+jacksonUtil.toJson(userMap));
	}

	/**
	 * Test method for {@link util.JacksonUtil#toBean(java.lang.String, java.lang.Class)}.
	 */
	@Test
	public void testToBean() {
		User user = new User(1,"donald",23);
		String jsonUser = jacksonUtil.toJson(user);
		User userTemp = jacksonUtil.toBean(jsonUser, User.class);
		log.info("<==toBean User:"+userTemp.toString());
	}

	/**
	 * Test method for {@link util.JacksonUtil#toList(java.lang.String, java.lang.Class)}.
	 */
	@Test
	public void testToList() {
		User user = new User(1,"donald",23);
		List<User> userList = new ArrayList<User>(2);
		userList.add(user);
		userList.add(new User(2,"jamel",30));
		String userListJson = jacksonUtil.toJson(userList);
		List<User> userListTemp = jacksonUtil.toList(userListJson, User.class);
		log.info("<==JsonToList:"+userListTemp.get(0).toString()+","+userListTemp.get(1).toString());
	}

	/**
	 * Test method for {@link util.JacksonUtil#toMap(java.lang.String, java.lang.Class, java.lang.Class)}.
	 */
	@Test
	public void testToMap() {
		User user = new User(1,"donald",23);
		Map<String,User> userMap = new HashMap<String,User>(2);
		userMap.put(user.getUserName(), user);
		userMap.put("jamel", new User(2,"jamel",30));
		String userMapJson = jacksonUtil.toJson(userMap);
		Map<String,User> userMapTemp = jacksonUtil.toMap(userMapJson, String.class, User.class);
		log.info("<==JsonToMap:"+userMapTemp.get("donald").toString()+","+userMapTemp.get("jamel").toString());
	}

}
