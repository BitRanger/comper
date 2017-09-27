
package com.caibowen.comper.ui;

import com.caibowen.comper.model.BWPaper;
import com.caibowen.comper.util.Str;
import com.caibowen.comper.AppContext;
import com.caibowen.comper.dao.DAOPaper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ModifyPaperModify extends JDialog {

  private final JPanel contentPanel = new JPanel();
  private JTextField paperName;
  private JTextField paperScore;
  private JTextArea paperDescription;
  private BWPaper wkPaper;

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
  public ModifyPaperModify(final BWPaper wkPaperOld) {
    setModal(true);
    setTitle("Add New Test");
    setBounds(100, 100, 549, 401);
    getContentPane().setLayout(new BorderLayout());
    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    getContentPane().add(contentPanel, BorderLayout.CENTER);
    contentPanel.setLayout(null);

    paperName = new JTextField(wkPaperOld.getName());
    paperName.setBounds(39, 73, 243, 63);
    contentPanel.add(paperName);
    paperName.setColumns(10);

    JLabel lblNewLabel = new JLabel("Test Name");
    lblNewLabel.setBounds(39, 38, 142, 25);
    contentPanel.add(lblNewLabel);

    JLabel label_2 = new JLabel("Description");
    label_2.setBounds(39, 167, 142, 25);
    contentPanel.add(label_2);

    paperDescription = new JTextArea(wkPaperOld.getDescription());
    paperDescription.setBounds(39, 212, 243, 82);
    contentPanel.add(paperDescription);

    JLabel label = new JLabel("Points");
    label.setBounds(39, 325, 111, 15);
    contentPanel.add(label);

    paperScore = new JTextField(wkPaperOld.getScore() + "");
    paperScore.setBounds(216, 322, 43, 21);
    contentPanel.add(paperScore);
    paperScore.setColumns(10);

    JLabel label_1 = new JLabel("Score");
    label_1.setBounds(265, 325, 54, 15);
    contentPanel.add(label_1);

    JButton button = new JButton("Add");
    button.setBounds(341, 74, 93, 23);
    contentPanel.add(button);
    button.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        if (!getAll()) {
          JOptionPane.showMessageDialog(null, "Enter correct score", "Error", JOptionPane.ERROR_MESSAGE);
          return;
        } else {
          if (writeToDB()) {
            System.out.println(wkPaper.toString());
            ;
            //JOptionPane.showMessageDialog(null,"添加成功","OK",JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showConfirmDialog(null, "Success", "OK", JOptionPane.INFORMATION_MESSAGE);
            ModifyPaperModify.this.dispose();
          }
        }
      }
    });


    JButton button_1 = new JButton("Cancel");
    button_1.setBounds(341, 168, 93, 23);
    contentPanel.add(button_1);
    button_1.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        ModifyPaperModify.this.dispose();

      }
    });

    JButton button_2 = new JButton("Reset");
    button_2.setBounds(341, 249, 93, 23);
    contentPanel.add(button_2);

    button_2.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        paperName.setText(wkPaperOld.getName());
        paperDescription.setText(wkPaperOld.getDescription());
        paperScore.setText("" + wkPaperOld.getScore());
      }
    });


  }

  public boolean writeToDB() {
//		wkPaper -> db;
    ((DAOPaper) AppContext.beanAssembler.getBean("daoPaper")).save(wkPaper);
    return true;
  }

  public boolean getAll() {
    int score;
    if (!Str.Utils.notBlank(paperName.getText()))
      return false;
    else {
      wkPaper = new BWPaper();
      wkPaper.setName(paperName.getText().trim());
      wkPaper.setDescription(paperDescription.getText().trim());
      try {

        score = Integer.parseInt(paperScore.getText().trim());
      } catch (Exception e) {
        // TODO: handle exception
        //JOptionPane.showMessageDialog(null, "请输入改Test的正确Points", "Error", JOptionPane.ERROR_MESSAGE);
        return false;
      }
//			wkPaper.set

      if (score <= 0) {
        //JOptionPane.showMessageDialog(null, "请输入改Test的正确Points", "Error", JOptionPane.ERROR_MESSAGE);
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