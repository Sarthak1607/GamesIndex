
/**
 * This class is used to store and get the 
 * Dynamic Property from the property File.
 */
package com.framework.Utility;

import java.io.FileInputStream;
import java.util.logging.Level;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.logging.Logger;

public class GlobalConfiguration {

	private static Properties globalORProperties=new Properties();
	private static Properties globalProperties = new Properties();
	private static Logger logger;

	public static String getDynamicProperty(String propertyName) throws IOException {
		InputStream inputStream = new FileInputStream("resources/global.properties");
		globalProperties.load(inputStream);
		String propertyValue = globalProperties.getProperty(propertyName);
		if (propertyValue != null) {
			return propertyValue;
		} else {
			throw new FrameworkException("Dynamic properties not found with name \"" + propertyName + "\"");
		}
	}

	public static String writeDynamicProperty(String propertyName, String value) throws IOException {
		OutputStream outStream = new FileOutputStream("resources/global.properties");
		String propertyValue = globalProperties.getProperty(propertyName);
		globalProperties.setProperty(propertyName,value);
		globalProperties.store(outStream, null);
		if (propertyValue != null) {
			return propertyValue;
		} else {
			throw new FrameworkException("Dynamic properties not found with name \"" + propertyName + "\"");
		}
	}
	
	public static String getORProperty(String propertyName) 
	            throws FrameworkException, IOException {
		 InputStream inputStream = new FileInputStream("resources/objectrepository.properties");
		 globalORProperties.load(inputStream);
	        String propertyValue = globalORProperties.getProperty(propertyName);
	        if (propertyValue != null) {
	            return propertyValue;
	        } else {
	            throw new FrameworkException("Global OR properties not found with name \""
	                    + propertyName + "\"");
	        }
	    }
	/**
     * get formated object repository for given property name and 
     * parameters
     * @param propertyName
     * @param params
     * @return
	 * @throws IOException 
	 * @throws FrameworkException 
     */
	public static String[] getFormatedOR(String propertyName, Object... params) throws FrameworkException, IOException {
		
		String orPropertyValue = GlobalConfiguration.getORProperty(propertyName);
		String formatedORPropertyValue = MessageFormat.format(orPropertyValue, params);
		
		if (formatedORPropertyValue.split("~").length != 2) {
			throw new FrameworkException("Invalid OR property: "+ propertyName);
		}
		return formatedORPropertyValue.split("~");
	}
}
