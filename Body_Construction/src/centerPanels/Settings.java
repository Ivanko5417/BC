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
		cbs[0] = new JCheckBox("��������");
		cbs[1] = new JCheckBox("�������");
		cbs[2] = new JCheckBox("����������");
		cbs[3] = new JCheckBox("���������");
		cbs[4] = new JCheckBox("������");
		cbs[5] = new JCheckBox("���");
		cbs[6] = new JCheckBox("�� �������");
		cbs[7] = new JCheckBox("���");
		cbs[8] = new JCheckBox("�����");
		cbs[9] = new JCheckBox("�� �������");
		cbs[10] = new JCheckBox("���� ���������");
		cbs[11] = new JCheckBox("������");
		cbs[12] = new JCheckBox("�����������");
		cbs[13] = new JCheckBox("������");
		cbs[14] = new JCheckBox("����");
		cbs[15] = new JCheckBox("���");
		for(int i = 0; i < 16; i++)
		{
			cbs[i].setBounds(50, 20+i*32, 200, 50);
			add(cbs[i]);
		}
		cbs[15].setLocation(1000, 800);
	}
}
