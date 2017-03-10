package centerPanels;

import static panels.Common.clients;

import java.util.Vector;

import main.Constants;
import table.MainTableModel;

public class Sink extends Main_Center_Panel{
	public void refreshTable() {
		int column = table.getSelectedColumn(), row = table.getSelectedRow();
		mod.getDataVector().clear();
		
		for(int i = 0; i < clients.size(); i++)
		{
			if(clients.get(i).getStatus()== Constants.TypesOfClient.SINK)
			{
				Vector<String> newRow = new Vector<String>();
				newRow.add(clients.get(i).getCall());
				newRow.add(clients.get(i).getTrainer());
				newRow.add(clients.get(i).getGym());
				newRow.add(clients.get(i).getAccountSpam());
				newRow.add(clients.get(i).getName());
				newRow.add(clients.get(i).getNumber());
				newRow.add(clients.get(i).getAccountClient());
				newRow.add(clients.get(i).getDate());
				newRow.add(clients.get(i).getAddress());
				newRow.add(clients.get(i).getComment());
				mod.addRow(newRow);
			}
		}
		if (column > -1 && row > -1) {
			table.setColumnSelectionInterval(column, column);
			table.setRowSelectionInterval(row, row);
		}
		super.refreshTable();
	}
	public Sink()
	{
		Vector<String> headerVect = new Vector<String>();
		headerVect.add("Звонильщик");
		headerVect.add("Тренер");
		headerVect.add("Зал");
		headerVect.add("Вк Спамщика");
		headerVect.add("Имя");
		headerVect.add("Номер");
		headerVect.add("Вк клиента");
		headerVect.add("Дата");
		headerVect.add("Адресс");
		headerVect.add("Комментарий");
		revalidate(headerVect);
	}
}
