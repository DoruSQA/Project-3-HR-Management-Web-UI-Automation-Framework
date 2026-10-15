package project.tests;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import project.base.BaseTest;
import project.testdata.TestDataFactory;

public class EmployeeDeleteTests extends BaseTest {


	@BeforeMethod(alwaysRun = true)
	public void setUpEmployeeDelete() {
		
		try {
			validEmployee = TestDataFactory.createValidEmployeeUser();
			validEmployee.setFirstname("JonnyDel").setLastname("CageDel").setEmployeeId("1Del");
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
	

	@Test(groups = { "Regression", "PIM"}, description = "TC_PIM_DELETE_001")
	public void deleteEmployeeUsingDeleteIcon() {
		
		// Test Steps
		dashboard.openPersonnelInformationManagement();
		employeeListPage.searchByEmployeeId(validEmployee.getEmployeeId());
		employeeListPage.clickDeleteIcon().acceptConfirmationToDelete();
		//createdEmployeeId=null;
		
		// Validation_1: On confirmation message
		Assert.assertTrue(employeeListPage.isConfirmationMsgDisplayed(), "Confirmation message is not displayed");
		
		// Intermediary test steps (Search again)
		employeeListPage.searchByEmployeeId(validEmployee.getEmployeeId());
		
		// Validation_2: Employee is no longer listed
		Assert.assertTrue(employeeListPage.isNoRecordsFoundMsgDisplayed(), "No record message is not displayed");
		Assert.assertEquals(employeeListPage.getSearchedRecordsResultHeading(), "No Records Found", "Table heading message doesn't match");
		
	}

	@Test(groups = { "Regression", "PIM"}, description = "TC_PIM_DELETE_002")
	public void deleteEmployeeUsingCheckBox() {
		
		// Test Steps
		dashboard.openPersonnelInformationManagement();
		employeeListPage.searchByEmployeeId(validEmployee.getEmployeeId());
		employeeListPage.clickDeleteCheckBox().clickDeleteSelectedItemsBtn().acceptConfirmationToDelete();

		// Validation_1: On confirmation message
		Assert.assertTrue(employeeListPage.isConfirmationMsgDisplayed(), "Confirmation message is not displayed");
		
		// Intermediary test steps (Search again)
		employeeListPage.searchByEmployeeId(validEmployee.getEmployeeId());
		
		// Validation_2: Employee is no longer listed
		Assert.assertTrue(employeeListPage.isNoRecordsFoundMsgDisplayed(), "No record message is not displayed");
		Assert.assertEquals(employeeListPage.getSearchedRecordsResultHeading(), "No Records Found", "Table heading message doesn't match");
	}
	
	@Test(groups = { "Regression", "PIM"}, description = "TC_PIM_DELETE_003")
	public void cancelEmployeeDeletionFromConfirmationDialogBox(){
		
		// Test Steps
		dashboard.openPersonnelInformationManagement();
		employeeListPage.searchByEmployeeId(validEmployee.getEmployeeId());
		employeeListPage.clickDeleteIcon().clickCancelConfirmationBtn();
		
		// Validation_1: On confirmation message
		Assert.assertFalse(employeeListPage.isConfirmationMsgDisplayed(), "Confirmation message should not be displayed");
		
		// Intermediary test steps (Search again)
		employeeListPage.searchByEmployeeId(validEmployee.getEmployeeId());
		
		// Validation_2: Employee is no longer listed
		Assert.assertFalse(employeeListPage.isNoRecordsFoundMsgDisplayed(), "No record message should not be displayed");
		Assert.assertNotEquals(employeeListPage.getSearchedRecordsResultHeading(), "No Records Found", "Table heading message doesn't match");

	}

}
