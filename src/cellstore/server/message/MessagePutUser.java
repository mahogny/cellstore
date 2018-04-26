package cellstore.server.message;

import java.io.IOException;
import java.io.Serializable;

import cellstore.db.CellStoreUser;
import cellstore.server.CellStoreMain;
import cellstore.server.ClientThread;
import cellstore.server.response.Response;
import cellstore.server.response.ResponsePassFail;

/**
 * 
 * Message to server: Get one DimRed
 * 
 * @author Johan Henriksson
 *
 */
public class MessagePutUser extends Message implements Serializable
	{
	private static final long serialVersionUID = 1L;
	
	public int id=-1;
	
	public CellStoreUser user;
	
	@Override
	public Response handleOnServer(ClientThread client, CellStoreMain main)
		{
		ResponsePassFail resp = new ResponsePassFail();
		try
			{
			resp.id=main.db.putNewUser(user);
			resp.passed=true;
			}
		catch (IOException e)
			{
			e.printStackTrace();
			resp.passed=false;
			}
		return resp;
		}

	}
