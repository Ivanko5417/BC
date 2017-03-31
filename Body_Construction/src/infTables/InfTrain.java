package infTables;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import main.Client;
import main.Constants;
import main.Functions;
import main.SQL;
public class InfTrain extends JFrame implements WindowListener {
	private static int id = -1;
	private Client client;
	JTable table;
	DefaultTableModel mod = null;
	Connection connect = null;
	private static InfTrain instance = new InfTrain();
	class Data implements Comparable<Data> {
		private String Date = "", Time = "";
		private int Type = -1;
		public Data(String Date, String Time, int Type) {
			this.Date = Date;
			this.setTime(Time);
			this.setType(Type);
		}
		public String getDate() {
			return Date;
		}
		public void setDate(String date) {
			Date = date;
		}
		@Override
		public String toString() {
			return Date + Time + Type;
		}
		@Override
		public boolean equals(Object obj) {
			return this.toString().equals(obj.toString());
		}
		@Override
		public int compareTo(Data o) {
			return this.toString().compareTo(o.toString());
		}
		public String getTime() {
			return Time;
		}
		public void setTime(String time) {
			Time = time;
		}
		public int getType() {
			return Type;
		}
		public void setType(int type) {
			Type = type;
		}
	}
	private void refreshTable() {
		
		mod.getDataVector().clear();
		try {
			ArrayList<Data> dates = new ArrayList<Data>();
			connect = DriverManager.getConnection(Constants.URL,
					Constants.connInfo);
			String query = "SELECT * FROM `" + Constants.NamesOfTables.DATES
					+ "` WHERE `Client_id` = " + getId() + " AND State = "
					+ Constants.StatesOfDates.SUCCESSFUL + "  ORDER BY `Date`";
			ResultSet rs = SQL.doSQL(query, connect);
			rs.last();
			int count = rs.getRow();
			int i = 0;
			if (rs.first())
				do {
					String dateTime = rs.getString("Date");
					dates.add(new Data(dateTime.substring(0, 10),
							dateTime.substring(10, 16), rs.getInt("Type")));
					i++;
				} while (rs.next());
			

				Vector<String> firstRow = new Vector<String>();
				firstRow.add(getClient().getDate());
				firstRow.add("");
				firstRow.add("Спам");
				mod.addRow(firstRow);
				Collections.sort(dates);
				for (i = 0; i < count; i++) {
					Vector<String> newRow = new Vector<String>();
					newRow.add(dates.get(i).getDate());
					newRow.add(dates.get(i).getTime());
					newRow.add(Functions.getTypeOfDate(dates.get(i).getType()));
					mod.addRow(newRow);
				}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void show(Client cl) {
		this.setId(cl.getId());
		this.setClient(cl);
		refreshTable();
		setVisible(true);
	}
	private InfTrain() {
		getContentPane().setLayout(new BorderLayout());
		getContentPane().setPreferredSize(new Dimension(220, 200));
		Vector<String> header = new Vector<String>();
		header.add("Дата");
		header.add("Время");
		header.add("Тип");
		mod = new DefaultTableModel(header, 0);
		table = new JTable(mod) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		pack();
		setVisible(true);
	}
	@Override
	public void windowActivated(WindowEvent e) {
	}
	@Override
	public void windowClosed(WindowEvent e) {
	}
	@Override
	public void windowClosing(WindowEvent e) {
		setVisible(false);
	}
	@Override
	public void windowDeactivated(WindowEvent e) {
	}
	@Override
	public void windowDeiconified(WindowEvent e) {
	}
	@Override
	public void windowIconified(WindowEvent e) {
	}
	@Override
	public void windowOpened(WindowEvent e) {
	}
	public static InfTrain getInstance() {
		return instance;
	}
	public  int getId() {
		return id;
	}
	public  void setId(int id) {
		InfTrain.id = id;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
}
