package centerPanels;
import static main.Constants.PASSWORD;
import static main.Constants.URL;
import static main.Constants.USER_NAME;
import static main.Constants.connInfo;
import static panels.Common.clients;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import main.Client;
import main.Constants;
import main.DateTimePicker;
import main.Functions;
import main.SQL;
import main.User;
import panels.Common;
public class Trainer extends Main_Center_Panel{
	private JFrame frameNextCall = new JFrame();
	private JPanel panelNextCall = new JPanel();
	public static Connection connect1, connect2;
	private int selectedClient = -1; 
	public void refreshTable() {
		int column = table.getSelectedColumn(), row = table.getSelectedRow();

		mod.getDataVector().clear();
		try {
			connect1 = DriverManager.getConnection(URL, Constants.connInfo);
			connect2 = DriverManager.getConnection(URL, Constants.connInfo);
			String query = "SELECT * FROM `"+Constants.NamesOfTables.NUMBERS+"` WHERE Trainer='"
					+ User.CurrentUser0 + "'",queryDates;
			ResultSet rs = SQL.doSQL(query, connect1);
			rs.last();
			int count = rs.getRow();
			clients.clear();
			rs.first();
			int i = 0;
			do {

				queryDates = "SELECT * FROM `"+Constants.NamesOfTables.DATES+"` WHERE `Client_id`="
						+ rs.getInt("id") + "  ORDER BY `Date`";
				ResultSet rsDates = SQL.doSQL(queryDates, connect2);
				clients.add(new Client());
				clients.get(i).setId(rs.getInt("id"));
				clients.get(i).setCall(rs.getString("Call"));
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
				if (rsDates.last()) {
					clients.get(i).setLastDate(rsDates.getString("Date"), rsDates.getInt("id"));
				}
				
				i++;
			} while (rs.next());
			if(i == 0)
			{
				Vector<String> newRow = new Vector<String>();
				mod.addRow(newRow);
				mod.getDataVector().clear();
			}
			else
			{
				Collections.sort(clients);
				for(i = 0; i < count; i++)
				{
					if(clients.get(i).getStatus() == Constants.TypesOfClient.SINK)
						break;
					Vector<String> newRow = new Vector<String>();
					newRow.add(clients.get(i).getTrainer());
					newRow.add(clients.get(i).getGym());
					newRow.add(clients.get(i).getAccountSpam());
					newRow.add(clients.get(i).getName());
					newRow.add(clients.get(i).getNumber());
					newRow.add(clients.get(i).getAccountClient());
					newRow.add(clients.get(i).getDate());
					newRow.add(clients.get(i).getLastDate());
					newRow.add(clients.get(i).getAddress());
					newRow.add(clients.get(i).getComment());
					newRow.add(Functions.getStatus(clients.get(i).getStatus()));
					table.getSelectedRow();
					table.getSelectedColumn();
					mod.addRow(newRow);
				}
			}
		} catch (SQLException e) {
			System.out.println("Проблема с БД. Trainer.java");
			System.out.println(e.getMessage());
		}
		if (column > -1 && row > -1) {
			table.setColumnSelectionInterval(column, column);
			table.setRowSelectionInterval(row, row);
		}
		super.refreshTable();
	}
	
	public Trainer() {
		setLayout(new BorderLayout());
		Vector<String> headerVect = new Vector<String>();
		headerVect.add("Тренер");
		headerVect.add("Зал");
		headerVect.add("Вк Спамщика");
		headerVect.add("Имя");
		headerVect.add("Номер");
		headerVect.add("Вк клиента");
		headerVect.add("Дата спама");
		headerVect.add("Дата тренировки");
		headerVect.add("Адресс");
		headerVect.add("Комментарий");
		headerVect.add("Статус");
		table = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		revalidate(headerVect);
		table.addMouseListener(Common.leftPanelTrainer.tableClick);
		scrollPane = new JScrollPane(table);
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane);
	}
}
