package project.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import project.base.BaseTest;
import project.expected.LoginExpected;
import project.testdata.TestDataFactory;
import project.testdata.TestDataGenerator;

/*receive a copy from the basetestcon2*/
/*one browser per class, and reset state of the browser after each test*/ //thread 1
/*all the tests are executed sequential, but each thread have multiple classes !*/

public class LoginTests extends BaseTest {

	@BeforeMethod(alwaysRun = true)
	public void setUpLogin()
	{
		adminUser = TestDataFactory.createAdminUserData();
	}
	
	@Test(groups = {"Smoke","Authentication"}, description = "TC_LOGIN_001")
	public void loginWithValidCredentials ()
	{
		//Test Steps
		loginPage.authentication(adminUser);
		
		//Validations
		softAssertion.assertEquals(dashboard.waitForProfilePictureDisplayed(), true, "Profile picture is not displayed on index page");
		softAssertion.assertAll();
	}
	
	@Test(groups = {"Regression","Authentication"}, description = "TC_LOGIN_002")
	public void loginWithValidUsernameAndWrongPassword ()
	{
		//Test Steps
		adminUser.setPassword(TestDataGenerator.randomPassword(10)); // => valid password length = 8
		loginPage.authentication(adminUser);
		
		//Validations
		softAssertion.assertEquals(loginPage.isBadCredentialsMessageDisplaiyed(), true, "Error msg is not displayed");
		softAssertion.assertEquals(loginPage.getBadCredentialMsg(),LoginExpected.FAILED_MSG_WITH_INVALID_CREDENTIALS, "Error text msg is not as required");
		softAssertion.assertAll();
	}
	
	@Test(groups = {"Regression","Authentication"}, description = "TC_LOGIN_003")
	public void loginWithValidPasswordAndWrongUsername()
	{
		//Test Steps
		adminUser.setUsername(TestDataGenerator.randomUsername(7)); // => valid username length = 5
		loginPage.authentication(adminUser);
		
		//Validations
		softAssertion.assertEquals(loginPage.isBadCredentialsMessageDisplaiyed(), true, "Error msg is not displayed");
		softAssertion.assertEquals(loginPage.getBadCredentialMsg(),LoginExpected.FAILED_MSG_WITH_INVALID_CREDENTIALS, "Error text msg is not as required");
		softAssertion.assertAll();
	}
	
	@Test(groups = {"Regression","Authentication"}, description = "TC_LOGIN_004")
	public void loginWithEmptyUsernameAndValidPassword()
	{
		//Test Steps
		adminUser.setUsername(TestDataGenerator.emptyString()); // => ""
		loginPage.authentication(adminUser);
		
		//Validations
		softAssertion.assertEquals(loginPage.getUsernameRequiredErrorMsg(),LoginExpected.FAILED_MSG_WITH_EMPTY_USERNAME, "Error text msg is not as required");
		softAssertion.assertFalse(dashboard.isProfilePictureDisplayed());
		softAssertion.assertAll();
	}
	
	@Test(groups = {"Regression","Authentication"}, description = "TC_LOGIN_005")
	public void loginWithEmptyPasswordAndValidUsername()
	{
		//Test Steps
		adminUser.setPassword(TestDataGenerator.emptyString()); // => ""
		loginPage.authentication(adminUser);
		
		//Validations
		softAssertion.assertEquals(loginPage.getPasswordRequiredErrorMsg(),LoginExpected.FAILED_MSG_WITH_EMPTY_PASSWORD, "Error text msg is not as required");
		softAssertion.assertFalse(dashboard.isProfilePictureDisplayed());
		softAssertion.assertAll();
	}

}
