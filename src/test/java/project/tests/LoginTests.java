package project.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import orangeHRM.test_metadata.TestCase;
import project.base.BaseTest;
import project.expected.LoginExpected;
import project.testdata.TestDataFactory;
import project.testdata.TestDataGenerator;

/* Receive a copy of the base test configuration */
/* Use one browser per class and reset the browser state after each test */
// Thread 1
/* Tests within a class execute sequentially, while multiple classes can run in parallel across threads */

public class LoginTests extends BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setUpLogin() {

        // Prepare valid administrator credentials before each login test
        adminUser = TestDataFactory.createAdminUserData();
    }

    @Test(groups = {"Smoke", "Authentication"})
    @TestCase(id = "TC_LOGIN_001")
    public void loginWithValidCredentials() {

        // Attempt to log in using valid administrator credentials
        loginPage.authentication(adminUser);

        // Verify successful authentication by checking that the profile picture is displayed
        softAssertion.assertEquals(dashboard.waitForProfilePictureDisplayed(), true);
        softAssertion.assertAll();
    }

    @Test(groups = {"Regression", "Authentication"})
    @TestCase(id = "TC_LOGIN_002")
    public void loginWithValidUsernameAndWrongPassword() {

        // Replace the valid password with a randomly generated password
        adminUser.setPassword(TestDataGenerator.randomPassword(10));

        // Attempt to log in using a valid username and an incorrect password
        loginPage.authentication(adminUser);

        // Verify the invalid credentials error message is displayed with the expected text
        softAssertion.assertEquals(loginPage.isBadCredentialsMessageDisplaiyed(), true);
        softAssertion.assertEquals(loginPage.getBadCredentialMsg(),LoginExpected.FAILED_MSG_WITH_INVALID_CREDENTIALS);
        softAssertion.assertAll();
    }

    @Test(groups = {"Regression", "Authentication"})
    @TestCase(id = "TC_LOGIN_003")
    public void loginWithValidPasswordAndWrongUsername() {

        // Replace the valid username with a randomly generated username
        adminUser.setUsername(TestDataGenerator.randomUsername(7));

        // Attempt to log in using an incorrect username and the valid password
        loginPage.authentication(adminUser);

        // Verify the invalid credentials error message is displayed with the expected text
        softAssertion.assertEquals(loginPage.isBadCredentialsMessageDisplaiyed(), true);
        softAssertion.assertEquals(loginPage.getBadCredentialMsg(),LoginExpected.FAILED_MSG_WITH_INVALID_CREDENTIALS);
        softAssertion.assertAll();
    }

    @Test(groups = {"Regression", "Authentication"})
    @TestCase(id = "TC_LOGIN_004")
    public void loginWithEmptyUsernameAndValidPassword() {

        // Clear the username field while keeping the valid password
        adminUser.setUsername(TestDataGenerator.emptyString());

        // Attempt to log in with an empty username
        loginPage.authentication(adminUser);

        // Verify the required-username validation message and confirm login was unsuccessful
        softAssertion.assertEquals(loginPage.getUsernameRequiredErrorMsg(), LoginExpected.FAILED_MSG_WITH_EMPTY_USERNAME);
        softAssertion.assertFalse(dashboard.isProfilePictureDisplayed());
        softAssertion.assertAll();
    }

    @Test(groups = {"Regression", "Authentication"})
    @TestCase(id = "TC_LOGIN_005")
    public void loginWithEmptyPasswordAndValidUsername() {

        // Clear the password field while keeping the valid username
        adminUser.setPassword(TestDataGenerator.emptyString());

        // Attempt to log in with an empty password
        loginPage.authentication(adminUser);

        // Verify the required-password validation message and confirm login was unsuccessful
        softAssertion.assertEquals(loginPage.getPasswordRequiredErrorMsg(),LoginExpected.FAILED_MSG_WITH_EMPTY_PASSWORD);
        softAssertion.assertFalse(dashboard.isProfilePictureDisplayed());
        softAssertion.assertAll();
    }
}
