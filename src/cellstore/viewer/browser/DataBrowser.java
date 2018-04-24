package cellstore.viewer.browser;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
		BrowserPaneUsers vu=new BrowserPaneUsers(conn);
		BrowserPaneCounts vcs=new BrowserPaneCounts(conn);
		BrowserPaneProjections vdr=new BrowserPaneProjections(conn);
		BrowserPaneClusterings vc=new BrowserPaneClusterings(conn);
		
		tabbedPane.add("Projections", vdr);
		tabbedPane.add("Clusterings", vc);
		tabbedPane.add("Counts", vcs);
		tabbedPane.add("Users", vu);
		
		BrowserPaneStudies vs=new BrowserPaneStudies(conn);
		JPanel pvs=new JPanel();
		pvs.setBorder(BorderFactory.createTitledBorder("Studies"));
		pvs.setLayout(new GridLayout(1, 1));
		pvs.add(vs);
		
		setLayout(new GridLayout(2, 1));
		add(tabbedPane);
		add(pvs);

		
		setSize(600,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setTitle("CellStore browser");
		}

	}
