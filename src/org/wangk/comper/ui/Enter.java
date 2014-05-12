package org.wangk.comper.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import org.wangk.comper.context.AppContext;
import org.wangk.comper.feature.model.QuestionType;
import org.wangk.comper.model.WKQuestionContent;
import org.wangk.comper.model.WKQuestionMeta;

public class Enter extends JDialog {

	private static final long serialVersionUID = 6252400495728195347L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField_hard;
	private JTextField textField_score;
	private JTextField textField_points;
	private JTextField textField_paper;
	private JTextArea textArea_problem;
	private JTextArea textArea_answer;
	private JTextArea textArea_comment ;
	private JComboBox comboBox_type ;
	private JComboBox comboBox_points ;
	private JComboBox<Integer> comboBox_paper;
	String problem,answer,comment;
	private String paperId;
	private QuestionType qtye;
	float hard;
	int score;
	int paper;
	//int point;
	//private Set<Integer> set_point = new HashSet<>(48);
	private int point;
	

	public Enter(String s) {
		setTitle(s);
		setModal(true);
		setBounds(100, 100, 969, 542);
		
		hard = 0.0f;
		score =0 ;
		problem = answer = comment = "";
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel label = new JLabel("录入");
			label.setBounds(21, 22, 54, 15);
			contentPanel.add(label);
		}

		textArea_problem = new JTextArea();
		textArea_problem.setBounds(17, 56, 304, 214);
		contentPanel.add(textArea_problem);
		
		textArea_answer = new JTextArea();
		textArea_answer.setBounds(378, 56, 252, 214);
		contentPanel.add(textArea_answer);
		
		textArea_comment = new JTextArea();
		textArea_comment.setBounds(649, 56, 271, 214);		
		contentPanel.add(textArea_comment);
		

		JLabel label = new JLabel("知识点");
		label.setBounds(367, 298, 54, 15);
		contentPanel.add(label);

		JLabel label_1 = new JLabel("难度");
		label_1.setBounds(21, 337, 54, 15);
		contentPanel.add(label_1);

		textField_hard = new JTextField("0.0");
		textField_hard.setBounds(87, 334, 66, 21);
		contentPanel.add(textField_hard);
		textField_hard.setColumns(10);
		textField_hard.addFocusListener(new focus());

		textField_score = new JTextField("0");
		textField_score.setBounds(87, 386, 66, 21);
		contentPanel.add(textField_score);
		textField_score.setColumns(10);
		textField_score.addFocusListener(new focus());
		

		JLabel label_2 = new JLabel("答案");
		label_2.setBounds(378, 22, 54, 15);
		contentPanel.add(label_2);

		String types[] = {"选择题","填空题","判断题","简解题","解析题","应用题"};
		comboBox_type = new JComboBox(types);
		comboBox_type.setMaximumRowCount(6);
		comboBox_type.setSelectedIndex(0);
		comboBox_type.setBounds(87, 295, 118, 21);
		contentPanel.add(comboBox_type);

		JLabel label_3 = new JLabel("题型");
		label_3.setBounds(21, 298, 54, 15);
		contentPanel.add(label_3);

		JLabel label_4 = new JLabel("分值");
		label_4.setBounds(21, 389, 54, 15);
		contentPanel.add(label_4);

		String point_s[]={"1","2","3","4","5"};
		comboBox_points = new JComboBox(point_s);
		comboBox_points.setBounds(463, 295, 141, 21);
		contentPanel.add(comboBox_points);
		comboBox_points.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String string = comboBox_points.getSelectedObjects()[0]+"";
				string = string.trim();
				textField_points.setText(string);
				
			}
		});
		
		JLabel label_5 = new JLabel("备注");
		label_5.setBounds(656, 22, 54, 15);
		contentPanel.add(label_5);
		
		JLabel label_6 = new JLabel("知识点");
		label_6.setBounds(367, 355, 54, 15);
		contentPanel.add(label_6);
		
		textField_points = new JTextField("1");
		textField_points.setBounds(463, 352, 141, 21);
		contentPanel.add(textField_points);
		textField_points.setColumns(10);
		
		JLabel label_7 = new JLabel("所属试卷");
		label_7.setBounds(656, 298, 54, 15);
		contentPanel.add(label_7);
		/*
		textField_paper = new JTextField("0");
		textField_paper.setBounds(656, 352, 213, 21);
		contentPanel.add(textField_paper);
		textField_paper.setColumns(10);
		*/
		comboBox_paper = new JComboBox<Integer>();
		for(int i =0;i<10;i++)
			comboBox_paper.addItem(i);
		
		comboBox_paper.setBounds(656, 352, 213, 21);
		contentPanel.add(comboBox_paper);
		comboBox_paper.setSelectedIndex(0);
		
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("完成");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						getAll();
						debugInformation();	
						JOptionPane.showMessageDialog(Enter.this, "完成", "已保存", JOptionPane.INFORMATION_MESSAGE);
						Enter.this.dispose();
					}
				});
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

	public boolean getAll() {
		
		problem = textArea_problem.getText();
		answer = textArea_answer.getText();
		comment = textArea_comment.getText();
		
		point = Integer.parseInt(textField_points.getText().trim());
		hard = Float.parseFloat(textField_hard.getText().trim());
		score = Integer.parseInt(textField_score.getText().trim());
		paper =Integer.parseInt((comboBox_paper.getSelectedObjects()[0]+"").trim());
		String tyString="";
		String types[] = {"选择题","填空题","判断题","简解题","解析题","应用题"};
		tyString = comboBox_type.getSelectedObjects()[0]+"";
		for(int i=0;i<6;i++)
		{
			if(tyString.equals(types[i]))
				qtye = QuestionType.lookup((int)Math.pow(2, i+1));
		}
		//paperId = textField_paper.getText();
		
		
		WKQuestionMeta meta = new WKQuestionMeta();
		meta.id_chapter = point;
		meta.id_paper = paper;//Integer.parseInt(paperId.trim());
		meta.score = score;
		meta.difficulty = hard;
		meta.type = qtye;
		WKQuestionContent qc = new WKQuestionContent();
		qc.content = problem;
		qc.answer = answer;
		qc.comment = comment;

		System.out.println("Enter.getAll()");
		System.out.println(meta.toString());
		System.out.println(qc.toString());
		AppContext.daoQuestion.saveWithContent(meta, qc);
		System.out.println("Enter.getAll()");
		
		/*
		WKQuestion question = new 
		
		AppContext.daoQuestion.save(meta);
		*/
		
		return true;
	}
	
	public void debugInformation()
	{
		System.out.println("pro  "+ problem);
		System.out.println("ans "+ answer);
		System.out.println("com  "+ comment);
		System.out.println("hard "+ hard);
		System.out.println("score" + score);
		System.out.println("QuestionType" + qtye);
		System.out.println("PaperId " + paper);
	}

	public String getProblem() {
		return problem;
	}

	public String getAnswer() {
		return answer;
	}

	public QuestionType getQtye() {
		return qtye;
	}

	public float getHard() {
		return hard;
	}

	public int getScore() {
		return score;
	}

	
	
	
	public int getPoint() {
		return point;
	}




	class focus implements FocusListener
	{

		@Override
		public void focusGained(FocusEvent arg0) {
			// TODO Auto-generated method stub			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			// TODO Auto-generated method stub
			try {
				hard = Float.parseFloat(textField_hard.getText().trim());
				score = Integer.parseInt(textField_score.getText().trim());
				if(hard >1.0f || hard <0.0f)
					JOptionPane.showMessageDialog(null, "难度应该在0到1 之间", "难度", JOptionPane.ERROR_MESSAGE);
			} catch (Exception e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "请输入数字", "难度", JOptionPane.ERROR_MESSAGE);
			}
		}		
	}

}
