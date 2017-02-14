package centerPanels;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Settings extends JPanel {

    JScrollPane scrollPane = new JScrollPane(this);
	public Settings()
	{

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(this.getPreferredSize());
		setLayout(null);
		JCheckBox[] cbs = new JCheckBox[16];
		cbs[0] = new JCheckBox("Источник");
		cbs[1] = new JCheckBox("Спамщик");
		cbs[2] = new JCheckBox("Звонильщик");
		cbs[3] = new JCheckBox("Доставщик");
		cbs[4] = new JCheckBox("Тренер");
		cbs[5] = new JCheckBox("Зал");
		cbs[6] = new JCheckBox("Вк спамщик");
		cbs[7] = new JCheckBox("Имя");
		cbs[8] = new JCheckBox("Номер");
		cbs[9] = new JCheckBox("Вк клиента");
		cbs[10] = new JCheckBox("Дата появления");
		cbs[11] = new JCheckBox("Адресс");
		cbs[12] = new JCheckBox("Комментарий");
		cbs[13] = new JCheckBox("Статус");
		cbs[14] = new JCheckBox("Цена");
		cbs[15] = new JCheckBox("Тип");
		for(int i = 0; i < 16; i++)
		{
			cbs[i].setBounds(50, 20+i*32, 200, 50);
			add(cbs[i]);
		}
		cbs[15].setLocation(1000, 800);
	}
}
