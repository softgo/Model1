package com.bruce.gogo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
 * 配置文件的公共使用类.
 * 
 * @author admin
 *
 */
public class ConfigConstants {

	static Logger logger  = Logger.getLogger(ConfigConstants.class.getName());

	protected static Properties prop = new Properties();

    /**
     * 静态读入属性文件到Properties p变量中
     */
    protected static void init(String propertyFileName) {
        InputStream stream = null;
        try {
        	stream = ConfigConstants.class.getClassLoader().getResourceAsStream(propertyFileName);
            if (stream != null){
                prop.load(stream);
             }
        } catch (IOException e) {
            logger.error("load " + propertyFileName + " into Constants error!");
        }
        finally {
            if (stream != null) {
                try {
                	stream.close();
                } catch (IOException e) {
                    logger.error("close " + propertyFileName + " error!");
                }
            }
        }
    }

    /**
     * 封装了Properties类的getProperty函数,使p变量对子类透明.
     *
     * @param key          property key.
     * @param defaultValue 当使用property key在properties中取不到值时的默认值.
     */
    protected static String getProperty(String key, String defaultValue) {
        return prop.getProperty(key, defaultValue);
    }

}
