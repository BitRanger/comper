package org.wangk.comper.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
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
import javax.swing.table.AbstractTableModel;

import org.wangk.comper.context.AppContext;
import org.wangk.comper.dao.DAOChapter;
import org.wangk.comper.dao.DAOPaper;
import org.wangk.comper.model.WKChapter;
import org.wangk.comper.model.WKPaper;

public class ModifyChapter extends JPanel {
	
	private static final long serialVersionUID = 4434770620883212400L;

	static final String[] COLUMN_NAME = {
		"ID","名称", "备注","发布日期",
			"详情", "修改", "删除" };

	JTable table = new JTable();
	MyTableModel myTable = new MyTableModel();
	Map<Integer, WKChapter> rowToChapter = new HashMap<>(2048);

	List<WKChapter> chapters;
	
	public ModifyChapter() {
		setBorder(BorderFactory.createLineBorder(Color.darkGray, 1));
		init();
	}
static SimpleDateFormat FORMATTER = new SimpleDateFormat("YY-MM-DD");

	void updateTableContent() {
		// headName = new String[10];
		// headName
		final DAOChapter daoChapter = AppContext.beanAssembler
				.getBean("daoChapter");
		
		chapters = daoChapter.getAll();

		ArrayList<Object[]> tableContent = new ArrayList<>(chapters.size());
		for (int i = 0; i < chapters.size(); i++) {
			final WKChapter m = chapters.get(i);
			rowToChapter.put(i, m);
//			"ID","名称", "备注","发布日期",
//			"详情", "修改", "删除" };
			tableContent.add(new Object[]{
				Integer.toString(i),
				m.name,
				m.description,
				FORMATTER.format(m.time_created),
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
		table.setDefaultRenderer(JButton.class, new ComboBoxCellRenderer());
		JScrollPane scrollPane = new JScrollPane(table);
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
		table.setVisible(true);
		addHandler();
	}

	private void addHandler() {

		final DAOChapter daoChapter = AppContext.beanAssembler
				.getBean("daoChapter");
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				
				WKChapter chapter = rowToChapter.get(row);
				
				int column = table.getSelectedColumn();

				WKChapter meta = rowToChapter.get(row);
				if (meta == null) {
					System.err.println("Fuck null meta !  at row " + row);
					return;
				}
				
				if (column == COLUMN_NAME.length - 3) {
					// 处理button事件写在这里...
					//JOptionPane.showMessageDialog(null, "详情");
					ModifyChapterDetail detail = new ModifyChapterDetail(chapter);
					detail.setVisible(true);
					//					updateTableContent();
				}

				if (column == COLUMN_NAME.length - 2) {
					
					ModifyChapterModify com = new ModifyChapterModify(chapter);
					com.setVisible(true);
					
				}

				if (column == COLUMN_NAME.length - 1) {
					daoChapter.delete(meta.id);
					updateTableContent();
				}
			}
		});
	}

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
//			"ID","名称", "备注","发布日期",
//				"详情", "修改", "删除" };
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return columnIndex < 4 ? String.class : JButton.class;
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}

	}
}

