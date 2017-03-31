package infTables;
import static panels.Common.clients;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
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
import sun.awt.WindowClosingListener;
public class PayMents extends JFrame implements WindowListener {
	private static int id = -1;
	private Client client;
	JTable table;
	DefaultTableModel mod = null;
	Connection connect = null;
	private static PayMents instance = new PayMents();
	class Data implements Comparable<Data> {
		private String Date = "";
		int Count = 0, WhereMoney = -1;
		public Data(String Date, int Count, int WhereMoney) {
			this.Date = Date;
			this.Count = Count;
			this.WhereMoney = WhereMoney;
		}
		public String getDate() {
			return Date;
		}
		public void setDate(String date) {
			Date = date;
		}
		public int getWhereMoney() {
			return WhereMoney;
		}
		public void setWhereMoney(int whereMoney) {
			WhereMoney = whereMoney;
		}
		public int getCount() {
			return Count;
		}
		public void setCount(int count) {
			Count = count;
		}
		@Override
		public String toString() {
			return Date;
		}
		@Override
		public boolean equals(Object obj) {
			return this.toString().equals(obj.toString());
		}
		@Override
		public int compareTo(Data o) {
			return this.toString().compareTo(o.toString());
		}
	}
	private void refreshTable() {
		mod.getDataVector().clear();
		try {
			ArrayList<Data> payments = new ArrayList<Data>();
			connect = DriverManager.getConnection(Constants.URL,
					Constants.connInfo);
			String query = "SELECT * FROM `" + Constants.NamesOfTables.PAYMENTS
					+ "` WHERE `id` = " + getId() + "";
			ResultSet rs = SQL.doSQL(query, connect);
			rs.last();
			int count = rs.getRow();
			int i = 0;
			if (rs.first())
				do {
					payments.add(new Data(rs.getString("Date"),
							rs.getInt("Count"), rs.getInt("Where_Money")));
					i++;
				} while (rs.next());
			if (i == 0) {
				Vector<String> newRow = new Vector<String>();
				mod.addRow(newRow);
				mod.getDataVector().clear();
			} else {
				Collections.sort(payments);
				for (i = 0; i < count; i++) {
					Vector<String> newRow = new Vector<String>();
					newRow.add(payments.get(i).getDate());
					newRow.add("" + payments.get(i).getCount());
					newRow.add(Functions
							.getWhereMoney(payments.get(i).getWhereMoney()));
					mod.addRow(newRow);
				}
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
	private PayMents() {
		getContentPane().setLayout(new BorderLayout());
		getContentPane().setPreferredSize(new Dimension(200, 200));
		Vector<String> header = new Vector<String>();
		header.add("Дата");
		header.add("Кол-во($)");
		header.add("Способо оплаты");
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
	public static PayMents getInstance() {
		return instance;
	}
	public  int getId() {
		return id;
	}
	public void setId(int id) {
		PayMents.id = id;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
}
