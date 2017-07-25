package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PropertiesUtil {
	private static final Logger log  = LoggerFactory.getLogger(PropertiesUtil.class);
    private static volatile PropertiesUtil instance = null;
    private static Properties properties = null;
    static{
    	 if (properties == null) {
    		 properties = new Properties();
         }
         try {
        	 InputStream inputStream = Thread.currentThread().getContextClassLoader()
        	            .getResourceAsStream("application.properties");
        	 properties.load(inputStream);
         } catch (IOException e1) {
             e1.printStackTrace();
         }
    }
    public static synchronized PropertiesUtil getInstance() {
        if (instance == null) {
        	instance = new PropertiesUtil();
        }
        return instance;
    }
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    public static void main(String[] args) {
		log.info("==datasouce driver:"+PropertiesUtil.getInstance().getProperty("spring.datasource.driverClassName"));
	}
}
