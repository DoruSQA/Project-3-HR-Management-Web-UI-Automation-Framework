package project.tests;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import orangeHRM.test_metadata.TestCase;
import project.base.BaseTest;
import project.expected.EmployeeDeleteExpected;
import project.testdata.TestDataFactory;

public class EmployeeDeleteTests extends BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setUpEmployeeDelete() {

        try {
            // Create a unique employee test record for the deletion scenarios
            validEmployee = TestDataFactory.createValidEmployeeUser();
            validEmployee.setFirstname("JonnyDel").setLastname("CageDel").setEmployeeId("1Del");

            // Log in as administrator and navigate to the employee creation form
            loginPage.authentication(adminUser);
            dashboard.openPersonnelInformationManagement();
            employeeListPage.clickAddEmployeeButton();

            // Create the employee required for the deletion tests
            addEmployeePage.fillEmployeeInformation(validEmployee);
            addEmployeePage.clickSaveButton();

            // Verify the employee creation process has completed successfully
            addEmployeePage.waitForConfirmationMsgToBeDisplayed();
            personalDetailsPage.waitForPersonalDetailsHeadingToBeDisplayed();

        } catch (Exception e) {
            throw new SkipException("BeforeMethod failed", e);
        }
    }

    @Test(groups = { "Regression", "Smoke", "PIM" })
    @TestCase(id = "TC_PIM_DELETE_001")
    public void deleteEmployeeUsingDeleteIcon() {

        // Navigate to the employee list and locate the employee by ID
        dashboard.openPersonnelInformationManagement();
        employeeListPage.searchByEmployeeId(validEmployee.getEmployeeId());

        // Delete the employee using the row-level delete icon and confirm the action
        employeeListPage.clickDeleteIcon().acceptConfirmationToDelete();

        // Verify the employee deletion confirmation message is displayed
        Assert.assertTrue(employeeListPage.isConfirmationMsgDisplayed());

        // Search for the deleted employee to verify the removal from the list
        employeeListPage.searchByEmployeeId(validEmployee.getEmployeeId());

        // Verify the employee no longer appears in the search results
        Assert.assertTrue(employeeListPage.isNoRecordsFoundMsgDisplayed());
        Assert.assertEquals(employeeListPage.getSearchedRecordsResultHeading(),EmployeeDeleteExpected.NO_RECORD_FOUND_MSG );
    }

    @Test(groups = { "Regression", "PIM" })
    @TestCase(id = "TC_PIM_DELETE_002")
    public void deleteEmployeeUsingCheckBox() {

        // Navigate to the employee list and locate the employee by ID
        dashboard.openPersonnelInformationManagement();
        employeeListPage.searchByEmployeeId(validEmployee.getEmployeeId());

        // Select the employee using the checkbox and confirm the bulk deletion
        employeeListPage.clickDeleteCheckBox()
                .clickDeleteSelectedItemsBtn()
                .acceptConfirmationToDelete();

        // Verify the employee deletion confirmation message is displayed
        Assert.assertTrue(employeeListPage.isConfirmationMsgDisplayed());

        // Search for the deleted employee to verify the removal from the list
        employeeListPage.searchByEmployeeId(validEmployee.getEmployeeId());

        // Verify the employee no longer appears in the search results
        Assert.assertTrue(employeeListPage.isNoRecordsFoundMsgDisplayed());
        Assert.assertEquals(employeeListPage.getSearchedRecordsResultHeading(),EmployeeDeleteExpected.NO_RECORD_FOUND_MSG);
    }

    @Test(groups = { "Regression", "PIM" })
    @TestCase(id = "TC_PIM_DELETE_003")
    public void cancelEmployeeDeletionFromConfirmationDialogBox() {

        // Navigate to the employee list and locate the employee by ID
        dashboard.openPersonnelInformationManagement();
        employeeListPage.searchByEmployeeId(validEmployee.getEmployeeId());

        // Open the deletion confirmation dialog and cancel the operation
        employeeListPage.clickDeleteIcon().clickCancelConfirmationBtn();

        // Verify no deletion confirmation message is displayed
        Assert.assertFalse(employeeListPage.isConfirmationMsgDisplayed());

        // Search for the employee to verify the cancellation preserved the record
        employeeListPage.searchByEmployeeId(validEmployee.getEmployeeId());

        // Verify the employee remains in the search results
        Assert.assertFalse(employeeListPage.isNoRecordsFoundMsgDisplayed());
        Assert.assertNotEquals(employeeListPage.getSearchedRecordsResultHeading(),EmployeeDeleteExpected.NO_RECORD_FOUND_MSG);
    }
}
