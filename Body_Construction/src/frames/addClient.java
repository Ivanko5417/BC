package frames;
import javax.swing.*;
import javax.swing.text.MaskFormatter;

import main.Constants;
import main.Functions;
import main.SQL;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
public class addClient extends JFrame {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_4;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	private JTextField textField_12;
	JComboBox comboBox_1 ;
	public addClient() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		JLabel label = new JLabel("\u0418\u043C\u044F");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		getContentPane().add(label, gbc_label);
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);
		JLabel label_1 = new JLabel("\u041D\u043E\u043C\u0435\u0440");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 1;
		getContentPane().add(label_1, gbc_label_1);
		JFormattedTextField formattedTextField = new JFormattedTextField(
				Constants.NUMBER_MASK);
		GridBagConstraints gbc_formattedTextField = new GridBagConstraints();
		gbc_formattedTextField.insets = new Insets(0, 0, 5, 0);
		gbc_formattedTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_formattedTextField.gridx = 1;
		gbc_formattedTextField.gridy = 1;
		getContentPane().add(formattedTextField, gbc_formattedTextField);
		JLabel label_2 = new JLabel(
				"\u041A\u043E\u043C\u043C\u0435\u043D\u0442\u0430\u0440\u0438\u0439");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 0;
		gbc_label_2.gridy = 2;
		getContentPane().add(label_2, gbc_label_2);
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 2;
		getContentPane().add(textField_1, gbc_textField_1);
		JLabel label_3 = new JLabel(
				"\u0422\u0438\u043F \u0442\u0440\u0435\u043D\u0438\u0440\u043E\u0432\u043A\u0438");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 0;
		gbc_label_3.gridy = 3;
		getContentPane().add(label_3, gbc_label_3);
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(
				new String[]{"\u0421\u0443\u0448\u043A\u0430",
						"\u041C\u0430\u0441\u0441\u0430"}));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 3;
		getContentPane().add(comboBox, gbc_comboBox);
		JLabel label_4 = new JLabel("\u0426\u0435\u043D\u0430");
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.insets = new Insets(0, 0, 5, 5);
		gbc_label_4.gridx = 0;
		gbc_label_4.gridy = 4;
		getContentPane().add(label_4, gbc_label_4);
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 0);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 4;
		getContentPane().add(textField_2, gbc_textField_2);
		JLabel label_5 = new JLabel("\u0414\u043E\u043B\u0433");
		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		gbc_label_5.insets = new Insets(0, 0, 5, 5);
		gbc_label_5.gridx = 0;
		gbc_label_5.gridy = 5;
		getContentPane().add(label_5, gbc_label_5);
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(0, 0, 5, 0);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 1;
		gbc_textField_3.gridy = 5;
		getContentPane().add(textField_3, gbc_textField_3);
		JLabel label_6 = new JLabel(
				"\u041D\u043E\u043C\u0435\u0440 \u0442\u0440\u0435\u043D\u0438\u0440\u043E\u0432\u043A\u0438");
		GridBagConstraints gbc_label_6 = new GridBagConstraints();
		gbc_label_6.insets = new Insets(0, 0, 5, 5);
		gbc_label_6.gridx = 0;
		gbc_label_6.gridy = 6;
		getContentPane().add(label_6, gbc_label_6);
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		GridBagConstraints gbc_textField_5 = new GridBagConstraints();
		gbc_textField_5.insets = new Insets(0, 0, 5, 0);
		gbc_textField_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_5.gridx = 1;
		gbc_textField_5.gridy = 6;
		getContentPane().add(textField_5, gbc_textField_5);
		JLabel label_7 = new JLabel("\u041A\u043E\u043B-\u0432\u043E");
		GridBagConstraints gbc_label_7 = new GridBagConstraints();
		gbc_label_7.insets = new Insets(0, 0, 5, 5);
		gbc_label_7.gridx = 0;
		gbc_label_7.gridy = 7;
		getContentPane().add(label_7, gbc_label_7);
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		GridBagConstraints gbc_textField_6 = new GridBagConstraints();
		gbc_textField_6.insets = new Insets(0, 0, 5, 0);
		gbc_textField_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_6.gridx = 1;
		gbc_textField_6.gridy = 7;
		getContentPane().add(textField_6, gbc_textField_6);
		JLabel lblCv = new JLabel("\u041D\u043E\u043C\u0435\u0440 CV");
		GridBagConstraints gbc_lblCv = new GridBagConstraints();
		gbc_lblCv.insets = new Insets(0, 0, 5, 5);
		gbc_lblCv.gridx = 0;
		gbc_lblCv.gridy = 8;
		getContentPane().add(lblCv, gbc_lblCv);
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		GridBagConstraints gbc_textField_7 = new GridBagConstraints();
		gbc_textField_7.insets = new Insets(0, 0, 5, 0);
		gbc_textField_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_7.gridx = 1;
		gbc_textField_7.gridy = 8;
		getContentPane().add(textField_7, gbc_textField_7);
		JLabel lblCv_1 = new JLabel("\u0414\u0430\u0442\u0430 CV");
		GridBagConstraints gbc_lblCv_1 = new GridBagConstraints();
		gbc_lblCv_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblCv_1.gridx = 0;
		gbc_lblCv_1.gridy = 9;
		getContentPane().add(lblCv_1, gbc_lblCv_1);
		String mask = "20##.##.##";
		MaskFormatter date = null;
		try {
			date = new MaskFormatter(mask);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		JFormattedTextField formattedTextField_1 = new JFormattedTextField(
				date);
		GridBagConstraints gbc_formattedTextField_1 = new GridBagConstraints();
		gbc_formattedTextField_1.insets = new Insets(0, 0, 5, 0);
		gbc_formattedTextField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_formattedTextField_1.gridx = 1;
		gbc_formattedTextField_1.gridy = 9;
		getContentPane().add(formattedTextField_1, gbc_formattedTextField_1);
		JButton btnNewButton = new JButton("\u041E\u041A");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				int isClient = 0;
				System.out.println(Functions.getStatus((String)comboBox_1.getSelectedItem()));
				if(Functions.getStatus((String)comboBox_1.getSelectedItem()) == Constants.TypesOfClient.PAY ||
						Functions.getStatus((String)comboBox_1.getSelectedItem()) == Constants.TypesOfClient.CLIENT) isClient = 1;
				String query = "INSERT INTO `"+Constants.NamesOfTables.NUMBERS+"`(`Spam`, `Call`, `Courier`, `Trainer`, `Gym`, `Name_Client`, `Number`, `Vk_Client`,`Comment`, `IsClient`, `Status`) "
						+ "VALUES ('"+textField_4.getText()+"','"+textField_8.getText()+"','"+textField_12.getText()+"','"+textField_9.getText()+"','"+textField_10.getText()+"', "
								+ "'"+textField.getText()+"','"+formattedTextField.getText()+"','"+textField_11.getText()+"','"+textField_1.getText()+"', "+isClient+", "+Functions.getStatus((String)comboBox_1.getSelectedItem())+")";
				Connection connect = null;
				try {
					connect = DriverManager.getConnection(Constants.URL, Constants.connInfo);
					SQL.doSQLWithoutResult(query, connect);
					query = "SELECT `id` FROM `"+Constants.NamesOfTables.NUMBERS+"` WHERE `Number` = '"+formattedTextField.getText()+"'";
					ResultSet rs;
					rs = SQL.doSQL(query, connect);
					rs.next();
					int id = rs.getInt("id");
					
					query = "INSERT INTO `"+Constants.NamesOfTables.CLIENTS+"`(`id`, `TypeOfTrain`, `Cost`, `Debt`, `NumberTrain`, `CountOfTrain`, `NumberCV`, `Date`)"
							+ " VALUES ("+id+", "+Functions.getTypeOfTrain((String)comboBox.getSelectedItem())+", "+textField_2.getText()+", "+textField_3.getText()+", "
									+ ""+textField_5.getText()+", "+textField_6.getText()+", "+textField_7.getText()+", '"+formattedTextField_1.getText()+"')";
					
					SQL.doSQLWithoutResult(query, connect);
					SQL.closeConnect(connect);
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		JLabel lblNewLabel = new JLabel(
				"\u0421\u043F\u0430\u043C\u0449\u0438\u043A");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 10;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		textField_4 = new JTextField();
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.insets = new Insets(0, 0, 5, 0);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 1;
		gbc_textField_4.gridy = 10;
		getContentPane().add(textField_4, gbc_textField_4);
		textField_4.setColumns(10);
		JLabel lblNewLabel_1 = new JLabel(
				"\u0417\u0432\u043E\u043D\u0438\u043B\u044C\u0449\u0438\u043A");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 11;
		getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);
		textField_8 = new JTextField();
		textField_8.setColumns(10);
		GridBagConstraints gbc_textField_8 = new GridBagConstraints();
		gbc_textField_8.insets = new Insets(0, 0, 5, 0);
		gbc_textField_8.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_8.gridx = 1;
		gbc_textField_8.gridy = 11;
		getContentPane().add(textField_8, gbc_textField_8);
		
		JLabel lblNewLabel_3 = new JLabel("\u041A\u0443\u0440\u044C\u0435\u0440");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 12;
		getContentPane().add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		textField_12 = new JTextField();
		GridBagConstraints gbc_textField_12 = new GridBagConstraints();
		gbc_textField_12.insets = new Insets(0, 0, 5, 0);
		gbc_textField_12.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_12.gridx = 1;
		gbc_textField_12.gridy = 12;
		getContentPane().add(textField_12, gbc_textField_12);
		textField_12.setColumns(10);
		JLabel label_8 = new JLabel("\u0422\u0440\u0435\u043D\u0435\u0440");
		GridBagConstraints gbc_label_8 = new GridBagConstraints();
		gbc_label_8.insets = new Insets(0, 0, 5, 5);
		gbc_label_8.gridx = 0;
		gbc_label_8.gridy = 13;
		getContentPane().add(label_8, gbc_label_8);
		textField_9 = new JTextField();
		textField_9.setColumns(10);
		GridBagConstraints gbc_textField_9 = new GridBagConstraints();
		gbc_textField_9.insets = new Insets(0, 0, 5, 0);
		gbc_textField_9.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_9.gridx = 1;
		gbc_textField_9.gridy = 13;
		getContentPane().add(textField_9, gbc_textField_9);
		JLabel label_9 = new JLabel("Зал");
		GridBagConstraints gbc_label_9 = new GridBagConstraints();
		gbc_label_9.insets = new Insets(0, 0, 5, 5);
		gbc_label_9.gridx = 0;
		gbc_label_9.gridy = 14;
		getContentPane().add(label_9, gbc_label_9);
		textField_10 = new JTextField();
		textField_10.setColumns(10);
		GridBagConstraints gbc_textField_10 = new GridBagConstraints();
		gbc_textField_10.insets = new Insets(0, 0, 5, 0);
		gbc_textField_10.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_10.gridx = 1;
		gbc_textField_10.gridy = 14;
		getContentPane().add(textField_10, gbc_textField_10);
		JLabel label_10 = new JLabel("Ссылка");
		GridBagConstraints gbc_label_10 = new GridBagConstraints();
		gbc_label_10.insets = new Insets(0, 0, 5, 5);
		gbc_label_10.gridx = 0;
		gbc_label_10.gridy = 15;
		getContentPane().add(label_10, gbc_label_10);
		textField_11 = new JTextField();
		textField_11.setColumns(10);
		GridBagConstraints gbc_textField_11 = new GridBagConstraints();
		gbc_textField_11.insets = new Insets(0, 0, 5, 0);
		gbc_textField_11.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_11.gridx = 1;
		gbc_textField_11.gridy = 15;
		getContentPane().add(textField_11, gbc_textField_11);
		
		JLabel lblNewLabel_2 = new JLabel("\u0421\u0442\u0430\u0442\u0443\u0441");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 16;
		getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"\u041F\u0440\u043E\u0431\u043D\u0430\u044F(\u0410)", "\u041F\u0440\u043E\u0431\u043D\u0430\u044F(\u041F)", "\u041F\u0440\u043E\u0431\u043D\u0430\u044F(\u041D)", "\u041F\u043B\u0430\u0442\u043D\u0430\u044F", "\u0420\u0430\u0441\u043F\u0438\u0441\u0430\u043D\u0438\u0435"}));
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 1;
		gbc_comboBox_1.gridy = 16;
		getContentPane().add(comboBox_1, gbc_comboBox_1);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridwidth = 2;
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 17;
		getContentPane().add(btnNewButton, gbc_btnNewButton);
		setSize(444,494);
	}
}
