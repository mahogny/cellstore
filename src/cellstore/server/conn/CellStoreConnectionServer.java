package cellstore.server.conn;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collection;

import cellstore.db.CellClustering;
import cellstore.db.CellDimRed;
import cellstore.server.message.Message;
import cellstore.server.message.MessageGetUsers;
import cellstore.server.message.MessageGetDimRed;
import cellstore.server.response.Response;
import cellstore.server.response.ResponseListClusterings;
import cellstore.server.response.ResponseListDimRed;

/**
 * Connection to a database
 * 
 * @author Johan Henriksson
 *
 */
public class CellStoreConnectionServer implements CellStoreConnection
	{
	private DataInputStream dis;
	private DataOutputStream dos;
	Socket s;
	
	public CellStoreConnectionServer(String addr, int port) throws UnknownHostException, IOException
		{
		s = new Socket(addr, port);
		dis=new DataInputStream(s.getInputStream());
		dos=new DataOutputStream(s.getOutputStream());
		}
	
	/**
	 * Send message and get response
	 */
	private Response sendReceive(Message m) throws IOException
		{
		try
			{
			//First send the message
			send(m);
			//Get response
			ObjectInputStream ois=new ObjectInputStream(dis);
			Response resp=(Response)ois.readObject();
			return resp;
			}
		catch (ClassNotFoundException e)
			{
			throw new IOException(e.getMessage());
			}
		}

	/**
	 * Send message, do not expect response. But maybe should always get a pass/fail at least
	 */
	private void send(Message m) throws IOException
		{
		ObjectOutputStream oos=new ObjectOutputStream(dos);
		oos.writeObject(m);
		oos.flush();
		}
		
	@Override
	public CellDimRed getDimRed(int id) throws IOException
		{
		MessageGetDimRed m=new MessageGetDimRed();
		m.id=id; //TODO possibly instead use a set
		ResponseListDimRed resp=(ResponseListDimRed)sendReceive(m);
		return resp.list.get(id);
		}

	@Override
	public Collection<Integer> getListClusterings() throws IOException
		{
		MessageGetUsers m=new MessageGetUsers();
		m.id=-1; //TODO Possibly instead use a Set
		ResponseListClusterings resp=(ResponseListClusterings)sendReceive(m);
		return resp.list.keySet();
		}

	@Override
	public CellClustering getClustering(int id) throws IOException
		{
		MessageGetUsers m=new MessageGetUsers();
		m.id=id;
		ResponseListClusterings resp=(ResponseListClusterings)sendReceive(m);
		return resp.list.get(id);
		}

	public boolean authenticate(String user, String password)
		{
		//TODO
		
		return false;
		}
	
	
	
	public void close() throws IOException
		{
		s.close();
		}
	
	
	}
