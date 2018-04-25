package cellstore.server.message;

import java.io.Serializable;

import cellstore.server.CellStoreMain;
import cellstore.server.ClientThread;
import cellstore.server.response.Response;
import cellstore.server.response.ResponseListProjections;

/**
 * 
 * Message to server: Get one DimRed
 * 
 * @author Johan Henriksson
 *
 */
public class MessageGetProjection extends Message implements Serializable
	{
	private static final long serialVersionUID = 1L;
	
	public int id=-1;
	
	@Override
	public Response handleOnServer(ClientThread client, CellStoreMain main)
		{
		ResponseListProjections resp=new ResponseListProjections();
		if(id>=0)
			resp.list.put(id, client.db.datasets.projections.get(id));
		else
			resp.list.putAll(client.db.datasets.projections);
		return resp;
		}

	}
