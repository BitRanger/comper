package org.wangk.comper.ui;

import org.wangk.comper.context.AppContext;
import org.wangk.comper.dao.DAOChapter;
import org.wangk.comper.dao.DAOPaper;
import org.wangk.comper.dao.DAOQuestion;
import org.wangk.comper.dao.QuestionService;
import org.wangk.comper.feature.*;
import org.wangk.comper.feature.model.QuestionType;
import org.wangk.comper.util.Pair;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Choose extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField choose_score;
	private JTextField fill_score;
	private JTextField trf_score;
	private JTextField answer_score;
	private JTextField explain_score;
	private JTextField app_score;
	private JSlider slider_hard;
	private JSpinner spinner_hard;
	private JSpinner choose_number;
	private JSpinner spinner_fill;
	private JSpinner spinner_trf;
	private JSpinner spinner_answer;
	private JSpinner spinner_explain;
	private JSpinner spinner_application;
	private JComboBox point;
	private JTextField chapter;
	private  JLabel score;
	String show_String,acu_String;
	Set<Integer> range_Set = new HashSet<>(48);
	int total_score,part_score,temp_score;
	int choose_score_last,fill_score_last,trf_score_last,answer_score_last,explain_score_last,app_score_last;
	
	DAOChapter daoChapter;
	DAOPaper daoPaper;
	DAOQuestion daoQuestion;
	QuestionService questionService;
	
	private Config config;

	/**
	 * Create the dialog.
	 */
	public Choose(String s) {
		//SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS
//		daoQuestion = AppContext.beanAssembler.getBean("daoQuestion");
		
		setTitle(s);
		setModal(true);
		setBounds(100, 100, 650, 368);
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
		show_String = acu_String =""; 
		total_score = 0;
		part_score = 0;
		temp_score = 0;
		choose_score_last  = 0;
		fill_score_last = 0;
		trf_score_last = 0;
		answer_score_last = 0;
		explain_score_last = 0;
		app_score_last = 0;
	
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

		choose_number = new JSpinner();
		choose_number.setModel(new SpinnerNumberModel(0, 0, 50, 1));
		choose_number.setBounds(514, 48, 74, 22);
		contentPanel.add(choose_number);

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
		
		JLabel lblNewLabel = new JLabel("解析题");
		lblNewLabel.setBounds(386, 196, 54, 15);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("应用题");
		lblNewLabel_1.setBounds(386, 230, 54, 15);
		contentPanel.add(lblNewLabel_1);

		spinner_fill = new JSpinner();
		spinner_fill.setModel(new SpinnerNumberModel(0, 0,50,1));
		spinner_fill.setBounds(514, 88, 74, 22);
		contentPanel.add(spinner_fill);

		spinner_trf = new JSpinner();
		spinner_trf.setModel(new SpinnerNumberModel(0, 0, 50, 1));
		spinner_trf.setBounds(514, 126, 74, 22);
		contentPanel.add(spinner_trf);

		spinner_answer = new JSpinner();
		spinner_answer.setModel(new SpinnerNumberModel(0, 0,50, 1));
		spinner_answer.setBounds(514, 158, 74, 22);
		contentPanel.add(spinner_answer);
		
		spinner_explain = new JSpinner();
		spinner_explain.setModel(new SpinnerNumberModel(0, 0, 50, 1));
		spinner_explain.setBounds(514, 190, 74, 22);
		contentPanel.add(spinner_explain);
		
		spinner_application = new JSpinner();
		spinner_application.setModel(new SpinnerNumberModel(0, 0, 50, 1));
		spinner_application.setBounds(514, 227, 74, 22);
		contentPanel.add(spinner_application);
		
		

		String kno_point[] = {"1","2","3","4","5","6"};
		point = new JComboBox(kno_point);
		point.setMaximumRowCount(4);
		point.setSelectedIndex(1);
		point.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				String x = point.getSelectedObjects()[0]+" ";
				show_String+= x;
				x =x.trim();
				chapter.setText(show_String);
				range_Set.add(Integer.parseInt(x));
				acu_String+="";
				
			}
		});
		point.setBounds(175, 88, 141, 21);
		contentPanel.add(point);		

		spinner_hard = new JSpinner();		
		spinner_hard.setModel(new SpinnerNumberModel(0.5, 0.0, 1.0, 0.05));		
		
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

		choose_score = new JTextField("0");
		choose_score.setBounds(450, 48, 49, 21);
		contentPanel.add(choose_score);
		choose_score.setColumns(10);
		choose_score.addFocusListener(new focus());		
		choose_score.getDocument().addDocumentListener(new doc());

		fill_score = new JTextField("0");
		fill_score.setColumns(10);	
		fill_score.setBounds(450, 88, 49, 21);
		contentPanel.add(fill_score);
		fill_score.addFocusListener(new focus());
		fill_score.getDocument().addDocumentListener(new doc());

		trf_score = new JTextField("0");
		trf_score.setColumns(10);
		trf_score.setBounds(450, 126, 49, 21);
		contentPanel.add(trf_score);
		trf_score.addFocusListener(new focus());
		trf_score.getDocument().addDocumentListener(new doc());

		answer_score = new JTextField("0");
		answer_score.setColumns(10);
		answer_score.setBounds(450, 158, 49, 21);
		contentPanel.add(answer_score);
		answer_score.addFocusListener(new focus());
		answer_score.getDocument().addDocumentListener(new doc());
		
		explain_score = new JTextField("0");
		explain_score.setColumns(10);
		explain_score.setBounds(450, 189, 49, 21);
		contentPanel.add(explain_score);
		explain_score.addFocusListener(new focus());
		explain_score.getDocument().addDocumentListener(new doc());
		
		app_score = new JTextField("0");
		app_score.setColumns(10);
		app_score.setBounds(450, 227, 49, 21);
		contentPanel.add(app_score);
		app_score.addFocusListener(new focus());
		app_score.getDocument().addDocumentListener(new doc());
		
		JLabel label_8 = new JLabel("总分");
		label_8.setBounds(386, 270, 54, 15);
		contentPanel.add(label_8);
		
		score = new JLabel("  0  分");
		score.setBounds(514, 270, 54, 15);
		contentPanel.add(score);
		
		JLabel label_10 = new JLabel("章节");
		label_10.setBounds(54, 147, 54, 15);
		contentPanel.add(label_10);
		
		chapter = new JTextField();
		chapter.setBounds(174, 144, 142, 21);
		contentPanel.add(chapter);
		
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("完成");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						double hard = (double) spinner_hard.getValue();
						try {
							int choose_score_int =Integer.parseInt(choose_score.getText());
							int choose_number_int =(int) choose_number.getValue();
							int fill_score_int = Integer.parseInt(fill_score.getText());
							int fill_number_int = (int) spinner_fill.getValue();
							int trf_score_int = Integer.parseInt(trf_score.getText());
							int trf_number_int = (int) spinner_trf.getValue();
							int answer_score_int = Integer.parseInt(answer_score.getText());
							int answer_number_int =(int) spinner_answer.getValue();
							int explain_number_int = (int) spinner_explain.getValue();
							int app_number_int = (int) spinner_application.getValue();
							
							Pair<Integer, Integer> choose_problem = new Pair<Integer, Integer>(choose_score_last,choose_number_int);
							Pair<Integer, Integer> fill_problem =  new Pair<Integer, Integer>(fill_score_last, fill_number_int);
							Pair<Integer, Integer> trf_problem =  new Pair<Integer, Integer>(trf_score_last, trf_number_int);
							Pair<Integer, Integer> answer_problem = new Pair<Integer, Integer>(answer_score_last, answer_number_int);
							Pair<Integer, Integer> explain_problem = new Pair<Integer, Integer>(explain_score_last, explain_number_int);
							Pair<Integer, Integer> app_problem = new Pair<Integer, Integer>(app_score_last, explain_number_int);
							
							Map<QuestionType, Pair<Integer, Integer>> typeMap = new HashMap<QuestionType, Pair<Integer,Integer>>();							
							typeMap.put(QuestionType.lookup(2), choose_problem);
							typeMap.put(QuestionType.lookup(4), fill_problem);
							typeMap.put(QuestionType.lookup(8), trf_problem);
							typeMap.put(QuestionType.lookup(16), answer_problem);
							typeMap.put(QuestionType.lookup(32), explain_problem);
							typeMap.put(QuestionType.lookup(64), app_problem);
														
							float tole = 0.02f;
							
							if(range_Set.isEmpty())
							{
								JOptionPane.showMessageDialog(null, "未输入知识点", "错误", JOptionPane.ERROR_MESSAGE);
							}
							else {
								JOptionPane.showMessageDialog(null, "OK", "OK", JOptionPane.INFORMATION_MESSAGE);
								
								config =  Config.build((float)hard, range_Set, typeMap, 1, tole, SystemConfig.getDefault());
								Choose.this.dispose();
							}
							
							
							//SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS
							
							
						} catch (Exception e) {
							// TODO: handle exception
							System.out.println("asd");
							JOptionPane.showMessageDialog(null, "请输入数字", "错误", JOptionPane.ERROR_MESSAGE);
						}					
						
					}
				});
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
	
	class focus implements FocusListener
	{
		@Override
		public void focusLost(FocusEvent e) {			
			if(e.getSource() == choose_score)
				choose_score_last = temp_score;
			if(e.getSource() == trf_score)
				trf_score_last = temp_score;
			if(e.getSource() ==  fill_score)
				fill_score_last = temp_score;
			if(e.getSource() == answer_score)
				answer_score_last =  temp_score;
			if(e.getSource() == explain_score)
				explain_score_last = temp_score;
			if(e.getSource() == app_score)
				app_score_last = temp_score;
		}
		
		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == choose_score)
				temp_score = Integer.parseInt(choose_score.getText().trim());
			if(e.getSource() == trf_score)
				temp_score =  Integer.parseInt(trf_score.getText().trim());
			if(e.getSource() ==  fill_score)
				temp_score = Integer.parseInt(fill_score.getText().trim());
			if(e.getSource() == answer_score)
				temp_score = Integer.parseInt(answer_score.getText().trim());
			if(e.getSource() == explain_score)
				temp_score = Integer.parseInt(explain_score.getText().trim());
			if(e.getSource() == app_score)
				temp_score = Integer.parseInt(app_score.getText().trim());
		}
	}
	
	class  doc implements DocumentListener
	{

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			String stri="";
			part_score  -= temp_score;//System.out.println(part_score+ "part");
			if(e.getDocument() == choose_score.getDocument())	
				stri = choose_score.getText();
			if(e.getDocument() ==  fill_score.getDocument())
			{
				stri = fill_score.getText();
				//System.out.println("fill");
			}
			
			if(e.getDocument() == trf_score.getDocument())
				stri =trf_score.getText();
			
			if(e.getDocument() == answer_score.getDocument())
			{
				stri = answer_score.getText();
				//System.out.println("Choose.doc.insertUpdate()");
			//	System.out.println(stri+"ans" + part_score);
			}
			if(e.getDocument() == explain_score.getDocument())
				stri = explain_score.getText();
			if(e.getDocument() == app_score.getDocument())
				stri = app_score.getText();
				
			stri = stri.trim();
			try {
				temp_score = Integer.parseInt(stri);
			} catch (Exception e2) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "请输入数字", "错误", JOptionPane.ERROR_MESSAGE);
			}
			
			System.out.println("0 " +stri +" "+ part_score + " "+temp_score);
			part_score+= temp_score;
			System.out.println("1 " +stri +" "+ part_score+ " "+temp_score);
			//System.out.println(part_score+ "part");
			score.setText(part_score+"  分");		
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			String stri="";
			part_score  -= temp_score;
			if(e.getDocument() == choose_score.getDocument())
				stri = choose_score.getText();
				
			if(e.getDocument() ==  fill_score.getDocument())
				stri = fill_score.getText();
			
			if(e.getDocument() == trf_score.getDocument())
			{
				stri =trf_score.getText();
				
			}
				
			if(e.getDocument() == answer_score.getDocument())
			{
				stri = answer_score.getText();				
			}
			
			if(e.getDocument() == explain_score.getDocument())
				stri = explain_score.getText();
			
			if(e.getDocument() == app_score.getDocument())
				stri = app_score.getText();
			
			stri = stri.trim();
			if(stri.length() ==0)
				stri ="0";
			
			temp_score = Integer.parseInt(stri);
			System.out.println(stri);
			part_score+= temp_score;
			score.setText(part_score+"  分");
		}		
	}	
}



