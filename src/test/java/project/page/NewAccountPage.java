package project.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.base.BasePage;
import project.utils.ElementWaits;

public class NewAccountPage extends BasePage {

	private static final Logger logger = LoggerFactory.getLogger(NewAccountPage.class);

	//Constructor 
	public NewAccountPage(WebDriver d) {
		super(d);
	}

	// Locators
	private By sideMenu = By.xpath("//div[@class='oxd-sidepanel-body']");
	private By pageHeading = By.xpath("//h6");
	private By logoutBox=By.xpath("//li[@class='--active oxd-userdropdown']");

	// Page Actions
	public boolean isSideMenuDisplayed() {

		return ElementWaits.waitToBeInteractable(driver, sideMenu).isDisplayed();
	}

	public boolean isPageHeadingDisplayed() {

		return ElementWaits.waitToBeInteractable(driver, pageHeading).isDisplayed();
	}
	
	public boolean isLogoutBoxDisplayed()
	{
		try {
			return ElementWaits.waitToBeInteractable(driver, logoutBox).isDisplayed();
		}catch (Exception e)
		{
			return false;
		}
	}

	public void selectLogoutOption() {

		ElementWaits.waitToBeInteractable(driver, logoutSelectOption).click();
	}

	// Page Behavior
	public void logout() {

		logger.info("Starting logout process");

		clickOnProfilePicture();
		if (isLogoutBoxDisplayed())
			selectLogoutOption();

		logger.info("Logout request submitted");
	}

}