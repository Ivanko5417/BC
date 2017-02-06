package main;

import static main.Constans.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQL {

	public static ResultSet doSQL(String query, Connection connect)
	{
		ResultSet rs = null;
		try {
			Class.forName(DRIVER_NAME);
			Statement stmt = connect.createStatement();
			rs = stmt.executeQuery(query);
			return rs;
		} catch (Exception e) {
			System.out.println("Проблема с БД. Запрос=" + query);
			return rs;
		}
	}
	public static void doSQLWithoutResult(String query, Connection connect)
	{
		try {
			Class.forName(DRIVER_NAME);
			Statement stmt = connect.createStatement();
			stmt.executeUpdate(query);
		} catch (Exception e) {
			System.out.println("Проблема с БД. Запрос=" + query);
			System.out.println(e.getMessage());
		}
	}
	public static void closeConnect(Connection connect)
	{
		try {
			connect.close();
		} catch (SQLException e) {
			System.out.println("Ошибка с закрытием соединения.");
		}
	}
	
}
