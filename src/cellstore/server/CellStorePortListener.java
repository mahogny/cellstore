package cellstore.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * Handler of incoming connections
 * 
 * @author Johan Henriksson
 *
 */
public class CellStorePortListener extends Thread
	{
	public static final int DEFAULT_PORT = 12649;
		
	public boolean shutdown=false;
	private ServerSocket serverSocket;
	
	CellStoreMain main;
	
	/**
	 * Construct a new port listener
	 * 
	 * @param main
	 * @throws IOException
	 */
	public CellStorePortListener(CellStoreMain main) throws IOException
		{
		this.main=main;
		int portNumber = DEFAULT_PORT;

    serverSocket = new ServerSocket(portNumber);
		}

	
	/**
	 * Keep accepting incoming connections
	 */
	public void run()
		{
    try
			{
			while(!shutdown)
				{
				System.out.println("Server waiting for connection");
			  Socket clientSocket = serverSocket.accept();
			  System.out.println("got connection");
			  ClientThread conn=new ClientThread(main, clientSocket);
			  conn.start();
			  System.out.println("Server accepted new connection");
				}
			serverSocket.close();
			}
		catch (IOException e)
			{
			e.printStackTrace();
			}
		}
	
	
	

	}
