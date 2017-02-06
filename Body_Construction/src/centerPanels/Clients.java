package centerPanels;

import static main.Constans.PASSWORD;
import static main.Constans.URL;
import static main.Constans.USER_NAME;
import static panels.Common.clients;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import main.Client;
import main.DateTimePicker;
import main.SQL;
import main.User;
import panels.Common;

public class Clients extends JPanel {
	JFrame frameAdd = new JFrame("Добавить клиента");
	JPanel panelAdd = new JPanel();
	private JTable table;
	private DefaultTableModel mod;
	private void addClient()
		{
		
		frameAdd.setVisible(true);
		frameAdd.setSize(400, 400);
		panelAdd.setLayout(null);
		
		JLabel lblAccountSpam = new JLabel("Ссылка на аккаунт спама");
		lblAccountSpam.setLocation(10,10);
		lblAccountSpam.setSize(200, 30);
		panelAdd.add(lblAccountSpam);
		JTextField txtAccountSpam =  new JTextField();
		txtAccountSpam.setLocation(10, 40);
		txtAccountSpam.setSize(200, 20);
		panelAdd.add(txtAccountSpam);
		
		JLabel lblAccountClient = new JLabel("Ссылка на аккаунт клиента");
		lblAccountClient.setLocation(10,60);
		lblAccountClient.setSize(200, 30);
		panelAdd.add(lblAccountClient);
		JTextField txtAccountClient =  new JTextField();
		txtAccountClient.setLocation(10, 90);
		txtAccountClient.setSize(200, 20);
		panelAdd.add(txtAccountClient);
		
		JLabel lblNumber= new JLabel("Номер телефона");
		lblNumber.setLocation(10,110);
		lblNumber.setSize(200, 30);
		panelAdd.add(lblNumber);
		JTextField txtNumber =  new JTextField();
		txtNumber.setLocation(10, 140);
		txtNumber.setSize(200, 20);
		panelAdd.add(txtNumber);
		
		JLabel lblName = new JLabel("Имя");
		lblName.setLocation(10,160);
		lblName.setSize(200, 30);
		panelAdd.add(lblName);
		JTextField txtName =  new JTextField();
		txtName.setLocation(10, 190);
		txtName.setSize(200, 20);
		panelAdd.add(txtName);
		
		JLabel lblDate = new JLabel("Дата первого звонка");
		lblDate.setLocation(10,210);
		lblDate.setSize(200, 30);
		panelAdd.add(lblDate);

		DateTimePicker dateTimePicker = new DateTimePicker();
        dateTimePicker.setFormats( DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.MEDIUM ) );
        dateTimePicker.setTimeFormat( DateFormat.getTimeInstance( DateFormat.MEDIUM ) );
        dateTimePicker.setDate(Calendar.getInstance().getTime());
        dateTimePicker.setBounds(10, 240, 200, 26);
        panelAdd.add(dateTimePicker);
		JLabel lblComment = new JLabel("Комментарий");
		lblComment.setLocation(10,260);
		lblComment.setSize(200, 30);
		panelAdd.add(lblComment);
		JTextArea txtComment =  new JTextArea();
		txtComment.setLineWrap(true);
		txtComment.setWrapStyleWord(true);
		txtComment.setBorder(txtAccountClient.getBorder());
		JScrollPane scrollComment = new JScrollPane(txtComment);
		scrollComment.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollComment.setLocation(10, 290);
		scrollComment.setSize(200, 40);
		panelAdd.add(scrollComment);		
		JButton btnOk = new JButton("ОК");
		btnOk.setLocation(10, 330);
		btnOk.setSize(50, 20);
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
					try
					{

						Date date = Calendar.getInstance().getTime();
						Connection connect = null;

						connect = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
						String query = "INSERT INTO clients (Spam, Vk_Spam, Name_Client, Number, Vk_Client, Date, Сomment, Status)"
								+ " values('" + User.CurrentUser0 + "', '" + txtAccountSpam.getText() + "', "
										+ "'" + txtName.getText()+"', '" + txtNumber.getText() + "', "
												+ "'" + txtAccountClient.getText() + "',"
														+ " '"+Common.getDate(date)+"', "
														+ "'" + txtComment.getText() + "', 0)";
						SQL.doSQLWithoutResult(query, connect);
						query = "SELECT * FROM clients WHERE Number='"+txtNumber.getText()+"';";
						ResultSet rs = SQL.doSQL(query, connect);
						int id = -1;
						while (rs.next())
						{
							id = rs.getInt("id");
						}
						query = "INSERT INTO dates (Client_id, Type, Date, State) "
								+ "values("+id+",0,'"+Common.getDate(dateTimePicker.getDate())+"' ,0)";
						SQL.doSQLWithoutResult(query, connect);
						SQL.closeConnect(connect);
						
					}catch(Exception e)
					{System.out.println("Проблема с добавлением записи в БД.\n" + e.getMessage());}
					frameAdd.setVisible(false);					
				}
		});

		refreshTable();
		panelAdd.add(btnOk);
		frameAdd.add(panelAdd);
	}
	
	public void refreshTable()
	{

		int column = table.getSelectedColumn(), row = table.getSelectedRow();
		mod.getDataVector().clear();
		try
		{
			Connection connect;
			connect = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
			
			String query = "SELECT * FROM clients WHERE Spam='"+User.CurrentUser0+"'";
			ResultSet rs = SQL.doSQL(query, connect);
			rs.last();
			int count = rs.getRow();
			count++;
			clients = new Client[count-1];
			rs.first();
			int i = 0;
			do
			{
				clients[i] = new Client();
				clients[i].setCall(rs.getString("Call"));
				clients[i].setCourier(rs.getString("Courier"));
				clients[i].setTrainer(rs.getString("Trainer"));
				clients[i].setGym(rs.getString("Gym"));
				clients[i].setAccountSpam(rs.getString("Vk_Spam"));
				clients[i].setName(rs.getString("Name_Client"));
				clients[i].setNumber(rs.getString("Number"));
				clients[i].setAccountClient(rs.getString("Vk_Client"));
				clients[i].setDate(rs.getString("Date"));
				clients[i].setAddress(rs.getString("Address"));
				clients[i].setComment(rs.getString("Сomment"));
				clients[i].setStatus(""+rs.getInt("Status"));
				Vector<String> newRow = new Vector<String>();
				newRow.add(clients[i].getCall());
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
				newRow.add(clients[i].getStatus());
				table.getSelectedRow();
				table.getSelectedColumn();
				mod.addRow(newRow);
				//table.setSelectionMode(SelectionMode.MULTIPLE);
				i++;
			}while(rs.next());
			if(i == 0)
			{
				Vector<String> newRow = new Vector<String>();
				mod.addRow(newRow);
				mod.getDataVector().clear();
			}
			SQL.closeConnect(connect);
		}catch(Exception e)
		{
			System.out.println("Проблема с БД. Clients.java");
			System.out.println(e.getMessage());
		}

		if(column > -1 && row > -1)
		{

			table.setColumnSelectionInterval(column, column);
	        table.setRowSelectionInterval(row, row);
		}
	}
	
	public Clients()
	{

		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		JButton btnAdd = new JButton("Добавить клиента");
		Vector<String> headerVect = new Vector<String>();
	      headerVect.add("Звонильщик");
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
	      table = new JTable()
	      {
	    	@Override
	    	public boolean isCellEditable(int row, int column) {
	    		
	    		return false;
	    	}
	      };
	      mod = new DefaultTableModel(headerVect, 0);
	      table.setModel(mod);
			table.setSelectionMode(0);
       JScrollPane scrollPane = new JScrollPane(table);
       scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
       add(scrollPane, BorderLayout.CENTER);
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addClient();
			}
		});
		
		add(btnAdd, BorderLayout.SOUTH);
		
	}
}
