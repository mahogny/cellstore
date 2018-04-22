package cellstore.viewer;

import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

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
public class PaneCellSets extends JPanel
	{
	private static final long serialVersionUID = 1L;

	public PaneCellSets(CellStoreConnectionLocal conn)
		{
		String[] columnNames = {
				"id",
        "Owner"};
		
		Map<Integer, CellStoreUser> users=conn.getAllUsers();
		Map<Integer,CellSetFile> cellsetFiles=conn.getCellSetFiles();
		
		
		
		int numuser=cellsetFiles.size();
		Object[][] data = new Object[numuser][];
		int i=0;
		for(CellSetFile cellset:cellsetFiles.values())
			{
			CellStoreUser u=users.get(cellset.ownerID);
			
			Object[] dat={
					new Integer(cellset.databaseIndex),
					u.username
					};
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
