package centerPanels;

import static panels.Common.clients;
import java.util.Vector;

public class Sink extends Main_Center_Panel{
	public void refreshTable() {
		int column = table.getSelectedColumn(), row = table.getSelectedRow();
		mod.getDataVector().clear();
		
		for(int i = 0; i < clients.length; i++)
		{
			if(clients[i].getStatus()== 10)
			{
				Vector<String> newRow = new Vector<String>();
				newRow.add(clients[i].getTrainer());
				newRow.add(clients[i].getGym());
				newRow.add(clients[i].getAccountSpam());
				newRow.add(clients[i].getName());
				newRow.add(clients[i].getNumber());
				newRow.add(clients[i].getAccountClient());
				newRow.add(clients[i].getDate());
				newRow.add(clients[i].getAddress());
				newRow.add(clients[i].getComment());
				table.getSelectedRow();
				table.getSelectedColumn();
				mod.addRow(newRow);
			}
		}
		if (column > -1 && row > -1) {
			table.setColumnSelectionInterval(column, column);
			table.setRowSelectionInterval(row, row);
		}
	}
	public Sink()
	{
	}
}
