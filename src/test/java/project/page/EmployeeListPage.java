package project.page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.base.BasePage;
import project.utils.ElementWaits;

public class EmployeeListPage extends BasePage {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeListPage.class);
	
	// Constructor
	public EmployeeListPage(WebDriver d) {
		super(d);
	}
	
	// Page Locators
	private By addEmplyeeBtn = By.xpath("//li/*[text()='Add Employee']");
	private By employeeIdInput = By.xpath("//*[text()='Employee Id']/../following-sibling::div//input");
	private By employeeNameInputBox = By.xpath("//*[text()='Employee Name']/../following-sibling::div//input");
	private By searchBtn = By.xpath("//button[@type='submit']");
	private By deleteIcon = By.xpath("//div[contains(@class,'table-body')]//i[contains(@class,'trash')]");
	private By deleteCheckBox = By.xpath("//div[contains(@class,'oxd-table-card')]//span[contains(@class,'oxd-checkbox-input')]");
	private By updateIcon = By.xpath("//div[contains(@class,'table-body')]//i[contains(@class,'fill')]");
	private By confirmDeleteBtn = By.xpath("//div[@role='document']//button[contains(.,'Delete')]");
	private By cancelDeleteBtn = By.xpath("//button[contains(.,'No, Cancel')]");
	private By firstEmployeeIdFromTable = By.xpath("//div[@class='oxd-table-body']/div[1]/div/div[2]/div");
	private By searchedRecords = By.xpath("//span[@class='oxd-text oxd-text--span' and contains(.,'Record')]");
	private By noRecodsFoundConfirmationMsg = By.xpath("//div[@id='oxd-toaster_1']/descendant::*[text()='No Records Found']");
	private By recordFoundMsg = By.xpath("//div[@class='orangehrm-horizontal-padding orangehrm-vertical-padding']/span[contains(.,'Record Found')]");
	private By deleteSelectedBtn = By.xpath("//button[contains(@class,'oxd-button oxd-button--medium oxd-button--label-danger orangehrm-horizontal-margin')]");
	private By deleteDialogBox = By.xpath("//div[@role='document' and contains(@class,'oxd-sheet oxd-sheet--rounded oxd-sheet--white oxd-dialog-sheet oxd-dialog')]");
	private By rowGroup = By.xpath("//div[@class='oxd-table-body']");
	private By recordFoundHeader = By.xpath("//div[@class='orangehrm-horizontal-padding orangehrm-vertical-padding' and contains(.,'Record Found')]");
	private By getRecordHeaderMsg =By.xpath("//div[@class='orangehrm-horizontal-padding orangehrm-vertical-padding']/span");
	private By tableRecordsHeading= By.xpath("//div[@class='orangehrm-horizontal-padding orangehrm-vertical-padding']");
	
	// Page Actions
	public boolean isRecordsFoundMsgDisplayed() {
		
		logger.info("Checking if 'Record Found' heading message is displayed");

		try {
			return ElementWaits.waitForVisibility(driver, recordFoundMsg).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean isNoRecordsFoundMsgDisplayed() {
		
		logger.info("Checking if 'No Records Found' message is displayed");

		try {
			return ElementWaits.waitForVisibility(driver, noRecodsFoundConfirmationMsg).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean waitForDeleteDialogBoxToBeVisible() {
		
		logger.info("Waiting for delete confirmation dialog");

		try {
			return ElementWaits.waitForVisibility(driver, deleteDialogBox).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public EmployeeListPage clickDeleteSelectedItemsBtn() {
		
		logger.info("Clicking Delete Selected button");

		ElementWaits.waitToBeInteractable(driver, deleteSelectedBtn).click();
		return this;
	}

	public String getSearchedRecordsResultHeading() {
		
		logger.info("Getting search results heading");

		try {
			return ElementWaits.waitToBeInteractable(driver, searchedRecords).getText();
		} catch (Exception e) {
			return "Records found..";
		}
	}

	public String getFirstEmployeeIdFromTable() {
		
		logger.info("Retrieving first employee ID from table");

		try {
			return ElementWaits.waitForVisibility(driver, firstEmployeeIdFromTable).getText();
		} catch (Exception e) {
			throw new RuntimeException("No record is there in order to get the employee ID");
		}
	}

	public boolean isUpdatedValueReflectedInTheTable(String value) {
		
		logger.info("Checking if value '{}' is reflected in table", value);

		try {
			return ElementWaits.waitToBeInteractable(driver,
					By.xpath("//*[normalize-space(text())='" + value + "']")).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void clickAddEmployeeButton() {
		
		ElementWaits.waitToBeInteractable(driver, addEmplyeeBtn).click();
	}

	public boolean isConfirmationMsgDisplayed() {
		
		logger.info("Checking confirmation message");

		try {
			return ElementWaits.waitToBeInteractable(driver, confirmationMsg).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public EmployeeListPage enterEmployeeByName(String id) {

		ElementWaits.waitToBeInteractable(driver, employeeNameInputBox).clear();
		ElementWaits.waitToBeInteractable(driver, employeeNameInputBox).sendKeys(id);
		return this;
	}

	public EmployeeListPage enterEmployeeId(String id) {

		ElementWaits.waitToBeInteractable(driver, employeeIdInput).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		driver.findElement(employeeIdInput).sendKeys(Keys.DELETE);
		ElementWaits.waitToBeInteractable(driver, employeeIdInput).sendKeys(id);
		return this;
	}

	public EmployeeListPage clickSearchButton() {

		ElementWaits.waitToBeInteractable(driver, searchBtn).click();
		return this;
	}

	public EmployeeListPage clickUpdateIcon() {

		ElementWaits.waitToBeInteractable(driver, updateIcon).click();
		return this;
	}

	public EmployeeListPage clickDeleteIcon() {

		ElementWaits.waitToBeInteractable(driver, deleteIcon).click();
		return this;
	}

	public boolean waitForRowGroup() {

		return ElementWaits.waitToBeInteractable(driver, rowGroup).isDisplayed();
	}

	public EmployeeListPage clickDeleteCheckBox() {

		ElementWaits.waitToBeInteractable(driver, deleteCheckBox).click();
		return this;
	}

	public void acceptConfirmationToDelete() {

		ElementWaits.waitToBeInteractable(driver, confirmDeleteBtn).click();
	}

	public boolean isEmployeePresent(String employeeId) {

	    searchByEmployeeId(employeeId);
	    return getRecordMsg();
	}
	
	public boolean getRecordMsg()
	{
		String msg =ElementWaits.waitToBeInteractable(driver,getRecordHeaderMsg).getText();
		
		if(msg.equalsIgnoreCase("No Records Found") )
		{
			return false;
		}
		else
			return true;
	}
	
	public boolean isRecordFoundMessageDisplayed()
	{
		try {
			return ElementWaits.waitToBeInteractable(driver, recordFoundHeader).isDisplayed();
		}catch (Exception e)
		{
			return false;
		}
	}
	
	public boolean waitForTableRecordsToBeDisplyed()
	{
		logger.info("Wait for table record heading to be displayed");
		
		try {
			return ElementWaits.waitToBeInteractable(driver, tableRecordsHeading).isDisplayed();
		}catch (Exception e)
		{
			return false;
		}
	}
	
	public boolean isRecordFoundMsgDisplayed()
	{
		return !isNoRecordsFoundMsgDisplayed();
	}
	
	public void clickCancelConfirmationBtn() {
		logger.info("Cancelling employee deletion");

		ElementWaits.waitToBeInteractable(driver, cancelDeleteBtn).click();
	}
	
	// Page Behavior
	public void deleteRecord(String employeeId) {
		
		logger.info("Deleting employee with ID {}", employeeId);

		enterEmployeeId(employeeId);
		clickSearchButton();
		waitForRowGroup();
		clickDeleteIcon();
		waitForDeleteDialogBoxToBeVisible();
		acceptConfirmationToDelete();
	}

	public void searchByEmployeeId(String employeeId) {
		
		logger.info("Searching employee by ID {}", employeeId);

		enterEmployeeId(employeeId);
		clickSearchButton();
	}

	public void searchByFirstName(String firstName) {
		
		logger.info("Searching employee by first name {}", firstName);

		enterEmployeeByName(firstName);
		clickSearchButton();
	}

	public void searchByLastName(String lastName) {
		
		logger.info("Searching employee by last name {}", lastName);
		
		waitForTableRecordsToBeDisplyed();
		enterEmployeeByName(lastName);
		clickSearchButton();

	}
}