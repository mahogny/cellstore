package cellstore.server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
	private ObjectInputStream dis;
	private ObjectOutputStream dos;
	private Socket clientSocket;
	
	public int userID;
	
	public boolean shutdown=false;
	public CellStoreDB db;
	private CellStoreMain main;
	

	/**
	 * Constructor of a communication thread
	 * 
	 * @param main
	 * @param clientSocket
	 * @throws IOException
	 */
	public ClientThread(CellStoreMain main, Socket clientSocket) throws IOException
		{
		this.main=main;
		this.db=main.db;
		this.clientSocket=clientSocket;
		
		dis=new ObjectInputStream(clientSocket.getInputStream());
		dos=new ObjectOutputStream(clientSocket.getOutputStream());

		//Version of the server
		dos.writeUTF("CellStore");
		dos.writeByte(1);
		dos.flush();
				
		System.out.println("Connection to server ready");
		}
	
	
	
	/**
	 * Send a message
	 * 
	 * @param m
	 * @throws IOException
	 */
	public void send(Message m) throws IOException
		{
		dos.writeObject(m);
		dos.flush();
		}
	
	
	
	/**
	 * Run and keep accepting messages
	 */
	@Override
	public void run()
		{
		try
			{
			while(!shutdown)
				{
				//Get a message
				Message m=(Message)dis.readObject();
				//System.out.println("Server got message "+m);
				Response resp=m.handleOnServer(this, main);
				
				//Send back response
				if(resp!=null)
					{
					//System.out.println("Server sending back response "+resp);
					dos.writeObject(resp);
					dos.flush();
					}
				else
					System.out.println("No response to client");
				}
			
			clientSocket.close();
			}
		catch (EOFException e)
			{
			System.out.println("--- EOF, client quit");
			}
		catch (Exception e)
			{
			e.printStackTrace();
			}
	
		
		}
	
	}