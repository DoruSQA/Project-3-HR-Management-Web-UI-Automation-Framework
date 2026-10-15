package project.tests;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import orangeHRM.test_metadata.TestCase;
import project.base.BaseTest;
import project.expected.PersonalDetailsExpected;
import project.testdata.TestDataFactory;

public class EmployeeUpdateTests extends BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setUpEmployeeUpdate() {

        try {
            // Prepare employee test data and authenticate as an administrator
            validEmployee = TestDataFactory.createValidEmployeeUser();
            validEmployee.setFirstname("JonnyUpdate").setLastname("CageUpdate").setEmployeeId("12Update");
            loginPage.authentication(adminUser);

            // Navigate to the employee creation form and register the test employee
            dashboard.openPersonnelInformationManagement();
            employeeListPage.clickAddEmployeeButton();
            addEmployeePage.fillEmployeeInformation(validEmployee);
            addEmployeePage.clickSaveButton();

            // Verify successful employee creation before executing the update tests
            addEmployeePage.waitForConfirmationMsgToBeDisplayed();
            personalDetailsPage.waitForPersonalDetailsHeadingToBeDisplayed();

        } catch (Exception e) {
            throw new SkipException("BeforeMethod failed", e);
        }
    }

    @Test(groups = {"Regression", "PIM"})
    @TestCase(id = "TC_PIM_UPDATE_001")
    public void updateEmployeeFirstName() {

        // Open the employee list, locate the test employee, and open the personal details page
        dashboard.openPersonnelInformationManagement();
        employeeListPage.enterEmployeeId(validEmployee.getEmployeeId()).clickSearchButton().clickUpdateIcon();

        // Update the employee's first name and save the changes
        String updatedFirstName = "updatedFirstName";
        personalDetailsPage.updateFirstName(updatedFirstName).clickSaveButton();

        // Verify the employee update confirmation message is displayed
        Assert.assertTrue(personalDetailsPage.waitForConfirmationMsgToBeDisplayed());

        // Return to the employee list and search for the updated employee record
        personalDetailsPage.clickEmployeeListButton();
        employeeListPage.enterEmployeeId(validEmployee.getEmployeeId()).clickSearchButton();

        // Verify the updated first name is reflected in the employee table
        softAssertion.assertTrue(employeeListPage.isUpdatedValueReflectedInTheTable(updatedFirstName));
        softAssertion.assertAll();
    }

    @Test(groups = {"Regression", "PIM"})
    @TestCase(id = "TC_PIM_UPDATE_002")
    public void updateEmployeeLastName() {

        // Open the employee list, locate the test employee, and open the personal details page
        dashboard.openPersonnelInformationManagement();
        employeeListPage.enterEmployeeId(validEmployee.getEmployeeId()).clickSearchButton().clickUpdateIcon();

        // Update the employee's last name and save the changes
        String updatedLastName = "updatedLastName";
        personalDetailsPage.updateLastName(updatedLastName).clickSaveButton();

        // Verify the employee update confirmation message is displayed
        Assert.assertTrue(personalDetailsPage.waitForConfirmationMsgToBeDisplayed());

        // Return to the employee list and search for the updated employee record
        personalDetailsPage.clickEmployeeListButton();
        employeeListPage.enterEmployeeId(validEmployee.getEmployeeId()).clickSearchButton();

        // Verify the updated last name is reflected in the employee table
        softAssertion.assertTrue(employeeListPage.isUpdatedValueReflectedInTheTable(updatedLastName));
        softAssertion.assertAll();
    }

    @Test(groups = {"Regression", "PIM"})
    @TestCase(id = "TC_PIM_UPDATE_003")
    public void updateEmployeeId() {

        // Open the employee list, locate the test employee, and open the personal details page
        dashboard.openPersonnelInformationManagement();
        employeeListPage.enterEmployeeId(validEmployee.getEmployeeId()).clickSearchButton().clickUpdateIcon();

        // Update the employee ID and save the changes
        String updatedEmployeeId = "9993991";
        personalDetailsPage.updateEmployeeId(updatedEmployeeId).clickSaveButton();

        // Verify the employee update confirmation message is displayed
        Assert.assertTrue(personalDetailsPage.waitForConfirmationMsgToBeDisplayed());

        // Update the test data and search for the employee using the new ID
        validEmployee.setEmployeeId(updatedEmployeeId);
        personalDetailsPage.clickEmployeeListButton();
        employeeListPage.enterEmployeeId(updatedEmployeeId).clickSearchButton();

        // Verify the updated employee ID is reflected in the employee table
        softAssertion.assertTrue(employeeListPage.isUpdatedValueReflectedInTheTable(updatedEmployeeId));
        softAssertion.assertAll();
    }

    @Test(groups = {"Regression", "PIM"})
    @TestCase(id = "TC_PIM_UPDATE_004")
    public void updateEmployeeWithDuplicateEmployeeId() {

        // Retrieve an employee ID that is already registered in the system
        String duplicatedEmployeeId = getDuplicateEmployeeId();

        // Locate the test employee and attempt to assign the duplicate employee ID
        dashboard.openPersonnelInformationManagement();
        employeeListPage.enterEmployeeId(validEmployee.getEmployeeId()).clickSearchButton().clickUpdateIcon();
        personalDetailsPage.updateEmployeeId(duplicatedEmployeeId).clickSaveButton();

        // Verify the duplicate employee ID error is displayed and the update is rejected
        Assert.assertEquals(personalDetailsPage.getEmployeeIdDubplicateErrorMsg(),PersonalDetailsExpected.FAILED_MSG_DUPLICATE_EMPLOYEE_ID);
        Assert.assertFalse(personalDetailsPage.waitForConfirmationMsgToBeDisplayed());
    }

    private String getDuplicateEmployeeId() {

        // Open the employee list and retrieve an existing employee ID for the duplicate ID test
        dashboard.openPersonnelInformationManagement();
        return employeeListPage.getFirstEmployeeIdFromTable();
    }
}
