package cellstore.server.message;

import java.io.IOException;
import java.io.Serializable;

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
public class MessageDeleteProjection extends Message implements Serializable
	{
	private static final long serialVersionUID = 1L;
	
	public int id;
	
	@Override
	public Response handleOnServer(ClientThread client, CellStoreMain main)
		{
		ResponsePassFail resp=new ResponsePassFail();
		try
			{
			main.db.removeProjection(id);
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
