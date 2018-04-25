package cellstore.server.message;

import cellstore.server.CellStoreMain;
import cellstore.server.ClientThread;
import cellstore.server.response.Response;
import cellstore.server.response.ResponseListUsers;

/**
 * 
 * Message to server: Get users
 * 
 * TODO  do NOT send the passwords!!!!!
 * and use SSL!!!
 * 
 * @author Johan Henriksson
 *
 */
public class MessageGetUsers extends Message
	{
	private static final long serialVersionUID = 1L;
	
	public int id=-1;

	@Override
	public Response handleOnServer(ClientThread client, CellStoreMain main)
		{
		//System.out.println("Asking for user "+id);
		ResponseListUsers resp=new ResponseListUsers();
		if(id>=0)
			resp.list.put(id, client.db.user.get(id));
		else
			resp.list.putAll(client.db.user);
		return resp;
		}

	}
