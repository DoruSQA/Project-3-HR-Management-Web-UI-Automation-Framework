package project.testdata.modeldata;

public class LoginUser{

    public LoginUser setUsername(String username) {
		this.username = username;
		return this;
	}

	public LoginUser setPassword(String password) {
		this.password = password;
		return this;
	}

	private String username;
    private String password;
 

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
  

   
}
