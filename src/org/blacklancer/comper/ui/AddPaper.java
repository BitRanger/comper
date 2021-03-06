/*******************************************************************************
 * Copyright (c) 2014 Cai Bowen, Zhou Liangpeng.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Cai Bowen,  Zhou Liangpeng. - initial API and implementation
 ******************************************************************************/
package org.blacklancer.comper.ui;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import org.blacklancer.comper.AppContext;
import org.blacklancer.comper.dao.DAOPaper;
import org.blacklancer.comper.model.WKChapter;
import org.blacklancer.comper.model.WKPaper;
import org.blacklancer.comper.util.Str;


public class AddPaper extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField paperName;
	private JTextField paperScore;
	private JTextArea paperDescription;
	private WKPaper wkPaper;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			Paper dialog = new Paper();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public AddPaper() {
		setModal(true);
		setTitle("添加新试卷");
		setBounds(100, 100, 549, 401);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		paperName = new JTextField();
		paperName.setBounds(39, 73, 243, 63);
		contentPanel.add(paperName);
		paperName.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("输入新的试卷名字");
		lblNewLabel.setBounds(39, 38, 142, 25);
		contentPanel.add(lblNewLabel);
		
		JLabel label_2 = new JLabel("新试卷描述");
		label_2.setBounds(39, 167, 142, 25);
		contentPanel.add(label_2);
		
		JButton button = new JButton("提交");
		button.setBounds(341, 74, 93, 23);
		contentPanel.add(button);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(!getAll())
				{
					JOptionPane.showMessageDialog(null, "请输入改试卷的正确分值", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				else
				{
					if(writeToDB())
					{
						System.out.println(wkPaper.toString()); ;
						//JOptionPane.showMessageDialog(null,"添加成功","OK",JOptionPane.INFORMATION_MESSAGE);
						if(JOptionPane.showConfirmDialog(null, "添加成功，是否再次添加", "继续", 2, JOptionPane.INFORMATION_MESSAGE) 
								== JOptionPane.CANCEL_OPTION)
							AddPaper.this.dispose();
					}
				}
			}
		});
		
		
		JButton button_1 = new JButton("取消");
		button_1.setBounds(341, 168, 93, 23);
		contentPanel.add(button_1);
		button_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				AddPaper.this.dispose();
				
			}
		});
		
		JButton button_2 = new JButton("重置");
		button_2.setBounds(341, 249, 93, 23);
		contentPanel.add(button_2);
		
		button_2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				paperName.setText("");
				paperDescription.setText("");
				paperScore.setText("0");
			}
		});
		
		paperDescription = new JTextArea();
		paperDescription.setBounds(39, 212, 243, 82);
		contentPanel.add(paperDescription);
		
		JLabel label = new JLabel("新试卷分值");
		label.setBounds(39, 325, 111, 15);
		contentPanel.add(label);
		
		paperScore = new JTextField();
		paperScore.setBounds(216, 322, 43, 21);
		contentPanel.add(paperScore);
		paperScore.setColumns(10);
		
		JLabel label_1 = new JLabel("分");
		label_1.setBounds(265, 325, 54, 15);
		contentPanel.add(label_1);
	}
	
	public boolean writeToDB()
	{
//		wkPaper -> db;
		((DAOPaper)AppContext.beanAssembler.getBean("daoPaper")).save(wkPaper);
		return true;
	}
	
	public boolean getAll()
	{
		int score ;
		if(!Str.Utils.notBlank(paperName.getText()))
			return false;
		else
		{
			wkPaper = new WKPaper();
			wkPaper.setName(paperName.getText().trim());
			wkPaper.setDescription(paperDescription.getText().trim());
			try {
				
				score = Integer.parseInt(paperScore.getText().trim());
			} catch (Exception e) {
				// TODO: handle exception
				//JOptionPane.showMessageDialog(null, "请输入改试卷的正确分值", "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
//			wkPaper.set
			
			if(score <=0)
			{
				//JOptionPane.showMessageDialog(null, "请输入改试卷的正确分值", "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			wkPaper.setName(paperName.getText().trim());
			wkPaper.setScore(score);
			wkPaper.setDescription(paperDescription.getText().trim());
			wkPaper.setName_publisher("Teacher");
			
//			wkPaper.setTime_published(time_published);
//			wkPaper.setTime_published(time_published);
			
//			wkchapter.time_created =
			return true;
		}
		
	}
}
