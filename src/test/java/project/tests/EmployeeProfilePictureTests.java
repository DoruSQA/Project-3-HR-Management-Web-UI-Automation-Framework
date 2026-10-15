package project.tests;

import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import orangeHRM.test_metadata.TestCase;
import project.base.BaseTest;
import project.expected.AddEmployeeExpected;

/* Group tests by common preconditions and workflows to simplify setup management */

public class EmployeeProfilePictureTests extends BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setUpEmployeeProfilePicture() {

        try {
            // Prepare employee test data and navigate to the employee creation form
            validEmployee.setFirstname("JonnyPic").setLastname("CagePic").setEmployeeId("1Pic");
            loginPage.authentication(adminUser);
            dashboard.openPersonnelInformationManagement();
            employeeListPage.clickAddEmployeeButton();

            // Populate the form with valid employee information before testing profile pictures
            addEmployeePage.fillEmployeeInformation(validEmployee);

        } catch (Exception e) {
            throw new SkipException("BeforeMethod failed", e);
        }
    }

    @Test(groups = {"Smoke", "PIM"})
    @TestCase(id = "TC_PIM_PICTURE_001")
    public void createEmployeeWithValidProfilePicture() {

        // Capture the default profile image before uploading a valid picture
        String defaultSrc = addEmployeePage.getProfileImageSrc();

        // Upload a supported profile picture and submit the employee creation form
        addEmployeePage.addProfilePhoto(AddEmployeeExpected.VALID_PROFILE_PICTURE_PATH);
        addEmployeePage.clickSaveButton();

        // Verify employee creation, successful redirection, and profile image update
        softAssertion.assertTrue(addEmployeePage.isEmployeeCreated());
        softAssertion.assertTrue(personalDetailsPage.waitForPersonalDetailsHeadingToBeDisplayed());
        softAssertion.assertNotEquals(defaultSrc, addEmployeePage.getProfileImageSrc());
        softAssertion.assertAll();
    }

    @Test(groups = {"Regression", "PIM"})
    @TestCase(id = "TC_PIM_PICTURE_002")
    public void createEmployeeWithUnsupportedProfilePictureFormat() {

        // Upload a profile picture with an unsupported file format and submit the form
        addEmployeePage.addProfilePhoto(AddEmployeeExpected.WRONG_FORMAT_PROFILE_PICTURE_PATH);
        addEmployeePage.clickSaveButton();

        // Verify the unsupported file format error is displayed and employee creation is rejected
        softAssertion.assertEquals(addEmployeePage.getPprofilePictureTypeErrorMsg(),AddEmployeeExpected.FAILED_MSG_UNSUPORTED_PROFILE_PICTURE_FORMAT);
        softAssertion.assertFalse(addEmployeePage.isEmployeeCreated());
        softAssertion.assertAll();
    }

    @Test(groups = {"Regression", "PIM"})
    @TestCase(id = "TC_PIM_PICTURE_003")
    public void createEmployeeWithCorruptedProfilePicture() {

        // Upload a corrupted profile picture and submit the employee creation form
        addEmployeePage.addProfilePhoto(AddEmployeeExpected.CORUPTED_PROFILE_PICTURE_PATH);
        addEmployeePage.clickSaveButton();

        // Verify the corrupted picture is rejected and the expected validation error is displayed
        softAssertion.assertFalse(addEmployeePage.isEmployeeCreated());
        softAssertion.assertEquals( addEmployeePage.getPprofilePictureTypeErrorMsg(),AddEmployeeExpected.FAILED_MSG_UNSUPORTED_PROFILE_PICTURE_FORMAT);
        softAssertion.assertAll();
    }

    @Test(groups = {"Regression", "PIM"})
    @TestCase(id = "TC_PIM_PICTURE_004")
    public void createEmployeeWithProfilePictureExceedingMaximumSize() {

        // Upload a profile picture that exceeds the permitted file size and submit the form
        addEmployeePage.addProfilePhoto(AddEmployeeExpected.LARGE_PROFILE_PICTURE_PATH);
        addEmployeePage.clickSaveButton();

        // Verify the file size validation error is displayed and employee creation is rejected
        softAssertion.assertEquals(addEmployeePage.getPprofilePictureSizeErrorMsg(),AddEmployeeExpected.FAILED_MSG_PROFILE_PICTURE_EXCEEDING_MAX_SIZE);
        softAssertion.assertFalse(addEmployeePage.isEmployeeCreated());
        softAssertion.assertAll();
    }
}
