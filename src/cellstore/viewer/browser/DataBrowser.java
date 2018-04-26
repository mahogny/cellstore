package cellstore.viewer.browser;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import cellstore.server.conn.CellStoreConnectionLocal;
import cellstore.viewer.DialogConnect;

/**
 * 
 * Data browser window
 * 
 * @author Johan Henriksson
 *
 */
public class DataBrowser extends JFrame implements ActionListener
	{
	private static final long serialVersionUID = 1L;
	
	private JTabbedPane tabbedPane = new JTabbedPane();

	private JMenuBar menubar=new JMenuBar();
	private JMenu mServer=new JMenu("Server");
	private JMenuItem miConnect=new JMenuItem("Connect");
	private JMenuItem miRefresh=new JMenuItem("Refresh");
	private JMenuItem miQuit=new JMenuItem("Quit");
	
	
	private BrowserPaneProjections vdr;
	
	/**
	 * Constructor of the browser
	 * 
	 * @param conn
	 */
	public DataBrowser(CellStoreConnectionLocal conn)
		{
		menubar.add(mServer);
		mServer.add(miConnect);
		mServer.add(miRefresh);
		mServer.add(miQuit);
		setJMenuBar(menubar);
		
		BrowserPaneUsers vu=new BrowserPaneUsers(conn);
		BrowserPaneCounts vcs=new BrowserPaneCounts(conn);
		vdr=new BrowserPaneProjections(conn);
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
		setTitle("CellStore Browser");
		}



	@Override
	public void actionPerformed(ActionEvent e)
		{
		/**
		 * should rather have a signal system. would prepare it also for server-driven updates
		 */
		if(e.getSource()==miRefresh)
			{
			vdr.updateTable();
			}
		else if(e.getSource()==miConnect)
			{
			new DialogConnect();
			}
		else if(e.getSource()==miQuit)
			{
			System.exit(0);
			}
		}

	}
