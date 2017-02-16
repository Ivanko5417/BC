package centerPanels;
import static main.Constants.PASSWORD;
import static main.Constants.URL;
import static main.Constants.USER_NAME;
import static panels.Common.clients;

import java.awt.BorderLayout;
import java.awt.Color;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import main.Client;
import main.DateTimePicker;
import main.Functions;
import main.SQL;
import main.User;
import panels.Common;
public class Call  extends Main_Center_Panel {
	private JFrame frameAdd = new JFrame("Добавить клиента");
	private JPanel panelAdd = new JPanel();
	private JFrame frameNextCall = new JFrame("Новый звонок");
	private JPanel panelNextCall = new JPanel();
	private JFrame frameAddTrainer = new JFrame("Тренер");
	private JPanel panelAddTrainer = new JPanel();
	private Connection connect1 = null;
	private Connection connect2 = null;
	private int selectedClient = -1, length = 0;
	private void initAddCall() {
		frameAdd.setSize(400, 400);
		panelAdd.setLayout(null);
		JLabel lblFrom = new JLabel("Источник");
		lblFrom.setLocation(10, 10);
		lblFrom.setSize(200, 30);
		panelAdd.add(lblFrom);
		String[] sFrome = {"Instagram", "Body24.com", "Facebook", "VK"};
		JComboBox cmFrom = new JComboBox(sFrome);
		cmFrom.setLocation(10, 40);
		cmFrom.setSize(200, 20);
		panelAdd.add(cmFrom);
		JLabel lblNumber = new JLabel("Номер телефона");
		lblNumber.setLocation(10, 60);
		lblNumber.setSize(200, 30);
		panelAdd.add(lblNumber);
		JTextField txtNumber = new JTextField();
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
		JTextField txtDate = new JTextField();
		txtDate.setLocation(10, 190);
		txtDate.setSize(200, 20);
		panelAdd.add(txtDate);
		JLabel lblComment = new JLabel("Комментарий");
		lblComment.setLocation(10, 210);
		lblComment.setSize(200, 30);
		panelAdd.add(lblComment);
		JTextArea txtComment = new JTextArea();
		txtComment.setLineWrap(true);
		txtComment.setWrapStyleWord(true);
		txtComment.setBorder(txtDate.getBorder());
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

				new Thread()
				{
					public void run() 
					{
						frameAdd.setVisible(false);
						try {
							Date date = Calendar.getInstance().getTime();
							Connection connect = null;

							connect = DriverManager.getConnection(URL,main.Constants.connInfo);
							String query = "INSERT INTO `clients` (From, Name_Client, Number,Call, Date, Comment, Status)"
									+ " values('"+cmFrom.getSelectedItem()+"'," + "'"
									+ txtName.getText() + "', '" + txtNumber.getText()
									+ "','"+User.CurrentUser0+"', "
									+ " '20" + (date.getYear() % 100) + "."
									+ (date.getMonth() + 1) + "." + date.getDate()
									+ "', " + "'" + txtComment.getText() + "', 1)";
							SQL.doSQLWithoutResult(query, connect);
							query = "SELECT * FROM `clients` WHERE Number='"
									+ txtNumber.getText() + "';";
							ResultSet rs = SQL.doSQL(query, connect);
							int id = -1;
							while (rs.next()) {
								id = rs.getInt("id");
							}
							query = "INSERT INTO dates (Client_id, Value, Date, State) "
									+ "values(" + id + ",'Зв1','" + txtDate.getText()
									+ "' ,0)";
							SQL.doSQLWithoutResult(query, connect);
							SQL.closeConnect(connect);
							refreshTable();
						} catch (Exception e) {
							System.out.println("Проблема с добавлением записи в БД.\n"
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
		lblDateCall.setFont(new Font("Arial", 1, 20));
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
				new Thread()
				{
					public void run() 
					{
						clients[selectedClient].setNumberDates(
								clients[selectedClient].getNumberDates() + 1);
						Connection connect = null;
						try {

							connect = DriverManager.getConnection(URL,main.Constants.connInfo);
						} catch (SQLException e1) {
							System.out.println("Проблема с БД. Call.Java, nextCall");
						}
						int id = clients[selectedClient].getId();
						String dateQuery = "INSERT INTO `dates` (`Client_id`, `Call`,`Type`,`Number`, `Date`, `State`)"
								+ "VALUES (" + id + ", '" + User.CurrentUser0 + "', "
								+ "0" + ", " + clients[selectedClient].getNumberDates()
								+ ", '" + Common.getDate(dateTimePicker.getDate())
								+ "', '0') ";
						SQL.doSQLWithoutResult(dateQuery, connect);
						SQL.closeConnect(connect);
						frameNextCall.setVisible(false);
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
		frameAddTrainer.setSize(300, 240);
		panelAddTrainer.setLayout(null);
		length = 0;
		Connection connect = null;
		ArrayList<String> gyms = new ArrayList<String>();
		String[][] trainers = new String[50][2];
		try {
			connect = DriverManager.getConnection(URL,main.Constants.connInfo);
			String queryTrainer = "SELECT * FROM `users` WHERE `Type` = 3";
			ResultSet rsTrainer = SQL.doSQL(queryTrainer, connect);
			while (rsTrainer.next()) {
				String s = rsTrainer.getString("Gym");
				String s1 = rsTrainer.getString("Name0");
				trainers[length][0] = s1;
				trainers[length][1] = s;
				length++;
				if (gyms.indexOf(s) == -1)
					gyms.add(s);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		JLabel lblGym = new JLabel("Зал");
		lblGym.setBounds(20, 20, 100, 30);
		lblGym.setFont(new Font("Arial", 1, 20));
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
		cmGym.setBounds(20, 50, 115, 26);
		cmGym.addActionListener(l);
		JLabel lblTrainer = new JLabel("Тренер");
		lblTrainer.setBounds(150, 20, 100, 30);
		lblTrainer.setFont(new Font("Arial", 1, 20));
		cmTrainer.setBounds(150, 50, 115, 26);
		JLabel lblDate = new JLabel("Дата");
		lblDate.setBounds(35, 90, 100, 30);
		lblDate.setFont(new Font("Arial", 1, 20));
		DateTimePicker dateTimePicker = new DateTimePicker();
		dateTimePicker.setFormats(DateFormat
				.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM));
		dateTimePicker
				.setTimeFormat(DateFormat.getTimeInstance(DateFormat.MEDIUM));
		dateTimePicker.setDate(Calendar.getInstance().getTime());
		dateTimePicker.setBounds(35, 120, 215, 26);
		JButton btnOk = new JButton("OK");
		btnOk.setBounds(105, 150, 75, 30);
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Thread()
				{
					public void run() 
					{
						frameAddTrainer.setVisible(false);
						int id = clients[selectedClient].getId();
						Connection connect = null;
						try {

							connect = DriverManager.getConnection(URL,main.Constants.connInfo);
						} catch (SQLException e) {
							System.out.println("Проблема БД. Call.java, Тренер");
							System.out.println(e.getMessage());
						}
						String query = "UPDATE `clients` SET `Gym` = '"
								+ cmGym.getSelectedItem() + "', `Trainer` = '"
								+ cmTrainer.getSelectedItem()
								+ "', `Status` = 3 WHERE `id` = " + id;
						SQL.doSQLWithoutResult(query, connect);
						query = "INSERT INTO `dates` (`Client_id`, `Trainer`, `Type`, `Date`, `State`) VALUES ("
								+ id + ", '" + cmTrainer.getSelectedItem() + "','2', '"
								+ Common.getDateTime(dateTimePicker.getDate())
								+ "', 0)";
						SQL.doSQLWithoutResult(query, connect);
						SQL.closeConnect(connect);
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
	public void initComponents() {
		initAddCall();
		initAddTrainer();
		initNextCall();
	}
	private void addCall() {
		frameAdd.setVisible(true);
	}
	private void nextCall(int x, int y) {
		if (clients[selectedClient].getNumberDates() + 1 > 2
				&& JOptionPane.showConfirmDialog(null,
						"Уже было три и более звонков, добавить этого лиента в слив?") == 0) {
			Common.Sink(clients[selectedClient]);
		} else {
			frameNextCall.setVisible(true);
			frameNextCall.setLocation(x, y);
		}
	}
	private void addTrainer() {
		frameAddTrainer.setVisible(true);
	}
	public void refreshTable() {
		int column = table.getSelectedColumn(), row = table.getSelectedRow();
		String queryClients, queryDates;
		mod.getDataVector().clear();
		try {
			queryClients = "SELECT * FROM clients WHERE `Call`='"
					+ User.CurrentUser0 + "'";

			connect1 = DriverManager.getConnection(URL,main.Constants.connInfo);
			ResultSet rsClients = SQL.doSQL(queryClients, connect1);
			rsClients.last();
			int count = rsClients.getRow();
			clients = new Client[count];
			System.out.println(count);
			int i = 0;
			ResultSet rsDates;
			connect2 = DriverManager.getConnection(URL,main.Constants.connInfo);
			if (rsClients.first())
				do {
					
					clients[i] = new Client();
					clients[i].setId(rsClients.getInt("id"));
					clients[i].setCourier(rsClients.getString("Courier"));
					clients[i].setTrainer(rsClients.getString("Trainer"));
					clients[i].setGym(rsClients.getString("Gym"));
					clients[i].setAccountSpam(rsClients.getString("Vk_Spam"));
					clients[i].setName(rsClients.getString("Name_Client"));
					clients[i].setNumber(rsClients.getString("Number"));
					clients[i]
							.setAccountClient(rsClients.getString("Vk_Client"));
					clients[i].setDate(rsClients.getString("Date"));
					clients[i].setAddress(rsClients.getString("Address"));
					clients[i].setComment(rsClients.getString("Comment"));
					clients[i].setStatus(rsClients.getInt("Status"));
					queryDates = "SELECT * FROM `dates` WHERE `Client_id`="
							+ rsClients.getInt("id") + "  ORDER BY `Date`";
					rsDates = SQL.doSQL(queryDates, connect2);
					System.out.println("1");
					if (rsDates.last()) {
						clients[i].setNumberDates(rsDates.getInt("Number"));
						clients[i].setDateCall(rsDates.getString("Date"));
					}
					i++;
				} while (rsClients.next());

			SQL.closeConnect(connect2);
			if (i == 0) {
				Vector<String> newRow = new Vector<String>();
				mod.addRow(newRow);
				mod.getDataVector().clear();
			} else {
				Arrays.sort(clients);
				System.out.println(clients.toString());
				for (i = 0; i < count; i++) {
					if (clients[i].getStatus() == 10)
						break;
					Vector<String> newRow = new Vector<String>();
					newRow.add(clients[i].getCourier());
					newRow.add(clients[i].getTrainer());
					newRow.add(clients[i].getGym());
					newRow.add(clients[i].getAccountSpam());
					newRow.add(clients[i].getName());
					newRow.add(clients[i].getNumber());
					newRow.add(clients[i].getAccountClient());
					newRow.add(clients[i].getDate());
					newRow.add(clients[i].getAddress());
					newRow.add(clients[i].getComment());
					newRow.add(Functions.getStatus(clients[i].getStatus()));
					newRow.add((1 + clients[i].getNumberDates()) + "");
					newRow.add(clients[i].getDateCall().substring(0, 10));
					mod.addRow(newRow);
				}
			}
			SQL.closeConnect(connect1);
		} catch (SQLException e) {
			System.out.println("Проблема с БД. Call.java");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		if (column > -1 && row > -1) {
			table.setColumnSelectionInterval(column, column);
			table.setRowSelectionInterval(row, row);
		}
	}
	public Call() {
		new Thread(){
			@Override
			public void run() {
				super.run();
				initComponents();				
			}
		}.start();
		/*headerVect.clear();
		headerVect.add("Доставщик");
		headerVect.add("Тренер");
		headerVect.add("Зал");
		headerVect.add("Вк Спамщика");
		headerVect.add("Имя");
		headerVect.add("Номер");
		headerVect.add("Вк клиента");
		headerVect.add("Дата");
		headerVect.add("Адресс");
		headerVect.add("Комментарий");
		headerVect.add("Статус");
		headerVect.add("Номер звонка");
		headerVect.add("Дата звонка");*/
		//mod = new DefaultTableModel(headerVect, 0);
		JPopupMenu popup = new JPopupMenu();
		JMenuItem itemTrainer = new JMenuItem("Тренер");
		popup.add(itemTrainer);
		JMenuItem itemCall = new JMenuItem("Звонок");
		popup.add(itemCall);
		JMenuItem itemSink = new JMenuItem("Слив");
		popup.add(itemSink);
		itemCall.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				nextCall((int) (scrollPane.getMousePosition().getX()),
						(int) (scrollPane.getMousePosition().getY()));
			}
		});
		itemTrainer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addTrainer();
			}
		});
		itemSink.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Common.Sink(clients[selectedClient]);
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
					selectedClient = table.getSelectedRow();
					popup.show(Call.this, (int) p.getX() + 1,
							(int) p.getY() + 1);
				}
				if (e.getClickCount() >= 2 && e.getClickCount() % 2 == 0) {
					selectedClient = table.getSelectedRow();
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
