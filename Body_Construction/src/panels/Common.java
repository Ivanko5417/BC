package panels;
import static main.Constants.CALL;
import static main.Constants.PASSWORD;
import static main.Constants.SPAM;
import static main.Constants.TRAINER;
import static main.Constants.URL;
import static main.Constants.USER_NAME;
import static panels.Authorization.mainPanel;
import static panels.Common.clients;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.Client;
import main.Main;
import main.SQL;
import main.User;
import centerPanels.Call;
import centerPanels.Clients;
import centerPanels.FreeCall;
import centerPanels.Schedule;
import centerPanels.Settings;
import centerPanels.Sink;
import centerPanels.Start_Center_Bar;
import centerPanels.Trainer;
public class Common extends JPanel {
	static public Left_Bar_Spam leftPanelSpam;
	static public Left_Bar_Call leftPanelCall;
	static public Left_Bar_Trainer leftPanelTrainer;
	static public Start_Center_Bar centerPanel;
	static public Schedule schedulePanel;
	static public Trainer clientsTrainerPanel;
	static public Clients clinetsPanel;
	static public FreeCall callfreePanel;
	static public Call callPanel;
	static public Sink sinkPanel;
	static public Settings settingsPanel;
	static public Client[] clients = null;
	static void setPanels(JPanel leftPanel, JPanel centerPanel) {
		mainPanel.removeAll();
		mainPanel.add(leftPanel, BorderLayout.WEST);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.revalidate();
		mainPanel.repaint();
	}
	static void setCommonPanels(JPanel centerPanel)
	{
		switch (User.Type) {
			case 0 :
				setPanels(leftPanelSpam, centerPanel);
				break;
			case 1 :
				setPanels(leftPanelCall, centerPanel);
				break;
			case 3 :
				setPanels(leftPanelTrainer, centerPanel);
				break;
		}
	}
	static void setSchedule() {
		setPanels(leftPanelTrainer, schedulePanel);
	}
	static void setClients() {
		setPanels(leftPanelSpam, clinetsPanel);
	}
	static void setFreeCall() {
		setPanels(leftPanelCall, callfreePanel);
	}
	static void setCall() {
		setPanels(leftPanelCall, callPanel);
	}
	static void setTrainer() {
		setPanels(leftPanelTrainer, clientsTrainerPanel);
	}
	static void setSettings()
	{
		setCommonPanels(settingsPanel);
	}
	static void setSink() {
		setCommonPanels(sinkPanel);
	}
	static public String getDate(Date d) {
		return "" + (2000 + d.getYear() % 100) + "." + (d.getMonth() + 1) + "."
				+ d.getDate();
	}
	static public String getDateTime(Date d) {
		return "" + (2000 + d.getYear() % 100) + "." + (d.getMonth() + 1) + "."
				+ d.getDate() + " " + d.getHours() + ":" + d.getMinutes()
				+ ":00";
	}
	static public String getTime(String s) {
		return s.substring(11, 16);
	}
	static public String getDateBase(Date d) {
		String m, s;
		if (d.getMonth() + 1 >= 10)
			m = "" + (d.getMonth() + 1);
		else
			m = "0" + (d.getMonth() + 1);
		if (d.getDate() >= 10)
			s = "" + (d.getDate());
		else
			s = "0" + (d.getDate());
		return "" + (2000 + d.getYear() % 100) + "-" + m + "-" + s;
	}
	static public void Sink(Client c) {
		int id = c.getId();
		Connection connect = null;
		try {
			connect = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
		} catch (SQLException e) {
			System.out.println("Проблема с БД. Call.java, Слив");
		}
		String comment = JOptionPane.showInputDialog(null,
				"Введите причину слива");
		if (comment != null) {
			String query = "UPDATE `clients` SET `Status` = 10, `Сomment` = '"
					+ comment + "' WHERE `id` = " + id + "";
			SQL.doSQLWithoutResult(query, connect);
			SQL.closeConnect(connect);
		}
	}
	public Common(int type) {
		setLayout(new BorderLayout());
		leftPanelSpam = new Left_Bar_Spam();
		leftPanelCall = new Left_Bar_Call();
		leftPanelTrainer = new Left_Bar_Trainer();
		centerPanel = new Start_Center_Bar();
		schedulePanel = new Schedule();
		clinetsPanel = new Clients();
		callfreePanel = new FreeCall();
		callPanel = new Call();
		clientsTrainerPanel = new Trainer();
		settingsPanel = new Settings();
		sinkPanel = new Sink();
		switch (type) {
			case SPAM :
				add(leftPanelSpam, BorderLayout.WEST);
				break;
			case CALL :
				add(leftPanelCall, BorderLayout.WEST);
				break;
			case TRAINER :
				add(leftPanelTrainer, BorderLayout.WEST);
			default :
				break;
		}
		add(centerPanel, BorderLayout.CENTER);
	}
}
