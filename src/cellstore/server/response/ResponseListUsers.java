package cellstore.server.response;

import java.io.Serializable;
import java.util.HashMap;

import cellstore.db.CellStoreUser;

/**
 * 
 * Response from server: List of users
 * 
 * @author Johan Henriksson
 *
 */
public class ResponseListUsers implements Response, Serializable
	{
	private static final long serialVersionUID = 1L;

	public HashMap<Integer,CellStoreUser> list=new HashMap<>();

	}
