package cellstore.viewer;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTable;

import cellstore.viewer.conn.CellStoreConnectionLocal;
import db.CellStoreUser;

/**
 * A view of all the users in the database
 * 
 * @author Johan Henriksson
 *
 */
public class UserView extends JPanel
	{
	private static final long serialVersionUID = 1L;

	public UserView(CellStoreConnectionLocal conn)
		{
		// TODO Auto-generated constructor stub
		
		
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
					u.user,
					u.name,
					u.email};
			data[i]=dat;
			i++;
			}
					
		JTable table = new JTable(data, columnNames);
		setLayout(new GridLayout(1, 1));
		add(table);
		
		
		//show columns
		}
	

	}
