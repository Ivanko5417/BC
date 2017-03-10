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
import static panels.Common.leftPanelSpam;
import static panels.Common.leftPanelTrainer;
import static panels.Common.schedulePanel;
import static panels.Common.sinkPanel;
import static panels.Common.leftPanelCourier;

import static panels.Common.settingsPanel;

import javax.swing.JFrame;

import panels.Authorization;


public class Main {
	public static JFrame frame;
	public static Authorization panelAuth = null;
	public static void main(String[] args) {
		Constants.connInfo.setProperty("useSSL","false"); 
		Constants.connInfo.setProperty("verifyServerCertificate","false");
		Constants.connInfo.setProperty("user", USER_NAME);
		Constants.connInfo.setProperty("password", PASSWORD);
		Constants.connInfo.setProperty("useUnicode","true"); 
		Constants.connInfo.setProperty("characterEncoding","cp1251");
		startAauth();
		
		
	}
	
	public static void startAauth()
	{
		frame = new JFrame();
		panelAuth = new Authorization();
		frame.setVisible(true);
		frame.add(panelAuth);
		frame.setSize( 300, 200);
		frame.revalidate();
		frame.setTitle("Body Construction");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocation(100, 100);
		frame.setFocusable(true);
		
	}
	
	public static void exit()
	{
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
