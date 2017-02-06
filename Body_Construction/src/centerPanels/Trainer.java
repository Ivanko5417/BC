package centerPanels;
import static main.Constans.PASSWORD;
import static main.Constans.URL;
import static main.Constans.USER_NAME;
import static panels.Common.clients;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import panels.Common;
import main.Client;
import main.SQL;
import main.User;
public class Trainer extends JPanel {
	private JTable table;
	JScrollPane scrollPane = null;
	private DefaultTableModel mod;
	public void refreshTable() {
		int column = table.getSelectedColumn(), row = table.getSelectedRow();
		mod.getDataVector().clear();
		try {
			Connection connect1, connect2;
			connect1 = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
			String query = "SELECT * FROM `clients` WHERE Trainer='"
					+ User.CurrentUser0 + "'",queryDates;
			ResultSet rs = SQL.doSQL(query, connect1);
			rs.last();
			int count = rs.getRow();
			clients = new Client[count];
			rs.first();
			int i = 0;
			do {

				queryDates = "SELECT * FROM `dates` WHERE `Client_id`="
						+ rs.getInt("id") + "  ORDER BY `Date`";
				connect2 = DriverManager.getConnection(URL, USER_NAME,
						PASSWORD);
				ResultSet rsDates = SQL.doSQL(queryDates, connect2);
				clients[i] = new Client();
				clients[i].setId(rs.getInt("id"));
				clients[i].setTrainer(rs.getString("Trainer"));
				clients[i].setGym(rs.getString("Gym"));
				clients[i].setAccountSpam(rs.getString("Vk_Spam"));
				clients[i].setName(rs.getString("Name_Client"));
				clients[i].setNumber(rs.getString("Number"));
				clients[i].setAccountClient(rs.getString("Vk_Client"));
				clients[i].setDate(rs.getString("Date"));
				clients[i].setAddress(rs.getString("Address"));
				clients[i].setComment(rs.getString("�omment"));
				clients[i].setStatus(""+rs.getInt("Status"));
				if (rsDates.last()) {
					clients[i].setDateCall(rsDates.getString("Date"));
				}
				SQL.closeConnect(connect2);
				
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
				Arrays.sort(clients);
				for(i = 0; i < count -1; i++)
				{
					if(clients[i].getStatus().equals("10"))
						break;
					Vector<String> newRow = new Vector<String>();
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
				}
			}
			SQL.closeConnect(connect1);
		} catch (Exception e) {
			System.out.println("�������� � ��. Trainer.java");
			System.out.println(e.getMessage());
		}
		if (column > -1 && row > -1) {
			table.setColumnSelectionInterval(column, column);
			table.setRowSelectionInterval(row, row);
		}
	}
	public Trainer() {
		setLayout(new BorderLayout());
		Vector<String> headerVect = new Vector<String>();
		headerVect.add("������");
		headerVect.add("���");
		headerVect.add("�� ��������");
		headerVect.add("���");
		headerVect.add("�����");
		headerVect.add("�� �������");
		headerVect.add("����");
		headerVect.add("������");
		headerVect.add("�����������");
		headerVect.add("������");
		table = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setSelectionMode(0);
		mod = new DefaultTableModel(headerVect, 0);
		table.setModel(mod);
		table.addMouseListener(Common.leftPanelTrainer.tableClick);
		scrollPane = new JScrollPane(table);
		System.out.println();
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane);
	}
}
