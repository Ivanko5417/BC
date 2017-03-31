package centerPanels;
import static main.Constants.URL;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import main.Client;
import main.Constants;
import main.Functions;
import main.SQL;
import main.User;
import panels.Common;
import table.MainTableModel;
public class FreeCall extends Main_Center_Panel {
	Client[] clients;
	private Connection connect1 = null, connect2 = null;
	public void refreshTable() {
		int column = table.getSelectedColumn(), row = table.getSelectedRow();
		mod.getDataVector().clear();
		try {
			String query = "SELECT * FROM `" + Constants.NamesOfTables.NUMBERS
					+ "` WHERE `Call`=''";
			connect1 = DriverManager.getConnection(URL,
					main.Constants.connInfo);
			connect2 = DriverManager.getConnection(URL,
					main.Constants.connInfo);
			ResultSet rsClient = SQL.doSQL(query, connect1);
			rsClient.last();
			int count = rsClient.getRow();
			count++;
			clients = new Client[count];
			int i = 0;
			if (rsClient.first())
				do {
					String queryDates = "SELECT * FROM `"
							+ Constants.NamesOfTables.DATES
							+ "` WHERE `Client_id`=" + rsClient.getInt("id")
							+ " ORDER BY `Date`";
					ResultSet rsDates = SQL.doSQL(queryDates, connect2);
					if (rsDates.last()) {
						clients[i] = new Client();
						clients[i].setId(rsClient.getInt("id"));
						clients[i].setSpam(rsClient.getString("Spam"));
						clients[i]
								.setAccountSpam(rsClient.getString("Vk_Spam"));
						clients[i].setName(rsClient.getString("Name_Client"));
						clients[i].setNumber(rsClient.getString("Number"));
						clients[i].setAccountClient(
								rsClient.getString("Vk_Client"));
						clients[i].setDate(rsClient.getString("Date"));
						clients[i].setComment(rsClient.getString("Comment"));
						clients[i].setStatus(rsClient.getInt("Status"));
						clients[i].setNumberDates(rsDates.getInt("Number"));
						clients[i].setLastDateId(rsDates.getInt("id"));
						clients[i].setLastDate(Common.getTime(rsDates.getString("Date")),
								rsDates.getInt("id"));
						Vector<String> newRow = new Vector<String>();
						newRow.add(clients[i].getLastDate());
						newRow.add(clients[i].getSpam());
						newRow.add(clients[i].getAccountSpam());
						newRow.add(clients[i].getName());
						newRow.add(clients[i].getNumber());
						newRow.add(clients[i].getAccountClient());
						newRow.add(clients[i].getDate());
						newRow.add(clients[i].getComment());
						newRow.add(Functions.getStatus(clients[i].getStatus()));
						mod.addRow(newRow);
						i++;
					}
				} while (rsClient.next());
			if (i == 0) {
				Vector<String> newRow = new Vector<String>();
				mod.addRow(newRow);
				mod.getDataVector().clear();
			}
			SQL.closeConnect(connect1);
		} catch (Exception e) {
			System.out.println("Проблема с БД. FreeCall.java");
			System.out.println(e.getMessage());
		}
		if (column > -1 && row > -1 && column < table.getColumnCount()
				&& row < table.getRowCount()) {
			table.setColumnSelectionInterval(column, column);
			table.setRowSelectionInterval(row, row);
		}
		super.refreshTable();
	}
	public FreeCall() {
		table.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() >= 2 && e.getClickCount() % 2 == 0) {
					new Thread() {
						public void run() {
							Connection connect = null;
							try {
								connect = DriverManager.getConnection(URL,
										main.Constants.connInfo);
							} catch (SQLException e1) {
							}
							int id = clients[table.getSelectedRow()].getId();
							String query = "UPDATE `"
									+ Constants.NamesOfTables.NUMBERS
									+ "` SET `Call` = '" + User.CurrentUser0
									+ "', `Status`= "
									+ Constants.TypesOfClient.CALL
									+ " WHERE `id`=" + id + ";";
							SQL.doSQLWithoutResult(query, connect);
							refreshTable();
							query = "UPDATE `" + Constants.NamesOfTables.DATES
									+ "` SET `Call`='" + User.CurrentUser0
									+ "' WHERE `Client_id`=" + id + "";
							SQL.doSQLWithoutResult(query, connect);
							SQL.closeConnect(connect);
						};
					}.start();
				}
			}
		});
		headerVect.clear();
		headerVect.add("Время");
		headerVect.add("Спамщик");
		headerVect.add("Вк аккаунта");
		headerVect.add("Имя");
		headerVect.add("Номер");
		headerVect.add("Вк клиента");
		headerVect.add("Дата");
		headerVect.add("Комментарий");
		headerVect.add("Статус");
		revalidate(headerVect);
	}
}
