
package com.caibowen.comper.ui;

import com.caibowen.comper.dao.DAOChapter;
import com.caibowen.comper.model.BWChapter;
import com.caibowen.comper.util.Str;
import com.caibowen.comper.AppContext;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AddChapter extends JDialog {

  private final JPanel contentPanel = new JPanel();
  private JTextField paperName;
  private JTextArea paperDescription;
  private String chapterName;
  private String chapterDes;
  private BWChapter wkchapter;
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

    JLabel lblNewLabel = new JLabel("Input Chapter Name");
    lblNewLabel.setBounds(39, 38, 142, 25);
    contentPanel.add(lblNewLabel);

    JLabel label_2 = new JLabel("Chapter Description");
    label_2.setBounds(39, 167, 142, 25);
    contentPanel.add(label_2);

    JButton button = new JButton("Submit");
    button.setBounds(386, 73, 93, 23);
    contentPanel.add(button);
    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (!getAll()) {
          JOptionPane.showMessageDialog(null, "Please enter the content", "Warning", JOptionPane.ERROR_MESSAGE);
          return;
        } else {
          if (writeToDB()) {
            if (JOptionPane.showConfirmDialog(null, "Success, Continue?", "Continue", 2, JOptionPane.INFORMATION_MESSAGE)
                == JOptionPane.CANCEL_OPTION)
              AddChapter.this.dispose();
          }
        }
      }
    });

    JButton button_1 = new JButton("Cancel");
    button_1.setBounds(386, 168, 93, 23);
    contentPanel.add(button_1);
    button_1.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        AddChapter.this.dispose();
      }
    });

    JButton button_2 = new JButton("Reset");
    button_2.setBounds(386, 250, 93, 23);
    contentPanel.add(button_2);
    button_2.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        paperDescription.setText("");
        paperName.setText("");
      }
    });


//		textArea = new JTextArea("Preview");
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

  public boolean writeToDB() {
//		wkchapter -> db;
    ((DAOChapter) AppContext.beanAssembler.getBean("daoChapter")).save(wkchapter);
    return true;
  }

  public boolean getAll() {
    if (!Str.Utils.notBlank(paperName.getText()))
      return false;
    else {
      wkchapter = new BWChapter();
      wkchapter.setName(paperName.getText().trim());
      wkchapter.setDescription(paperDescription.getText().trim());
//			wkchapter.time_created =
      return true;
    }
  }

}
