package org.wangk.comper.ui;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainDesign extends JFrame {
	
	private Container c;
	
	//菜单栏
	private JMenuBar menuBar;
	
	//文件菜单
	JMenu menuFile; 
	
	//编辑菜单
	JMenu menuEidt; 
	
	//视图菜单
	JMenu menuView; 
	
	//窗口菜单
	JMenu menuWindow;
	
	//帮助菜单
	JMenu menuHelp;
	
	JMenuItem itemOpen,itemNew,itemCopy,itemPrint,itemPaste,itemSave,itemSavaAs,itemExport;
	
	JLabel lablePre,lableReplace,lableRange,lableScore,lableHard;
	JButton buOk,buSelect;
	
	JTextArea textAreaPreView,textAreaReplace;
	JTextField editRange,editScore,editHard;
	
	JRadioButton radioSemester,radioMonth,radioUnit;
	ButtonGroup radioButtonGroup;
	
	GridBagLayout bagLayout;
	
	public MainDesign(String title) throws HeadlessException {
		super(title);
		// TODO Auto-generated constructor stub
		
		c = getContentPane();
		
		/**
		 * 创建各个菜单
		 */
		
		menuBar =  new JMenuBar();
		menuFile = new JMenu("文件");
		menuEidt = new JMenu("编辑");
		menuView = new JMenu("视图");
		menuWindow = new JMenu("窗口");
		menuHelp = new JMenu("帮助");
		
		/**
		 * 创建各个菜单项
		 */
		itemOpen = new JMenuItem("打开...");
		itemNew = new JMenuItem("新建");
		itemCopy = new JMenuItem("复制");
		itemPrint = new JMenuItem("打印");
		itemPaste = new JMenuItem("粘贴");
		itemSavaAs = new JMenuItem("另存为");
		itemSave = new JMenuItem("保存");
		itemExport = new JMenuItem("导出");
		
		/**
		 * 合并各个菜单选项
		 */
		menuFile.add(itemNew);
		menuFile.add(itemOpen);
		menuFile.add(itemPrint);
		menuFile.add(itemSavaAs);
		menuFile.add(itemSave);
		menuFile.add(itemExport);
		
		menuEidt.add(itemCopy);
		menuEidt.add(itemPaste);
		
		/**
		 * 将各个菜单加入菜单栏
		 */
		
		menuBar.add(menuFile);
		menuBar.add(menuEidt);
		menuBar.add(menuView);
		menuBar.add(menuWindow);
		menuBar.add(menuHelp);
		
		
		/**
		 * 界面的预览试卷组件
		 */
		//lablePre = new JLabel("预览试卷",JLabel.CENTER);
		
		/**
		 * 内容
		 */
		
		lablePre = new JLabel("预览试卷");
		lableReplace =new JLabel("可替换");
		lableRange = new JLabel("范围");
		lableScore = new JLabel("总分数");
		lableHard = new JLabel("难度");
		
		buOk = new JButton("生成试卷");
		buSelect = new JButton("查询");
		
		textAreaPreView = new JTextArea("啊似的发射点啊的事发生地方手动阀手动阀");
		textAreaReplace = new JTextArea("sa啊似的发射点发生大是大非人特瑞特有； 儿童温柔体贴为人体");
		
		radioSemester = new JRadioButton("期末");
		radioMonth = new JRadioButton("月考");
		radioUnit = new JRadioButton("单元测试");
		radioButtonGroup = new ButtonGroup();
		radioButtonGroup.add(radioSemester);
		radioButtonGroup.add(radioMonth);
		radioButtonGroup.add(radioUnit);
		
		editHard = new JTextField("只能输入0到1的数");
		editRange = new JTextField("请点击查询找出范围和知识点",34);
		editScore = new JTextField("输入本试卷的的总分数",34);
		
		bagLayout = new GridBagLayout();
		this.setLayout(bagLayout);	
		
		GridBagConstraints gs = new GridBagConstraints();
		
		gs.gridwidth = 2;
		gs.gridheight = 1;
		gs.insets = new Insets(1, 10, 10, 10);
		gs.weightx = 1.0;
		gs.weighty = 0.0;
		gs.fill = GridBagConstraints.HORIZONTAL;
		bagLayout.setConstraints(lablePre, gs);		
		this.add(lablePre);
		
		gs.gridx = 2;
		bagLayout.setConstraints(lableReplace, gs);
		this.add(lableReplace);
		
		gs.gridx = 0;
		gs.gridy = 1;
		gs.gridwidth = 2;
		gs.gridheight = 10;
		gs.weighty = 1.0;
		gs.fill = GridBagConstraints.VERTICAL;
		bagLayout.setConstraints(textAreaPreView, gs);
		textAreaPreView.setColumns(45);
		
		this.add(textAreaPreView);
		
		gs.gridx = 2;
		gs.gridy = 1;
		gs.gridwidth = 0;
		gs.gridheight = 1;
		gs.weighty = 0;
		gs.gridheight = 5;
		gs.fill = GridBagConstraints.BOTH;
		bagLayout.setConstraints(textAreaReplace, gs);
		textAreaReplace.setColumns(40);
		textAreaReplace.setRows(20);
		this.add(textAreaReplace);
		
		gs.gridx = 2;
		gs.gridy = 6;
		gs.gridheight = 1;
		gs.gridwidth = 1;
		//gs.fill = GridBagConstraints.HORIZONTAL;
		bagLayout.setConstraints(radioSemester, gs);
		this.add(radioSemester);
		
		gs.gridx = 3;
		gs.gridy = 6;
		gs.gridwidth = 1;
		gs.gridheight = 1;
		//gs.fill = GridBagConstraints.HORIZONTAL;
		bagLayout.setConstraints(radioMonth, gs);
		this.add(radioMonth);
		
		gs.gridx = 4;
		gs.gridy = 6;
		gs.gridwidth = 1;
		gs.gridheight = 1;
		//gs.fill = GridBagConstraints.HORIZONTAL;
		bagLayout.setConstraints(radioUnit, gs);
		this.add(radioMonth);
		
				
		gs.gridx = 2;
		gs.gridy = 7;
		gs.gridheight = 1;
		gs.fill = GridBagConstraints.HORIZONTAL;
		bagLayout.setConstraints(lableRange, gs);
		this.add(lableRange);
		

		gs.gridx = 3;
		gs.gridy = 7;
		gs.fill = GridBagConstraints.HORIZONTAL;
		bagLayout.setConstraints(buSelect, gs);
		this.add(buSelect);
		
		gs.gridx = 2;
		gs.gridy = 8;
		gs.fill = GridBagConstraints.HORIZONTAL;
		bagLayout.setConstraints(lableScore, gs);
		this.add(lableScore);
		

		gs.gridx = 3;
		gs.gridy = 8;
		gs.fill = GridBagConstraints.HORIZONTAL;
		bagLayout.setConstraints(editScore, gs);
		this.add(editScore);
		
		gs.gridx = 2;
		gs.gridy = 9;
		gs.fill = GridBagConstraints.HORIZONTAL;
		bagLayout.setConstraints(lableHard, gs);
		this.add(lableHard);
		

		gs.gridx = 3;
		gs.gridy = 9;
		gs.fill = GridBagConstraints.HORIZONTAL;
		bagLayout.setConstraints(editHard, gs);
		this.add(editHard);
		
		gs.gridx = 3;
		gs.gridy =10 ;
		bagLayout.setConstraints(buOk, gs);
		this.add(buOk);
		
		this.setJMenuBar(menuBar);
		this.setSize(1100,700);
		this.setVisible(true);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	

	public static void main(String[] s)
	{
		MainDesign f = new MainDesign("组卷系统");
	}
	
	
	
}
