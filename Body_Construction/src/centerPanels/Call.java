package centerPanels;
import static main.Constants.URL;
import static panels.Common.clients;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import main.Client;
import main.Constants;
import main.DateTimePicker;
import main.Functions;
import main.SQL;
import main.User;
import panels.Common;
import table.MainTableModel;
import table.ColorRenderer;
public class Call extends Main_Center_Panel {
	private JFrame frameAdd = new JFrame("Добавить клиента");
	private JPanel panelAdd = new JPanel();
	private JFrame frameNextCall = new JFrame("Новый звонок");
	private JPanel panelNextCall = new JPanel();
	private JFrame frameAddTrainer = new JFrame("Тренер");
	private JPanel panelAddTrainer = new JPanel();
	private JFrame frameAddDelivery = new JFrame("Доставка");
	private JPanel panelAddDelivery = new JPanel();
	private JButton btnOk = new JButton("OK");
	private Connection connect1 = null;
	private Connection connect2 = null;
	private int x = 20, y = 20;
	private int length = 0;
	public void addCall() {
		frameAdd.setVisible(true);
	}
	private void initAddCall() {
		frameAdd.setSize(400, 400);
		panelAdd.setLayout(null);
		JLabel lblFrom = new JLabel("Источник");
		lblFrom.setLocation(10, 10);
		lblFrom.setSize(200, 30);
		panelAdd.add(lblFrom);
		String[] sFrome = {"Instagram", "Body24.com", "Facebook", "VK"};
		JComboBox jComboBox = new JComboBox(sFrome);
		JComboBox cmFrom = jComboBox;
		cmFrom.setLocation(10, 40);
		cmFrom.setSize(200, 20);
		panelAdd.add(cmFrom);
		JLabel lblNumber = new JLabel("Номер телефона");
		lblNumber.setLocation(10, 60);
		lblNumber.setSize(200, 30);
		panelAdd.add(lblNumber);
		JFormattedTextField txtNumber = new JFormattedTextField(Constants.NUMBER_MASK);
		txtNumber.setLocation(10, 90);
		txtNumber.setSize(200, 20);
		panelAdd.add(txtNumber);
		JLabel lblName = new JLabel("Имя");
		lblName.setLocation(10, 110);
		lblName.setSize(200, 30);
		panelAdd.add(lblName);
		JTextField txtName = new JTextField();
		txtName.setLocation(10, 140);
		txtName.setSize(200, 20);
		panelAdd.add(txtName);
		JLabel lblDate = new JLabel("Дата первого звонка");
		lblDate.setLocation(10, 160);
		lblDate.setSize(200, 30);
		panelAdd.add(lblDate);
		DateTimePicker dateTimePicker = new DateTimePicker();
		dateTimePicker.setFormats(DateFormat
				.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM));
		dateTimePicker
				.setTimeFormat(DateFormat.getTimeInstance(DateFormat.MEDIUM));
		dateTimePicker.setDate(Calendar.getInstance().getTime());
		dateTimePicker.setBounds(10, 190, 200, 20);
		panelAdd.add(dateTimePicker);
		JLabel lblComment = new JLabel("Комментарий");
		lblComment.setLocation(10, 210);
		lblComment.setSize(200, 30);
		panelAdd.add(lblComment);
		JTextArea txtComment = new JTextArea();
		txtComment.setLineWrap(true);
		txtComment.setWrapStyleWord(true);
		txtComment.setBorder(txtName.getBorder());
		JScrollPane scrollComment = new JScrollPane(txtComment);
		scrollComment.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollComment.setLocation(10, 240);
		scrollComment.setSize(200, 40);
		panelAdd.add(scrollComment);
		JButton btnOk = new JButton("ОК");
		btnOk.setLocation(10, 280);
		btnOk.setSize(50, 20);
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Thread() {
					public void run() {
						frameAdd.setVisible(false);
						try {
							Date date = Calendar.getInstance().getTime();
							Connection connect = null;
							connect = DriverManager.getConnection(URL,
									main.Constants.connInfo);
							String query = "INSERT INTO `"
									+ Constants.NamesOfTables.NUMBERS
									+ "` (`From`, `Name_Client`, `Number`,`Call`, `Date`, `Comment`, `Status`)"
									+ " values('" + cmFrom.getSelectedItem()
									+ "'," + "'" + txtName.getText() + "', '"
									+ txtNumber.getText() + "','"
									+ User.CurrentUser0 + "', " + " '20"
									+ (date.getYear() % 100) + "."
									+ (date.getMonth() + 1) + "."
									+ date.getDate() + "', " + "'"
									+ txtComment.getText() + "', '"
									+ Constants.TypesOfClient.CALL + "')";
							SQL.doSQLWithoutResult(query, connect);
							query = "SELECT * FROM `"
									+ Constants.NamesOfTables.NUMBERS
									+ "` WHERE Number='" + txtNumber.getText()
									+ "';";
							ResultSet rs = SQL.doSQL(query, connect);
							int id = -1;
							while (rs.next()) {
								id = rs.getInt("id");
							}
							query = "INSERT INTO `"
									+ Constants.NamesOfTables.DATES
									+ "` (`Client_id`, `Date`,`Type` ) "
									+ "values(" + id + ",'"
									+ Common.getDateTime(dateTimePicker.getDate()) + "' ,"+Constants.TypesOfDates.CALL+")";
							SQL.doSQLWithoutResult(query, connect);
							SQL.closeConnect(connect);
							refreshTable();
						} catch (Exception e) {
							System.out.println(
									"Проблема с добавлением записи в БД.\n"
											+ e.getMessage());
						}
					};
				}.start();
			}
		});
		panelAdd.add(btnOk);
		frameAdd.add(panelAdd);
	}
	private void initNextCall() {
		frameNextCall.setSize(162, 150);
		frameNextCall.setResizable(false);
		panelNextCall.setLayout(null);
		JLabel lblDateCall = new JLabel("Дата");
		lblDateCall.setBounds(20, 5, 100, 30);
		lblDateCall.setFont(Constants.GENERAL_LABEL_FONT);
		DateTimePicker dateTimePicker = new DateTimePicker();
		dateTimePicker.setFormats(DateFormat
				.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM));
		dateTimePicker
				.setTimeFormat(DateFormat.getTimeInstance(DateFormat.MEDIUM));
		dateTimePicker.setDate(Calendar.getInstance().getTime());
		dateTimePicker.setBounds(20, 35, 115, 26);
		JButton btnOk = new JButton("ОК");
		btnOk.setBounds(40, 65, 75, 30);
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread() {
					public void run() {
						Common.currectNumber.setNumberDates(
								Common.currectNumber.getNumberDates() + 1);
						Connection connect = null;
						try {
							connect = DriverManager.getConnection(URL,
									main.Constants.connInfo);
						} catch (SQLException e1) {
							System.out.println(
									"Проблема с БД. Call.Java, nextCall");
						}
						int id = Common.currectNumber.getId();

						Functions.setDateState(connect, Common.currectNumber.getLastDateId(), Constants.StatesOfDates.SHIFT);
						String dateQuery = "INSERT INTO `"
								+ Constants.NamesOfTables.DATES
								+ "` (`Client_id`, `Call`,`Type`,`Number`, `Date`, `State`)"
								+ "VALUES (" + id + ", '" + User.CurrentUser0
								+ "', " + "0" + ", "
								+ Common.currectNumber.getNumberDates() + ", '"
								+ Common.getDateTime(dateTimePicker.getDate())
								+ "', '" + Constants.TypesOfDates.CALL + "') ";
						SQL.doSQLWithoutResult(dateQuery, connect);
						SQL.closeConnect(connect);
						frameNextCall.setVisible(false);
						clearSelection();
						refreshTable();
						Common.schedulePanel.clearSelection();
						Common.schedulePanel.refreshTable();
					};
				}.start();
			}
		});
		panelNextCall.add(lblDateCall);
		panelNextCall.add(dateTimePicker);
		panelNextCall.add(btnOk);
		frameNextCall.add(panelNextCall);
	}
	
	private void initAddTrainer() {
		frameAddTrainer.setSize(600, 240);
		panelAddTrainer.setLayout(null);
		length = 0;
		Connection connect = null;
		ArrayList<String> gyms = new ArrayList<String>();
		ArrayList<String> couriers = new ArrayList<String>();
		String[][] trainers = new String[50][2];
		try {
			connect = DriverManager.getConnection(URL, main.Constants.connInfo);
			String query = "SELECT * FROM `users` WHERE `Type` = "
					+ Constants.TypesOfUsers.TRAINER + "";
			ResultSet rs = SQL.doSQL(query, connect);
			while (rs.next()) {
				String s = rs.getString("Gym");
				String s1 = rs.getString("Name0");
				trainers[length][0] = s1;
				trainers[length][1] = s;
				length++;
				if (gyms.indexOf(s) == -1)
					gyms.add(s);
			}
			query = "SELECT Name0 FROM `users` WHERE `Type` = "
					+ Constants.TypesOfUsers.COURIER + "";
			rs = SQL.doSQL(query, connect);
			while (rs.next()) {
				couriers.add(rs.getString("Name0"));
			}
			SQL.closeConnect(connect);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		JLabel lblGym = new JLabel("Зал");
		lblGym.setBounds(x, y, 100, 30);
		lblGym.setFont(Constants.GENERAL_LABEL_FONT);
		JComboBox cmGym = new JComboBox(gyms.toArray());
		JComboBox cmTrainer = new JComboBox();
		ActionListener l = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cmTrainer.removeAllItems();
				for (int i = 0; i < length; i++)
					if (trainers[i][1]
							.equals(gyms.get(cmGym.getSelectedIndex()))) {
						cmTrainer.addItem(trainers[i][0]);
					}
			}
		};
		l.actionPerformed(null);
		cmGym.setBounds(x, y + 30, 115, 26);
		cmGym.addActionListener(l);
		JLabel lblTrainer = new JLabel("Тренер");
		lblTrainer.setBounds(lblGym.getX() + 130, y, 100, 30);
		lblTrainer.setFont(Constants.GENERAL_LABEL_FONT);
		cmTrainer.setBounds(cmGym.getX() + 130, y + 30, 115, 26);
		JLabel lblDate = new JLabel("Дата");
		lblDate.setBounds(x + 15, y + 70, 100, 30);
		lblDate.setFont(Constants.GENERAL_LABEL_FONT);
		DateTimePicker dateTimePicker = new DateTimePicker();
		dateTimePicker.setFormats(DateFormat
				.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM));
		dateTimePicker
				.setTimeFormat(DateFormat.getTimeInstance(DateFormat.MEDIUM));
		dateTimePicker.setDate(Calendar.getInstance().getTime());
		dateTimePicker.setBounds(x + 15, lblDate.getY() + 30, 215, 26);
		JLabel lblAddress = new JLabel("Адресс");
		lblAddress.setFont(Constants.GENERAL_LABEL_FONT);
		lblAddress.setBounds(lblGym.getX() + 265, y, 100, 30);
		JTextField txtAddress = new JTextField();
		txtAddress.setBounds(cmGym.getX() + 265, y + 30, 115, 26);
		JLabel lblCourier = new JLabel("Курьер");
		lblCourier.setFont(Constants.GENERAL_LABEL_FONT);
		lblCourier.setBounds(lblTrainer.getX() + 265, y, 115, 26);
		JComboBox cmCourier = new JComboBox<>(couriers.toArray());
		cmCourier.setBounds(cmTrainer.getX() + 265, y + 30, 115, 26);
		JLabel lblDateDelivery = new JLabel("Доставка");
		lblDateDelivery.setFont(Constants.GENERAL_LABEL_FONT);
		lblDateDelivery.setBounds(lblDate.getX() + 265, y + 70, 100, 30);
		DateTimePicker dateTimePickerCourier = new DateTimePicker();

		dateTimePickerCourier.setFormats(DateFormat
				.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM));
		dateTimePickerCourier
				.setTimeFormat(DateFormat.getTimeInstance(DateFormat.MEDIUM));
		dateTimePickerCourier.setDate(Calendar.getInstance().getTime());
		dateTimePickerCourier.setBounds(dateTimePicker.getX() + 265, y + 100,
				215, 26);
		btnOk.setBounds(262, 150, 75, 30);
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Thread() {
					public void run() {
						frameAddTrainer.setVisible(false);
						int id = Common.currectNumber.getId();
						Connection connect = null;
						try {
							connect = DriverManager.getConnection(URL,
									main.Constants.connInfo);
						} catch (SQLException e) {
							System.out
									.println("Проблема БД. Call.java, Тренер");
							System.out.println(e.getMessage());
						}
						String query = "";
						/*if (frameAddTrainer.getWidth() == 300) // Только пробную
																// добавляем
						{
							query = "UPDATE `" + Constants.NamesOfTables.NUMBERS
									+ "` SET `Gym` = '"
									+ cmGym.getSelectedItem()
									+ "', `Trainer` = '"
									+ cmTrainer.getSelectedItem()
									+ "', `Status` = "
									+ Constants.TypesOfClient.TRIAL
									+ " WHERE `id` = " + id;
							SQL.doSQLWithoutResult(query, connect);
							query = "INSERT INTO `"
									+ Constants.NamesOfTables.DATES
									+ "` (`Client_id`, `Trainer`, `Type`, `Date`, `State`) VALUES ("
									+ id + ", '" + cmTrainer.getSelectedItem()
									+ "','" + Constants.TypesOfDates.TRIAL
									+ "', '" + Common.getDateTime(
											dateTimePicker.getDate())
									+ "', 0)";
							SQL.doSQLWithoutResult(query, connect);
						}*/ 
						
						
						
						// добавляем и пробную и доставку


						Functions.setDateState(connect, Common.currectNumber.getLastDateId(), Constants.StatesOfDates.SUCCESSFUL);
							query = "UPDATE `" + Constants.NamesOfTables.NUMBERS
									+ "` SET `Gym` = '"
									+ cmGym.getSelectedItem()
									+ "', `Trainer` = '"
									+ cmTrainer.getSelectedItem()
									+ "', `Status` = "
									+ Constants.TypesOfClient.DELIVERY_AND_TRIAL
									+ ", `Address`='" + txtAddress.getText()
									+ "', `Courier` = '"
									+ cmCourier.getSelectedItem()
									+ "' WHERE `id` = " + id;
							SQL.doSQLWithoutResult(query, connect);
							query = "INSERT INTO `"
									+ Constants.NamesOfTables.DATES
									+ "` (`Client_id`, `Trainer`, `Type`, `Date`, `State`) VALUES ("
									+ id + ", '" + cmTrainer.getSelectedItem()
									+ "','" + Constants.TypesOfDates.TRIAL
									+ "', '" + Common.getDateTime(
											dateTimePicker.getDate())
									+ "', 0)";
							SQL.doSQLWithoutResult(query, connect);
							query = "INSERT INTO `"
									+ Constants.NamesOfTables.DATES
									+ "` (`Client_id`, `Courier`, `Type`, `Date`, `State`) VALUES ("
									+ id + ", '" + cmCourier.getSelectedItem()
									+ "','" + Constants.TypesOfDates.DELIVERY
									+ "', '"
									+ Common.getDateTime(
											dateTimePickerCourier.getDate())
									+ "', 0)";
							SQL.doSQLWithoutResult(query, connect);
						SQL.closeConnect(connect);
						clearSelection();
						refreshTable();
						Common.schedulePanel.clearSelection();
						Common.schedulePanel.refreshTable();
					};
				}.start();
			}
		});
		panelAddTrainer.add(lblDate);
		panelAddTrainer.add(dateTimePicker);
		panelAddTrainer.add(btnOk);
		panelAddTrainer.add(lblTrainer);
		panelAddTrainer.add(lblGym);
		panelAddTrainer.add(cmGym);
		panelAddTrainer.add(cmTrainer);
		panelAddTrainer.add(lblAddress);
		panelAddTrainer.add(lblCourier);
		panelAddTrainer.add(lblDateDelivery);
		panelAddTrainer.add(txtAddress);
		panelAddTrainer.add(cmCourier);
		panelAddTrainer.add(dateTimePickerCourier);
		frameAddTrainer.add(panelAddTrainer);
	}
	private void initAddDelivery() {
		frameAddDelivery.setSize(300, 240);
		panelAddDelivery.setLayout(null);
		length = 0;
		Connection connect = null;
		ArrayList<String> couriers = new ArrayList<String>();
		try {
			connect = DriverManager.getConnection(URL, main.Constants.connInfo);
			String query;
			ResultSet rs;
			query = "SELECT Name0 FROM `users` WHERE `Type` = "
					+ Constants.TypesOfUsers.COURIER + "";
			rs = SQL.doSQL(query, connect);
			while (rs.next()) {
				couriers.add(rs.getString("Name0"));
			}
			SQL.closeConnect(connect);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		JLabel lblAddress = new JLabel("Адресс");
		lblAddress.setFont(Constants.GENERAL_LABEL_FONT);
		lblAddress.setBounds(x, y, 100, 30);
		JTextField txtAddress = new JTextField();
		txtAddress.setBounds(x, y + 30, 115, 26);
		JLabel lblCourier = new JLabel("Курьер");
		lblCourier.setFont(Constants.GENERAL_LABEL_FONT);
		lblCourier.setBounds(x + 130, y, 115, 26);
		JComboBox cmCourier = new JComboBox<>(couriers.toArray());
		cmCourier.setBounds(lblCourier.getX(), y + 30, 115, 26);
		JLabel lblDateDelivery = new JLabel("Доставка");
		lblDateDelivery.setFont(Constants.GENERAL_LABEL_FONT);
		lblDateDelivery.setBounds(x + 15, y + 70, 100, 30);
		DateTimePicker dateTimePickerCourier = new DateTimePicker();

		dateTimePickerCourier.setFormats(DateFormat
				.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM));
		dateTimePickerCourier
				.setTimeFormat(DateFormat.getTimeInstance(DateFormat.MEDIUM));
		dateTimePickerCourier.setDate(Calendar.getInstance().getTime());
		dateTimePickerCourier.setBounds(lblDateDelivery.getX(), y + 100, 215,
				26);
		JButton btnOk = new JButton("ОК");
		btnOk.setBounds(113, 150, 75, 30);
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Thread() {
					public void run() {
						frameAddTrainer.setVisible(false);
						int id = Common.currectNumber.getId();
						Connection connect = null;
						try {
							connect = DriverManager.getConnection(URL,
									main.Constants.connInfo);
						} catch (SQLException e) {
							System.out
									.println("Проблема БД. Call.java, Тренер");
							System.out.println(e.getMessage());
						}
						String query;

						Functions.setDateState(connect, Common.currectNumber.getLastDateId(), Constants.StatesOfDates.SUCCESSFUL);
						query = "UPDATE `" + Constants.NamesOfTables.NUMBERS
								+ "` SET `Status` = "
								+ Constants.TypesOfClient.DELIVERY
								+ ", `Address`='" + txtAddress.getText()
								+ "', `Courier` = '"
								+ cmCourier.getSelectedItem()
								+ "' WHERE `id` = " + id;
						SQL.doSQLWithoutResult(query, connect);
						
						query = "INSERT INTO `" + Constants.NamesOfTables.DATES
								+ "` (`Client_id`, `Courier`, `Type`, `Date`, `State`) VALUES ("
								+ id + ", '" + cmCourier.getSelectedItem()
								+ "','" + Constants.TypesOfDates.DELIVERY
								+ "', '"
								+ Common.getDateTime(
										dateTimePickerCourier.getDate())
								+ "', 0)";
						SQL.doSQLWithoutResult(query, connect);
						SQL.closeConnect(connect);
						clearSelection();
						refreshTable();
						Common.schedulePanel.clearSelection();
						Common.schedulePanel.refreshTable();
					};
				}.start();
				frameAddDelivery.setVisible(false);
			}
		});
		panelAddDelivery.add(lblAddress);
		panelAddDelivery.add(btnOk);
		panelAddDelivery.add(lblCourier);
		panelAddDelivery.add(lblDateDelivery);
		panelAddDelivery.add(txtAddress);
		panelAddDelivery.add(cmCourier);
		panelAddDelivery.add(dateTimePickerCourier);
		frameAddDelivery.add(panelAddDelivery);
	}
	public void initComponents() {
		initAddCall();
		initAddTrainer();
		initNextCall();
		initAddDelivery();
	}
	public void nextCall(int x, int y) {
		if (Common.currectNumber.getNumberDates() + 1 > 2
				&& JOptionPane.showConfirmDialog(null,
						"Уже было три и более звонков, добавить этого лиента в слив?") == 0) {
			if (Common.schedulePanel.isEnabled())
				Common.Sink(Common.currectNumber, Common.schedulePanel);
			else
				Common.Sink(Common.currectNumber, Call.this);
		} else {
			frameNextCall.setVisible(true);
			frameNextCall.setLocation(x, y);
		}
	}
	public void addDelivery() {
		frameAddDelivery.setVisible(true);
	}
	public void addTrainerAndDelivery() {
		frameAddTrainer.setSize(600, 240);
		btnOk.setLocation(262, 150);
		frameAddTrainer.setVisible(true);
	}
	// ДЛЯ ДОБАВАВЛЕНИЯ ТОЛЬКО ПРОБНОЙ
	public void addTrainer() {
		frameAddTrainer.setSize(300, 240);
		btnOk.setLocation(113, 150);
		frameAddTrainer.setVisible(true);
	}
	public void refreshTable() {
		int column = table.getSelectedColumn(), row = table.getSelectedRow();
		String queryClients, queryDates;
		mod.getDataVector().clear();
		try {
			connect1 = DriverManager.getConnection(URL, Constants.connInfo);
			connect2 = DriverManager.getConnection(URL, Constants.connInfo);
			queryClients = "SELECT * FROM " + Constants.NamesOfTables.NUMBERS
					+ " WHERE `Call`='" + User.CurrentUser0 + "'";
			ResultSet rsClients = SQL.doSQL(queryClients, connect1);
			rsClients.last();
			int count = rsClients.getRow();
			clients.clear();
			int i = 0;
			ResultSet rsDates;
			if (rsClients.first())
				do {
					clients.add(new Client());
					clients.get(i).setId(rsClients.getInt("id"));
					clients.get(i).setCall(rsClients.getString("Call"));
					clients.get(i).setCourier(rsClients.getString("Courier"));
					clients.get(i).setTrainer(rsClients.getString("Trainer"));
					clients.get(i).setGym(rsClients.getString("Gym"));
					clients.get(i)
							.setAccountSpam(rsClients.getString("Vk_Spam"));
					clients.get(i).setName(rsClients.getString("Name_Client"));
					clients.get(i).setNumber(rsClients.getString("Number"));
					clients.get(i)
							.setAccountClient(rsClients.getString("Vk_Client"));
					clients.get(i).setDate(rsClients.getString("Date"));
					clients.get(i).setAddress(rsClients.getString("Address"));
					clients.get(i).setComment(rsClients.getString("Comment"));
					clients.get(i).setStatus(rsClients.getInt("Status"));
					queryDates = "SELECT * FROM `"
							+ Constants.NamesOfTables.DATES
							+ "` WHERE `Client_id`=" + rsClients.getInt("id")
							+ "  ORDER BY `Date`";
					rsDates = SQL.doSQL(queryDates, connect2);
					if (rsDates.last()) {
						clients.get(i).setNumberDates(rsDates.getInt("Number"));
						clients.get(i).setLastDate(rsDates.getString("Date"), rsDates.getInt("id"));
					}
					i++;
				} while (rsClients.next());
			if (i == 0) {
				Vector<String> newRow = new Vector<String>();
				mod.addRow(newRow);
				mod.getDataVector().clear();
			} else {
				Collections.sort(clients);
				for (i = 0; i < count; i++) {
					if (clients.get(i).getStatus() == 10)
						break;
					Vector<String> newRow = new Vector<String>();

					newRow.add("" + (i + 1));
					if (clients.get(i)
							.getStatus() == Constants.TypesOfClient.CALL)
						newRow.add(
								clients.get(i).getLastDate().substring(0, 10));
					else
						newRow.add("");
					newRow.add(clients.get(i).getCourier());
					newRow.add(clients.get(i).getTrainer());
					newRow.add(clients.get(i).getGym());
					newRow.add(clients.get(i).getAccountSpam());
					newRow.add(clients.get(i).getName());
					newRow.add(clients.get(i).getNumber());
					newRow.add(clients.get(i).getAccountClient());
					newRow.add(clients.get(i).getDate());
					newRow.add(clients.get(i).getAddress());
					newRow.add(clients.get(i).getComment());
					newRow.add(Functions.getStatus(clients.get(i).getStatus()));
					if (clients.get(i)
							.getStatus() == Constants.TypesOfClient.CALL)
						newRow.add((1 + clients.get(i).getNumberDates()) + "");
					else
						newRow.add("");
					mod.addRow(newRow);
				}
			}
		} catch (SQLException e) {
			System.out.println("Проблема с БД. Call.java");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		if (column > -1 && row > -1) {
			table.setColumnSelectionInterval(column, column);
			table.setRowSelectionInterval(row, row);
		}
		super.refreshTable();
	}
	public Call() {
		new Thread() {
			@Override
			public void run() {
				super.run();
				initComponents();
			}
		}.start();
		headerVect.clear();
		headerVect.add("Номер");
		headerVect.add("Дата звонка");
		headerVect.add("Доставщик");
		headerVect.add("Тренер");
		headerVect.add("Зал");
		headerVect.add("Вк аккаунта");
		headerVect.add("Имя");
		headerVect.add("Номер");
		headerVect.add("Вк клиента");
		headerVect.add("Дата");
		headerVect.add("Адресс");
		headerVect.add("Комментарий");
		headerVect.add("Статус");
		headerVect.add("Номер звонка");
		revalidate(headerVect);
		JMenuItem itemTrainerAndDelivery = new JMenuItem("Доставка пробной");
		Common.leftPanelCall.popup.add(itemTrainerAndDelivery);
		JMenuItem itemDelivery = new JMenuItem("Доставка");
		Common.leftPanelCall.popup.add(itemDelivery);
		JMenuItem itemCall = new JMenuItem("Перенос");
		Common.leftPanelCall.popup.add(itemCall);
		JMenuItem itemSink = new JMenuItem("Слив");
		Common.leftPanelCall.popup.add(itemSink);
		itemDelivery.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addDelivery();
			}
		});
		itemCall.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!Common.schedulePanel.isEnable()) {
					nextCall((int) (scrollPane.getMousePosition().getX()),
							(int) (scrollPane.getMousePosition().getY()));
					Common.schedulePanel.table.clearSelection();
				} else {
					nextCall(
							(int) (Common.schedulePanel.scrollPane
									.getMousePosition().getX()),
							(int) (Common.schedulePanel.scrollPane
									.getMousePosition().getY()));
					table.clearSelection();
				}
			}
		});
		itemTrainerAndDelivery.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addTrainerAndDelivery();
				if (Common.schedulePanel.isEnable()) {
					Common.schedulePanel.table.clearSelection();
				} else {
					table.clearSelection();
				}
			}
		});
		itemSink.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Common.schedulePanel.isEnabled())
					Common.Sink(Common.currectNumber, Common.schedulePanel);
				else
					Common.Sink(Common.currectNumber, Call.this);
			}
		});
		table.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					Point point = e.getPoint();
					int column = table.columnAtPoint(point);
					int row = table.rowAtPoint(point);
					table.setColumnSelectionInterval(column, column);
					table.setRowSelectionInterval(row, row);
					Point p = scrollPane.getMousePosition();
					Common.currectNumber = clients.get(table.getSelectedRow());
					if (Common.currectNumber
							.getStatus() == Constants.TypesOfClient.CALL) {
						Common.leftPanelCall.popup.show(Call.this,
								(int) p.getX() + 1, (int) p.getY() + 1);
					}
				}
				if (e.getClickCount() >= 2 && e.getClickCount() % 2 == 0) {
					Common.currectNumber = clients.get(table.getSelectedRow());
					if (Common.currectNumber
							.getStatus() == Constants.TypesOfClient.CALL)
						nextCall((int) (scrollPane.getMousePosition().getX()),
								(int) (scrollPane.getMousePosition().getY()));
				}
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		JButton btnAdd = new JButton("Добавить клиента");
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addCall();
			}
		});
		add(btnAdd, BorderLayout.SOUTH);
	}
}
