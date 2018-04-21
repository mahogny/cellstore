package cellstore.viewer.conn;

import java.util.Collection;

import db.CellClustering;
import db.CellDimRed;

/**
 * Connection to a database
 * 
 * @author Johan Henriksson
 *
 */
public class CellStoreConnectionServer implements CellStoreConnection
	{

	@Override
	public CellDimRed getDimRed(int i)
		{
		//return db.datasets.dimreds.get(0);
		return null;
		}

	@Override
	public Collection<Integer> getListClusterings()
		{
		//return db.datasets.clusterings.keySet();
		return null;
		}

	@Override
	public CellClustering getClustering(int clId)
		{
		//return db.datasets.clusterings.get(clId);
		return null;
		}

	
	
	
	}
