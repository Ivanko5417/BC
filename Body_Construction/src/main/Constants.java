package main;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.text.MaskFormatter;
public class Constants {
	public static Statement stmt, stmt1, stmt2;
	public static final String DRIVER_NAME = "com.mysql.jdbc.Driver",
			SERVER_NAME = "localhost",
			BASE_NAME = "bc",
			URL = "jdbc:mysql://" + SERVER_NAME + "/" + BASE_NAME,
			USER_NAME = "root", PASSWORD = "";
	public static Properties connInfo = new Properties();
	public static MaskFormatter NUMBER_MASK;
	public static KeyListener keylistenerForDouble = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e) {
			char key = e.getKeyChar();
			if (!Character.isDigit(key) && key != '.')
				e.consume();
		}
		@Override
		public void keyReleased(KeyEvent e) {
		}
		@Override
		public void keyPressed(KeyEvent e) {
		}
	};
	public static KeyListener keylistenerForNumbers = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e) {
			char key = e.getKeyChar();
			if (!Character.isDigit(key))
				e.consume();
		}
		@Override
		public void keyReleased(KeyEvent e) {
		}
		@Override
		public void keyPressed(KeyEvent e) {
		}
	};
	public static final Font GENERAL_LABEL_FONT = new Font("Arial", 1, 20);
	public static class TypesOfClient {
		public static final int SPAM = 0, CALL = 1, DELIVERY = 21,
				DELIVERY_AND_TRIAL = 22, TRIAL = 3, TRIAL_PASS = 31, TRIAL_TRIAL = 32, TRIAL_NOTHING = 33, PAY = 4, CLIENT = 5,
				SINK = 10;
		private TypesOfClient() {
		}
	}
	public static class NamesOfTables {
		public static final String NUMBERS = "numbers", CLIENTS = "clients", DATES = "dates", USERS = "users", PAYMENTS = "payments";
		private NamesOfTables() {
		}
	}
	public static class TypesOfDates {
		public static final int CALL = 0, DELIVERY = 1,
				TRIAL = 2,
				PAY = 4, CLIENT = 5;
		private TypesOfDates() {
		}
	}
	public static class StatesOfDates {
		public static final int WAIT = 0, SUCCESSFUL = 1, SHIFT = 3, UNSUCCESSFUL = 2;
		private StatesOfDates() {
		}
	}
	public static class TypesOfTrain {
		public static final int DRYING = 0, MASS = 1;
		private TypesOfTrain() {
		}
	}
	public static class TypesOfUsers {
		public static final int SPAM = 0, CALL = 1, COURIER = 2, TRAINER = 3, ADMIN = 10;
		private TypesOfUsers() {
		}
	}
	public static class NameOfColors
	{
		public static final String  GREEN = "green", RED = "red", BLUE = "blue", YELLOW = "yellow", WHITE = "white";
		private NameOfColors()
		{	
		}
	}
	public static class WhereMoney
	{
		public static final int  CASH = 0, CASH_BOX = 1;
		private WhereMoney()
		{	
		}
	}
	public static Font BUTTON_FONT;
}
