package cellstore.server.message;

import cellstore.server.ClientThread;
import cellstore.server.response.Response;
import cellstore.server.response.ResponseListClusterings;
import cellstore.server.response.ResponseListDimRed;

/**
 * 
 * 
 * @author Johan Henriksson
 *
 */
public class MessageGetCellClustering extends Message
	{
	private static final long serialVersionUID = 1L;
	
	public int id;

	@Override
	public Response handleOnServer(ClientThread client)
		{
		ResponseListClusterings resp=new ResponseListClusterings();
		if(id>=0)
			resp.list.put(id, client.db.datasets.clusterings.get(id));
		else
			resp.list.putAll(client.db.datasets.clusterings);
		return resp;
		}

	}
