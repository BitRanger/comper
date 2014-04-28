package org.wangk.comper.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class Choose extends JDialog {

	private static final long serialVersionUID = 6638446042080007558L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JSlider slider_hard;
	private JSpinner spinner_hard;

	/**
	 * Launch the application.
	 
	public static void main(String[] args) {
		try {
			Choose dialog = new Choose("选择");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	public Choose(String s) {
		setTitle(s);
		setBounds(100, 100, 643, 294);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel label = new JLabel("难度");
			label.setBounds(54, 22, 54, 15);
			contentPanel.add(label);
		}
		
		slider_hard = new JSlider(0,100,50);
		slider_hard.setMajorTickSpacing(20);
		slider_hard.setMinorTickSpacing(5);
		slider_hard.setPaintLabels(true);
		slider_hard.setPaintTicks(true);
	
		slider_hard.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int x = slider_hard.getValue();
				spinner_hard.setValue(new Double(x)/100);			
				
			}
		});
		//slider_hard.setMaximum(1);
		
	
		slider_hard.setBounds(165, 22, 151, 48);
		contentPanel.add(slider_hard);

		JLabel label = new JLabel("知识点");
		label.setBounds(54, 91, 54, 15);
		contentPanel.add(label);

		JLabel label_1 = new JLabel("题型");
		label_1.setBounds(386, 22, 54, 15);
		contentPanel.add(label_1);

		JSpinner spinner_selector = new JSpinner();
		spinner_selector.setBounds(514, 48, 74, 22);
		contentPanel.add(spinner_selector);

		JLabel label_2 = new JLabel("选择题");
		label_2.setBounds(386, 51, 54, 15);
		contentPanel.add(label_2);

		JLabel label_3 = new JLabel("填空题");
		label_3.setBounds(386, 91, 54, 15);
		contentPanel.add(label_3);

		JLabel label_4 = new JLabel("判断题");
		label_4.setBounds(386, 129, 54, 15);
		contentPanel.add(label_4);

		JLabel label_5 = new JLabel("解答题");
		label_5.setBounds(386, 161, 54, 15);
		contentPanel.add(label_5);

		JSpinner spinner_fill = new JSpinner();
		spinner_fill.setBounds(514, 88, 74, 22);
		contentPanel.add(spinner_fill);

		JSpinner spinner_trf = new JSpinner();
		spinner_trf.setBounds(514, 126, 74, 22);
		contentPanel.add(spinner_trf);

		JSpinner spinner_answer = new JSpinner();
		spinner_answer.setBounds(514, 158, 74, 22);
		contentPanel.add(spinner_answer);

		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(175, 88, 141, 21);
		contentPanel.add(comboBox_1);

		spinner_hard = new JSpinner();
		
		
		spinner_hard.setModel(new SpinnerNumberModel(0.0, 0.0, 1.0, 0.05));
		
		
		spinner_hard.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				double x = (double) spinner_hard.getValue();
				slider_hard.setValue(new Integer((int) (x*100)));
			}
		});
		
		spinner_hard.setBounds(98, 22, 49, 22);
		contentPanel.add(spinner_hard);

		JLabel label_6 = new JLabel("题目个数");
		label_6.setBounds(514, 22, 54, 15);
		contentPanel.add(label_6);

		JLabel label_7 = new JLabel("分值");
		label_7.setBounds(450, 22, 54, 15);
		contentPanel.add(label_7);

		textField = new JTextField();
		textField.setBounds(450, 48, 49, 21);
		contentPanel.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(450, 88, 49, 21);
		contentPanel.add(textField_1);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(450, 126, 49, 21);
		contentPanel.add(textField_2);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(450, 158, 49, 21);
		contentPanel.add(textField_3);
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

			JButton button = new JButton("重置");
			buttonPane.add(button);
			{
				JButton cancelButton = new JButton("取消");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
