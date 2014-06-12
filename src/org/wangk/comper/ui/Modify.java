package org.wangk.comper.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import org.wangk.comper.context.AppContext;
import org.wangk.comper.dao.DAOQuestion;
import org.wangk.comper.dao.QuestionService;
import org.wangk.comper.feature.model.TestQuestion;
import org.wangk.comper.model.WKQuestionContent;
import org.wangk.comper.model.WKQuestionMeta;

public class Modify extends JPanel {
	
//	
	private static final long serialVersionUID = 1L;
	static final String[] headName = 
		{ "id","题型","分值","难度","章节","试卷","详情","修改","删除" };
	
	JTable table;
	//MyTableModel myTable;
	AbstractTableModel myTable;
	private JScrollPane scrollPane;
	private JButton[] bu_detail;
	private JButton[] bu_modify;
	private JButton[] bu_del;
	
	List<WKQuestionMeta> metas;
	Map<Integer, TestQuestion> metaId2Question;
	int lines;
	Map<Integer, WKQuestionMeta> rowToQuestion = new HashMap<>(2048);
	
	public Modify() {
		setBorder(BorderFactory.createLineBorder(Color.red, 1));
		init();
	}

	void init() {
//		headName = new String[10];
//		headName
		System.out.println("Modify.init()");
		QuestionService questionService = AppContext.beanAssembler.getBean("questionService");
		questionService.loadAll();
		metas = questionService.getDaoQuestion().getAll();
		metaId2Question = questionService.getFullQuestion();
		
//		DAOQuestion daoQuestion = questionService.getDaoQuestion();
		
		lines = metas.size();
		System.out.println("lines "+lines);
		bu_detail = new JButton[lines];
		bu_modify = new JButton[lines];
		bu_del = new JButton[lines];
		for (int i = 0; i < lines; i++) {
			bu_detail[i] = new JButton("详细");
			bu_modify[i] = new JButton("修改");
			bu_del[i] = new JButton("删除");
		}		
		
		Object[][] obj = new Object[lines][]; 
		for(int i = 0; i != metas.size(); ++i) {
			obj[i] = new Object[headName.length];
			WKQuestionMeta meta = metas.get(i);
			TestQuestion question = metaId2Question.get(meta.id);
			
			obj[i][0] = i;
			obj[i][1] = meta.type.getZhName();
			obj[i][2] = meta.score;
			obj[i][3] = meta.difficulty;
			obj[i][4] = question.chapter.name;
			obj[i][5] = question.paper.name;

			obj[i][6] = bu_detail[i];
			obj[i][7] = bu_modify[i];
			obj[i][8] = bu_del[i];
			
//			obj[i][0] = meta.type.getZhName();
//			obj[i][1] = meta.score;
//			obj[i][2] = meta.difficulty;
//			obj[i][3] = question.chapter.name;
//			obj[i][4] = question.paper.name;
//
//			obj[i][5] = bu_detail[i];
//			obj[i][6] = bu_modify[i];
//			obj[i][7] = bu_del[i];
			rowToQuestion.put(i, meta);
		}

		myTable = new MyTableModel(headName,obj);
	
		table = new JTable(myTable);

//		{ "题型","分值","难度","章节","试卷","详情","修改","删除" };
//		table.getColumn(0).setMaxWidth(50);
//		table.getColumn(1).setMaxWidth(60);
//		table.getColumn(2).setMaxWidth(80);
//		table.getColumn(3).setMaxWidth(200);
//		table.getColumn(4).setMaxWidth(200);
		
		
		table.setDefaultRenderer(JButton.class, new ComboBoxCellRenderer());
		scrollPane = new JScrollPane(table);
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
		addHandler();
	}

	private void addHandler() {
		// 添加事件
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out
						.println("Modify.addHandler().new MouseAdapter() {...}.mouseClicked()");
				
				int row = table.getSelectedRow();
				int column = table.getSelectedColumn();			
				System.out
						.println("Modify.addHandler().new MouseAdapter() {...}.mouseClicked()");
				
				System.out.println("row=" + row + ":" + "column=" + column);
				
				WKQuestionMeta meta = rowToQuestion.get(row);
				TestQuestion question = metaId2Question.get(meta.id);
				
				if (column == headName.length -3) {
					// 处理button事件写在这里...
					JOptionPane.showMessageDialog(null, "hello1");
					
					System.out.println(((JButton) table.getValueAt(row, column))
							.getText());
				}				
				
				if (column == headName.length -2) {
					// 处理button事件写在这里...
					JOptionPane.showMessageDialog(null, "hello2");
					System.out.println(((JButton) table.getValueAt(row, column))
							.getText());					
				}
				
				if (column == headName.length -1) {
					// 处理button事件写在这里...
					JOptionPane.showMessageDialog(null, "hello3");
					System.out.println(((JButton) table.getValueAt(row, column))
							.getText());
					System.out
							.println("Modify.addHandler().new MouseAdapter() {...}.mouseClicked()");
					QuestionService questionService = AppContext.beanAssembler.getBean("questionService");
					questionService.delete(meta);
					
					myTable.fireTableRowsDeleted(0, lines-1);
					
					init();
					table.setModel(myTable);
					validate();				
					
					System.out.println("=========== init ============");
				}
			}
		});
	}

//	public static void main(String[] args) {
//		JFrame frame = new JFrame();
//		frame.add(new Modify());
//		//frame.setSize(new Dimension(1000, 600));
//		frame.setBounds(100, 50, 1100, 600);
//		frame.setVisible(true);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}
//	

	class MyTableModel extends AbstractTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String headName[];
		private Object obj[][];

		public MyTableModel() {
			super();
		}

		public MyTableModel(String[] headName, Object[][] obj) {
			this.headName = headName;
			this.obj = obj;
		}

		@Override
		public int getColumnCount() {
			return headName.length;
		}

		@Override
		public int getRowCount() {
			return obj.length;
		}

		@Override
		public Object getValueAt(int r, int c) {
			return obj[r][c];
		}

		@Override
		public String getColumnName(int c) {
			return headName[c];
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return obj[0][columnIndex].getClass();
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {

			return false;
		}

	}
}

class ComboBoxCellRenderer implements TableCellRenderer {
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		JButton cmb = (JButton) value;
		if (isSelected) {
			cmb.setForeground(table.getSelectionForeground());
			cmb.setBackground(table.getSelectionBackground());
		} else {
			cmb.setForeground((unselectedForeground != null) ? unselectedForeground
					: table.getForeground());
			cmb.setBackground((unselectedBackground != null) ? unselectedBackground
					: table.getBackground());
		}
		cmb.setFont(table.getFont());
		if (hasFocus) {
			cmb.setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
			if (!isSelected && table.isCellEditable(row, column)) {
				Color col;
				col = UIManager.getColor("Table.focusCellForeground");
				if (col != null) {
					cmb.setForeground(col);
				}
				col = UIManager.getColor("Table.focusCellBackground");
				if (col != null) {
					cmb.setBackground(col);
				}
			}
		} else {
			cmb.setBorder(noFocusBorder);
		}
		return cmb;
	}

	protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

	private Color unselectedForeground;
	private Color unselectedBackground;
}