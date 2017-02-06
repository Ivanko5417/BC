package main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import panels.Common;
public class MainThread extends Thread {
	public Timer mainTm;
	private void refreshTable(int Type) {
		switch (Type) {
			case 0 :
				Common.clinetsPanel.refreshTable();
				break;
			case 1 :
				Common.callfreePanel.refreshTable();
				Common.callPanel.refreshTable();
				break;
			case 3 :
				Common.schedulePanel.refreshTable();
				Common.clientsTrainerPanel.refreshTable();
				break;
			default :
				break;
		}
		Common.sinkPanel.refreshTable();
	}
	@Override
	public void run() {
		super.run();
		refreshTable(User.Type);
		mainTm = new Timer(1500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				refreshTable(User.Type);
			}
		});
		mainTm.start();
	}
}
