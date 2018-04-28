package cellstore.viewer.browser;

import java.awt.GridLayout;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import cellstore.db.CellSetFile;
import cellstore.db.CellStoreUser;
import cellstore.server.conn.CellStoreConnectionLocal;
import cellstore.viewer.event.CellStoreEvent;

/**
 * A view of all the cellsets in the database
 * 
 * @author Johan Henriksson
 *
 */
public class BrowserPaneCounts extends JPanel
	{
	private static final long serialVersionUID = 1L;

	private JTable table = new JTable();

	private CellStoreConnectionLocal conn;

	public BrowserPaneCounts(CellStoreConnectionLocal conn)
		{
		this.conn=conn;
		
		setLayout(new GridLayout(1, 1));
		table.setDefaultEditor(Object.class, null);
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		add(scrollPane);

		updateTable();
		/*
		table.addMouseListener(new MouseAdapter() 
			{
	    public void mousePressed(MouseEvent mouseEvent) 
	    	{
	    	JTable table =(JTable) mouseEvent.getSource();
	    	Point point = mouseEvent.getPoint();
	    	int row = table.rowAtPoint(point);
	    	if (mouseEvent.getClickCount() == 2 && row!=-1) //table.getSelectedRow() != -1 
	    		{
		    	// your valueChanged overridden method 
		    	}
	    	}
			});
		*/
		}

	public void cellStoreEvent(CellStoreEvent e)
		{
		}

	public void updateTable()
		{
		String[] columnNames = {
				"id",
				"Name",
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
					new Integer(cellset.id),
					cellset.name,
					u.username
					};
			data[i]=dat;
			i++;
			}
		
		DefaultTableModel m=new DefaultTableModel(data, columnNames);
		table.setModel(m);
		}
	

	}
