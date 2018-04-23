package cellstore.server.response;

import java.io.Serializable;

/**
 * 
 * General response, pass/fail
 * 
 * @author Johan Henriksson
 *
 */
public class ResponseAuthChallenge implements Response, Serializable
	{
	private static final long serialVersionUID = 1L;

	public String salt;
	public String moresalt;
	}
