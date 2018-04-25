package cellstore.server.conn;

import java.io.IOException;
import java.util.Collection;

import cellstore.db.CellClustering;
import cellstore.db.CellProjection;
import cellstore.db.CellSetFile;

public interface CellStoreConnection
	{

	public	CellProjection getProjection(int i) throws IOException;

	public Collection<Integer> getListClusterings() throws IOException;

	public CellClustering getClustering(int clId) throws IOException;

	public CellSetFile getCellSetFile(int id);

	public Integer putProjection(CellProjection p) throws IOException;

	
	public boolean authenticate(String user, String password) throws IOException;
	}
