package cellstore.server.response;

import java.io.Serializable;

/**
 * 
 * General response, pass/fail
 * 
 * @author Johan Henriksson
 *
 */
public class ResponsePassFail implements Response, Serializable
	{
	private static final long serialVersionUID = 1L;

	public boolean passed;
	public Integer id=-1;
	}
