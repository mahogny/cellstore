package cellstore.viewer.browser;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import cellstore.db.CellProjection;
import cellstore.db.CellStoreUser;
import cellstore.server.conn.CellStoreConnectionLocal;
import cellstore.viewer.projection.ViewerProjection;
import util.EvSwingUtil;

/**
 * A view of all the cellsets in the database
 * 
 * @author Johan Henriksson
 *
 */
public class BrowserPaneProjections extends JPanel implements ActionListener
	{
	private static final long serialVersionUID = 1L;

	private JButton bDelete=new JButton("Delete");

	public BrowserPaneProjections(final CellStoreConnectionLocal conn)
		{
		String[] columnNames = {
				"id",
				"Name",
        "Owner"};
		
		Map<Integer, CellStoreUser> users=conn.getAllUsers();
		Map<Integer,CellProjection> dimreds=conn.getDimReds();
		
		
		
		int numDimRed=dimreds.size();
		Object[][] data = new Object[numDimRed][];
		int i=0;
		for(CellProjection dimred:dimreds.values())
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
		table.setDefaultEditor(Object.class, null);

		bDelete.addActionListener(this);
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		
		JPanel pButtons=new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pButtons.add(bDelete);
		
		setLayout(new GridLayout(1, 1));
		add(EvSwingUtil.layoutACB(
				null, 
				scrollPane, 
				pButtons));

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
		}

	
	@Override
	public void actionPerformed(ActionEvent e)
		{
		if(e.getSource()==bDelete)
			{
			
			}
		}
	

	}
