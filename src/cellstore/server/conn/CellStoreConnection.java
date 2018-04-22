package cellstore.server.conn;

import java.io.IOException;
import java.util.Collection;

import cellstore.db.CellClustering;
import cellstore.db.CellDimRed;

public interface CellStoreConnection
	{

	CellDimRed getDimRed(int i) throws IOException;

	Collection<Integer> getListClusterings() throws IOException;

	CellClustering getClustering(int clId) throws IOException;

	}
