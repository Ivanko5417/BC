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
		if (StringTypeOfTrain.equals("�����"))
			return Constants.TypesOfTrain.DRYING;
		if (StringTypeOfTrain.equals("�����"))
			return Constants.TypesOfTrain.MASS;
		return -1;
	}
	public static String getTypeOfTrain(int IntTypeOfTrain) {
		if (IntTypeOfTrain == Constants.TypesOfTrain.DRYING)
			return "�����";
		if (IntTypeOfTrain == Constants.TypesOfTrain.MASS)
			return "�����";
		return "����";
	}
	public static String getTypeOfUser(int typeOfUser) {
		if (typeOfUser == Constants.TypesOfUsers.SPAM)
			return "�������";
		if (typeOfUser == Constants.TypesOfUsers.CALL)
			return "����������";
		if (typeOfUser == Constants.TypesOfUsers.COURIER)
			return "������";
		if (typeOfUser == Constants.TypesOfUsers.TRAINER)
			return "������";
		return "";
	}
	public static int getTypeOfUser(String typeOfUser) {
		if (typeOfUser.equals("�������"))
			return Constants.TypesOfUsers.SPAM;
		if (typeOfUser.equals("����������"))
			return Constants.TypesOfUsers.CALL;
		if (typeOfUser.equals("������"))
			return Constants.TypesOfUsers.COURIER;
		if (typeOfUser.equals("������"))
			return Constants.TypesOfUsers.TRAINER;
		return -1;
	}
	public static int getStatus(String Status) {
		if (Status.equals("����"))
			return Constants.TypesOfClient.SPAM;
		if (Status.equals("����"))
			return Constants.TypesOfClient.CALL;
		if (Status.equals("��������"))
			return Constants.TypesOfClient.DELIVERY;
		if (Status.equals("�������(�)"))
			return Constants.TypesOfClient.TRIAL_PASS;
		if (Status.equals("�������(�)"))
			return Constants.TypesOfClient.TRIAL_TRIAL;
		if (Status.equals("�������(�)"))
			return Constants.TypesOfClient.TRIAL_NOTHING;
		if (Status.equals("�������"))
			return Constants.TypesOfClient.PAY;
		if (Status.equals("����������"))
			return Constants.TypesOfClient.CLIENT;
		if (Status.equals("����"))
			return Constants.TypesOfClient.SINK;
		return -1;
	}
	public static String getStatus(int Status) {
		if (Status == Constants.TypesOfClient.SPAM)
			return "����";
		if (Status == Constants.TypesOfClient.CALL)
			return "����";
		if (Status == Constants.TypesOfClient.DELIVERY)
			return "��������";
		if (Status == Constants.TypesOfClient.DELIVERY_AND_TRIAL)
			return "��������/�������";
		if (Status == Constants.TypesOfClient.TRIAL_PASS)
			return "�������(�)";
		if (Status == Constants.TypesOfClient.TRIAL_TRIAL)
			return "�������(�)";
		if (Status == Constants.TypesOfClient.TRIAL_NOTHING)
			return "�������(�)";
		if (Status == Constants.TypesOfClient.PAY)
			return "�������";
		if (Status == Constants.TypesOfClient.CLIENT)
			return "����������";
		if (Status == Constants.TypesOfClient.SINK)
			return "����";
		return "";
	}
	public static int getTypeOfDate(String typeOfDate) {
		if (typeOfDate.equals("����"))
			return Constants.TypesOfDates.CALL;
		if (typeOfDate.equals("��������"))
			return Constants.TypesOfDates.DELIVERY;
		if (typeOfDate.equals("�������"))
			return Constants.TypesOfDates.TRIAL;
		if (typeOfDate.equals("�������"))
			return Constants.TypesOfDates.PAY;
		if (typeOfDate.equals("����������"))
			return Constants.TypesOfDates.CLIENT;
		return -1;
	}
	public static String getTypeOfDate(int typeOfDate) {
		if (typeOfDate == Constants.TypesOfDates.CALL)
			return "����";
		if (typeOfDate == Constants.TypesOfDates.DELIVERY)
			return "�������";
		if (typeOfDate == Constants.TypesOfDates.TRIAL)
			return "�������";
		if (typeOfDate == Constants.TypesOfDates.PAY)
			return "�������";
		if (typeOfDate == Constants.TypesOfDates.CLIENT)
			return "����������";
		return "";
	}
	public static String getTrialStatus(int Status) {
		if (Status == Constants.TypesOfClient.TRIAL_PASS)
			return "���������";
		if (Status == Constants.TypesOfClient.TRIAL_TRIAL)
			return "�������";
		if (Status == Constants.TypesOfClient.TRIAL_NOTHING)
			return "������";
		return "";
	}
	public static String getWhereMoney(int whereMoney) {
		if (whereMoney == Constants.WhereMoney.CASH)
			return "������";
		if (whereMoney == Constants.WhereMoney.CASH_BOX)
			return "�����";
		return "";
	}
	public static int getWhereMoney(String whereMoney) {
		if (whereMoney.equals("������"))
			return Constants.WhereMoney.CASH;
		if (whereMoney.equals("�����"))
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
				System.out.println("�������� � ��. getClientInf");
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
