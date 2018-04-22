package cellstore.viewer;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import cellstore.viewer.conn.CellStoreConnectionLocal;
import db.CellStoreUser;

/**
 * A view of all the users in the database
 * 
 * @author Johan Henriksson
 *
 */
public class PaneUsers extends JPanel
	{
	private static final long serialVersionUID = 1L;

	public PaneUsers(CellStoreConnectionLocal conn)
		{
		String[] columnNames = {
				"id",
        "User",
        "Full name",
        "Email"};
		
		int numuser=conn.db.user.size();
		Object[][] data = new Object[numuser][];
		int i=0;
		for(CellStoreUser u:conn.db.user.values())
			{
			Object[] dat={
					new Integer(u.id),
					u.username,
					u.name,
					u.email};
			data[i]=dat;
			i++;
			}
					
		JTable table = new JTable(data, columnNames);
		setLayout(new GridLayout(1, 1));
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		add(scrollPane);
		
		//table.getTableHeader().setVisible(true);
		}
	

	}
