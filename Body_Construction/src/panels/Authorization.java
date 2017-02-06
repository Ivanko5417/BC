package panels;


import static main.Constans.*;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import main.*;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Component;


import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Authorization extends JPanel {

	public static Common mainPanel;
	public static MainThread stream;
	private Connection connect = null;
	private String[][] getUsers()
	{
		String[][] users = null;//0 - Login,  1 - Pass, 2 -  Name, 3 - Name0, 4 - Type
		try
		{
			connect = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
			ResultSet rs = SQL.doSQL("SELECT * FROM users", connect);
			int i = 0;
			rs.last();
			int count = rs.getRow();
			count++;
			users = new String[count][5];
			rs.beforeFirst();
			while(rs.next())
			{
				users[i][0]=rs.getString("Login");
				users[i][1]=rs.getString("Pass");
				users[i][2]=rs.getString("Name_User");
				users[i][3]=rs.getString("Name0");
				users[i][4]=rs.getString("Type");
				i++;				
			}
			SQL.closeConnect(connect);
		}catch(Exception e)
		{
			System.out.println("Проблемы с БД. Authorization.java");
			System.out.println(e.getMessage());
		}
		
		return users;
	}
	public Authorization()
	{	
		setBackground(new Color(231,198,173));
		String[][] users = getUsers();
		setLayout(null);
		JButton btnSubmit = new JButton("Войти");
		JLabel lblLogin = new JLabel("Логин");
		JTextField txtLogin = new JTextField();
		JLabel lblPass = new JLabel("Пароль");
		JPasswordField txtPass= new JPasswordField();
		
		lblLogin.setBounds(121, 5, 58, 24);
		lblLogin.setFont(new Font("Arial",1, 20));
		lblLogin.setForeground(new Color(29, 99, 45));
		
		txtLogin.setBounds(91, 35, 118, 20);
		txtLogin.setFocusable(true	);
		lblPass.setBounds(120, 61, 100, 24);
		lblPass.setFont(new Font("Arial",1, 20));
		lblPass.setForeground(new Color(114,11,157));
		
		txtPass.setBounds(91, 91, 118, 20);
		txtPass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnSubmit.doClick();
			}
		});
		
		btnSubmit.setBounds(116, 122, 68, 23);
		btnSubmit.setForeground(new Color(249, 2, 51));
		btnSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = 0;
				
				while(!users[i][0].equals(""))
				{
					if(users[i][0].equals(txtLogin.getText()) && users[i][1].equals(txtPass.getText()))
					{
						JOptionPane.showMessageDialog(null, "Здравствуйте " +  users[i][2] + ". Приятной работы!");
						User.CurrentUser = users[i][2];
						User.CurrentUser0 = users[i][3];
						User.Type = Integer.parseInt(users[i][4]);
						mainPanel = new Common(User.Type);
						Main.frame.remove(Main.panelAuth);
						Main.frame.add(mainPanel);
						Main.frame.revalidate();
						Main.frame.
						setSize(1000, 600);
						stream = new MainThread();
						stream.setDaemon(true);
						stream.start();
						
						
						break;
					}
					else
						i++;
					if(i + 1 == users.length)
					{
						JOptionPane.showMessageDialog(null, "Неверный логин или пароль.", "Ошибка аунтификации", JOptionPane.ERROR_MESSAGE);
						break;
					}
				}
			}
		});
		
		add(lblLogin);
		add(lblPass);
		add(txtPass);
		add(txtLogin);
		add(btnSubmit);
	}
	public static  void startWithoutLogin() {

		Main.frame.remove(Main.panelAuth);
		Main.frame.add(mainPanel);
		Main.frame.revalidate();
		Main.frame.setSize(1000, 600);
		
	}
}
