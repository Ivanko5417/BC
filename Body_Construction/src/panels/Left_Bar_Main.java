package panels;
import static main.Main.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javafx.scene.shape.Box;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import main.Main;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Left_Bar_Main extends JPanel {
	public JButton btnSettings = new JButton("Настройки");
	public JButton btnSink = new JButton("Слив");
	public JButton btnExit = new JButton("Выход");
	public JPopupMenu popup;
	
	
	@Override
	public Component add(Component a){
		
		super.add(javax.swing.Box.createRigidArea(new Dimension(0, 5)));
		super.add(a);
		return this;
	}
	public Left_Bar_Main()
	{
		setBackground(new Color(91, 57, 38));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JLabel lblMenu = new JLabel("Меню");
		lblMenu.setFont(new Font("Arial", 1, 25));
		lblMenu.setForeground(new Color(168, 118, 201));
		lblMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lblMenu);
		add(javax.swing.Box.createRigidArea(new Dimension(0, 25)));
		btnSettings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Common.setSettings();
			}
		});
		btnSettings.setVisible(false);
		btnSettings.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSettings.setForeground(Color.DARK_GRAY);
		btnSink.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Common.setSink();
			}
		});
		btnSink.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnExit.setForeground(Color.PINK);
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.removeAll();
				frame.add(panelAuth);
				frame.setSize(300, 200);
				panelAuth.repaint();
				frame.revalidate();
				Main.exit();
			}
		});
		btnExit.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
}
