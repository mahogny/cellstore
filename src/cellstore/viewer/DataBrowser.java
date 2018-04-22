package cellstore.viewer;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import cellstore.server.conn.CellStoreConnectionLocal;

/**
 * 
 * Data browser window
 * 
 * @author Johan Henriksson
 *
 */
public class DataBrowser extends JFrame
	{
	private static final long serialVersionUID = 1L;
	
	private JTabbedPane tabbedPane = new JTabbedPane();

	public DataBrowser(CellStoreConnectionLocal conn)
		{
		setLayout(new GridLayout(1, 1));
		add(tabbedPane);
		
		PaneUsers vu=new PaneUsers(conn);
		PaneCellSets vcs=new PaneCellSets(conn);
		
		tabbedPane.add("Users", vu);
		tabbedPane.add("CellSets", vcs);
		
		setSize(800,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		}

	}
