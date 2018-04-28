package cellstore.viewer.projection;

import cellstore.db.CellClustering;

/**
 * One option as for what you can color by. For now only clusterings
 */
public class ComboOptionClustering
	{
	public CellClustering clustering;
				
	@Override
	public String toString()
		{
		if(clustering!=null)
			return clustering.name;
		else
			return "";
		}
	}