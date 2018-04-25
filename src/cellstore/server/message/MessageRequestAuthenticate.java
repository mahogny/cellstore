package cellstore.server.message;

import java.io.Serializable;
import cellstore.db.CellStoreUser;
import cellstore.server.CellStoreMain;
import cellstore.server.ClientThread;
import cellstore.server.response.Response;
import cellstore.server.response.ResponseAuthChallenge;

/**
 * 
 * Message to server: Request to get authenticated
 * 
 * @author Johan Henriksson
 *
 */
public class MessageRequestAuthenticate extends Message implements Serializable
	{
	private static final long serialVersionUID = 1L;
	
	public String username;
		
	@Override
	public Response handleOnServer(ClientThread client, CellStoreMain main)
		{
		ResponseAuthChallenge resp=new ResponseAuthChallenge();
		resp.moresalt=""+Math.random();
		
		CellStoreUser u=client.db.getUser(username);
		if(u!=null)
			{
			resp.salt=u.salt;
			}
		else
			{
			//Based on this, the client can tell if the user exists or not. Messy to fix
			resp.salt="";
			}
		
		return resp;
		}

	}
