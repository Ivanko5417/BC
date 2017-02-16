package main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import panels.Common;
public class MainThread extends Thread {
	public Timer mainTm;
	public static void refreshTable(int Type) {
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
		/*mainTm = new Timer(1500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				refreshTable(User.Type);
			}
		});
		mainTm.start();
		while(!Thread.currentThread().isInterrupted())
		{
			System.out.println("a");
			refreshTable(User.Type);
			try {
				sleep(1500);
			} catch (InterruptedException e) {
				System.out.println("Поток прерван");
				e.printStackTrace();
			}
		}*/
	}
}
