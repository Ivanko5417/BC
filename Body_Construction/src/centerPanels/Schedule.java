package centerPanels;
import static main.Constans.PASSWORD;
import static main.Constans.URL;
import static main.Constans.USER_NAME;
import static panels.Common.clients;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import panels.Common;
import main.Client;
import main.SQL;
import main.User;
public class Schedule extends JPanel {
	private JTable table;
	private DefaultTableModel mod;
	public void refreshTable() {
		int column = table.getSelectedColumn(), row = table.getSelectedRow();
		mod.getDataVector().clear();
		try {
			Connection connect, connect2;
			connect = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
			String query = "SELECT * FROM `clients` WHERE Trainer='"
					+ User.CurrentUser0 + "'";
			ResultSet rsClients = SQL.doSQL(query, connect);
			rsClients.last();
			int count = rsClients.getRow();
			clients = new Client[count];
			rsClients.first();
			int i = 0;
			do {
				String queryDates = "SELECT * FROM `dates` WHERE `Client_id`="
						+ rsClients.getInt("id") + " ORDER BY `Date`";
				connect2 = DriverManager
						.getConnection(URL, USER_NAME, PASSWORD);
				ResultSet rsDates = SQL.doSQL(queryDates, connect2);
				if (rsDates.last()) {
					String dateString0 = rsDates.getString("Date");
					String datesString1 = dateString0.substring(0,
							10);
					if (datesString1.equals(Common.getDateBase(Calendar
							.getInstance().getTime()))) {
						clients[i] = new Client();
						clients[i].setId(rsClients.getInt("id"));
						clients[i].setTrainer(rsClients.getString("Trainer"));
						clients[i].setGym(rsClients.getString("Gym"));
						clients[i].setAccountSpam(rsClients
								.getString("Vk_Spam"));
						clients[i].setName(rsClients.getString("Name_Client"));
						clients[i].setNumber(rsClients.getString("Number"));
						clients[i].setAccountClient(rsClients
								.getString("Vk_Client"));
						clients[i].setDate(rsClients.getString("Date"));
						clients[i].setAddress(rsClients.getString("Address"));
						clients[i].setComment(rsClients.getString("�omment"));
						clients[i].setStatus("" + rsClients.getInt("Status"));
						Vector<String> newRow = new Vector<String>();
						newRow.add(clients[i].getTrainer());
						newRow.add(clients[i].getGym());
						newRow.add(clients[i].getAccountSpam());
						newRow.add(clients[i].getName());
						newRow.add(clients[i].getNumber());
						newRow.add(clients[i].getAccountClient());
						newRow.add(Common.getTime(dateString0));
						newRow.add(clients[i].getAddress());
						newRow.add(clients[i].getComment());
						newRow.add(clients[i].getStatus());
						table.getSelectedRow();
						table.getSelectedColumn();
						mod.addRow(newRow);
					}
				}
				SQL.closeConnect(connect2);
				// table.setSelectionMode(SelectionMode.MULTIPLE);
				i++;
			} while (rsClients.next());
			if (i == 0) {
				mod.addRow(new Vector<String>());
			}
			SQL.closeConnect(connect);
		} catch (Exception e) {
			System.out.println("�������� � ��. Trainer.java");
			System.out.println(e.getMessage());
		}
		if (column > -1 && row > -1) {
			table.setColumnSelectionInterval(column, column);
			table.setRowSelectionInterval(row, row);
		}
	}
	public Schedule() {
		setLayout(new BorderLayout());
		Vector<String> headerVect = new Vector<String>();
		headerVect.add("������");
		headerVect.add("���");
		headerVect.add("�� ��������");
		headerVect.add("���");
		headerVect.add("�����");
		headerVect.add("�� �������");
		headerVect.add("�����");
		headerVect.add("������");
		headerVect.add("�����������");
		table = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setSelectionMode(0);
		mod = new DefaultTableModel(headerVect, 0);
		table.setModel(mod);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane);
	}
}
