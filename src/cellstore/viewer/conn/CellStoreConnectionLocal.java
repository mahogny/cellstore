package cellstore.viewer.conn;

import java.util.Collection;

import db.CellClustering;
import db.CellDimRed;
import db.CellStoreDB;

/**
 * Connection to a database
 * 
 * @author Johan Henriksson
 *
 */
public class CellStoreConnectionLocal implements CellStoreConnection
	{
	public CellStoreDB db;

	/* (non-Javadoc)
	 * @see cellstore.viewer.CellStoreConnectionInterface#getDimRed(int)
	 */
	@Override
	public CellDimRed getDimRed(int i)
		{
		return db.datasets.dimreds.get(0);
		}

	/* (non-Javadoc)
	 * @see cellstore.viewer.CellStoreConnectionInterface#getListClusterings()
	 */
	@Override
	public Collection<Integer> getListClusterings()
		{
		return db.datasets.clusterings.keySet();
		}

	/* (non-Javadoc)
	 * @see cellstore.viewer.CellStoreConnectionInterface#getClustering(int)
	 */
	@Override
	public CellClustering getClustering(int clId)
		{
		return db.datasets.clusterings.get(clId);
		}

	
	
	
	}
