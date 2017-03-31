package frames;

import java.awt.LayoutManager;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import static panels.Common.clients;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

import main.Client;
import main.Constants;
import main.Functions;
import main.SQL;
import table.ColorRenderer;
import table.HeaderColorRenderer;
import table.MainTableModel;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mysql.fabric.xmlrpc.base.Array;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class FindNumber extends JFrame {
	private JTable table;
	JPanel mainPanel = new JPanel();
	protected JScrollPane scrollPane;
	protected MainTableModel mod;
	ArrayList<Client> numbers = new ArrayList<Client>();
	public FindNumber() {
		setSize(1000,150);
		setTitle("Поиск номера");
		mainPanel.setLayout(new BorderLayout(0, 0));
		JButton btnOk = new JButton("\u041E\u041A");
		JPanel headPanel = new JPanel();
		mainPanel.add(headPanel, BorderLayout.NORTH);
		headPanel.setLayout(new BorderLayout(0, 0));
		
		JFormattedTextField txtNumber = new JFormattedTextField(Constants.NUMBER_MASK);
		txtNumber.setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);
		txtNumber.setFont(Constants.GENERAL_LABEL_FONT);
		txtNumber.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnOk.doClick();
			}
		});
		headPanel.add(txtNumber);
		
		JLabel lblNumber = new JLabel("\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u043D\u043E\u043C\u0435\u0440");
		lblNumber.setFont(Constants.GENERAL_LABEL_FONT);
		headPanel.add(lblNumber, BorderLayout.WEST);
		
		
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Connection connect = null;
				mod.getDataVector().clear();
				try {
					connect = DriverManager.getConnection(Constants.URL, Constants.connInfo);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				String query = "SELECT * FROM `"+Constants.NamesOfTables.NUMBERS+"` WHERE `Number` = '"+txtNumber.getText()+"'";
				ResultSet rs = SQL.doSQL(query, connect);
				int i,count = 0;
				try {
					while(rs.next())
					{
						Client cl = new Client();
						cl.setId(rs.getInt("id"));
						cl.setSource(rs.getString("From"));
						cl.setSpam(rs.getString("Spam"));
						cl.setCall(rs.getString("Call"));
						cl.setCourier(rs.getString("Courier"));
						cl.setTrainer(rs.getString("Trainer"));
						cl.setGym(rs.getString("Gym"));
						cl.setName(rs.getString("Name_Client"));
						cl.setNumber(rs.getString("Number"));
						cl.setComment(rs.getString("Comment"));
						cl.setStatus(rs.getInt("Status"));
						numbers.add(cl);
						count++;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				int n;
				if (count == 0) {
					Vector<String> newRow = new Vector<String>();
					mod.addRow(newRow);
					mod.getDataVector().clear();
				}
				else
				{
					Collections.sort(numbers);
					System.out.println(count);
					for( i = 0; i < count; i++)
					{
						if(numbers.get(i).getStatus() == Constants.TypesOfClient.SINK)
							break;

						Vector<String> newRow = new Vector<String>();
						newRow.add(numbers.get(i).getSource());
						newRow.add(numbers.get(i).getSpam());
						newRow.add(numbers.get(i).getCall());
						newRow.add(numbers.get(i).getCourier());
						newRow.add(numbers.get(i).getTrainer());
						newRow.add(numbers.get(i).getGym());
						newRow.add(numbers.get(i).getName());
						newRow.add(numbers.get(i).getNumber());
						newRow.add(numbers.get(i).getComment());
						newRow.add(Functions.getStatus(numbers.get(i).getStatus()));
						mod.addRow(newRow);
					}
				}
				SQL.closeConnect(connect);
				table.revalidate();
				table.repaint();
			}
		});
		headPanel.add(btnOk, BorderLayout.EAST);
		
		table = new JTable();
		mod = new MainTableModel(new String[] {
				"\u0418\u0441\u0442\u043E\u0447\u043D\u0438\u043A", "\u0421\u043F\u0430\u043C\u0449\u0438\u043A", "\u0417\u0432\u043E\u043D\u0438\u043B\u044C\u0449\u0438\u043A", "\u041A\u0443\u0440\u044C\u0435\u0440", "\u0422\u0440\u0435\u043D\u0435\u0440", "\u0417\u0430\u043B", "\u0418\u043C\u044F", "\u041D\u043E\u043C\u0435\u0440",  "\u041A\u043E\u043C\u043C\u0435\u043D\u0442\u0430\u0440\u0438\u0439","\u0421\u0442\u0430\u0442\u0443\u0441"
			});
		table.setModel(mod);
		for (int i = 0; i < mod.getColumnCount(); i++) {
			table.getColumn(mod.getColumnName(i))
					.setCellRenderer(new ColorRenderer());
		}
		table.getTableHeader().setDefaultRenderer(new HeaderColorRenderer());

		scrollPane = new JScrollPane(table);
		scrollPane.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		add(mainPanel);
	}
}
