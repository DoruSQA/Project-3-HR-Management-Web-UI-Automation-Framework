package project.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import project.utils.ElementWaits;

public abstract class BasePage {

	public WebDriver driver;

	// Parent Constructor
	public BasePage(WebDriver d) {
		this.driver = d;
	}

	// Common Locators
	public By firstNameInputBox = By.xpath("//input[@name='firstName']");
	public By lastNameInputBox = By.xpath("//input[@name='lastName']");
	public By employeeIdInputBox = By.xpath("//*[text()='Employee Id']/../following-sibling::div/input");
	public By confirmationMsg = By.xpath("//div[@id='oxd-toaster_1']/descendant::*[text()='Success']");
	public By profilePicture = By.xpath("//header//img");
	public By logoutSelectOption = By.xpath("//ul[@role='menu']/li[contains(.,'Logout')]");
	
	// Common Methods
	public void clickOnProfilePicture() {
		
		ElementWaits.waitToBeInteractable(driver, profilePicture).click();
	}
	
	public boolean isProfilePictureDisplayed() {

		try {
			return ElementWaits.waitToBeInteractable(driver, profilePicture).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	} // check in that moment
}
