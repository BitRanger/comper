package org.wangk.comper.ui;

import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.wangk.comper.context.AppContext;
import org.wangk.comper.context.XMLBeanAssembler;
import org.wangk.comper.context.config.InputStreamCallback;
import org.wangk.comper.context.config.InputStreamProvider;
import org.wangk.comper.context.config.InputStreamSupport;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.io.InputStream;

public class Ui {

	JFrame frame;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		AppContext.setUp();
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Ui window = new Ui();
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

	private Container c;

	// 菜单栏
	private JMenuBar menuBar;

	// 文件菜单
	JMenu menuFile;

	// 编辑菜单
	JMenu menuEidt;

	// 视图菜单
	JMenu menuView;

	// 窗口菜单
	JMenu menuWindow;

	// 帮助菜单
	JMenu menuHelp;

	JMenuItem itemOpen, itemNew, itemCopy, itemPrint, itemPaste, itemSave,
			itemSavaAs, itemExport,itemModify,itemEnter;

	public Ui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		//frame.setIgnoreRepaint(true);
		//frame.setSize(1500, 900);

		c = frame.getContentPane();
		

		/**
		 * 创建各个菜单
		 */

		menuBar = new JMenuBar();
		menuFile = new JMenu("文件");
		menuEidt = new JMenu("编辑");
		menuView = new JMenu("视图");
		menuWindow = new JMenu("窗口");
		menuHelp = new JMenu("帮助");

		/**
		 * 创建各个菜单项
		 */
		itemEnter = new JMenuItem("录入题库");
		itemEnter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Enter enter = new Enter("录入题库");
				enter.setVisible(true);
			}
		});
		itemOpen = new JMenuItem("打开...");
		itemNew = new JMenuItem("新建");
		itemCopy = new JMenuItem("复制");
		itemPrint = new JMenuItem("打印");
		itemPaste = new JMenuItem("粘贴");
		itemSavaAs = new JMenuItem("另存为");
		itemSave = new JMenuItem("保存");
		itemExport = new JMenuItem("导出");
		itemModify = new JMenuItem("题库修改");
		itemModify.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Modify modify = new Modify("修改题库");
				modify.setVisible(true);
			}
		});

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
		menuEidt.add(itemModify);
		menuEidt.add(itemEnter);

		/**
		 * 将各个菜单加入菜单栏
		 */

		menuBar.add(menuFile);
		menuBar.add(menuEidt);
		menuBar.add(menuView);
		menuBar.add(menuWindow);
		menuBar.add(menuHelp);

		frame.setJMenuBar(menuBar);

		frame.setBounds(100, 100, 1132, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("试卷预览");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 27, 118, 43);
		frame.getContentPane().add(lblNewLabel);

		JList list_pre = new JList();
		list_pre.setValueIsAdjusting(true);
		list_pre.setSize(new Dimension(1300, 900));
		list_pre.setToolTipText("生成的试卷预览");
		list_pre.setBounds(10, 80, 614, 507);
		frame.getContentPane().add(list_pre);

		JButton button_choose = new JButton("选择");
		button_choose.setFont(new Font("宋体", Font.PLAIN, 20));
		button_choose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Choose choose = new Choose("选择");
				choose.setVisible(true);
				
				
			}
		});
		button_choose.setBounds(957, 219, 87, 34);
		frame.getContentPane().add(button_choose);

		JLabel label_hard = new JLabel("难度");
		label_hard.setFont(new Font("宋体", Font.PLAIN, 24));
		label_hard.setBounds(694, 311, 98, 34);
		frame.getContentPane().add(label_hard);

		JLabel label_range = new JLabel("范围");
		label_range.setFont(new Font("宋体", Font.PLAIN, 24));
		label_range.setBounds(694, 395, 98, 34);
		frame.getContentPane().add(label_range);

		JButton button_done = new JButton("生成试卷");
		button_done.setFont(new Font("宋体", Font.PLAIN, 20));
		button_done.setBounds(694, 508, 122, 47);
		frame.getContentPane().add(button_done);

		JComboBox comboBox_replace = new JComboBox();
		comboBox_replace.setBounds(694, 80, 356, 96);
		frame.getContentPane().add(comboBox_replace);

		JLabel label_2 = new JLabel("备选方案");
		label_2.setFont(new Font("宋体", Font.PLAIN, 20));
		label_2.setBounds(695, 39, 97, 31);
		frame.getContentPane().add(label_2);

		JLabel label_score = new JLabel("总分");
		label_score.setFont(new Font("宋体", Font.PLAIN, 24));
		label_score.setBounds(694, 224, 98, 29);
		frame.getContentPane().add(label_score);

		JButton button = new JButton("修改");
		button.setFont(new Font("宋体", Font.PLAIN, 20));
		button.setBounds(933, 508, 118, 47);
		frame.getContentPane().add(button);

		JButton btnxml = new JButton("导出HTML");
		btnxml.setFont(new Font("宋体", Font.PLAIN, 20));
		btnxml.setBounds(435, 27, 137, 39);
		frame.getContentPane().add(btnxml);
	}
}
