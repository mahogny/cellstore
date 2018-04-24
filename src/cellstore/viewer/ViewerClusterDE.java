package cellstore.viewer;

import java.awt.GridLayout;

import javax.swing.JFrame;

import cellstore.server.conn.CellStoreConnection;

/**
 * 
 * 
 * @author Johan Henriksson
 *
 */
public class ViewerClusterDE extends JFrame
	{
	private static final long serialVersionUID = 1L;

	public CellStoreConnection conn;
	private PaneDE paneDE;
	
	public ViewerClusterDE(CellStoreConnection conn)
		{
		this.conn=conn;
		
		paneDE=new PaneDE(conn);
		setLayout(new GridLayout(1,1));
		add(paneDE);
		
		setSize(400,500);
		setVisible(true);
		setTitle("Cluster marker genes");
		}
	
	
	
	
	}
