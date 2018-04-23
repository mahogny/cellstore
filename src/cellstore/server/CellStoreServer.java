package cellstore.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CellStoreServer
	{
	

	public boolean shutdown=false;
	
	public void run() throws IOException 
		{
		int portNumber = 6666;

    ServerSocket serverSocket = new ServerSocket(portNumber);
    
    while(!shutdown)
    	{
      Socket clientSocket = serverSocket.accept();
      ClientThread conn=new ClientThread(clientSocket);
      conn.start();
    	}
    
    serverSocket.close();
		}
	
	
	

	}
