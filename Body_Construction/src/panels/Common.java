package panels;
import static main.Constants.PASSWORD;
import static main.Constants.URL;
import static main.Constants.USER_NAME;
import static panels.Authorization.mainPanel;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import centerPanels.AdminClients;
import centerPanels.Call;
import centerPanels.Clients;
import centerPanels.FreeCall;
import centerPanels.Main_Center_Panel;
import centerPanels.Schedule;
import centerPanels.ScheduleWeek;
import centerPanels.Settings;
import centerPanels.Sink;
import centerPanels.Start_Center_Bar;
import centerPanels.Trainer;
import centerPanels.Users;
import frames.FindNumber;
import frames.addClient;
import main.Client;
import main.Constants;
import main.Constants.TypesOfUsers;
import main.Functions;
import main.SQL;
import main.User;
public class Common extends JPanel {
	static public Left_Bar_Spam leftPanelSpam;
	static public Left_Bar_Call leftPanelCall;
	static public Left_Bar_Trainer leftPanelTrainer;
	static public Left_Bar_Courier leftPanelCourier;
	static public Left_Bar_Admin leftPanelAdmin;
	static public Start_Center_Bar centerPanel;
	static public Schedule schedulePanel;
	static public ScheduleWeek scheduleWeekPanel;
	static public Trainer clientsTrainerPanel;
	static public Clients clinetsPanel;
	static public FreeCall callfreePanel;
	static public Call callPanel;
	static public FindNumber findNumberPanel;
	static public Sink sinkPanel;
	static public AdminClients adminClientsPanel;
	static public Users usersPanel;
	static public Settings settingsPanel;
	static public addClient addCl;
	static public Client currectNumber = null;
	static public ArrayList<Client> clients = new ArrayList<Client>();
	static void setPanels(JPanel leftPanel, JPanel centerPanel) {
		schedulePanel.setEnable(false);
		new Thread() {
			@Override
			public void run() {
				super.run();
				mainPanel.removeAll();
				mainPanel.add(leftPanel, BorderLayout.WEST);
				mainPanel.add(centerPanel, BorderLayout.CENTER);
				mainPanel.revalidate();
				centerPanel.repaint();
			}
		}.start();
	}
	static void setCommonPanels(JPanel centerPanel) {
		switch (User.CurrentType) {
			case Constants.TypesOfUsers.SPAM :
				setPanels(leftPanelSpam, centerPanel);
				break;
			case Constants.TypesOfUsers.CALL :
				setPanels(leftPanelCall, centerPanel);
				break;
			case Constants.TypesOfUsers.COURIER :
				setPanels(leftPanelCourier, centerPanel);
				break;
			case Constants.TypesOfUsers.TRAINER :
				setPanels(leftPanelTrainer, centerPanel);
				break;
			case Constants.TypesOfUsers.ADMIN :
				setPanels(leftPanelAdmin, centerPanel);
				break;
		}
	}
	static void setSchedule() {
		new Thread() {
			@Override
			public void run() {
				super.run();
				switch (User.CurrentType) {
					case Constants.TypesOfUsers.CALL :
						setPanels(leftPanelCall, schedulePanel);
						break;
					case Constants.TypesOfUsers.TRAINER :
						setPanels(leftPanelTrainer, schedulePanel);
						break;
					case Constants.TypesOfUsers.COURIER :
						setPanels(leftPanelCourier, schedulePanel);
				}
				schedulePanel.setEnable(true);
				schedulePanel.refreshTable();
			}
		}.start();
	}
	static void setScheduleWeek() {
		new Thread() {
			@Override
			public void run() {
				super.run();
				switch (User.CurrentType) {
					case Constants.TypesOfUsers.ADMIN :
						setPanels(leftPanelAdmin, scheduleWeekPanel);
						scheduleWeekPanel.refreshTable();
						break;
					default :
						setPanels(leftPanelTrainer, scheduleWeekPanel);
						scheduleWeekPanel.refreshTable();
						break;
				}
			}
		}.start();
	}
	static void setClients() {
		new Thread() {
			@Override
			public void run() {
				super.run();
				setPanels(leftPanelSpam, clinetsPanel);
				clinetsPanel.refreshTable();
			}
		}.start();
	}
	static void setFreeCall() {
		new Thread() {
			@Override
			public void run() {
				super.run();
				setPanels(leftPanelCall, callfreePanel);
				callfreePanel.refreshTable();
			}
		}.start();
	}
	static void setCall() {
		new Thread() {
			@Override
			public void run() {
				super.run();
				setPanels(leftPanelCall, callPanel);
				callPanel.refreshTable();
			}
		}.start();
	}
	static void setTrainer() {
		new Thread() {
			@Override
			public void run() {
				super.run();
				setPanels(leftPanelTrainer, clientsTrainerPanel);
				clientsTrainerPanel.refreshTable();
			}
		}.start();
	}
	static void setAdminClients() {
		new Thread() {
			@Override
			public void run() {
				super.run();
				setPanels(leftPanelAdmin, adminClientsPanel);
				adminClientsPanel.refreshTable();
			}
		}.start();
	}
	static void setUsers() {
		new Thread() {
			@Override
			public void run() {
				super.run();
				setPanels(leftPanelAdmin, usersPanel);
				usersPanel.refreshTable();
			}
		}.start();
	}
	static void setSettings() {
		setCommonPanels(settingsPanel);
	}
	static void setSink() {
		new Thread() {
			@Override
			public void run() {
				super.run();
				setCommonPanels(sinkPanel);
				sinkPanel.refreshTable();
			}
		}.start();
	}
	static void FindNumber() {
		findNumberPanel.setVisible(true);
		
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
	static public void Sink(Client c, Main_Center_Panel m) {
		new Thread() {
			@Override
			public void run() {
				super.run();
				int id = c.getId();
				Connection connect = null;
				try {
					connect = DriverManager.getConnection(URL, USER_NAME,
							PASSWORD);
				} catch (SQLException e) {
					System.out.println("Проблема с БД. Common.java, Слив");
				}
				Functions.setDateState(connect, c.getLastDateId(),
						Constants.StatesOfDates.UNSUCCESSFUL);
				String comment = JOptionPane.showInputDialog(null,
						"Введите причину слива");
				if (comment != null) {
					String query = "UPDATE `" + Constants.NamesOfTables.NUMBERS
							+ "` SET `Status` = " + Constants.TypesOfClient.SINK
							+ ", `Comment` = '" + comment + "' WHERE `id` = "
							+ id + "";
					SQL.doSQLWithoutResult(query, connect);
					SQL.closeConnect(connect);
				}
				m.clearSelection();
				m.refreshTable();
			}
		}.start();
	}
	public Common(int type) {
		setLayout(new BorderLayout());
		leftPanelSpam = new Left_Bar_Spam();
		findNumberPanel = new FindNumber();
		leftPanelCall = new Left_Bar_Call();
		leftPanelTrainer = new Left_Bar_Trainer();
		leftPanelCourier = new Left_Bar_Courier();
		leftPanelAdmin = new Left_Bar_Admin();
		centerPanel = new Start_Center_Bar();
		clinetsPanel = new Clients();
		callfreePanel = new FreeCall();
		adminClientsPanel = new AdminClients();
		usersPanel = new Users();
		callPanel = new Call();
		schedulePanel = new Schedule();
		scheduleWeekPanel = new ScheduleWeek();
		clientsTrainerPanel = new Trainer();
		settingsPanel = new Settings();
		sinkPanel = new Sink();
		addCl = new addClient();
		switch (type) {
			case TypesOfUsers.SPAM :
				add(leftPanelSpam, BorderLayout.WEST);
				break;
			case TypesOfUsers.CALL :
				add(leftPanelCall, BorderLayout.WEST);
				break;
			case TypesOfUsers.COURIER :
				add(leftPanelCourier, BorderLayout.WEST);
				break;
			case TypesOfUsers.TRAINER :
				add(leftPanelTrainer, BorderLayout.WEST);
				break;
			case TypesOfUsers.ADMIN :
				add(leftPanelAdmin, BorderLayout.WEST);
				break;
			default :
				break;
		}
		add(centerPanel, BorderLayout.CENTER);
	}
}
