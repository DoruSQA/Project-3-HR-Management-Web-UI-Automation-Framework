package project.testdata.modeldata;

public class EmployeeUser {

	
	private String firstname;
	private String lastname;
    private String employeeId;
	
    public EmployeeUser setFirstname(String firstname) {
		this.firstname = firstname;
		return this;
	}

	public EmployeeUser setLastname(String lastname) {
		this.lastname = lastname;
		return this;
	}

	public EmployeeUser setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
		return this;
	}
 

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
    
    public String getEmployeeId()
    {
    	return employeeId;
    }
    
    
}
