package com.framework.Utility;


import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class Listners extends TestListenerAdapter {
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest test;

	public void onStart(ITestContext testContext)
	{
		htmlReporter = new ExtentHtmlReporter("D:/JavaCrux/GameCatalouge/Reports/reports.html");
		htmlReporter.config().setDocumentTitle("Automation MY Report");
		htmlReporter.config().setReportName("REST API Testing Report");

		
		htmlReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Host name", "localhost");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("user", "pavan");
	}

	public void onTestSuccess(ITestResult result) {
		test = extent.createTest(result.getName());

		test.log(Status.PASS, "Test case is Passed you all are happy" + result.getName());
	}

	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getName());

		test.log(Status.FAIL, "Test case is FAIL you all are SAD" + result.getName());
	}

	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getName());

		test.log(Status.SKIP, "Test case is SKIPPED you all are SKIPPED" + result.getName());
	}

	public void onFinish(ITestContext textContext) {
		extent.flush();
	}
}
