package panels;
import static main.Constants.PASSWORD;
import static main.Constants.URL;
import static main.Constants.USER_NAME;
import static panels.Common.clients;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import centerPanels.Call;
import centerPanels.Main_Center_Panel;
import centerPanels.Schedule;
import main.Client;
import main.Constants;
import main.DateTimePicker;
import main.SQL;
import main.User;
public class Left_Bar_Trainer extends Left_Bar_Main {
	public JPopupMenu popupTrainer = new JPopupMenu();
	private JFrame frameMoney = new JFrame("Дошёл");
	private JFrame frameTrainShift = new JFrame("Перенос");
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
				if(Common.schedulePanel.isEnable())
					cl = Common.schedulePanel.getClients().get(table.getSelectedRow());
				else
				cl = Common.clients.get(table.getSelectedRow());
				JScrollPane scrollPane = (JScrollPane) (table.getParent()
						.getParent());
				Point p = scrollPane.getMousePosition();
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
	private void clearSelection()
	{

		if(Common.schedulePanel.isEnable())
		{
			Common.schedulePanel.clearSelection();
		}else
			Common.clientsTrainerPanel.clearSelection();
	}
	private void initMoneyFrame() {
		String[] sTypes = {"Сушка", "Масса"};
		String[] sCosts = {"76", "99", "129", "249"};
		JPanel panelMoney = new JPanel();
		frameMoney.setSize(300, 240);
		panelMoney.setLayout(null);
		Connection connect = null;
		JLabel lblTypeOfTrain = new JLabel("Тип");
		lblTypeOfTrain.setBounds(20, 20, 100, 30);
		lblTypeOfTrain.setFont(new Font("Arial", 1, 20));
		JComboBox cmTypeOfTrain = new JComboBox(sTypes);
		JComboBox cmCost = new JComboBox(sCosts);
		cmTypeOfTrain.setBounds(20, 50, 115, 26);
		JLabel lblCost = new JLabel("Цена($)");
		lblCost.setBounds(150, 20, 100, 30);
		lblCost.setFont(new Font("Arial", 1, 20));
		cmCost.setBounds(150, 50, 115, 26);
		JLabel lblDate = new JLabel("Дата платной");
		lblDate.setBounds(35, 90, 200, 30);
		lblDate.setFont(new Font("Arial", 1, 20));
		DateTimePicker dateTimePicker = new DateTimePicker();
		dateTimePicker.setFormats(DateFormat
				.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM));
		dateTimePicker.setTimeFormat(
				DateFormat.getTimeInstance(DateFormat.DATE_FIELD));
		dateTimePicker.setDate(Calendar.getInstance().getTime());
		dateTimePicker.setBounds(35, 120, 215, 26);
		panelMoney.add(dateTimePicker);
		JButton btnOk = new JButton("OK");
		btnOk.setBounds(105, 150, 75, 30);
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
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
				int typeOfTrain = -1;
				switch ((String) cmTypeOfTrain.getSelectedItem()) {
					case "Сушка" :
						typeOfTrain = Constants.TypesOfTrain.DRYING;
						break;
					case "Масса" :
						typeOfTrain = Constants.TypesOfTrain.MASS;
						break;
				}
				String query = "UPDATE `clients` SET `Status` = "+Constants.TypesOfClient.PAY+", `Cost` = "
						+ cmCost.getSelectedItem() + ", `TypeOfTrain` = '"
						+ typeOfTrain + "' WHERE `id` = " + id;
				SQL.doSQLWithoutResult(query, connect);
				query = "INSERT INTO `"+Constants.NamesOfTables.DATES+"` (`Client_id`, `Trainer`, `Type`, `Date`, `State`) VALUES ("
						+ id + ", '" + User.CurrentUser0 + "','"+Constants.TypesOfDates.PAY+"', '"
						+ Common.getDateTime(dateTimePicker.getDate())
						+ "', 0)";
				SQL.doSQLWithoutResult(query, connect);
				SQL.closeConnect(connect);
				frameMoney.setVisible(false);
				clearSelection();
			}
		});
		panelMoney.add(lblDate);
		panelMoney.add(dateTimePicker);
		panelMoney.add(btnOk);
		panelMoney.add(lblCost);
		panelMoney.add(lblTypeOfTrain);
		panelMoney.add(cmTypeOfTrain);
		panelMoney.add(cmCost);
		frameMoney.add(panelMoney);
	}
	private void initTrainShift() {
		JPanel panelTrainShift = new JPanel();
		frameTrainShift.setSize(262, 150);
		frameTrainShift.setResizable(false);
		panelTrainShift.setLayout(null);
		JLabel lblDateTrain = new JLabel("Дата пробной");
		lblDateTrain.setBounds(20, 5, 200, 30);
		lblDateTrain.setFont(new Font("Arial", 1, 20));
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
				String dateQuery = "INSERT INTO `"+Constants.NamesOfTables.DATES+"` (`Client_id`, `Trainer`,`Type`,`Number`, `Date`, `State`)"
						+ "VALUES (" + id + ", '" + User.CurrentUser0 + "', "
						+ Constants.TypesOfDates.TRIAL + ", " + cl.getNumberDates() + ", '"
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
	private void init() {
		initMoneyFrame();
		initTrainShift();
	}
	public Left_Bar_Trainer() {
		init();
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
		JMenuItem itemTrainShift = new JMenuItem("Перенос");
		popupTrainer.add(itemTrainShift);
		itemTrainShift.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frameTrainShift.setVisible(true);
			}
		});
		JMenuItem itemCall = new JMenuItem("Перезвон");
		popupTrainer.add(itemCall);
		itemCall.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearSelection();
			}
		});
		JMenuItem itemMoney = new JMenuItem("Дошёл");
		popupTrainer.add(itemMoney);
		itemMoney.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frameMoney.setVisible(true);
			}
		});
		JButton btnSchedule = new JButton("Расписание");
		btnSchedule.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSchedule.setForeground(new Color(39, 124, 78));
		btnSchedule.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Common.setSchedule();
			}
		});
		add(btnSchedule);
		JButton btnScheduleWeek = new JButton("Расписание");
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
