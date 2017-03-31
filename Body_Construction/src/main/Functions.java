package main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Functions {
	public static boolean isTimeuser(int TypeOfUser) {
		if (TypeOfUser == Constants.TypesOfUsers.CALL
				|| TypeOfUser == Constants.TypesOfUsers.COURIER
				|| TypeOfUser == Constants.TypesOfUsers.TRAINER)
			return true;
		else
			return false;
	}
	public static int getTypeOfTrain(String StringTypeOfTrain) {
		if (StringTypeOfTrain.equals("Сушка"))
			return Constants.TypesOfTrain.DRYING;
		if (StringTypeOfTrain.equals("Масса"))
			return Constants.TypesOfTrain.MASS;
		return -1;
	}
	public static String getTypeOfTrain(int IntTypeOfTrain) {
		if (IntTypeOfTrain == Constants.TypesOfTrain.DRYING)
			return "Сушка";
		if (IntTypeOfTrain == Constants.TypesOfTrain.MASS)
			return "Масса";
		return "НИЧО";
	}
	public static String getTypeOfUser(int typeOfUser) {
		if (typeOfUser == Constants.TypesOfUsers.SPAM)
			return "Спамщик";
		if (typeOfUser == Constants.TypesOfUsers.CALL)
			return "Звонильщик";
		if (typeOfUser == Constants.TypesOfUsers.COURIER)
			return "Курьер";
		if (typeOfUser == Constants.TypesOfUsers.TRAINER)
			return "Тренер";
		return "";
	}
	public static int getTypeOfUser(String typeOfUser) {
		if (typeOfUser.equals("Спамщик"))
			return Constants.TypesOfUsers.SPAM;
		if (typeOfUser.equals("Звонильщик"))
			return Constants.TypesOfUsers.CALL;
		if (typeOfUser.equals("Курьер"))
			return Constants.TypesOfUsers.COURIER;
		if (typeOfUser.equals("Тренер"))
			return Constants.TypesOfUsers.TRAINER;
		return -1;
	}
	public static int getStatus(String Status) {
		if (Status.equals("Спам"))
			return Constants.TypesOfClient.SPAM;
		if (Status.equals("Звон"))
			return Constants.TypesOfClient.CALL;
		if (Status.equals("Доставка"))
			return Constants.TypesOfClient.DELIVERY;
		if (Status.equals("Пробная(А)"))
			return Constants.TypesOfClient.TRIAL_PASS;
		if (Status.equals("Пробная(П)"))
			return Constants.TypesOfClient.TRIAL_TRIAL;
		if (Status.equals("Пробная(Н)"))
			return Constants.TypesOfClient.TRIAL_NOTHING;
		if (Status.equals("Платная"))
			return Constants.TypesOfClient.PAY;
		if (Status.equals("Расписание"))
			return Constants.TypesOfClient.CLIENT;
		if (Status.equals("Слив"))
			return Constants.TypesOfClient.SINK;
		return -1;
	}
	public static String getStatus(int Status) {
		if (Status == Constants.TypesOfClient.SPAM)
			return "Спам";
		if (Status == Constants.TypesOfClient.CALL)
			return "Звон";
		if (Status == Constants.TypesOfClient.DELIVERY)
			return "Доставка";
		if (Status == Constants.TypesOfClient.DELIVERY_AND_TRIAL)
			return "Доставка/Пробная";
		if (Status == Constants.TypesOfClient.TRIAL_PASS)
			return "Пробная(А)";
		if (Status == Constants.TypesOfClient.TRIAL_TRIAL)
			return "Пробная(П)";
		if (Status == Constants.TypesOfClient.TRIAL_NOTHING)
			return "Пробная(Н)";
		if (Status == Constants.TypesOfClient.PAY)
			return "Платная";
		if (Status == Constants.TypesOfClient.CLIENT)
			return "Расписание";
		if (Status == Constants.TypesOfClient.SINK)
			return "Слив";
		return "";
	}
	public static int getTypeOfDate(String typeOfDate) {
		if (typeOfDate.equals("Звон"))
			return Constants.TypesOfDates.CALL;
		if (typeOfDate.equals("Доставка"))
			return Constants.TypesOfDates.DELIVERY;
		if (typeOfDate.equals("Пробная"))
			return Constants.TypesOfDates.TRIAL;
		if (typeOfDate.equals("Платная"))
			return Constants.TypesOfDates.PAY;
		if (typeOfDate.equals("Расписание"))
			return Constants.TypesOfDates.CLIENT;
		return -1;
	}
	public static String getTypeOfDate(int typeOfDate) {
		if (typeOfDate == Constants.TypesOfDates.CALL)
			return "Звон";
		if (typeOfDate == Constants.TypesOfDates.DELIVERY)
			return "Доствка";
		if (typeOfDate == Constants.TypesOfDates.TRIAL)
			return "Пробная";
		if (typeOfDate == Constants.TypesOfDates.PAY)
			return "Платная";
		if (typeOfDate == Constants.TypesOfDates.CLIENT)
			return "Расписание";
		return "";
	}
	public static String getTrialStatus(int Status) {
		if (Status == Constants.TypesOfClient.TRIAL_PASS)
			return "Абонемент";
		if (Status == Constants.TypesOfClient.TRIAL_TRIAL)
			return "Пробная";
		if (Status == Constants.TypesOfClient.TRIAL_NOTHING)
			return "Ничего";
		return "";
	}
	public static String getWhereMoney(int whereMoney) {
		if (whereMoney == Constants.WhereMoney.CASH)
			return "Деньги";
		if (whereMoney == Constants.WhereMoney.CASH_BOX)
			return "Касса";
		return "";
	}
	public static int getWhereMoney(String whereMoney) {
		if (whereMoney.equals("Деньги"))
			return Constants.WhereMoney.CASH;
		if (whereMoney.equals("Касса"))
			return Constants.WhereMoney.CASH_BOX;
		return -1;
	}
	public static int getClientInf(Client cl) {
		if (cl.isClient()) {
			Connection connect = null;
			try {
				connect = DriverManager.getConnection(Constants.URL,
						Constants.connInfo);
				String query = "SELECT * FROM `"
						+ Constants.NamesOfTables.CLIENTS + "` WHERE `id` = "
						+ cl.getId();
				ResultSet rs = SQL.doSQL(query, connect);
				rs.first();
				cl.setDebt(rs.getDouble("Debt"));
				cl.setTypeOfTrain(rs.getInt("TypeOfTrain"));
				cl.setCost(rs.getDouble("Cost"));
				cl.setNumberTrain(rs.getInt("NumberTrain"));
				cl.setCountOfTrain(rs.getInt("CountOfTrain"));
				cl.setNumberCV(rs.getInt("NumberCV"));
				cl.setLogo(rs.getInt("Logo"));
				cl.setPrePay(rs.getInt("PrePay"));
				cl.setPR(rs.getInt("PR"));
				cl.setOpinion(rs.getInt("Opinion"));
				cl.setDateCV(rs.getString("Date"));
				SQL.closeConnect(connect);
			} catch (SQLException e) {
				System.out.println("Проблема с БД. getClientInf");
				e.printStackTrace();
			}
			return 0;
		}
		return -1;
	}
	public static int getCenterSize(int frameSize, int componentSize) {
		return (frameSize - componentSize) / 2;
	}
	public static void setDateState(Connection connect, int id, int State) {
		SQL.doSQLWithoutResult("UPDATE `" + Constants.NamesOfTables.DATES
				+ "` SET `State` = " + State + "  WHERE `id` = " + id, connect);
	}
}
