package cellstore.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import cellstore.db.CellDimRed;
import cellstore.db.CellStoreDB;
import cellstore.server.message.Message;
import cellstore.server.response.Response;

/**
 * 
 * A thread handling messages from a client
 * 
 */
public class ClientThread extends Thread
	{
	private DataInputStream dis;
	private DataOutputStream dos;
	private Socket clientSocket;
	
	public int userID;
	
	public boolean shutdown=false;
	public CellStoreDB db;
	
	public ClientThread(CellStoreDB db)
		{
		this.db=db;
		}
	
	
	public ClientThread(Socket clientSocket) throws IOException
		{
		this.clientSocket=clientSocket;
		dis=new DataInputStream(clientSocket.getInputStream());
		dos=new DataOutputStream(clientSocket.getOutputStream());
		}
	
	public void send(Message m) throws IOException
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
			
			while(!shutdown)
				{
				//Get a message
				ObjectInputStream ois=new ObjectInputStream(dis);
				Message m=(Message)ois.readObject();
				Response resp=m.handleOnServer(this);
				//Send back response
				if(resp!=null)
					{
					ObjectOutputStream oos=new ObjectOutputStream(dos);
					oos.writeObject(resp);
					oos.flush();
					}
				}
			
			clientSocket.close();
			}
		catch (Exception e)
			{
			e.printStackTrace();
			}
	
		
		}
	
	}