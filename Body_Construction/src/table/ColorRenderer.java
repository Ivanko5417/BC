package table;
import java.awt.Color;
import java.awt.Component;
import java.awt.SystemColor;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
public class ColorRenderer extends JLabel implements TableCellRenderer {
	public static final int BT = 2;
	private static final int SBT = 1;
	public ColorRenderer() {
		setBorder(BorderFactory.createEmptyBorder(BT, BT, BT, BT));
		setOpaque(true);
	}
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		String s = (String) value;
		setHorizontalAlignment(CENTER);
		if (s != null)
			if (s.equals("Пробная")) {
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
			} else if (s.equals("Слив")) {
				setBackground(Color.RED);
				setForeground(Color.WHITE);
			} else {

					setBackground(Color.WHITE);
					setForeground(Color.BLACK);
			}
		
		if (s.length() >= 4
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
		return this;
	}
}