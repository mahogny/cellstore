package cellstore.server.conn;

import java.io.IOException;
import java.util.Collection;

import cellstore.db.CellClustering;
import cellstore.db.CellDimRed;
import cellstore.db.CellSetFile;

public interface CellStoreConnection
	{

	public	CellDimRed getDimRed(int i) throws IOException;

	public Collection<Integer> getListClusterings() throws IOException;

	public CellClustering getClustering(int clId) throws IOException;

	public CellSetFile getCellSetFile(int id);

	
	public boolean authenticate(String user, String password) throws IOException;
	}
