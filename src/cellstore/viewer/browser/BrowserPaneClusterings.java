package cellstore.viewer.browser;

import java.awt.GridLayout;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import cellstore.db.CellClustering;
import cellstore.db.CellStoreUser;
import cellstore.server.conn.CellStoreConnectionLocal;

/**
 * A view of all the cellsets in the database
 * 
 * @author Johan Henriksson
 *
 */
public class BrowserPaneClusterings extends JPanel
	{
	private static final long serialVersionUID = 1L;

	public BrowserPaneClusterings(CellStoreConnectionLocal conn)
		{
		String[] columnNames = {
				"id",
				"Name",
        "Owner"};
		
		Map<Integer, CellStoreUser> users=conn.getAllUsers();
		Map<Integer,CellClustering> dimreds=conn.getClusterings();
		
		
		
		int numDimRed=dimreds.size();
		Object[][] data = new Object[numDimRed][];
		int i=0;
		for(CellClustering dimred:dimreds.values())
			{
			CellStoreUser u=users.get(dimred.owner);
			
			Object[] dat={
					new Integer(dimred.id),
					dimred.name,
					u.username
					};
			data[i]=dat;
			i++;
			}
					
		JTable table = new JTable(data, columnNames);
		setLayout(new GridLayout(1, 1));
		table.setDefaultEditor(Object.class, null);


		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		add(scrollPane);

		/*
		table.addMouseListener(new MouseAdapter() 
			{
	    public void mousePressed(MouseEvent mouseEvent) 
	    	{
	    	JTable table =(JTable) mouseEvent.getSource();
	    	Point point = mouseEvent.getPoint();
	    	int row = table.rowAtPoint(point);
	    	if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) 
	    		{
	    		System.out.println("row "+row);
		    	// your valueChanged overridden method 
		    	}
	    	}
			});
		*/
		}
	

	}
