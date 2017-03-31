package table;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import main.Client;
import main.Constants;
import main.Functions;
import main.SQL;
import main.User;
import panels.Common;
public class MainTableModel implements TableModel {
	private final String[] colNames;
	private Vector<Vector<String>> data = new Vector<Vector<String>>();
	private final int column;
	private boolean isCellEditable = false;
	public MainTableModel(String[] columnNames) {
        colNames = columnNames;
        column = columnNames.length;
    }
	public MainTableModel(String[] columnNames, boolean isCellEditable) {
        colNames = columnNames;
        column = columnNames.length;
        this.isCellEditable = isCellEditable;
    }
	public MainTableModel(Vector<String> columnNames) {
    	colNames = new String[columnNames.size()];
        for(int i = 0; i < columnNames.size(); i++)
        	colNames[i] = columnNames.get(i);
        column = columnNames.size();
    }
	public MainTableModel(Vector<String> columnNames, boolean isCellEditable) {
    	colNames = new String[columnNames.size()];
        for(int i = 0; i < columnNames.size(); i++)
        	colNames[i] = columnNames.get(i);
        column = columnNames.size();
        this.isCellEditable = isCellEditable;
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
		if(isCellEditable)
		if(!getColumnName(columnIndex).equals("Номер"))
		return isCellEditable; 
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
		edit(aValue,rowIndex,columnIndex);
	}
	private boolean edit(Object aValue,int rowIndex,int columnIndex)
	{
		String value = (String)aValue;
		
		String queryNumbers = "UPDATE `"+Constants.NamesOfTables.NUMBERS+"` SET ", 
				where = " WHERE `id` = "+Common.currectNumber.getId(), queryClients = "UPDATE `"+Constants.NamesOfTables.CLIENTS+"` SET",query;
		
		query = "";
		int color, type;
		switch(getColumnName(columnIndex))
		{
			case "Источник":
				query = queryNumbers+"`From`= '"+value+"'"+where;
				break;
			case "Спам":
				query = queryNumbers+"`Spam`= '"+value+"'"+where;
				break;
			case "Звонильщик":
				query = queryNumbers+"`Call`= '"+value+"'"+where;
				break;
			case "Тренер":
				query = queryNumbers+"`Trainer`= '"+value+"'"+where;
				break;
			case "Имя":
				query = queryNumbers+"`Name_Client`= '"+value+"'"+where;
				break;
			case "Цена":
				query = queryClients+"`Cost`= '"+value+"'"+where;
				break;
			case "Долг":
				query = queryClients+"`Debt`= '"+value+"'"+where;
				break;
			case "Тип тренировки":
				type = Functions.getTypeOfTrain(value);
				if( type != -1)
				query = queryClients+"`TypeOfTrain`= '"+type+"'"+where;
				else
					return false;
				break;
			case "Logo":
				color = Client.getColor(value);
				query = queryClients+"`Logo`= '"+color+"'"+where;
				break;
			case "Пред":
				color = Client.getColor(value);
				query = queryClients+"`PrePay`= '"+color+"'"+where;
				break;
			case "Пиар":
				color = Client.getColor(value);
				query = queryClients+"`PR`= '"+color+"'"+where;
				break;
			case "Отзыв":
				color = Client.getColor(value);
				query = queryClients+"`Opinion`= '"+color+"'"+where;
				break;
				
		}
		Connection connect =  null;
		try {
			connect = DriverManager.getConnection(Constants.URL, Constants.connInfo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQL.doSQLWithoutResult(query, connect);
		SQL.closeConnect(connect);
		data.get(rowIndex).remove(columnIndex);
		data.get(rowIndex).insertElementAt((String)aValue, columnIndex);
		return true;
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
		if (rowIndex < 0 || rowIndex > data.size()){
			throw new ArrayIndexOutOfBoundsException(
					"Invalid count of row: " + rowIndex);}
	}
}
