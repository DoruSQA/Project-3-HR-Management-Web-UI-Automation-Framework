package project.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class CustomReport implements ITestListener {

	private static final ExtentReports extent = createExtentReport();

	private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	private static ExtentReports createExtentReport() {

		String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());

		String report = System.getProperty("user.dir") + "/reports/AutomationReport_" + timestamp + ".html";

		ExtentSparkReporter spark = new ExtentSparkReporter(report);

		spark.config().setReportName("Automation Report");
		spark.config().setDocumentTitle("Selenium Tests");
		spark.config().setTheme(Theme.DARK);

		ExtentReports extent = new ExtentReports();
		extent.attachReporter(spark);

		String threadNr = System.getProperty("threads");
		
		extent.setSystemInfo("Environment", System.getProperty("env"));
		extent.setSystemInfo("Suite", System.getProperty("testGroup"));
		if (Integer.parseInt(threadNr) > 1)
			extent.setSystemInfo("Execution Type", "Parallel");
		else
			extent.setSystemInfo("Execution Type", "Sequential");
		extent.setSystemInfo("Infrastructre", System.getProperty("execution", "local"));
		extent.setSystemInfo("Browser", System.getProperty("browserName", "chrome"));
		extent.setSystemInfo("Resolution", System.getProperty("resolution", "desktop"));
		extent.setSystemInfo("Java", System.getProperty("java.version"));

		return extent;
	}

	
	private String getTestName2(ITestResult result) {

	    String methodName = result.getMethod().getMethodName();

	    String description = result.getMethod().getDescription();

	    if(description != null && description.contains("TC_")) {
	        return description + "_"+ methodName;
	    }

	    return methodName;
	}

	@Override
	public void onTestStart(ITestResult result) {

		Logger logger =LoggerFactory.getLogger(result.getTestClass().getRealClass());
		
		String testName = getTestName2(result);

		ExtentTest extentTest = extent.createTest(testName);

		test.set(extentTest);
		
		logger.info("========== START {} ==========", testName);
	}


	@Override
	public void onTestSuccess(ITestResult result) {

	    Logger logger = LoggerFactory.getLogger(result.getTestClass().getRealClass());

	    String testName = getTestName2(result);

	    test.get().pass("Test Passed: " + testName);

	    test.remove();

	    logger.info("========== SUCCESS {} ==========", testName);
	}


	@Override
	public void onTestFailure(ITestResult result) {

	    Logger logger = LoggerFactory.getLogger(result.getTestClass().getRealClass());

	    String testName = getTestName2(result);

	    ExtentTest extentTest = test.get();

	    if (extentTest == null) {
	        extentTest = extent.createTest(testName);
	        test.set(extentTest);
	    }

	    extentTest.fail("Test Failed: " + testName);

	    extentTest.fail(result.getThrowable().getMessage());

	    extentTest.fail("Location: " + result.getTestClass().getName());
	    extentTest.fail("Require further investigation...");

	    test.remove();

	    logger.error("========== FAILED {} ==========", testName);
	}
	
	
	@Override
	public void onTestSkipped(ITestResult result) {

	    Throwable t = result.getThrowable();

	    if (t != null) {

	        StringBuilder msg = new StringBuilder();

	        msg.append("Skipped: ").append(getTestName2(result));

	        msg.append("<br>Main reason: ").append(t.getMessage());

	        if (t.getCause() != null) {
	            msg.append("<br>Cause: ")
	               .append(t.getCause().getClass().getSimpleName())
	               .append(" - ")
	               .append(t.getCause().getMessage());
	        }

	        test.get().skip(msg.toString());
	    }

	    test.remove();
	}
	public void onTestFinish(ITestResult result) {

		if (result.getTestContext().getAttribute("testName") != null) {
			result.getTestContext().removeAttribute("testName");
		}

	}

	@Override
	public void onStart(ITestContext context) {
		extent.setSystemInfo("Tester S.D.", "QA Team");
	}

	@Override
	public void onFinish(ITestContext context) {

		extent.flush();
	}
}
