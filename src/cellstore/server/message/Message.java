package cellstore.server.message;

import java.io.Serializable;

import cellstore.server.ClientThread;
import cellstore.server.response.Response;

public abstract class Message implements Serializable
	{
	private static final long serialVersionUID = 1L;

	public abstract Response handleOnServer(ClientThread client);
	

	/**
	 * send a message back, a different kind.
	 * 
	 * 
	 * 
	 * 
	 */
	
	}
