package cellstore.viewer;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import cellstore.viewer.conn.CellStoreConnectionLocal;
import db.CellSet;
import db.CellSetFile;
import db.CellStoreUser;

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
		
		int numuser=conn.db.datasets.cellsets.size();
		Object[][] data = new Object[numuser][];
		int i=0;
		for(CellSetFile cellset:conn.db.datasets.cellsets.values())
			{
			CellStoreUser u=conn.db.user.get(cellset.ownerID);
			
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
