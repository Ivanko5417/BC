package centerPanels;

import static main.Constants.URL;
import static panels.Common.clients;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.mysql.jdbc.Driver;

import main.Constants;
import main.Functions;
import main.SQL;
import main.User;
import panels.Common;

public class Users extends Main_Center_Panel {
	Connection connect;
	ArrayList<User> users  = new ArrayList<User>();
	User currentUser;
	@Override
	public void refreshTable() {int column = table.getSelectedColumn(), row = table.getSelectedRow();
	mod.getDataVector().clear();
	try {
		connect = DriverManager.getConnection(URL,Constants.connInfo);
		String query = "SELECT * FROM `"+Constants.NamesOfTables.USERS+"` WHERE `Type` <> 10";
		ResultSet rs = SQL.doSQL(query, connect);
		rs.last();
		int count = rs.getRow();
		users.clear();
		rs.first();
		int i = 0;
		if(count > 0)
		do {
				users.add(new User());
				users.get(i).setId(rs.getInt("id"));
				users.get(i).setName_User(rs.getString("Name_User"));
				users.get(i).setName0(rs.getString("Name0"));
				users.get(i).setType(rs.getInt("Type"));
				if(users.get(i).getType() == Constants.TypesOfUsers.TRAINER)
					users.get(i).setGym(rs.getString("Gym"));
				users.get(i).setLogin(rs.getString("Login"));
				users.get(i).setPassword(rs.getString("Pass"));
				users.get(i).setActive(rs.getBoolean("Active"));
				i++;
		} while (rs.next());
		if (i == 0) {
			Vector<String> newRow = new Vector<String>();
			
			mod.addRow(newRow);
			mod.getDataVector().clear();
		}
		else
		{
			Collections.sort(users);
			for( i = 0; i < count; i++)
			{
				Vector<String> newRow = new Vector<String>();
				User us = users.get(i);
				if(!us.isActive()) continue;
				newRow.add(us.getName_User());
				newRow.add(us.getName0());
				if(us.getType() == Constants.TypesOfUsers.TRAINER)
				newRow.add(Functions.getTypeOfUser(us.getType()) +"("+ us.getGym() + ")");
				else
					newRow.addElement(Functions.getTypeOfUser(us.getType()));
				newRow.add(us.getLogin());
				newRow.add(us.getPassword());
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
	public Users() {
		headerVect.clear();
		headerVect.add("Полное Имя");
		headerVect.add("Ник");
		headerVect.add("Должность");
		headerVect.add("Логин");
		headerVect.add("Пароль");
		revalidate(headerVect);
		JPopupMenu popUpMenu = new JPopupMenu();
		JMenuItem itemDelete = new JMenuItem("Уволить");
		itemDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					connect = DriverManager.getConnection(Constants.URL, Constants.connInfo);
					String query = "UPDATE `"+Constants.NamesOfTables.USERS+"` SET `Active`=FALSE WHERE `id` = "+currentUser.getId();
					SQL.doSQLWithoutResult(query, connect);
					SQL.closeConnect(connect);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				refreshTable();
			}
		});
		popUpMenu.add(itemDelete);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					Point point = e.getPoint();
					int column = table.columnAtPoint(point);
					int row = table.rowAtPoint(point);
					table.setColumnSelectionInterval(column, column);
					table.setRowSelectionInterval(row, row);
					Point p = scrollPane.getMousePosition();
					currentUser = users.get(table.getSelectedRow());
						popUpMenu.show(Users.this,
								(int) p.getX() + 1, (int) p.getY() + 1);
				}
				super.mousePressed(e);
			}
		});
		
	}
}
