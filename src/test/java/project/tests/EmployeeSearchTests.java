package project.tests;

import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import orangeHRM.test_metadata.TestCase;
import project.base.BaseTest;
import project.expected.EmployeeSearchExpected;
import project.testdata.TestDataFactory;
import project.testdata.TestDataGenerator;

public class EmployeeSearchTests extends BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setUpEmployeeSearch() {

        try {
            // Prepare employee test data and authenticate as an administrator
            validEmployee = TestDataFactory.createValidEmployeeUser();
            validEmployee.setFirstname("JonnySearch").setLastname("CageSearch").setEmployeeId("1Search");
            loginPage.authentication(adminUser);

            // Navigate to the employee creation form and register the test employee
            dashboard.openPersonnelInformationManagement();
            employeeListPage.clickAddEmployeeButton();
            addEmployeePage.fillEmployeeInformation(validEmployee);
            addEmployeePage.clickSaveButton();

            // Verify successful employee creation before executing the search tests
            addEmployeePage.waitForConfirmationMsgToBeDisplayed();
            personalDetailsPage.waitForPersonalDetailsHeadingToBeDisplayed();

        } catch (Exception e) {
            throw new SkipException("BeforeMethod failed", e);
        }
    }

    @Test(groups = {"Smoke", "PIM"})
    @TestCase(id = "TC_PIM_SEARCH_001")
    public void searchEmployeeByEmployeeId() {

        // Open the employee list and search using the registered employee ID
        dashboard.openPersonnelInformationManagement();
        employeeListPage.searchByEmployeeId(validEmployee.getEmployeeId());

        // Verify the employee is found and the search returns matching records
        softAssertion.assertNotEquals( employeeListPage.getSearchedRecordsResultHeading(), EmployeeSearchExpected.NO_RECORD_FOUND_MSG);
        softAssertion.assertFalse(employeeListPage.isNoRecordsFoundMsgDisplayed());
        softAssertion.assertAll();
    }

    @Test(groups = {"Regression", "PIM"})
    @TestCase(id = "TC_PIM_SEARCH_002")
    public void searchEmployeeByFirstName() {

        // Open the employee list and search using the registered employee's first name
        dashboard.openPersonnelInformationManagement();
        employeeListPage.searchByFirstName(validEmployee.getFirstname());

        // Verify the employee is found and the search returns matching records
        softAssertion.assertNotEquals(employeeListPage.getSearchedRecordsResultHeading(),EmployeeSearchExpected.NO_RECORD_FOUND_MSG);
        softAssertion.assertFalse(employeeListPage.isNoRecordsFoundMsgDisplayed());
        softAssertion.assertAll();
    }

    @Test(groups = {"Regression", "PIM"})
    @TestCase(id = "TC_PIM_SEARCH_003")
    public void searchEmployeeByLastName() {

        // Open the employee list and search using the registered employee's last name
        dashboard.openPersonnelInformationManagement();
        employeeListPage.searchByLastName(validEmployee.getLastname());

        // Verify the employee is found and the search returns matching records
        softAssertion.assertNotEquals( employeeListPage.getSearchedRecordsResultHeading(),EmployeeSearchExpected.NO_RECORD_FOUND_MSG);
        softAssertion.assertFalse(employeeListPage.isNoRecordsFoundMsgDisplayed());
        softAssertion.assertAll();
    }

    @Test(groups = {"Regression", "PIM"})
    @TestCase(id = "TC_PIM_SEARCH_004")
    public void searchEmployeeThatDoesNotExist() {

        // Search for an employee using a randomly generated, non-existing last name
        dashboard.openPersonnelInformationManagement();
        employeeListPage.searchByLastName(TestDataGenerator.randomLastName(6));

        // Verify the search returns the expected no-records-found message
        softAssertion.assertEquals(employeeListPage.getSearchedRecordsResultHeading(),EmployeeSearchExpected.NO_RECORD_FOUND_MSG);
        softAssertion.assertTrue(employeeListPage.isNoRecordsFoundMsgDisplayed());
        softAssertion.assertAll();
    }
}
