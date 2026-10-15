package project.base;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import project.page.AddEmployeePage;
import project.page.DashboardPage;
import project.page.EmployeeListPage;
import project.page.LoginPage;
import project.page.NewAccountPage;
import project.page.PersonalDetailsPage;
import project.testdata.TestDataFactory;
import project.testdata.modeldata.EmployeeUser;
import project.testdata.modeldata.LoginUser;
import project.testdata.modeldata.NewAccountUser;
import project.tests.LoginTests;
import project.utils.ConfigEnvReader;

public abstract class BaseTest {
	
	// WebDriver
	public WebDriver driver;
	
	// Web Pages
	public LoginPage loginPage;
	public DashboardPage dashboard;
	public EmployeeListPage employeeListPage;
	public AddEmployeePage addEmployeePage;
	public NewAccountPage newAccountPage;
	public PersonalDetailsPage personalDetailsPage;
	
	// Logger
	private static Logger logger = LoggerFactory.getLogger(BaseTest.class);
	
	// Soft Assert
	public SoftAssert softAssertion;
	
	// Test Data
	public LoginUser adminUser;
	public EmployeeUser validEmployee;
	public NewAccountUser newAccountUser;
	

	public WebDriver createDriver() {

		logger.info("Creating WebDriver instance");

		WebDriver driver = null;
		MutableCapabilities options = null;

		String browser = System.getProperty("browserName", "chrome");

		if (browser.equalsIgnoreCase("chrome")) {

			ChromeOptions chromeOptions = new ChromeOptions();

			// Chrome Browser configuration
			chromeOptions.addArguments("--start-maximized");
			chromeOptions.addArguments("--disable-notifications");
			chromeOptions.addArguments("--disable-popup-blocking");
			chromeOptions.addArguments("--disable-translate");
			chromeOptions.addArguments("--incognito");

			chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

			Map<String, Object> prefs = new HashMap<>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			prefs.put("profile.password_manager_leak_detection", false);

			chromeOptions.setExperimentalOption("prefs", prefs);

			options = chromeOptions;

		} else if (browser.equalsIgnoreCase("firefox")) {

			FirefoxOptions firefoxOptions = new FirefoxOptions();

			firefoxOptions.addArguments("--start-maximized");

			firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

			options = firefoxOptions;

		} else if (browser.equalsIgnoreCase("edge")) {

			EdgeOptions edgeOptions = new EdgeOptions();

			edgeOptions.addArguments("--start-maximized");
			edgeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

			options = edgeOptions;

		} else {

			throw new RuntimeException("Browser necunoscut: " + browser);
		}

		if (options instanceof ChromeOptions)
			driver = new ChromeDriver((ChromeOptions) options);

		else if (options instanceof FirefoxOptions)
			driver = new FirefoxDriver((FirefoxOptions) options);

		else if (options instanceof EdgeOptions)
			driver = new EdgeDriver((EdgeOptions) options);


		return driver;
	}

	public void closeBrowser(WebDriver d) {
		if (d != null)
			d.quit();
	}

	
	@BeforeClass(alwaysRun = true)
	public void setup() {

		// Init Driver
		driver = createDriver();
		
		// Init Web Pages
		loginPage = new LoginPage(driver);
		dashboard = new DashboardPage(driver);
		employeeListPage = new EmployeeListPage(driver);
		addEmployeePage = new AddEmployeePage(driver);
		newAccountPage = new NewAccountPage(driver);
		personalDetailsPage = new PersonalDetailsPage(driver);
		
		// Init Soft Assertion
		softAssertion = new SoftAssert();
		
		// Init Test Data
		adminUser = TestDataFactory.createAdminUserData();
		validEmployee = TestDataFactory.createValidEmployeeUser();
		newAccountUser = TestDataFactory.createValidUserAccount();
	}

	@BeforeMethod(alwaysRun = true)
	public void navigate() {

		driver.get(ConfigEnvReader.get("baseUrl"));
	}

	@AfterMethod(alwaysRun = true)
	public void cleanupAfterTest(ITestResult result) {

	    if (!LoginTests.class.equals(result.getTestClass().getRealClass())) 
	        cleanupResourceCreated();
	    
	    clearBrowserState();
	    softAssertion = new SoftAssert();
	}
	
	@AfterClass(alwaysRun = true)
	public void closeBrowser() {

		if (driver != null) 
			driver.quit();
	}
	
	private void cleanupResourceCreated()
	{
		try {
			// Logout from that session
			newAccountPage.logout();
			// New session with useradmin
			loginPage.navigateToLoginPage();
			loginPage.authentication(adminUser);
			dashboard.openPersonnelInformationManagement();
		if(employeeListPage.isEmployeePresent(validEmployee.getEmployeeId()))
		{
			employeeListPage.deleteRecord(validEmployee.getEmployeeId());
			employeeListPage.isConfirmationMsgDisplayed();
		}
		}catch (Exception e)
		{
			logger.error("Eroare in cleanUpResources " + e);
		}

	}
	
	private void clearBrowserState() {

	    driver.manage().deleteAllCookies();
	    ((JavascriptExecutor) driver).executeScript("window.localStorage.clear();");
	    ((JavascriptExecutor) driver).executeScript("window.sessionStorage.clear();");
	}

}
