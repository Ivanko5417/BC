package main;

import java.util.Properties;

public class Constants {
	public static final int SPAM = 0, CALL = 1, COURIER = 2, TRAINER = 3;

	public static final String DRIVER_NAME = "com.mysql.jdbc.Driver",
			//SERVER_NAME = "vdroby1c.beget.tech",
			SERVER_NAME = "5.9.23.71",
	// BASE_NAME = "vdroby1c_bc",
			//BASE_NAME = "bc",
			BASE_NAME = "alex1515_bc",
	 URL = "jdbc:mysql://" + SERVER_NAME + "/" + BASE_NAME,
			 USER_NAME = "alex1515_root",
			 PASSWORD = "446daa34889a";
	// USER_NAME = "alex1515",	
	// PASSWORD = "0M5f9L4z";
	//USER_NAME = "vdroby1c_bc",	
	// PASSWORD = "123123";
	public static  Properties connInfo = new Properties();
}
