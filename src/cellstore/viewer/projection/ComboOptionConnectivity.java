package cellstore.viewer.projection;

import cellstore.db.CellConnectivity;

/**
 * Option for a combobox
 */
public class ComboOptionConnectivity
	{
	public CellConnectivity connectivity;
				
	@Override
	public String toString()
		{
		if(connectivity!=null)
			return connectivity.name;
		else
			return "";
		}
	}