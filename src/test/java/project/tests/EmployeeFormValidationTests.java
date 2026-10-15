package project.tests;

import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import orangeHRM.test_metadata.TestCase;
import project.base.BaseTest;
import project.expected.AddEmployeeExpected;
import project.testdata.TestDataFactory;
import project.testdata.TestDataGenerator;

public class EmployeeFormValidationTests extends BaseTest {

	@BeforeMethod(alwaysRun = true)
	public void setUpEmployeeFormValidation() {

		try {
			// Prepare valid employee data and navigate to the employee creation form
			validEmployee = TestDataFactory.createValidEmployeeUser();
			validEmployee.setFirstname("JonnyForm").setLastname("CageForm").setEmployeeId("1Form");

			loginPage.authentication(adminUser);
			dashboard.openPersonnelInformationManagement();
			employeeListPage.clickAddEmployeeButton();

		} catch (Exception e) {
			throw new SkipException("BeforeMethod failed", e);
		}
	}

	private void createEmployee() {

		try {
			// Create the employee and verify successful submission and redirection
			addEmployeePage.fillEmployeeInformation(validEmployee);
			addEmployeePage.clickSaveButton();
			addEmployeePage.waitForConfirmationMsgToBeDisplayed();
			personalDetailsPage.waitForPersonalDetailsHeadingToBeDisplayed();

		} catch (Exception e) {
			throw new SkipException("Employee could not be created");
		}
	}

	@Test(groups = { "Regression", "PIM" })
	@TestCase(id = "TC_PIM_FORM_001")
	public void createEmployeeWithEmptyFirstName() {

		// Clear the first name field while keeping the remaining employee data valid
		validEmployee.setFirstname(TestDataGenerator.emptyString());
		addEmployeePage.fillEmployeeInformation(validEmployee);
		addEmployeePage.clickSaveButton();

		// Verify the required-field validation message is displayed for the first name
		softAssertion.assertTrue(addEmployeePage.isInputFieldErrorMsgDisplayed());
		softAssertion.assertEquals(addEmployeePage.getInputFieldErrorMsg(),AddEmployeeExpected.FAILED_MSG_WITH_EMPTY_EMPLOYEE_FNAME);
		softAssertion.assertAll();
	}

	@Test(groups = { "Regression", "PIM" })
	@TestCase(id = "TC_PIM_FORM_002")
	public void createEmployeeWithEmptyLastName() {

		// Clear the last name field while keeping the remaining employee data valid
		validEmployee.setLastname(TestDataGenerator.emptyString());
		addEmployeePage.fillEmployeeInformation(validEmployee);
		addEmployeePage.clickSaveButton();

		// Verify the required-field validation message is displayed for the last name
		softAssertion.assertTrue(addEmployeePage.isInputFieldErrorMsgDisplayed());
		softAssertion.assertEquals(addEmployeePage.getInputFieldErrorMsg(),AddEmployeeExpected.FAILED_MSG_WITH_EMPTY_EMPLOYEE_LNAME);
		softAssertion.assertAll();
	}

	@Test(groups = { "Regression", "PIM" })
	@TestCase(id = "TC_PIM_FORM_003")
	public void createEmployeeWithDuplicateEmployeeId() {

		// Create an employee to register the employee ID before testing for duplicates
		createEmployee();

		// Attempt to create another employee using the same employee ID
		employeeListPage.clickAddEmployeeButton();
		addEmployeePage.fillEmployeeInformation(validEmployee);
		addEmployeePage.clickSaveButton();

		// Verify the duplicate employee ID is rejected and the employee is not created
		softAssertion = new SoftAssert();
		softAssertion.assertEquals(addEmployeePage.isEmployeeIdErrorMsgDisplayed(), true);
		softAssertion.assertFalse(addEmployeePage.isEmployeeCreated());
		softAssertion.assertAll();
	}

	@Test(groups = { "Regression", "PIM" })
	@TestCase(id = "TC_PIM_FORM_004")
	public void createEmployeeWithFirstNameExceedingMaximumLength() {

		// Set the first name to 31 characters, exceeding the 30-character limit
		validEmployee.setFirstname(TestDataGenerator.randomFirstName(31));
		addEmployeePage.fillEmployeeInformation(validEmployee);
		addEmployeePage.clickSaveButton();

		// Verify the application rejects the first name for exceeding its maximum
		// length
		softAssertion.assertTrue(addEmployeePage.isInputFieldErrorMsgDisplayed());
		softAssertion.assertEquals(addEmployeePage.getInputFieldErrorMsg(),AddEmployeeExpected.FAILED_MSG_WITH_EMPLOYEE_FNAME_EXCEEDING_MAX_LENGTH);
		softAssertion.assertAll();
	}

	@Test(groups = { "Regression", "PIM" })
	@TestCase(id = "TC_PIM_FORM_005")
	public void createEmployeeWithLastNameExceedingMaximumLength() {

		// Set the last name to 31 characters, exceeding the 30-character limit
		validEmployee.setLastname(TestDataGenerator.randomLastName(31));
		addEmployeePage.fillEmployeeInformation(validEmployee);
		addEmployeePage.clickSaveButton();

		// Verify the application rejects the last name for exceeding its maximum length
		softAssertion.assertTrue(addEmployeePage.isInputFieldErrorMsgDisplayed());
		softAssertion.assertEquals(addEmployeePage.getInputFieldErrorMsg(),AddEmployeeExpected.FAILED_MSG_WITH_EMPLOYEE_LNAME_EXCEEDING_MAX_LENGTH);
		softAssertion.assertAll();
	}

	@Test(groups = { "Regression", "PIM" })
	@TestCase(id = "TC_PIM_FORM_006")
	public void createEmployeeWithEmployeeIdExceedingMaximumLength() {

		// Set the employee ID to 11 characters, exceeding the 10-character limit
		validEmployee.setEmployeeId(TestDataGenerator.randomEmployeeId(11));
		addEmployeePage.fillEmployeeInformation(validEmployee);
		addEmployeePage.clickSaveButton();

		// Verify the application rejects the employee ID for exceeding its maximum
		// length
		softAssertion.assertTrue(addEmployeePage.isInputFieldErrorMsgDisplayed());
		softAssertion.assertEquals(addEmployeePage.getInputFieldErrorMsg(),AddEmployeeExpected.FAILED_MSG_WITH_EMPLOYEE_ID_EXCEEDING_MAX_LENGTH);
		softAssertion.assertAll();
	}

	@Test(groups = { "Regression", "PIM" })
	@TestCase(id = "TC_PIM_FORM_007")
	public void createEmployeeWithSpecialCharactersInFirstName() {

		// Enter a first name containing five randomly generated special characters
		validEmployee.setFirstname(TestDataGenerator.specialChars(5));
		addEmployeePage.fillEmployeeInformation(validEmployee);
		addEmployeePage.clickSaveButton();

		// Verify the employee is not created and the invalid first name is flagged
		softAssertion.assertFalse(addEmployeePage.isEmployeeCreated());
		softAssertion.assertTrue(addEmployeePage.isInputFieldErrorMsgDisplayed());
		softAssertion.assertAll();
	}

	@Test(groups = { "Regression", "PIM" })
	@TestCase(id = "TC_PIM_FORM_008")
	public void createEmployeeWithSpecialCharactersInLastName() {

		// Enter a last name containing five randomly generated special characters
		validEmployee.setLastname(TestDataGenerator.specialChars(5));
		addEmployeePage.fillEmployeeInformation(validEmployee);
		addEmployeePage.clickSaveButton();

		// Verify the employee is not created and the invalid last name is flagged
		softAssertion.assertFalse(addEmployeePage.isEmployeeCreated());
		softAssertion.assertTrue(addEmployeePage.isInputFieldErrorMsgDisplayed());
		softAssertion.assertAll();
	}

	@Test(groups = { "Regression", "PIM" })
	@TestCase(id = "TC_PIM_FORM_009")
	public void createEmployeeWithNumericFirstName() {

		// Enter a first name consisting of seven randomly generated digits
		validEmployee.setFirstname(TestDataGenerator.randomNumericValue(7));
		addEmployeePage.fillEmployeeInformation(validEmployee);
		addEmployeePage.clickSaveButton();

		// Verify the employee is not created and the invalid first name is flagged
		softAssertion.assertFalse(addEmployeePage.isEmployeeCreated());
		softAssertion.assertTrue(addEmployeePage.isInputFieldErrorMsgDisplayed());
		softAssertion.assertAll();
	}

	@Test(groups = { "Regression", "PIM" })
	@TestCase(id = "TC_PIM_FORM_010")
	public void createEmployeeWithNumericLastName() {

		// Enter a last name consisting of seven randomly generated digits
		validEmployee.setLastname(TestDataGenerator.randomNumericValue(7));
		addEmployeePage.fillEmployeeInformation(validEmployee);
		addEmployeePage.clickSaveButton();

		// Verify the employee is not created and the invalid last name is flagged
		softAssertion.assertFalse(addEmployeePage.isEmployeeCreated());
		softAssertion.assertTrue(addEmployeePage.isInputFieldErrorMsgDisplayed());
		softAssertion.assertAll();
	}

	@Test(groups = { "Regression", "PIM" })
	@TestCase(id = "TC_PIM_FORM_011")
	public void createEmployeeWithOnlySpacesInFirstName() {

		// Enter a first name containing only whitespace characters
		validEmployee.setFirstname(TestDataGenerator.whitespaceString(2));
		addEmployeePage.fillEmployeeInformation(validEmployee);
		addEmployeePage.clickSaveButton();

		// Verify whitespace-only input is rejected as an empty first name
		softAssertion.assertTrue(addEmployeePage.isInputFieldErrorMsgDisplayed());
		softAssertion.assertEquals(addEmployeePage.getInputFieldErrorMsg(),AddEmployeeExpected.FAILED_MSG_WITH_EMPTY_EMPLOYEE_FNAME);
		softAssertion.assertAll();
	}

	@Test(groups = { "Regression", "PIM" })
	@TestCase(id = "TC_PIM_FORM_012")
	public void createEmployeeWithOnlySpacesInLastName() {

		// Enter a last name containing only whitespace characters
		validEmployee.setLastname(TestDataGenerator.whitespaceString(2));
		addEmployeePage.fillEmployeeInformation(validEmployee);
		addEmployeePage.clickSaveButton();

		// Verify whitespace-only input is rejected as an empty last name
		softAssertion.assertTrue(addEmployeePage.isInputFieldErrorMsgDisplayed());
		softAssertion.assertEquals(addEmployeePage.getInputFieldErrorMsg(),AddEmployeeExpected.FAILED_MSG_WITH_EMPTY_EMPLOYEE_LNAME);
		softAssertion.assertAll();
	}
}