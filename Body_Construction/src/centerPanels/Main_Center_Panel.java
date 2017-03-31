package centerPanels;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import panels.Common;
import table.ColorRenderer;
import table.HeaderColorRenderer;
import table.MainTableModel;
public class Main_Center_Panel extends JPanel {
	Vector<String> headerVect = new Vector<String>();
	protected JTable table;
	protected JScrollPane scrollPane;
	protected MainTableModel mod;
	public void refreshTable() {
		table.revalidate();
		table.repaint();
		mod.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				System.out.println("d");
			}
		});
	}
	public void revalidate(Vector<String> header) {
		revalidate(header, false);
	}
	public void revalidate(Vector<String> header, boolean isCellEditable) {
		mod = new MainTableModel(header, isCellEditable);
		table.setModel(mod);
		for (int i = 0; i < mod.getColumnCount(); i++) {
			table.getColumn(mod.getColumnName(i))
					.setCellRenderer(new ColorRenderer());
		}
		table.getTableHeader().setDefaultRenderer(new HeaderColorRenderer());
	}
	public void clearSelection() {
		table.clearSelection();
	}
	public Main_Center_Panel() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		headerVect.add("Номер");
		headerVect.add("Звонильщик");
		headerVect.add("Доставщик");
		headerVect.add("Тренер");
		headerVect.add("Зал");
		headerVect.add("Вк аккаунта");
		headerVect.add("Имя");
		headerVect.add("Номер");
		headerVect.add("Вк клиента");
		headerVect.add("Дата");
		headerVect.add("Адресс");
		headerVect.add("Комментарий");
		headerVect.add("Статус");
		headerVect.add("Цена");
		headerVect.add("Тип");
		table = new JTable();
		table.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 67) {
					if (table.getSelectedRow() != -1
							&& table.getSelectedRow() != -1) {
						String s = Common.clients.get(table.getSelectedRow())
								.get(table.getSelectedColumn());
						Toolkit.getDefaultToolkit().getSystemClipboard()
								.setContents(new StringSelection(s), null);
					}
					System.out.println("asd");
					System.out.println(table.getSelectedColumn());
				}
			}
		});
		table.setSelectionMode(0);
		scrollPane = new JScrollPane(table);
		scrollPane.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane, BorderLayout.CENTER);
	}
}
