
package com.caibowen.comper.ui;

import com.caibowen.comper.feature.Config;
import com.caibowen.comper.feature.builder.IHtmlGen;
import com.caibowen.comper.feature.model.TestPaper;
import com.caibowen.comper.AppContext;
import com.caibowen.comper.dao.QuestionService;
import com.caibowen.comper.feature.TrainingField;
import com.caibowen.comper.feature.model.Group;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 *
 * @author BowenCai
 *
 */
public class MainUi {

  JFrame frame;
  Config config;
  IHtmlGen htmlGenerator;

  private JLabel label_score;
  private JLabel label_hard;
  private JLabel label_range;
  //private JList list_pre ;

  JTextArea textArea;
  JComboBox<String> comboBox_replace;
  String result[];
  String backSolution[];


  TrainingField field = new TrainingField();

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    AppContext.setUp();
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        try {
          MainUi window = new MainUi();
          window.frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the application.
   */

  Container c;

  private JMenuBar menuBar;

  JMenu menuFile;

  JMenu menuEidt;

  JMenu menuAdd;

  JMenu menuModify;

  JMenu menuView;

  JMenu menuWindow;

  JMenu menuHelp;

  JMenuItem itemOpen, itemNew, itemCopy, itemPrint, itemPaste, itemSave,
      itemSavaAs, itemExport, itemAddQuestion, itemAddChapter, itemAddPaper,
      itemModifyQuestion, itemModifCHapter, itemModifyPaper, itemHelp;

  public MainUi() {
    initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    frame = new JFrame("Test Paper Auto-Composition");
    frame.getContentPane().setBackground(new Color(220, 220, 220));
    backSolution = new String[]{"Best Comb", "Second", "Third"};
    result = new String[]{"Best Comb", "Second", "Third"};

		/*
		result[0]="	result = new String[1];";
		result[1]="	result = new String[2];";
		result[2]="	result = new String[3];";
		//frame.setIgnoreRepaint(true);
		//frame.setSize(1500, 900);
	*/
    c = frame.getContentPane();

    menuBar = new JMenuBar();
    menuFile = new JMenu("File");//
    menuWindow = new JMenu("Window");
    menuModify = new JMenu("View");//
    menuAdd = new JMenu("Add");//
    menuHelp = new JMenu("Help");//


    itemOpen = new JMenuItem("Open...");
    itemNew = new JMenuItem("New");
    itemCopy = new JMenuItem("Copy");
    itemPrint = new JMenuItem("Print");
    itemPaste = new JMenuItem("Paste");
    itemSavaAs = new JMenuItem("Save As");
    itemSave = new JMenuItem("Save");
    itemExport = new JMenuItem("Export");

    itemExport.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        if (config == null) {
          JOptionPane.showMessageDialog(null, "Please Generate A Test First", "error", JOptionPane.ERROR_MESSAGE);
          return;
        }

        //setAll();
        JOptionPane.showMessageDialog(null, "Saved to \"test_paper.html\"", "Success", JOptionPane.INFORMATION_MESSAGE);
        htmlGenerator.output("test_paper");

      }
    });

    itemModifyQuestion = new JMenuItem("View Question");
    itemModifyQuestion.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        JFrame frame = new JFrame();
        frame.setTitle("View Question");
        frame.add(new ModifyQuestion());
        frame.setBounds(100, 50, 1100, 600);
        frame.setVisible(true);
      }
    });
    itemModifCHapter = new JMenuItem("View Chapter");
    itemModifCHapter.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        JFrame frame = new JFrame();
        frame.setTitle("View Chapter");
//				frame.add(new Modify());
        frame.add(new ModifyChapter());
        //frame.setSize(new Dimension(1000, 600));
        frame.setBounds(100, 50, 1100, 600);
        frame.setVisible(true);

      }
    });

    itemModifyPaper = new JMenuItem("View Test");
    itemModifyPaper.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        JFrame frame = new JFrame();
        frame.setTitle("View Test");
//				frame.add(new Modify());
        frame.add(new ModifyPaper());
        //frame.setSize(new Dimension(1000, 600));
        frame.setBounds(100, 50, 1100, 600);
        frame.setVisible(true);
      }
    });

    itemAddQuestion = new JMenuItem("Add Question");
    itemAddQuestion.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        AddQuestion enter = new AddQuestion("Add Question");
        enter.setVisible(true);
      }
    });

    itemAddPaper = new JMenuItem("Add Test");
    itemAddPaper.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        AddPaper paper = new AddPaper();
        paper.setVisible(true);

      }
    });

    itemAddChapter = new JMenuItem("Add Chapter");
    itemAddChapter.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        AddChapter chapter = new AddChapter();
        chapter.setVisible(true);
      }
    });

    itemHelp = new JMenuItem("Version");
    itemHelp.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        JOptionPane.showMessageDialog(null, " Auto Test Generator\n Author: Bowen Cai\n Version1.0.3", "Help", JOptionPane.INFORMATION_MESSAGE);
      }
    });

    /**
     * 合并各个菜单选项
     */
    //menuFile.add(itemNew);
    //menuFile.add(itemOpen);
    //menuFile.add(itemPrint);
    //menuFile.add(itemSavaAs);
    //menuFile.add(itemSave);
    menuFile.add(itemExport);

//		menuEidt.add(itemCopy);
//		menuEidt.add(itemPaste);
    menuModify.add(itemModifyQuestion);
    menuModify.add(itemModifCHapter);
    menuModify.add(itemModifyPaper);

    //menuEidt.add(itemAddQuestion);
    //menuEidt.add(itemAddChapter);
    //menuEidt.add(itemAddPaper);

    menuAdd.add(itemAddQuestion);
    menuAdd.add(itemAddChapter);
    menuAdd.add(itemAddPaper);

    menuHelp.add(itemHelp);

    /**
     * 将各个菜单加入菜单栏
     */

    menuBar.add(menuFile);
    menuBar.add(menuModify);
    //menuBar.add(menuView);
    menuBar.add(menuAdd);
    menuBar.add(menuHelp);

    frame.setJMenuBar(menuBar);

    frame.setBounds(100, 100, 1132, 700);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(null);

    JLabel lblNewLabel = new JLabel("Preview Test");
    lblNewLabel.setFont(new Font("MS Sans Serif", Font.PLAIN, 20));
    lblNewLabel.setBounds(10, 27, 118, 43);

    JButton button_choose = new JButton("Config");
    button_choose.setFont(new Font("MS Sans Serif", Font.PLAIN, 14));
    button_choose.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        Choose choose = new Choose(new JFrame(), "Config");
        choose.setVisible(true);
        config = choose.getConfig();
        if (config != null)
          setAll();
      }
    });

    button_choose.setBounds(957, 219, 87, 34);
    frame.getContentPane().add(button_choose);

    label_hard = new JLabel("Difficulty");
    label_hard.setFont(new Font("MS Sans Serif", Font.PLAIN, 14));
    label_hard.setBounds(694, 311, 98, 34);
    frame.getContentPane().add(label_hard);

    label_range = new JLabel("Scope");
    label_range.setFont(new Font("MS Sans Serif", Font.PLAIN, 14));
    label_range.setBounds(694, 395, 253, 34);
    frame.getContentPane().add(label_range);

    JButton button_done = new JButton("Generate");
    button_done.setFont(new Font("MS Sans Serif", Font.PLAIN, 20));
    button_done.setBounds(694, 508, 122, 47);
    frame.getContentPane().add(button_done);
    button_done.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub

        if (config == null)
          JOptionPane.showMessageDialog(null, "Please config", "error", JOptionPane.WARNING_MESSAGE);
        else {
          comboBox_replace.addItem(backSolution[0]);
          comboBox_replace.addItem(backSolution[1]);
          comboBox_replace.addItem(backSolution[2]);
          if (getPaper())
            JOptionPane.showMessageDialog(null, "Finished", "Success", JOptionPane.INFORMATION_MESSAGE);
          textArea.setText(result[0]);
        }


      }
    });

    comboBox_replace = new JComboBox<String>();
    comboBox_replace.setBounds(694, 80, 356, 96);
    frame.getContentPane().add(comboBox_replace);
    comboBox_replace.setFont(new Font("MS Sans Serif", Font.PLAIN, 41));
    //comboBox_replace.add

    ActionListener actionListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {

        String x = comboBox_replace.getSelectedObjects()[0] + "";
        x = x.trim();
        for (int i = 0; i < 3; i++) {
          if (x.equals(backSolution[i])) {
            textArea.setText(result[i]);
            break;
          }

        }
      }
    };


    comboBox_replace.addActionListener(actionListener);


    JLabel label_2 = new JLabel("Candidates:");
    label_2.setFont(new Font("MS Sans Serif", Font.PLAIN, 16));
    label_2.setBounds(695, 39, 97, 31);
    frame.getContentPane().add(label_2);

    label_score = new JLabel("Total Points");
    label_score.setFont(new Font("MS Sans Serif", Font.PLAIN, 14));
    label_score.setBounds(690, 224, 100, 30);
    frame.getContentPane().add(label_score);

    JButton button = new JButton("Modify");
    button.setFont(new Font("MS Sans Serif", Font.PLAIN, 20));
    button.setBounds(933, 508, 118, 47);
    frame.getContentPane().add(button);
    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        Choose choose = new Choose(new JFrame(), "Select");
        choose.setVisible(true);
        config = choose.getConfig();
        if (config != null) {
          setAll();
        }
      }
    });


    textArea = new JTextArea("Test Preview");
    textArea.setLineWrap(true);
    textArea.setEditable(false);
    textArea.setBounds(20, 72, 560, 483);
    frame.getContentPane().add(textArea);

    JScrollPane js = new JScrollPane(textArea);
    js.setBounds(20, 72, 560, 483);
    frame.getContentPane().add(js);

    JButton btnHtml = new JButton("Export To HTML");
    btnHtml.setFont(new Font("MS Sans Serif", Font.PLAIN, 16));
    btnHtml.setBounds(357, 27, 215, 39);
    frame.getContentPane().add(btnHtml);
    btnHtml.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        if (config == null) {
          JOptionPane.showMessageDialog(null, "Please Generate A Test First", "error", JOptionPane.ERROR_MESSAGE);
          return;
        }
//        System.out
//            .println("MainUi.initialize().new ActionListener() {...}.actionPerformed()");
//        System.out.println("difficulty: " + config.getDifficulty());
        //setAll();
        JOptionPane.showMessageDialog(null, "Saved to \"test_paper.html\"", "Success", JOptionPane.INFORMATION_MESSAGE);
        htmlGenerator.output("test_paper");
      }
    });
  }

  public void setAll() {
    float df = config.getDifficulty();
    df *= 10;
    int df1 = (int)df;
    int df2 = (int) (10 * (df - df1));
    label_hard.setText("Difficulty : 0." + df1 + df2);
    label_score.setText("Points: " + config.getTotalScore());
    String string = "Scope :";
    for (Integer i : config.getChapterIdSet()) {
      string += i + "; ";
    }

    label_range.setText(string);

    field = AppContext.beanAssembler.getBean("trainingField");
  }


  public boolean getPaper() {

    System.out.println(config);

    field.setConfig(config);
    field.prepare();

    field.train();

    List<Group> groups = field.getSortedResult();

    htmlGenerator = AppContext.beanAssembler.getBean("htmlGenerator");
    htmlGenerator.clear();
    htmlGenerator.setConfig(config);

    QuestionService questionService = AppContext.beanAssembler.getBean("questionService");
    TestPaper paper = questionService.toPaper(groups.get(groups.size() - 1));
    htmlGenerator.add(paper);
    result[0] = paper.toString();

    paper = questionService.toPaper(groups.get(groups.size() - 2));
    htmlGenerator.add(paper);
    result[1] = paper.toString();

    paper = questionService.toPaper(groups.get(groups.size() - 3));
    htmlGenerator.add(paper);
    result[2] = paper.toString();

//		result[0] = groups.get(groups.size() - 1).toString();
//		result[1] = groups.get(groups.size() - 2).toString();
//		result[2] = groups.get(groups.size() - 3).toString();
//		System.out.println(result[0]);
    return true;
  }

}
