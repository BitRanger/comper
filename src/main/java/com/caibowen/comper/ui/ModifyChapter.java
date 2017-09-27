
package com.caibowen.comper.ui;

import com.caibowen.comper.dao.DAOChapter;
import com.caibowen.comper.model.BWChapter;
import com.caibowen.comper.AppContext;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModifyChapter extends JPanel {

  private static final long serialVersionUID = 4434770620883212400L;

  static final String[] COLUMN_NAME = {
      "ID", "Name", "Comment", "Date",
      "Detail", "Modify", "Delete"};

  JTable table = new JTable();
  MyTableModel myTable = new MyTableModel();
  Map<Integer, BWChapter> rowToChapter = new HashMap<>(2048);

  List<BWChapter> chapters;

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
      final BWChapter m = chapters.get(i);
      rowToChapter.put(i, m);
//			"ID","Name", "Comment","Date Added",
//			"Detail", "Modify", "Delete" };
      tableContent.add(new Object[]{
          m.id,
          m.name,
          m.description,
          FORMATTER.format(m.time_created),
          new JButton("Detail"),
          new JButton("Modify"),
          new JButton("Delete")
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

        BWChapter chapter = rowToChapter.get(row);

        int column = table.getSelectedColumn();

        BWChapter meta = rowToChapter.get(row);
        if (meta == null) {
          System.err.println("Null meta !  at row " + row);
          return;
        }

        if (column == COLUMN_NAME.length - 3) {
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
          JOptionPane.showMessageDialog(null, "Deleted!");
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
//			"ID","Name", "Comment","Date Added",
//				"Detail", "Modify", "Delete" };
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

