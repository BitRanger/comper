
package com.caibowen.comper.ui;

import com.caibowen.comper.model.BWPaper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ModifyPaperDetail extends JDialog {

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
  public ModifyPaperDetail(BWPaper wkPaper) {
    setModal(true);
    setTitle("TestDetail");
    setBounds(100, 100, 549, 401);
    getContentPane().setLayout(new BorderLayout());
    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    getContentPane().add(contentPanel, BorderLayout.CENTER);
    contentPanel.setLayout(null);

    paperName = new JTextField();
    paperName.setBounds(39, 73, 243, 63);
    paperName.setEditable(false);
    paperName.setText(wkPaper.getName());
    contentPanel.add(paperName);
    paperName.setColumns(10);

    JLabel lblNewLabel = new JLabel("Test Name");
    lblNewLabel.setBounds(39, 38, 142, 25);
    contentPanel.add(lblNewLabel);

    JLabel label_2 = new JLabel("Description");
    label_2.setBounds(39, 167, 142, 25);
    contentPanel.add(label_2);

    JButton button = new JButton("Cancel");
    button.setBounds(341, 74, 93, 23);
    contentPanel.add(button);
    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        ModifyPaperDetail.this.dispose();
      }
    });

    paperDescription = new JTextArea();
    paperDescription.setEditable(false);
    paperDescription.setText(wkPaper.getDescription());
    paperDescription.setBounds(39, 212, 243, 82);
    contentPanel.add(paperDescription);

    JLabel label = new JLabel("TestPoints");
    label.setBounds(39, 325, 111, 15);
    contentPanel.add(label);

    paperScore = new JTextField();
    paperScore.setBounds(216, 322, 43, 21);
    paperScore.setText("" + wkPaper.getScore());
    paperScore.setEditable(false);
    contentPanel.add(paperScore);
    paperScore.setColumns(10);

    JLabel label_1 = new JLabel("Score");
    label_1.setBounds(265, 325, 54, 15);
    contentPanel.add(label_1);
  }

}
