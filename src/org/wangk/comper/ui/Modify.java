package org.wangk.comper.ui;
import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.wangk.comper.context.R.str;
import org.wangk.comper.model.WKQuestionMeta;

public class Modify extends JDialog 
{  
    JFrame mainWin = new JFrame("修改题目信息");  
    final int COLUMN_COUNT = 9;  
    DefaultTableModel model;  
    JTable table;  
    JMenuBar menuBar;
    JMenu modifyMenu,okMenu,cancleMenu,exitMenu,addMenu;
    String string[][];
    int lines;
    // title;
    //用于保存被隐藏列的List集合 
    
    Modify()
    {
    	getAllInformation();
    	initData(string);
    	table = new JTable(model);  
        table.setToolTipText("hello");
        //table.setFont(new Font("宋体", Font.PLAIN, 17));
        //table.setBounds(100, 100, 300, 400);
 
        mainWin.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);  
        mainWin.setBounds(100, 100, 1200, 600);
 
        //为窗口安装菜单  
        menuBar = new JMenuBar();  
        mainWin.setJMenuBar(menuBar);  
        
        
        addMenu = new JMenu("增加");
        menuBar.add(addMenu);
        addMenu.addMenuListener(new menu());
        
        
        modifyMenu = new JMenu("修改");
        menuBar.add(modifyMenu);
        modifyMenu.addMenuListener(new menu());
        
        okMenu = new JMenu("确定");
        menuBar.add(okMenu);
        okMenu.addMenuListener(new menu());
        
        cancleMenu = new JMenu("取消");
        menuBar.add(cancleMenu);
        cancleMenu.addMenuListener(new menu());
        
        exitMenu = new JMenu("退出");
        menuBar.add(exitMenu);
        exitMenu.addMenuListener(new menu());       
   
        //mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        mainWin.setVisible(true); 
        //this.dispose();
    }
    
    
    ArrayList<TableColumn> hiddenColumns = new ArrayList<TableColumn>();  
    public void initData(String string[][])  
    {  
    	String[] title = {"题型","分值","描述","内容","答案","难度","范围 ","章节","试卷"};
    	
    	//需要参数表示表的大小即行数
    	
    	WKQuestionMeta meta = new WKQuestionMeta();     	
    	
    	model = new DefaultTableModel(title, lines);
    	
        //model = new DefaultTableModel(COLUMN_COUNT ,COLUMN_COUNT);  
//    	
//        for (int i = 0; i < 100 ; i++ )  
//        {  
//            for (int j = 0; j < 9 ; j++ )  
//            {  
//                model.setValueAt("老单元格值 俄空军分公司的，反攻倒算是豆腐干大师傅个" + i + " " + j , i , j); 
//            }  
//        }
//        
    	for(int i=0;i<lines;i++)
    	{
    		for (int j = 0; j < COLUMN_COUNT; j++) {
				model.setValueAt(string[i][j] +i+" "+j, i, j);
			}
    	}
    	
    	//for(int i=0;i<)
    	
    	
    	
    	
//        
//        String[] newCells = new String[COLUMN_COUNT];  
//        for (int i = 0; i < newCells.length; i++)  
//        {  
//            newCells[i] = "新单元格值 " + model.getRowCount() + " " + i;  
//        }  
//        //向TableModel中新增一行。  
//        model.addRow(newCells);  
//        
        
        
        //table.setRowHeight(10);
        
         
    }
    
    public class menu implements MenuListener
    {

		@Override
		public void menuCanceled(MenuEvent e) {
			// TODO Auto-generated method stub
			//if(e.getSource() == tableMenu)
			
		}

		@Override
		public void menuDeselected(MenuEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void menuSelected(MenuEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == addMenu)
				JOptionPane.showMessageDialog(null, "ADD");
			else if(e.getSource() == modifyMenu)
				JOptionPane.showMessageDialog(null, "Modify");
			else if(e.getSource() == okMenu)
				JOptionPane.showMessageDialog(null, "OK");
			else if(e.getSource() == cancleMenu)
				JOptionPane.showMessageDialog(null, "Cancle");
			else 
				JOptionPane.showMessageDialog(null, "Exit");
				
		}
    }    
    
    public void  getAllInformation() {
    	WKQuestionMeta meta = new WKQuestionMeta();
    	lines =100;
    	//{"题型","分值","描述","内容","答案","难度","范围 ","章节","试卷"};
    	string = new String[lines][9];
    	for(int i=0;i<lines;i++)
    	{
    		for(int j=0;j < 9;j++)
    			string[i][j] = "soure";
    	}
		
	}
    
    
 
//    public static void main(String[] args)   
//    {  
//        new Modify();  
//    }  
} 
