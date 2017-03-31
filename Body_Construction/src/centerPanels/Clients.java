package centerPanels;
import static main.Constants.PASSWORD;
import static main.Constants.URL;
import static main.Constants.USER_NAME;
import static panels.Common.clients;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
public class Clients extends Main_Center_Panel { 
	JFrame frameAdd = new JFrame("Добавить клиента");
	JPanel panelAdd = new JPanel();
	public static Connection connect;
	private void initClient()
	{
		frameAdd.setSize(400, 400);
		panelAdd.setLayout(null);
		JLabel lblAccountSpam = new JLabel("Ссылка на аккаунт спама");
		lblAccountSpam.setLocation(10, 10);
		lblAccountSpam.setSize(200, 30);
		panelAdd.add(lblAccountSpam);
		JTextField txtAccountSpam = new JTextField();
		txtAccountSpam.setLocation(10, 40);
		txtAccountSpam.setSize(200, 20);
		panelAdd.add(txtAccountSpam);
		JLabel lblAccountClient = new JLabel("Ссылка на аккаунт клиента");
		lblAccountClient.setLocation(10, 60);
		lblAccountClient.setSize(200, 30);
		panelAdd.add(lblAccountClient);
		JTextField txtAccountClient = new JTextField();
		txtAccountClient.setLocation(10, 90);
		txtAccountClient.setSize(200, 20);
		panelAdd.add(txtAccountClient);
		JLabel lblNumber = new JLabel("Номер телефона");
		lblNumber.setLocation(10, 110);
		lblNumber.setSize(200, 30);
		panelAdd.add(lblNumber);
		JFormattedTextField txtNumber = new JFormattedTextField(Constants.NUMBER_MASK);
		txtNumber.setLocation(10, 140);
		txtNumber.setSize(200, 20);
		panelAdd.add(txtNumber);
		JLabel lblName = new JLabel("Имя");
		lblName.setLocation(10, 160);
		lblName.setSize(200, 30);
		panelAdd.add(lblName);
		JTextField txtName = new JTextField();
		txtName.setLocation(10, 190);
		txtName.setSize(200, 20);
		panelAdd.add(txtName);
		JLabel lblDate = new JLabel("Дата первого звонка");
		lblDate.setLocation(10, 210);
		lblDate.setSize(200, 30);
		panelAdd.add(lblDate);
		DateTimePicker dateTimePicker = new DateTimePicker();
		dateTimePicker.setFormats(DateFormat
				.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM));
		dateTimePicker
				.setTimeFormat(DateFormat.getTimeInstance(DateFormat.MEDIUM));
		dateTimePicker.setDate(Calendar.getInstance().getTime());
		dateTimePicker.setBounds(10, 240, 200, 26);
		panelAdd.add(dateTimePicker);
		JLabel lblComment = new JLabel("Комментарий");
		lblComment.setLocation(10, 260);
		lblComment.setSize(200, 30);
		panelAdd.add(lblComment);
		JTextArea txtComment = new JTextArea();
		txtComment.setLineWrap(true);
		txtComment.setWrapStyleWord(true);
		txtComment.setBorder(txtAccountClient.getBorder());
		JScrollPane scrollComment = new JScrollPane(txtComment);
		scrollComment.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollComment.setLocation(10, 290);
		scrollComment.setSize(200, 40);
		panelAdd.add(scrollComment);
		JButton btnOk = new JButton("ОК");
		btnOk.setLocation(10, 330);
		btnOk.setSize(50, 20);
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Thread(){
					@Override
					public void run() {
						super.run();

						frameAdd.setVisible(false);
						try {
							Date date = Calendar.getInstance().getTime();
							Connection connect = null;
							connect = DriverManager.getConnection(URL, USER_NAME,
									PASSWORD);
							String query = "INSERT INTO `"+Constants.NamesOfTables.NUMBERS+"` (`Spam`, `Vk_Spam`, `Name_Client`, `Number`, `Vk_Client`, `Date`, `Comment`, `Status`)"
									+ " values('" + User.CurrentUser0 + "', '"
									+ txtAccountSpam.getText() + "', " + "'"
									+ txtName.getText() + "', '" + txtNumber.getText()
									+ "', " + "'" + txtAccountClient.getText() + "',"
									+ " '" + Common.getDate(date) + "', " + "'"
									+ txtComment.getText() + "', '"+Constants.TypesOfClient.SPAM+"')";
							SQL.doSQLWithoutResult(query, connect);
							query = "SELECT * FROM "+Constants.NamesOfTables.NUMBERS+" WHERE Number='"
									+ txtNumber.getText() + "';";
							ResultSet rs = SQL.doSQL(query, connect);
							int id = -1;
							while (rs.next()) {
								id = rs.getInt("id");
							}
							query = "INSERT INTO `"+Constants.NamesOfTables.DATES+"` (`Client_id`, `Type`, `Date`, `State`) "
									+ "values(" + id + ",'"+Constants.TypesOfDates.CALL+"','"
									+ Common.getDateTime(dateTimePicker.getDate())
									+ "' ,0)";
							SQL.doSQLWithoutResult(query, connect);
							SQL.closeConnect(connect);
						} catch (Exception e) {
							System.out.println("Проблема с добавлением записи в БД.\n"
									+ e.getMessage());
						}
					}
				}.start();
				
			}
		});
		panelAdd.add(btnOk);
		frameAdd.add(panelAdd);
	}
	private void addClient() {

		frameAdd.setVisible(true);
	}
	public void refreshTable() {
		int column = table.getSelectedColumn(), row = table.getSelectedRow();
		mod.getDataVector().clear();
		try {
			connect = DriverManager.getConnection(URL,Constants.connInfo);
			String query = "SELECT * FROM `"+Constants.NamesOfTables.NUMBERS+"` WHERE `Spam`='"
					+ User.CurrentUser0 + "'";
			ResultSet rs = SQL.doSQL(query, connect);
			rs.last();
			int count = rs.getRow();
			clients.clear();
			rs.first();
			int i = 0;
			if(count > 0)
			do {
					clients.add(new Client());
					clients.get(i).setId(rs.getInt("id"));
					clients.get(i).setSource(rs.getString("From"));
					clients.get(i).setSpam(rs.getString("Spam"));
					clients.get(i).setCall(rs.getString("Call"));
					clients.get(i).setCourier(rs.getString("Courier"));
					clients.get(i).setTrainer(rs.getString("Trainer"));
					clients.get(i).setGym(rs.getString("Gym"));
					clients.get(i).setAccountSpam(rs.getString("Vk_Spam"));
					clients.get(i).setName(rs.getString("Name_Client"));
					clients.get(i).setNumber(rs.getString("Number"));
					clients.get(i).setAccountClient(rs.getString("Vk_Client"));
					clients.get(i).setDate(rs.getString("Date"));
					clients.get(i).setAddress(rs.getString("Address"));
					clients.get(i).setComment(rs.getString("Comment"));
					clients.get(i).setStatus(rs.getInt("Status"));
					clients.get(i).setClient(rs.getBoolean("IsClient"));
					Functions.getClientInf(clients.get(i));
					i++;
			} while (rs.next());
			if (i == 0) {
				Vector<String> newRow = new Vector<String>();
				mod.addRow(newRow);
				mod.getDataVector().clear();
			}
			else
			{
				Collections.sort(clients);
				for( i = 0; i < count; i++)
				{
					if(clients.get(i).getStatus() == Constants.TypesOfClient.SINK)
						break;

					Vector<String> newRow = new Vector<String>();
					newRow.add("" + (i + 1));
					newRow.add(clients.get(i).getCall());
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
					if(clients.get(i).isClient())
					{
						newRow.add(""+clients.get(i).getCost());
						newRow.add(Functions.getTypeOfTrain(clients.get(i).getTypeOfTrain()));
					}
					mod.addRow(newRow);
				}
			}
		} catch (SQLException e) {
			System.out.println("Проблема с БД. Clients.java");
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}
		if (column > -1 && row > -1) {
			table.setColumnSelectionInterval(column, column);
			table.setRowSelectionInterval(row, row);
		}
		super.refreshTable();
	}
	public Clients() {
		new Thread()
		{
			public void run() 
			{
				initClient();
			};
		}.start();
		JButton btnAdd = new JButton("Добавить клиента");
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addClient();
			}
		});

		revalidate(headerVect);
		add(btnAdd, BorderLayout.SOUTH);
	}
}
