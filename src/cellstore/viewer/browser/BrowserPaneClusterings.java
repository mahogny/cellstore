package cellstore.viewer.browser;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import cellstore.db.CellClustering;
import cellstore.db.CellStoreUser;
import cellstore.server.conn.CellStoreConnectionLocal;
import cellstore.viewer.event.CellStoreEvent;
import util.EvSwingUtil;

/**
 * A view of all the cellsets in the database
 * 
 * @author Johan Henriksson
 *
 */
public class BrowserPaneClusterings extends JPanel
	{
	private static final long serialVersionUID = 1L;
	
	private JButton cDelete=new JButton("Delete");
	private JTable table = new JTable();

	private CellStoreConnectionLocal conn;

	public BrowserPaneClusterings(CellStoreConnectionLocal conn)
		{
		this.conn=conn;
		
					
		setLayout(new GridLayout(1, 1));
		table.setDefaultEditor(Object.class, null);

		//bDelete.addActionListener(this);


		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		add(scrollPane);
		
		JPanel pButtons=new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pButtons.add(cDelete);
		setLayout(new GridLayout(1, 1));
		add(EvSwingUtil.layoutACB(
				null, 
				scrollPane, 
				pButtons));
		


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
		
		DefaultTableModel m=new DefaultTableModel(data, columnNames);
		table.setModel(m);
		}
	

	}
