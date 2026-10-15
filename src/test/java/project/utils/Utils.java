package project.utils;

import java.nio.file.Paths;

//import java.util.Scanner;

public class Utils {

	

	public static String getTestFilePath(String fileName) {

		return Paths.get("src", "test", "resources", "testdatafiles", fileName).toAbsolutePath().toString();
	}

}
