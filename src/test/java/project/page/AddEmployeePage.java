package project.page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.base.BasePage;
import project.testdata.modeldata.EmployeeUser;
import project.testdata.modeldata.NewAccountUser;
import project.utils.ElementWaits;

public class AddEmployeePage extends BasePage {

	private static final Logger logger = LoggerFactory.getLogger(AddEmployeePage.class);
	
	// Constructor
	public AddEmployeePage(WebDriver d) {
		super(d);
	}
	
	// Locators
	private By saveButton = By.xpath("//button[@type='submit']");
	private By toggleSwitch = By.xpath("//*[text()='Create Login Details']/following-sibling::div//span");
	private By usernameInput = By.xpath("//*[text()='Username']/../following-sibling::*/input");
	private By passwordInput = By.xpath("//*[text()='Password']/../following-sibling::*/input");
	private By confirmPasswordInput = By.xpath("//*[text()='Confirm Password']/../following-sibling::*/input");
	private By selectEnabledStatus = By.xpath("//*[text()='Enabled']");
	private By profilePhotoInput = By.xpath("//input[@type='file']");
	private By profilePhotoSrc = By.xpath("//img[@alt='profile picture' and @class='employee-image']");
	private By employeeIdErrorMsg = By.xpath("//*[text()='Employee Id already exists']");
	private By inputFieldErrorMsg = By.xpath("//span[contains(@class,'input-field-error-message')]");
	private By profilePictureErrorMsg = By.xpath("//*[contains(@class, 'input-field-error-message')]");

	// Page Actions
	public String getPprofilePictureTypeErrorMsg() {

		return ElementWaits.waitToBeInteractable(driver, profilePictureErrorMsg).getText();
	}

	public String getPprofilePictureSizeErrorMsg() {

		return ElementWaits.waitToBeInteractable(driver, profilePictureErrorMsg).getText();
	}

	public void toggleLoginDetails() {

		ElementWaits.waitToBeInteractable(driver, toggleSwitch).click();
	}

	public boolean isEmployeeIdErrorMsgDisplayed() {

		return ElementWaits.waitToBeInteractable(driver, employeeIdErrorMsg).isDisplayed();
	}

	public boolean isInputFieldErrorMsgDisplayed() {

		return ElementWaits.waitToBeInteractable(driver, inputFieldErrorMsg).isDisplayed();
	}

	public String getInputFieldErrorMsg() {

		return ElementWaits.waitToBeInteractable(driver, inputFieldErrorMsg).getText();
	}

	public void selectLogoutOption() {

		ElementWaits.waitToBeInteractable(driver, logoutSelectOption).click();
	}

	public String getProfileImageSrc() {

		return driver.findElement(profilePhotoSrc).getAttribute("src");
	}

	public void addProfilePhoto(String pathOfPhoto) {

		driver.findElement(profilePhotoInput).sendKeys(pathOfPhoto);
	}

	public void enterFirstname(String username) {

		ElementWaits.waitToBeInteractable(driver, firstNameInputBox).sendKeys(username);
	}

	public boolean waitForConfirmationMsgToBeDisplayed() {

		return ElementWaits.waitToBeInteractable(driver, confirmationMsg).isDisplayed();
	}

	public boolean isEmployeeCreated() {
		logger.info("Checking if employee was successfully created");

		try {
			return ElementWaits.waitToBeInteractable(driver, confirmationMsg).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void clickEnabledStatus() {

		ElementWaits.waitToBeInteractable(driver, selectEnabledStatus).click();
	}

	public void enterUsername(String username) {

		ElementWaits.waitToBeInteractable(driver, usernameInput).sendKeys(username);
	}

	public void enterPassword(String password) {

		ElementWaits.waitToBeInteractable(driver, passwordInput).sendKeys(password);
	}

	public void enterConfirmPassword(String confirmPassword) {

		ElementWaits.waitToBeInteractable(driver, confirmPasswordInput).sendKeys(confirmPassword);
	}

	public void enterLastname(String lastname) {

		ElementWaits.waitToBeInteractable(driver, lastNameInputBox).sendKeys(lastname);
	}

	public void enterEmployeeId(String employeeId) {


		ElementWaits.waitToBeInteractable(driver, employeeIdInputBox).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		ElementWaits.waitToBeInteractable(driver, employeeIdInputBox).sendKeys(Keys.DELETE);
		ElementWaits.waitToBeInteractable(driver, employeeIdInputBox).sendKeys(employeeId);
	}

	public void clickSaveButton() {

		ElementWaits.waitToBeInteractable(driver, saveButton).click();
	}
	
	// Page Behavior
	public void fillEmployeeInformation(EmployeeUser employee) {
		
		logger.info("Filling employee information");
		enterFirstname(employee.getFirstname());
		enterLastname(employee.getLastname());
		enterEmployeeId(employee.getEmployeeId());
	}

	public void createNewAccount(NewAccountUser newAccountUser) {
		
		logger.info("Creating login details for new employee");
		enterUsername(newAccountUser.getUsername());
		enterPassword(newAccountUser.getPassword());
		enterConfirmPassword(newAccountUser.getConfirmPasswod());
	}

	public void logout() {
		
		logger.info("Logging out");
		clickOnProfilePicture();
		selectLogoutOption();
	}
}