package table;
import java.util.Vector;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
public class MainTableModel implements TableModel {
	private final String[] colNames;
	private Vector<Vector<String>> data = new Vector<Vector<String>>();
	private final int column;
	public MainTableModel(String[] columnNames) {
        colNames = columnNames;
        column = columnNames.length;
    }
	public MainTableModel(Vector<String> columnNames) {
    	colNames = new String[columnNames.size()];
        for(int i = 0; i < columnNames.size(); i++)
        	colNames[i] = columnNames.get(i);
        column = columnNames.size();
    }
	public int getRowCount() {
		return data.size();
	}
	public int getColumnCount() {
		return colNames.length;
	}
	public String getColumnName(int columnIndex) {
		validateColumn(columnIndex);
		return colNames[columnIndex];
	}
	public Class<?> getColumnClass(int columnIndex) {
		validateColumn(columnIndex);
		return String.class;
	}
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	public void addRow(Vector<String> newRow) {
		if(newRow.size() > column)
			System.out.println("Лажа с колонками");
		for(int i = newRow.size(); i < column; i++)
			newRow.add("");
		data.add(newRow);
	}
	public Vector<Vector<String>> getDataVector() {
		return data;
	}
	public String getValueAt(int rowIndex, int columnIndex) {
		validateColumn(columnIndex);
		validateRow(rowIndex);
		return data.get(rowIndex).get(columnIndex);
	}
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	}
	public void addTableModelListener(TableModelListener l) {
		
	}
	public void removeTableModelListener(TableModelListener l) {
	}
	private void validateColumn(int columnIndex) {
		if (columnIndex < 0 || columnIndex >= column)
			throw new ArrayIndexOutOfBoundsException(
					"Invalid column index: " + columnIndex);
	}
	private void validateRow(int rowIndex) {
		if (rowIndex < 0 || rowIndex > data.size())
			throw new ArrayIndexOutOfBoundsException(
					"Invalid count of column: " + rowIndex);
	}
}
