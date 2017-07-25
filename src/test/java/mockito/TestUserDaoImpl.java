package mockito;

import static org.junit.Assert.*;

import org.donald.dao.impl.UserDaoImpl;
import org.donald.enity.User;
import org.donald.service.impl.UserServiceImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Mock UserDaoImpl
 * @author donald
 * 2017年7月20日
 * 下午4:37:23
 */
public class TestUserDaoImpl {
	private Logger log = LoggerFactory.getLogger(TestUserDaoImpl.class);
	@Mock 
	private UserDaoImpl userDao;//mock对象

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		//初始化Mock注解
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() throws Exception {
	}
    /**
     * 模拟返回值
     * @throws Exception
     */
	@Test
	public void testReturn() throws Exception {
	   Mockito.when(userDao.findUserById(1)).thenReturn(new User(1,"donald",23));
	   User user = userDao.findUserById(1);
	   log.info("==mock return value："+user.toString());
	   assertEquals("用户信息错误",1,user.getUserId().intValue());
	  
	   User user2 = userDao.findUserById(2);
	   //由于没有定义userDao.findUserById(2)的Mock行为，所以返回为null
	   log.info("==mock return value unmock："+user2);
	   assertNull("存在脏数据",user2);
	   
	   //模拟异常，这种针对有返回的值操作
	   Mockito.when(userDao.findUserById(3)).thenThrow(new RuntimeException("模拟异常抛出..."));
	   //抛出运行时异常
	   userDao.findUserById(3);
	}
	/**
	 * 模拟无返回值操作，抛出异常
	 * @throws Exception
	 */
	@Test(expected=RuntimeException.class)
	public void testThrowExceptionWithoutReturn() throws Exception {
		Mockito.doThrow(new RuntimeException()).when(userDao).saveUser(new User(1,"donald",23));
		//抛出运行时异常
		userDao.saveUser(new User(1,"donald",23));
	}
	/**
	 * 模拟参数匹配任何int参数，这意味着参数为任意值，其返回值均是User(2,"jamel",27)
	 * @throws Exception
	 */
	@Test(timeout=1000)
	public void testMockMatchParamsAndVerifyInvokerTimes() throws Exception {
		Mockito.when(userDao.findUserById(Mockito.anyInt())).thenReturn(new User(2,"jamel",27));
	    log.info("=====test mock match param："+userDao.findUserById(1));
	    log.info("=====test mock match param："+userDao.findUserById(2));
	    //校验mock对象方法是否调用一次
	    // 下面两个写法验证效果一样，均验证add方法是否被调用了一次
	 	//这个校验同时校验方法名和参数是否相等，且调用次数相等，则通过。
	 	Mockito.verify(userDao).findUserById(Mockito.anyInt()); 
	 	Mockito.verify(userDao, Mockito.times(1)).findUserById(Mockito.anyInt());
	 	Mockito.verify(userDao, Mockito.times(2)).findUserById(Mockito.anyInt());
	 	Mockito.verify(userDao, Mockito.times(3)).findUserById(Mockito.anyInt());
	 	Mockito.verify(userDao).findUserById(1);
	}
	/**
	 * 校验mock对象调用顺序
	 * @throws Exception
	 */
	@Test
	public void testMockInvokerOrder() throws Exception {
		userDao.findUserById(1);
		userDao.saveUser(new User(1,"donald",23));
		//检验单个mock对象方法的调用顺序
		InOrder inOrder = Mockito.inOrder(userDao); 
		inOrder.verify(userDao).saveUser(new User(1,"donald",23));
		inOrder.verify(userDao).findUserById(1);
		UserServiceImpl userService = Mockito.mock(UserServiceImpl.class);
		userService.findUserById(1);
		userDao.findUserById(1);
		InOrder inOrderX = Mockito.inOrder(userDao,userService);
		inOrderX.verify(userService).findUserById(1);
		inOrderX.verify(userDao).findUserById(1);
	}
	/**
	 * 测试不同的返回结果
	 * @throws Exception
	 */
	@Test
	public void testReturnDifferent() throws Exception {
		Mockito.when(userDao.findUserById(1)) 
		.thenReturn(new User(1,"donald",23))// 第一次会返回这个结果 
		.thenThrow(new RuntimeException()); // 第二次会抛出异常 
		log.info("=====test mock return diffrent result relay invoker times："+userDao.findUserById(1));
		userDao.findUserById(1);
	}
	/**
	 * 结果拦截器
	 */
	Answer<User> grabOperateArguments = new Answer<User>() { 
		public User answer(InvocationOnMock invocation) { 
			Object[] args = invocation.getArguments(); 
			Object mock = invocation.getMock(); 
			return new User(1,"donald",23);
			} 
	};
	/**
	 * 拦截结果
	 * @throws Exception
	 */
	@Test
	public void testInteceptorResult() throws Exception {
		Mockito.when(userDao.findUserById(1)).thenAnswer(grabOperateArguments); 
	    log.info("=====Inteceptor:"+userDao.findUserById(1));
	}
	/**
	 * 改变默认返回值拦截器
	 */
	Answer<User> changeDefaultValue = new Answer<User>() { 
		public User answer(InvocationOnMock invocation) { 
			Object[] args = invocation.getArguments(); 
			Object mock = invocation.getMock();//Mock对象
			return new User(0,"root",0);
			} 
	};
	/**
	 * 改变默认返回值
	 * @throws Exception
	 */
	@Test
	public void testChangeDefalutReturn() throws Exception {
		//对应没有mock的操作行为，返回默认unstubbed 结果SmartNull，并输出mock操作
		UserDaoImpl mockOne = Mockito.mock(UserDaoImpl.class, Mockito.RETURNS_SMART_NULLS);
		Mockito.when(mockOne.findUserById(1)).thenReturn(new User(4,"mark",28));
		log.info("=====change return value :"+mockOne.findUserById(1));
		log.info("=====change return value :"+mockOne.findUserById(2));
		//对应没有mock的操作行为，返回changeDefaultValue的结果
		UserDaoImpl mockTwo = Mockito.mock(UserDaoImpl.class, changeDefaultValue);
		Mockito.when(mockTwo.findUserById(1)).thenReturn(new User(5,"rain",30));
		log.info("=====change return value by Answer:"+mockTwo.findUserById(1));
		log.info("=====change return value by Answer:"+mockTwo.findUserById(2));
	}
	/**
	 * 捕获函数的参数值
	 * @throws Exception
	 */
	@Test
	public void testGetParams() throws Exception {
		ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class); 
		userDao.saveUser(new User("donald",23));
		//确保保存用户调用
		Mockito.verify(userDao).saveUser(argument.capture()); 
		//断言保存用户名称
		assertEquals("用户保存异常","John", argument.getValue().getUserName());
	}
	/**
	 * 暗中调用真实对象
	 * @throws Exception
	 */
	@Test
	public void testSpy() throws Exception {
		//引用mock对象
		UserDaoImpl userDaoSpy = Mockito.spy(userDao);
		Mockito.when(userDao.findUserById(6)).thenReturn(new User(6,"rain",30));
		System.out.println("===spy mock:"+userDaoSpy.findUserById(1));
		Mockito.verify(userDaoSpy).findUserById(6);
		Mockito.verify(userDao).findUserById(6);
		
	}
	/**
	 * 调用mock对象真实方法
	 * @throws Exception
	 */
	@Test
	public void testCallRealMethod() throws Exception {
		//调用mock对象真实方法
		Mockito.when(userDao.findUserById(6)).thenCallRealMethod();
		System.out.println("===CallRealMethod:"+userDao.findUserById(1));
	}
	/**
	 * 重置Mock对象
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testReset() throws Exception {
		Mockito.when(userDao.findUserById(1)).thenReturn(new User(1,"donald",23));
		userDao.findUserById(1);
		Mockito.reset(userDao); //at this point the mock forgot any interactions & stubbing
		//重置mock对象的交互和stub过后，交互行为为0
		Mockito.verifyZeroInteractions(userDao);
	}
	/**
	 * 1.检查mock操作是否超时
	 * 2.检验mock操作是否在指定时间内，调用指定次数
	 */
	@Test
	public void testMockTimeout() throws Exception {
		Mockito.when(userDao.findUserById(1)).thenReturn(new User(1,"donald",23));
		userDao.findUserById(1);
		//判断是否在一百毫秒内，调mock方法一次，两次，至少两次
		Mockito.verify(userDao, Mockito.timeout(100)).findUserById(1); 
		//这个与上面一句效果相同
		Mockito.verify(userDao, Mockito.timeout(100).times(1)).findUserById(1); 
		Mockito.verify(userDao, Mockito.timeout(100).times(2)).findUserById(1);
		Mockito.verify(userDao, Mockito.timeout(100).atLeast(2)).findUserById(1);
	}
	
	/**
	 * Mock详情，是否为mock对象和spy对象
	 */
	@Test
	public void testMockDetails() {
		System.out.println("===isMock:"+Mockito.mockingDetails(userDao).isMock());
		System.out.println("===isSpy:"+Mockito.mockingDetails(userDao).isSpy());
	}

}
