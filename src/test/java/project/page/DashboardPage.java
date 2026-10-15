package project.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.base.BasePage;
import project.utils.ElementWaits;

public class DashboardPage extends BasePage {

	/* WebDriver mostenit de la BasePage ! */
	private static final Logger logger = LoggerFactory.getLogger(DashboardPage.class);

	// Constructor
	public DashboardPage(WebDriver d) {
		super(d);
	}

	// Locators
	private By profilePicture = By.xpath("//header/descendant::img[@alt='profile picture']");
	private By sideMenuPIM = By.xpath("//*[@class='oxd-main-menu']/descendant::*[text()='PIM']");

	// Page Actions
	public boolean waitForProfilePictureDisplayed() {
		
		logger.info("Waiting for profile picture to be displayed");
		return ElementWaits.waitToBeInteractable(driver, profilePicture).isDisplayed();
	}

	public void openPersonnelInformationManagement() {
		
		logger.info("Opening Personnel Information Management (PIM) page");
		ElementWaits.waitForVisibility(driver, sideMenuPIM).click();
	}


}