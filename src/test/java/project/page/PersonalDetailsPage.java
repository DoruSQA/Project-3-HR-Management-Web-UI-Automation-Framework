package project.page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.base.BasePage;
import project.utils.ElementWaits;

public class PersonalDetailsPage extends BasePage {

	private static final Logger logger = LoggerFactory.getLogger(PersonalDetailsPage.class);

	// Constructor
	public PersonalDetailsPage(WebDriver d) {
		super(d);
	}
	
	// Locators
	private By saveBtnFirstSection = By.xpath("//div[@class='orangehrm-horizontal-padding orangehrm-vertical-padding']//button[@type='submit']");
	private By employeeListBtn = By.xpath("//*[text()='Employee List']");
	private By employeeIdDubplicateErrorMsg = By.xpath("//*[@class='oxd-text oxd-text--span oxd-input-field-error-message oxd-input-group__message']");
	private By personalDetailsHeading = By.xpath("//h6[contains(.,'Personal Details')]");
	
	// Page Actions
	public String getEmployeeIdDubplicateErrorMsg() {

		logger.info("Retrieving duplicate employee ID validation message");

		try {
			return ElementWaits.waitToBeInteractable(driver, employeeIdDubplicateErrorMsg).getText();
		} catch (Exception e) {
			logger.debug("Duplicate employee ID validation message not displayed");
			return "timeout exceeded";
		}
	}

	public boolean waitForPersonalDetailsHeadingToBeDisplayed() {

		logger.info("Waiting for Personal Details page to be displayed");

		try {
			return ElementWaits.waitToBeInteractable(driver, personalDetailsHeading).isDisplayed();
		} catch (Exception e) {
			logger.debug("Personal Details page was not displayed");
			return false;
		}
	}

	public boolean waitForConfirmationMsgToBeDisplayed() {

		logger.info("Waiting for confirmation message");

		try {
			return ElementWaits.waitToBeInteractable(driver, confirmationMsg).isDisplayed();
		} catch (Exception e) {
			logger.debug("Confirmation message was not displayed");
			return false;
		}
	}

	public boolean confirmEmployeeCreation() {

		logger.info("Confirming employee creation");

		try {
			return ElementWaits.waitForVisibility(driver, confirmationMsg).isDisplayed();
		} catch (Exception e) {
			logger.debug("Employee creation confirmation not found");
			return false;
		}
	}

	public void clickSaveButton() {

		ElementWaits.waitToBeInteractable(driver, saveBtnFirstSection).click();
	}

	public void clickEmployeeListButton() {

		ElementWaits.waitToBeInteractable(driver, employeeListBtn).click();
	}

	public PersonalDetailsPage updateEmployeeId(String employeeId) {

		ElementWaits.waitToBeInteractable(driver, employeeIdInputBox).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		driver.findElement(employeeIdInputBox).sendKeys(Keys.DELETE);
		driver.findElement(employeeIdInputBox).sendKeys(employeeId);

		return this;
	}

	public PersonalDetailsPage updateFirstName(String firstname) {

		ElementWaits.waitToBeInteractable(driver, firstNameInputBox).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		driver.findElement(firstNameInputBox).sendKeys(Keys.DELETE);
		driver.findElement(firstNameInputBox).sendKeys(firstname);

		return this;
	}

	public PersonalDetailsPage updateLastName(String lastname) {

		ElementWaits.waitToBeInteractable(driver, lastNameInputBox).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		driver.findElement(lastNameInputBox).sendKeys(Keys.DELETE);
		driver.findElement(lastNameInputBox).sendKeys(lastname);

		return this;
	}

}