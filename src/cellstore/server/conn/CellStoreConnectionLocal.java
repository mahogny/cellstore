package cellstore.server.conn;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import cellstore.db.CellClustering;
import cellstore.db.CellDimRed;
import cellstore.db.CellSetFile;
import cellstore.db.CellStoreDB;
import cellstore.db.CellStoreUser;

/**
 * Connection to a database
 * 
 * @author Johan Henriksson
 *
 */
public class CellStoreConnectionLocal implements CellStoreConnection
	{
	private CellStoreDB db;

	public CellStoreConnectionLocal(CellStoreDB db2)
		{
		db=db2;
		}

	@Override
	public CellDimRed getDimRed(int i)
		{
		return db.datasets.dimreds.get(0);
		}

	public Map<Integer, CellDimRed> getDimReds()
		{
		return db.datasets.dimreds;
		}

	@Override
	public Collection<Integer> getListClusterings()
		{
		return db.datasets.clusterings.keySet();
		}

	@Override
	public CellClustering getClustering(int clId)
		{
		return db.datasets.clusterings.get(clId);
		}

	public Map<Integer, CellStoreUser> getAllUsers()
		{
		return db.user;
		}
	
	

	public Map<Integer, CellSetFile> getCellSetFiles()
		{
		return db.datasets.cellsets;
		}


	
	
	
	}
