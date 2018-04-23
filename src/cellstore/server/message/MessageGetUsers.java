package cellstore.server.message;

import cellstore.server.ClientThread;
import cellstore.server.response.Response;
import cellstore.server.response.ResponseListClusterings;
import cellstore.server.response.ResponseListDimRed;
import cellstore.server.response.ResponseListUsers;

/**
 * 
 * Message to server: Get users
 * 
 * TODO  do NOT send the passwords!!!!!
 * 
 * @author Johan Henriksson
 *
 */
public class MessageGetUsers extends Message
	{
	private static final long serialVersionUID = 1L;
	
	public int id;

	@Override
	public Response handleOnServer(ClientThread client)
		{
		ResponseListUsers resp=new ResponseListUsers();
		if(id>=0)
			resp.list.put(id, client.db.user.get(id));
		else
			resp.list.putAll(client.db.user);
		return resp;
		}

	}
