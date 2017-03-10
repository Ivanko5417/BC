package panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Left_Bar_Spam extends Left_Bar_Main {
	
	public Left_Bar_Spam()
	{
		JButton btnClients = new JButton("База телефонов");
		btnClients.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnClients.setForeground(new Color(194,26,12));
		btnClients.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Common.setClients();
			}
		});
		add(btnClients);
		add(btnSettings);
		add(btnSink);
		add(btnExit);
	}
}
