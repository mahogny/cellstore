package cellstore.viewer;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import cellstore.db.CellDimRed;
import cellstore.db.CellSet;
import cellstore.db.CellSetFile;
import cellstore.db.CellStoreUser;
import cellstore.server.conn.CellStoreConnectionLocal;

/**
 * A view of all the cellsets in the database
 * 
 * @author Johan Henriksson
 *
 */
public class PaneDimReds extends JPanel
	{
	private static final long serialVersionUID = 1L;

	public PaneDimReds(CellStoreConnectionLocal conn)
		{
		String[] columnNames = {
				"id",
				"Name",
        "Owner"};
		
		Map<Integer, CellStoreUser> users=conn.getAllUsers();
		Map<Integer,CellDimRed> dimreds=conn.getDimReds();
		
		
		
		int numDimRed=dimreds.size();
		Object[][] data = new Object[numDimRed][];
		int i=0;
		for(CellDimRed dimred:dimreds.values())
			{
			CellStoreUser u=users.get(dimred.ownerID);
			
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
		}
	

	}
