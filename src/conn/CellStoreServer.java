package conn;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import cellstore.viewer.conn.message.MessageToServer;

public class CellStoreServer
	{
	
	
	public class Connection extends Thread
		{
		DataInputStream dis;
		DataOutputStream dos;
		
		int userID;
		
		public Connection(Socket clientSocket) throws IOException
			{
			dis=new DataInputStream(clientSocket.getInputStream());
			dos=new DataOutputStream(clientSocket.getOutputStream());
			}

		public void send(MessageToServer m) throws IOException
			{
			ObjectOutputStream oos=new ObjectOutputStream(dos);
			oos.writeObject(m);
			oos.flush();
			}
		
		@Override
		public void run()
			{
			try
				{
				//Write version of server
				dos.writeInt(666);
				
				
				for(;;)
					{
					//Check which type of command
					ObjectInputStream ois=new ObjectInputStream(dis);
					MessageToServer m=(MessageToServer)ois.readObject();
					m.handleOnServer();
					}
				
				
				}
			catch (Exception e)
				{
				e.printStackTrace();
				}

			
			}
		
		}
	
	

	
	public void run() throws IOException 
		{
		int portNumber = 6666;

    ServerSocket serverSocket = new ServerSocket(portNumber);
    
    for(;;)
    	{
      Socket clientSocket = serverSocket.accept();
      Connection conn=new Connection(clientSocket);
      conn.start();
    	}
    
    
		
		
		
		
		}
	
	
	

	}
