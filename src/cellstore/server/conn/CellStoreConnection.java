package cellstore.server.conn;

import java.io.IOException;
import java.util.Collection;

import cellstore.db.CellClustering;
import cellstore.db.CellProjection;
import cellstore.db.CellSetFile;
import cellstore.viewer.event.CellStoreEvent;
import cellstore.viewer.event.CellStoreEventListener;

public interface CellStoreConnection
	{

	public void addListener(CellStoreEventListener e);

	public CellProjection getProjection(int i) throws IOException;
	public Integer putProjection(CellProjection p) throws IOException;
	public boolean removeProjection(int id) throws IOException;
	
	public void emitEvent(CellStoreEvent e);

	public Collection<Integer> getListClusterings() throws IOException;

	public CellClustering getClustering(int clId) throws IOException;

	public CellSetFile getCellSetFile(int id);

	
	public boolean authenticate(String user, String password) throws IOException;
	
	
	}
