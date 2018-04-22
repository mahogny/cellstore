package cellstore.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import cellstore.server.message.Message;

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
