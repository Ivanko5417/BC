package panels;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import infTables.PayMents;
import main.Constants;
import main.Functions;
import main.SQL;
public class Left_Bar_Admin extends Left_Bar_Main {
	JFrame addUser = new JFrame("Добавить");
	private void initAddUser() {
		int width = 360;
		String[] typeOfUsers = {
				Functions.getTypeOfUser(Constants.TypesOfUsers.SPAM),
				Functions.getTypeOfUser(Constants.TypesOfUsers.CALL),
				Functions.getTypeOfUser(Constants.TypesOfUsers.COURIER),
				Functions.getTypeOfUser(Constants.TypesOfUsers.TRAINER)};
		String[] nameOfGyms = {"БГ", "МИ", "ФР"};
		JPanel panelAddUser = new JPanel();
		panelAddUser.setPreferredSize(new Dimension(width, 320));
		addUser.add(panelAddUser);
		addUser.pack();
		panelAddUser.setLayout(null);
		JLabel lblName_User = new JLabel("Полное имя");
		lblName_User.setBounds(20, 20, 200, 20);
		lblName_User.setFont(Constants.GENERAL_LABEL_FONT);
		JTextField txtUser_Name = new JTextField();
		txtUser_Name.setFont(Constants.GENERAL_LABEL_FONT);
		txtUser_Name.setBounds(20, 50, 150, 30);
		JLabel lblName0 = new JLabel("Ник");
		lblName0.setBounds(190, 20, 100, 20);
		lblName0.setFont(Constants.GENERAL_LABEL_FONT);
		JTextField txtName0 = new JTextField();
		txtName0.setFont(Constants.GENERAL_LABEL_FONT);
		txtName0.setBounds(190, 50, 150, 30);
		JLabel lblType = new JLabel("Тип");
		lblType.setBounds(20, 90, 100, 20);
		lblType.setFont(Constants.GENERAL_LABEL_FONT);
		JComboBox cmType = new JComboBox(typeOfUsers);
		JComboBox cmGym = new JComboBox(nameOfGyms);
		cmGym.setEnabled(false);
		cmType.setFont(Constants.GENERAL_LABEL_FONT.deriveFont(0));
		cmType.setBounds(20, 120, 150, 30);
		cmType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cm = (JComboBox) e.getSource();
				if (cm.getSelectedItem().equals(Functions
						.getTypeOfUser(Constants.TypesOfUsers.TRAINER))) {
					cmGym.setEnabled(true);
				} else
					cmGym.setEnabled(false);
			}
		});
		JLabel lblGym = new JLabel("Зал");
		lblGym.setBounds(190, 90, 100, 20);
		lblGym.setFont(Constants.GENERAL_LABEL_FONT);
		cmGym.setFont(Constants.GENERAL_LABEL_FONT.deriveFont(0));
		cmGym.setBounds(190, 120, 150, 30);
		JLabel lblLogin = new JLabel("Логин");
		lblLogin.setBounds(20, 190, 200, 20);
		lblLogin.setFont(Constants.GENERAL_LABEL_FONT);
		JTextField txtLogin = new JTextField();
		txtLogin.setFont(Constants.GENERAL_LABEL_FONT);
		txtLogin.setBounds(20, 210, 150, 30);
		JLabel lblPass = new JLabel("Пароль");
		lblPass.setBounds(190, 190, 100, 20);
		lblPass.setFont(Constants.GENERAL_LABEL_FONT);
		JTextField txtPass = new JTextField();
		txtPass.setFont(Constants.GENERAL_LABEL_FONT);
		txtPass.setBounds(190, 210, 150, 30);
		JButton btnOk = new JButton("OK");
		btnOk.setSize(100, 40);
		btnOk.setLocation(Functions.getCenterSize(width, btnOk.getWidth()),
				250);
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Connection connect = null;
				try {
					connect = DriverManager.getConnection(Constants.URL,
							Constants.connInfo);
				} catch (SQLException e) {
					System.out.println("Проблема с БД. AddUser");
					e.printStackTrace();
				}
				String gym = " ";
				if (cmGym.isEnabled())
					gym = (String) cmGym.getSelectedItem();
				String query = "INSERT INTO `" + Constants.NamesOfTables.USERS
						+ "`(`Login`, `Pass`, `Name_User`, `Name0`, `Date`, `Gym`, `Type`) VALUES ('" + txtLogin.getText() + "', '"
						+ txtPass.getText() + "', '" + txtUser_Name.getText() + "', '"
						+ txtName0.getText() + "', '"+Common.getDate(Calendar.getInstance().getTime())+"', '" + gym + "', "
						+ Functions.getTypeOfUser(
								(String) cmType.getSelectedItem())
						+ ")";
				SQL.doSQLWithoutResult(query, connect);
				SQL.closeConnect(connect);
				addUser.setVisible(false);
			}
		});
		panelAddUser.add(lblName0);
		panelAddUser.add(lblName_User);
		panelAddUser.add(lblLogin);
		panelAddUser.add(lblPass);
		panelAddUser.add(txtLogin);
		panelAddUser.add(txtPass);
		panelAddUser.add(txtName0);
		panelAddUser.add(txtUser_Name);
		panelAddUser.add(lblType);
		panelAddUser.add(cmType);
		panelAddUser.add(lblGym);
		panelAddUser.add(cmGym);
		panelAddUser.add(btnOk);
	}
	private void initComponents() {
		initAddUser();
	}
	private void addUser() {
		addUser.setVisible(true);
	}
	public Left_Bar_Admin() {
		initComponents();
		
		JButton btnClients = new JButton("Клиенты");
		btnClients.setAlignmentX(CENTER_ALIGNMENT);
		btnClients.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Common.setAdminClients();
			}
		});
		add(btnClients);

		JButton btnTrainers = new JButton("Расписание тренеров");
		btnTrainers.setAlignmentX(CENTER_ALIGNMENT);
		btnTrainers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Common.setScheduleWeek();
			}
		});
		add(btnTrainers);

		JButton btnNumbers = new JButton("Номера");
		btnNumbers.setAlignmentX(CENTER_ALIGNMENT);
		btnNumbers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		JButton btnCreateUser = new JButton("Добавить работника");
		btnCreateUser.setAlignmentX(CENTER_ALIGNMENT);
		btnCreateUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addUser();
			}
		});

		add(btnCreateUser);
		JButton btnUsers = new JButton("Работники");
		btnUsers .setAlignmentX(CENTER_ALIGNMENT);
		btnUsers .addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Common.setUsers();
			}
		});
		add(btnUsers );
		JButton btnStats = new JButton("Поиск");
		btnStats.setAlignmentX(CENTER_ALIGNMENT);
		btnStats.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Common.FindNumber();
			}
		});
		add(btnStats);
		JButton addClient= new JButton("Добавить клиента");
		addClient.setAlignmentX(CENTER_ALIGNMENT);
		addClient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Common.addCl.setVisible(true);
			}
		});
		add(addClient);
		add(btnExit);
	}
}
