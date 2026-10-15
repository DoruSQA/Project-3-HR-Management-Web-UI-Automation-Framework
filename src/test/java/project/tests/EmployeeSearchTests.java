package project.tests;

import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import project.base.BaseTest;
import project.testdata.TestDataFactory;
import project.testdata.TestDataGenerator;

public class EmployeeSearchTests extends BaseTest {

	
	@BeforeMethod(alwaysRun = true)
	public void setUpEmployeeSearch()
	{
		try {
			validEmployee = TestDataFactory.createValidEmployeeUser();
			validEmployee.setFirstname("JonnySearch").setLastname("CageSearch").setEmployeeId("1Search");
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
	
	@Test(groups = {"Smoke","PIM"}, description = "TC_PIM_SEARCH_001")
	public void searchEmployeeByEmployeeId() {
		
		//Test Steps
		dashboard.openPersonnelInformationManagement();
		employeeListPage.searchByEmployeeId(validEmployee.getEmployeeId());
		
		//Validation
		softAssertion.assertNotEquals(employeeListPage.getSearchedRecordsResultHeading(), "No Records Found", "The record was not found");
		softAssertion.assertFalse(employeeListPage.isNoRecordsFoundMsgDisplayed(), "Error message should not be displayed");
		softAssertion.assertAll();
		
	}

	@Test(groups = {"Regression","PIM"}, description = "TC_PIM_SEARCH_002")
	public void searchEmployeeByFirstName() {
		
		//Test Steps
		dashboard.openPersonnelInformationManagement();
		employeeListPage.searchByFirstName(validEmployee.getFirstname());
				
		//Validation
		softAssertion.assertNotEquals(employeeListPage.getSearchedRecordsResultHeading(), "No Records Found", "The record was not found");
		softAssertion.assertFalse(employeeListPage.isNoRecordsFoundMsgDisplayed(), "Error message should not be displayed");
		softAssertion.assertAll();
		
	}

	@Test(groups = {"Regression","PIM"}, description = "TC_PIM_SEARCH_003")
	public void searchEmployeeByLastName() {
		
		//Test Steps
		dashboard.openPersonnelInformationManagement();
		employeeListPage.searchByLastName(validEmployee.getLastname());
						
		//Validation
		softAssertion.assertNotEquals(employeeListPage.getSearchedRecordsResultHeading(), "No Records Found", "The record was not found");
		softAssertion.assertFalse(employeeListPage.isNoRecordsFoundMsgDisplayed(), "Error message should not be displayed");
		softAssertion.assertAll();
		
	}
	
	@Test(groups = {"Regression","PIM"}, description = "TC_PIM_SEARCH_004")
	public void searchEmployeeThatDoesNotExist() {
		
		//Test Steps
		dashboard.openPersonnelInformationManagement();
		employeeListPage.searchByLastName(TestDataGenerator.randomLastName(6));
								
		//Validation
		softAssertion.assertEquals(employeeListPage.getSearchedRecordsResultHeading(), "No Records Found", "The record was found");
		softAssertion.assertTrue(employeeListPage.isNoRecordsFoundMsgDisplayed(), "Error message should be displayed");
		softAssertion.assertAll();
		
	}
}
