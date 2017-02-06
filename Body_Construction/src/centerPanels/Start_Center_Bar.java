package centerPanels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Start_Center_Bar extends JPanel {
	
	public Start_Center_Bar()
	{
		setBackground(Color.WHITE);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		JLabel lblStart1 = new JLabel("Выберете пункт меню");
		lblStart1.setForeground(Color.GRAY);
		lblStart1.setFont(new Font("Arial", 2, 25));
		lblStart1.setAlignmentY(Component.CENTER_ALIGNMENT);
		lblStart1.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lblStart1);
		JLabel lblStart2 = new JLabel(" из списка слева");
		lblStart2.setForeground(Color.GRAY);
		lblStart2.setFont(new Font("Arial", 2, 25));
		lblStart2.setAlignmentY(Component.CENTER_ALIGNMENT);
		lblStart2.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lblStart2);
	}
}
