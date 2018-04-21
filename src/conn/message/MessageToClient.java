package conn.message;

import java.io.Serializable;

public abstract class MessageToClient implements Serializable
	{
	int messageID;
	
	public abstract void handleOnClient();
	

	/**
	 * send a message back, a different kind.
	 * 
	 * 
	 * 
	 * 
	 */
	
	}
