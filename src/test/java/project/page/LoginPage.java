package project.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.base.BasePage;
import project.testdata.model.LoginUser;
import project.testdata.model.NewAccountUser;
import project.utils.ConfigEnvReader;
import project.utils.ElementWaits;

public class LoginPage extends BasePage {

	/* WebDriver mostenit de la BasePage ! */
	private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);

	// Constructor
	public LoginPage(WebDriver d) {
		super(d);
	}

	// Page Locators
	private By passwordInput = By.xpath("//input[@name='password']");
	private By usernameInput = By.xpath("//input[@name='username']");
	private By loginButton = By.xpath("//button[contains(@class,'orangehrm-login-button')]");
	private By badCredentialsMsg = By.xpath("//*[text()='Invalid credentials']");
	private By usernameRequiredMsg = By.xpath("//*[text()='Username']/../../descendant::*[text()='Required']");
	private By passwordRequiredMsg = By.xpath("//*[text()='Password']/../../descendant::*[text()='Required']");
	private By csrfToken =By.xpath("//input[@type='hidden' and @name='_token']");

	public By inputFieldLocator(String id) {
		return By.xpath("//input[@id='" + id + "']");
	}

	// Page Actions 
	public void enterUsername(String username) {

		ElementWaits.waitToBeInteractable(driver, usernameInput).sendKeys(username);
	}

	public String getUsernameRequiredErrorMsg() {

		return driver.findElement(usernameRequiredMsg).getText();
	}

	public String getPasswordRequiredErrorMsg() {

		return driver.findElement(passwordRequiredMsg).getText();
	}

	public boolean isBadCredentialsMessageDisplaiyed() {

		logger.info("Checking if invalid credentials message is displayed");

		try {
			return ElementWaits.waitToBeInteractable(driver, badCredentialsMsg).isDisplayed();
		} catch (Exception e) {
			logger.debug("Invalid credentials message is not displayed");
			return false;
		}
	}

	public String getBadCredentialMsg() {

		return driver.findElement(badCredentialsMsg).getText();
	}

	public void navigateToLoginPage() {

		driver.get(ConfigEnvReader.get("baseUrl"));
	}

	public void enterPassword(String password) {

		ElementWaits.waitToBeInteractable(driver, passwordInput).sendKeys(password);
	}

	public void clickLogin() {

		ElementWaits.waitToBeInteractable(driver, loginButton).click();
	}
	
	public void waitForCsrfToken() {

	    logger.info("Waiting for CSRF token");

	    ElementWaits.waitForValuePresent(driver, csrfToken);

	    logger.info("CSRF token loaded");
	}
	
	public boolean waitForLoginBtmToBeDisplayed() {

		return ElementWaits.waitToBeInteractable(driver, loginButton).isDisplayed();
	}

	public boolean isLoginButtonVisible() {

		return driver.findElement(loginButton).isDisplayed();
	}

	// Page Behavior 
	public void authentication(String Username, String Password) {

		logger.info("Starting authentication");
		waitForCsrfToken();
		enterUsername(Username);
		enterPassword(Password);
		clickLogin();

		logger.info("Authentication request submitted");
	}

	public void authentication(LoginUser loginUser) {

		logger.info("Starting authentication");

		enterUsername(loginUser.getUsername());
		enterPassword(loginUser.getPassword());
		clickLogin();

		logger.info("Authentication request submitted");
	}

	public void authentication(NewAccountUser newAccountUser) {

		logger.info("Starting authentication");

		enterUsername(newAccountUser.getUsername());
		enterPassword(newAccountUser.getPassword());
		clickLogin();

		logger.info("Authentication request submitted");
	}
}