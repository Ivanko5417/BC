package main;
import static main.Constants.PASSWORD;
import static main.Constants.USER_NAME;
import static panels.Authorization.mainPanel;
import static panels.Authorization.stream;
import static panels.Common.callPanel;
import static panels.Common.callfreePanel;
import static panels.Common.centerPanel;
import static panels.Common.clientsTrainerPanel;
import static panels.Common.clinetsPanel;
import static panels.Common.leftPanelCall;
import static panels.Common.leftPanelCourier;
import static panels.Common.leftPanelSpam;
import static panels.Common.leftPanelTrainer;
import static panels.Common.schedulePanel;
import static panels.Common.settingsPanel;
import static panels.Common.sinkPanel;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.JFrame;
import javax.swing.text.MaskFormatter;

import frames.addClient;
import panels.Authorization;
public class Main {
	private static int x, y;
	public static JFrame frame;
	public static Authorization panelAuth = null;
	public static void main(String[] args) {
		Constants.connInfo.setProperty("useSSL", "false");
		Constants.connInfo.setProperty("verifyServerCertificate", "false");
		Constants.connInfo.setProperty("user", USER_NAME);
		Constants.connInfo.setProperty("password", PASSWORD);
		Constants.connInfo.setProperty("useUnicode", "true");
		Constants.connInfo.setProperty("characterEncoding", "cp1251");
		String mask = "+375-(##)-###-##-##";
		try {
			Constants.NUMBER_MASK = new MaskFormatter(mask);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Constants.BUTTON_FONT = Font
					.createFont(Font.TRUETYPE_FONT,
							new File("res/fonts/ButtonFont.ttf"))
					.deriveFont(25.0F);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		startAauth();
	}
	public static void startAauth() {
		frame = new JFrame();
		panelAuth = new Authorization();
		frame.setUndecorated(true);
		frame.setVisible(true);
		frame.add(panelAuth);
		// frame.setSize( 300, 200);
		frame.setSize(400, 400);
		frame.revalidate();
		frame.setTitle("Body Construction");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocation(100, 100);
		frame.setFocusable(true);
		frame.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
				x = e.getX();
				y = e.getY();
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		frame.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
			}
			@Override
			public void mouseDragged(MouseEvent e) {
				frame.setLocation(e.getXOnScreen() - x, e.getYOnScreen() - y);
			}
		});
	}
	public static void exit() {
		stream = null;
		leftPanelCall = null;
		leftPanelSpam = null;
		centerPanel = null;
		schedulePanel = null;
		clinetsPanel = null;
		callfreePanel = null;
		mainPanel = null;
		frame.setVisible(false);
		frame = null;
		panelAuth = null;
		callPanel = null;
		leftPanelTrainer = null;
		leftPanelCourier = null;
		clientsTrainerPanel = null;
		sinkPanel = null;
		settingsPanel = null;
		User.CurrentUser = "";
		User.CurrentUser0 = "";
		System.gc();
		startAauth();
	}
}
