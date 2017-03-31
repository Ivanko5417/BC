package centerPanels;
import static main.Constants.URL;
import static panels.Common.clients;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import infTables.InfTrain;
import infTables.PayMents;
import main.Client;
import main.Constants;
import main.Functions;
import main.SQL;
import panels.Common;
public class AdminClients extends Main_Center_Panel {
	Connection connect = null;
	//Client currentClient = new Client();
	public JPopupMenu popupAdmin = new JPopupMenu();
	@Override
	public void refreshTable() {
		int column = table.getSelectedColumn(), row = table.getSelectedRow();
		mod.getDataVector().clear();
		try {
			connect = DriverManager.getConnection(URL, Constants.connInfo);
			String query = "SELECT * FROM `" + Constants.NamesOfTables.NUMBERS
					+ "` WHERE `IsClient` = 1";
			ResultSet rs = SQL.doSQL(query, connect);
			rs.last();
			int count = rs.getRow();
			clients.clear();
			rs.first();
			int i = 0;
			if (count > 0)
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
			} else {
				Collections.sort(clients);
				for (i = 0; i < count; i++) {
					if (clients.get(i)
							.getStatus() == Constants.TypesOfClient.SINK)
						break;
					Vector<String> newRow = new Vector<String>();
					newRow.add("" + (i + 1));
					newRow.add(clients.get(i).getSource());
					newRow.add(clients.get(i).getDateCV());
					newRow.add(clients.get(i).getSpam());
					newRow.add(clients.get(i).getCall());
					newRow.add(clients.get(i).getTrainer());
					newRow.add(clients.get(i).getName());
					newRow.add("" + clients.get(i).getCost());
					newRow.add("" + clients.get(i).getDebt());
					newRow.add(Functions
							.getTypeOfTrain(clients.get(i).getTypeOfTrain()));
					newRow.add(Client.getColor(clients.get(i).getLogo()));
					newRow.add(Client.getColor(clients.get(i).getPrePay()));
					newRow.add(Client.getColor(clients.get(i).getPR()));
					newRow.add(Client.getColor(clients.get(i).getOpinion()));
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
	private void initPopUp()
	{
		JMenuItem itemPayMents = new JMenuItem("Оплаты");
		popupAdmin.add(itemPayMents);
		itemPayMents.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PayMents.getInstance().show(Common.currectNumber);
			}
		});
		JMenuItem itemInfTrain = new JMenuItem("История");
		popupAdmin.add(itemInfTrain);
		itemInfTrain.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				InfTrain.getInstance().show(Common.currectNumber);
			}
		});
	}
	private void initComponents()
	{
		initPopUp();
	}
	public AdminClients() {
		initComponents();
		headerVect.clear();
		headerVect.add("Номер");
		headerVect.add("Источник");
		headerVect.add("Дата CV");
		headerVect.add("Спам");
		headerVect.add("Звонильщик");
		headerVect.add("Тренер");
		headerVect.add("Имя");
		headerVect.add("Цена");
		headerVect.add("Долг");
		headerVect.add("Тип тренировки");
		headerVect.add("Logo");
		headerVect.add("Пред");
		headerVect.add("Пиар");
		headerVect.add("Отзыв");
		revalidate(headerVect, true);
		table.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent e) {

				Common.currectNumber = clients.get(table.getSelectedRow());
				if (e.getButton() == MouseEvent.BUTTON3) {
					Point point = e.getPoint();
					int column = table.columnAtPoint(point);
					int row = table.rowAtPoint(point);
					table.setColumnSelectionInterval(column, column);
					table.setRowSelectionInterval(row, row);
					Point p = scrollPane.getMousePosition();
					Common.currectNumber = clients.get(table.getSelectedRow());
					popupAdmin.show(AdminClients.this,
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
			public void mouseClicked(MouseEvent e) {
			}
		});
	}
}
