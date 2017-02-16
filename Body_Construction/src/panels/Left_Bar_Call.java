package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import main.Main;
import main.MainThread;
import main.User;

public class Left_Bar_Call extends Left_Bar_Main{
	public JButton btnFreeCall = null;
	public JButton btnCall = null;
	public Left_Bar_Call()
	{

		btnFreeCall = new JButton("База свободных номеров");
		btnFreeCall.setAlignmentX(CENTER_ALIGNMENT);
		btnFreeCall.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Common.setFreeCall();
			}
		});
		btnFreeCall.setForeground(new Color(194,26,12));
		btnCall = new JButton("Мои номера");
		btnCall.setAlignmentX(CENTER_ALIGNMENT);
		btnCall.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Common.setCall();
				
			}
		});
		btnCall.setForeground(new Color(169, 13, 221));
		add(btnFreeCall);
		add(btnCall);
		add(btnSettings);
		add(btnSink);
		add(btnExit);
	}
}
