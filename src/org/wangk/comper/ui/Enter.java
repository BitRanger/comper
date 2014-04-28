package org.wangk.comper.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class Enter extends JDialog {

	private static final long serialVersionUID = 6252400495728195347L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField_hard;
	private JTextField textField;

	/**
	 * Launch the application.
	 
	public static void main(String[] args) {
		try {
			Enter dialog = new Enter("加题");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	public Enter(String s) {
		setTitle(s);
		setBounds(100, 100, 969, 542);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel label = new JLabel("录入");
			label.setBounds(21, 22, 54, 15);
			contentPanel.add(label);
		}

		JTextArea textArea_enter = new JTextArea();
		textArea_enter.setBounds(17, 56, 304, 214);
		contentPanel.add(textArea_enter);

		JLabel label = new JLabel("知识点");
		label.setBounds(367, 298, 54, 15);
		contentPanel.add(label);

		JLabel label_1 = new JLabel("难度");
		label_1.setBounds(21, 337, 54, 15);
		contentPanel.add(label_1);

		textField_hard = new JTextField();
		textField_hard.setBounds(87, 334, 66, 21);
		contentPanel.add(textField_hard);
		textField_hard.setColumns(10);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(378, 56, 252, 214);
		contentPanel.add(textArea);

		JLabel label_2 = new JLabel("答案");
		label_2.setBounds(378, 22, 54, 15);
		contentPanel.add(label_2);

		String types[] = {"选择题","填空题","判断题","解答题"};
		JComboBox comboBox_type = new JComboBox(types);
		comboBox_type.setMaximumRowCount(4);
		comboBox_type.setSelectedIndex(0);
		comboBox_type.setBounds(87, 295, 118, 21);
		contentPanel.add(comboBox_type);

		JLabel label_3 = new JLabel("题型");
		label_3.setBounds(21, 298, 54, 15);
		contentPanel.add(label_3);

		JLabel label_4 = new JLabel("分值");
		label_4.setBounds(21, 389, 54, 15);
		contentPanel.add(label_4);

		textField = new JTextField();
		textField.setBounds(87, 386, 66, 21);
		contentPanel.add(textField);
		textField.setColumns(10);

		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(463, 295, 141, 21);
		contentPanel.add(comboBox_1);
		
		JLabel label_5 = new JLabel("备注");
		label_5.setBounds(656, 22, 54, 15);
		contentPanel.add(label_5);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(649, 56, 271, 214);
		contentPanel.add(textArea_1);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("完成");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}

			JButton btnNewButton = new JButton("重置");
			buttonPane.add(btnNewButton);
			{
				JButton cancelButton = new JButton("取消");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
