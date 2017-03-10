package panels;

import static panels.Common.clients;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import centerPanels.Call;
import centerPanels.Main_Center_Panel;

public class Left_Bar_Call extends Left_Bar_Main{
	private JButton btnFreeCall = null;
	private JButton btnTodayCall = null;
	private JButton btnCall = null;
	public Left_Bar_Call()
	{
		popup = new JPopupMenu();
		btnFreeCall = new JButton("База свободных номеров");
		btnFreeCall.setAlignmentX(CENTER_ALIGNMENT);
		btnFreeCall.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Common.setFreeCall();
			}
		});
		btnFreeCall.setForeground(new Color(194,26,12));
		btnTodayCall = new JButton("Обзвон");
		btnTodayCall.setAlignmentX(CENTER_ALIGNMENT);
		btnTodayCall.setForeground(Color.RED);
		btnTodayCall.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Common.setSchedule();
			}
		});
		
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
		add(btnTodayCall);
		add(btnCall);
		add(btnSink);
		add(btnExit);
	}
}
