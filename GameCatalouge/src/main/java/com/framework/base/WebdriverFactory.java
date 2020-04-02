package com.framework.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;


import java.io.File;
import java.io.IOException;


import org.apache.commons.lang3.StringUtils;

import com.framework.Utility.FrameworkException;
import com.framework.Utility.GlobalConfiguration;

public class WebdriverFactory {

	
	
	static WebDriver createInstance(String browserName) throws IOException {

		WebDriver driver = null;
		switch (browserName) {
		case "firefox":
			System.setProperty("webdriver.gecko.driver",
					GlobalConfiguration.getDynamicProperty("browser.firefoxDriver"));

			String firefoxBinaryPath = GlobalConfiguration.getDynamicProperty("firefox.BinaryPath");
			FirefoxBinary firefoxBinary = null;
			if (StringUtils.isEmpty(firefoxBinaryPath)) {
				throw new FrameworkException("firefoxBinaryPath not defined in " + "global.proerties file.");
			}
			firefoxBinary = new FirefoxBinary(new File(firefoxBinaryPath));

			String firefoxProfileName = GlobalConfiguration.getDynamicProperty("firefox.ProfileName");
			FirefoxProfile firefoxProfile;
			if (!StringUtils.isEmpty(firefoxProfileName)) {
				ProfilesIni profileINI = new ProfilesIni();
				firefoxProfile = profileINI.getProfile(firefoxProfileName);
			} else {
				firefoxProfile = new FirefoxProfile();
			}

			FirefoxOptions firefoxOptions = new FirefoxOptions().setBinary(firefoxBinary).setProfile(firefoxProfile);
			try {
				driver = new FirefoxDriver(firefoxOptions);
			} catch (Throwable t) {
				throw new FrameworkException("Error while creating driver instance " + t);
			}
			break;
		case "chrome":
			System.setProperty("webdriver.chrome.driver",
					GlobalConfiguration.getDynamicProperty("browser.chromeDriver"));
			try {
				driver = new ChromeDriver();
			} catch (Throwable t) {
				throw new FrameworkException("Error while creating driver instance " + t);
			}
			break;
		case "ie":
			System.setProperty("webdriver.ie.driver",
					GlobalConfiguration.getDynamicProperty("browser.ieDriver"));
			try {
				driver = new InternetExplorerDriver();
			} catch (Throwable t) {
				throw new FrameworkException("Error while creating driver instance " + t);
			}
			break;
		case "edge":
			System.setProperty("webdriver.edge.driver", 
					GlobalConfiguration.getDynamicProperty("browser.edgeDriver"));
			try {
				driver = new EdgeDriver();
			} catch (Throwable t) {
				throw new FrameworkException("Error while creating driver instance " + t);
			}
			break;
		default:
			throw new FrameworkException("Browser not supported: " + browserName);
		}
		return driver;
	}
}
