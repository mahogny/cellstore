package cellstore.server.message;

import java.io.Serializable;

import cellstore.server.CellStoreMain;
import cellstore.server.ClientThread;
import cellstore.server.response.Response;

/**
 * A message from the client to the server
 * 
 * @author Johan Henriksson
 *
 */
public abstract class Message implements Serializable
	{
	private static final long serialVersionUID = 1L;

	public abstract Response handleOnServer(ClientThread client, CellStoreMain main);
	}
