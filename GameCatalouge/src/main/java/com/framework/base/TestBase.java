package com.framework.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeClass;

public class TestBase {

	public static Logger logger;

	@BeforeClass
	public void steup() throws FileNotFoundException, IOException {
		logger = Logger.getLogger("WageCalculation");// Added logger
		PropertyConfigurator.configure("log4j.properties"); // added loger

		logger.setLevel(Level.DEBUG);
	}
}
