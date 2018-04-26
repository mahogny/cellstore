package cellstore.server.conn;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collection;

import cellstore.db.CellClustering;
import cellstore.db.CellProjection;
import cellstore.db.CellSetFile;
import cellstore.server.message.Message;
import cellstore.server.message.MessageAuthenticate;
import cellstore.server.message.MessageDeleteProjection;
import cellstore.server.message.MessageGetClusterings;
import cellstore.server.message.MessageGetUsers;
import cellstore.server.message.MessagePutProjection;
import cellstore.server.message.MessageRequestAuthenticate;
import cellstore.server.message.MessageGetProjection;
import cellstore.server.response.Response;
import cellstore.server.response.ResponseAuthChallenge;
import cellstore.server.response.ResponseListClusterings;
import cellstore.server.response.ResponseListProjections;
import cellstore.server.response.ResponsePassFail;
import cellstore.viewer.event.CellStoreEvent;
import cellstore.viewer.event.CellStoreEventListener;

/**
 * Connection to a database
 * 
 * @author Johan Henriksson
 *
 */
public class CellStoreConnectionServer implements CellStoreConnection
	{
	private ObjectInputStream dis;
	private ObjectOutputStream dos;	
	private Socket s;

	/**
	 * A connection to a server over internet
	 * 
	 * @param addr
	 * @param port
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public CellStoreConnectionServer(String addr, int port) throws UnknownHostException, IOException
		{
		s = new Socket(addr, port);
		dos=new ObjectOutputStream(s.getOutputStream());
		dos.flush();

		dis=new ObjectInputStream(s.getInputStream());
		
		//Read version from server
		String servername=dis.readUTF();
		int version=dis.readByte();
		System.out.println("Client got version: "+servername+"    "+version);
		}
	
	/**
	 * Send message and get response
	 */
	private Response sendReceive(Message m) throws IOException
		{
		try
			{
			//Send the message
			System.out.println("Client sending message "+m);
			dos.writeObject(m);
			dos.flush();
			
			//Get response
			Response resp=(Response)dis.readObject();
			System.out.println("Client getting response "+resp);
			return resp;
			}
		catch(OptionalDataException e)
			{
			System.out.println("EOF: "+e.eof+"  length: "+e.length);
			throw new IOException(e);
			}
		catch (ClassNotFoundException e)
			{
			throw new IOException(e.getMessage(),e);
			}
		}

	/**
	 * Send message, do not expect response. But maybe should always get a pass/fail at least
	 */
	/*
	private void send(Message m) throws IOException
		{
		dos.writeObject(m);
		dos.flush();
		}
	*/
		
	@Override
	public CellProjection getProjection(int id) throws IOException
		{
		MessageGetProjection m=new MessageGetProjection();
		m.id=id; //TODO possibly instead use a set
		ResponseListProjections resp=(ResponseListProjections)sendReceive(m);
		return resp.list.get(id);
		}
	
	public Integer putProjection(CellProjection p) throws IOException
		{
		MessagePutProjection m=new MessagePutProjection();
		m.projection=p;
		ResponsePassFail respPF=(ResponsePassFail)sendReceive(m);
		return respPF.id;
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
		MessageGetClusterings m=new MessageGetClusterings();
		m.id=id;
		ResponseListClusterings resp=(ResponseListClusterings)sendReceive(m);
		return resp.list.get(id);
		}

	/**
	 * Authenticate
	 */
	public boolean authenticate(String user, String password) throws IOException
		{
		//First request to authenticate
		MessageRequestAuthenticate mReq=new MessageRequestAuthenticate();
		mReq.username="user";
		ResponseAuthChallenge respChallenge=(ResponseAuthChallenge)sendReceive(mReq);
		
		//Given challenge, authenticate
		MessageAuthenticate mAuth=new MessageAuthenticate();
		mAuth.setPass(password, respChallenge);
		ResponsePassFail respPF=(ResponsePassFail)sendReceive(mAuth);
		
		return respPF.passed;
		}
	
	
	public CellSetFile getCellSetFile(int id)
		{
		throw new RuntimeException("not implemented");
		}

	
	public void close() throws IOException
		{
		s.close();
		}

	@Override
	public void addListener(CellStoreEventListener e)
		{
		// TODO Auto-generated method stub
		
		}

	@Override
	public void emitEvent(CellStoreEvent e)
		{
		// TODO Auto-generated method stub
		
		}
	
	
	public boolean removeProjection(int id) throws IOException
		{
		MessageDeleteProjection m=new MessageDeleteProjection();
		m.id=id;
		ResponsePassFail resp=(ResponsePassFail)sendReceive(m);
		return resp.passed;
		}

	}
