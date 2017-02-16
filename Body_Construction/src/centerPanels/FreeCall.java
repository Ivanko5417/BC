package centerPanels;

import static main.Constants.PASSWORD;
import static main.Constants.URL;
import static main.Constants.USER_NAME;
import static panels.Common.clients;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;


import main.Client;
import main.Functions;
import main.SQL;
import main.User;
public class FreeCall  extends Main_Center_Panel  {
	Client[] clients;
	private Connection connect = null;
	public  void refreshTable()
	{

		int column = table.getSelectedColumn(), row = table.getSelectedRow();
		mod.getDataVector().clear();
		try
		{
			String query = "SELECT * FROM `clients` WHERE `Call`=''";
			connect = DriverManager.getConnection(URL,main.Constants.connInfo);
			ResultSet rs = SQL.doSQL(query, connect);
			
			rs.last();
			int count = rs.getRow();
			count++;
			clients = new Client[count];
			int i = 0;
			if(rs.first())
			do
			{
				clients[i] = new Client();
				clients[i].setId(rs.getInt("id"));
				clients[i].setCourier(rs.getString("Courier"));
				clients[i].setTrainer(rs.getString("Trainer"));
				clients[i].setGym(rs.getString("Gym"));
				clients[i].setAccountSpam(rs.getString("Vk_Spam"));
				clients[i].setName(rs.getString("Name_Client"));
				clients[i].setNumber(rs.getString("Number"));
				clients[i].setAccountClient(rs.getString("Vk_Client"));
				clients[i].setDate(rs.getString("Date"));
				clients[i].setAddress(rs.getString("Address"));
				clients[i].setComment(rs.getString("Comment"));
				clients[i].setStatus(rs.getInt("Status"));
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
				mod.addRow(newRow);
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
			System.out.println("Проблема с БД. FreeCall.java");
			System.out.println(e.getMessage());
		}

		if(column > -1 && row > -1 &&column < table.getColumnCount() && row < table.getRowCount())
		{

			table.setColumnSelectionInterval(column, column);
	        table.setRowSelectionInterval(row, row);
		}
	}
	public FreeCall()
	{
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
					if(e.getClickCount() >= 2 && e.getClickCount()%2==0)
					{ 
						new Thread()
						{
							public void run() 
							{

								Connection connect = null;
								try {
									connect = DriverManager.getConnection(URL,main.Constants.connInfo);
								} catch (SQLException e1) {
								}
								int id = clients[table.getSelectedRow()].getId();
								String query = "UPDATE clients SET `Call` = '"+User.CurrentUser0+"', `Status`= 1 WHERE `id`="
							+id+";";
								SQL.doSQLWithoutResult(query, connect);
								refreshTable();
								query = "UPDATE `dates` SET `Call`='"+User.CurrentUser0+"' WHERE `Client_id`="+id+"";
								SQL.doSQLWithoutResult(query, connect);
								SQL.closeConnect(connect);
							};
						}.start();
					}
				}
			});
		
	}
}
