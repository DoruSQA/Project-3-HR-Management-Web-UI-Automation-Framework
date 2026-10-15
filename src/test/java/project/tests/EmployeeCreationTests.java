package project.tests;

import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import project.base.BaseTest;

//AfterMethod form the child first
//BeforeMethod from the paret first

/*EmplyeeManagementTests*/ // ->maybe package namee

public class EmployeeCreationTests extends BaseTest {

	
	@BeforeMethod(alwaysRun = true)
	public void setUpEmployeeCreation() {

		try {
			loginPage.authentication(adminUser);
			dashboard.openPersonnelInformationManagement();
			employeeListPage.clickAddEmployeeButton();
		} catch (Exception e) {
			throw new SkipException("BeforeMethod failed", e);
		}

	}

	@Test(groups = { "Smoke", "Regression", "PIM"}, description = "TC_PIM_CREATE_001")
	public void createEmployeeWithMandatoryFields() {

		// Test steps
		addEmployeePage.fillEmployeeInformation(validEmployee);
		addEmployeePage.clickSaveButton();
		
		// Validation
		softAssertion.assertEquals(addEmployeePage.waitForConfirmationMsgToBeDisplayed(), true,
				"Confirmation msg is not displayed");
		softAssertion.assertEquals(personalDetailsPage.waitForPersonalDetailsHeadingToBeDisplayed(), true,
				"Personal details headers is not displayed");
		softAssertion.assertAll();
	}

	@Test(groups = { "Regression", "PIM" }, description = "TC_PIM_CREATE_002")
	public void createEmployeeWithLoginAccountCredentials() {

		addEmployeePage.fillEmployeeInformation(validEmployee);

		// Test Steps
		addEmployeePage.toggleLoginDetails();
		addEmployeePage.createNewAccount(newAccountUser);
		addEmployeePage.clickEnabledStatus();
		addEmployeePage.clickSaveButton();

		// Validation_1: Employee Creation
		softAssertion.assertEquals(addEmployeePage.waitForConfirmationMsgToBeDisplayed(), true,
				"Confirmation msg is not displayed");
		softAssertion.assertEquals(personalDetailsPage.waitForPersonalDetailsHeadingToBeDisplayed(), true,
				"Personal details headers is not displayed");
		softAssertion.assertAll();

		// Intermediary Steps: login with new credentials created
		addEmployeePage.logout();
		loginPage.waitForLoginBtmToBeDisplayed();
		loginPage.authentication(newAccountUser);

		// Validation_2: New Account 
		softAssertion = new SoftAssert(); // reset assert
		softAssertion.assertEquals(newAccountPage.isPageHeadingDisplayed(), true);
		softAssertion.assertEquals(newAccountPage.isSideMenuDisplayed(), true);
		softAssertion.assertEquals(newAccountPage.isProfilePictureDisplayed(), true);
		softAssertion.assertAll();
	}

	
	

}
