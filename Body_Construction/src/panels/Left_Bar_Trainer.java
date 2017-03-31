package panels;
import static main.Constants.PASSWORD;
import static main.Constants.URL;
import static main.Constants.USER_NAME;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import main.Client;
import main.Constants;
import main.DateTimePicker;
import main.Functions;
import main.SQL;
import main.User;
public class Left_Bar_Trainer extends Left_Bar_Main {
	public JPopupMenu popupTrainer = new JPopupMenu();
	private JFrame frameMoney = new JFrame("Дошёл");
	private JFrame frameTrainShift = new JFrame("Перенос");
	private JFrame frameTrain = new JFrame("Тренировка");
	private JTextField txtPaid = null;
	JCheckBox chbCash = null;
	Client cl = null;
	public MouseListener tableClick = new MouseListener() {
		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
		@Override
		public void mousePressed(MouseEvent e) {
			JTable table = (JTable) (e.getSource());
			if (e.getButton() == MouseEvent.BUTTON3) {
				Point point = e.getPoint();
				int column = table.columnAtPoint(point);
				int row = table.rowAtPoint(point);
				table.setColumnSelectionInterval(column, column);
				table.setRowSelectionInterval(row, row);
				if (Common.schedulePanel.isEnable())
					cl = Common.schedulePanel.getClients()
							.get(table.getSelectedRow());
				else
					cl = Common.clients.get(table.getSelectedRow());
				JScrollPane scrollPane = (JScrollPane) (table.getParent()
						.getParent());
				Point p = scrollPane.getMousePosition();
				if(Common.schedulePanel.CheckToday())
				{

					if (Common.schedulePanel.isEnable())
						popupTrainer
								.show(scrollPane.getParent(), (int) p.getX() + 1,
										(int) p.getY() + 1
												+ (int) Common.schedulePanel.headerPanel
														.getPreferredSize()
														.getHeight());
					else
						popupTrainer.show(scrollPane.getParent(),
								(int) p.getX() + 1, (int) p.getY() + 1);	
				}
			}
		}
		@Override
		public void mouseExited(MouseEvent arg0) {
		}
		@Override
		public void mouseEntered(MouseEvent arg0) {
		}
		@Override
		public void mouseClicked(MouseEvent arg0) {
		}
	};
	private void clearSelection() {
		if (Common.schedulePanel.isEnable()) {
			Common.schedulePanel.clearSelection();
		} else
			Common.clientsTrainerPanel.clearSelection();
	}
	private void initMoneyFrame() {
		String[] sTypes = {"Сушка", "Масса"};
		String[] sCosts = {"76", "99", "129", "249"};
		String[] sCount = {"4", "8", "12", "24"};
		JPanel panelMoney = new JPanel();
		frameMoney.setSize(430, 240);
		panelMoney.setLayout(null);
		Connection connect = null;
		JLabel lblTypeOfTrain = new JLabel("Тип");
		lblTypeOfTrain.setBounds(20, 20, 100, 30);
		lblTypeOfTrain.setFont(Constants.GENERAL_LABEL_FONT);
		JComboBox cmTypeOfTrain = new JComboBox(sTypes);
		JComboBox cmCost = new JComboBox(sCosts);
		cmTypeOfTrain.setBounds(20, 50, 115, 26);
		JLabel lblCost = new JLabel("Цена($)");
		lblCost.setBounds(150, 20, 100, 30);
		lblCost.setFont(Constants.GENERAL_LABEL_FONT);
		cmCost.setBounds(150, 50, 115, 26);
		JLabel lblPaid = new JLabel("Внесено($)");
		lblPaid.setBounds(280, 20, 150, 30);
		lblPaid.setFont(Constants.GENERAL_LABEL_FONT);
		JTextField txtPaid = new JTextField();
		txtPaid.setBounds(280, 50, 115, 26);
		txtPaid.addKeyListener(Constants.keylistenerForDouble);
		JCheckBox chbCash = new JCheckBox("Касса");
		chbCash.setBounds(275, 75, 100, 20);
		JLabel lblCount = new JLabel("Кол-во");
		lblCount.setBounds(20, 90, 100, 30);
		lblCount.setFont(Constants.GENERAL_LABEL_FONT);
		lblCount.addKeyListener(Constants.keylistenerForNumbers);
		JComboBox cmCount = new JComboBox(sCount);
		cmCount.setBounds(20, 120, 115, 26);
		JLabel lblDate = new JLabel("Дата платной");
		lblDate.setBounds(150, 90, 200, 30);
		lblDate.setFont(Constants.GENERAL_LABEL_FONT);
		DateTimePicker dateTimePicker = new DateTimePicker();
		dateTimePicker.setFormats(DateFormat
				.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM));
		dateTimePicker.setTimeFormat(
				DateFormat.getTimeInstance(DateFormat.DATE_FIELD));
		dateTimePicker.setDate(Calendar.getInstance().getTime());
		dateTimePicker.setBounds(150, 120, 215, 26);
		panelMoney.add(dateTimePicker);
		JButton btnOk = new JButton("OK");
		btnOk.setBounds(170, 150, 75, 30);
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					double paid = Double.parseDouble(txtPaid.getText()),
							cost = Double.parseDouble(
									cmCost.getSelectedItem().toString()),
							debt = 0;
					if (paid > cost) {
						/*
						 * if(JOptionPane.showConfirmDialog(
						 * null,"Внесенные деньги превышают долг, продолжить?")
						 * == JOptionPane.NO_OPTION ||
						 * JOptionPane.showConfirmDialog(
						 * null,"Внесенные деньги превышают долг, продолжить?")
						 * == JOptionPane.CANCEL_OPTION)
						 */ JOptionPane.showMessageDialog(null,
								"Внесённая сумма больше цены.");
						new NumberFormatException();
					} else
						debt = cost - paid;
					frameMoney.setVisible(false);
					int id = cl.getId();
					Connection connect = null;
					try {
						connect = DriverManager.getConnection(URL, USER_NAME,
								PASSWORD);
					} catch (SQLException e) {
						System.out.println(
								"Проблема БД. Left_Bar_Trainer.java, Платная");
						System.out.println(e.getMessage());
					}
					String queryDates = "SELECT * FROM `"
							+ Constants.NamesOfTables.DATES
							+ "` WHERE `Client_id`=" + cl.getId()
							+ "  AND `Type` = " + Constants.TypesOfDates.TRIAL
							+ " ORDER BY `Date`";
					ResultSet rs = SQL.doSQL(queryDates, connect);
					rs = SQL.doSQL(queryDates, connect);
					try {
						if (rs.last()) {
							Functions.setDateState(connect, rs.getInt("id"),
									Constants.StatesOfDates.SUCCESSFUL);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					int typeOfTrain = -1;
					if (paid != 0) // добавляем запись в PayMents
					{
						Date date = Calendar.getInstance().getTime();
						int whereMoney;
						if (chbCash.isSelected())
							whereMoney = Constants.WhereMoney.CASH_BOX;
						else
							whereMoney = Constants.WhereMoney.CASH;
						String query = "INSERT INTO `"
								+ Constants.NamesOfTables.PAYMENTS
								+ "`(`id`, `Date`, `Count`, `Where_Money`, `Trainer`) VALUES ("
								+ id + ", '" + Common.getDate(date) + "', "
								+ paid + ", " + whereMoney + ", '"
								+ User.CurrentUser0 + "')";
						SQL.doSQLWithoutResult(query, connect);
					}
					switch ((String) cmTypeOfTrain.getSelectedItem()) {
						case "Сушка" :
							typeOfTrain = Constants.TypesOfTrain.DRYING;
							break;
						case "Масса" :
							typeOfTrain = Constants.TypesOfTrain.MASS;
							break;
					}
					String query;
					if (cl.getStatus() == Constants.TypesOfClient.CLIENT) {//Новая конвесия
						// TODO НОВАЯ КОНВЕРСИЯ
						// ДОБАВЛЯЕМ ЗАПИСЬ В clients
						int NumberCV = 0;
						query = "SELECT `Debt`, `NumberCV`  FROM `"+Constants.NamesOfTables.CLIENTS+"` WHERE `id` = "+id;
						ResultSet rsSelectFromClients = SQL.doSQL(query, connect);
						try {
							rsSelectFromClients.first();
							debt += rsSelectFromClients.getInt("Debt");
							NumberCV = rsSelectFromClients.getInt("NumberCV") + 1;
						} catch (SQLException e) {
							e.printStackTrace();
						}
						query = "UPDATE `" + Constants.NamesOfTables.CLIENTS
								+ "` SET  `TypeOfTrain` = '" + typeOfTrain
								+ "', `Cost` = " + cost + ", `Debt` = " + debt
								+ ", `NumberTrain` = 1, `CountOfTrain` = "
								+ cmCount.getSelectedItem() + ", `NumberCV` = "+NumberCV+" WHERE `id` = "
								+ id;
						SQL.doSQLWithoutResult(query, connect);
						// ДОБАВЛЕНИЕ ТРЕНИРОВКИ
						query = "INSERT INTO `" + Constants.NamesOfTables.DATES
								+ "` (`Client_id`, `Trainer`, `Type`, `Date`, `State`) VALUES ("
								+ id + ", '" + User.CurrentUser0 + "','"
								+ Constants.TypesOfDates.CLIENT + "', '"
								+ Common.getDateTime(dateTimePicker.getDate())
								+ "', 0)";
						SQL.doSQLWithoutResult(query, connect);
					} else {
						// ИЗМЕНЯЕМ СТАТУС НА ПЛАТНУЮ
						query = "UPDATE `" + Constants.NamesOfTables.NUMBERS
								+ "` SET `Status` = "
								+ Constants.TypesOfClient.PAY
								+ ", `IsClient` = 1  WHERE `id` = " + id;
						SQL.doSQLWithoutResult(query, connect);
						// ДОБАВЛЯЕМ ЗАПИСЬ В clients
						query = "INSERT INTO `"
								+ Constants.NamesOfTables.CLIENTS
								+ "`(`id`, `TypeOfTrain`, `Cost`, `Debt`, `CountOfTrain`, `Date`) VALUES("
								+ id + ", '" + typeOfTrain + "', " + cost + ", "
								+ debt + ", " + cmCount.getSelectedItem()
								+ ", '"
								+ Common.getDate(
										Calendar.getInstance().getTime())
								+ "')";
						SQL.doSQLWithoutResult(query, connect);
						// ДОБАВЛЕНИЕ ТРЕНИРОВКИ
						query = "INSERT INTO `" + Constants.NamesOfTables.DATES
								+ "` (`Client_id`, `Trainer`, `Type`, `Date`, `State`) VALUES ("
								+ id + ", '" + User.CurrentUser0 + "','"
								+ Constants.TypesOfDates.PAY + "', '"
								+ Common.getDateTime(dateTimePicker.getDate())
								+ "', 0)";
						SQL.doSQLWithoutResult(query, connect);
					}
					SQL.closeConnect(connect);
					clearSelection();
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null,
							"Поле \"Внесено\" заполнено неверно, либо значения больше цены. ");
				} catch (NullPointerException e) {
					JOptionPane.showMessageDialog(null,
							"Поле \"Внесено\" пустое. ");
				}
			}
		});
		panelMoney.add(lblDate);
		panelMoney.add(dateTimePicker);
		panelMoney.add(btnOk);
		panelMoney.add(lblCost);
		panelMoney.add(lblTypeOfTrain);
		panelMoney.add(cmTypeOfTrain);
		panelMoney.add(cmCost);
		panelMoney.add(chbCash);
		panelMoney.add(lblPaid);
		panelMoney.add(txtPaid);
		panelMoney.add(lblCount);
		panelMoney.add(cmCount);
		frameMoney.add(panelMoney);
	}
	private void initTrainShift() {
		JPanel panelTrainShift = new JPanel();
		frameTrainShift.setSize(262, 150);
		frameTrainShift.setResizable(false);
		panelTrainShift.setLayout(null);
		JLabel lblDateTrain = new JLabel("Дата пробной");
		lblDateTrain.setBounds(20, 5, 200, 30);
		lblDateTrain.setFont(Constants.GENERAL_LABEL_FONT);
		DateTimePicker dateTimePicker = new DateTimePicker();
		dateTimePicker.setFormats(DateFormat
				.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM));
		dateTimePicker
				.setTimeFormat(DateFormat.getTimeInstance(DateFormat.MEDIUM));
		dateTimePicker.setDate(Calendar.getInstance().getTime());
		dateTimePicker.setBounds(20, 35, 215, 26);
		JButton btnOk = new JButton("ОК");
		btnOk.setBounds(90, 65, 75, 30);
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.setNumberDates(cl.getNumberDates() + 1);
				Connection connect = null;
				try {
					connect = DriverManager.getConnection(URL, USER_NAME,
							PASSWORD);
				} catch (SQLException e1) {
					System.out.println(
							"Проблема с БД. Left_Bar_Train.Java, nextCall");
				}
				int id = cl.getId();
				Functions.setDateState(connect, cl.getLastDateId(),
						Constants.StatesOfDates.SHIFT);
				String dateQuery = "INSERT INTO `"
						+ Constants.NamesOfTables.DATES
						+ "` (`Client_id`, `Trainer`,`Type`,`Number`, `Date`, `State`)"
						+ "VALUES (" + id + ", '" + User.CurrentUser0 + "', "
						+ Constants.TypesOfDates.TRIAL + ", "
						+ cl.getNumberDates() + ", '"
						+ Common.getDateTime(dateTimePicker.getDate())
						+ "', '0') ";
				SQL.doSQLWithoutResult(dateQuery, connect);
				SQL.closeConnect(connect);
				frameTrainShift.setVisible(false);
				clearSelection();
			}
		});
		panelTrainShift.add(lblDateTrain);
		panelTrainShift.add(dateTimePicker);
		panelTrainShift.add(btnOk);
		frameTrainShift.add(panelTrainShift);
	}
	private void initTrain() {
		JPanel panelTrain = new JPanel();
		panelTrain.setPreferredSize(new Dimension(265, 195));
		panelTrain.setLayout(null);
		JLabel lblDateTrain = new JLabel("Дата");
		lblDateTrain.setBounds(20, 5, 200, 30);
		lblDateTrain.setFont(Constants.GENERAL_LABEL_FONT);
		DateTimePicker dateTimePicker = new DateTimePicker();
		dateTimePicker.setFormats(DateFormat
				.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM));
		dateTimePicker
				.setTimeFormat(DateFormat.getTimeInstance(DateFormat.MEDIUM));
		dateTimePicker.setDate(Calendar.getInstance().getTime());
		dateTimePicker.setBounds(20, 35, 215, 26);
		JLabel lblPaid = new JLabel("Внесено($)");
		lblPaid.setBounds(74, 65, 150, 30);
		lblPaid.setFont(Constants.GENERAL_LABEL_FONT);
		txtPaid = new JTextField();
		txtPaid.setText("0");
		txtPaid.setBounds(74, 95, 115, 26);
		txtPaid.addKeyListener(Constants.keylistenerForDouble);
		chbCash = new JCheckBox("Касса");
		chbCash.setBounds(70, 125, 100, 20);
		JButton btnOk = new JButton("ОК");
		btnOk.setBounds(93, 145, 75, 30);
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.setNumberDates(cl.getNumberDates() + 1);
				Connection connect = null;
				try {
					connect = DriverManager.getConnection(URL, USER_NAME,
							PASSWORD);
				} catch (SQLException e1) {
					System.out.println(
							"Проблема с БД. Left_Bar_Train.Java, nextCall");
				}
				double debt = Double.parseDouble(txtPaid.getText());
				int id = cl.getId(); // ДОБАВЛЯЕМ ТРЕНИРОВКУ
				Functions.setDateState(connect, cl.getLastDateId(),
						Constants.StatesOfDates.SUCCESSFUL);
				String query = "INSERT INTO `" + Constants.NamesOfTables.DATES
						+ "` (`Client_id`, `Trainer`,`Type`,`Number`, `Date`, `State`)"
						+ "VALUES (" + id + ", '" + User.CurrentUser0 + "', "
						+ Constants.TypesOfDates.CLIENT + ", "
						+ cl.getCountOfTrain() + ", '"
						+ Common.getDateTime(dateTimePicker.getDate())
						+ "', '0') ";
				SQL.doSQLWithoutResult(query, connect);
				if (debt != 0) // добавляем запись в PayMents
				{
					Date date = Calendar.getInstance().getTime();
					int whereMoney;
					if (chbCash.isSelected())
						whereMoney = Constants.WhereMoney.CASH_BOX;
					else
						whereMoney = Constants.WhereMoney.CASH;
					String queryPay = "INSERT INTO `"
							+ Constants.NamesOfTables.PAYMENTS
							+ "`(`id`, `Date`, `Count`, `Where_Money`, `Trainer`) VALUES ("
							+ id + ", '" + Common.getDate(date) + "', " + debt
							+ ", " + whereMoney + ", '" + User.CurrentUser0
							+ "')";
					SQL.doSQLWithoutResult(queryPay, connect);
				}
				// ДОБАВЛЯЕМ ВНЕСЕННЫЕ ДЕНЬГИ
				String queryDebt = "";
				if (debt > cl.getDebt())
					JOptionPane.showMessageDialog(null,
							"Долг этого клиента меньше");
				else if (cl.getDebt() != 0 && debt != 0) {
					queryDebt = " `Debt` = " + (cl.getDebt() - debt) + ",";
				}
				// ИЗМЕНЯЕМ СТАТУС
				if (cl.getStatus() == Constants.TypesOfClient.PAY) {
					query = "UPDATE `" + Constants.NamesOfTables.NUMBERS
							+ "` SET  `Status` = "
							+ Constants.TypesOfClient.CLIENT + " WHERE `id` = "
							+ id;
					SQL.doSQLWithoutResult(query, connect);
				}
				// УВЕЛИЧИВАЕМ НОМЕР ТРЕНИРОВКИ
				System.out.println(cl.getNumberTrain() + " " + cl.getCountOfTrain());
				int number = cl.getNumberTrain() + 1;
							query = "UPDATE `" + Constants.NamesOfTables.CLIENTS
							+ "` SET " + queryDebt + " `NumberTrain` = "
							+ number + " WHERE `id` = " + id + "";
					SQL.doSQLWithoutResult(query, connect);
				SQL.closeConnect(connect);
				frameTrain.setVisible(false);
				clearSelection();
			}
		});
		panelTrain.add(chbCash);
		panelTrain.add(lblDateTrain);
		panelTrain.add(dateTimePicker);
		panelTrain.add(lblPaid);
		panelTrain.add(txtPaid);
		panelTrain.add(btnOk);
		frameTrain.add(panelTrain);
		frameTrain.pack();
	}
	private void init() {
		initMoneyFrame();
		initTrainShift();
		initTrain();
	}
	public void Train() {
		frameTrain.setVisible(true);
	}
	public Left_Bar_Trainer() {
		init();
		JMenuItem itemMoney = new JMenuItem("Дошёл");
		popupTrainer.add(itemMoney);
		itemMoney.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (cl.getStatus() / 10 == Constants.TypesOfClient.TRIAL) // TRIAL_PASS,
																			// TRIAL_TRIAL,
																			// TRIAL_NOTHING
																			// --
																			// двузначные
																			// и
																			// первая
																			// цифра
																			// всех
																			// равна
																			// TRAL
					frameMoney.setVisible(true);
				
				if (cl.getStatus() == Constants.TypesOfClient.PAY || (cl
						.getStatus() == Constants.TypesOfClient.CLIENT
						&& cl.getNumberTrain() < cl.getCountOfTrain())) {
					txtPaid.setText("0");
					if (cl.getDebt() == 0) {
						txtPaid.setEditable(false);
						chbCash.setEnabled(false);
					} else {
						txtPaid.setEditable(true);
						chbCash.setEnabled(true);
					}
					Train();
				}else
					if(cl.getStatus() == Constants.TypesOfClient.CLIENT
							&& cl.getNumberTrain() == cl.getCountOfTrain())
					{
						frameMoney.setVisible(true);
						System.out.println("Новая конверсия");
					}
			}
		});
		JMenuItem itemTrainShift = new JMenuItem("Перенос");
		popupTrainer.add(itemTrainShift);
		itemTrainShift.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frameTrainShift.setVisible(true);
			}
		});
		JMenuItem itemSink = new JMenuItem("Слив");
		popupTrainer.add(itemSink);
		itemSink.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Common.schedulePanel.isEnabled())
					Common.Sink(cl, Common.schedulePanel);
				else
					Common.Sink(cl, Common.clientsTrainerPanel);
				clearSelection();
			}
		});
		JButton btnSchedule = new JButton("Расписание дня");
		btnSchedule.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSchedule.setForeground(new Color(39, 124, 78));
		btnSchedule.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Common.setSchedule();
			}
		});
		add(btnSchedule);
		JButton btnScheduleWeek = new JButton("Расписание недели");
		btnScheduleWeek.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnScheduleWeek.setForeground(new Color(231, 124, 154));
		btnScheduleWeek.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Common.setScheduleWeek();
			}
		});
		add(btnScheduleWeek);
		JButton btnClients = new JButton("Мои клиенты");
		btnClients.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnClients.setForeground(new Color(39, 124, 78));
		btnClients.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Common.setTrainer();
			}
		});
		add(btnClients);
		add(btnSettings);
		add(btnSink);
		add(btnExit);
	}
}
