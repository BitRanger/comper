
package com.caibowen.comper.ui;

import com.caibowen.comper.model.BWChapter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ModifyChapterDetail extends JDialog {

  private final JPanel contentPanel = new JPanel();
  private JTextField paperName;
  private JTextArea paperDescription;
  private String chapterName;
  private String chapterDes;

  public BWChapter oldChapter;

  /**
   * Create the dialog.
   */
  public ModifyChapterDetail(BWChapter c) {
    oldChapter = c;
    setModal(true);
    setTitle("Add Chapter");
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

    JLabel lblNewLabel = new JLabel("Name");
    lblNewLabel.setBounds(39, 38, 142, 25);
    contentPanel.add(lblNewLabel);

    JLabel label_2 = new JLabel("Description");
    label_2.setBounds(39, 167, 142, 25);
    contentPanel.add(label_2);

    JButton button = new JButton("Close");
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
