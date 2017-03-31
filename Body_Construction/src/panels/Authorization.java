package panels;


import static main.Constants.DRIVER_NAME;
import static main.Constants.URL;
import static main.Constants.connInfo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javafx.scene.layout.Border;
import main.Constants;
import main.Main;
import main.MainThread;
import main.SQL;
import main.User;

public class Authorization extends JPanel {

	public static Common mainPanel;
	public static MainThread stream;
	private Connection connect = null;
	private Image background, buttonNoActive, buttonActive, buttonClose, buttonHide;

	ArrayList<User> users = new ArrayList<User>();
	private void getUsers()
	{
		users.clear();
		try
		{
			connect = DriverManager.getConnection(URL, connInfo);
			ResultSet rs = SQL.doSQL("SELECT * FROM "+Constants.NamesOfTables.USERS+"", connect);
			while(rs.next())
			{
				if(!rs.getBoolean("Active")) continue;
				User us = new User();
				us.setLogin(rs.getString("Login"));
				us.setPassword(rs.getString("Pass"));
				us.setName_User(rs.getString("Name_User"));
				us.setName0(rs.getString("Name0"));
				us.setType(rs.getInt("Type"));
				users.add(us);
			}
			SQL.closeConnect(connect);
		}catch(Exception e)
		{
			System.out.println("Проблемы с БД. Authorization.java");
			System.out.println(e.getMessage());
		}
		
	}
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(background, 0, 0, null);
	}
	public Authorization()
	{	
		try {
			background = ImageIO.read(new File("res/backgrounds/Background.png"));
			buttonNoActive  = ImageIO.read(new File("res/buttons/ButtonNoActive.png"));
			buttonActive  = ImageIO.read(new File("res/buttons/ButtonActive.png"));
			buttonClose  = ImageIO.read(new File("res/buttons/CloseButton.png"));
			buttonHide  = ImageIO.read(new File("res/buttons/Buthide.png"));
			//background  = ImageIO.read(getClass().getResourceAsStream("background.png"));Buthide.png
		} catch (IOException e1) {
			System.out.println("Не удалось загрузить файл.");
			e1.printStackTrace();
		}
		getUsers();
		//setBackground(new Color(231,198,173));
		setLayout(null);
		JButton btnSubmit = new JButton();
		JButton btnExit = new JButton();
		JButton btnHide = new JButton();
		JTextField txtLogin = new JTextField();
		JPasswordField txtPass= new JPasswordField();
		txtLogin.setBounds(87, 246, 226, 27);
		txtLogin.setFont(new Font("Arial", 0, 20));
		txtLogin.setText("Логин");
		txtLogin.setFocusable(true);
		txtLogin.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				JTextField txtLogin = (JTextField)e.getSource();
				if(txtLogin.getText().equals(""))
				txtLogin.setText("Логин");
			}
			@Override
			public void focusGained(FocusEvent e) {
				JTextField txtLogin = (JTextField)e.getSource();
				if(txtLogin.getText().equals("Логин")) txtLogin.setText("");
			}
		});
		txtPass.setToolTipText("Пароль");
		txtPass.setFont(new Font("Arial", 0, 20));
		txtPass.setBounds(87,286, 226 , 27);
		txtPass.setText("Пароль");
		txtPass.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				JPasswordField txtPass = (JPasswordField)e.getSource();
				if(txtPass.getText().equals(""))
					txtPass.setText("Пароль");
			}
			@Override
			public void focusGained(FocusEvent e) {
				JPasswordField txtPass = (JPasswordField)e.getSource();
				if(txtPass.getText().equals("Пароль")) txtPass.setText("");
			}
		});
		txtPass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnSubmit.doClick();
			}
		});
		btnExit.setBounds(368,0, 32, 32);
		btnExit.setBorder(BorderFactory.createEmptyBorder());
		
		btnExit.setIcon(new ImageIcon(buttonClose));
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		btnHide.setBounds(334,0, 32, 32);
		btnHide.setBorder(BorderFactory.createEmptyBorder());
		
		btnHide.setIcon(new ImageIcon(buttonHide));
		btnHide.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Main.frame.setState(JFrame.ICONIFIED);
			}
		});
		btnSubmit.setBounds(116, 319, 163, 38);
		btnSubmit.setPressedIcon(new ImageIcon(buttonActive));
		btnSubmit.setIcon(new ImageIcon(buttonNoActive));
		btnSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = 0;
				//0 - Login,  1 - Pass, 2 -  Name, 3 - Name0, 4 - Type
				while(!users.get(i).getLogin().equals(""))
				{
					if(users.get(i).getLogin().equals(txtLogin.getText()) && users.get(i).getPassword().equals(txtPass.getText()))
					{
						JOptionPane.showMessageDialog(null, "Здравствуйте " +  users.get(i).getName_User() + ". Приятной работы!");
						User.CurrentUser = users.get(i).getName_User();
						User.CurrentUser0 = users.get(i).getName0();
						User.CurrentType = users.get(i).getType();
						mainPanel = new Common(User.CurrentType);
						Main.frame.remove(Main.panelAuth);
						Main.frame.add(mainPanel);
						Main.frame.revalidate();
						Main.frame.
						setSize(1000, 600);
						stream = new MainThread();
						stream.setDaemon(true);
						stream.start();

						try {
							Class.forName(DRIVER_NAME);
						} catch (ClassNotFoundException e2) {
							e2.printStackTrace();
						}
							
						break;
					}
					else
						i++;
					if(i + 1 == users.size())
					{
						JOptionPane.showMessageDialog(null, "Неверный логин или пароль.", "Ошибка аунтификации", JOptionPane.ERROR_MESSAGE);
						break;
					}
				}
			}
		});
		add(txtPass);
		add(txtLogin);
		add(btnExit);
		add(btnHide);
		add(btnSubmit);
		repaint();
	}
	public static  void startWithoutLogin() {

		Main.frame.remove(Main.panelAuth);
		Main.frame.add(mainPanel);
		Main.frame.revalidate();
		Main.frame.setSize(1000, 600);
		
	}
}
