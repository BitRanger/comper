package org.wangk.comper.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.wangk.comper.context.AppContext;
import org.wangk.comper.feature.Config;
import org.wangk.comper.feature.IEvaluator;
import org.wangk.comper.feature.IRandomGenerator;
import org.wangk.comper.feature.TrainingField;
import org.wangk.comper.feature.impl.Evaluator;
import org.wangk.comper.feature.impl.RandomGenerator;
import org.wangk.comper.feature.impl.Trainer;
import org.wangk.comper.feature.model.Group;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.util.List;

public class Ui {

	JFrame frame;
	Config config;
	private JLabel label_score;
	private JLabel label_hard;
	private JLabel label_range ;
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

	Container c;

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
		frame.getContentPane().setBackground(new Color(220, 220, 220));
		backSolution = new String[]{"最佳方案","第二方案","第三方案"};
		result = new String[]{"最佳方案","第二方案","第三方案"};
		
		/*
		result[0]="	result = new String[1];";
		result[1]="	result = new String[2];";
		result[2]="	result = new String[3];";
		//frame.setIgnoreRepaint(true);
		//frame.setSize(1500, 900);
	*/
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
				Modify modify = new Modify();
				
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
/*
		list_pre = new JList();
		list_pre.setValueIsAdjusting(true);
		list_pre.setSize(new Dimension(1300, 900));
		list_pre.setToolTipText("生成的试卷预览");
		list_pre.setBounds(10, 80, 614, 507);
		frame.getContentPane().add(list_pre);
		
		*/

		JButton button_choose = new JButton("选择");
		button_choose.setFont(new Font("宋体", Font.PLAIN, 20));
		button_choose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Choose choose = new Choose(new javax.swing.JFrame(),"选择");				
				choose.setVisible(true);
				config = choose.getConfig();
System.out
		.println("Ui.initialize().new ActionListener() {...}.actionPerformed()");
System.out.println(config);
				if(config!=null)
					setAll();
			}
		});
		
		button_choose.setBounds(957, 219, 87, 34);
		frame.getContentPane().add(button_choose);

		label_hard = new JLabel("难度");
		label_hard.setFont(new Font("宋体", Font.PLAIN, 14));
		label_hard.setBounds(694, 311, 98, 34);
		frame.getContentPane().add(label_hard);

		label_range = new JLabel("范围");
		label_range.setFont(new Font("宋体", Font.PLAIN, 14));
		label_range.setBounds(694, 395, 253, 34);
		frame.getContentPane().add(label_range);

		JButton button_done = new JButton("生成试卷");
		button_done.setFont(new Font("宋体", Font.PLAIN, 20));
		button_done.setBounds(694, 508, 122, 47);
		frame.getContentPane().add(button_done);
		button_done.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				comboBox_replace.addItem(backSolution[0]);
				comboBox_replace.addItem(backSolution[1]);
				comboBox_replace.addItem(backSolution[2]);				
				if(getPaper())
					JOptionPane.showMessageDialog(null, "生成成功", "成功", JOptionPane.INFORMATION_MESSAGE);
				textArea.setText(result[0]);
				
			}
		});
		//String backSolution[3] ={"最佳方案","第二方案","第三方案"};
		comboBox_replace = new JComboBox<String>();
		comboBox_replace.setBounds(694, 80, 356, 96);
		frame.getContentPane().add(comboBox_replace);
		comboBox_replace.setFont(new Font("宋体", Font.PLAIN, 41));
		//comboBox_replace.add
		
		 ActionListener actionListener = new ActionListener() {
		      @Override
			public void actionPerformed(ActionEvent actionEvent) {

		    	  String x = comboBox_replace.getSelectedObjects()[0]+"";
		    	  x = x.trim();
					for (int i = 0; i < 3; i++) 
					{
						if(x.equals(backSolution[i]))
						{
							textArea.setText(result[i]);
							break;
						}
							
					}
		      }
		    };
		
		
		comboBox_replace.addActionListener(actionListener);
		
		
		

		JLabel label_2 = new JLabel("备选方案");
		label_2.setFont(new Font("宋体", Font.PLAIN, 20));
		label_2.setBounds(695, 39, 97, 31);
		frame.getContentPane().add(label_2);

		label_score = new JLabel("总分");
		label_score.setFont(new Font("宋体", Font.PLAIN, 14));
		label_score.setBounds(694, 224, 98, 29);
		frame.getContentPane().add(label_score);

		JButton button = new JButton("修改");
		button.setFont(new Font("宋体", Font.PLAIN, 20));
		button.setBounds(933, 508, 118, 47);
		frame.getContentPane().add(button);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Choose choose = new Choose(new javax.swing.JFrame(),"选择");				
				choose.setVisible(true);
				config = choose.getConfig();
				if (config != null) {
					setAll();
				}
			}
		});
		
		
		textArea = new JTextArea("试卷预览");
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setBounds(20, 72, 560, 483);
		frame.getContentPane().add(textArea);
		
		JScrollPane js = new JScrollPane(textArea);
		js.setBounds(20, 72, 560, 483);
		frame.getContentPane().add(js);

		JButton btnHtml = new JButton("导出HTML");
		btnHtml.setFont(new Font("宋体", Font.PLAIN, 20));
		btnHtml.setBounds(435, 27, 137, 39);
		frame.getContentPane().add(btnHtml);
		btnHtml.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(config==null)
				{
					JOptionPane.showMessageDialog(null, "error","123123",JOptionPane.ERROR_MESSAGE);
					return;
				}
				// TODO Auto-generated method stub
				//if(config.getDifficulty() == null)
				System.out.println("hard"+config.getDifficulty());
				//setAll();
			}
		});		
	}
	
	public void setAll() {
		label_hard.setText("难度   :"  + config.getDifficulty());
		label_score.setText("总分   :" + config.getTotalScore());
		String string = "范围   :";
		for (Integer i : config.getChapterIdSet()) {
			string+=i+ "; " ;
		}
		
		label_range.setText(string);
		
		
		/**
		 * 计算结果：
		 */
		IEvaluator evaluator = new Evaluator();
		Trainer trainer = new Trainer();
		trainer.setQuestionSerive(AppContext.questionService);
		
		IRandomGenerator rGenerator = new RandomGenerator();

		field.setEvaluator(evaluator);
		field.setRandomGenerator(rGenerator);
		field.setTrainer(trainer);
	}
	
	/*
	 * 遗传算法的到试卷
	 */
	public boolean getPaper()
	{
		System.out.println("Ui.getPaper()");
		field.setConfig(config);
		field.prepare();
		System.out.println("Ui.getPaper()");
		field.train();
		System.out.println("Ui.getPaper()");
		List<Group> groups = field.getSortedResult();
		System.out.println("Ui.getPaper()");
		result[0] = AppContext.questionService.toPaper(groups.get(groups.size() - 1)).toString();
		result[1] = AppContext.questionService.toPaper(groups.get(groups.size() - 2)).toString();
		result[2] = AppContext.questionService.toPaper(groups.get(groups.size() - 3)).toString();
		
//		result[0] = groups.get(groups.size() - 1).toString();
//		result[1] = groups.get(groups.size() - 2).toString();
//		result[2] = groups.get(groups.size() - 3).toString();
//		System.out.println(result[0]);
		return true;
	}
	
}
