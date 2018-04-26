package cellstore.viewer;

import java.awt.GridLayout;

import javax.swing.JFrame;

import cellstore.server.conn.CellStoreConnection;
import cellstore.viewer.event.CellStoreEvent;
import cellstore.viewer.event.CellStoreEventListener;

/**
 * 
 * 
 * @author Johan Henriksson
 *
 */
public class ViewerClusterDE extends JFrame implements CellStoreEventListener
	{
	private static final long serialVersionUID = 1L;

	public CellStoreConnection conn;
	private PaneDE paneDE;
	
	public ViewerClusterDE(CellStoreConnection conn)
		{
		this.conn=conn;
		conn.addListener(this);
		
		paneDE=new PaneDE(conn);
		setLayout(new GridLayout(1,1));
		add(paneDE);
		
		setSize(400,500);
		setVisible(true);
		setTitle("Cluster marker genes");
		}

	@Override
	public void cellStoreEvent(CellStoreEvent e)
		{
		}
	
	
	
	
	}
