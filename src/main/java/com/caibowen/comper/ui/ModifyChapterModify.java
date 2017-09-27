
package com.caibowen.comper.ui;

import com.caibowen.comper.AppContext;
import com.caibowen.comper.dao.DAOChapter;
import com.caibowen.comper.model.BWChapter;
import com.caibowen.comper.util.Str;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ModifyChapterModify extends JDialog {

  /**
   *
   */
  private static final long serialVersionUID = -2659718340091665705L;

  private final JPanel contentPanel = new JPanel();
  private JTextField paperName;
  private JTextArea paperDescription;
  private String chapterName;
  private String chapterDes;

  public BWChapter oldChapter;

  /**
   * Create the dialog.
   */
  public ModifyChapterModify(BWChapter c) {
    oldChapter = c;
    setModal(true);
    setTitle("Add Chapter");
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

    JLabel lblNewLabel = new JLabel("New Name");
    lblNewLabel.setBounds(39, 38, 142, 25);
    contentPanel.add(lblNewLabel);

    JLabel label_2 = new JLabel("New Description");
    label_2.setBounds(39, 167, 142, 25);
    contentPanel.add(label_2);

    JButton button = new JButton("Submit");
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

        JOptionPane.showMessageDialog(null, "Modified");
        ModifyChapterModify.this.dispose();
      }
    });

    JButton button_1 = new JButton("Cancel");
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
//		JButton button_2 = new JButton("Reset");
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
