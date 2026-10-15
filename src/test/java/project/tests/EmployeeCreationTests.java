package project.tests;

import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import orangeHRM.test_metadata.TestCase;
import project.base.BaseTest;

//AfterMethod form the child first
//BeforeMethod from the paret first

/*EmplyeeManagementTests*/ // ->maybe package namee

public class EmployeeCreationTests extends BaseTest {

	
	@BeforeMethod(alwaysRun = true)
	public void setUpEmployeeCreation() {

	    try {
	        // Log in as administrator and navigate to the employee creation form
	        loginPage.authentication(adminUser);
	        dashboard.openPersonnelInformationManagement();
	        employeeListPage.clickAddEmployeeButton();

	    } catch (Exception e) {
	        throw new SkipException("BeforeMethod failed", e);
	    }
	}

	@Test(groups = { "Smoke", "Regression", "PIM" })
	@TestCase(id = "TC_PIM_CREATE_001")
	public void createEmployeeWithMandatoryFields() {

	    // Fill in the mandatory employee information
	    addEmployeePage.fillEmployeeInformation(validEmployee);

	    // Submit the employee creation form
	    addEmployeePage.clickSaveButton();

	    // Verify successful employee creation and redirection
	    softAssertion.assertEquals(addEmployeePage.waitForConfirmationMsgToBeDisplayed(), true);
	    softAssertion.assertEquals(personalDetailsPage.waitForPersonalDetailsHeadingToBeDisplayed(), true);
	    softAssertion.assertAll();
	}

	@Test(groups = { "Regression", "PIM" })
	@TestCase(id = "TC_PIM_CREATE_002")
	public void createEmployeeWithLoginAccountCredentials() {

	    // Fill in the mandatory employee information
	    addEmployeePage.fillEmployeeInformation(validEmployee);

	    // Enable login details and configure the new user account
	    addEmployeePage.toggleLoginDetails();
	    addEmployeePage.createNewAccount(newAccountUser);
	    addEmployeePage.clickEnabledStatus();

	    // Submit the employee creation form
	    addEmployeePage.clickSaveButton();

	    // Verify successful employee creation and redirection
	    softAssertion.assertEquals(addEmployeePage.waitForConfirmationMsgToBeDisplayed(), true);
	    softAssertion.assertEquals(personalDetailsPage.waitForPersonalDetailsHeadingToBeDisplayed(), true);
	    softAssertion.assertAll();

	    // Log out and authenticate using the newly created account
	    addEmployeePage.logout();
	    loginPage.waitForLoginBtmToBeDisplayed();
	    loginPage.authentication(newAccountUser);

	    // Verify successful login and access to the new account
	    softAssertion = new SoftAssert();
	    softAssertion.assertEquals(newAccountPage.isPageHeadingDisplayed(), true);
	    softAssertion.assertEquals(newAccountPage.isSideMenuDisplayed(), true);
	    softAssertion.assertEquals(newAccountPage.isProfilePictureDisplayed(), true);
	    softAssertion.assertAll();
	}

	
	

}
