package centerPanels;

import static main.Constans.PASSWORD;
import static main.Constans.URL;
import static main.Constans.USER_NAME;
import static panels.Common.clients;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import main.Client;
import main.SQL;
import main.User;

public class Sink extends JPanel {
	private JTable table;
	private DefaultTableModel mod;
	public void refreshTable() {
		int column = table.getSelectedColumn(), row = table.getSelectedRow();
		mod.getDataVector().clear();
		
		for(int i = 0; i < clients.length; i++)
		{
			if(clients[i].getStatus().equals("10"))
			{
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
				table.getSelectedRow();
				table.getSelectedColumn();
				mod.addRow(newRow);
			}
		}
		if (column > -1 && row > -1) {
			table.setColumnSelectionInterval(column, column);
			table.setRowSelectionInterval(row, row);
		}
	}
	public Sink()
	{
		setLayout(new BorderLayout());
		Vector<String> headerVect = new Vector<String>();
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
