/*******************************************************************************
 * Copyright (c) 2014 Cai Bowen, Zhou Liangpeng.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Cai Bowen,  Zhou Liangpeng. - initial API and implementation
 ******************************************************************************/
package org.blacklancer.comper.ui;

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

import org.blacklancer.comper.AppContext;
import org.blacklancer.comper.dao.DAOPaper;
import org.blacklancer.comper.model.WKPaper;

public class ModifyPaper extends JPanel {
	
	private static final long serialVersionUID = 4434770620883212400L;

	static final String[] COLUMN_NAME = {
		"ID","名称", "备注", "发布者", "发布日期",
			"详情", "修改", "删除" };

	JTable table = new JTable();
	MyTableModel myTable = new MyTableModel();
	Map<Integer, WKPaper> rowToPaper = new HashMap<>(2048);

	List<WKPaper> papers;
	
	public ModifyPaper() {
		setBorder(BorderFactory.createLineBorder(Color.darkGray, 1));
		init();
	}
static SimpleDateFormat FORMATTER = new SimpleDateFormat("YY-MM-DD");

	void updateTableContent() {
		// headName = new String[10];
		// headName
		final DAOPaper daoPaper = AppContext.beanAssembler
				.getBean("daoPaper");
		papers = daoPaper.getAll();

		ArrayList<Object[]> tableContent = new ArrayList<>(papers.size());
		for (int i = 0; i < papers.size(); i++) {
			final WKPaper m = papers.get(i);
			rowToPaper.put(i, m);
//			static final String[] COLUMN_NAME = {
//				"ID","名称", "备注", "发布者", "发布日期",
//					"详情", "修改", "删除" };
			tableContent.add(new Object[]{
				Integer.toString(i),
				m.name,
				m.description,
				m.name_publisher,
				FORMATTER.format(m.time_published),
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

		final DAOPaper daoPaper = AppContext.beanAssembler
				.getBean("daoPaper");
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				int column = table.getSelectedColumn();

				WKPaper meta = rowToPaper.get(row);
				if (meta == null) {
					System.err.println("Fuck null meta at row " + row);
					return;
				}
				
				if (column == COLUMN_NAME.length - 3) {
					// 处理button事件写在这里...
					//JOptionPane.showMessageDialog(null, "详情");
					
					//ModifyChapterDetail detail = new ModifyChapterDetail(paper);
					ModifyPaperDetail detail = new ModifyPaperDetail(meta);
					detail.setVisible(true);
					

					System.out.println(((JButton) table.getValueAt(row, column))
							.getText());
				}

				if (column == COLUMN_NAME.length - 2) {
					// 处理button事件写在这里...
				//	JOptionPane.showMessageDialog(null, "修改");
					ModifyPaperModify modify =new ModifyPaperModify(meta);
					modify.setVisible(true);
					System.out.println(((JButton) table.getValueAt(row, column))
							.getText());
				}

				if (column == COLUMN_NAME.length - 1) {
					daoPaper.delete(meta.id);
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
//			"ID","名称", "备注", "发布者", "发布日期",
//				"详情", "修改", "删除" };
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return columnIndex < 5 ? String.class : JButton.class;
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}

	}
}

