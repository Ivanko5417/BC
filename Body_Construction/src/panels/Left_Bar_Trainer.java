package panels;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import main.Client;
public class Left_Bar_Trainer extends Left_Bar_Main {
	public JPopupMenu popupTrainer = new JPopupMenu();
	Client cl = null;
	public MouseListener tableClick = new MouseListener() {
		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
		@Override
		public void mousePressed(MouseEvent e) {
			JTable table = (JTable) (e.getSource());
			if (e.getButton() == MouseEvent.BUTTON3) {
				Point point = e.getPoint();
				int column = table.columnAtPoint(point);
				int row = table.rowAtPoint(point);
				table.setColumnSelectionInterval(column, column);
				table.setRowSelectionInterval(row, row);
				cl = Common.clients[table.getSelectedRow()];
				JScrollPane scrollPane = (JScrollPane) (table.getParent()
						.getParent());
				Point p = scrollPane.getMousePosition();
				popupTrainer.show(scrollPane.getParent(), (int) p.getX() + 1, (int) p.getY() + 1);
			
			}
		}
		@Override
		public void mouseExited(MouseEvent arg0) {
		}
		@Override
		public void mouseEntered(MouseEvent arg0) {
		}
		@Override
		public void mouseClicked(MouseEvent arg0) {
		}
	};
	public Left_Bar_Trainer() {
		JMenuItem itemSink = new JMenuItem("Слив");
		popupTrainer.add(itemSink);
		itemSink.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("ЛЛ");
				Common.Sink(cl);
			}
		});
		JButton btnSchedule = new JButton("Расписание");
		btnSchedule.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSchedule.setForeground(new Color(39, 124, 78));
		btnSchedule.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Common.setSchedule();
			}
		});
		add(btnSchedule);
		JButton btnClients = new JButton("Мои клиенты");
		btnClients.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnClients.setForeground(new Color(39, 124, 78));
		btnClients.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Common.setTrainer();
			}
		});
		add(btnClients);
		add(btnSink);
		add(btnExit);
	}
}
