package org.donald.powermock;

import java.io.File;

/**
 * 
 * @author  donald 
 * 2017年7月24日 
 * 下午12:41:31
 */
public class PMService {
	

	/**
	 * 普通Mock： Mock参数传递的对象
	 * 
	 * @param file
	 * @return
	 */
	public boolean callArgumentInstance(File file) {
		return file.exists();
	}

	/**
	 * Mock方法内部new出来的对象
	 * 
	 * @param path
	 * @return
	 */
	public boolean callInternalInstance(String path) {
		File file = new File(path);
		return file.exists();
	}

	/**
	 * Mock普通对象的final方法
	 * 
	 * @param refer
	 * @return
	 */
	public boolean callFinalMethod(PMHelper refer) {
		return refer.isAlive();
	}

	/**
	 * Mock普通类的静态方法
	 * 
	 * @return
	 */
	public boolean callStaticMethod() {
		return PMHelper.isExist();
	}

	/**
	 * Mock 私有方法
	 * 
	 * @return
	 */
	public boolean callPrivateMethod() {
		return isExist();
	}

	private boolean isExist() {
		return false;
	}
	/**
	 * Mock系统类的静态和final方法 
	 * @param str
	 * @return
	 */
	public boolean callSystemFinalMethod(String str) {
		return str.isEmpty();
	}
	/**
	 * Mock系统类的静态和final方法 
	 * @param str
	 * @return
	 */
	public String callSystemStaticMethod(String str) {
		return System.getProperty(str);
	}

}
