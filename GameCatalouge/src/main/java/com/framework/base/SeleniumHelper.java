package com.framework.base;

import java.io.IOException;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.framework.Utility.FrameworkException;
import com.framework.Utility.GlobalConfiguration;

public class SeleniumHelper extends TestBase {

	public void openBrowser(String browserName) {
		openBrowser(browserName, true);
	}

	public void openBrowser(String browserName, boolean isWindowMaximized) {
		try {
			int maxRetryCount = 3;
			if (browserName == null) {
				browserName = GlobalConfiguration.getDynamicProperty("browserName");
			}
			for (int counter = 0; counter < maxRetryCount; counter++) {
				try {
					initWebDriver(browserName, isWindowMaximized);
					// debug why this
					// logger.info("Open Browser");
					break;
				} catch (WebDriverException e) {
					if (counter < (maxRetryCount - 1)) {
						logger.error("Unable to open browser, retrying it again...");
					} else {
						throw e;
					}
				}
			}
		} catch (Throwable t) {
			throw new FrameworkException("Could not open Browser" + browserName + "\"");
		}
	}

	private void initWebDriver(String browserName, boolean isWindowMaximized) throws FrameworkException, IOException {
		int globalSyncSeconds = -1;
		try {
			globalSyncSeconds = Integer.parseInt(GlobalConfiguration.getDynamicProperty("globalSync.seconds"));
		} catch (Exception e) {

			throw new FrameworkException("Invalid globalSync.seconds value in globalproperties.txt: \""
					+ GlobalConfiguration.getDynamicProperty("globalSync.seconds") + "\"");

		}
		driver = WebdriverFactory.createInstance(browserName);
		if (isWindowMaximized) {
			driver.manage().window().maximize();
		}
		driver.manage().timeouts().implicitlyWait(globalSyncSeconds, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();

	}

	public void openUrl(String url) {
		try {
			driver.get(url);
		} catch (Throwable t) {
			throw new FrameworkException("Cannot naviiiagte to the url" + url + t);
		}
	}

	public WebDriver getdriver() {
		return driver;
	}

	public void click(String fieldName, String locatorType, String locatorValue) throws Exception {
		try {
			WebElement element = (WebElement) getBy(locatorType, locatorValue);
			element.click();

		} catch (Exception e) {
			throw new Exception("Error while clicking on " + fieldName);
		}
	}

	public void clickExceutor(String fieldName, String locatorType, String locatorValue) throws Exception{
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement button = driver.findElement(getBy(locatorType, locatorValue));
			js.executeScript("arguments[0].click();", button);
		} catch (Throwable t) {
			throw new Exception("Error while clicking on " + fieldName);
		}
	}

	public By getBy(String locatorType, String locatorValue) throws FrameworkException, IOException {

		if (locatorType.equalsIgnoreCase("globalOR")) {
			String orPropertyValue = GlobalConfiguration.getORProperty(locatorValue);
			locatorType = orPropertyValue.split("~")[0];
			locatorValue = orPropertyValue.split("~")[1];
		}
		By by = null;
		switch (locatorType.toLowerCase()) {
		case "id":
			by = By.id(locatorValue);
			break;
		case "name":
			by = By.name(locatorValue);
			break;
		case "xpath":
			by = By.xpath(locatorValue);
			break;
		case "cssselector":
			by = By.cssSelector(locatorValue);
			break;
		case "classname":
			by = By.className(locatorValue);
			break;
		case "linktext":
			by = By.linkText(locatorValue);
			break;
		case "partiallinktext":
			by = By.partialLinkText(locatorValue);
			break;
		default:
			throw new FrameworkException("Invalid locator type defined: " + locatorType);
		}
		return by;
	}

	 /**
     * wait for specified field got visible,
     * located by given locator
     * @param fieldName
     * @param locatorType
     * @param locatorValue
     * @param waitSecondsString
	 * @throws Exception 
     */
    public void explicitWaitForElementGetVisible(String fieldName, 
            String locatorType, String locatorValue, 
            String waitSecondsString) throws Exception {
        int waitSeconds = 15;
        try {
            waitSeconds = Integer.parseInt(waitSecondsString);
        } catch (Exception e) {
            logger.info( "Invalid wait seconds value,"
                    + " taken default value of 15 seconds", e);
        }
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, waitSeconds);
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    getBy(locatorType, locatorValue)));
        } catch (Throwable t) {
            throw new Exception("fieldName"
                    + " did not got visible" + "\n"+  t.getMessage());
        }
    }

    
    
    
    
    
    private WebDriver driver;
}
