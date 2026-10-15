package project.expected;

import project.utils.Utils;

public class AddEmployeeExpected {
	
	// Static text
	public final static String URL = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
	public final static String VALID_PROFILE_PICTURE_PATH = Utils.getTestFilePath("profile-picture-valid.png");
	public final static String WRONG_FORMAT_PROFILE_PICTURE_PATH = Utils.getTestFilePath("profile-picture-wrong-format.txt");
	public final static String CORUPTED_PROFILE_PICTURE_PATH = Utils.getTestFilePath("profile-picture-corrupted.png");
	public final static String LARGE_PROFILE_PICTURE_PATH = Utils.getTestFilePath("profile-picture-large.png");

	// Error messages
	public final static String FAILED_MSG_WITH_EMPTY_EMPLOYEE_FNAME = "Required";
	public final static String FAILED_MSG_WITH_EMPTY_EMPLOYEE_LNAME = "Required";
	public final static String FAILED_MSG_WITH_EMPLOYEE_FNAME_EXCEEDING_MAX_LENGTH = "Should not exceed 30 characters";
	public final static String FAILED_MSG_WITH_EMPLOYEE_LNAME_EXCEEDING_MAX_LENGTH = "Should not exceed 30 characters";
	public final static String FAILED_MSG_WITH_EMPLOYEE_ID_EXCEEDING_MAX_LENGTH = "Should not exceed 10 characters";
	public final static String FAILED_MSG_UNSUPORTED_PROFILE_PICTURE_FORMAT = "File type not allowed";
	public final static String FAILED_MSG_PROFILE_PICTURE_EXCEEDING_MAX_SIZE = "Attachment Size Exceeded";
}
