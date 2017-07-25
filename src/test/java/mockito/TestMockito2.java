package mockito;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.donald.enity.User;
import org.donald.service.IUserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.verification.Timeout;
/**
 * Mock函数操作,可以通过doThrow(), doAnswer(), doNothing(), 
 * doReturn() and doCallRealMethod() 来自定义函数操作。
 * @author donald
 * 2017年7月19日
 * 上午9:04:40
 */
public class TestMockito2 {
	private List mock;
	@Before
	public void setUp() throws Exception {
		mock = Mockito.mock(LinkedList.class); 
	}
   
	@After
	public void tearDown() throws Exception {
		mock = null;
	}
    /**
     * 自定义返回不同结果
     */
	@Test
	public void testReturnDiffResult() {
		Mockito.when(mock.get(0))
		.thenThrow(new RuntimeException()) // 第一次会抛出异常 
		.thenReturn("foo"); // 第二次会返回这个结果 
		//First call: throws runtime exception: 
		mock.get(0); // 第一次 
		//Second call: prints "foo" 
		System.out.println("自定义返回不同结果:"+mock.get(0)); // 第二次
		 //Any consecutive call: prints "foo" as well (last stubbing wins). 
		System.out.println("自定义返回不同结果:"+mock.get(0)); // 第n次(n> 2)，依旧以最后返回最后一个配置
	}
	/**
	 * 结果拦截器
	 */
	Answer<String> grabOperateArguments = new Answer<String>() { 
		public String answer(InvocationOnMock invocation) { 
			Object[] args = invocation.getArguments(); 
			Object mock = invocation.getMock(); 
			return "called with arguments: " + args[0];
			} 
	};
	/**
	 * 对返回结果进行拦截,grab
	 */
	@Test
	public void testInteceptorResult() {
		Mockito.when(mock.add("foo")).thenAnswer(grabOperateArguments); 
//		the following prints "called with arguments: foo" 
		System.out.println("对返回结果进行拦截:"+mock.add("foo"));
	}
	
   /**
    * 暗中调用真实对象
    * Impossible: real method is called so spy.get(0) throws IndexOutOfBoundsException (the list is yet empty)
    * Mockito.when(spy.get(0)).thenReturn("foo");
    * You have to use doReturn() for stubbing
    * Mockito.doReturn("foo").when(spy).get(0);
    */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testSpy() {
		List list = new LinkedList(); 
		List spy = Mockito.spy(list); 
		//optionally, you can stub out some methods: 
		Mockito.when(spy.size()).thenReturn(100); 
		//using the spy calls *real* methods spy.add("one"); 
		spy.add("one"); 
		//prints "one" - the first element of a list 
		System.out.println("暗中调用真实对象:"+spy.get(0)); 

		//size() method was stubbed - 100 is printed 
		System.out.println("暗中调用真实对象:"+spy.size()); 
		//optionally, you can verify 
		Mockito.verify(spy).add("one"); 
		Mockito.verify(spy).add("two");
	}
	/**
	 * 改变默认返回值拦截器
	 */
	Answer<String> changeDefaultValue = new Answer<String>() { 
		public String answer(InvocationOnMock invocation) { 
			Object[] args = invocation.getArguments(); 
			Object mock = invocation.getMock();//Mock对象
			return invocation.getMethod().getName()+" mock操作没有定义，参数 为: " + args[0];
			} 
	};
    /**
     * 改变默认返回值
     */
	@SuppressWarnings("rawtypes")
	@Test
	public void testChangeDefalutReturn() {
		//对应没有mock的操作行为，返回默认unstubbed 结果SmartNull，并输出mock操作
		LinkedList mockOne = Mockito.mock(LinkedList.class, Mockito.RETURNS_SMART_NULLS);
		Mockito.when(mockOne.get(0)).thenReturn("one");
		System.out.println("改变默认返回值:"+mockOne.get(0));
		System.out.println("改变默认返回值:"+mockOne.get(1));
		//对应没有mock的操作行为，返回changeDefaultValue的结果
		LinkedList mockTwo = Mockito.mock(LinkedList.class, changeDefaultValue);
		Mockito.when(mockTwo.get(0)).thenReturn("two");
		System.out.println("改变默认返回值:"+mockTwo.get(0));
		System.out.println("改变默认返回值:"+mockTwo.get(1));
	}
    /**
     * 捕获函数的参数值
     */
	@Test
	public void testGetParams() {
		ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class); 
		IUserService userServiceMock = Mockito.mock(IUserService.class);
		userServiceMock.saveUser(new User("donald",23));
		//确保保存用户调用
		Mockito.verify(userServiceMock).saveUser(argument.capture()); 
		//断言保存用户名称
		assertEquals("用户保存异常","John", argument.getValue().getUserName());
	}

	/**
	 * 部分Mock	
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testOther() {
		//you can create partial mock with spy() method: 
		List mock = Mockito.spy(new LinkedList()); 
		//you can enable partial mock capabilities selectively on mocks: 
//		Foo mock = Mockito.mock(Foo.class); 
		//Be sure the real implementation is 'safe'. 
		//If real implementation throws exceptions or depends on 
		//specific state of the object then you're in trouble. 
		//调用真实方法
		Mockito.when(mock.get(0)).thenCallRealMethod();
		System.out.println("部分Mock"+mock.get(0));

	}
	/**
	 * 重置Mock对象
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testReset() {
		List mock = Mockito.mock(List.class); 
		Mockito.when(mock.size()).thenReturn(10);
		mock.add(1);
		Mockito.reset(mock); //at this point the mock forgot any interactions & stubbing
		//重置mock对象的交互和stub过后，交互行为为0
		Mockito.verifyZeroInteractions(mock);

		
	}
    /**
     * 序列化
     */
	@SuppressWarnings("unchecked")
	@Test
	public void testSerializable() {
		List<Object> list = new ArrayList<Object>(); 
		List<Object> spy = Mockito.mock(ArrayList.class, Mockito.withSettings() 
				.spiedInstance(list) 
				.defaultAnswer(Answers.CALLS_REAL_METHODS.get()) 
				.serializable());
	}
	/**
	 * 1.检查mock操作是否超时
	 * 2.检验mock操作是否在指定时间内，调用指定次数
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testMockTimeout() {
		List mock = Mockito.mock(List.class); 
		//passes when add() is called within given time span 
		Mockito.verify(mock, Mockito.timeout(100)).add("one"); 
		//above is an alias to: 
		Mockito.verify(mock, Mockito.timeout(100).times(1)).add("one"); 
		//passes when add() is called *exactly* 2 times within given time span 
		Mockito.verify(mock, Mockito.timeout(100).times(2)).add("twice");
		//passes when add() is called *at least* 2 times within given time span 
		Mockito.verify(mock, Mockito.timeout(100).atLeast(2)).add("atLeast"); 
		//verifies add() within given time span using given verification mode 
		//useful only if you have your own custom verification modes. 
		//yourOwnVerificationMode为自己实现的校验模式
//		Mockito.verify(mock, new Timeout(100, yourOwnVerificationMode)).add("one");

	}
	/**
	 * Mock详情，是否为mock对象和spy对象
	 */
	@Test
	public void testMockDetails() {
		Mockito.mockingDetails(mock).isMock();
		Mockito.mockingDetails(mock).isSpy();
	}



}
