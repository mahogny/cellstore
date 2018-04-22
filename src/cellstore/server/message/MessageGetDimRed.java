package cellstore.server.message;

import java.io.Serializable;

import cellstore.server.ClientThread;
import cellstore.server.response.Response;
import cellstore.server.response.ResponseListDimRed;

/**
 * 
 * Message to server: Get one DimRed
 * 
 * @author Johan Henriksson
 *
 */
public class MessageGetDimRed extends Message implements Serializable
	{
	private static final long serialVersionUID = 1L;
	
	public int id;
	
	@Override
	public Response handleOnServer(ClientThread client)
		{
		ResponseListDimRed resp=new ResponseListDimRed();
		if(id>=0)
			resp.list.put(id, client.db.datasets.dimreds.get(id));
		else
			resp.list.putAll(client.db.datasets.dimreds);
		return resp;
		}

	}
