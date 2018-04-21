package cellstore.viewer.conn;

import java.util.Collection;

import db.CellClustering;
import db.CellDimRed;

public interface CellStoreConnection
	{

	CellDimRed getDimRed(int i);

	Collection<Integer> getListClusterings();

	CellClustering getClustering(int clId);

	}
