package panels;
import static main.Constants.URL;
import static main.Constants.connInfo;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import centerPanels.Call;
import main.Constants;
import main.DateTimePicker;
import main.SQL;
import main.User;
public class Left_Bar_Courier extends Left_Bar_Main {
	private Connection connect = null;
	private JFrame frameNextDelivery = new JFrame("Перенос");
	private JPanel panelNextDelivery = new JPanel();
	private JFrame frameAddTrainer = new JFrame("Пробная");
	private JPanel panelAddTrainer = new JPanel();
	private int x = 20, y = 20;
	private int length;
	private void initAddTrainer() {
		frameAddTrainer.setSize(600, 240);
		panelAddTrainer.setLayout(null);
		length = 0;
		Connection connect = null;
		ArrayList<String> gyms = new ArrayList<String>();
		String[][] trainers = new String[50][2];
		try {
			connect = DriverManager.getConnection(URL, main.Constants.connInfo);
			String query = "SELECT * FROM `users` WHERE `Type` = "
					+ Constants.TypesOfUsers.TRAINER;
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
		JButton btnOk = new JButton("ОК");
		btnOk.setBounds(262, 150, 75, 30);
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Thread() {
					public void run() {
						frameAddTrainer.setVisible(false);
						int id = Common.currectCient.getId();
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
						// пробную добавляем
						query = "UPDATE `clients` SET `Gym` = '"
								+ cmGym.getSelectedItem() + "', `Trainer` = '"
								+ cmTrainer.getSelectedItem() + "', `Status` = "
								+ Constants.TypesOfClient.TRIAL
								+ " WHERE `id` = " + id;
						SQL.doSQLWithoutResult(query, connect);
						query = "INSERT INTO `"+Constants.NamesOfTables.DATES+"` (`Client_id`, `Trainer`, `Type`, `Date`, `State`) VALUES ("
								+ id + ", '" + cmTrainer.getSelectedItem()
								+ "','" + Constants.TypesOfDates.TRIAL + "', '"
								+ Common.getDateTime(dateTimePicker.getDate())
								+ "', 0)";
						SQL.doSQLWithoutResult(query, connect);
						SQL.closeConnect(connect);
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
		frameAddTrainer.add(panelAddTrainer);
	}
	private void initNextCall() {
		frameNextDelivery.setSize(162, 150);
		frameNextDelivery.setResizable(false);
		panelNextDelivery.setLayout(null);
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
						Common.currectCient.setNumberDates(
								Common.currectCient.getNumberDates() + 1);
						Connection connect = null;
						try {
							connect = DriverManager.getConnection(URL,
									main.Constants.connInfo);
						} catch (SQLException e1) {
							System.out.println(
									"Проблема с БД. Scheldule.Java, nextDelivery");
						}
						int id = Common.currectCient.getId();
						String dateQuery = "INSERT INTO `"+Constants.NamesOfTables.DATES+"` (`Client_id`, `Courier`,`Type`,`Number`, `Date`, `State`)"
								+ "VALUES (" + id + ", '" + User.CurrentUser0
								+ "', " + Constants.TypesOfDates.DELIVERY + ", "
								+ Common.currectCient.getNumberDates() + ", '"
								+ Common.getDate(dateTimePicker.getDate())
								+ "', '0') ";
						SQL.doSQLWithoutResult(dateQuery, connect);
						dateQuery = "UPDATE `"+Constants.NamesOfTables.DATES+"` SET `Type` = "
								+ Constants.TypesOfDates.SUCCESSFUL_DELIVERY
								+ " WHERE `id` = "
								+ Common.currectCient.getLastDateId();
						SQL.doSQLWithoutResult(dateQuery, connect);
						SQL.closeConnect(connect);
						frameNextDelivery.setVisible(false);
						Common.schedulePanel.clearSelection();
						Common.schedulePanel.refreshTable();
					};
				}.start();
			}
		});
		panelNextDelivery.add(lblDateCall);
		panelNextDelivery.add(dateTimePicker);
		panelNextDelivery.add(btnOk);
		frameNextDelivery.add(panelNextDelivery);
	}
	private void addTrainer() {
		frameAddTrainer.setVisible(true);
	}
	private void initComponents() {
		initAddTrainer();
		initNextCall();
	}
	private void nextDelivery() {
		if (Common.currectCient.getNumberDates() + 1 > 2
				&& JOptionPane.showConfirmDialog(null,
						"Уже было три и более звонков, добавить этого лиента в слив?") == 0) {
			Common.Sink(Common.currectCient, Common.schedulePanel);
		} else {
			frameNextDelivery.setVisible(true);
		}
	}
	public Left_Bar_Courier() {
		initComponents();
		popup = new JPopupMenu();
		JMenuItem itemDelivery = new JMenuItem("Доставка");
		popup.add(itemDelivery);
		itemDelivery.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(Common.currectCient.getStatus());
				if (Common.currectCient.getStatus() == 3) {
					try {
						connect = DriverManager.getConnection(URL, connInfo);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					String query = "UPDATE `"+Constants.NamesOfTables.DATES+"` SET `Type` = "
							+ Constants.TypesOfDates.SUCCESSFUL_DELIVERY
							+ " WHERE `id` = "
							+ Common.currectCient.getLastDateId();
					SQL.doSQLWithoutResult(query, connect);
					SQL.closeConnect(connect);
					Common.schedulePanel.clearSelection();
					Common.schedulePanel.refreshTable();
					JOptionPane.showMessageDialog(null, "ОК");
				} else if (Common.currectCient
						.getStatus() == Constants.TypesOfClient.DELIVERY) {
					try {
						connect = DriverManager.getConnection(URL, connInfo);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					String query = "UPDATE `"+Constants.NamesOfTables.DATES+"` SET `Type` = "
							+ Constants.TypesOfDates.SUCCESSFUL_DELIVERY
							+ " WHERE `id` = "
							+ Common.currectCient.getLastDateId();
					SQL.doSQLWithoutResult(query, connect);
					query = "UPDATE `clients` SET `Status` = "
							+ Constants.TypesOfClient.SUCCESSFUL_DELIVERY
							+ " WHERE `id` = " + Common.currectCient.getId();
					SQL.doSQLWithoutResult(query, connect);
					SQL.closeConnect(connect);
					Common.schedulePanel.clearSelection();
					Common.schedulePanel.refreshTable();
					JOptionPane.showMessageDialog(null, "ОК");
				}
			}
		});
		JMenuItem itemTrainer = new JMenuItem("Пробная");
		popup.add(itemTrainer);
		itemTrainer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		itemTrainer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addTrainer();
			}
		});
		JMenuItem itemShift = new JMenuItem("Перенос");
		popup.add(itemShift);
		itemShift.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nextDelivery();
			}
		});
		JMenuItem itemSink = new JMenuItem("Слив");
		itemSink.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					connect = DriverManager.getConnection(URL, connInfo);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				String query = "UPDATE `"+Constants.NamesOfTables.DATES+"` SET `Type` = "
						+ Constants.TypesOfDates.UNSUCCESSFUL_DELIVERY
						+ " WHERE `id` = "
						+ Common.currectCient.getLastDateId();
				SQL.doSQLWithoutResult(query, connect);
				SQL.closeConnect(connect);
				Common.Sink(Common.currectCient, Common.schedulePanel);
			}
		});
		popup.add(itemSink);
		JButton btnScheldule = new JButton("Расписание");
		btnScheldule.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnScheldule.setForeground(new Color(194, 26, 12));
		btnScheldule.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Common.setSchedule();
			}
		});
		add(btnScheldule);
		add(btnExit);
	}
}
