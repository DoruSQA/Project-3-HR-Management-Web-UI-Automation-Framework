package project.testdata;

import project.testdata.model.EmployeeUser;
import project.testdata.model.LoginUser;
import project.testdata.model.NewAccountUser;
import project.utils.DataReader;

public class TestDataFactory {

	public static LoginUser createAdminUserData() {

		return DataReader.read("adminUser", LoginUser.class);
	}

	public static LoginUser createInvalidUserData() {

		return DataReader.read("invalidUser", LoginUser.class);
	}
	
	public static EmployeeUser createValidEmployeeUser()
	{
		return DataReader.read("validEmployeeUser", EmployeeUser.class);
	}
	
	public static NewAccountUser createValidUserAccount()
	{
		return DataReader.read("validAccountUser", NewAccountUser.class);
	}
	
}
