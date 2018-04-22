package cellstore.viewer;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import cellstore.viewer.conn.CellStoreConnectionLocal;

public class DataBrowser extends JFrame
	{
	JTabbedPane tabbedPane = new JTabbedPane();

	public DataBrowser(CellStoreConnectionLocal conn)
		{
		setLayout(new GridLayout(1, 1));
		add(tabbedPane);
		
		UserView vu=new UserView(conn);

		tabbedPane.add("Users", vu);

		
		setSize(800,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		}

	}
