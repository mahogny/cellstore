package cellstore.db;

/**
 * Key for lookup table
 */
class CellRef
	{
	public int set;
	public int cell;
	
	public int hashCode()
		{
		return set+cell;
		}
	
	public boolean equals(Object obj)
		{
		if(obj instanceof CellRef)
			{
			CellRef b=(CellRef)obj;
			return set==b.set && cell==b.cell;
			}
		else
			return false;
		}

	public CellRef(int set, int cell)
		{
		this.set = set;
		this.cell = cell;
		}
	
	@Override
	public String toString()
		{
		return "("+set+","+cell+")";
		}
	}