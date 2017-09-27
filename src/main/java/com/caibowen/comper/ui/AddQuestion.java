
package com.caibowen.comper.ui;

import com.caibowen.comper.feature.model.QuestionType;
import com.caibowen.comper.model.BWQuestionContent;
import com.caibowen.comper.model.BWQuestionMeta;
import com.caibowen.comper.AppContext;
import com.caibowen.comper.dao.DAOQuestion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class AddQuestion extends JDialog {

  private static final long serialVersionUID = 6252400495728195347L;
  private final JPanel contentPanel = new JPanel();
  private JTextField textField_hard;
  private JTextField textField_score;
  private JTextField textField_points;
  private JTextField textField_paper;
  private JTextArea textArea_problem;
  private JTextArea textArea_answer;
  private JTextArea textArea_comment;
  private JComboBox comboBox_type;
  private JComboBox comboBox_points;
  private JComboBox<Integer> comboBox_paper;
  String problem, answer, comment;
  private String paperId;
  private QuestionType qtye;
  float hard;
  int score;
  int paper;
  //int point;
  //private Set<Integer> set_point = new HashSet<>(48);
  private int point;


  public AddQuestion(String s) {
    setTitle(s);
    setModal(true);
    setBounds(100, 100, 969, 542);

    hard = 0.0f;
    score = 0;
    problem = answer = comment = "";
    getContentPane().setLayout(new BorderLayout());
    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    getContentPane().add(contentPanel, BorderLayout.CENTER);
    contentPanel.setLayout(null);
    {
      JLabel label = new JLabel("Save");
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


    JLabel label = new JLabel("Chapter");
    label.setBounds(367, 298, 54, 15);
    contentPanel.add(label);

    JLabel label_1 = new JLabel("Difficulty");
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


    JLabel label_2 = new JLabel("Anwser");
    label_2.setBounds(378, 22, 54, 15);
    contentPanel.add(label_2);

    String types[] = {"M-Choice", "Fill Blanks", "True or False", "A&A", "Analyze", "Application"};
    comboBox_type = new JComboBox(types);
    comboBox_type.setMaximumRowCount(6);
    comboBox_type.setSelectedIndex(0);
    comboBox_type.setBounds(87, 295, 118, 21);
    contentPanel.add(comboBox_type);

    JLabel label_3 = new JLabel("Type");
    label_3.setBounds(21, 298, 54, 15);
    contentPanel.add(label_3);

    JLabel label_4 = new JLabel("Points");
    label_4.setBounds(21, 389, 54, 15);
    contentPanel.add(label_4);


    //与数据库相连
    String point_s[] = {"1", "2", "3", "4", "5"};
    comboBox_points = new JComboBox(point_s);
    comboBox_points.setBounds(431, 295, 102, 21);
    contentPanel.add(comboBox_points);
    comboBox_points.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        String string = comboBox_points.getSelectedObjects()[0] + "";
        string = string.trim();
        textField_points.setText(string);

      }
    });

    JLabel label_5 = new JLabel("Comment");
    label_5.setBounds(656, 22, 54, 15);
    contentPanel.add(label_5);

    JLabel label_6 = new JLabel("Chapter");
    label_6.setBounds(367, 355, 54, 15);
    contentPanel.add(label_6);

    textField_points = new JTextField("1");
    textField_points.setBounds(431, 352, 215, 21);
    contentPanel.add(textField_points);
    textField_points.setColumns(10);

    JLabel label_7 = new JLabel("From Test");
    label_7.setBounds(689, 298, 80, 15);
    contentPanel.add(label_7);
    /*
		textField_paper = new JTextField("0");
		textField_paper.setBounds(656, 352, 213, 21);
		contentPanel.add(textField_paper);
		textField_paper.setColumns(10);
		*/
    comboBox_paper = new JComboBox<Integer>();
    for (int i = 0; i < 10; i++)
      comboBox_paper.addItem(i);

    comboBox_paper.setBounds(688, 352, 213, 21);
    contentPanel.add(comboBox_paper);
    comboBox_paper.setSelectedIndex(0);

    JButton addPoint = new JButton("Add Chapter");
    addPoint.setBounds(543, 294, 103, 23);
    contentPanel.add(addPoint);
    addPoint.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        AddChapter chapter = new AddChapter();
        chapter.setVisible(true);

      }
    });

    JButton addPaper = new JButton("Add Test");
    addPaper.setBounds(767, 294, 116, 23);
    contentPanel.add(addPaper);
    addPaper.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        AddPaper paper = new AddPaper();
        paper.setVisible(true);
      }
    });


    {
      JPanel buttonPane = new JPanel();
      buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
      getContentPane().add(buttonPane, BorderLayout.SOUTH);
      {
        JButton okButton = new JButton("Finished");
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);
        okButton.addActionListener(new ActionListener() {

          @Override
          public void actionPerformed(ActionEvent arg0) {
            // TODO Auto-generated method stub
            getAll();
            debugInformation();
            //JOptionPane.showMessageDialog(AddQuestion.this, "Finished", "已保存", JOptionPane.INFORMATION_MESSAGE);
            if (JOptionPane.showConfirmDialog(null, "Success, Continure?", "Continure", 2, JOptionPane.INFORMATION_MESSAGE)
                == JOptionPane.CANCEL_OPTION)
              AddQuestion.this.dispose();
          }
        });
      }

      JButton btnReset = new JButton("Reset");
      btnReset.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
          // TODO Auto-generated method stub
          textArea_answer.setText("");
          textArea_comment.setText("");
          textArea_problem.setText("");
          textField_score.setText("0");
        }
      });


      buttonPane.add(btnReset);
      {
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
        cancelButton.addActionListener(new ActionListener() {

          @Override
          public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            AddQuestion.this.dispose();
          }
        });
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
    paper = Integer.parseInt((comboBox_paper.getSelectedObjects()[0] + "").trim());
    String tyString = "";
    String types[] = {"M-Choice", "Fill Blanks", "True or False", "A&A", "Analyze", "Application"};
    tyString = comboBox_type.getSelectedObjects()[0] + "";
    for (int i = 0; i < 6; i++) {
      if (tyString.equals(types[i]))
        qtye = QuestionType.lookup((int) Math.pow(2, i + 1));
    }
    //paperId = textField_paper.getText();


    BWQuestionMeta meta = new BWQuestionMeta();
    meta.id_chapter = point;
    meta.id_paper = paper;//Integer.parseInt(paperId.trim());
    meta.score = score;
    meta.difficulty = hard;
    meta.type = qtye;
    BWQuestionContent qc = new BWQuestionContent();
    qc.content = problem;
    qc.answer = answer;
    qc.comment = comment;

    System.out.println("Enter.getAll()");
    System.out.println(meta.toString());
    System.out.println(qc.toString());
    ((DAOQuestion) AppContext.beanAssembler.getBean("daoQuestion")).saveWithContent(meta, qc);
    System.out.println("Enter.getAll()");
		
		/*
		WKQuestion question = new 
		
		AppContext.daoQuestion.save(meta);
		*/

    return true;
  }

  public void debugInformation() {
    System.out.println("pro  " + problem);
    System.out.println("ans " + answer);
    System.out.println("com  " + comment);
    System.out.println("hard " + hard);
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


  class focus implements FocusListener {

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
        if (hard > 1.0f || hard < 0.0f)
          JOptionPane.showMessageDialog(null, "Difficulty应该在0到1 之间", "Difficulty", JOptionPane.ERROR_MESSAGE);
      } catch (Exception e) {
        // TODO: handle exception
        JOptionPane.showMessageDialog(null, "请输入数字", "Difficulty", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

}
