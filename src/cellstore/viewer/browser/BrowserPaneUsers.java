package cellstore.viewer.browser;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import cellstore.db.CellStoreUser;
import cellstore.server.conn.CellStoreConnectionLocal;
import util.EvSwingUtil;

/**
 * A view of all the users in the database
 * 
 * @author Johan Henriksson
 *
 */
public class BrowserPaneUsers extends JPanel
	{
	private static final long serialVersionUID = 1L;

	private JButton bDelete=new JButton("Delete");
	private JButton bAdd = new JButton("Add user");
	
	public BrowserPaneUsers(CellStoreConnectionLocal conn)
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
		table.setDefaultEditor(Object.class, null);
		
		//bDelete.addActionListener(this);
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		add(scrollPane);
		
		JPanel pButtons=new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pButtons.add(bAdd);
		pButtons.add(bDelete);
		
		setLayout(new GridLayout(1, 1));
		add(EvSwingUtil.layoutACB(
				null, 
				scrollPane, 
				pButtons));
		
		//table.getTableHeader().setVisible(true);
		}
	

	}
