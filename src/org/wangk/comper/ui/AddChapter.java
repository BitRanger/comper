/*******************************************************************************
 * Copyright (c) 2014 WangKang.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     WangKang - initial API and implementation
 ******************************************************************************/
package org.wangk.comper.ui;
import java.awt.BorderLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLData;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import org.wangk.comper.context.AppContext;
import org.wangk.comper.dao.DAOChapter;
import org.wangk.comper.model.WKChapter;
import org.wangk.comper.model.WKPaper;
import org.wangk.comper.util.Str;


public class AddChapter extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField paperName;
	private JTextArea paperDescription;
	private String chapterName;
	private String chapterDes;
	private WKChapter wkchapter;

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
	public AddChapter() {
		setModal(true);
		setTitle("添加知识点");
		setBounds(100, 100, 543, 389);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		paperName = new JTextField("");
		paperName.setBounds(39, 73, 243, 63);
		contentPanel.add(paperName);
		paperName.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("输入新的知识点名称");
		lblNewLabel.setBounds(39, 38, 142, 25);
		contentPanel.add(lblNewLabel);
		
		JLabel label_2 = new JLabel("新知识点描述");
		label_2.setBounds(39, 167, 142, 25);
		contentPanel.add(label_2);
		
		JButton button = new JButton("提交");
		button.setBounds(386, 73, 93, 23);
		contentPanel.add(button);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!getAll())
				{
					JOptionPane.showMessageDialog(null,"请输入知识点","Warning",JOptionPane.ERROR_MESSAGE);
					return;
				}
				else
				{
					if(writeToDB())
					{
						//JOptionPane.showMessageDialog(null,"添加成功","OK",JOptionPane.INFORMATION_MESSAGE);
						if(JOptionPane.showConfirmDialog(null, "添加成功，是否再次添加", "继续", 2, JOptionPane.INFORMATION_MESSAGE) 
								== JOptionPane.CANCEL_OPTION)
							AddChapter.this.dispose();
					}
				}
			}
		});
		
		JButton button_1 = new JButton("取消");
		button_1.setBounds(386, 168, 93, 23);
		contentPanel.add(button_1);
		button_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				AddChapter.this.dispose();
			}
		});
		
		JButton button_2 = new JButton("重置");
		button_2.setBounds(386, 250, 93, 23);
		contentPanel.add(button_2);
		button_2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				paperDescription.setText("");
				paperName.setText("");
			}
		});
		
		
//		textArea = new JTextArea("试卷预览");
//		textArea.setLineWrap(true);
//		textArea.setEditable(false);
//		textArea.setBounds(20, 72, 560, 483);
//		frame.getContentPane().add(textArea);
//		
//		JScrollPane js = new JScrollPane(textArea);
//		js.setBounds(20, 72, 560, 483);
//		frame.getContentPane().add(js);
		
		paperDescription = new JTextArea("");
		paperDescription.setLineWrap(true);
		paperDescription.setBounds(39, 229, 243, 82);
		contentPanel.add(paperDescription);
	}

	public boolean writeToDB()
	{
//		wkchapter -> db;
		((DAOChapter)AppContext.beanAssembler.getBean("daoChapter")).save(wkchapter);
		return true;
	}
	
	public boolean getAll()
	{
		
		if(!Str.Utils.notBlank(paperName.getText()))
			return false;
		else
		{
			wkchapter = new WKChapter();
			wkchapter.setName(paperName.getText().trim());
			wkchapter.setDescription(paperDescription.getText().trim());		
//			wkchapter.time_created =
			return true;
		}
		
	}

}
