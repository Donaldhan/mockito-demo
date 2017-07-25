/**
 * 
 */
package powermock;

import static org.junit.Assert.*;

import java.io.File;

import org.donald.powermock.PMHelper;
import org.donald.powermock.PMService;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * PowerMock示例
 * @RunWith：这个是指定使用的单元测试执行类
 * @author donald 2017年7月24日 下午12:53:37
 */
@RunWith(PowerMockRunner.class)
public class TestPMService {
	PMService pmService;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		pmService = new PMService();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		pmService = null;
	}

	/**
	 * 普通Mock不需要加@RunWith和@PrepareForTest注解。
	 * {@link org.donald.powermock.PMService#callArgumentInstance(java.io.File)}
	 * @throws Exception
	 */
	@Test
	public void testCallArgumentInstance() throws Exception {
		File file = PowerMockito.mock(File.class); 
	    PowerMockito.when(file.exists()).thenReturn(true);
	    assertTrue(pmService.callArgumentInstance(file)); 

	}

	/**
	 * 当使用PowerMockito.whenNew方法时，必须加注解@PrepareForTest和@RunWith。
	 * 注解@PrepareForTest里写的类是需要mock的new对象代码所在的类。 Test method for
	 * {@link org.donald.powermock.PMService#callInternalInstance(java.lang.String)}
	 * .
	 * @throws Exception 
	 */
	@Test
	@PrepareForTest(PMService.class)
	public void testCallInternalInstance() throws Exception {
		File file = PowerMockito.mock(File.class);
		PowerMockito.whenNew(File.class).withArguments("error").thenReturn(file);
		PowerMockito.when(file.exists()).thenReturn(true);
		assertTrue(pmService.callInternalInstance("error"));
	}

	/**
	 * 当需要mock final方法的时候，必须加注解@PrepareForTest和@RunWith。
	 * 注解@PrepareForTest里写的类，是final方法所在的类。
	 * {@link org.donald.powermock.PMService#callFinalMethod(org.donald.powermock.PMHelper)}
	 */
	@Test
    @PrepareForTest(PMHelper.class) 
	public void testCallFinalMethod() {
		 PMHelper depencency =  PowerMockito.mock(PMHelper.class);
		 PowerMockito.when(depencency.isAlive()).thenReturn(true);
		 assertTrue(pmService.callFinalMethod(depencency));
	}

	/**
	 * 当需要mock静态方法的时候，必须加注解@PrepareForTest和@RunWith。
	 * 注解@PrepareForTest里写的类是静态方法所在的类。
	 * {@link org.donald.powermock.PMService#callStaticMethod()}.
	 */
	@Test
	@PrepareForTest(PMHelper.class) 
	public void testCallStaticMethod() {
	      PowerMockito.mockStatic(PMHelper.class); 
	      PowerMockito.when(PMHelper.isExist()).thenReturn(true);
	      assertTrue(pmService.callStaticMethod());
	}

	/**
	 * 和Mock普通方法一样，只是需要加注解@PrepareForTest(PMService.class)，注解里写的类是私有方法所在的类。 
	 * {@link org.donald.powermock.PMService#callPrivateMethod()}.
	 * @throws Exception 
	 */
	@Test
	@PrepareForTest(PMService.class)
	public void testCallPrivateMethod() throws Exception {
		  pmService = PowerMockito.mock(PMService.class); 
	      PowerMockito.when(pmService.callPrivateMethod()).thenCallRealMethod(); 
	      PowerMockito.when(pmService, "isExist").thenReturn(true);
	      assertTrue(pmService.callPrivateMethod());
	}

	/**
	 * 和Mock普通对象的静态方法、final方法一样，
	 * 只不过注解@PrepareForTest里写的类不一样 ，注解里写的类是需要调用系统方法所在的类。
	 * {@link org.donald.powermock.PMService#callSystemFinalMethod(java.lang.String)}
	 * .
	 */
	@Test
	@PrepareForTest(PMService.class)
	public void testCallSystemStaticMethod() {
		 PowerMockito.mockStatic(System.class); 
	     PowerMockito.when(System.getProperty("jdk")).thenReturn("1.7");
	     assertEquals("1.7", pmService.callSystemStaticMethod("jdk")); 
	}

}
