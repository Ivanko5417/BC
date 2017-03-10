package centerPanels;
import static main.Constants.URL;
import static main.Constants.connInfo;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.Client;
import main.Constants;
import main.Functions;
import main.SQL;
import main.User;
import panels.Common;
public class ScheduleWeek extends Main_Center_Panel {
	private ArrayList<Client> clients = new ArrayList<Client>();
	private Connection connect1, connect2;
	private Calendar firstDayOfWeek = Calendar.getInstance();
	private Calendar lastDayOfWeek = Calendar.getInstance();
	/*private Date firstDayOfWeek = new Date(2017, 2, 6);
	private Date lastDayOfWeek = new Date(2017, 2, 12);*/
	private final int startDay = 7, endDay = 22;
	public JPanel headerPanel = new JPanel();
	private String parseTime(int i, int j)
	{
		
		String s = "";
		if (i < 10)
			s = "0" + i;
		else
			s += i;
		s += ":";
		if (j < 10)
			s += "0" + j;
		else
			s += j;
		return s;
	}
	@Override
	public void refreshTable() {
		int column = table.getSelectedColumn(), row = table.getSelectedRow();
		mod.getDataVector().clear();
		clients.clear();
		try {
			connect1 = DriverManager.getConnection(URL, connInfo);
			connect2 = DriverManager.getConnection(URL, connInfo);
			String query = "SELECT * FROM `clients` WHERE `Trainer`='"
					+ User.CurrentUser0 + "' AND (`Status`= "
							+ Constants.TypesOfClient.TRIAL + " OR `Status` = "
							+ Constants.TypesOfClient.PAY + " OR `Status` = "
							+ Constants.TypesOfClient.CLIENT + ")";
			ResultSet rsClients = SQL.doSQL(query, connect1);
			int i = 0;
			if (rsClients.first())
				do {
					String queryDates = "SELECT * FROM `"+Constants.NamesOfTables.DATES+"` WHERE `Client_id`="
							+ rsClients.getInt("id") + " ORDER BY `Date`";
					ResultSet rsDates = SQL.doSQL(queryDates, connect2);
					if (rsDates.last()) {
						String dateString0 = rsDates.getString("Date");
						String dateString1 = dateString0.substring(0, 10);
						if (dateString1.compareTo(
								Common.getDateBase(firstDayOfWeek.getTime())) >= 0
								&& dateString1.compareTo(Common
										.getDateBase(lastDayOfWeek.getTime())) <= 0) {// ПАРСЕР
																			// ДЛЯ
																			// ПРОВЕРКИ
																			// ДАТЫ
																			// В
																			// ТЕЧЕНИИ
																			// НЕДЕЛИ
							Client cl = new Client();
							cl.setLastDate(Common.getTime(dateString0));
							cl.setDay(Integer.parseInt(dateString0.substring(8, 10)));
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
							clients.add(cl);
							i++;
						}
					}
				} while (rsClients.next());
				Collections.sort(clients);
				for (i = startDay; i <= endDay; i++) {
					for (int j = 0; j <= 30; j += 30) {
						Vector<String> newRow = new Vector<String>();
						String s = parseTime(i, j);
						System.out.println(s);
						s+="time";
						newRow.add(s);
						s = s.substring(0, s.length()-4);
						for(int x = 0; x < clients.size(); x++)
						{
							
							String nextTime ="";
							if(j == 0) nextTime = parseTime(i, j+30);
							else
								if(i+1 <= endDay)
								nextTime = parseTime(i+1, j-30);
							if(clients.get(x).getLastDate().compareTo(s) >= 0 &&  clients.get(x).getLastDate().compareTo(nextTime) < 0)
							{
								for(int q = firstDayOfWeek.get(Calendar.DAY_OF_MONTH); q < clients.get(x).getDay(); q++)
									newRow.add("");
								String client = clients.get(x).getName()+ " " + clients.get(x).getNumber(); //добавить номер занятия для тех кто по расписанию (5/8), есть ли у него долг 
								if(clients.get(x).getStatus() == Constants.TypesOfClient.TRIAL)											//для пробной: что куплено?(Ничего, разовая, абонемент)
									client+="first";
								if(clients.get(x).getStatus() == Constants.TypesOfClient.PAY)
									client+="‪money";
								if(clients.get(x).getStatus() == Constants.TypesOfClient.CLIENT)
									client+="‪client";
								newRow.add(client);
								break;
							}
						}
						mod.addRow(newRow);
					}
				}
		} catch (SQLException e) {
			System.out.println("Проблема с БД. Schedule.java");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		if (column > -1 && row > -1) {
			table.setColumnSelectionInterval(column, column);
			table.setRowSelectionInterval(row, row);
		}
		super.refreshTable();
	}
	public ScheduleWeek() {
		firstDayOfWeek.set(Calendar.DAY_OF_WEEK, firstDayOfWeek.getFirstDayOfWeek());
		lastDayOfWeek.set(Calendar.DAY_OF_WEEK, lastDayOfWeek.getFirstDayOfWeek() + 6);
		headerPanel.setLayout(new BorderLayout());
		JButton btnBackDay = new JButton("Назад");
		JLabel lblDay = new JLabel(firstDayOfWeek.getTime() + "-" + lastDayOfWeek.getTime());
		btnBackDay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				firstDayOfWeek.set(Calendar.DAY_OF_MONTH,
						firstDayOfWeek.get(Calendar.DAY_OF_MONTH) - 7);
				lastDayOfWeek.set(Calendar.DAY_OF_MONTH,
						lastDayOfWeek.get(Calendar.DAY_OF_MONTH) - 7);
				
				lblDay.setText(firstDayOfWeek.getTime() + "-" + lastDayOfWeek.getTime());
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

				firstDayOfWeek.set(Calendar.DAY_OF_MONTH,
						firstDayOfWeek.get(Calendar.DAY_OF_MONTH) + 7);
				lastDayOfWeek.set(Calendar.DAY_OF_MONTH,
						lastDayOfWeek.get(Calendar.DAY_OF_MONTH) + 7);
				lblDay.setText(firstDayOfWeek.getTime() + "-" + lastDayOfWeek.getTime());
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
		headerVect.add("Время");
		headerVect.add("ПН");
		headerVect.add("ВТ");
		headerVect.add("СР");
		headerVect.add("ЧТ");
		headerVect.add("ПТ");
		headerVect.add("СБ");
		headerVect.add("ВС");
		revalidate(headerVect);
	}
}
