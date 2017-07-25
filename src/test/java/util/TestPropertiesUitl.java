package util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestPropertiesUitl {
	private static final String DATASOURCE_DRIVER_CLASS_NAME  = "spring.datasource.driverClassName";
	private static final String	DATASOURCE_URL	= "spring.datasource.url";
	private static final String	DATASOURCE_USERNAME = "spring.datasource.username";
	private static final String	DATASOURCE_PASSWORD	= "spring.datasource.password";
	private static PropertiesUtil propertiesUtil = null;
	@Before
	public void setUp() throws Exception {
		propertiesUtil = PropertiesUtil.getInstance();
	}

	@After
	public void tearDown() throws Exception {
		propertiesUtil = null;
		
	}

	@Test
	public void test() {
		String datasourceDriver = propertiesUtil.getProperty(DATASOURCE_DRIVER_CLASS_NAME);
		System.out.println("数据库驱动："+datasourceDriver);
		assertTrue("驱动加载加载异常",datasourceDriver.startsWith("com.mysql"));
		System.out.println("数据库url："+propertiesUtil.getProperty(DATASOURCE_URL));
		String username = propertiesUtil.getProperty(DATASOURCE_USERNAME);
		System.out.println("数据库用户名："+username);
		assertTrue("非测试环境用户",username.endsWith("test"));
		String userPassword = propertiesUtil.getProperty(DATASOURCE_PASSWORD);
		System.out.println("数据库用户密码："+propertiesUtil.getProperty(DATASOURCE_PASSWORD));
		assertTrue("非测试环境用户密码",userPassword.endsWith("test"));
	}

}
