package cellstore.viewer.projection;

import cellstore.db.CellClustering;

/**
 * One option as for what you can color by. For now only clusterings
 */
public class ProjectionColorBy
	{
	public CellClustering clustering;
				
	@Override
	public String toString()
		{
		return clustering.name;
		}
	}