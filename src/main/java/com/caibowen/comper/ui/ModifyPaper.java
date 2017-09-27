
package com.caibowen.comper.ui;

import com.caibowen.comper.AppContext;
import com.caibowen.comper.dao.DAOPaper;
import com.caibowen.comper.model.BWPaper;

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

public class ModifyPaper extends JPanel {

  private static final long serialVersionUID = 4434770620883212400L;

  static final String[] COLUMN_NAME = {
      "ID", "Name", "Comment", "Publisher", "Date Added",
      "Detail", "Modify", "Delete"};

  JTable table = new JTable();
  MyTableModel myTable = new MyTableModel();
  Map<Integer, BWPaper> rowToPaper = new HashMap<>(2048);

  List<BWPaper> papers;

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
      final BWPaper m = papers.get(i);
      rowToPaper.put(i, m);
//			static final String[] COLUMN_NAME = {
//				"ID","Name", "Comment", "Publisher", "Date Added",
//					"Detail", "Modify", "Delete" };
      tableContent.add(new Object[]{
          m.id,
          m.name,
          m.description,
          m.name_publisher,
          FORMATTER.format(m.time_published),
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

    final DAOPaper daoPaper = AppContext.beanAssembler
        .getBean("daoPaper");

    table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int row = table.getSelectedRow();
        int column = table.getSelectedColumn();

        BWPaper meta = rowToPaper.get(row);
        if (meta == null) {
          System.err.println("Fuck null meta at row " + row);
          return;
        }

        if (column == COLUMN_NAME.length - 3) {
          // 处理button事件写在这里...
          //JOptionPane.showMessageDialog(null, "Detail");

          //ModifyChapterDetail detail = new ModifyChapterDetail(paper);
          ModifyPaperDetail detail = new ModifyPaperDetail(meta);
          detail.setVisible(true);


//          System.out.println(((JButton) table.getValueAt(row, column))
//              .getText());
        }

        if (column == COLUMN_NAME.length - 2) {
          // 处理button事件写在这里...
          //	JOptionPane.showMessageDialog(null, "Modify");
          ModifyPaperModify modify = new ModifyPaperModify(meta);
          modify.setVisible(true);
//          System.out.println(((JButton) table.getValueAt(row, column))
//              .getText());
        }

        if (column == COLUMN_NAME.length - 1) {
          daoPaper.delete(meta.id);
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
//			"ID","Name", "Comment", "Publisher", "Date Added",
//				"Detail", "Modify", "Delete" };
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

