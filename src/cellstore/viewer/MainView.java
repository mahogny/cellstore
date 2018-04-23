package cellstore.viewer;

import java.io.IOException;

import cellstore.server.CellStoreMain;
import cellstore.server.conn.CellStoreConnectionLocal;

/**
 * 
 * Start the SWING-based viewer for CellStore
 * 
 * @author Johan Henriksson
 *
 */
public class MainView
	{

	
	public static void main(String[] args) throws IOException
		{
		System.out.println("Reading data");
		CellStoreMain main=new CellStoreMain();
		
		CellStoreConnectionLocal conn=new CellStoreConnectionLocal(main.db);
		
		/*
		System.out.println("run viewer for clusters");
		new CellStoreViewer(conn);
		*/
		System.out.println("Run viewer for users");
		new DataBrowser(conn);
		
//		new CellStoreViewer(null);
		}

	}
