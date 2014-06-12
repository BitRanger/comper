package org.wangk.comper.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import org.wangk.comper.context.AppContext;
import org.wangk.comper.dao.QuestionService;
import org.wangk.comper.feature.model.TestQuestion;
import org.wangk.comper.model.WKQuestionMeta;

public class ModifyQuestion extends JPanel {
	
	private static final long serialVersionUID = 4434770620883212400L;

	static final String[] COLUMN_NAME = {
		"ID","题型", "分值", "难度", "章节", "试卷",
			"详情", "修改", "删除" };

	JTable table = new JTable();
	// MyTableModel myTable;
	MyTableModel myTable = new MyTableModel();
	Map<Integer, WKQuestionMeta> rowToQuestion = new HashMap<>(2048);

	List<WKQuestionMeta> metas;
	Map<Integer, TestQuestion> metaId2Question;
	
	public ModifyQuestion() {
		setBorder(BorderFactory.createLineBorder(Color.darkGray, 1));
		init();
	}

	void updateTableContent() {
		
		final QuestionService questionService = AppContext.beanAssembler
				.getBean("questionService");
		questionService.loadAll();
		metas = questionService.getDaoQuestion().getAll();
		metaId2Question = questionService.getFullQuestion();

		ArrayList<Object[]> tableContent = new ArrayList<>(metas.size());
		for (int i = 0; i < metas.size(); i++) {
			final WKQuestionMeta m = metas.get(i);
			
			rowToQuestion.put(i, m);
			tableContent.add(new Object[]{
				i,
				m.type.getZhName(),
				m.score,
				m.difficulty,
				metaId2Question.get(m.id).chapter.name,
				metaId2Question.get(m.id).paper.name,
				new JButton("详细"),
				new JButton("修改"),
				new JButton("删除")
			});
		}
		myTable.tableContent = tableContent;
		myTable.fireTableDataChanged();
		System.gc();
	}
	
	void init() {
		updateTableContent();
		table.setModel(myTable);
		// { "题型","分值","难度","章节","试卷","详情","修改","删除" };
		// table.getColumn(0).setMaxWidth(50);
		// table.getColumn(1).setMaxWidth(60);
		// table.getColumn(2).setMaxWidth(80);
		// table.getColumn(3).setMaxWidth(200);
		// table.getColumn(4).setMaxWidth(200);

		table.setDefaultRenderer(JButton.class, new ComboBoxCellRenderer());
		JScrollPane scrollPane = new JScrollPane(table);
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
		table.setVisible(true);
		addHandler();
	}

	private void addHandler() {

		final QuestionService questionService = AppContext.beanAssembler
				.getBean("questionService");
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				int column = table.getSelectedColumn();

				WKQuestionMeta meta = rowToQuestion.get(row);
				if (meta == null) {
System.err.println("Fuck null meta at row " + row);
					return;
				}
				
				TestQuestion question = metaId2Question.get(meta.id);

				if (column == COLUMN_NAME.length - 3) {
					// 处理button事件写在这里...
					JOptionPane.showMessageDialog(null, "hello1");

					System.out.println(((JButton) table.getValueAt(row, column))
							.getText());
				}

				if (column == COLUMN_NAME.length - 2) {
					// 处理button事件写在这里...
					JOptionPane.showMessageDialog(null, "hello2");
					System.out.println(((JButton) table.getValueAt(row, column))
							.getText());
				}

				if (column == COLUMN_NAME.length - 1) {
					questionService.delete(meta);
					updateTableContent();
				}
			}
		});
	}

	// public static void main(String[] args) {
	// JFrame frame = new JFrame();
	// frame.add(new Modify());
	// //frame.setSize(new Dimension(1000, 600));
	// frame.setBounds(100, 50, 1100, 600);
	// frame.setVisible(true);
	// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	// }
	//

	class MyTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 8486091548566279385L;
		ArrayList<Object[]> tableContent;


		@Override
		public int getColumnCount() {
			return COLUMN_NAME.length;
		}

		@Override
		public int getRowCount() {
			return tableContent.size();
		}

		@Override
		public Object getValueAt(int r, int c) {
			return tableContent.get(r)[c];
		}

		@Override
		public String getColumnName(int c) {
			return COLUMN_NAME[c];
		}

//		static final String[] COLUMN_NAME = {
//			"ID","题型", "分值", "难度", "章节", "试卷",
//				"详情", "修改", "删除" };
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return columnIndex < 6 ? String.class : JButton.class;
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}

	}
}

class MyCBCellRenderer implements TableCellRenderer {
	
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
