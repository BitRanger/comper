
package com.caibowen.comper.ui;

import com.caibowen.comper.dao.DAOChapter;
import com.caibowen.comper.feature.SystemConfig;
import com.caibowen.comper.feature.model.QuestionType;
import com.caibowen.comper.model.BWChapter;
import com.caibowen.comper.util.Pair;
import com.caibowen.comper.AppContext;
import com.caibowen.comper.dao.DAOPaper;
import com.caibowen.comper.dao.DAOQuestion;
import com.caibowen.comper.dao.QuestionService;
import com.caibowen.comper.feature.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.*;
import java.util.List;

public class Choose extends JDialog {

  private final JPanel contentPanel = new JPanel();
  private JTextField choose_score;
  private JTextField fill_score;
  private JTextField trf_score;
  private JTextField answer_score;
  private JTextField explain_score;
  private JTextField app_score;
  private JSlider slider_hard;
  //private JSpinner spinner_hard;
  private JSpinner choose_number;
  private JSpinner spinner_fill;
  private JSpinner spinner_trf;
  private JSpinner spinner_answer;
  private JSpinner spinner_explain;
  private JSpinner spinner_application;
  private JComboBox<Integer> point;
  private JComboBox comboBox_hard;
  private JTextField chapter;
  private JLabel score;
  String show_String, acu_String;
  Set<Integer> range_Set = new HashSet<>(48);
  int total_score, part_score, temp_score;
  int choose_score_last, fill_score_last, trf_score_last, answer_score_last, explain_score_last, app_score_last;

  DAOChapter daoChapter;
  DAOPaper daoPaper;
  DAOQuestion daoQuestion;
  QuestionService questionService;

  Config config = Config.buildDefault();

  /**
   * Create the dialog.
   */
  public Choose(JFrame fr, String s) {

    setTitle(s);
    setModal(true);
    setBounds(100, 100, 650, 400);
    getContentPane().setLayout(new BorderLayout());
    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    getContentPane().add(contentPanel, BorderLayout.CENTER);
    contentPanel.setLayout(null);
    {
      JLabel label = new JLabel("Difficulty");
      label.setBounds(54, 22, 70, 18);
      contentPanel.add(label);
    }

    slider_hard = new JSlider(1, 10, 5);
    slider_hard.setMajorTickSpacing(1);
    slider_hard.setMinorTickSpacing(1);
    slider_hard.setPaintLabels(true);
    slider_hard.setPaintTicks(true);
    show_String = acu_String = "";
    total_score = 0;
    part_score = 0;
    temp_score = 0;
    choose_score_last = 0;
    fill_score_last = 0;
    trf_score_last = 0;
    answer_score_last = 0;
    explain_score_last = 0;
    app_score_last = 0;

    slider_hard.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        int x = slider_hard.getValue();
        //spinner_hard.setValue(new Double(x)/100);
        comboBox_hard.setSelectedIndex(x - 1);
      }
    });
    //slider_hard.setMaximum(1);


    slider_hard.setBounds(165, 22, 151, 48);

    contentPanel.add(slider_hard);

    JLabel label = new JLabel("Chapter");
    label.setBounds(54, 91, 54, 15);
    contentPanel.add(label);

    JLabel label_1 = new JLabel("Type");
    label_1.setBounds(386, 22, 75, 15);
    contentPanel.add(label_1);

    choose_number = new JSpinner();
    choose_number.setModel(new SpinnerNumberModel(0, 0, 50, 1));
    choose_number.setBounds(514, 48, 74, 22);
    contentPanel.add(choose_number);

    JLabel label_2 = new JLabel("M-Choice");
    label_2.setBounds(386, 51, 72, 15);
    contentPanel.add(label_2);

    JLabel label_3 = new JLabel("Fill Blanks");
    label_3.setBounds(386, 91, 72, 15);
    contentPanel.add(label_3);

    JLabel label_4 = new JLabel("T or F");
    label_4.setBounds(386, 129, 72, 15);
    contentPanel.add(label_4);

    JLabel label_5 = new JLabel("Q&A");
    label_5.setBounds(386, 161, 72, 15);
    contentPanel.add(label_5);

    JLabel lblNewLabel = new JLabel("Analyze");
    lblNewLabel.setBounds(386, 196, 72, 15);
    contentPanel.add(lblNewLabel);

    JLabel lblNewLabel_1 = new JLabel("Application");
    lblNewLabel_1.setBounds(386, 230, 72, 15);
    contentPanel.add(lblNewLabel_1);

    spinner_fill = new JSpinner();
    spinner_fill.setModel(new SpinnerNumberModel(0, 0, 50, 1));
    spinner_fill.setBounds(514, 88, 74, 22);
    contentPanel.add(spinner_fill);

    spinner_trf = new JSpinner();
    spinner_trf.setModel(new SpinnerNumberModel(0, 0, 50, 1));
    spinner_trf.setBounds(514, 126, 74, 22);
    contentPanel.add(spinner_trf);

    spinner_answer = new JSpinner();
    spinner_answer.setModel(new SpinnerNumberModel(0, 0, 50, 1));
    spinner_answer.setBounds(514, 158, 74, 22);
    contentPanel.add(spinner_answer);

    spinner_explain = new JSpinner();
    spinner_explain.setModel(new SpinnerNumberModel(0, 0, 50, 1));
    spinner_explain.setBounds(514, 190, 74, 22);
    contentPanel.add(spinner_explain);

    spinner_application = new JSpinner();
    spinner_application.setModel(new SpinnerNumberModel(0, 0, 50, 1));
    spinner_application.setBounds(514, 227, 74, 22);
    contentPanel.add(spinner_application);

//		String kno_point[] = {"1","2","3","4","5","6"};
//		point = new JComboBox(kno_point);
    point = new JComboBox<>();

    List<BWChapter> chapters = ((DAOChapter) AppContext.beanAssembler.getBean("daoChapter")).getAll();
    int id = chapters.get(0).id;
    int size = chapters.size();
    List<Integer> ids = new ArrayList<>();
    for (BWChapter c : chapters) {
      point.addItem(c.id);
    }

    point.setMaximumRowCount(20);
    point.setSelectedIndex(1);
    point.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {

        String x = point.getSelectedObjects()[0] + " ";
        show_String += x;
        x = x.trim();
        chapter.setText(show_String);
        range_Set.add(Integer.parseInt(x));
        acu_String += "";

      }
    });
    point.setBounds(175, 88, 141, 21);
    contentPanel.add(point);


//
//		spinner_hard = new JSpinner();		
//		spinner_hard.setModel(new SpinnerNumberModel(0.5, 0.0, 1.0, 0.05));		
//		
//		spinner_hard.addChangeListener(new ChangeListener() {
//			public void stateChanged(ChangeEvent e) {
//				double x = (double) spinner_hard.getValue();
//				slider_hard.setValue(new Integer((int) (x*100)));
//			}
//		});
//		
//		spinner_hard.setBounds(98, 22, 49, 22);
//		contentPanel.add(spinner_hard);

    String string[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    comboBox_hard = new JComboBox(string);
    comboBox_hard.setSelectedIndex(4);
    comboBox_hard.setBounds(118, 22, 43, 21);
    contentPanel.add(comboBox_hard);
    comboBox_hard.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        String x = comboBox_hard.getSelectedObjects()[0] + "";
        slider_hard.setValue(Integer.parseInt(x.trim()));
      }
    });

    JLabel label_6 = new JLabel("Number");
    label_6.setBounds(514, 22, 54, 15);
    contentPanel.add(label_6);

    JLabel label_7 = new JLabel("Points");
    label_7.setBounds(450, 22, 54, 15);
    contentPanel.add(label_7);

    choose_score = new JTextField("0");
    choose_score.setBounds(450, 48, 49, 21);
    contentPanel.add(choose_score);
    choose_score.setColumns(10);
    choose_score.addFocusListener(new focus());
    choose_score.getDocument().addDocumentListener(new doc());

    fill_score = new JTextField("0");
    fill_score.setColumns(10);
    fill_score.setBounds(450, 88, 49, 21);
    contentPanel.add(fill_score);
    fill_score.addFocusListener(new focus());
    fill_score.getDocument().addDocumentListener(new doc());

    trf_score = new JTextField("0");
    trf_score.setColumns(10);
    trf_score.setBounds(450, 126, 49, 21);
    contentPanel.add(trf_score);
    trf_score.addFocusListener(new focus());
    trf_score.getDocument().addDocumentListener(new doc());

    answer_score = new JTextField("0");
    answer_score.setColumns(10);
    answer_score.setBounds(450, 158, 49, 21);
    contentPanel.add(answer_score);
    answer_score.addFocusListener(new focus());
    answer_score.getDocument().addDocumentListener(new doc());

    explain_score = new JTextField("0");
    explain_score.setColumns(10);
    explain_score.setBounds(450, 189, 49, 21);
    contentPanel.add(explain_score);
    explain_score.addFocusListener(new focus());
    explain_score.getDocument().addDocumentListener(new doc());

    app_score = new JTextField("0");
    app_score.setColumns(10);
    app_score.setBounds(450, 227, 49, 21);
    contentPanel.add(app_score);
    app_score.addFocusListener(new focus());
    app_score.getDocument().addDocumentListener(new doc());

    JLabel label_8 = new JLabel("Total Points");
    label_8.setBounds(386, 270, 70, 15);
    contentPanel.add(label_8);

    score = new JLabel("0");
    score.setBounds(514, 270, 34, 15);
    contentPanel.add(score);

    JLabel label_10 = new JLabel("Chapters Added:");
    label_10.setBounds(54, 147, 100, 15);
    contentPanel.add(label_10);

    chapter = new JTextField();
    chapter.setBounds(174, 144, 142, 21);
    contentPanel.add(chapter);


    {
      JPanel buttonPane = new JPanel();
      buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
      getContentPane().add(buttonPane, BorderLayout.SOUTH);
      {
        JButton okButton = new JButton("Finish");
        okButton.setActionCommand("OK");
        okButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent arg0) {
            if (part_score != 100) {
              JOptionPane.showMessageDialog(null, "Need 100 Points In Total", "Error", JOptionPane.WARNING_MESSAGE);
              return;
            }
            float difficulty = Float.parseFloat(comboBox_hard.getSelectedObjects()[0] + "");
            SystemConfig temp = SystemConfig.getDefault();
            difficulty = (difficulty - 1) / 10 * (temp.DIFF_UPPER - temp.DIFF_LOWER) + temp.DIFF_LOWER;
            try {

              Map<QuestionType, Pair<Integer, Integer>> typeMap = new HashMap<QuestionType, Pair<Integer, Integer>>(15);

              int choose_score_int = Integer.parseInt(choose_score.getText());
              int choose_number_int = (int) choose_number.getValue();
              if (choose_number_int > 0 && choose_score_int > 0) {
                typeMap.put(QuestionType.MULTI_CHOICE, new Pair<Integer, Integer>(choose_score_int, choose_number_int));
              }
              int fill_score_int = Integer.parseInt(fill_score.getText());
              int fill_number_int = (int) spinner_fill.getValue();
              if (fill_number_int > 0 && fill_score_int > 0) {
                typeMap.put(QuestionType.FILL_BLANKS, new Pair<Integer, Integer>(fill_score_int, fill_number_int));
              }
              int trf_score_int = Integer.parseInt(trf_score.getText());
              int trf_number_int = (int) spinner_trf.getValue();
              if (trf_number_int > 0 && trf_score_int > 0) {
                typeMap.put(QuestionType.TRUE_FALSE, new Pair<Integer, Integer>(trf_score_int, trf_number_int));
              }
              int answer_score_int = Integer.parseInt(answer_score.getText());
              int answer_number_int = (int) spinner_answer.getValue();
              if (answer_score_int > 0 && answer_number_int > 0) {
                typeMap.put(QuestionType.SIMPLE_QA,
                    new Pair<Integer, Integer>(answer_score_int, answer_number_int));
              }
              int explain_number_int = (int) spinner_explain.getValue();//explain_score_last
              int app_number_int = (int) spinner_application.getValue();//app_score_last
              if (explain_number_int > 0 && explain_score_last > 0) {
                typeMap.put(QuestionType.ANALYZE,
                    new Pair<Integer, Integer>(explain_score_last, explain_number_int));
              }
              if (app_number_int > 0 && app_score_last > 0) {
                typeMap.put(QuestionType.APPLICATION,
                    new Pair<Integer, Integer>(app_score_last, app_number_int));
              }

              if (range_Set.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No chapter", "Error", JOptionPane.ERROR_MESSAGE);
              } else {
                JOptionPane.showMessageDialog(null, "Goal Saved!", "OK", JOptionPane.INFORMATION_MESSAGE);


                config.update(difficulty, range_Set, typeMap);
                Choose.this.dispose();
              }

            } catch (Exception e) {
//              System.out.println("asd");
              JOptionPane.showMessageDialog(null, "Enter a number", "error", JOptionPane.ERROR_MESSAGE);
            }

          }
        });
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);
      }

      JButton button = new JButton("Reset");
      buttonPane.add(button);
      {
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
      }
    }
  }


  public Config getConfig() {
    return config;
  }

  class focus implements FocusListener {
    @Override
    public void focusLost(FocusEvent e) {
      if (e.getSource() == choose_score)
        choose_score_last = temp_score;
      if (e.getSource() == trf_score)
        trf_score_last = temp_score;
      if (e.getSource() == fill_score)
        fill_score_last = temp_score;
      if (e.getSource() == answer_score)
        answer_score_last = temp_score;
      if (e.getSource() == explain_score)
        explain_score_last = temp_score;
      if (e.getSource() == app_score)
        app_score_last = temp_score;

    }

    @Override
    public void focusGained(FocusEvent e) {
      if (e.getSource() == choose_score)
        temp_score = Integer.parseInt(choose_score.getText().trim());
      if (e.getSource() == trf_score)
        temp_score = Integer.parseInt(trf_score.getText().trim());
      if (e.getSource() == fill_score)
        temp_score = Integer.parseInt(fill_score.getText().trim());
      if (e.getSource() == answer_score)
        temp_score = Integer.parseInt(answer_score.getText().trim());
      if (e.getSource() == explain_score)
        temp_score = Integer.parseInt(explain_score.getText().trim());
      if (e.getSource() == app_score)
        temp_score = Integer.parseInt(app_score.getText().trim());
    }
  }

  class doc implements DocumentListener {

    @Override
    public void changedUpdate(DocumentEvent e) {
      // TODO Auto-generated method stub

    }

    @Override
    public void insertUpdate(DocumentEvent e) {
      // TODO Auto-generated method stub
      String stri = "";
      part_score -= temp_score;
      if (e.getDocument() == choose_score.getDocument())
        stri = choose_score.getText();
      if (e.getDocument() == fill_score.getDocument()) {
        stri = fill_score.getText();
        //System.out.println("fill");
      }

      if (e.getDocument() == trf_score.getDocument())
        stri = trf_score.getText();

      if (e.getDocument() == answer_score.getDocument()) {
        stri = answer_score.getText();
        //System.out.println("Choose.doc.insertUpdate()");
        //	System.out.println(stri+"ans" + part_score);
      }
      if (e.getDocument() == explain_score.getDocument())
        stri = explain_score.getText();
      if (e.getDocument() == app_score.getDocument())
        stri = app_score.getText();

      stri = stri.trim();
      try {
        temp_score = Integer.parseInt(stri);
      } catch (Exception e2) {
        JOptionPane.showMessageDialog(null, "Enter a number", "Error", JOptionPane.ERROR_MESSAGE);
      }

//      System.out.println("0 " + stri + " " + part_score + " " + temp_score);
      part_score += temp_score;
//      System.out.println("1 " + stri + " " + part_score + " " + temp_score);
      //System.out.println(part_score+ "part");
      score.setText(part_score + "");
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
      String stri = "";
      part_score -= temp_score;
      if (e.getDocument() == choose_score.getDocument())
        stri = choose_score.getText();

      if (e.getDocument() == fill_score.getDocument())
        stri = fill_score.getText();

      if (e.getDocument() == trf_score.getDocument()) {
        stri = trf_score.getText();

      }

      if (e.getDocument() == answer_score.getDocument()) {
        stri = answer_score.getText();
      }

      if (e.getDocument() == explain_score.getDocument())
        stri = explain_score.getText();

      if (e.getDocument() == app_score.getDocument())
        stri = app_score.getText();

      stri = stri.trim();
      if (stri.length() == 0)
        stri = "0";

      temp_score = Integer.parseInt(stri);
//      System.out.println(stri);
      part_score += temp_score;
      score.setText(part_score + "");
    }
  }
}


