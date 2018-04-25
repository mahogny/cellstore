package cellstore.server.response;

import java.io.Serializable;
import java.util.HashMap;

import cellstore.db.CellProjection;

/**
 * 
 * Response from server: List of DimReds
 * 
 * @author Johan Henriksson
 *
 */
public class ResponseListProjections implements Response, Serializable
	{
	private static final long serialVersionUID = 1L;

	public HashMap<Integer,CellProjection> list=new HashMap<>();
	}
