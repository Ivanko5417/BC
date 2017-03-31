package centerPanels;
import static main.Constants.URL;
import static main.Constants.connInfo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.Client;
import table.ColorRenderer;
import main.Constants;
import main.Constants.TypesOfClient;
import main.Functions;
import main.SQL;
import main.User;
import panels.Common;
import panels.Left_Bar_Trainer;
import table.MainTableModel;
public class Schedule extends Main_Center_Panel {
	ArrayList<Client> clients = new ArrayList<Client>();
	Calendar dayScheldule = Calendar.getInstance();
	public static Connection connect1, connect2;
	public JPanel headerPanel = new JPanel();
	private boolean isEnable = false;
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.red);
		g.drawRect(100, 100, 100, 100);
	}
	public ArrayList<Client> getClients() {
		return clients;
	}
	public boolean CheckToday()
	{
		if ((dayScheldule.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance()
				.get(Calendar.DAY_OF_MONTH))
				&& (dayScheldule.get(Calendar.MONTH) == Calendar.getInstance()
						.get(Calendar.MONTH))
				&& (dayScheldule.get(Calendar.YEAR) == Calendar.getInstance()
						.get(Calendar.YEAR)))
		return true;
		else
			return false;
	}
	public void refreshTable() {
		int column = table.getSelectedColumn(), row = table.getSelectedRow();
		mod.getDataVector().clear();
		clients.clear();
		try {
			connect1 = DriverManager.getConnection(URL, connInfo);
			connect2 = DriverManager.getConnection(URL, connInfo);
			String query = "";
			switch (User.CurrentType) {
				case Constants.TypesOfUsers.CALL :
					query = "SELECT * FROM `" + Constants.NamesOfTables.NUMBERS
							+ "` WHERE `Call`='" + User.CurrentUser0
							+ "' AND `Status`=" + Constants.TypesOfClient.CALL;
					break;
				case Constants.TypesOfUsers.TRAINER :
					query = "SELECT * FROM `" + Constants.NamesOfTables.NUMBERS
							+ "` WHERE `Trainer`='" + User.CurrentUser0
							+ "' AND (`Status`= "
							+ Constants.TypesOfClient.TRIAL_PASS
							+ " OR `Status`= "
							+ Constants.TypesOfClient.TRIAL_TRIAL
							+ " OR `Status`= "
							+ Constants.TypesOfClient.TRIAL_NOTHING
							+ " OR `Status` = " + Constants.TypesOfClient.PAY
							+ " OR `Status` = " + Constants.TypesOfClient.CLIENT
							+ ")";
					break;
				case Constants.TypesOfUsers.COURIER :
					query = "SELECT * FROM `" + Constants.NamesOfTables.NUMBERS
							+ "` WHERE `Courier`='" + User.CurrentUser0 + "' ";
					break;
			}
			ResultSet rsClients = SQL.doSQL(query, connect1);
			int i = 0;
			if (rsClients.first())
				do {
					String queryDates;
					switch (User.CurrentType) {
						case Constants.TypesOfUsers.COURIER :
							queryDates = "SELECT * FROM `"
									+ Constants.NamesOfTables.DATES
									+ "` WHERE `Client_id`="
									+ rsClients.getInt("id") + " AND `Type` = "
									+ Constants.TypesOfDates.DELIVERY
									+ " AND `State` = "
									+ Constants.StatesOfDates.WAIT
									+ "  ORDER BY `Date`";
							break;
						default :
							queryDates = "SELECT * FROM `"
									+ Constants.NamesOfTables.DATES
									+ "` WHERE `Client_id`="
									+ rsClients.getInt("id")
									+ " ORDER BY `Date`";
							break;
					}
					ResultSet rsDates = SQL.doSQL(queryDates, connect2);
					if (rsDates.last()) {
						String dateString0 = rsDates.getString("Date");
						String datesString1 = dateString0.substring(0, 10);
						if (datesString1.equals(
								Common.getDateBase(dayScheldule.getTime()))) {
							Client cl = new Client();
							cl.setLastDate(Common.getTime(dateString0),
									rsDates.getInt("id"));
							cl.setId(rsClients.getInt("id"));
							cl.setTrainer(rsClients.getString("Trainer"));
							cl.setGym(rsClients.getString("Gym"));
							cl.setAccountSpam(rsClients.getString("Vk_Spam"));
							cl.setName(rsClients.getString("Name_Client"));
							cl.setNumber(rsClients.getString("Number"));
							cl.setAccountClient(
									rsClients.getString("Vk_Client"));
							cl.setDate(rsClients.getString("Date"));
							cl.setAddress(rsClients.getString("Address"));
							cl.setComment(rsClients.getString("Comment"));
							cl.setStatus(rsClients.getInt("Status"));
							cl.setNumberDates(rsDates.getInt("Number"));
							cl.setLastDateId(rsDates.getInt("id"));
							cl.setClient(rsClients.getBoolean("IsClient"));
							if (cl.isClient()) {
								Functions.getClientInf(cl);
							}
							clients.add(cl);
							i++;
						}
					}
				} while (rsClients.next());
			if (i == 0) {
				Vector<String> newRow = new Vector<String>();
				mod.addRow(newRow);
				mod.getDataVector().clear();
			} else {
				Collections.sort(clients);
				for (i = 0; i < clients.size(); i++) {
					Vector<String> newRow = new Vector<String>();
					if (Functions.isTimeuser(User.CurrentType)) {
						newRow.add(clients.get(i).getLastDate());
					}
					newRow.add(Functions.getStatus(clients.get(i).getStatus()));
					newRow.add(clients.get(i).getName());
					newRow.add(clients.get(i).getNumber());
					if (User.CurrentType == Constants.TypesOfUsers.TRAINER) {
						newRow.add(clients.get(i).getNumberTrain() + "/"
								+ clients.get(i).getCountOfTrain());
					}
					if (User.CurrentType == Constants.TypesOfUsers.COURIER) {
						newRow.add(clients.get(i).getAddress());
					}
					newRow.add(clients.get(i).getComment());
					mod.addRow(newRow);
				}
			}
		} catch (SQLException e) {
			System.out.println("Проблема с БД. Schedule.java");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		if (column > -1 && row > -1 && row <= table.getRowCount()) {
			table.setColumnSelectionInterval(column, column);
			table.setRowSelectionInterval(row, row);
		}
		super.refreshTable();
	}
	private void setLabelDay(JLabel lblDay) {
		if ((dayScheldule.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance()
				.get(Calendar.DAY_OF_MONTH))
				&& (dayScheldule.get(Calendar.MONTH) == Calendar.getInstance()
						.get(Calendar.MONTH))
				&& (dayScheldule.get(Calendar.YEAR) == Calendar.getInstance()
						.get(Calendar.YEAR)))
			lblDay.setText("Сегодня");
		else
			lblDay.setText(Common.getDate(dayScheldule.getTime()));
	}
	public Schedule() {
		headerPanel.setLayout(new BorderLayout());
		JButton btnBackDay = new JButton("Назад");
		JLabel lblDay = new JLabel("Сегодня");
		btnBackDay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dayScheldule.set(Calendar.DAY_OF_MONTH,
						dayScheldule.get(Calendar.DAY_OF_MONTH) - 1);
				setLabelDay(lblDay);
				new Thread() {
					public void run() {
						table.clearSelection();
						refreshTable();
					};
				}.start();
			}
		});
		JButton btnNextDay = new JButton("Вперед");
		btnNextDay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dayScheldule.set(Calendar.DAY_OF_MONTH,
						dayScheldule.get(Calendar.DAY_OF_MONTH) + 1);
				setLabelDay(lblDay);
				new Thread() {
					public void run() {
						table.clearSelection();
						refreshTable();
					};
				}.start();
			}
		});
		lblDay.setHorizontalAlignment(SwingConstants.CENTER);
		lblDay.setFont(Constants.GENERAL_LABEL_FONT);
		headerPanel.add(btnBackDay, BorderLayout.LINE_START);
		headerPanel.add(lblDay, BorderLayout.CENTER);
		headerPanel.add(btnNextDay, BorderLayout.LINE_END);
		add(headerPanel, BorderLayout.NORTH);
		headerVect.clear();
		if (Functions.isTimeuser(User.CurrentType)) {
			headerVect.add("Время");
		}
		headerVect.add("Статус");
		headerVect.add("Имя");
		headerVect.add("Номер");
		if (User.CurrentType == Constants.TypesOfUsers.TRAINER) {
			headerVect.add("Номер тренирвоки");
		}
		if (User.CurrentType == Constants.TypesOfUsers.COURIER) {
			headerVect.add("Адресс");
		}
		headerVect.add("Комментарий");
		revalidate(headerVect);
		switch (User.CurrentType) {
			case Constants.TypesOfUsers.CALL :
				table.addMouseListener(new MouseListener() {
					@Override
					public void mouseReleased(MouseEvent arg0) {
					}
					@Override
					public void mousePressed(MouseEvent e) {
						if(CheckToday())
						{
							if (e.getButton() == MouseEvent.BUTTON3) {
								Point point = e.getPoint();
								int column = table.columnAtPoint(point);
								int row = table.rowAtPoint(point);
								table.setColumnSelectionInterval(column, column);
								table.setRowSelectionInterval(row, row);
								Point p = scrollPane.getMousePosition();
								Common.currectNumber = clients
										.get(table.getSelectedRow());
								Common.leftPanelCall.popup.show(Schedule.this,
										(int) p.getX() + 1, (int) p.getY() +(int) headerPanel
										.getPreferredSize()
										.getHeight());
							}
							if (e.getClickCount() >= 2
									&& e.getClickCount() % 2 == 0) {
								Common.currectNumber = clients
										.get(table.getSelectedRow());
								Common.callPanel.nextCall(
										(int) (scrollPane.getMousePosition()
												.getX()),
										(int) (scrollPane.getMousePosition().getY())
												+ (int) headerPanel
														.getPreferredSize()
														.getHeight());
							}
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
				break;
			case Constants.TypesOfUsers.COURIER :
				table.addMouseListener(new MouseListener() {
					@Override
					public void mouseReleased(MouseEvent arg0) {
					}
					@Override
					public void mousePressed(MouseEvent e) {
						if (CheckToday() && e.getButton() == MouseEvent.BUTTON3) {
							Point point = e.getPoint();
							int column = table.columnAtPoint(point);
							int row = table.rowAtPoint(point);
							table.setColumnSelectionInterval(column, column);
							table.setRowSelectionInterval(row, row);
							Point p = scrollPane.getMousePosition();
							Common.currectNumber = clients
									.get(table.getSelectedRow());
							Common.leftPanelCourier.popup.show(Schedule.this,
									(int) p.getX() + 1,
									(int) p.getY() + 1 + (int) headerPanel
											.getPreferredSize().getHeight());
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
				break;
			case Constants.TypesOfUsers.TRAINER :
				table.addMouseListener(Common.leftPanelTrainer.tableClick);
		}
	}
	public boolean isEnable() {
		return isEnable;
	}
	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}
}
