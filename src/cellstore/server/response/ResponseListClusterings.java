package cellstore.server.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import cellstore.db.CellClustering;
import cellstore.db.CellDimRed;

/**
 * 
 * Response from server: List of clusterings
 * 
 * @author Johan Henriksson
 *
 */
public class ResponseListClusterings implements Response, Serializable
	{
	private static final long serialVersionUID = 1L;

	public HashMap<Integer,CellClustering> list=new HashMap<>();

	}
