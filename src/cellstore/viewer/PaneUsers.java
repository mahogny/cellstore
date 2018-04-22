package cellstore.viewer;

import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import cellstore.db.CellStoreUser;
import cellstore.server.conn.CellStoreConnectionLocal;

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
		
		Map<Integer, CellStoreUser> users=conn.getAllUsers();

		int numuser=users.size();
		Object[][] data = new Object[numuser][];
		int i=0;
		for(CellStoreUser u:users.values())
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
