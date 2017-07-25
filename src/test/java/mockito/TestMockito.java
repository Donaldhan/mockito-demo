package mockito;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * http://www.jianshu.com/p/77db26b4fb54
 * @author donald
 * 2017年7月18日
 * 下午1:08:12
 */
public class TestMockito {
	private static final Logger log  = LoggerFactory.getLogger(TestMockito.class);
	@SuppressWarnings("rawtypes")
	private LinkedList mockedList;
	@Before
	public void setUp() throws Exception {
		// 模拟LinkedList 的一个对象 
		 mockedList = Mockito.mock(LinkedList.class); 
	}

	@After
	public void tearDown() throws Exception {
		mockedList= null;
	}
   /**
    * 模拟对象
    */
	@Test
	public void testMockObject() {
		// 此时调用get方法，会返回null，因为还没有对方法调用的返回值做模拟 
		System.out.println("模拟对象:"+mockedList.get(0));
		log.info("模拟对象:"+mockedList.get(0));

	}
	/**
	 * 模拟方法调用的返回值
	 */
	@Test
	public void testMockReturn() {
		// 模拟LinkedList 的一个对象 
		// 模拟获取第一个元素时，返回字符串first。  给特定的方法调用返回固定值在官方说法中称为stub。
		Mockito.when(mockedList.get(0)).thenReturn("first"); 
		// 此时打印输出first 
		System.out.println("模拟方法调用的返回值:"+mockedList.get(0));
	}
	/**
	 * 模拟方法调用抛出异常
	 */
	@Test
	public void testMockThrowException() {
		// 模拟获取第二个元素时，抛出RuntimeException  
		Mockito.when(mockedList.get(1)).thenThrow(new RuntimeException()); 
		// 此时将会抛出RuntimeException  
		System.out.println("模拟方法调用抛出异常:"+mockedList.get(1));

	}
	/**
	 * 如果一个函数没有返回值类型，那么可以使用此方法模拟异常抛出
	 */
	@Test
	public void testMockThrowExceptionWithoutReturn() {
		Mockito.doThrow(new RuntimeException()).when(mockedList).clear();
		//following throws RuntimeException:
		mockedList.clear();
		System.out.println("模拟方法没有返回值函数调用抛出异常:"+mockedList.get(1));
	}
	/**
	 * 模拟调用方法时的参数匹配
	 */
	@Test
	public void testMockMatchParams() {
		// anyInt()匹配任何int参数，这意味着参数为任意值，其返回值均是element  
		//stubbing using built-in anyInt() argument matcher
		Mockito.when(mockedList.get(Mockito.anyInt())).thenReturn("element"); 
		// 此时打印是element 
		//following prints "element" 
		System.out.println("模拟调用方法时的参数匹配:"+mockedList.get(999));
		//stubbing using custom matcher (let's say isValid() returns your own matcher implementation): 
//		Mockito.when(mockedList.contains(argThat(isValid()))).thenReturn("element"); 
		//you can also verify using an argument matcher 
		//校验mock对象方法是否调用一次，等价于
		Mockito.verify(mockedList).get(Mockito.anyInt()); 
		//argument matchers can also be written as Java 8 Lambdas 
//		Mockito.verify(mockedList).add(someString -> someString.length() > 5);

	}
	/**
	 * 模拟方法调用次数
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testMockMethodInvokerTimes() {
		// 调用add一次 
		mockedList.add("once"); 
		// 下面两个写法验证效果一样，均验证add方法是否被调用了一次
		//这个校验同时校验方法名和参数是否相等，且调用次数相等，则通过。
		Mockito.verify(mockedList).add("once"); 
		Mockito.verify(mockedList, Mockito.times(1)).add("once");
		
		//using mock 
		mockedList.add("once");
		mockedList.add("twice"); 
		mockedList.add("twice"); 
		mockedList.add("three times");
		mockedList.add("three times");
		mockedList.add("three times"); 
		//following two verifications work exactly the same - times(1) is used by default 
		Mockito.verify(mockedList).add("once"); 
		Mockito.verify(mockedList, Mockito.times(1)).add("once"); 
		//exact number of invocations verification 
		Mockito.verify(mockedList, Mockito.times(2)).add("twice"); 
		Mockito.verify(mockedList, Mockito.times(3)).add("three times"); 
		//verification using never(). never() is an alias to times(0) 
		Mockito.verify(mockedList, Mockito.never()).add("never happened"); 
		//verification using atLeast()/atMost() 
		Mockito.verify(mockedList, Mockito.atLeastOnce()).add("three times"); 
		Mockito.verify(mockedList, Mockito.atLeast(2)).add("five times"); 
		Mockito.verify(mockedList, Mockito.atMost(5)).add("three times");

	}
	/**
	 * 校验行为
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testMockAction() {
		// mock creation 
		// using mock object 
		mockedList.add("one"); 
		mockedList.clear(); 
		//verification,校验mode
		Mockito.verify(mockedList).add("one"); 
		Mockito.verify(mockedList).add("two"); 
		Mockito.verify(mockedList).clear();

	}
	/**
	 * 模拟方法调用(Stubbing)
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testMockMethodInvoker() {
		//You can mock concrete classes, not just interfaces 
		LinkedList mockedListx = Mockito.mock(LinkedList.class);
		//stubbing 
		Mockito.when(mockedListx.get(0)).thenReturn("first"); 
		Mockito.when(mockedListx.get(1)).thenThrow(new RuntimeException());
		//following prints "first" 
		System.out.println("模拟方法调用(Stubbing):"+mockedListx.get(0));
		//following prints "null" because get(2) was not stubbed 
		System.out.println("模拟方法调用(Stubbing):"+mockedListx.get(2));
		//following throws runtime exception 
		System.out.println("模拟方法调用(Stubbing):"+mockedListx.get(1)); 
		//following prints "null" because get(999) was not stubbed 
		System.out.println("模拟方法调用(Stubbing):"+mockedListx.get(999)); 
		Mockito.verify(mockedListx).get(0);

	}
	/**
	 * 校验方法调用顺序
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testMockMethodInvokerOrder() {
		// A. Single mock whose methods must be invoked in a particular order 
		//校验单个方法的调用顺序
		List singleMock = Mockito.mock(List.class); 
		//using a single mock 
		singleMock.add("was added first"); 
		singleMock.add("was added second"); 
		//create an inOrder verifier for a single mock 
		InOrder inOrder = Mockito.inOrder(singleMock); 
		//following will make sure that add is first called with 
		//"was added first, then with "was added second" 
		//确保方法按顺序调用
//		inOrder.verify(singleMock).add("was added second"); 
		inOrder.verify(singleMock).add("was added first"); 
		inOrder.verify(singleMock).add("was added second"); 
		// B. Multiple mocks that must be used in a particular order 
		//校验两个mock对象的方法是否按顺序调用
		List firstMock = Mockito.mock(List.class); 
		List secondMock = Mockito.mock(List.class); 
		//using mocks 
		firstMock.add("firstMock was called first"); 
		secondMock.add("secondMock was called second"); 
		//create inOrder object passing any mocks that need to be verified in order 
		InOrder inOrderx = Mockito.inOrder(firstMock, secondMock); 
		//following will make sure that firstMock was called before secondMock 
		//确保两个mock的对象的方法，按顺序调用
		inOrderx.verify(secondMock).add("firstMock was called second"); 
		inOrderx.verify(firstMock).add("secondMock was called first"); 
//		inOrderx.verify(secondMock).add("was called second"); 
		// Oh, and A + B can be mixed together at will
	}
	/**
	 * 校验方法是否从未调用
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testMockMethodHasInvoker() {
		//using mocks - only mockOne is interacted 
		List mockOne = Mockito.mock(List.class); 
		List mockTwo = Mockito.mock(List.class); 
		List mockThree = Mockito.mock(List.class); 
		mockOne.add("one"); 
		//ordinary verification 
		 Mockito.verify(mockOne).add("one"); 
		//verify that method was never called on a mock 
		 Mockito.verify(mockOne, Mockito.never()).add("two"); 
//		 Mockito.verify(mockOne, Mockito.never()).add("one");
		 mockTwo.add("one");
		 //verify that other mocks were not interacted 
		 //校验mock对象没有交互
		 Mockito.verifyZeroInteractions(mockTwo, mockThree);
	}
}
