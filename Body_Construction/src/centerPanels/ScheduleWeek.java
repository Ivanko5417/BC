package centerPanels;
import static main.Constants.URL;
import static main.Constants.connInfo;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.Client;
import main.Constants;
import main.Functions;
import main.SQL;
import main.User;
import panels.Common;
public class ScheduleWeek extends Main_Center_Panel {
	private ArrayList<Client> clients = new ArrayList<Client>();
	private ArrayList<String> trainers = new ArrayList<String>();
	private Connection connect1, connect2;
	private Calendar firstDayOfWeek = Calendar.getInstance();
	private Calendar lastDayOfWeek = Calendar.getInstance();
	private final int startDay = 7, endDay = 22;
	private int sizeOfWeek = 7;
	public JPanel headerPanel = new JPanel();
	private String parseTime(int i, int j) {
		String s = "";
		if (i < 10)
			s = "0" + i;
		else
			s += i;
		s += ":";
		if (j < 10)
			s += "0" + j;
		else
			s += j;
		return s;
	}
	@Override
	public void refreshTable() {
		int column = table.getSelectedColumn(), row = table.getSelectedRow();
		mod.getDataVector().clear();
		clients.clear();
		try {
			connect1 = DriverManager.getConnection(URL, connInfo);
			connect2 = DriverManager.getConnection(URL, connInfo);
			String startQuery = "SELECT * FROM `"
					+ Constants.NamesOfTables.NUMBERS + "` WHERE `Trainer`='",
					endQuery = "' AND (`Status`= "
							+ Constants.TypesOfClient.TRIAL_PASS
							+ " OR `Status`= "
							+ Constants.TypesOfClient.TRIAL_TRIAL
							+ " OR `Status`= "
							+ Constants.TypesOfClient.TRIAL_NOTHING
							+ " OR `Status` = " + Constants.TypesOfClient.PAY
							+ " OR `Status` = " + Constants.TypesOfClient.CLIENT
							+ ")";
			String query, queryDates;
			ResultSet rsClients;
			int i = 0;
			switch (User.CurrentType) {
				case Constants.TypesOfUsers.ADMIN :
					for (int j = 0; j < trainers.size(); j++) {
						query = startQuery + trainers.get(j) + endQuery;
						rsClients = SQL.doSQL(query, connect1);
						if (rsClients.first())
							do {
								queryDates = "SELECT * FROM `"
										+ Constants.NamesOfTables.DATES
										+ "` WHERE `Client_id`="
										+ rsClients.getInt("id") + " AND `Trainer` = '"+trainers.get(j)+"' AND DATE(`Date`) = '"+Common.getDateBase(firstDayOfWeek.getTime())+"' ORDER BY `Date`";
								ResultSet rsDates = SQL.doSQL(queryDates, connect2);
								if (rsDates.last()) {
										Client cl = new Client();
										cl.setLastDate(Common.getTime(rsDates.getString("Date")),
												rsDates.getInt("id"));
										cl.setId(rsClients.getInt("id"));
										cl.setTrainer(
												rsClients.getString("Trainer"));
										cl.setGym(rsClients.getString("Gym"));
										cl.setAccountSpam(
												rsClients.getString("Vk_Spam"));
										cl.setName(
												rsClients.getString("Name_Client"));
										cl.setNumber(rsClients.getString("Number"));
										cl.setAccountClient(
												rsClients.getString("Vk_Client"));
										cl.setDate(rsClients.getString("Date"));
										cl.setAddress(
												rsClients.getString("Address"));
										cl.setComment(
												rsClients.getString("Comment"));
										cl.setStatus(rsClients.getInt("Status"));
										cl.setNumberDates(rsDates.getInt("Number"));
										cl.setLastDateId(rsDates.getInt("id"));
										cl.setClient(
												rsClients.getBoolean("IsClient"));
										Functions.getClientInf(cl);
										clients.add(cl);
										i++;
									}
							} while (rsClients.next());
					}
					break;
				default :
					query = startQuery + User.CurrentUser0 + endQuery;
					rsClients = SQL.doSQL(query, connect1);
					if (rsClients.first())
						do {
							queryDates = "SELECT * FROM `"
									+ Constants.NamesOfTables.DATES
									+ "` WHERE `Client_id`=" + rsClients.getInt("id")
									+ " AND `Trainer` = '"+User.CurrentUser0+"' ORDER BY `Date`";
							ResultSet rsDates = SQL.doSQL(queryDates, connect2);
							if (rsDates.last()) {
								String dateString0 = rsDates.getString("Date");
								String dateString1 = dateString0.substring(0,
										10);
								if (dateString1
										.compareTo(Common.getDateBase(
												firstDayOfWeek.getTime())) >= 0
										&& dateString1.compareTo(
												Common.getDateBase(lastDayOfWeek
														.getTime())) <= 0) {// ПАРСЕР
									// ДЛЯ
									// ПРОВЕРКИ
									// ДАТЫ
									// В
									// ТЕЧЕНИИ
									// НЕДЕЛИ
									Client cl = new Client();
									cl.setLastDate(Common.getTime(dateString0),
											rsDates.getInt("id"));
									cl.setDay(Integer.parseInt(
											dateString0.substring(8, 10)));
									cl.setId(rsClients.getInt("id"));
									cl.setTrainer(
											rsClients.getString("Trainer"));
									cl.setGym(rsClients.getString("Gym"));
									cl.setAccountSpam(
											rsClients.getString("Vk_Spam"));
									cl.setName(
											rsClients.getString("Name_Client"));
									cl.setNumber(rsClients.getString("Number"));
									cl.setAccountClient(
											rsClients.getString("Vk_Client"));
									cl.setDate(rsClients.getString("Date"));
									cl.setAddress(
											rsClients.getString("Address"));
									cl.setComment(
											rsClients.getString("Comment"));
									cl.setStatus(rsClients.getInt("Status"));
									cl.setNumberDates(rsDates.getInt("Number"));
									cl.setLastDateId(rsDates.getInt("id"));
									cl.setClient(
											rsClients.getBoolean("IsClient"));
									Functions.getClientInf(cl);
									clients.add(cl);
									i++;
								}
							}
						} while (rsClients.next());
					break;
			}
			Collections.sort(clients);
			for (i = startDay; i <= endDay; i++) {
				for (int j = 0; j <= 30; j += 30) {
					Vector<String> newRow = new Vector<String>();
					String s = parseTime(i, j);
					s += "time";
					newRow.add(s);
					s = s.substring(0, s.length() - 4);
					for (int x = 0; x < clients.size(); x++) {
						String nextTime = "";
						if (j == 0)
							nextTime = parseTime(i, j + 30);
						else if (i + 1 <= endDay)
							nextTime = parseTime(i + 1, j - 30);
						if (clients.get(x).getLastDate().compareTo(s) >= 0
								&& clients.get(x).getLastDate()
										.compareTo(nextTime) < 0) {
							switch (User.CurrentType) {
								case Constants.TypesOfUsers.ADMIN :
									int q = 0;
									while(!clients.get(x).getTrainer().equals(trainers.get(q)))
									{
										newRow.add("");
										q++;
									}
									break;
								default :

									for ( q = firstDayOfWeek
											.get(Calendar.DAY_OF_MONTH); q < clients
													.get(x).getDay(); q++)
										newRow.add("");
									break;
							}
							String client = clients.get(x).getName() + " "
									+ clients.get(x).getNumber(); // добавить
																	// номер
																	// занятия
																	// для тех
																	// кто по
																	// расписанию
																	// (5/8),
																	// есть ли у
																	// него долг
							if (clients.get(x)
									.getStatus() == Constants.TypesOfClient.TRIAL_PASS
									|| clients.get(x)
											.getStatus() == Constants.TypesOfClient.TRIAL_TRIAL
									|| clients.get(x)
											.getStatus() == Constants.TypesOfClient.TRIAL_NOTHING) // для
								// пробной:
								// что
								// куплено?(Ничего,
								// разовая,
								// абонемент)
								client += "| "
										+ Functions.getTrialStatus(
												clients.get(x).getStatus())
										+ "first";
							if (clients.get(x)
									.getStatus() == Constants.TypesOfClient.PAY)
								client += "| " + clients.get(x).getNumberTrain()
										+ "/" + clients.get(x).getCountOfTrain()
										+ "| Долг: " + clients.get(x).getDebt()
										+ "‪money";
							if (clients.get(x)
									.getStatus() == Constants.TypesOfClient.CLIENT)
								client += "| " + clients.get(x).getNumberTrain()
										+ "/" + clients.get(x).getCountOfTrain()
										+ "| Долг: " + clients.get(x).getDebt()
										+ "‪client";
							newRow.add(client);
							break;
						}
					}
					mod.addRow(newRow);
				}
			}
		} catch (SQLException e) {
			System.out.println("Проблема с БД. Schedule.java");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		if (column > -1 && row > -1) {
			table.setColumnSelectionInterval(column, column);
			table.setRowSelectionInterval(row, row);
		}
		super.refreshTable();
	}
	private void setDaysOfWeek() {
		switch (User.CurrentType) {
			case Constants.TypesOfUsers.ADMIN :
				setSizeOfWeek(1);
				break;
			default :
				setSizeOfWeek(7);
				break;
		}
	}
	public ScheduleWeek() {
		setDaysOfWeek();
		firstDayOfWeek.set(Calendar.DAY_OF_WEEK,
				firstDayOfWeek.getFirstDayOfWeek());
		lastDayOfWeek.set(Calendar.DAY_OF_WEEK,
				lastDayOfWeek.getFirstDayOfWeek() + getSizeOfWeek() - 1);
		headerPanel.setLayout(new BorderLayout());
		JButton btnBackDay = new JButton("Назад");
		JLabel lblDay = new JLabel(
				firstDayOfWeek.getTime() + "-" + lastDayOfWeek.getTime());
		btnBackDay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				firstDayOfWeek.set(Calendar.DAY_OF_MONTH,
						firstDayOfWeek.get(Calendar.DAY_OF_MONTH)
								- getSizeOfWeek());
				lastDayOfWeek.set(Calendar.DAY_OF_MONTH,
						lastDayOfWeek.get(Calendar.DAY_OF_MONTH)
								- getSizeOfWeek());
				lblDay.setText(firstDayOfWeek.getTime() + "-"
						+ lastDayOfWeek.getTime());
				new Thread() {
					public void run() {
						table.clearSelection();
						refreshTable();
					};
				}.start();
			}
		});
		JButton btnNextDay = new JButton("Вперед");
		btnNextDay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				firstDayOfWeek.set(Calendar.DAY_OF_MONTH,
						firstDayOfWeek.get(Calendar.DAY_OF_MONTH)
								+ getSizeOfWeek());
				lastDayOfWeek.set(Calendar.DAY_OF_MONTH,
						lastDayOfWeek.get(Calendar.DAY_OF_MONTH)
								+ getSizeOfWeek());
				lblDay.setText(firstDayOfWeek.getTime() + "-"
						+ lastDayOfWeek.getTime());
				new Thread() {
					public void run() {
						table.clearSelection();
						refreshTable();
					};
				}.start();
			}
		});
		lblDay.setHorizontalAlignment(SwingConstants.CENTER);
		lblDay.setFont(Constants.GENERAL_LABEL_FONT);
		headerPanel.add(btnBackDay, BorderLayout.LINE_START);
		headerPanel.add(lblDay, BorderLayout.CENTER);
		headerPanel.add(btnNextDay, BorderLayout.LINE_END);
		add(headerPanel, BorderLayout.NORTH);
		headerVect.clear();
		headerVect.add("Время");
		switch (User.CurrentType) {
			case Constants.TypesOfUsers.TRAINER :
				headerVect.add("ПН");
				headerVect.add("ВТ");
				headerVect.add("СР");
				headerVect.add("ЧТ");
				headerVect.add("ПТ");
				headerVect.add("СБ");
				headerVect.add("ВС");
				break;
			case Constants.TypesOfUsers.ADMIN :
				try {
					connect1 = DriverManager.getConnection(URL, connInfo);
					ResultSet rs = SQL.doSQL(
							"SELECT * FROM `" + Constants.NamesOfTables.USERS
									+ "` WHERE `Type` = "
									+ Constants.TypesOfUsers.TRAINER + " ORDER BY `Gym`",
							connect1);
					while (rs.next()) {
						trainers.add(rs.getString("Name0"));
						headerVect.add(trainers.get(trainers.size() - 1) + "(" + rs.getString("Gym") + ")");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
		}
		revalidate(headerVect);
	}
	public int getSizeOfWeek() {
		return sizeOfWeek;
	}
	public void setSizeOfWeek(int sizeOfWeek) {
		this.sizeOfWeek = sizeOfWeek;
	}
}
