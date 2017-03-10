package main;
import java.awt.Font;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;
public class Constants {
	public static Statement stmt, stmt1, stmt2;
	public static final String DRIVER_NAME = "com.mysql.jdbc.Driver",
			SERVER_NAME = "localhost",
			BASE_NAME = "bc",
			URL = "jdbc:mysql://" + SERVER_NAME + "/" + BASE_NAME,
			USER_NAME = "root", PASSWORD = "";
	public static Properties connInfo = new Properties();
	public static final Font GENERAL_LABEL_FONT = new Font("Arial", 1, 20);
	public static class TypesOfClient {
		public static final int SPAM = 0, CALL = 1, DELIVERY = 21,
				SUCCESSFUL_DELIVERY = 2, TRIAL = 3, PAY = 4, CLIENT = 5,
				SINK = 10;
		private TypesOfClient() {
		}
	}
	public static class NamesOfTables {
		public static final String NUMBERS = "numbers", CLIENTS = "clients", DATES = "dates", USERS = "users";
		private NamesOfTables() {
		}
	}
	public static class TypesOfDates {
		public static final int CALL = 0, DELIVERY = 1,
				SUCCESSFUL_DELIVERY = 11, UNSUCCESSFUL_DELIVERY = 12, TRIAL = 2,
				PAY = 4, CLIENT = 5;
		private TypesOfDates() {
		}
	}
	public static class TypesOfTrain {
		public static final int DRYING = 0, MASS = 1;
		private TypesOfTrain() {
		}
	}
	public static class TypesOfUsers {
		public static final int SPAM = 0, CALL = 1, COURIER = 2, TRAINER = 3;
		private TypesOfUsers() {
		}
	}
}
