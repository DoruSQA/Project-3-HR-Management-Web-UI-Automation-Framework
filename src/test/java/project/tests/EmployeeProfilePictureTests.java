package project.tests;

import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import project.base.BaseTest;
import project.expected.AddEmployeeExpected;

/*grup by the common flow /precon... =>easy for beforemethod preconditions*/

public class EmployeeProfilePictureTests extends BaseTest {


	@BeforeMethod(alwaysRun = true)
	public void setUpEmployeeProfilePicture() {
		
		try {
			validEmployee.setFirstname("JonnyPic").setLastname("CagePic").setEmployeeId("1Pic");
			loginPage.authentication(adminUser);
			dashboard.openPersonnelInformationManagement();
			employeeListPage.clickAddEmployeeButton();
			addEmployeePage.fillEmployeeInformation(validEmployee);
		}
		catch (Exception e)
		{
			throw new SkipException("BeforeMethod failed", e);
		}
	}

	@Test(groups = {"Smoke","PIM"}, description = "TC_PIM_PICTURE_001")
	public void createEmployeeWithValidProfilePicture() {

		// Test steps
		String defaultSrc = addEmployeePage.getProfileImageSrc();
		addEmployeePage.addProfilePhoto(AddEmployeeExpected.VALID_PROFILE_PICTURE_PATH);
		addEmployeePage.clickSaveButton();
		
		// Validation
		softAssertion.assertTrue(addEmployeePage.isEmployeeCreated(), "Employee was not created succesuflly");
		softAssertion.assertTrue(personalDetailsPage.waitForPersonalDetailsHeadingToBeDisplayed(), "Redirection to personal details page was not succesfull");
		softAssertion.assertNotEquals(defaultSrc, addEmployeePage.getProfileImageSrc(), "Image src was not changed");
		softAssertion.assertAll();
	}

	@Test(groups = {"Regression","PIM"}, description = "TC_PIM_PICTURE_002")
	public void createEmployeeWithUnsupportedProfilePictureFormat() {

		// Test steps
		addEmployeePage.addProfilePhoto(AddEmployeeExpected.WRONG_FORMAT_PROFILE_PICTURE_PATH);
		addEmployeePage.clickSaveButton();
			
		// Validation
		softAssertion.assertEquals(addEmployeePage.getPprofilePictureTypeErrorMsg(),AddEmployeeExpected.FAILED_MSG_UNSUPORTED_PROFILE_PICTURE_FORMAT, "Error msg is not as correct");
		softAssertion.assertFalse(addEmployeePage.isEmployeeCreated(), "Employee is created succesuflly");
		softAssertion.assertAll();
	}

	@Test(groups = {"Regression","PIM"}, description = "TC_PIM_PICTURE_003")
	public void createEmployeeWithCorruptedProfilePicture() {

		// Test steps
		addEmployeePage.addProfilePhoto(AddEmployeeExpected.CORUPTED_PROFILE_PICTURE_PATH);
		addEmployeePage.clickSaveButton();
		
		// Validation
		softAssertion.assertFalse(addEmployeePage.isEmployeeCreated(), "Employee is created succesuflly");
		softAssertion.assertEquals(addEmployeePage.getPprofilePictureTypeErrorMsg(),AddEmployeeExpected.FAILED_MSG_UNSUPORTED_PROFILE_PICTURE_FORMAT, "Error msg is not as correct");
		softAssertion.assertAll();
	}

	@Test(groups = {"Regression","PIM"}, description = "TC_PIM_PICTURE_004")
	public void createEmployeeWithProfilePictureExceedingMaximumSize() {

		// Test steps
		addEmployeePage.addProfilePhoto(AddEmployeeExpected.LARGE_PROFILE_PICTURE_PATH);
		addEmployeePage.clickSaveButton();

		// Validation
		softAssertion.assertEquals(addEmployeePage.getPprofilePictureSizeErrorMsg(),
				AddEmployeeExpected.FAILED_MSG_PROFILE_PICTURE_EXCEEDING_MAX_SIZE, "Error msg is not as correct");
		softAssertion.assertFalse(addEmployeePage.isEmployeeCreated(), "Employee is created succesuflly");
		softAssertion.assertAll();
	}
}
