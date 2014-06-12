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


public class ModifyChapterDetail extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField paperName;
	private JTextArea paperDescription;
	private String chapterName;
	private String chapterDes;
	
	public WKChapter oldChapter;

	/**
	 * Create the dialog.
	 */
	public ModifyChapterDetail(WKChapter c) {
		oldChapter = c;
		setModal(true);
		setTitle("添加知识点");
		setBounds(100, 100, 543, 389);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		paperName = new JTextField("");
		paperName.setEditable(false);
		paperName.setBounds(39, 73, 243, 63);
		contentPanel.add(paperName);
		paperName.setColumns(10);
		paperName.setText(oldChapter.name);
		
		paperDescription = new JTextArea(oldChapter.description);
		paperDescription.setEditable(false);
		paperDescription.setLineWrap(true);
		paperDescription.setBounds(39, 229, 243, 82);
		contentPanel.add(paperDescription);
		
		JLabel lblNewLabel = new JLabel("知识点名称");
		lblNewLabel.setBounds(39, 38, 142, 25);
		contentPanel.add(lblNewLabel);
		
		JLabel label_2 = new JLabel("知识点描述");
		label_2.setBounds(39, 167, 142, 25);
		contentPanel.add(label_2);
		
		JButton button = new JButton("退出");
		button.setBounds(386, 73, 93, 23);
		contentPanel.add(button);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {				
				ModifyChapterDetail.this.dispose();
			}
		});
		
	}


}
