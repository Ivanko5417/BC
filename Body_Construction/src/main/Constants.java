package main;

import java.util.Properties;

public class Constants {
	public static final int SPAM = 0, CALL = 1, COURIER = 2, TRAINER = 3;

	public static final String DRIVER_NAME = "com.mysql.jdbc.Driver",
			SERVER_NAME = "212.24.56.196",
		//	SERVER_NAME = "localhost",
	 BASE_NAME = "bc",
	 URL = "jdbc:mysql://" + SERVER_NAME + "/" + BASE_NAME,
	//		 USER_NAME = "root",
	 USER_NAME = "alex1515",
	 PASSWORD = "QWEqwe123";
	 //PASSWORD = "";
	public static  Properties connInfo = new Properties();
}
