package cellstore.server.conn;

import java.util.Collection;
import java.util.Map;

import cellstore.db.CellClustering;
import cellstore.db.CellDimRed;
import cellstore.db.CellSetFile;
import cellstore.db.CellStoreDB;
import cellstore.db.CellStoreDatasets;
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
		return db.datasets.dimreds.get(i);
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
	public CellClustering getClustering(int id)
		{
		return db.datasets.clusterings.get(id);
		}

	public Map<Integer, CellStoreUser> getAllUsers()
		{
		return db.user;
		}
	
	

	public Map<Integer, CellSetFile> getCellSetFiles()
		{
		return db.datasets.cellsets;
		}

	public CellSetFile getCellSetFile(int id)
		{
		CellSetFile csf=db.datasets.cellsets.get(id);
		if(csf==null)
			System.out.println("Tried to get csf "+id);
		return csf;
		}

	public Map<Integer, CellClustering> getClusterings()
		{
		return db.datasets.clusterings;
		}


	public Map<Integer, CellStoreDatasets> getStudies()
		{
		return db.grouping;
		}


	public boolean authenticate(String user, String password)
		{
		return true;
		}
	}
