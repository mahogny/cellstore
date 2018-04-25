package cellstore.viewer.browser;

import java.awt.GridLayout;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import cellstore.db.CellStoreDatasets;
import cellstore.db.CellStoreUser;
import cellstore.server.conn.CellStoreConnectionLocal;

/**
 * 
 * A view of all studies (groups) the database
 * 
 * @author Johan Henriksson
 *
 */
public class BrowserPaneStudies extends JPanel
	{
	private static final long serialVersionUID = 1L;

	public BrowserPaneStudies(final CellStoreConnectionLocal conn)
		{
		String[] columnNames = {
				"id",
				"Study name",
        "Owner",
        "#cells"};
		
		Map<Integer,CellStoreUser> users=conn.getAllUsers();
		Map<Integer,CellStoreDatasets> dimreds=conn.getStudies();
		
		
		
		int numDimRed=dimreds.size();
		Object[][] data = new Object[numDimRed][];
		int i=0;
		for(CellStoreDatasets dimred:dimreds.values())
			{
			CellStoreUser u=users.get(dimred.ownerID);
			
			Object[] dat={
					new Integer(dimred.id),
					dimred.groupName,
					u.username,
					new Integer(666)
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
		//On double-click, open projection
		table.addMouseListener(new MouseAdapter() 
			{
	    public void mousePressed(MouseEvent mouseEvent) 
	    	{
	    	JTable table =(JTable) mouseEvent.getSource();
	    	Point point = mouseEvent.getPoint();
	    	int row = table.rowAtPoint(point);
	    	if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) 
	    		{
	    		int id=(Integer)table.getModel().getValueAt(row, 0);
	    		
	    		
	    		ViewerProjection viewer=new ViewerProjection(conn);
	    		viewer.setDimRed(id);
	    		
		    	}
	    	}
			});
			*/
		}
	

	}
