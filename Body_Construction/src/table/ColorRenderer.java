package table;
import java.awt.Color;
import java.awt.Component;
import java.awt.SystemColor;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import main.Constants;
public class ColorRenderer extends JLabel implements TableCellRenderer {
	public static final int BT = 2;
	private static final int SBT = 1;
	public ColorRenderer() {
		setBorder(BorderFactory.createEmptyBorder(BT, BT, BT, BT));
		setOpaque(true);
	}
	private boolean equals(String s, String s1) {
		if (s.length() >= s1.length()
				&& s.substring(s.length() - s1.length(), s.length()).equals(s1))
		{

			setText(s.substring(0, s.length()- s1.length()));
			return true;
		}
		else
			return false;
	}
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		String s = (String) value;
		setHorizontalAlignment(CENTER);
		if (s != null)
			if (s.equals("Пробная(А)") || s.equals("Пробная(П)") || s.equals("Пробная(Н)")) {
				setBackground(Color.BLUE);
				setForeground(Color.WHITE);
			} else if (s.equals("Платная")) {
				setBackground(Color.GREEN);
				setForeground(Color.WHITE);
			} else if (s.equals("Звон")) {
				setBackground(Color.YELLOW);
				setForeground(Color.BLACK);
			} else if (s.equals("Спам")) {
				setBackground(Color.GRAY);
				setForeground(Color.BLACK);
			} else if (s.equals("Доставка")) {
				setBackground(new Color(128, 57, 124));
				setForeground(Color.WHITE);
			} else if (s.equals("Назначить пробную")) {
				setBackground(Color.MAGENTA);
				setForeground(Color.WHITE);
			} else if (s.equals("Расписание")) {
				setBackground(Color.ORANGE);
				setForeground(Color.BLACK);
			} else if (s.equals("Слив")) {
				setBackground(Color.RED);
				setForeground(Color.WHITE);
			} else {

					setBackground(Color.WHITE);
					setForeground(Color.BLACK);
			}
		
		if (s!= null && s.length() >= 4
				&& s.substring(s.length() - 4, s.length()).equals("time")) {
			setBackground(Color.LIGHT_GRAY);
			setText(s.substring(0, s.length() - 4));
		} else if (s.length() >= 5
				&& s.substring(s.length() - 5, s.length()).equals("money")) {
			setBackground(Color.GREEN);
			setForeground(Color.BLACK);
			setText(s.substring(0, s.length() - 5));
		} else if (s.length() >= 5
				&& s.substring(s.length() - 5, s.length()).equals("first")) {
			setBackground(Color.BLUE);
			setForeground(Color.WHITE);
			setText(s.substring(0, s.length() - 5));
		} else if (s.length() >= 6
				&& s.substring(s.length() - 6, s.length()).equals("client")) {
			setBackground(Color.ORANGE);
			setForeground(Color.BLACK);
			setText(s.substring(0, s.length() - 6));
		} else
			setText(s);
		if (isSelected) {
			setForeground(SystemColor.textHighlightText);
			setBackground(SystemColor.textHighlight);
			setBorder(hasFocus
					? BorderFactory.createLineBorder(SystemColor.BLACK, SBT)
					: BorderFactory.createEmptyBorder(BT, BT, BT, BT));
		} else {
			setBorder(BorderFactory.createEmptyBorder());
		}
		
		
		if(equals(s, Constants.NameOfColors.RED)) 
		{
			setBackground(Color.RED);
		}else
			if(equals(s, Constants.NameOfColors.YELLOW)) 
			{
				setBackground(Color.YELLOW);
			}else
				if(equals(s, Constants.NameOfColors.GREEN)) 
				{
					setBackground(Color.GREEN);
				}else
					if(equals(s, Constants.NameOfColors.BLUE)) 
					{
						setBackground(Color.BLUE);
					}else
						if(equals(s, Constants.NameOfColors.WHITE)) 
						{
							setBackground(Color.WHITE);
						}
		return this;
	}
}