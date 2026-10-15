package project.tests;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import project.base.BaseTest;
import project.expected.PersonalDetailsExpected;
import project.testdata.TestDataFactory;

public class EmployeeUpdateTests extends BaseTest {


	@BeforeMethod(alwaysRun = true)
	public void setUpEmployeeUpdate()
	{
		try {
			validEmployee = TestDataFactory.createValidEmployeeUser();
			validEmployee.setFirstname("JonnyUpdate").setLastname("CageUpdate").setEmployeeId("12Update");
			loginPage.authentication(adminUser);
			dashboard.openPersonnelInformationManagement();
			employeeListPage.clickAddEmployeeButton();
			addEmployeePage.fillEmployeeInformation(validEmployee);
			addEmployeePage.clickSaveButton();
			
			addEmployeePage.waitForConfirmationMsgToBeDisplayed();			
			personalDetailsPage.waitForPersonalDetailsHeadingToBeDisplayed();
	
		}
		catch (Exception e)
		{
			throw new SkipException("BeforeMethod failed", e);
		}
	}
	
	@Test(groups = {"Regression","PIM"}, description = "TC_PIM_UPDATE_001")
	public void updateEmployeeFirstName() {
		
		//Test Steps
		dashboard.openPersonnelInformationManagement();
		employeeListPage.enterEmployeeId(validEmployee.getEmployeeId()).clickSearchButton().clickUpdateIcon();
		String updatedFirstName="updatedFirstName";
		personalDetailsPage.updateFirstName(updatedFirstName).clickSaveButton();
		
		//Validation_1: On personalDetailsPage
		Assert.assertTrue(personalDetailsPage.waitForConfirmationMsgToBeDisplayed(), "Update was not succesfully completed");
		
		//Intermediary Test Steps
		personalDetailsPage.clickEmployeeListButton();
		employeeListPage.enterEmployeeId(validEmployee.getEmployeeId()).clickSearchButton();
		
		//Validation_2: On employeeListPage
		softAssertion.assertTrue(employeeListPage.isUpdatedValueReflectedInTheTable(updatedFirstName), "Updated value is not reflected on frontend");
		softAssertion.assertAll();
		
	}

	@Test(groups = {"Regression","PIM"}, description = "TC_PIM_UPDATE_002")
	public void updateEmployeeLastName() {
		
		//Test Steps
		dashboard.openPersonnelInformationManagement();
		employeeListPage.enterEmployeeId(validEmployee.getEmployeeId()).clickSearchButton().clickUpdateIcon();
		String updatedLastName="updatedLastName";
		personalDetailsPage.updateLastName(updatedLastName).clickSaveButton();
		
		//Validation_1: On personalDetailsPage
		Assert.assertTrue(personalDetailsPage.waitForConfirmationMsgToBeDisplayed(), "Update was not succesfully completed");
		
		//Intermediary Test Steps
		personalDetailsPage.clickEmployeeListButton();
		employeeListPage.enterEmployeeId(validEmployee.getEmployeeId()).clickSearchButton();
		
		//Validation_2: On employeeListPage
		softAssertion.assertTrue(employeeListPage.isUpdatedValueReflectedInTheTable(updatedLastName), "Updated value is not reflected on frontend");
		softAssertion.assertAll();
		
	}

	@Test(groups = {"Regression","PIM"}, description = "TC_PIM_UPDATE_003")
	public void updateEmployeeId() {
		
		//Test Steps
		dashboard.openPersonnelInformationManagement();
		employeeListPage.enterEmployeeId(validEmployee.getEmployeeId()).clickSearchButton().clickUpdateIcon();
		String updatedEmployeeId="9993991";
		personalDetailsPage.updateEmployeeId(updatedEmployeeId).clickSaveButton();
		
		//Validation_1: On personalDetailsPage
		Assert.assertTrue(personalDetailsPage.waitForConfirmationMsgToBeDisplayed(), "Update was not succesfully completed");
			
		//Intermediary Test Steps
		validEmployee.setEmployeeId(updatedEmployeeId);
		personalDetailsPage.clickEmployeeListButton();
		employeeListPage.enterEmployeeId(updatedEmployeeId).clickSearchButton();
		
		//createdEmployeeId=updatedEmployeeId;
		
		//Validation_2: On employeeListPage
		softAssertion.assertTrue(employeeListPage.isUpdatedValueReflectedInTheTable(updatedEmployeeId), "Updated value is not reflected on frontend");
		softAssertion.assertAll();
		
	}

	@Test(groups = {"Regression","PIM"}, description = "TC_PIM_UPDATE_004")
	public void updateEmployeeWithDuplicateEmployeeId() {
		
		//Test Steps
		String duplicatedEmployeeId=getDuplicateEmployeeId();
		dashboard.openPersonnelInformationManagement();
		employeeListPage.enterEmployeeId(validEmployee.getEmployeeId()).clickSearchButton().clickUpdateIcon();
		personalDetailsPage.updateEmployeeId(duplicatedEmployeeId).clickSaveButton();
						
		//Validation: On personalDetailsPage
		Assert.assertEquals(personalDetailsPage.getEmployeeIdDubplicateErrorMsg(),PersonalDetailsExpected.FAILED_MSG_DUPLICATE_EMPLOYEE_ID, "Error msg is not correct");
		Assert.assertFalse(personalDetailsPage.waitForConfirmationMsgToBeDisplayed(), "Update confirmation message should not be displayed");
		
	}
	
	
	private String getDuplicateEmployeeId()
	{
		dashboard.openPersonnelInformationManagement();
		return employeeListPage.getFirstEmployeeIdFromTable();
		
	}
	
	
	
	

}
