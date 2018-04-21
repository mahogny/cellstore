package cellstore.viewer.conn.message;

import java.io.Serializable;

public class MessageGetDimRed extends MessageToServer implements Serializable
	{
	private static final long serialVersionUID = 1L;
	
	int id;
	
	@Override
	public void handleOnServer()
		{
		}

	}
