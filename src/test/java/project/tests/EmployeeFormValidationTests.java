package project.tests;

import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import project.base.BaseTest;
import project.expected.AddEmployeeExpected;
import project.testdata.TestDataFactory;
import project.testdata.TestDataGenerator;

public class EmployeeFormValidationTests extends BaseTest {


	@BeforeMethod(alwaysRun = true)
	public void setUpEmployeeFormValidation() {

		try {
			validEmployee = TestDataFactory.createValidEmployeeUser();
			validEmployee.setFirstname("JonnyForm").setLastname("CageForm").setEmployeeId("1Form");
			loginPage.authentication(adminUser);
			dashboard.openPersonnelInformationManagement();
			employeeListPage.clickAddEmployeeButton();
		}
		catch (Exception e)
		{
			throw new SkipException("BeforeMethod failed", e);
		}

	}

	private void createEmployee() {

		try {
			addEmployeePage.fillEmployeeInformation(validEmployee);
			addEmployeePage.clickSaveButton();
			addEmployeePage.waitForConfirmationMsgToBeDisplayed();
			personalDetailsPage.waitForPersonalDetailsHeadingToBeDisplayed();
		}catch (Exception e)
		{
			 throw new SkipException("Employee could not be created");
		}
		
	}


	@Test(groups = {"Regression","PIM"}, description = "TC_PIM_FORM_001")
	public void createEmployeeWithEmptyFirstName( ) {

		// Test Steps
		validEmployee.setFirstname(TestDataGenerator.emptyString()); // => ""
		addEmployeePage.fillEmployeeInformation(validEmployee);
		addEmployeePage.clickSaveButton();
		
		// Validations
		softAssertion.assertTrue(addEmployeePage.isInputFieldErrorMsgDisplayed(), "Error field msg is not displayed");
		softAssertion.assertEquals(addEmployeePage.getInputFieldErrorMsg(),
				AddEmployeeExpected.FAILED_MSG_WITH_EMPTY_EMPLOYEE_FNAME, "Error msg is not correct");
		softAssertion.assertAll();
	}

	@Test(groups = {"Regression","PIM"}, description = "TC_PIM_FORM_002")
	public void createEmployeeWithEmptyLastName() {


		// Test Steps
		validEmployee.setLastname(TestDataGenerator.emptyString());  // => ""
		addEmployeePage.fillEmployeeInformation(validEmployee);
		addEmployeePage.clickSaveButton();

		// Validations
		softAssertion.assertTrue(addEmployeePage.isInputFieldErrorMsgDisplayed(), "Error field msg is not displayed");
		softAssertion.assertEquals(addEmployeePage.getInputFieldErrorMsg(),
				AddEmployeeExpected.FAILED_MSG_WITH_EMPTY_EMPLOYEE_LNAME, "Error msg is not correct");
		softAssertion.assertAll();
	}


	@Test(groups = {"Regression","PIM"}, description = "TC_PIM_FORM_003")
	public void createEmployeeWithDuplicateEmployeeId() {

		createEmployee();
		
		// Test Steps
		employeeListPage.clickAddEmployeeButton();
		addEmployeePage.fillEmployeeInformation(validEmployee); // create employee again with same id (no name issues)
		addEmployeePage.clickSaveButton();

		// Validation
		softAssertion = new SoftAssert(); // reset assert
		softAssertion.assertEquals(addEmployeePage.isEmployeeIdErrorMsgDisplayed(), true,
				"Employee error msg is not displayed");
		softAssertion.assertFalse(addEmployeePage.isEmployeeCreated(), "Employee shuld not be created with duplicated ID");
		softAssertion.assertAll();
	}

	@Test(groups = {"Regression","PIM"}, description = "TC_PIM_FORM_004")
	public void createEmployeeWithFirstNameExceedingMaximumLength() {

		// Test Steps
		validEmployee.setFirstname(TestDataGenerator.randomFirstName(31)); // 30 max length
		addEmployeePage.fillEmployeeInformation(validEmployee);
		addEmployeePage.clickSaveButton();

		// Validations
		softAssertion.assertTrue(addEmployeePage.isInputFieldErrorMsgDisplayed(), "Error field msg is not displayed");
		softAssertion.assertEquals(addEmployeePage.getInputFieldErrorMsg(),
				AddEmployeeExpected.FAILED_MSG_WITH_EMPLOYEE_FNAME_EXCEEDING_MAX_LENGTH, "Error msg is not correct");
		softAssertion.assertAll();
	}

	@Test(groups = {"Regression","PIM"}, description = "TC_PIM_FORM_005")
	public void createEmployeeWithLastNameExceedingMaximumLength() {

		// Test Steps
		validEmployee.setLastname(TestDataGenerator.randomLastName(31)); // 30 max length
		addEmployeePage.fillEmployeeInformation(validEmployee);
		addEmployeePage.clickSaveButton();

		// Validations
		softAssertion.assertTrue(addEmployeePage.isInputFieldErrorMsgDisplayed(), "Error field msg is not displayed");
		softAssertion.assertEquals(addEmployeePage.getInputFieldErrorMsg(),
				AddEmployeeExpected.FAILED_MSG_WITH_EMPLOYEE_LNAME_EXCEEDING_MAX_LENGTH, "Error msg is not correct");
		softAssertion.assertAll();
	}

	@Test(groups = {"Regression","PIM"}, description = "TC_PIM_FORM_006")
	public void createEmployeeWithEmployeeIdExceedingMaximumLength() {

		// Test Steps
		validEmployee.setEmployeeId(TestDataGenerator.randomEmployeeId(11)); // 10 max length
		addEmployeePage.fillEmployeeInformation(validEmployee);
		addEmployeePage.clickSaveButton();

		// Validations
		softAssertion.assertTrue(addEmployeePage.isInputFieldErrorMsgDisplayed(), "Error field msg is not displayed");
		softAssertion.assertEquals(addEmployeePage.getInputFieldErrorMsg(),
				AddEmployeeExpected.FAILED_MSG_WITH_EMPLOYEE_ID_EXCEEDING_MAX_LENGTH, "Error msg is not correct");
		softAssertion.assertAll();
	}

	@Test(groups = {"Regression","PIM"}, description = "TC_PIM_FORM_007")
	public void createEmployeeWithSpecialCharactersInFirstName() {

		// Test Steps
		validEmployee.setFirstname(TestDataGenerator.specialChars(5)); // => random 5 special chars: ';(-@+$(&'
		addEmployeePage.fillEmployeeInformation(validEmployee);
		addEmployeePage.clickSaveButton();

		// Validations
		softAssertion.assertFalse(addEmployeePage.isEmployeeCreated(), "Employee was created succesuflly");
		softAssertion.assertTrue(addEmployeePage.isInputFieldErrorMsgDisplayed(), "Error field msg is not displayed");
		softAssertion.assertAll();
	}

	@Test(groups = {"Regression","PIM"}, description = "TC_PIM_FORM_008")
	public void createEmployeeWithSpecialCharactersInLastName() {

		// Test Steps
		validEmployee.setLastname(TestDataGenerator.specialChars(5)); // => random 5 special chars: '#(-@+$(&'
		addEmployeePage.fillEmployeeInformation(validEmployee);
		addEmployeePage.clickSaveButton();

		// Validations
		softAssertion.assertFalse(addEmployeePage.isEmployeeCreated(), "Employee was created succesuflly");
		softAssertion.assertTrue(addEmployeePage.isInputFieldErrorMsgDisplayed(), "Error field msg is not displayed");
		softAssertion.assertAll();

	}

	@Test(groups = {"Regression","PIM"}, description = "TC_PIM_FORM_009")
	public void createEmployeeWithNumericFirstName() {

		// Test Steps
		validEmployee.setFirstname(TestDataGenerator.randomNumericValue(7));// => random 7 digit value: '1584514'
		addEmployeePage.fillEmployeeInformation(validEmployee);
		addEmployeePage.clickSaveButton();

		// Validations
		softAssertion.assertFalse(addEmployeePage.isEmployeeCreated(), "Employee was created succesuflly");
		softAssertion.assertTrue(addEmployeePage.isInputFieldErrorMsgDisplayed(), "Error field msg is not displayed");
		softAssertion.assertAll();
	}

	@Test(groups = {"Regression","PIM"}, description = "TC_PIM_FORM_010")
	public void createEmployeeWithNumericLastName() {

		// Test Steps
		validEmployee.setLastname(TestDataGenerator.randomNumericValue(7)); // => random 7 digit value: '5917514'
		addEmployeePage.fillEmployeeInformation(validEmployee);
		addEmployeePage.clickSaveButton();

		// Validations
		softAssertion.assertFalse(addEmployeePage.isEmployeeCreated(), "Employee was created succesuflly");
		softAssertion.assertTrue(addEmployeePage.isInputFieldErrorMsgDisplayed(), "Error field msg is not displayed");
		softAssertion.assertAll();
	}

	@Test(groups = {"Regression","PIM"}, description = "TC_PIM_FORM_011")
	public void createEmployeeWithOnlySpacesInFirstName() {

		// Test Steps
		validEmployee.setFirstname(TestDataGenerator.whitespaceString(2)); // =>  "  "
		addEmployeePage.fillEmployeeInformation(validEmployee);
		addEmployeePage.clickSaveButton();

		// Validations
		softAssertion.assertTrue(addEmployeePage.isInputFieldErrorMsgDisplayed(), "Error field msg is not displayed");
		softAssertion.assertEquals(addEmployeePage.getInputFieldErrorMsg(),
				AddEmployeeExpected.FAILED_MSG_WITH_EMPTY_EMPLOYEE_FNAME, "Error msg is not correct");
		softAssertion.assertAll();
	}

	@Test(groups = {"Regression","PIM"}, description = "TC_PIM_FORM_012")
	public void createEmployeeWithOnlySpacesInLastName() {

		// Test Steps
		validEmployee.setLastname(TestDataGenerator.whitespaceString(2)); // =>  "  "
		addEmployeePage.fillEmployeeInformation(validEmployee);
		addEmployeePage.clickSaveButton();

		// Validations
		softAssertion.assertTrue(addEmployeePage.isInputFieldErrorMsgDisplayed(), "Error field msg is not displayed");
		softAssertion.assertEquals(addEmployeePage.getInputFieldErrorMsg(),
				AddEmployeeExpected.FAILED_MSG_WITH_EMPTY_EMPLOYEE_LNAME, "Error msg is not correct");
		softAssertion.assertAll();
	}

}
