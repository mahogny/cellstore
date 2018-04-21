package cellstore.viewer.conn.message;

import java.io.Serializable;

public abstract class MessageToServer implements Serializable
	{
	int messageID;
	
	public abstract void handleOnServer();
	

	/**
	 * send a message back, a different kind.
	 * 
	 * 
	 * 
	 * 
	 */
	
	}
