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

import org.blacklancer.comper.AppContext;
import org.blacklancer.comper.dao.DAOChapter;
import org.blacklancer.comper.model.WKChapter;
import org.blacklancer.comper.model.WKPaper;
import org.blacklancer.comper.util.Str;


public class ModifyChapterModify extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField paperName;
	private JTextArea paperDescription;
	private String chapterName;
	private String chapterDes;
	
	public WKChapter oldChapter;

	/**
	 * Create the dialog.
	 */
	public ModifyChapterModify(WKChapter c) {
		oldChapter = c;
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
		paperName.setText(oldChapter.name);
		
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

				final DAOChapter daoChapter = AppContext.beanAssembler
						.getBean("daoChapter");
				String s = paperName.getText();
				if (!Str.Utils.notBlank(s)) {
					s = "";
				} else {
					s = s.trim();
				}
				oldChapter.setName(s);
				
				s = paperDescription.getText();
				if (!Str.Utils.notBlank(s)) {
					s = "";
				} else {
					s = s.trim();
				}
				oldChapter.description = paperDescription.getText().trim();
				
				
				daoChapter.update(oldChapter);

				JOptionPane.showMessageDialog(null, "已修改");
				ModifyChapterModify.this.dispose();
			}
		});
		
		JButton button_1 = new JButton("取消");
		button_1.setBounds(386, 168, 93, 23);
		contentPanel.add(button_1);
		button_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				ModifyChapterModify.this.dispose();
			}
		});
//		
//		JButton button_2 = new JButton("重置");
//		button_2.setBounds(386, 250, 93, 23);
//		contentPanel.add(button_2);
//		button_2.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				paperDescription.setText("");
//				paperName.setText("");
//			}
//		});
		
		paperDescription = new JTextArea(oldChapter.description);
		paperDescription.setLineWrap(true);
		paperDescription.setBounds(39, 229, 243, 82);
		contentPanel.add(paperDescription);
	}


}
