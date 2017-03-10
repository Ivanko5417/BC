package table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class HeaderColorRenderer extends DefaultTableCellRenderer {
	public Component getTableCellRendererComponent(
			JTable table, Object value, boolean isSelected,
				boolean hasFocus, int row, int column) 
		{
			JLabel label =
				(JLabel) super.getTableCellRendererComponent(
				 	table, value, isSelected, hasFocus,
						row, column);
			label.setBackground(Color.LIGHT_GRAY);
			label.setForeground(Color.BLACK);
			label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			label.setFont(new java.awt.Font("Arial", Font.BOLD, 13));
			label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			return label;
		}

}
