package cellstore.server.conn;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.WeakHashMap;

import cellstore.db.CellClustering;
import cellstore.db.CellConnectivity;
import cellstore.db.CellProjection;
import cellstore.db.CellSetFile;
import cellstore.db.CellStoreDB;
import cellstore.db.CellStoreDatasets;
import cellstore.db.CellStoreUser;
import cellstore.viewer.event.CellStoreEventListener;
import cellstore.viewer.event.CellStoreEventProjectionsUpdated;
import cellstore.viewer.event.CellStoreEvent;

/**
 * Connection to a database
 * 
 * @author Johan Henriksson
 *
 */
public class CellStoreConnectionLocal implements CellStoreConnection
	{
	private CellStoreDB db;
	private WeakHashMap<CellStoreEventListener, Object> mapListeners=new WeakHashMap<>();

	/**
	 * Create a local connection
	 */
	public CellStoreConnectionLocal(CellStoreDB db2)
		{
		db=db2;
		}

	/**
	 * Send an event to all listeners
	 */
	public void emitEvent(CellStoreEvent e)
		{
		for(CellStoreEventListener list:mapListeners.keySet())
			list.cellStoreEvent(e);
		}
	
	/**
	 * Add an event listener
	 */
	public void addListener(CellStoreEventListener e)
		{
		mapListeners.put(e, null);
		}
	
	@Override
	public CellProjection getProjection(int i)
		{
		return db.datasets.projections.get(i);
		}

	public Map<Integer, CellProjection> getProjections()
		{
		return db.datasets.projections;
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
			System.out.println("Tried but failed to get csf "+id);
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


	@Override
	public Integer putProjection(CellProjection p) throws IOException
		{
		throw new IOException("not implemented");
		}
	
	public Integer putUser(CellStoreUser user) throws IOException
		{
		Integer ret=db.putNewUser(user);
		//emitEvent(new ...());
		return ret;
		}
	
	public boolean removeProjection(int id) throws IOException
		{
		boolean ret=db.removeProjection(id);
		emitEvent(new CellStoreEventProjectionsUpdated());
		return ret;
		}

	@Override
	public Collection<Integer> getListConnectivity() throws IOException
		{
		return db.datasets.connectivity.keySet();
		}

	@Override
	public CellConnectivity getConnectivity(int clId) throws IOException
		{
		return db.datasets.connectivity.get(clId);
		}

	}
