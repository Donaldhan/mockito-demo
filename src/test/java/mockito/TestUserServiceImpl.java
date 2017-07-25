package mockito;

import static org.junit.Assert.*;

import org.donald.dao.IUserDao;
import org.donald.enity.User;
import org.donald.service.impl.UserServiceImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
/**
 * mock UserServiceImpl
 * @RunWith：这个是指定使用的单元测试执行类
 * @author donald
 * 2017年7月20日
 * 下午4:35:40
 */
public class TestUserServiceImpl {
	/**
	 * @Mock: 创建一个Mock;
	 * @InjectMocks: 创建一个实例，其余用@Mock（或@Spy）注解创建的mock将被注入到用该实例中,@InjectMocks对接对象并不是mock对象；
	 * 注意：必须使用@RunWith(MockitoJUnitRunner.class) 或 Mockito.initMocks(this)进行mocks的初始化和注入。
	 */
	@InjectMocks
	private UserServiceImpl userService;
	@Mock
	private IUserDao userDao;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() throws Exception {
	}
    
	@Test
	public void testFindUserById() throws Exception {
		Mockito.when(userDao.findUserById(1)).thenReturn(new User(1,"donald",23));
		User user = userService.findUserById(1);
		assertNotNull(user);
		Mockito.verify(userDao, Mockito.times(1));
	}
	@Test
	public void testUserHasLogin(){
		System.out.println("=====userService is mock:"+Mockito.mockingDetails(userService).isMock());
		//userService非mock对象，Mockito.when方法参数必须是一个mock对象的方法调用
		Mockito.when(userService.userHasLogin(new User(1,"donald",23))).thenReturn(true);
		boolean isLogin  = userService.userHasLogin(new User(1,"donald",23));
		assertTrue(isLogin);
	}
	@Test
	public void testUpdateUser() throws Exception {
		User user = new User(1,"donald",23);
		Mockito.doNothing().when(userDao).saveUser(user);
		userService.saveUser(user);
		user.setUserName("rain");
		Mockito.when(userDao.UpdateUser(user)).thenReturn(user);
		assertEquals("用户更新异常","rain",user.getUserName());
		
	}
	

}
