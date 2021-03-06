package cellstore.server.message;

import java.io.Serializable;

import cellstore.server.CellStoreMain;
import cellstore.server.ClientThread;
import cellstore.server.response.Response;
import cellstore.server.response.ResponseListClusterings;

/**
 * 
 * Message to server: Get a list of clusterings
 * 
 * @author Johan Henriksson
 *
 */
public class MessageGetClusterings extends Message implements Serializable
	{
	private static final long serialVersionUID = 1L;
	
	//TODO make it a list
	public int id=-1;

	@Override
	public Response handleOnServer(ClientThread client, CellStoreMain main)
		{
		ResponseListClusterings resp=new ResponseListClusterings();
		if(id>=0)
			resp.list.put(id, client.db.datasets.clusterings.get(id));
		else
			resp.list.putAll(client.db.datasets.clusterings);
		return resp;
		}

	}
