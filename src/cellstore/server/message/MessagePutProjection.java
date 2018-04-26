package cellstore.server.message;

import java.io.Serializable;

import cellstore.db.CellProjection;
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
public class MessagePutProjection extends Message implements Serializable
	{
	private static final long serialVersionUID = 1L;
	
	public int id=-1;
	
	public CellProjection projection;
	
	@Override
	public Response handleOnServer(ClientThread client, CellStoreMain main)
		{
		ResponsePassFail resp=new ResponsePassFail();
		resp.id=main.db.putNewCellProjection(projection);
		resp.passed=true;
		return resp;
		}

	}
