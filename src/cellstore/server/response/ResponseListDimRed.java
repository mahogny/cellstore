package cellstore.server.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import cellstore.db.CellDimRed;

/**
 * 
 * Response from server: List of DimReds
 * 
 * @author Johan Henriksson
 *
 */
public class ResponseListDimRed implements Response, Serializable
	{
	private static final long serialVersionUID = 1L;

	public HashMap<Integer,CellDimRed> list=new HashMap<>();
	}
