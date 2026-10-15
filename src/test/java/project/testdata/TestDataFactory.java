package project.testdata;

import project.testdata.modeldata.EmployeeUser;
import project.testdata.modeldata.LoginUser;
import project.testdata.modeldata.NewAccountUser;
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
