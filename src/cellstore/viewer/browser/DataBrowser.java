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
import cellstore.viewer.event.CellStoreEvent;
import cellstore.viewer.event.CellStoreEventListener;

/**
 * 
 * Data browser window
 * 
 * @author Johan Henriksson
 *
 */
public class DataBrowser extends JFrame implements ActionListener, CellStoreEventListener
	{
	private static final long serialVersionUID = 1L;
	
	private JTabbedPane tabbedPane = new JTabbedPane();

	private JMenuBar menubar=new JMenuBar();
	private JMenu mServer=new JMenu("Server");
	private JMenuItem miConnect=new JMenuItem("Connect");
	private JMenuItem miRefresh=new JMenuItem("Refresh");
	private JMenuItem miQuit=new JMenuItem("Quit");
	
	
	private BrowserPaneProjections viewProjections;
	BrowserPaneCounts viewCounts;
	BrowserPaneUsers viewUsers;
	BrowserPaneClusterings viewClusterings;
	
	/**
	 * Constructor of the browser
	 * 
	 * @param conn
	 */
	public DataBrowser(CellStoreConnectionLocal conn)
		{
		conn.addListener(this);
		
		menubar.add(mServer);
		mServer.add(miConnect);
		mServer.add(miRefresh);
		mServer.add(miQuit);
		setJMenuBar(menubar);
		
		viewUsers=new BrowserPaneUsers(conn);
		viewCounts=new BrowserPaneCounts(conn);
		viewProjections=new BrowserPaneProjections(conn);
		viewClusterings=new BrowserPaneClusterings(conn);
		
		tabbedPane.add("Projections", viewProjections);
		tabbedPane.add("Clusterings", viewClusterings);
		tabbedPane.add("Counts", viewCounts);
		tabbedPane.add("Users", viewUsers);
		
		BrowserPaneStudies vs=new BrowserPaneStudies(conn);
		JPanel pvs=new JPanel();
		pvs.setBorder(BorderFactory.createTitledBorder("Studies"));
		pvs.setLayout(new GridLayout(1, 1));
		pvs.add(vs);
		
		setLayout(new GridLayout(2, 1));
		add(tabbedPane);
		add(pvs);

		
		miConnect.addActionListener(this);
		miRefresh.addActionListener(this);
		miQuit.addActionListener(this);
		
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
			viewProjections.updateTable();
			viewUsers.updateTable();
			viewCounts.updateTable();
			viewClusterings.updateTable();
			}
		else if(e.getSource()==miConnect)
			{
			DialogConnect dia=new DialogConnect();
			if(dia.isOk)
				{
				
				}
			}
		else if(e.getSource()==miQuit)
			{
			System.exit(0);
			}
		}



	@Override
	public void cellStoreEvent(CellStoreEvent e)
		{
		viewUsers.cellStoreEvent(e);
		viewCounts.cellStoreEvent(e);
		viewProjections.cellStoreEvent(e);
		viewClusterings.cellStoreEvent(e);
		}

	}
