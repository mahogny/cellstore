package cellstore.server.message;

import java.io.Serializable;
import cellstore.db.CellStoreUser;
import cellstore.server.ClientThread;
import cellstore.server.response.Response;
import cellstore.server.response.ResponsePassFail;

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
	public Response handleOnServer(ClientThread client)
		{
		ResponsePassFail resp=new ResponsePassFail();
		
		CellStoreUser u=client.db.getUser(username);
		if(u!=null)
			{
			resp.passed=true;
			client.userID=u.id;
			}
		else
			{
			resp.passed=false;
			}
		return resp;
		}

	}
