import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.BoxLayout;


public class Modify extends JDialog {
	private JTable table;

	/**
	 * Launch the application.
	
	public static void main(String[] args) {
		try {
			Modify dialog = new Modify("修改");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 */
	/**
	 * Create the dialog.
	 */
	public Modify(String s) {
		setTitle(s);
		setBounds(100, 100, 789, 462);
		
		String[] columnNames = {"First Name",  
                "Last Name",  
                "Sport",  
                "# of Years",  
                "Vegetarian"};
		Object[][] data = {  
			    {"Kathy", "Smith",  
			     "Snowboarding", new Integer(5), new Boolean(false)},  
			    {"John", "Doe",  
			     "Rowing", new Integer(3), new Boolean(true)},  
			    {"Sue", "Black",  
			     "Knitting", new Integer(2), new Boolean(false)},  
			    {"Jane", "White",  
			     "Speed reading", new Integer(20), new Boolean(true)},  
			    {"Joe", "Brown",  
			  
			     "Pool", new Integer(10), new Boolean(false)}  
			}; 
		getContentPane().setLayout(null);
		
		JTable table = new JTable(data, columnNames);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"Kathy", "Smith", "Snowboarding", new Integer(5), Boolean.FALSE, null, null, null},
				{"John", "Doe", "Rowing", new Integer(3), Boolean.TRUE, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
			},
			new String[] {
				"First Name", "Last Name", "Sport", "# of Years", "Vegetarian", "New column", "New column", "New column"
			}
		));
		table.setRowHeight(30);
		JScrollPane scrollPane = new JScrollPane(table);  
		scrollPane.setBounds(0, 0, 773, 423);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		table.setFillsViewportHeight(true);  
		getContentPane().add(scrollPane);
		
		JButton bbu_ok = new JButton("OK");
		JButton bu_can =  new JButton("Cancel");
		scrollPane.add(bbu_ok);
		scrollPane.add(bu_can);
		/*Container container = getContentPane();
		container.setLayout(new BorderLayout());  
		container.add(table.getTableHeader(), BorderLayout.PAGE_START);  
		container.add(table, BorderLayout.CENTER);  
		*/
		
	}
}
